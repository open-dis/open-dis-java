package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author mcgredo
 */
public class EntityStatePduTest {

    public EntityStatePduTest() {
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
    public void testMobility() {
        EntityStatePdu espdu = new EntityStatePdu();
        int mobility = espdu.getEntityAppearance_mobility();
        assert (mobility == 0);

        espdu.setEntityAppearance_mobility(1);
        mobility = espdu.getEntityAppearance_mobility();
        assert (mobility == 1);
    }

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("EntityStatePdu-26.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        // Header
        assertEquals(6, pdu.getProtocolVersion());
        assertEquals(7, pdu.getExerciseID());
        assertEquals(1, pdu.getPduType());
        assertEquals(1, pdu.getProtocolFamily());
        //TODO assertEquals((long) ???, aPdu.getTimestamp());
        assertEquals(144, pdu.getLength());
        assertEquals(0, pdu.getPadding());

        EntityStatePdu espdu = (EntityStatePdu) pdu;

        // Entity ID
        assertEquals(42, espdu.getEntityID().getSite());
        assertEquals(4, espdu.getEntityID().getApplication());
        assertEquals(26, espdu.getEntityID().getEntity());

        // Force ID
        assertEquals(1, espdu.getForceId());

        // Articulation Parameters
        assertEquals(0, espdu.getArticulationParameters().size());

        // Entity Type (aka DIS Enumeration)
        assertEquals(1, espdu.getEntityType().getEntityKind());
        assertEquals(1, espdu.getEntityType().getDomain());
        assertEquals(39, espdu.getEntityType().getCountry());
        assertEquals(7, espdu.getEntityType().getCategory());
        assertEquals(2, espdu.getEntityType().getSubcategory());
        assertEquals(1, espdu.getEntityType().getSpec());
        assertEquals(0, espdu.getEntityType().getExtra());

        // Alternative Entity Type
        assertEquals(1, espdu.getAlternativeEntityType().getEntityKind());
        assertEquals(1, espdu.getAlternativeEntityType().getDomain());
        assertEquals(39, espdu.getAlternativeEntityType().getCountry());
        assertEquals(7, espdu.getAlternativeEntityType().getCategory());
        assertEquals(2, espdu.getAlternativeEntityType().getSubcategory());
        assertEquals(1, espdu.getAlternativeEntityType().getSpec());
        assertEquals(0, espdu.getAlternativeEntityType().getExtra());

        // Entity Linear Velocity
        assertEquals(0, espdu.getEntityLinearVelocity().getX(), 0);
        assertEquals(0, espdu.getEntityLinearVelocity().getY(), 0);
        assertEquals(0, espdu.getEntityLinearVelocity().getZ(), 0);

        // Entity Location
        assertEquals(4374082.80485589, espdu.getEntityLocation().getX(), 0.001);
        assertEquals(1667679.95730107, espdu.getEntityLocation().getY(), 0.001);
        assertEquals(4318284.36890269, espdu.getEntityLocation().getZ(), 0.001);

        // Entity Orientation
        assertEquals(1.93505, espdu.getEntityOrientation().getPsi(), 0.001);
        assertEquals(0, espdu.getEntityOrientation().getTheta(), 0.001);
        assertEquals(-2.31924, espdu.getEntityOrientation().getPhi(), 0.001);

        // Entity Appearance
        assertEquals(0, espdu.getEntityAppearance_paintScheme());
        assertEquals(0, espdu.getEntityAppearance_mobility());
        assertEquals(0, espdu.getEntityAppearance_firepower());

        // Dead Reckoning Parameters
        // TODO assertEquals(???, espdu.getDeadReckoningParameters());
        // Entity Marking
        assertEquals("26", new String(espdu.getMarking().getCharacters()).trim());

        // Capabilities
        assertEquals(0, espdu.getCapabilities());
    }

    @Test
    public void unmarshal_articulated_params() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("EntityStatePdu-vbs-articulated-parameters.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        EntityStatePdu espdu = (EntityStatePdu) pdu;

        assertEquals(16, espdu.getArticulationParameters().size());

        // We are only checking 2 out of 16 parameters below, ones I cared about:
        // Primary Turret Azimuth and Primary Gun Elevation
        ArticulationParameter ap1 = espdu.getArticulationParameters().get(1);

        assertEquals(0, ap1.getParameterTypeDesignator());
        assertEquals(0, ap1.getChangeIndicator());
        assertEquals(0, ap1.getPartAttachedTo()); // 0 means attached directly to entity
        assertEquals(4107, ap1.getParameterType()); // raw
        assertEquals(4096, ap1.getArticulatedPartIndex()); // Primary turret #1
        assertEquals(11, ap1.getParameterTypeMetric()); // 11 is Azimuth
        assertEquals(128, ap1.getParameterTypeClass()); // 128 is model-specific station ID
        assertEquals(-1.541110, ap1.getParameterValueFirstSubfield(), 0.000001); // Radians

        ArticulationParameter ap7 = espdu.getArticulationParameters().get(7);

        assertEquals(0, ap7.getParameterTypeDesignator());
        assertEquals(0, ap7.getChangeIndicator());
        assertEquals(1, ap7.getPartAttachedTo()); // 1 means attached to parameter 1 (aka Primary turret above)
        assertEquals(4429, ap7.getParameterType()); // raw
        assertEquals(4416, ap7.getArticulatedPartIndex()); // Primary gun #1
        assertEquals(13, ap7.getParameterTypeMetric()); // 13 is Elevation
        assertEquals(138, ap7.getParameterTypeClass()); // 138 is model-specific station ID
        assertEquals(0.078117, ap7.getParameterValueFirstSubfield(), 0.000001); // Radians
    }

    @Test
    public void unmarshal_articulated_parameters() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("EntityStatePdu-vrf-articulated-parameters.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        EntityStatePdu espdu = (EntityStatePdu) pdu;

        assertEquals(8, espdu.getArticulationParameters().size());

        // We are only checking 2 out of 16 parameters below, ones I cared about:
        // Primary Turret Azimuth and Primary Gun Elevation
        ArticulationParameter ap0 = espdu.getArticulationParameters().get(0);

        assertEquals(0, ap0.getParameterTypeDesignator());
        assertEquals(98, ap0.getChangeIndicator());
        assertEquals(0, ap0.getPartAttachedTo()); // 0 means attached directly to entity
        assertEquals(4107, ap0.getParameterType()); // raw
        assertEquals(4096, ap0.getArticulatedPartIndex()); // Primary turret #1
        assertEquals(11, ap0.getParameterTypeMetric()); // 11 is Azimuth
        assertEquals(128, ap0.getParameterTypeClass()); // 128 is model-specific station ID
        assertEquals(-0.785398, ap0.getParameterValue(), 0.000001); // Radians

        ArticulationParameter ap1 = espdu.getArticulationParameters().get(4);

        assertEquals(0, ap1.getParameterTypeDesignator());
        assertEquals(0, ap1.getChangeIndicator());
        assertEquals(1, ap1.getPartAttachedTo()); // 1 means attached to parameter 1 (aka Primary turret above)
        assertEquals(4429, ap1.getParameterType()); // raw
        assertEquals(4416, ap1.getArticulatedPartIndex()); // Primary gun #1
        assertEquals(13, ap1.getParameterTypeMetric()); // 13 is Elevation
        assertEquals(138, ap1.getParameterTypeClass()); // 138 is model-specific station ID
        assertEquals(0, ap1.getParameterValue(), 0.000001); // Radians
    }

    @Test
    public void marshal() {
        EntityStatePdu espdu = new EntityStatePdu();

        byte[] buffer = espdu.marshal();

        assertEquals(buffer.length, espdu.getLength());
    }

    /**
     * Tests to make sure the PDU length is being placed in the PDU whenever the
     * PDU is marshalled, automatically. This tests to make sure a patch has
     * been correctly applied.
     */
    @Test
    public void pduLengthTest() {
        EntityStatePdu espdu = new EntityStatePdu();
        byte[] buffer = espdu.marshal();
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(buffer);
        assertEquals(aPdu.getLength(), 144);
    }

    /**
     * Make sure the timestamp has been set in marshalled PDUs
     */
    @Test
    public void pduTimestampNpsTest() {
        EntityStatePdu espdu = new EntityStatePdu();
        byte[] buffer = espdu.marshalWithNpsTimestamp();
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(buffer);
        assertFalse(aPdu.getTimestamp() == 0);
    }

    /**
     * Make sure the timestamp has NOT been set when marshalled with this method
     *
     */
    @Test
    @Ignore
    public void pduTimestampTest() {
        /*
        EntityStatePdu espdu = new EntityStatePdu();

        espdu.setTimestamp(0xffffffffL);
        assertEquals(espdu.getTimestamp(), 0xffffffffL);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        espdu.marshal(dos);
        byte[] buffer = baos.toByteArray();

        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(buffer);
        assertEquals(aPdu.getTimestamp(),  0xFFFFFFFFL);
    
        buffer = aPdu.marshal();
        aPdu = factory.createPdu(buffer);
        assertEquals(aPdu.getTimestamp(), (long)0xffffffffL); // make sure no rollover
         */
    }

    @Test
    public void MarkingToLongTest() {
        EntityStatePdu espdu = new EntityStatePdu();
        Marking marking = espdu.getMarking();
        final String s = new String("This is a marking that exceeds the maximum length and will be truncated.");
        marking.setCharacters(s.getBytes());
        byte[] buff = marking.getCharacters();
        assertEquals(buff.length, Marking.MARKING_STRING_LENGTH);
    }

    @Test
    public void MarkingToShortTest() {
        EntityStatePdu espdu = new EntityStatePdu();
        Marking marking = espdu.getMarking();
        final String s = new String("short");
        marking.setCharacters(s.getBytes());
        byte[] buff = marking.getCharacters();
        assertEquals(buff.length, Marking.MARKING_STRING_LENGTH);
    }
}
