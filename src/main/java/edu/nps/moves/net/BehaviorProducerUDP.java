/*
 Copyright (c) 1995-2009 held by the author(s).  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer
      in the documentation and/or other materials provided with the
      distribution.
    * Neither the names of the Naval Postgraduate School (NPS)
      Modeling Virtual Environments and Simulation (MOVES) Institute
      (http://www.nps.edu and http://www.movesinstitute.org)
      nor the names of its contributors may be used to endorse or
      promote products derived from this software without specific
      prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/
package edu.nps.moves.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Vector;

import edu.nps.moves.dis.Pdu;
import edu.nps.moves.disutil.PduFactory;
import java.nio.Buffer;

/**
 * This implements an object that can read and write DIS PDUs from a unicast
 * or multicast UDP socket. It implements the BehaviorProducer interface,
 * which allows objects to register as listeners for PDU arrival events,
 * and the BehaviorWriter interface, which allows PDUs to be written. It's
 * a bit complex internally, but not all that bad from an interface standpoint.<p>
 *
 * This runs in a thread of its own. The listeners for PDU events can either
 * run in threads of their own, which is a bit complex, or simply process
 * PDU objects as they come in, which is simple but may have performance
 * problems if processing a PDU takes a long time.<p>
 *
 * @author DMcG
 */
public class BehaviorProducerUDP implements BehaviorProducerIF, // Listener pattern for pdus
                                            BehaviorWriterIF, // IF for writing DIS pdus
                                            Runnable // Threaded object
{

    /** People who listen to us for PDU events. This is a Vector rather than
     * the preferred List to preserve compatability with older VRML browsers
     * that don't have the newer List interface.
     */
    private Vector<BehaviorConsumerIF> behaviorConsumerListeners;

    /**
     * Whether listeners are given unique copies of PDUs, or a single shared
     * copy.
     */
    private boolean useCopies = true;

    /**
     * Socket (multicast or unicast) that sends and receives data
     */
    private final DatagramSocket socket;
    private DatagramPacket packet;
    private Pdu pdu;
    private Pdu copyPdu;
    private PduFactory pduf;
    
    /** An allocated receive only buffer */
    private ByteBuffer buffer;

    public BehaviorProducerUDP(DatagramSocket pSocket) {
        behaviorConsumerListeners = new Vector<BehaviorConsumerIF>();
        socket = pSocket;
        buffer = ByteBuffer.allocate(MTU_SIZE);
        packet = new DatagramPacket(buffer.array(), buffer.capacity());

        // Doesn't use FastEntityStatePdu (by default)
        pduf = new PduFactory();
    }

    public void addListener(BehaviorConsumerIF consumer) {
        // Add it only if absent, so we don't get dupe copies.
        if (!(behaviorConsumerListeners.contains(consumer))) {
            behaviorConsumerListeners.add(consumer);
        }
    }

    public void removeListener(BehaviorConsumerIF consumer) {
        behaviorConsumerListeners.remove(consumer);
    }
  
    public void setDefaultDestination(InetAddress addr, int port) {
        packet.setAddress(addr);
        packet.setPort(port);
    }

    public void write(ByteBuffer buffer) {
        packet.setData(buffer.array());
        try {
            socket.send(packet);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    /**
     * If we have a byte buffer we are marshalling to, it may be bigger than the
     * actuall size of the marshalled PDU. This writes only the first numberOfBytes
     * bytes of the ByteBuffer to the network.
     * 
     * @param buffer
     * @param numberOfBytes
     */
     public void write(ByteBuffer buffer, int numberOfBytes) {
        byte[] normalBuffer = buffer.array();
        packet.setData(normalBuffer, 0, numberOfBytes);
        try {
            socket.send(packet);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void setUseCopies(boolean shouldCreateCopy) {
        useCopies = shouldCreateCopy;
    }

    /** Entry point for thread */
    public void run() {        
        
        while (true) {
            try {
                final Buffer buff = buffer.rewind();
                socket.receive(packet);

                // ByteBuffers are not thread safe
                synchronized (buff) {
                    pdu = pduf.createPdu(buffer);
                    if (pdu != null) {
                        for (BehaviorConsumerIF consumer : behaviorConsumerListeners) {

                            // Use a copy of the received PDU for more safety, or send a single
                            // copy of the object to multiple listeners for better performance.
                            if (useCopies) {
                                copyPdu = pduf.createPdu(buffer);
                                consumer.receivePdu(copyPdu);
                            } else {
                                consumer.receivePdu(pdu);
                            }
                        }
                    }
                }
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
    }

} // end class file BehaviorProducerUDP.java