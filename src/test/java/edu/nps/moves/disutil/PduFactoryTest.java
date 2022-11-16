package edu.nps.moves.disutil;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

import edu.nps.moves.dis.AcknowledgePdu;
import edu.nps.moves.dis.AcknowledgeReliablePdu;
import edu.nps.moves.dis.ActionRequestPdu;
import edu.nps.moves.dis.ActionRequestReliablePdu;
import edu.nps.moves.dis.ActionResponsePdu;
import edu.nps.moves.dis.ActionResponseReliablePdu;
import edu.nps.moves.dis.AggregateStatePdu;
import edu.nps.moves.dis.ArealObjectStatePdu;
import edu.nps.moves.dis.CollisionElasticPdu;
import edu.nps.moves.dis.CollisionPdu;
import edu.nps.moves.dis.CommentPdu;
import edu.nps.moves.dis.CommentReliablePdu;
import edu.nps.moves.dis.CreateEntityPdu;
import edu.nps.moves.dis.CreateEntityReliablePdu;
import edu.nps.moves.dis.DataPdu;
import edu.nps.moves.dis.DataQueryPdu;
import edu.nps.moves.dis.DataQueryReliablePdu;
import edu.nps.moves.dis.DataReliablePdu;
import edu.nps.moves.dis.DesignatorPdu;
import edu.nps.moves.dis.DetonationPdu;
import edu.nps.moves.dis.ElectronicEmissionsPdu;
import edu.nps.moves.dis.EntityStatePdu;
import edu.nps.moves.dis.EntityStateUpdatePdu;
import edu.nps.moves.dis.EnvironmentalProcessPdu;
import edu.nps.moves.dis.EventReportPdu;
import edu.nps.moves.dis.EventReportReliablePdu;
import edu.nps.moves.dis.FastEntityStatePdu;
import edu.nps.moves.dis.FirePdu;
import edu.nps.moves.dis.GriddedDataPdu;
import edu.nps.moves.dis.IntercomControlPdu;
import edu.nps.moves.dis.IntercomSignalPdu;
import edu.nps.moves.dis.IsGroupOfPdu;
import edu.nps.moves.dis.IsPartOfPdu;
import edu.nps.moves.dis.LinearObjectStatePdu;
import edu.nps.moves.dis.MinefieldDataPdu;
import edu.nps.moves.dis.MinefieldQueryPdu;
import edu.nps.moves.dis.MinefieldResponseNackPdu;
import edu.nps.moves.dis.MinefieldStatePdu;
import edu.nps.moves.dis.Pdu;
import edu.nps.moves.dis.PointObjectStatePdu;
import edu.nps.moves.dis.ReceiverPdu;
import edu.nps.moves.dis.RecordQueryReliablePdu;
import edu.nps.moves.dis.RemoveEntityPdu;
import edu.nps.moves.dis.RemoveEntityReliablePdu;
import edu.nps.moves.dis.RepairCompletePdu;
import edu.nps.moves.dis.RepairResponsePdu;
import edu.nps.moves.dis.ResupplyCancelPdu;
import edu.nps.moves.dis.ResupplyOfferPdu;
import edu.nps.moves.dis.ResupplyReceivedPdu;
import edu.nps.moves.dis.SeesPdu;
import edu.nps.moves.dis.ServiceRequestPdu;
import edu.nps.moves.dis.SetDataPdu;
import edu.nps.moves.dis.SetDataReliablePdu;
import edu.nps.moves.dis.SetRecordReliablePdu;
import edu.nps.moves.dis.SignalPdu;
import edu.nps.moves.dis.StartResumePdu;
import edu.nps.moves.dis.StartResumeReliablePdu;
import edu.nps.moves.dis.StopFreezePdu;
import edu.nps.moves.dis.StopFreezeReliablePdu;
import edu.nps.moves.dis.TransferControlRequestPdu;
import edu.nps.moves.dis.TransmitterPdu;
import java.io.IOException;

public class PduFactoryTest {

