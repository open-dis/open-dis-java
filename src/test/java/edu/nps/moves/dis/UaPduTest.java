package edu.nps.moves.dis;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.nps.moves.disutil.PduFactory;

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
        Pdu pdu = factory.createPdu(edu.nps.moves.dis.PduFileLoader.load("UAPduV6.raw"));

        // Header
        assertEquals(6, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(29, pdu.getPduType());
        assertEquals(6, pdu.getProtocolFamily());
        assertEquals(76, pdu.getLength());

        UaPdu uaPdu = (UaPdu) pdu;

        // Emitting Entity ID
        assertEquals(1000, uaPdu.getEmittingEntityID().getSite());
        assertEquals(224, uaPdu.getEmittingEntityID().getApplication());
        assertEquals(413, uaPdu.getEmittingEntityID().getEntity());

        // Event ID
        assertEquals(0, uaPdu.getEventID().getSite());
        assertEquals(0, uaPdu.getEventID().getApplication());
        assertEquals(0, uaPdu.getEventID().getEventNumber());

        // Change Indicator
        assertEquals(0, uaPdu.getStateChangeIndicator());

        // Passive Parameter Index
        assertEquals(0, uaPdu.getPassiveParameterIndex());

        // Shaft
        assertEquals(0, uaPdu.getNumberOfShafts());

        // APA
        assertEquals(0, uaPdu.getNumberOfAPAs());

        // Emitter Systems
        assertEquals(1, uaPdu.getNumberOfUAEmitterSystems());
        assertEquals(11, uaPdu.getEmitterSystems().get(0).getEmitterSystemDataLength());
        assertEquals(1, uaPdu.getEmitterSystems().get(0).getAcousticEmitterSystem().getAcousticID());
        assertEquals(2001, uaPdu.getEmitterSystems().get(0).getAcousticEmitterSystem().getAcousticName());
        assertEquals(1, uaPdu.getEmitterSystems().get(0).getAcousticEmitterSystem().getAcousticFunction());

        // Relative Position
        assertEquals(-0.00572799, uaPdu.getEmitterSystems().get(0).getEmitterLocation().getX(), 0.00001);
        assertEquals(-0.0106909, uaPdu.getEmitterSystems().get(0).getEmitterLocation().getY(), 0.00001);
        assertEquals(-5.99993, uaPdu.getEmitterSystems().get(0).getEmitterLocation().getZ(), 0.00001);

        // Acoustic Beams
        assertEquals(1, uaPdu.getEmitterSystems().get(0).getBeamRecords().size());
        assertEquals(1, uaPdu.getEmitterSystems().get(0).getNumberOfBeams());
        assertEquals(6, uaPdu.getEmitterSystems().get(0).getBeamRecords().get(0).getBeamDataLength());
        assertEquals(0, uaPdu.getEmitterSystems().get(0).getBeamRecords().get(0).getBeamIDNumber());
        assertEquals(1, uaPdu.getEmitterSystems().get(0).getBeamRecords().get(0).getFundamentalDataParameters().getActiveEmissionParameterIndex());
        assertEquals(0, uaPdu.getEmitterSystems().get(0).getBeamRecords().get(0).getFundamentalDataParameters().getScanPattern());
        assertEquals(6.28319, uaPdu.getEmitterSystems().get(0).getBeamRecords().get(0).getFundamentalDataParameters().getAzimuthalBeamwidth(), 0.00001);
        assertEquals(0, uaPdu.getEmitterSystems().get(0).getBeamRecords().get(0).getFundamentalDataParameters().getBeamCenterAzimuth(), 0.00001);
        assertEquals(0, uaPdu.getEmitterSystems().get(0).getBeamRecords().get(0).getFundamentalDataParameters().getBeamCenterDE(), 0.00001);
        assertEquals(0, uaPdu.getEmitterSystems().get(0).getBeamRecords().get(0).getFundamentalDataParameters().getDeBeamwidth(), 0.00001);
    }
}
