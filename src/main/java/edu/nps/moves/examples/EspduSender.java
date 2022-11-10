package edu.nps.moves.examples;

import java.io.*;
import java.net.*;
import java.util.*;

import edu.nps.moves.dis.*;
import edu.nps.moves.disutil.CoordinateConversions;
import edu.nps.moves.disutil.DisTime;
import static edu.nps.moves.examples.EspduSender.NUMBER_TO_SEND;
import java.util.concurrent.TimeUnit;

/**
 * Creates and sends ESPDUs in IEEE binary format.
 *
 * @author DMcG
 */
public class EspduSender {

    public static final int NUMBER_TO_SEND = 5000;

    public enum NetworkMode {
        UNICAST, MULTICAST, BROADCAST
    };

    /**
     * Default multicast group address we send on
     */
    public static final String DEFAULT_MULTICAST_GROUP = "239.1.2.3";

    /**
     * Default port we send on
     */
    public static final int DIS_DESTINATION_PORT = 3000;
    
    public static final int DIS_HEARTBEAT_SECS = 10;

    /**
     * Possible system properties, passed in via -Dattr=val networkMode:
     * unicast, broadcast, multicast destinationIp: where to send the packet. If
     * in multicast mode, this can be multicast. To determine broadcast
     * destination IP, use an online broadcast address calculator, for example
     * http://www.remotemonitoringsystems.ca/broadcast.php If in multicast mode,
     * a join() will be done on the multicast address. port: port used for both
     * source and destination.
     *
     * @param args
     */
    public static void main(String args[]) throws UnknownHostException, IOException, RuntimeException, InterruptedException {

        MulticastSocket socket = null; // must be initialized, even if null
        Set<InetAddress> broadcastAddresses;

        // Default settings. These are used if no system properties are set. 
        // If system properties are passed in, these are over ridden.
        int port = DIS_DESTINATION_PORT;
        NetworkMode mode = NetworkMode.MULTICAST;
        InetAddress destinationIp = null; // must be initialized, even if null

        destinationIp = InetAddress.getByName(DEFAULT_MULTICAST_GROUP);

        // All system properties, passed in on the command line via -Dattribute=value
        final Properties systemProperties = System.getProperties();

        // IP address we send to
        final String destinationIpString = systemProperties.getProperty("destinationIp");

        // Port we send to, and local port we open the socket on
        final String portString = systemProperties.getProperty("port");

        // Network mode: unicast, multicast, broadcast
        final String networkModeString = systemProperties.getProperty("networkMode"); // unicast or multicast or broadcast

        // Set up a socket to send information
        // Port we send to
        if (portString != null) {
            port = Integer.parseInt(portString);
        }

        // Where we send packets to, the destination IP address
        if (destinationIpString != null) {
            destinationIp = InetAddress.getByName(destinationIpString);
        }

        // Type of transport: unicast, broadcast, or multicast
        // TODO convert to String constants
        if (networkModeString != null) {
            if (networkModeString.equalsIgnoreCase("unicast")) {
                mode = NetworkMode.UNICAST;
            } else if (networkModeString.equalsIgnoreCase("broadcast")) {
                mode = NetworkMode.BROADCAST;
            } else if (networkModeString.equalsIgnoreCase("multicast")) {
                mode = NetworkMode.MULTICAST;
            }
        }

        if (mode == NetworkMode.MULTICAST) {
            if (!destinationIp.isMulticastAddress()) {
                throw new RuntimeException("Sending to multicast address, but destination address " + destinationIp.toString() + "is not multicast");
            }
            socket = new MulticastSocket(port);
            socket.joinGroup(destinationIp);
        }

        
        EntityStatePdu espdu = new EntityStatePdu();

        // Initialize values in the Entity State PDU object. The exercise ID is 
        // a way to differentiate between different virtual worlds on one network.
        // Note that some values (such as the PDU type and PDU family) are set
        // automatically when you create the ESPDU.
        espdu.setExerciseID((short) 1);

        // The EID is the unique identifier for objects in the world. This 
        // EID should match up with the ID for the object specified in the 
        // VMRL/x3d/virtual world.
        EntityID entityID = espdu.getEntityID();
        entityID.setSite(1);  // 0 is apparently not a valid site number, per the spec
        entityID.setApplication(1);
        entityID.setEntity(2);

        // Set the entity type. SISO has a big list of enumerations, so that by
        // specifying various numbers we can say this is an M1A2 American tank,
        // the USS Enterprise, and so on. We'll make this a tank. There is a 
        // separate project elsehwhere in this project that implements DIS 
        // enumerations in C++ and Java, but to keep things simple we just use
        // numbers here.
        EntityType entityType = espdu.getEntityType();
        entityType.setEntityKind((short) 1);      // Platform (vs lifeform, munition, sensor, etc.)
        entityType.setCountry(225);              // USA
        entityType.setDomain((short) 1);          // Land (vs air, surface, subsurface, space)
        entityType.setCategory((short) 1);        // Tank
        entityType.setSubcategory((short) 1);     // M1 Abrams
        entityType.setSpec((short) 3);            // M1A2 Abrams

        
        DisTime disTime = DisTime.getInstance(); // TODO explain

        // ICBM coordinates for my office
        double lat = 36.595517;
        double lon = -121.877000;

        
        // Loop through sending N ESPDUs

        System.out.println("Sending " + NUMBER_TO_SEND + " ESPDU packets to " + destinationIp.toString() + ". One packet every " + DIS_HEARTBEAT_SECS + " seconds.");
        for (int idx = 0; idx < NUMBER_TO_SEND; idx++) {
            // DIS time is a pain in the ass. DIS time units are 2^31-1 units per
            // hour, and time is set to DIS time units from the top of the hour. 
            // This means that if you start sending just before the top of the hour
            // the time units can roll over to zero as you are sending. The receivers
            // (escpecially homegrown ones) are often not able to detect rollover
            // and may start discarding packets as dupes or out of order. We use
            // an NPS timestamp here, hundredths of a second since the start of the
            // year. The DIS standard for time is often ignored in the wild; I've seen
            // people use Unix time (seconds since 1970) and more. Or you can
            // just stuff idx into the timestamp field to get something that is monotonically
            // increasing.

            // Note that timestamp is used to detect duplicate and out of order packets. 
            // That means if you DON'T change the timestamp, many implementations will simply
            // discard subsequent packets that have an identical timestamp. Also, if they
            // receive a PDU with an timestamp lower than the last one they received, they
            // may discard it as an earlier, out-of-order PDU. So it is a good idea to
            // update the timestamp on ALL packets sent.
            // An alterative approach: actually follow the standard. It's a crazy concept,
            // but it might just work.
            int timestamp = disTime.getDisAbsoluteTimestamp();
            espdu.setTimestamp(timestamp);

            double disCoordinates[] = CoordinateConversions.getXYZfromLatLonDegrees(lat, lon, 1.0);
            Vector3Double location = espdu.getEntityLocation();
            location.setX(disCoordinates[0]);
            location.setY(disCoordinates[1]);
            location.setZ(disCoordinates[2]);

            // Optionally, we can do some rotation of the entity
            /*
            Orientation orientation = espdu.getEntityOrientation();
            float psi = orientation.getPsi();
            psi = psi + idx;
            orientation.setPsi(psi);
            orientation.setTheta((float)(orientation.getTheta() + idx /2.0));
             */
            // You can set other ESPDU values here, such as the velocity, acceleration,
            // and so on.
            
            // Marshal out the espdu object to a byte array, then send a datagram
            // packet with that data in it.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            espdu.marshal(dos);

            // The byte array here is the packet in DIS format. We put that into a 
            // datagram and send it.
            byte[] data = baos.toByteArray();
 
            DatagramPacket packet = new DatagramPacket(data,data.length, destinationIp, 3000);
            socket.send(packet);            

            location = espdu.getEntityLocation();

            System.out.println("Sent ESPDU #" + idx + " Site,App,Id=[" + entityID.getSite() + ", " + entityID.getApplication() + ", " + entityID.getEntity() + "]");
            System.out.println(" DIS coordinates location=[" + location.getX() + ", " + location.getY() + ", " + location.getZ() + "]");
            double lla[] = CoordinateConversions.xyzToLatLonDegrees(location.toArray());
            System.out.println(" Location (lat/lon/alt): [" + lla[0] + ", " + lla[1] + ", " + lla[2] + "]");

            System.out.println("Sleeping for heartbeat of " + DIS_HEARTBEAT_SECS + " seconds.");
            Thread.sleep(TimeUnit.SECONDS.toMillis(DIS_HEARTBEAT_SECS));
        }
    }

