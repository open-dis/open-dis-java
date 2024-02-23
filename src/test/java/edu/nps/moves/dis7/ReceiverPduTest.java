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
public class ReceiverPduTest {
    
    public ReceiverPduTest() {
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
        ReceiverPdu rpdu = new ReceiverPdu();
        byte[] buffer = rpdu.marshal();
        assertEquals(buffer.length, rpdu.getLength());
    }
    
    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(edu.nps.moves.dis7.PduFileLoader.load("ReceiverPdu.raw"));
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(27, pdu.getPduType());
        assertEquals(4, pdu.getProtocolFamily());
        assertEquals(36, pdu.getLength());
        assertEquals(47, pdu.getPduStatus());
        assertEquals(0, pdu.getPadding());

        ReceiverPdu rpdu = (ReceiverPdu) pdu;

        // RadioReferenceID
        assertEquals(1, rpdu.getRadioReferenceID().getSiteID());
        assertEquals(210, rpdu.getRadioReferenceID().getApplicationID());
        assertEquals(1, rpdu.getRadioReferenceID().getEntityID());

        // RadioNumber
        assertEquals(1, rpdu.getRadioNumber());

        //ReceiverState
        assertEquals(1, rpdu.getReceiverState());

        //ReceivedPower
        assertEquals(55.0, rpdu.getReceivedPower(), 0.001);

        // TransmitterEntityId
        assertEquals(1, rpdu.getTransmitterEntityId().getSiteID());
        assertEquals(210, rpdu.getTransmitterEntityId().getApplicationID());
        assertEquals(3, rpdu.getTransmitterEntityId().getEntityID());
        
        //TransmitterRadioId
        assertEquals(1, rpdu.getTransmitterRadioId());

    }
}
