
package edu.nps.moves.disutil;

import edu.nps.moves.dis.Pdu;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * <p>A UDP server that receives DIS PDU packets and fires off events
 * to interested parties. The event firing is on the socket-processing
 * thread, so be quick about handling the events.</p>
 *
 * <p>Using the new {@link java.nio.ByteBuffer}-based marshalling is
 * more efficient than the old IO Streams technique, and it also supports
 * receiving several DIS PDUs in a single UDP datagram. Each datagram
 * is scraped sequentially so that if one PDU is placed after another,
 * they will each be unmarshalled, and an event will be fired off for
 * each one in turn.</p>
 *
 * @author Robert Harder
 * @since ??
 */
public class PduNioMulticastReceiver extends NioServer {

    private final static Logger LOGGER = Logger.getLogger(PduNioMulticastReceiver.class.getName());

    private Collection<PduNioMulticastReceiver.Listener> listeners = new LinkedList<PduNioMulticastReceiver.Listener>();    // Event listeners
    private PduNioMulticastReceiver.Event event = new PduNioMulticastReceiver.Event(this);

    private PduFactory pduFactory;
    private Pdu pdu;    // Last pdu received
    //private ByteBuffer buffer;
    private boolean unmarshalWithByteBuffer = true; // instead of data input stream
    //private boolean lookForMultiplePdusPerPacket;


    /**
     * Creates a new instance of PduMulticastReceiver that is
     * in the {@link UdpServer.State#STOPPED} state
     */
    public PduNioMulticastReceiver(){
        super();
        initComponents();
    }

    private void initComponents(){
//        final DatagramPacket packet = super.getPacket();        // Long-lived, shared packet
//        buffer = ByteBuffer.wrap( packet.getData() );           // Wrap the data portion
        pduFactory = new PduFactory();

        super.addNioServerListener(new NioServer.Adapter() {
            Pdu temp;
            @Override
            public void nioServerUdpDataReceived(NioServer.Event evt) {
                try{
                    temp = null;
                    // Efficient and clean
                    if( unmarshalWithByteBuffer ){
                        ByteBuffer buffer = evt.getBuffer();
                        while( (temp = pduFactory.createPdu(buffer)) != null ){
                            firePduReceived( temp );
                        }   // end while: more pdus to check
                    }   // end if: use byte buffer

                    // Inefficient and dirty
                    else {
                        ByteBuffer buffer = evt.getBuffer();
                        byte[] data = new byte[ buffer.remaining() ];
                        buffer.get(data);
                        temp = pduFactory.createPdu(data);
                        firePduReceived(temp);
                    }   // end else: use old system
                }catch(Exception e){
                    System.err.println("Encountered an error. Please contact open-dis developers.");
                    e.printStackTrace();
                }
            }   // end packet received

        }); // end listener
    }


    /**
     * Returns the last parsed PDU.
     */
    public Pdu getPdu(){
        return this.pdu;
    }


    /**
     * Returns whether or not the ByteBuffer marshalling
     * technique is being used (default).
     */
    public boolean getUseByteBuffer(){
        return this.unmarshalWithByteBuffer;
    }

    /**
     * Sets whether or not to use the more efficient
     * ByteBuffer marshalling technique (default).
     * @param use whether or not to use it
     */
    public void setUseByteBuffer( boolean use ){
        this.unmarshalWithByteBuffer = use;
    }


    /**
     * Returns whether or not FastEspdu objects
     * are created which use less memory since
     * all their fields are flattened to primitives
     * instead of several objects.
     * @return using or not using fast pdu
     */
    public boolean getUseFastPdu(){
        return this.pduFactory.getUseFastPdu();
    }


    /**
     * Sets whether or not to generate the
     * Fast Espdu packets (all primitive fields).
     * @param use
     */
    public void setUseFastEspdu( boolean use ){
        this.pduFactory.setUseFastPdu(use);
    }


/* ********  E V E N T S  ******** */



    /** Adds a {@link Listener}.
     * @param l the UdpServer.Listener
     */
    public synchronized void addPduMulticastReceiverListener(PduNioMulticastReceiver.Listener l) {
        listeners.add(l);
    }

    /** Removes a {@link Listener}.
     * @param l the UdpServer.Listener
     */
    public synchronized void removePduMulticastReceiverListener(PduNioMulticastReceiver.Listener l) {
        listeners.remove(l);
    }


    /**
     * Fires event on calling thread.
     * @param pdu 
     */
    protected synchronized void firePduReceived( Pdu pdu ) {
        this.pdu = pdu;
        PduNioMulticastReceiver.Listener[] ll = listeners.toArray(new PduNioMulticastReceiver.Listener[ listeners.size() ] );
        for( PduNioMulticastReceiver.Listener l : ll ){
            try{
                l.pduReceived(event);
            } catch( Exception exc ){
                LOGGER.warning("PduMulticastReceiver.Listener " + l + " threw an exception: " + exc.getMessage() );
            }   // end catch
        }   // end for: each listener
     }  // end fireUdpServerPacketReceived






/* ********  L I S T E N E R  ******** */


    public static interface Listener extends java.util.EventListener{


        /**
         * Called when a packet is received. This is called on the IO thread,
         * so don't take too long, and if you want to offload the processing
         * to another thread, be sure to copy the data out of the datagram
         * since it will be clobbered the next time around.
         *
         * @param evt the event
         * @see Event#getPdu
         */
        public abstract void pduReceived( PduNioMulticastReceiver.Event evt );


    }   // end inner static class Listener



/* ********  E V E N T  ******** */


    public static class Event extends java.util.EventObject  {


        /**
         * Creates a Event based on the given {@link PduMulticastReceiver}.
         * @param src the source of the event
         */
        public Event( PduNioMulticastReceiver src ){
            super(src);
        }

        /**
         * Returns the {@link Pdu} for this event. Since the server
         * runs on a single thread, this method is a shorthand
         * for <code>((PduMulticastReceiver)getSource()).getPdu()</code>.
         * @return the {@link Pdu}
         */
        public Pdu getPdu(){
            return ((PduNioMulticastReceiver)getSource()).getPdu();
        }
    }

}
