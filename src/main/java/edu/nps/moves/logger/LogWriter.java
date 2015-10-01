package edu.nps.moves.logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import javax.xml.bind.*;

import edu.nps.moves.dis.*;

/**
 * Writes the contents of the PDU buffer to the file in a separate thread.
 * 
 * This accepts Lists of PDUs from the reader thread and writes them out in
 * XML format. Since the reader thread may be faster at times than the writer,
 * we can queue up PDU lists and write them out as we get the chance.
 *
 * @author mcgredo
 * @version $Id:$
 */
public class LogWriter implements Runnable {

    String exerciseName;
    boolean writing = false;
    boolean unqueuedPdus = false;
    BlockingQueue<List<Pdu>> listQueue = new LinkedBlockingQueue<List<Pdu>>();

    /** Creates a new instance of LogWriter. Exercise name is used to
     * create a directory we write log files to
     * @param pExerciseName
     */
    public LogWriter(String pExerciseName) {
        exerciseName = pExerciseName;
    }

    /** 
     * Irritating interaction with the reder thread. They may have read a
     * few PDUs, but don't have enough for a "full" list to send to us.
     * this keeps the writer thread alive until it sends the data to us.
     * @param state
     */
    public void setUnqueuedPdus(boolean state) {
        unqueuedPdus = state;
    }

    /**
     *  Add a list of PDUs to our write queue
     * @param pduList
     */
    public void addListToWriteQueue(List<Pdu> pduList) {
        try {
            listQueue.put(pduList);
        //System.out.println("enqued pdu list of size " + pduList.size());
        } catch (Exception e) {
            System.out.println("unable to add list to write queue" + e);
        }
    }

    /**
     * Create a directory to hold the log files
     */
    private void createLogDirectory() {
        try {
            // Create the directory the log files will be in; this is the exercise name.
            // It may be that this directory has already been created.
            File logDirectory = new File(exerciseName);
            boolean createdDirectory = logDirectory.mkdir();
            if (createdDirectory) {
                System.out.println("created log directory " + exerciseName);
            } else {
                //System.out.println("could not create log directory; assuming is is already there " + exerciseName);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Start writing PDUs. This blocks if it has no PDU lists to write.
     */
    public void run() {
        this.createLogDirectory();

        long startTime = System.currentTimeMillis();
        int count = 0;

        while (true) {
            try {
                // Blocks until there's something in the queue to take
                List<Pdu> pdusToLog = listQueue.take();

                // Flip on a boolean so we don't quit out of the thread while
                // still writing PDUs to disk
                writing = true;

                // Create a log file name, or the format "exerciseName_nnnnn"
                long time = System.currentTimeMillis();
                String fileName = exerciseName + "/" + exerciseName + "_" + time + ".xml";

                PduContainer container = new PduContainer();
                container.setPdus(pdusToLog);

                // Marshall the list out to a file. The long list of class names is
                // essentially all the classes in the dis package; this is the safest
                // option for jaxb. You can copy & paste the list from PduContainer.java
                // in the dis package.

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
                        edu.nps.moves.dis.SupplyQuantity.class);

                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(container, new FileOutputStream(fileName));

                writing = false;

            } // End try
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     *  Test to see whether we're done writing and it's OK to quit the thread
     */
    public boolean finishedWriting() {
        return ((listQueue.isEmpty()) && (!writing) && (!unqueuedPdus));
    }
}
