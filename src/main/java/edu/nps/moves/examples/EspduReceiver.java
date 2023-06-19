package edu.nps.moves.examples;

import edu.nps.moves.disutil.*;
import edu.nps.moves.dis.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example code that receives ESPDUs from the network in IEEE format.
 */
public class EspduReceiver {

    public static void main(String args[]) throws IOException, InterruptedException {

        DisConnection con = new DisConnection(EspduSender.DEFAULT_MULTICAST_GROUP, EspduSender.DIS_PORT);
        new Thread(con).start(); // In this thread we receive pdu's from the network and put them into a queue

        // In this thread we take pdu's off the queue and process them.
        new Runnable() {

            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Pdu pdu = con.getNext();
                        if (pdu != null) {
                            System.out.println("Received PDU of type: " + pdu.getClass().getName());
                            if (pdu instanceof EntityStatePdu) {
                                EntityStatePdu espdu = (EntityStatePdu) pdu;
                                System.out.println(" Marking: " + espdu.getMarking().getCharactersString());
                                EntityID eid = espdu.getEntityID();
                                System.out.println(" Site, App, Id: [" + eid.getSite() + ", " + eid.getApplication() + ", " + eid.getEntity() + "] ");
                                Vector3Double position = espdu.getEntityLocation();
                                System.out.println(" Location in DIS geocentric xyz coordinates: [" + position.getX() + ", " + position.getY() + ", " + position.getZ() + "]");
                                final double[] latlon = CoordinateConversions.xyzToLatLonDegrees(position.toArray());
                                System.out.println(" Location in Latitude Longitude Elevation: [" + latlon[0] + ", " + latlon[1] + ", " + latlon[2] + "]");
                            }
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DisConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.run();
    }
}
