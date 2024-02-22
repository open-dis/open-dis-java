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
public class SignalPduTest {

    public SignalPduTest() {
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
        SignalPdu spdu = new SignalPdu();
        byte[] buffer = spdu.marshal();
        assertEquals(buffer.length, spdu.getLength());
    }

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(edu.nps.moves.dis7.PduFileLoader.load("SignalPdu.raw"));
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(26, pdu.getPduType());
        assertEquals(4, pdu.getProtocolFamily());
        assertEquals(40, pdu.getLength());
        assertEquals(47, pdu.getPduStatus());
        assertEquals(0, pdu.getPadding());

        SignalPdu spdu = (SignalPdu) pdu;

        // RadioIdentifier
        assertEquals(1, spdu.getRadioIdentifier().getSiteNumber());
        assertEquals(2, spdu.getRadioIdentifier().getApplicationNumber());
        assertEquals(1, spdu.getRadioIdentifier().getReferenceNumber());
        
        //RadioNumber
        assertEquals(1, spdu.getRadioNumber());

        //EncodingScheme
        assertEquals(16384, spdu.getEncodingScheme());
        assertEquals(1, spdu.getEncodingClass());
        assertEquals(0, spdu.getEncodingType());
        
        //TDL Type
        assertEquals(15, spdu.getTdlType());
        
        //Sample Rate
        assertEquals(55000, spdu.getSampleRate());
        
        //Data Length
        assertEquals(64, spdu.getDataLength());
        
        //Data
        assertEquals('S', spdu.getData()[0]);
        assertEquals('u', spdu.getData()[1]);
        assertEquals('c', spdu.getData()[2]);
        assertEquals('c', spdu.getData()[3]);
        assertEquals('e', spdu.getData()[4]);
        assertEquals('s', spdu.getData()[5]);
        assertEquals('s', spdu.getData()[6]);
        assertEquals('!', spdu.getData()[7]);
    }
}
