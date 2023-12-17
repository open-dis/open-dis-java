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
public class FirePduTest {

    public FirePduTest() {
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
        FirePdu fpdu = new FirePdu();

        byte[] buffer = fpdu.marshal();

        assertEquals(buffer.length, fpdu.getLength());
    }

    @Test
    public void unmarshal_MunitionDescriptor()
            throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(edu.nps.moves.dis7.PduFileLoader.load("FirePdu_MunitionDescriptor.raw"));

        // Header
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(2, pdu.getPduType());
        assertEquals(2, pdu.getProtocolFamily());
        assertEquals(96, pdu.getLength());
        assertEquals(0, pdu.getPduStatus());
        assertEquals(0, pdu.getPadding());

        FirePdu fPdu = (FirePdu) pdu;

        // Firing Entity ID
        assertEquals(1, fPdu.getFiringEntityID().getSiteID());
        assertEquals(2, fPdu.getFiringEntityID().getApplicationID());
        assertEquals(1, fPdu.getFiringEntityID().getEntityID());

        // Target Entity ID
        assertEquals(200, fPdu.getTargetEntityID().getSiteID());
        assertEquals(400, fPdu.getTargetEntityID().getApplicationID());
        assertEquals(1, fPdu.getTargetEntityID().getEntityID());

        // Munition Entity ID
        assertEquals(1, fPdu.getMunitionExpendibleID().getSiteID());
        assertEquals(2, fPdu.getMunitionExpendibleID().getApplicationID());
        assertEquals(2, fPdu.getMunitionExpendibleID().getEntityID());

        // Event ID
        assertEquals(1, fPdu.getEventID().getSimulationAddress().getSite());
        assertEquals(226, fPdu.getEventID().getSimulationAddress().getApplication());
        assertEquals(1, fPdu.getEventID().getEventNumber());

        // Fire Mission Index
        assertEquals(55, fPdu.getFireMissionIndex());

        // Location
        assertEquals(3434174.675, fPdu.getLocationInWorldCoordinates().getX(), 0.001);
        assertEquals(700499.234, fPdu.getLocationInWorldCoordinates().getY(), 0.001);
        assertEquals(5310970.613, fPdu.getLocationInWorldCoordinates().getZ(), 0.001);

        // Descriptor
        MunitionDescriptor munDesc = (MunitionDescriptor) fPdu.getDescriptor();

        assertEquals(2, munDesc.getMunitionType().getEntityKind());
        assertEquals(2, munDesc.getMunitionType().getDomain());
        assertEquals(57, munDesc.getMunitionType().getCountry());
        assertEquals(1, munDesc.getMunitionType().getCategory());
        assertEquals(2, munDesc.getMunitionType().getSubcategory());
        assertEquals(2, munDesc.getMunitionType().getSpecific());
        assertEquals(2, munDesc.getMunitionType().getExtra());

        assertEquals(4000, munDesc.getWarhead());
        assertEquals(7000, munDesc.getFuse());
        assertEquals(55, munDesc.getQuantity());
        assertEquals(54, munDesc.getRate());

        //  Velocity
        assertEquals(4, fPdu.getVelocity().getX(), 0);
        assertEquals(5, fPdu.getVelocity().getY(), 0);
        assertEquals(6, fPdu.getVelocity().getZ(), 0);

        // Range
        assertEquals(55.5, fPdu.getRange(), 0.001);
    }

    @Test
    public void unmarshal_ExpendableDescriptor()
            throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(edu.nps.moves.dis7.PduFileLoader.load("FirePdu_ExpendableDescriptor.raw"));

        // Header
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(2, pdu.getPduType());
        assertEquals(2, pdu.getProtocolFamily());
        assertEquals(96, pdu.getLength());
        assertEquals(16, pdu.getPduStatus());
        assertEquals(0, pdu.getPadding());

        FirePdu fPdu = (FirePdu) pdu;

        // Firing Entity ID
        assertEquals(1, fPdu.getFiringEntityID().getSiteID());
        assertEquals(2, fPdu.getFiringEntityID().getApplicationID());
        assertEquals(1, fPdu.getFiringEntityID().getEntityID());

        // Target Entity ID
        assertEquals(200, fPdu.getTargetEntityID().getSiteID());
        assertEquals(400, fPdu.getTargetEntityID().getApplicationID());
        assertEquals(1, fPdu.getTargetEntityID().getEntityID());

        // Munition Entity ID
        assertEquals(1, fPdu.getMunitionExpendibleID().getSiteID());
        assertEquals(2, fPdu.getMunitionExpendibleID().getApplicationID());
        assertEquals(2, fPdu.getMunitionExpendibleID().getEntityID());

        // Event ID
        assertEquals(1, fPdu.getEventID().getSimulationAddress().getSite());
        assertEquals(226, fPdu.getEventID().getSimulationAddress().getApplication());
        assertEquals(1, fPdu.getEventID().getEventNumber());

        // Fire Mission Index
        assertEquals(55, fPdu.getFireMissionIndex());

        // Location
        assertEquals(3434174.675, fPdu.getLocationInWorldCoordinates().getX(), 0.001);
        assertEquals(700499.234, fPdu.getLocationInWorldCoordinates().getY(), 0.001);
        assertEquals(5310970.613, fPdu.getLocationInWorldCoordinates().getZ(), 0.001);

        // Descriptor
        ExpendableDescriptor expendableDesc = (ExpendableDescriptor) fPdu.getDescriptor();

        assertEquals(8, expendableDesc.getExpendableType().getEntityKind());
        assertEquals(2, expendableDesc.getExpendableType().getDomain());
        assertEquals(57, expendableDesc.getExpendableType().getCountry());
        assertEquals(5, expendableDesc.getExpendableType().getCategory());
        assertEquals(6, expendableDesc.getExpendableType().getSubcategory());
        assertEquals(8, expendableDesc.getExpendableType().getSpecific());
        assertEquals(8, expendableDesc.getExpendableType().getExtra());

        assertEquals(0, expendableDesc.getPadding());

        //  Velocity
        assertEquals(4, fPdu.getVelocity().getX(), 0);
        assertEquals(5, fPdu.getVelocity().getY(), 0);
        assertEquals(6, fPdu.getVelocity().getZ(), 0);

        // Range
        assertEquals(55.5, fPdu.getRange(), 0.001);
    }
}
