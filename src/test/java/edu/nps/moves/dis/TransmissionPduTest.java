package edu.nps.moves.dis;

import edu.nps.moves.disutil.BasicHaveQuickMpRecord;
import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import java.util.List;
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

    @Test
    public void unmarshal_OmniDirectional_Antenna_Pattern() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(PduFileLoader.load("TransmitterPdu_AntennaPattern_OmniDirectional.raw"));

        TransmitterPdu tpdu = (TransmitterPdu) aPdu;
        commonAntennaPatternTransmitterPduTest(tpdu);

        assertEquals(0, tpdu.getAntennaPatternType());
        assertEquals(0, tpdu.getSphericalHarmonicAntennaPatternList().size());

    }

    @Test
    public void unmarshal_Beam_Antenna_Pattern() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(PduFileLoader.load("TransmitterPdu_AntennaPattern_Beam.raw"));

        TransmitterPdu tpdu = (TransmitterPdu) aPdu;
        commonAntennaPatternTransmitterPduTest(tpdu);

        assertEquals(1, tpdu.getAntennaPatternType());
        assertEquals(0, tpdu.getSphericalHarmonicAntennaPatternList().size());
        BeamAntennaPattern beamAntennaPattern = tpdu.getAntennaPatternList().get(0);

        assertEquals(55.5f, beamAntennaPattern.getAzimuthBeamwidth(), 0.0f);
        Orientation ori = new Orientation();
        ori.setPsi(1);
        ori.setTheta(2);
        ori.setPhi(3);

        Orientation beamDirection = beamAntennaPattern.getBeamDirection();
        assertEquals(ori.getPsi(), beamDirection.getPsi(), 0.0f);
        assertEquals(ori.getTheta(), beamDirection.getTheta(), 0.0f);
        assertEquals(ori.getPhi(), beamDirection.getPhi(), 0.0f);

        assertEquals(54.5f, beamAntennaPattern.getElevationBeamwidth(), 0.0f);
        assertEquals(53.5f, beamAntennaPattern.getEx(), 0.0f);
        assertEquals(52.5f, beamAntennaPattern.getEz(), 0.0f);
        assertEquals(51.5f, beamAntennaPattern.getPhase(), 0.0f);
        assertEquals(2, beamAntennaPattern.getReferenceSystem());
    }

    @Test
    public void unmarshal_Spherical_Antenna_Pattern() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(PduFileLoader.load("TransmitterPdu_AntennaPattern_Spherical.raw"));

        TransmitterPdu tpdu = (TransmitterPdu) aPdu;
        commonAntennaPatternTransmitterPduTest(tpdu);

        assertEquals(2, tpdu.getAntennaPatternType());
        SphericalHarmonicAntennaPattern spherical = tpdu.getSphericalHarmonicAntennaPatternList().get(0);

        assertEquals(1, spherical.getHarmonicOrder());
        assertEquals(1, spherical.getReferenceSystem());

        byte[] data = spherical.getCoefficientsList().get(0).getOtherParameters();
        float coefficient = translateToFloat(data);
        assertEquals(55.0f, coefficient, 0.0f);
        data = spherical.getCoefficientsList().get(1).getOtherParameters();
        coefficient = translateToFloat(data);
        assertEquals(55.1f, coefficient, 0.0f);
        data = spherical.getCoefficientsList().get(2).getOtherParameters();
        coefficient = translateToFloat(data);
        assertEquals(55.2f, coefficient, 0.0f);
        data = spherical.getCoefficientsList().get(3).getOtherParameters();
        coefficient = translateToFloat(data);
        assertEquals(55.3f, coefficient, 0.0f);

    }

    private void commonAntennaPatternTransmitterPduTest(TransmitterPdu tpdu) {
        assertEquals(7, tpdu.getRadioEntityType().getEntityKind());
        assertEquals(1, tpdu.getRadioEntityType().getDomain());
        assertEquals(225, tpdu.getRadioEntityType().getCountry());
        assertEquals(1, tpdu.getRadioEntityType().getCategory());
        assertEquals(0, tpdu.getRadioEntityType().getNomenclature());
        assertEquals(0, tpdu.getRadioEntityType().getNomenclatureVersion());

        assertEquals(2, tpdu.getModulationType().getSpreadSpectrum());
        assertEquals(1, tpdu.getModulationType().getMajor());
        assertEquals(10, tpdu.getModulationType().getDetail());
        assertEquals(3, tpdu.getModulationType().getSystem());

        assertEquals(55555555, tpdu.getFrequency());

        assertEquals(8, tpdu.getModulationParameterCount());
        assertEquals(4, tpdu.getModulationParametersList().size());

        List<Short> modParamenterList = tpdu.getModulationParametersList();
        assertEquals(258, (short) modParamenterList.toArray()[0]);
        assertEquals(772, (short) modParamenterList.toArray()[1]);
        assertEquals(1286, (short) modParamenterList.toArray()[2]);
        assertEquals(1800, (short) modParamenterList.toArray()[3]);

    }

    private float translateToFloat(byte[] data) {
        int i = (int) ((0xff & data[0]) << 24
                | (0xff & data[1]) << 16
                | (0xff & data[2]) << 8
                | (0xff & data[3]) << 0);
        float f = Float.intBitsToFloat(i);
        return f;
    }
}
