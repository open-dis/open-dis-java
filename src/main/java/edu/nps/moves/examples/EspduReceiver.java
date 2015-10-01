package edu.nps.moves.examples;

import java.net.*;
import java.util.*;

import edu.nps.moves.disutil.*;

import edu.nps.moves.dis.*;

/**
 * Receives PDUs from the network in IEEE format.
 *
 * @author DMcG
 * @version $Id:$
 */
public class EspduReceiver {

    /** Max size of a PDU in binary format that we can receive. This is actually
     * somewhat outdated--PDUs can be larger--but this is a reasonable starting point
     */
    public static final int MAX_PDU_SIZE = 8192;

    public static void main(String args[]) {
        MulticastSocket socket;
        DatagramPacket packet;
        InetAddress address;
        PduFactory pduFactory = new PduFactory();

        try {
            // Specify the socket to receive data
            socket = new MulticastSocket(3001);
            socket.setBroadcast(true);
       
            //address = InetAddress.getByName(EspduSender.DEFAULT_MULTICAST_GROUP);
            //socket.joinGroup(address);

            // Loop infinitely, receiving datagrams
            while (true) {
                byte buffer[] = new byte[MAX_PDU_SIZE];
                packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);

                List<Pdu> pduBundle = pduFactory.getPdusFromBundle(packet.getData());
                System.out.println("Bundle size is " + pduBundle.size());
                
                Iterator it = pduBundle.iterator();

                while(it.hasNext())
                {
                    Pdu aPdu = (Pdu)it.next();
                
                    System.out.print("got PDU of type: " + aPdu.getClass().getName());
                    if(aPdu instanceof EntityStatePdu)
                    {
                        EntityID eid = ((EntityStatePdu)aPdu).getEntityID();
                        Vector3Double position = ((EntityStatePdu)aPdu).getEntityLocation();
                        System.out.print(" EID:[" + eid.getSite() + ", " + eid.getApplication() + ", " + eid.getEntity() + "] ");
                        System.out.print(" Location in DIS coordinates: [" + position.getX() + ", " + position.getY() + ", " + position.getZ() + "]");
                    }
                    System.out.println();
                } // end trop through PDU bundle

            } // end while
        } // End try
        catch (Exception e) {

            System.out.println(e);
        }


    } // end main
} // end class
