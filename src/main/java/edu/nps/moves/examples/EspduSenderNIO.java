package edu.nps.moves.examples;

import java.io.*;
import java.net.*;
import java.util.*;

import edu.nps.moves.dis.*;

/**
 * Creates and sends ESPDUs in IEEE binary format. very similar to EspduSender.java, but
 * this uses Robert Harder's more memory efficient NIO methods for marshalling.
 *
 * @author DMcG
 */
public class EspduSenderNIO
{
    /** multicast group we send on */
    public static final String MULTICAST_GROUP="239.1.2.3";
   
    /** Port we send on */
    public static final int    PORT = 62040;
    
public static void main(String args[])
{
    EntityStatePdu espdu = new EntityStatePdu();
    MulticastSocket socket;
    InetAddress     address;
    
    espdu.setExerciseID((short)0);
    
    // The EID is the unique identifier for objects in the world. This 
    // EID should match up with the ID for the object specified in the 
    // VMRL/x3d world.
    EntityID eid = espdu.getEntityID();
    eid.setSite(1); // 0 is apparently not a valid site number
    eid.setApplication(1);
    eid.setEntity(2);

    try
    {
        socket = new MulticastSocket(PORT);
        address = InetAddress.getByName(MULTICAST_GROUP);
        socket.joinGroup(address);
        
        while(true)
        {
            for(int idx = 0; idx < 100; idx++)
            {
                // The timestamp should be monotonically increasing. Many implementations
                // discard packets that have earlier timestamps (assumption is that it
                // arrived out of order) or non-increasing timestamp (dupe packet).
                // The time should be slaved to clock time, so we can determine the time
                // between packets, but this is the minimum for testing.
                long timestamp = espdu.getTimestamp();
                timestamp++;
                espdu.setTimestamp(timestamp);
                
                // Modify the x-axis position of the object
                Vector3Double location = espdu.getEntityLocation();
                location.setX(idx);
                location.setY(idx);
                
                // Do some rotation to make sure that works
                Orientation orientation = espdu.getEntityOrientation();
                float psi = orientation.getPsi();
                psi = psi + idx;
                orientation.setPsi(psi);
                orientation.setTheta((float)(orientation.getTheta() + idx /2.0));
                
                // Marshal out the object to a byte array, then send a datagram
                // packet with that data in it. This uses Robert Harder's NIO
                // code for marshalling.
                byte data[] = espdu.marshal();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, PORT);
                
                socket.send(packet);

                // Almost any sender will overwhelm a receiver if not constrained. This
                // slows down the send rate so the receiver has enough time to process it
                Thread.sleep(1000);
                
                System.out.println("Moving espdu");
            }
        }
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
        
}

}