    /**
     * A number of sites get all snippy about using 255.255.255.255 for a
     * broadcast address; it trips their security software and they kick you off
     * their network. (Comcast, NPS.) This determines the broadcast address for
     * all connected interfaces, based on the IP and subnet mask. If you have a
     * dual-homed host it will return a broadcast address for both. If you have
     * some VMs running on your host this will pick up the addresses for those
     * as well--eg running VMWare on your laptop with a local IP this will also
     * pick up a 192.168 address assigned to the VM by the host OS.
     *
     * @return set of all broadcast addresses
     */
    public static Set<InetAddress> getBroadcastAddresses() {
        Set<InetAddress> broadcastAddresses = new HashSet<>();
        Enumeration interfaces;

        try {
            interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface anInterface = (NetworkInterface) interfaces.nextElement();

                if (anInterface.isUp()) {
                    Iterator it = anInterface.getInterfaceAddresses().iterator();
                    while (it.hasNext()) {
                        InterfaceAddress anAddress = (InterfaceAddress) it.next();
                        if ((anAddress == null || anAddress.getAddress().isLinkLocalAddress())) {
                            continue;
                        }

                        //System.out.println("Getting broadcast address for " + anAddress);
                        InetAddress broadcastAddress = anAddress.getBroadcast();
                        if (broadcastAddress != null) {
                            broadcastAddresses.add(broadcastAddress);
                        }
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return broadcastAddresses;
    }

}
