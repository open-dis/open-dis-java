package edu.nps.moves.examples;

import java.net.*;
import java.io.*;
import java.util.*;

import edu.nps.moves.dis.*;
import edu.nps.moves.disutil.PduFactory;

public class ReceiverPerformance 
{
    public static final int PORT=62040;
    public static final String MULTICAST_GROUP = "239.1.2.3";
    public static final boolean USE_FAST_ESPDU = false;
    

    public static void main(String args[])
    {
        PduFactory factory;
        MulticastSocket socket = null;
        InetAddress address = null;
        
        
        try
        {
            socket = new MulticastSocket(PORT);
            address = InetAddress.getByName(MULTICAST_GROUP);
            socket.joinGroup(address);
            
            factory = new PduFactory();
            
            while(true)
            {
                byte buffer[] = new byte[1500];
                DatagramPacket packet;
                
                packet = new DatagramPacket(buffer, buffer.length);
                
                socket.receive(packet);
                
                
                Pdu pdu = factory.createPdu(packet.getData());
                System.out.println("pdu is " + pdu.getPduType());
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }

}
