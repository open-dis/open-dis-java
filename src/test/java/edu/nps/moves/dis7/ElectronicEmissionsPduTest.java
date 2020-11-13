package edu.nps.moves.dis7;


import edu.nps.moves.dis.PduFileLoader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * @author lcowan
 */
public class ElectronicEmissionsPduTest
{

    public ElectronicEmissionsPduTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }


    @Test
    public void unmarshal()
        throws IOException
    {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("ElectromagneticEmissionPdu.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.

        // Header
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(51, pdu.getExerciseID());
        assertEquals(23, pdu.getPduType());
        assertEquals(6, pdu.getProtocolFamily());
        assertEquals(100, pdu.getMarshalledSize());
        assertEquals(0, pdu.getPadding());

        ElectronicEmissionsPdu espdu = (ElectronicEmissionsPdu) pdu;

        // Entity ID
        assertEquals(162, espdu.getEmittingEntityID().getSiteID());
        assertEquals(2, espdu.getEmittingEntityID().getApplicationID());
        assertEquals(0, espdu.getEmittingEntityID().getEntityID());

        // Event ID
        assertEquals(0, espdu.getEventID().getEventNumber());

        // Emission System
        assertEquals(18, espdu.getSystemDataLength());
        assertEquals(1, espdu.getNumberOfBeams());

        // Emitter System
        assertEquals(15660, espdu.getEmitterSystem().getEmitterName());
        assertEquals(7, espdu.getEmitterSystem().getEmitterFunction());
        assertEquals(1, espdu.getEmitterSystem().getEmitterIDNumber());

        // Location
        assertEquals(0.0, espdu.getLocation().getX(), 0.0001);
        assertEquals(0.0, espdu.getLocation().getY(), 0.0001);
        assertEquals(0.0, espdu.getLocation().getZ(), 0.0001);

        // Beam
        assertEquals(13, espdu.getBeam().getBeamDataLength());
        assertEquals(1, espdu.getBeam().getBeamNumber());
        assertEquals(1, espdu.getBeam().getBeamParameterIndex());
        assertEquals(14000000000.0, espdu.getBeam().getFundamentalParameterData().getFrequency(), 0.000001);
        assertEquals(6000000000.0, espdu.getBeam().getFundamentalParameterData().getFrequencyRange(), 0.000001);
        assertEquals(55.0, espdu.getBeam().getFundamentalParameterData().getEffectiveRadiatedPower(), 0.000001);
        assertEquals(600.0, espdu.getBeam().getFundamentalParameterData().getPulseRepetitionFrequency(), 0.000001);
        assertEquals(3.0, espdu.getBeam().getFundamentalParameterData().getPulseWidth(), 0.000001);
        assertEquals(0, espdu.getBeam().getBeamData().getBeamAzimuthCenter(), 0.000001);
        assertEquals(0.698132, espdu.getBeam().getBeamData().getBeamAzimuthSweep(), 0.000001);
        assertEquals(0.436332, espdu.getBeam().getBeamData().getBeamElevationCenter(), 0.000001);
        assertEquals(0.436332, espdu.getBeam().getBeamData().getBeamElevationSweep(), 0.000001);
        assertEquals(90.4001, espdu.getBeam().getBeamData().getBeamSweepSync(), 0.000001);
        assertEquals(7, espdu.getBeam().getBeamFunction());
        assertEquals(0, espdu.getBeam().getNumberOfTargets());
        assertEquals(0, espdu.getBeam().getHighDensityTrackJam());
        assertEquals(0, espdu.getBeam().getBeamStatus().getBeamState());
        assertEquals(0, espdu.getBeam().getJammingTechnique().getCategory());
        assertEquals(0, espdu.getBeam().getJammingTechnique().getKind());
        assertEquals(0, espdu.getBeam().getJammingTechnique().getSpecific());
        assertEquals(0, espdu.getBeam().getJammingTechnique().getCategory());


    }

    @Test
    public void marshal()
    {
        EntityStatePdu espdu = new EntityStatePdu();

        byte[] buffer = espdu.marshal();

        assertEquals(buffer.length, espdu.getMarshalledSize());
    }
}
