package edu.nps.moves.dis7;


import edu.nps.moves.disutil.PduFileLoader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * @author mcgredo
 */
public class EntityStatePduTest
{

    public EntityStatePduTest()
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
        Pdu pdu = factory.createPdu(PduFileLoader.load("EntityStatePdu-26.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.

        // Header
        assertEquals(6, pdu.getProtocolVersion());
        assertEquals(7, pdu.getExerciseID());
        assertEquals(1, pdu.getPduType());
        assertEquals(1, pdu.getProtocolFamily());
        assertEquals(144, pdu.getLength());
        assertEquals(0, pdu.getPadding());

        EntityStatePdu espdu = (EntityStatePdu) pdu;

        // Entity ID
        assertEquals(42, espdu.getEntityID().getSiteID());
        assertEquals(4, espdu.getEntityID().getApplicationID());
        assertEquals(26, espdu.getEntityID().getEntityID());

        // Force ID
        assertEquals(1, espdu.getForceId());

        // Entity Type (aka DIS Enumeration)
        assertEquals(1, espdu.getEntityType().getEntityKind());
        assertEquals(1, espdu.getEntityType().getDomain());
        assertEquals(39, espdu.getEntityType().getCountry());
        assertEquals(7, espdu.getEntityType().getCategory());
        assertEquals(2, espdu.getEntityType().getSubcategory());
        assertEquals(1, espdu.getEntityType().getSpecific());
        assertEquals(0, espdu.getEntityType().getExtra());

        // Alternative Entity Type
        assertEquals(1, espdu.getAlternativeEntityType().getEntityKind());
        assertEquals(1, espdu.getAlternativeEntityType().getDomain());
        assertEquals(39, espdu.getAlternativeEntityType().getCountry());
        assertEquals(7, espdu.getAlternativeEntityType().getCategory());
        assertEquals(2, espdu.getAlternativeEntityType().getSubcategory());
        assertEquals(1, espdu.getAlternativeEntityType().getSpecific());
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

        // Dead Reckoning Parameters
        // TODO assertEquals(???, espdu.getDeadReckoningParameters());
        // Entity Marking
        assertEquals("26", new String(espdu.getMarking().getCharacters()).trim());

        // Capabilities
        assertEquals(0, espdu.getCapabilities());
    }

    @Test
    public void marshal()
    {
        EntityStatePdu espdu = new EntityStatePdu();

        byte[] buffer = espdu.marshal();

        assertEquals(buffer.length, espdu.getLength());
    }
}