    @Test
    public void testCreatePduByteArray() {
        PduFactory pduFactory = new PduFactory();

        /*
         * Create a particular object, marshal() it to a generic byte array,
         * use pduFactory.createPdu() to recreate the object, and make sure it is of the correct type.
         */
        
        // TODO: OTHER
        assertTrue(pduFactory.createPdu(new EntityStatePdu().marshal()) instanceof EntityStatePdu);
        assertTrue(pduFactory.createPdu(new FirePdu().marshal()) instanceof FirePdu);
        assertTrue(pduFactory.createPdu(new DetonationPdu().marshal()) instanceof DetonationPdu);
        assertTrue(pduFactory.createPdu(new CollisionPdu().marshal()) instanceof CollisionPdu);
        assertTrue(pduFactory.createPdu(new ServiceRequestPdu().marshal()) instanceof ServiceRequestPdu);
        assertTrue(pduFactory.createPdu(new ResupplyOfferPdu().marshal()) instanceof ResupplyOfferPdu);
        assertTrue(pduFactory.createPdu(new ResupplyReceivedPdu().marshal()) instanceof ResupplyReceivedPdu);
        assertTrue(pduFactory.createPdu(new ResupplyCancelPdu().marshal()) instanceof ResupplyCancelPdu);
        assertTrue(pduFactory.createPdu(new RepairCompletePdu().marshal()) instanceof RepairCompletePdu);
        assertTrue(pduFactory.createPdu(new RepairResponsePdu().marshal()) instanceof RepairResponsePdu);
        assertTrue(pduFactory.createPdu(new CreateEntityPdu().marshal()) instanceof CreateEntityPdu);
        assertTrue(pduFactory.createPdu(new RemoveEntityPdu().marshal()) instanceof RemoveEntityPdu);
        assertTrue(pduFactory.createPdu(new StartResumePdu().marshal()) instanceof StartResumePdu);
        assertTrue(pduFactory.createPdu(new StopFreezePdu().marshal()) instanceof StopFreezePdu);
        assertTrue(pduFactory.createPdu(new AcknowledgePdu().marshal()) instanceof AcknowledgePdu);
        assertTrue(pduFactory.createPdu(new ActionRequestPdu().marshal()) instanceof ActionRequestPdu);
        assertTrue(pduFactory.createPdu(new ActionResponsePdu().marshal()) instanceof ActionResponsePdu);
        assertTrue(pduFactory.createPdu(new DataQueryPdu().marshal()) instanceof DataQueryPdu);
        assertTrue(pduFactory.createPdu(new SetDataPdu().marshal()) instanceof SetDataPdu);
        assertTrue(pduFactory.createPdu(new DataPdu().marshal()) instanceof DataPdu);
        assertTrue(pduFactory.createPdu(new EventReportPdu().marshal()) instanceof EventReportPdu);
        assertTrue(pduFactory.createPdu(new CommentPdu().marshal()) instanceof CommentPdu);
        assertTrue(pduFactory.createPdu(new ElectronicEmissionsPdu().marshal()) instanceof ElectronicEmissionsPdu);
        assertTrue(pduFactory.createPdu(new DesignatorPdu().marshal()) instanceof DesignatorPdu);
        assertTrue(pduFactory.createPdu(new TransmitterPdu().marshal()) instanceof TransmitterPdu);
        assertTrue(pduFactory.createPdu(new SignalPdu().marshal()) instanceof SignalPdu);
        assertTrue(pduFactory.createPdu(new ReceiverPdu().marshal()) instanceof ReceiverPdu);
        // TODO: IFF_ATC_NAVAIDS
        // TODO: UNDERWATER_ACOUSTIC
        assertTrue(pduFactory.createPdu(new SeesPdu().marshal()) instanceof SeesPdu);
        assertTrue(pduFactory.createPdu(new IntercomSignalPdu().marshal()) instanceof IntercomSignalPdu);
        assertTrue(pduFactory.createPdu(new IntercomControlPdu().marshal()) instanceof IntercomControlPdu);
        assertTrue(pduFactory.createPdu(new AggregateStatePdu().marshal()) instanceof AggregateStatePdu);
        assertTrue(pduFactory.createPdu(new IsGroupOfPdu().marshal()) instanceof IsGroupOfPdu);
        assertTrue(pduFactory.createPdu(new TransferControlRequestPdu().marshal()) instanceof TransferControlRequestPdu);
        assertTrue(pduFactory.createPdu(new IsPartOfPdu().marshal()) instanceof IsPartOfPdu);
        assertTrue(pduFactory.createPdu(new MinefieldStatePdu().marshal()) instanceof MinefieldStatePdu);
        assertTrue(pduFactory.createPdu(new MinefieldQueryPdu().marshal()) instanceof MinefieldQueryPdu);
        assertTrue(pduFactory.createPdu(new MinefieldDataPdu().marshal()) instanceof MinefieldDataPdu);
        assertTrue(pduFactory.createPdu(new MinefieldResponseNackPdu().marshal()) instanceof MinefieldResponseNackPdu);
        assertTrue(pduFactory.createPdu(new EnvironmentalProcessPdu().marshal()) instanceof EnvironmentalProcessPdu);
        assertTrue(pduFactory.createPdu(new GriddedDataPdu().marshal()) instanceof GriddedDataPdu);
        assertTrue(pduFactory.createPdu(new PointObjectStatePdu().marshal()) instanceof PointObjectStatePdu);
        assertTrue(pduFactory.createPdu(new LinearObjectStatePdu().marshal()) instanceof LinearObjectStatePdu);
        assertTrue(pduFactory.createPdu(new ArealObjectStatePdu().marshal()) instanceof ArealObjectStatePdu);
        // TODO: TSPI
        // TODO: APPEARANCE
        // TODO: ARTICULATED_PARTS
        // TODO: LE_FIRE
        // TODO: LE_DETONATION
        assertTrue(pduFactory.createPdu(new CreateEntityReliablePdu().marshal()) instanceof CreateEntityReliablePdu);
        assertTrue(pduFactory.createPdu(new RemoveEntityReliablePdu().marshal()) instanceof RemoveEntityReliablePdu);
        assertTrue(pduFactory.createPdu(new StartResumeReliablePdu().marshal()) instanceof StartResumeReliablePdu);
        assertTrue(pduFactory.createPdu(new StopFreezeReliablePdu().marshal()) instanceof StopFreezeReliablePdu);
        assertTrue(pduFactory.createPdu(new AcknowledgeReliablePdu().marshal()) instanceof AcknowledgeReliablePdu);
        assertTrue(pduFactory.createPdu(new ActionRequestReliablePdu().marshal()) instanceof ActionRequestReliablePdu);
        assertTrue(pduFactory.createPdu(new ActionResponseReliablePdu().marshal()) instanceof ActionResponseReliablePdu);
        assertTrue(pduFactory.createPdu(new DataQueryReliablePdu().marshal()) instanceof DataQueryReliablePdu);
        assertTrue(pduFactory.createPdu(new SetDataReliablePdu().marshal()) instanceof SetDataReliablePdu);
        assertTrue(pduFactory.createPdu(new DataReliablePdu().marshal()) instanceof DataReliablePdu);
        assertTrue(pduFactory.createPdu(new EventReportReliablePdu().marshal()) instanceof EventReportReliablePdu);
        assertTrue(pduFactory.createPdu(new CommentReliablePdu().marshal()) instanceof CommentReliablePdu);
        // TODO: RECORD_R
        assertTrue(pduFactory.createPdu(new SetRecordReliablePdu().marshal()) instanceof SetRecordReliablePdu);
        assertTrue(pduFactory.createPdu(new RecordQueryReliablePdu().marshal()) instanceof RecordQueryReliablePdu);
        assertTrue(pduFactory.createPdu(new CollisionElasticPdu().marshal()) instanceof CollisionElasticPdu);
        assertTrue(pduFactory.createPdu(new EntityStateUpdatePdu().marshal()) instanceof EntityStateUpdatePdu);
    }

