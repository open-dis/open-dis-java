package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
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
    public void testMobility()
    {
        EntityStatePdu espdu = new EntityStatePdu();
        int mobility = espdu.getEntityAppearance_mobility();
        assert(mobility == 0);
        
        espdu.setEntityAppearance_mobility(1);
        mobility = espdu.getEntityAppearance_mobility();
        assert(mobility == 1);
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
    public void marshal() {
        EntityStatePdu espdu = new EntityStatePdu();

        byte[] buffer = espdu.marshal();

        assertEquals(buffer.length, espdu.getLength());
    }
}
