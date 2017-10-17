package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransmissionPduTest {

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(PduFileLoader.load("TransmitterPdu.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        assertEquals(6, aPdu.getProtocolVersion());
        assertEquals(1, aPdu.getExerciseID());
        assertEquals(25, aPdu.getPduType());
        assertEquals(4, aPdu.getProtocolFamily());
        //FIXME is timestamp wrong? assertEquals((long) 0, aPdu.getTimestamp());
        assertEquals(104, aPdu.getLength());
        assertEquals(0, aPdu.getPadding());

        TransmitterPdu tpdu = (TransmitterPdu) aPdu;

        assertEquals(1677, tpdu.getEntityId().getSite());
        assertEquals(1678, tpdu.getEntityId().getApplication());
        assertEquals(169, tpdu.getEntityId().getEntity());
        assertEquals(1, tpdu.getRadioId());
        assertEquals(2, tpdu.getTransmitState());
        assertEquals(10000000000l, tpdu.getFrequency());
        assertEquals(20000, tpdu.getTransmitFrequencyBandwidth(), 0);
        assertEquals(35, tpdu.getPower(), 0);
    }

    @Test
    public void marshal() {
        TransmitterPdu tpdu = new TransmitterPdu();

        byte[] buffer = tpdu.marshal();

        assertEquals(buffer.length, tpdu.getLength());
    }

    @Test
    public void unmarshal_HAVEQUICK() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(PduFileLoader.load("TransmitterPdu-HAVEQUICK.raw"));

        TransmitterPdu tpdu = (TransmitterPdu) aPdu;

        assertEquals(1, tpdu.getModulationType().getSpreadSpectrum());
        assertEquals(1, tpdu.getModulationType().getMajor());
        assertEquals(2, tpdu.getModulationType().getDetail());
        assertEquals(2, tpdu.getModulationType().getSystem());

        assertEquals(16, tpdu.getModulationParameterCount());
        assertEquals(8, tpdu.getModulationParametersList().size());
        assertEquals(316, (int)tpdu.getModulationParametersList().get(0));
        assertEquals(301, (int)tpdu.getModulationParametersList().get(1));
    }
}
