
package edu.nps.moves.logger;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Reads from the log files, replaying the data on a multicast group.
 * 
 * @author mcgredo
 */
public class LogReplay 
{
    /** time, in milliseconds, that it is not worth sleeping for */
    public static final int MIN_SLEEP_TIME = 2;
    
    /** Port for sending socket */
    private int port;
    
    /** Multicast group to replay log file on */
    private InetAddress multicastGroup;
    
    /** Multicast socket for sending */
    private MulticastSocket socket;
    
    /** Exercise name */
    private String exerciseName;

    /** Properties config file */
    private Properties configuration;
    
    private long timeLastSent = 0;
    
    private int count = 0;

    public LogReplay(Properties properties)
    {
        configuration = properties;
        
        try
        {
            exerciseName = properties.getProperty("exerciseName");
            port = Integer.parseInt(properties.getProperty("port"));

            multicastGroup = InetAddress.getByName(properties.getProperty("multicastGroup"));
            
            if(multicastGroup.isMulticastAddress() == false)
            {
                System.out.println("The address " + properties.getProperty("multicastGroup") + " is not a multicast address");
                System.exit(0);
            }
            
            socket = new MulticastSocket(port);
            socket.joinGroup(multicastGroup);
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.out.println("Format error in the properties file. Make sure the port number is an integer and ");
            System.out.println("that the multicast address is in the correct format");
        } 
        
    }
    
    /** 
     *  Replay the PDUs that have been logged 
     */
    public void replayExercise()
    {
        try
        {
            File exerciseDirectory = new File(exerciseName);
            if(exerciseDirectory.isDirectory() == false)
            {
                System.out.println ("not a directory");
                System.exit(0);
            }
            
            String logFiles[] = exerciseDirectory.list();
            for(int idx = 0; idx < logFiles.length; idx++)
            {
                System.out.println("Log file being replayed: " + logFiles[idx]);
                
                //  Check to make sure the file fits the pattern of 
                // "exerciseName_nnnn"
                String fileName = logFiles[idx];
                if(!fileName.startsWith(exerciseName + "_"))
                {
                    continue;
                }
                fileName = exerciseName + "/" + fileName;
 
                // TODO read PDU from file and then call replayList
                 
           } // end of loop through lists of XML pdu files
            
            System.out.println("Pdus sent: " + count);
            
        }
        catch(Exception e)
        {
            
        }
    }
    
    private void replayList(List pduList)
    {

        for(int idx = 0; idx < pduList.size(); idx++)
        {
            edu.nps.moves.dis.Pdu aPdu = (edu.nps.moves.dis.Pdu)pduList.get(idx);
            long timestamp = aPdu.getTimestamp();

            try
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                aPdu.marshal(dos);
                byte ieeeData[] = baos.toByteArray();
                DatagramPacket packet = new DatagramPacket(ieeeData, ieeeData.length, multicastGroup, port);
                socket.send(packet);
                count++;
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            
            if(idx + 1 < pduList.size())
            {
                edu.nps.moves.dis.Pdu nextPdu = (edu.nps.moves.dis.Pdu)pduList.get(idx + 1);
                long nextTime = nextPdu.getTimestamp();
                long difference = nextTime - timestamp;
                
                //System.out.println("Difference: " + difference);
                if(difference > MIN_SLEEP_TIME)
                {
                    try
                    {
                        Thread.sleep(difference);
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }

            }
        }
    }
    
    public static void main(String args[])
    {
        // Must always pass at least the property file name
        if(args.length != 1)
        {
            System.out.println("Usage: LogReplay propertyFile");
            System.exit(0);
        }
        
        //System.out.println("props file: " + args[0]);
        
        // preflight the property file passed in to make sure it exists
        try
        {
            Properties loggerProperties = new Properties();
            //System.out.println("Attempting to load properties file " + args[0]);
            
            InputStream propsFile = DisLogger.class.getResourceAsStream(args[0]);
            loggerProperties.load(propsFile);

            LogReplay replay = new LogReplay(loggerProperties);
            replay.replayExercise();
           
        }
        catch(Exception e)
        {
            System.out.println("File not found; check to make sure properties file exists");
            System.out.println(e);
        }
        
        System.out.println("exiting main thread of logger");
       
    } // End main
}