    @Test
    public void testCreatePduByteBuffer() {
        PduFactory pduFactory = new PduFactory();
        assertTrue(pduFactory.createPdu(ByteBuffer.wrap(new EntityStatePdu().marshal())) instanceof EntityStatePdu);
        // etc.  previous test gives coverage
    }

    @Test
    public void testUseFastPdu() {
        PduFactory pduFactory = new PduFactory(false);
        // No matter which you start with, createPdu returns EntityStatePdu
        assertTrue(pduFactory.createPdu(new EntityStatePdu().marshal()) instanceof EntityStatePdu);
        assertTrue(pduFactory.createPdu(new FastEntityStatePdu().marshal()) instanceof EntityStatePdu);
        
        pduFactory = new PduFactory(true);
        // No matter which you start with, createPdu returns FastEntityStatePdu
        assertTrue(pduFactory.createPdu(new EntityStatePdu().marshal()) instanceof FastEntityStatePdu);
        assertTrue(pduFactory.createPdu(new FastEntityStatePdu().marshal()) instanceof FastEntityStatePdu);
    }
    
    @Test
    public void testUnsupportedPduType() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu p = factory.createPdu(PduFileLoader.load("OrgStateV2Pdu-Type137-JCATS.bin")); // // Pdu type 137 sent by JCATS 14.  
        assertEquals(p.getPduType(), 137); // Org State V2
        assertEquals(p.getProtocolFamily(), 130); // Experimental family.
        assertEquals(p.getProtocolVersion(), 6);
        assertEquals(p.getPduLength(), 280);
        ExperimentalPdu e = (ExperimentalPdu)p;
        assertEquals(e.getBody().length, 268);
    }
}
