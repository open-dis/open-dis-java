package edu.nps.moves.logger;

import java.io.*;
import java.net.*;
import java.util.*;

import edu.nps.moves.dis.*;
import edu.nps.moves.disutil.*;


/**
 * Logs DIS packets to XML format. You can easily receive DIS packets off the wire faster than
 * they can be logged to XML, so you need to be careful about that. With a 2 GHz
 * core duo and OSX, on a macbook pro laptop drive, you can log roughly 1,000 
 * packets per second.<p>
 * 
 * Since we cannot hold all the PDUs in memory at once, we set up a rotating 
 * system in which packets are read into a list, then, when the list is full,
 * the list is handed off to a separate thread for marshalling to XML. This 
 * gives us at least a chance of not dropping packets while writing to file.<p>
 * 
 * The classes here and in LogReplay are intended to be used from the command
 * line, but it should be easy to wrap a GUI around them.<p>
 * 
 * the classes are configured via a properties file. this defines the multicast
 * group to listen on, the directory to which files should be written, etc.
 * 
 * @author DMcG
 * @version $Id:$
 */
public class DisLogger implements Runnable {

    /** Maximum PDU size. Theoretically this can be 8K or more, but all our stuff is smaller */
    public static final int MAX_PDU_SIZE = 2048;
    public static final int MAX_PDU_LOGFILE_SIZE = 100;
    /** How long to wait for a packet to arrive, in ms, before throwing an exception */
    public static final int READ_TIMEOUT = 5000;
    /** How many PDUs to save per file */
    private int pdusPerFile;
    /** Multicast group to listen on */
    private InetAddress multicastGroup;
    /** port to listen on */
    private int port;
    /** Exercise name */
    private String exerciseName;
    /** socket to listen for PDUs */
    private MulticastSocket socket;
    /** should we continue reading?*/
    private boolean done = false;
    private LogWriter logWriter;

    /** Breaks us out of network read log loop (perhaps after READ_TIMEOUT has passed */
    public void setDone() {
        done = true;
    }

    /**
     * Create a new DIS logger with the given properties object. This includes the
     * multicast group, port, an exercise name, and the number of pdus per file 
     */
    public DisLogger(Properties loggerProperties) {
        String stringMulticastGroup = loggerProperties.getProperty("multicastGroup");
        String stringPort = loggerProperties.getProperty("port");
        exerciseName = loggerProperties.getProperty("exerciseName");
        String stringPdusPerFile = loggerProperties.getProperty("pdusPerFile");


        System.out.println("Multicast group: " + stringMulticastGroup + " port:" + stringPort +
                " exerciseName: " + exerciseName);

        try {
            port = Integer.parseInt(stringPort);
            pdusPerFile = Integer.parseInt(stringPdusPerFile);
            multicastGroup = InetAddress.getByName(stringMulticastGroup);
            if (multicastGroup.isMulticastAddress() == false) {
                System.out.println("The address " + stringMulticastGroup + " is not a multicast address");
                System.exit(0);
            }

            socket = new MulticastSocket(port);
            socket.setSoTimeout(READ_TIMEOUT);
            socket.joinGroup(multicastGroup);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Format error in the properties file. Make sure the port number is an integer and ");
            System.out.println("that the multicast address is in the correct format");
        }

    }

    /**
     * Run the logger. Create a new factory to decode IEEE format PDUs, and start reading
     * into the buffer. When the buffer fills we do a quick-change and write the buffer to
     * a file in another thread, so we don't drop (too many) packets.
     */
    public void run() {
        PduFactory pduFactory = new PduFactory();
        Pdu pdu;
        List<Pdu> currentPduList = new ArrayList<Pdu>(100);
        boolean timedout = false;
        int count = 0;

        if (logWriter == null) {
            logWriter = new LogWriter(exerciseName);
            Thread writerThread = new Thread(logWriter);
            writerThread.setDaemon(false);
            writerThread.start();
            Thread.yield();
        }

        System.out.println("Starting to listen for PDUs");
        while (done == false) {
            byte buffer[] = new byte[MAX_PDU_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                try {
                    socket.receive(packet);
                    logWriter.setUnqueuedPdus(true);
                    count++;
                } catch (SocketTimeoutException ste) {
                    timedout = true; // should only happen if we exceed read timeout limit
                }

                if (timedout == false) {
                    // Convert the byte array to an object
                    byte pduBytes[] = packet.getData();
                    pdu = pduFactory.createPdu(pduBytes);

                    // If we got back a valid packet, add it to our list
                    if (pdu != null) {
                        currentPduList.add(pdu);
                    }

                } // end of timed out

                // Once we pass a threshold of the number of PDUs we want per
                // file, we write those out to a log file. We do this in a 
                // separate thread so that we have a fighting chance of keeping
                // up with new incoming PDUs. we also write to file if the user
                // has specified he's done reading. We 
                if ((currentPduList.size() > pdusPerFile) || (done == true)) {
                    List<Pdu> pdusToLog = currentPduList;
                    currentPduList = new ArrayList<Pdu>();

                    // Tell the log writer thread to log this batch of PDUs. It
                    // may already be involved in writing a list, so we add it to
                    // a queue and the writer task will get to it as it can.
                    logWriter.addListToWriteQueue(pdusToLog);
                    logWriter.setUnqueuedPdus(false);
                    Thread.yield(); // give writer task an opportunity to be scheduled
                }

            } catch (Exception e) {
                System.out.println(e);
            }

            timedout = false;

        } // end loop

        System.out.println("PDUs captured: " + count);

    }

    /**
     * We may have broken out of the netowrk read loop, but the writer task is
     * still involved in flushing out the already captured PDUs to disk. This
     * gives us a test until that is done.
     */
    public boolean finishedWriting() {
        if (done == false) {
            return false;
        }

        return logWriter.finishedWriting();
    }

    /**
     * Entry point. Pass in the properties file to initialze from the command line.
     * @param args
     */
    public static void main(String args[]) {
        // Must always pass at least the property file name
        if (args.length != 1) {
            System.out.println("Usage: DisLogger propertyFile");
            System.exit(0);
        }
        System.out.println("Properties file=" + args[0]);


        // preflight the property file passed in to make sure it exists
        try {
            Properties loggerProperties = new Properties();

            // Load the configuration properties file
            InputStream propsFile = DisLogger.class.getResourceAsStream(args[0]);
            loggerProperties.load(propsFile);

            System.out.println("loaded properties file");

            // Start the logger thread listening
            DisLogger logger = new DisLogger(loggerProperties);
            Thread loggerThread = new Thread(logger);
            loggerThread.setDaemon(false);
            loggerThread.start();

            // Let it listen for a while
            Thread.sleep(100000);

            // tell the logger thread to stop
            logger.setDone();

            Thread.sleep(1000);

            // Wait for the already-collected Pdus to be flushed out to disk
            while (logger.finishedWriting() == false) {
                Thread.yield();
            }

        } catch (Exception e) {
            System.out.println("File not found; check to make sure properties file exists");
            System.out.println(e);
        }

        System.out.println("exiting main thread of logger");

    }
}
