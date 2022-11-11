package edu.nps.moves.examples;

import java.net.*;
import edu.nps.moves.disutil.*;
import edu.nps.moves.dis.*;
import java.io.IOException;

/**
 * Receives PDUs from the network in IEEE format.
 *
 * @author DMcG
 * @version $Id:$
 */
public class EspduReceiver {

    public static final int MAX_PDU_SIZE = 16384;

    public static final int DIS_PORT = 3000;

    public static void main(String args[]) throws IOException {

        MulticastSocket socket = new MulticastSocket(DIS_PORT);
        InetAddress address = InetAddress.getByName(EspduSender.DEFAULT_MULTICAST_GROUP);
        socket.joinGroup(address);

        DatagramPacket packet;
        PduFactory pduFactory = new PduFactory();

        // Loop infinitely, receiving datagrams
        while (true) {
            byte buffer[] = new byte[MAX_PDU_SIZE];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            Pdu aPdu = pduFactory.createPdu(packet.getData());

            System.out.println("Received PDU of type: " + aPdu.getClass().getName());
            if (aPdu instanceof EntityStatePdu) {
                EntityID eid = ((EntityStatePdu) aPdu).getEntityID();
                Vector3Double position = ((EntityStatePdu) aPdu).getEntityLocation();
                System.out.println(" Site,App,Id:[" + eid.getSite() + ", " + eid.getApplication() + ", " + eid.getEntity() + "] ");
                System.out.println(" Location in DIS coordinates: [" + position.getX() + ", " + position.getY() + ", " + position.getZ() + "]");

                final double[] latlon = CoordinateConversions.xyzToLatLonDegrees(position.toArray());
                System.out.println(" Location in Latitude Longitude Elevation: [" + latlon[0] + ", " + latlon[1] + ", " + latlon[2] + "]");
            }
        }
    }
}
