
package edu.nps.moves.logger;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.bind.*;

import edu.nps.moves.dis.*;
import edu.nps.moves.disutil.*;

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
                
                        JAXBContext context = JAXBContext.newInstance(
                edu.nps.moves.dis.MinefieldStatePdu.class,
                edu.nps.moves.dis.AcknowledgeReliablePdu.class,
                edu.nps.moves.dis.SyntheticEnvironmentFamilyPdu.class,
                edu.nps.moves.dis.DesignatorPdu.class,
                edu.nps.moves.dis.AcousticBeamData.class,
                edu.nps.moves.dis.AcousticEmitterSystemData.class,
                edu.nps.moves.dis.FundamentalParameterDataIff.class,
                edu.nps.moves.dis.Relationship.class,
                edu.nps.moves.dis.EntityManagementFamilyPdu.class,
                edu.nps.moves.dis.FastEntityStatePdu.class,
                edu.nps.moves.dis.DataReliablePdu.class,
                edu.nps.moves.dis.BurstDescriptor.class,
                edu.nps.moves.dis.EntityInformationFamilyPdu.class,
                edu.nps.moves.dis.IffAtcNavAidsLayer2Pdu.class,
                edu.nps.moves.dis.NamedLocation.class,
                edu.nps.moves.dis.TransferControlRequestPdu.class,
                edu.nps.moves.dis.EightByteChunk.class,
                edu.nps.moves.dis.ElectronicEmissionsPdu.class,
                edu.nps.moves.dis.CreateEntityReliablePdu.class,
                edu.nps.moves.dis.LinearSegmentParameter.class,
                edu.nps.moves.dis.EventReportPdu.class,
                edu.nps.moves.dis.MinefieldResponseNackPdu.class,
                edu.nps.moves.dis.FourByteChunk.class,
                edu.nps.moves.dis.EntityStateUpdatePdu.class,
                edu.nps.moves.dis.EnvironmentalProcessPdu.class,
                edu.nps.moves.dis.GridAxisRecord.class,
                edu.nps.moves.dis.IntercomCommunicationsParameters.class,
                edu.nps.moves.dis.LinearObjectStatePdu.class,
                edu.nps.moves.dis.EventReportReliablePdu.class,
                edu.nps.moves.dis.ArealObjectStatePdu.class,
                edu.nps.moves.dis.AcousticBeamFundamentalParameter.class,
                edu.nps.moves.dis.Vector3Float.class,
                edu.nps.moves.dis.StopFreezePdu.class,
                edu.nps.moves.dis.ArticulationParameter.class,
                edu.nps.moves.dis.FixedDatum.class,
                edu.nps.moves.dis.CommentReliablePdu.class,
                edu.nps.moves.dis.ServiceRequestPdu.class,
                edu.nps.moves.dis.IsGroupOfPdu.class,
                edu.nps.moves.dis.UaPdu.class,
                edu.nps.moves.dis.PointObjectStatePdu.class,
                edu.nps.moves.dis.FundamentalParameterData.class,
                edu.nps.moves.dis.DataQueryReliablePdu.class,
                edu.nps.moves.dis.SetRecordReliablePdu.class,
                edu.nps.moves.dis.ElectronicEmissionBeamData.class,
                edu.nps.moves.dis.DetonationPdu.class,
                edu.nps.moves.dis.RecordSet.class,
                edu.nps.moves.dis.ActionResponsePdu.class,
                edu.nps.moves.dis.ShaftRPMs.class,
                edu.nps.moves.dis.ActionRequestPdu.class,
                edu.nps.moves.dis.IsPartOfPdu.class,
                edu.nps.moves.dis.DeadReckoningParameter.class,
                edu.nps.moves.dis.GridAxisRecordRepresentation2.class,
                edu.nps.moves.dis.MinefieldQueryPdu.class,
                edu.nps.moves.dis.SystemID.class,
                edu.nps.moves.dis.MinefieldDataPdu.class,
                edu.nps.moves.dis.SimulationAddress.class,
                edu.nps.moves.dis.EntityID.class,
                edu.nps.moves.dis.AntennaLocation.class,
                edu.nps.moves.dis.DataQueryPdu.class,
                edu.nps.moves.dis.EntityType.class,
                edu.nps.moves.dis.IffAtcNavAidsLayer1Pdu.class,
                edu.nps.moves.dis.GridAxisRecordRepresentation1.class,
                edu.nps.moves.dis.BeamAntennaPattern.class,
                edu.nps.moves.dis.ResupplyReceivedPdu.class,
                edu.nps.moves.dis.Orientation.class,
                edu.nps.moves.dis.Pdu.class,
                edu.nps.moves.dis.ReceiverPdu.class,
                edu.nps.moves.dis.AggregateType.class,
                edu.nps.moves.dis.Environment.class,
                edu.nps.moves.dis.WarfareFamilyPdu.class,
                edu.nps.moves.dis.StopFreezeReliablePdu.class,
                edu.nps.moves.dis.SetDataReliablePdu.class,
                edu.nps.moves.dis.CommentPdu.class,
                edu.nps.moves.dis.SimulationManagementWithReliabilityFamilyPdu.class,
                edu.nps.moves.dis.CollisionPdu.class,
                edu.nps.moves.dis.IffFundamentalData.class,
                edu.nps.moves.dis.VariableDatum.class,
                edu.nps.moves.dis.FirePdu.class,
                edu.nps.moves.dis.SetDataPdu.class,
                edu.nps.moves.dis.AcousticEmitterSystem.class,
                edu.nps.moves.dis.RemoveEntityReliablePdu.class,
                edu.nps.moves.dis.RepairCompletePdu.class,
                edu.nps.moves.dis.CreateEntityPdu.class,
                edu.nps.moves.dis.IntercomControlPdu.class,
                edu.nps.moves.dis.ModulationType.class,
                edu.nps.moves.dis.SixByteChunk.class,
                edu.nps.moves.dis.IntercomSignalPdu.class,
                edu.nps.moves.dis.AggregateID.class,
                edu.nps.moves.dis.StartResumePdu.class,
                edu.nps.moves.dis.ActionResponseReliablePdu.class,
                edu.nps.moves.dis.SimulationManagementFamilyPdu.class,
                edu.nps.moves.dis.SphericalHarmonicAntennaPattern.class,
                edu.nps.moves.dis.ElectronicEmissionSystemData.class,
                edu.nps.moves.dis.LayerHeader.class,
                edu.nps.moves.dis.Point.class,
                edu.nps.moves.dis.RecordQueryReliablePdu.class,
                edu.nps.moves.dis.ResupplyOfferPdu.class,
                edu.nps.moves.dis.ClockTime.class,
                edu.nps.moves.dis.SignalPdu.class,
                edu.nps.moves.dis.RadioEntityType.class,
                edu.nps.moves.dis.AggregateStatePdu.class,
                edu.nps.moves.dis.StartResumeReliablePdu.class,
                edu.nps.moves.dis.DistributedEmissionsFamilyPdu.class,
                edu.nps.moves.dis.TrackJamTarget.class,
                edu.nps.moves.dis.AngularVelocityVector.class,
                edu.nps.moves.dis.RepairResponsePdu.class,
                edu.nps.moves.dis.Vector3Double.class,
                edu.nps.moves.dis.AcknowledgePdu.class,
                edu.nps.moves.dis.EntityStatePdu.class,
                edu.nps.moves.dis.RemoveEntityPdu.class,
                edu.nps.moves.dis.PduContainer.class,
                edu.nps.moves.dis.GriddedDataPdu.class,
                edu.nps.moves.dis.VectoringNozzleSystemData.class,
                edu.nps.moves.dis.DataPdu.class,
                edu.nps.moves.dis.ActionRequestReliablePdu.class,
                edu.nps.moves.dis.EmitterSystem.class,
                edu.nps.moves.dis.GridAxisRecordRepresentation0.class,
                edu.nps.moves.dis.SeesPdu.class,
                edu.nps.moves.dis.MinefieldFamilyPdu.class,
                edu.nps.moves.dis.ObjectType.class,
                edu.nps.moves.dis.ApaData.class,
                edu.nps.moves.dis.BeamData.class,
                edu.nps.moves.dis.PropulsionSystemData.class,
                edu.nps.moves.dis.OneByteChunk.class,
                edu.nps.moves.dis.AcousticEmitter.class,
                edu.nps.moves.dis.TransmitterPdu.class,
                edu.nps.moves.dis.EventID.class,
                edu.nps.moves.dis.Marking.class,
                edu.nps.moves.dis.AggregateMarking.class,
                edu.nps.moves.dis.CollisionElasticPdu.class,
                edu.nps.moves.dis.TwoByteChunk.class,
                edu.nps.moves.dis.ResupplyCancelPdu.class,
                edu.nps.moves.dis.RadioCommunicationsFamilyPdu.class,
                edu.nps.moves.dis.LogisticsFamilyPdu.class,
                edu.nps.moves.dis.SupplyQuantity.class    );

                Unmarshaller unmarshaller = context.createUnmarshaller();
                PduContainer unmarshalledObject = (PduContainer)unmarshaller.unmarshal(new FileInputStream(fileName));
                System.out.println("got " + unmarshalledObject.getNumberOfPdus() + " back");

                 this.replayList(unmarshalledObject.getPdus());
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
