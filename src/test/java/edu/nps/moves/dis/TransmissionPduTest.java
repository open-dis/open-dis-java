package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFileLoader;
import edu.nps.moves.disutil.BasicHaveQuickMpRecord;
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

        assertEquals(312500000, tpdu.getFrequency());

        assertEquals(16, tpdu.getModulationParameterCount());
        assertEquals(8, tpdu.getModulationParametersList().size());

        BasicHaveQuickMpRecord hq = new BasicHaveQuickMpRecord();
        hq.unmarshal(tpdu.getModulationParametersList());

        assertEquals(316, hq.getNetIdNetNumber());
        assertEquals(0, hq.getNetIdFrequencyTable());
        assertEquals(0, hq.getNetIdMode());
        assertEquals(301, hq.getMwodIndex());
        assertEquals(1, hq.getTimeOfDay());
    }

    @Test
    public void marshal_HAVEQUICK() {
        TransmitterPdu tpdu = new TransmitterPdu();

        BasicHaveQuickMpRecord hq = new BasicHaveQuickMpRecord();
        hq.setMwodIndex((short) 301);
        hq.setTimeOfDay(1);
        hq.setNetIdNetNumber(316);

        tpdu.setModulationParametersList(hq.marshal());
        tpdu.setModulationParameterCount((short) hq.getMarshalledSize());

        byte[] buffer = tpdu.marshal();

        assertEquals(buffer.length, tpdu.getLength());
    }
}
