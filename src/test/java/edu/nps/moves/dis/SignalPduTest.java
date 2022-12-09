package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class SignalPduTest {

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(PduFileLoader.load("SignalPdu.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        assertEquals(6, aPdu.getProtocolVersion());
        assertEquals(1, aPdu.getExerciseID());
        assertEquals(26, aPdu.getPduType());
        assertEquals(4, aPdu.getProtocolFamily());
        //FIXME is timestamp wrong? assertEquals((long) 0.001000000, aPdu.getTimestamp());
        assertEquals(1056, aPdu.getLength());
        assertEquals(0, aPdu.getPadding());

        SignalPdu spdu = (SignalPdu) aPdu;

        assertEquals(1677, spdu.getEntityId().getSite());
        assertEquals(1678, spdu.getEntityId().getApplication());
        assertEquals(169, spdu.getEntityId().getEntity());
        assertEquals(1, spdu.getRadioId());
        assertEquals(4, spdu.getEncodingScheme());
        assertEquals(0, spdu.getEncodingClass());
        assertEquals(4, spdu.getEncodingType());
        assertEquals(0, spdu.getTdlType());
        assertEquals(22050, spdu.getSampleRate());
        assertEquals(8192, spdu.getDataLength());
        assertEquals(512, spdu.getSamples());
        assertEquals(8192 / Byte.SIZE, spdu.getData().length);
    }

    @Test
    public void marshal() {
        SignalPdu spdu = new SignalPdu();
        final int numChunks = 10;
        byte[] data = new byte[numChunks];

        spdu.setData(data);
        spdu.setDataLength((short) numChunks * Byte.SIZE);

        assertEquals(numChunks * Byte.SIZE, spdu.getDataLength());

        byte[] buffer = spdu.marshal();

        assertEquals(buffer.length, spdu.getLength());
    }
    
       @Test
    public void unmarshalVrF() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(PduFileLoader.load("SignalPDIVrForces.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        assertEquals(6, aPdu.getProtocolVersion());
        assertEquals(1, aPdu.getExerciseID());
        assertEquals(26, aPdu.getPduType());
        assertEquals(4, aPdu.getProtocolFamily());
        //FIXME is timestamp wrong? assertEquals((long) 0.001000000, aPdu.getTimestamp());
        assertEquals(156, aPdu.getLength());
        assertEquals(0, aPdu.getPadding());

        SignalPdu spdu = (SignalPdu) aPdu;

        assertEquals(1, spdu.getEntityId().getSite());
        assertEquals(3001, spdu.getEntityId().getApplication());
        assertEquals(1, spdu.getEntityId().getEntity());
        assertEquals(0, spdu.getRadioId());
        assertEquals(32768, spdu.getEncodingScheme());
        assertEquals(2, spdu.getEncodingClass());
        assertEquals(0, spdu.getEncodingType());
        assertEquals(0, spdu.getTdlType());
        assertEquals(1000000, spdu.getSampleRate());
        assertEquals(984, spdu.getDataLength());
        assertEquals(0, spdu.getSamples());
        assertEquals(984 / Byte.SIZE, spdu.getData().length);
    }
}
