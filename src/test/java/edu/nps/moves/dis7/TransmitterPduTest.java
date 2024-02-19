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
public class TransmitterPduTest {

    public TransmitterPduTest() {
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
        TransmitterPdu tpdu = new TransmitterPdu();
        ModulationParametersGeneric mpg = new ModulationParametersGeneric();
        tpdu.setModulationParameters(mpg);
        AntennaPatternGeneric apg = new AntennaPatternGeneric();
        tpdu.setAntennaPattern(apg);
        byte[] buffer = tpdu.marshal();

        assertEquals(buffer.length, tpdu.getLength());
    }

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(edu.nps.moves.dis7.PduFileLoader.load("TransmitterPdu_VariableTransmitterParameters.raw"));
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(25, pdu.getPduType());
        assertEquals(4, pdu.getProtocolFamily());
        assertEquals(208, pdu.getLength());
        assertEquals(0, pdu.getPduStatus());
        assertEquals(0, pdu.getPadding());

        TransmitterPdu tpdu = (TransmitterPdu) pdu;

        // Modulation Type
        assertEquals(2, tpdu.getModulationType().getSpreadSpectrum());
        assertEquals(1, tpdu.getModulationType().getMajorModulation());
        assertEquals(10, tpdu.getModulationType().getDetail());
        assertEquals(1, tpdu.getModulationType().getRadioSystem());

        assertEquals(55555555, tpdu.getFrequency());

        // Modulation Parameters
        assertEquals(8, tpdu.getModulationParameterCount());
        assertEquals(1, (byte)((ModulationParametersGeneric)tpdu.getModulationParameters()).getModulationParametersList().get(0));
        assertEquals(2, (byte)((ModulationParametersGeneric)tpdu.getModulationParameters()).getModulationParametersList().get(1));
        assertEquals(3, (byte)((ModulationParametersGeneric)tpdu.getModulationParameters()).getModulationParametersList().get(2));
        assertEquals(4, (byte)((ModulationParametersGeneric)tpdu.getModulationParameters()).getModulationParametersList().get(3));
        assertEquals(5, (byte)((ModulationParametersGeneric)tpdu.getModulationParameters()).getModulationParametersList().get(4));
        assertEquals(6, (byte)((ModulationParametersGeneric)tpdu.getModulationParameters()).getModulationParametersList().get(5));
        assertEquals(7, (byte)((ModulationParametersGeneric)tpdu.getModulationParameters()).getModulationParametersList().get(6));
        assertEquals(8, (byte)((ModulationParametersGeneric)tpdu.getModulationParameters()).getModulationParametersList().get(7));
        

        
        // Antenna Pattern
        assertEquals(55.5, ((BeamAntennaPattern) tpdu.getAntennaPattern()).getAzimuthBeamwidth(), 0.001);
        final EulerAngles beamDirection = ((BeamAntennaPattern) tpdu.getAntennaPattern()).getBeamDirection();
        assertEquals(3.0, beamDirection.getPhi(), 0.001);
        assertEquals(2.0, beamDirection.getTheta(), 0.001);
        assertEquals(1.0, beamDirection.getPsi(), 0.001);
        assertEquals(54.5, ((BeamAntennaPattern) tpdu.getAntennaPattern()).getElevationBeamwidth(), 0.001);
        assertEquals(53.5, ((BeamAntennaPattern) tpdu.getAntennaPattern()).getEx(), 0.001);
        assertEquals(52.5, ((BeamAntennaPattern) tpdu.getAntennaPattern()).getEz(), 0.001);
        assertEquals(51.5, ((BeamAntennaPattern) tpdu.getAntennaPattern()).getPhase(), 0.001);
        assertEquals(2, ((BeamAntennaPattern) tpdu.getAntennaPattern()).getReferenceSystem());

        
        // Variable transmitter parameters
        assertEquals(3, tpdu.getVariableTransmitterParameterCount());

        assertEquals(2, tpdu.getVariableTransmitterParametersList().get(0).getRecordType());
        assertEquals(16, tpdu.getVariableTransmitterParametersList().get(0).getRecordLength());
        assertEquals(1, (byte) tpdu.getVariableTransmitterParametersList().get(0).getRecordSpecificFieldsList().get(0));
        assertEquals(2, (byte) tpdu.getVariableTransmitterParametersList().get(0).getRecordSpecificFieldsList().get(1));
        assertEquals(3, (byte) tpdu.getVariableTransmitterParametersList().get(0).getRecordSpecificFieldsList().get(2));
        assertEquals(4, (byte) tpdu.getVariableTransmitterParametersList().get(0).getRecordSpecificFieldsList().get(3));
        assertEquals(5, (byte) tpdu.getVariableTransmitterParametersList().get(0).getRecordSpecificFieldsList().get(4));

        assertEquals(3, tpdu.getVariableTransmitterParametersList().get(1).getRecordType());
        assertEquals(24, tpdu.getVariableTransmitterParametersList().get(1).getRecordLength());        
        assertEquals(1, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(0));
        assertEquals(2, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(1));
        assertEquals(3, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(2));
        assertEquals(4, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(3));
        assertEquals(5, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(4));
        assertEquals(6, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(5));
        assertEquals(7, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(6));
        assertEquals(8, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(7));
        assertEquals(9, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(8));
        assertEquals(10, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(9));
        assertEquals(12, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(10));
        assertEquals(13, (byte) tpdu.getVariableTransmitterParametersList().get(1).getRecordSpecificFieldsList().get(11));
        

        assertEquals(2, tpdu.getVariableTransmitterParametersList().get(2).getRecordType());
        assertEquals(16, tpdu.getVariableTransmitterParametersList().get(2).getRecordLength());
        assertEquals(1, (byte) tpdu.getVariableTransmitterParametersList().get(2).getRecordSpecificFieldsList().get(0));
        assertEquals(2, (byte) tpdu.getVariableTransmitterParametersList().get(2).getRecordSpecificFieldsList().get(1));
        assertEquals(3, (byte) tpdu.getVariableTransmitterParametersList().get(2).getRecordSpecificFieldsList().get(2));
        assertEquals(4, (byte) tpdu.getVariableTransmitterParametersList().get(2).getRecordSpecificFieldsList().get(3));
        assertEquals(5, (byte) tpdu.getVariableTransmitterParametersList().get(2).getRecordSpecificFieldsList().get(4));        
    }
}
