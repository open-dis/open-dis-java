package edu.nps.moves.dis7;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fo
 */
public class UaPduTest {

    public UaPduTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void marshal() {
        UaPdu uapdu = new UaPdu();

        byte[] buffer = uapdu.marshal();

        assertEquals(buffer.length, uapdu.getLength());
    }

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(edu.nps.moves.dis7.PduFileLoader.load("UAPdu.raw"));
        // Header
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(29, pdu.getPduType());
        assertEquals(6, pdu.getProtocolFamily());
        assertEquals(56, pdu.getLength());
        assertEquals(12, pdu.getPduStatus());
        assertEquals(0, pdu.getPadding());

        UaPdu uaPdu = (UaPdu) pdu;

        //EmittingEntityID
        assertEquals(1, uaPdu.getEmittingEntityID().getSiteID());
        assertEquals(260, uaPdu.getEmittingEntityID().getApplicationID());
        assertEquals(55, uaPdu.getEmittingEntityID().getEntityID());
     
        //Apa
        assertEquals(1, uaPdu.getNumberOfAPAs());
        assertEquals(54, uaPdu.getApaData().get(0).getParameterIndex());
        assertEquals(55, uaPdu.getApaData().get(0).getParameterValue());
        
        
        


        //EmitterSystems
        
        
        assertEquals(1, uaPdu.getNumberOfUAEmitterSystems());
        assertEquals(0, uaPdu.getEmitterSystems().get(0).getEmitterSystemDataLength());
        assertEquals(0, uaPdu.getEmitterSystems().get(0).getBeamRecords().size());
        assertEquals(0, uaPdu.getEmitterSystems().get(0).getNumberOfBeams());
        //Relative position
        assertEquals(1, uaPdu.getEmitterSystems().get(0).getEmitterLocation().getX(), 0.001);
        assertEquals(1, uaPdu.getEmitterSystems().get(0).getEmitterLocation().getY(), 0.001);
        assertEquals(1, uaPdu.getEmitterSystems().get(0).getEmitterLocation().getZ(), 0.001);

       //EventId
       assertEquals(0, uaPdu.getEventID().getSimulationAddress().getSite());
       assertEquals(0, uaPdu.getEventID().getSimulationAddress().getApplication());
       assertEquals(0, uaPdu.getEventID().getEventNumber());
    }
}
