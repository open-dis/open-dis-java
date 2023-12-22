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
public class DetonationPduTest {

    public DetonationPduTest() {
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
        DetonationPdu dpdu = new DetonationPdu();

        byte[] buffer = dpdu.marshal();

        assertEquals(buffer.length, dpdu.getLength());
    }

    @Test
    public void unmarshal()
            throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(edu.nps.moves.dis7.PduFileLoader.load("DetonationPdu_Descriptors_VariableParameters.raw"));
        // Header
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(3, pdu.getPduType());
        assertEquals(2, pdu.getProtocolFamily());
        assertEquals(200, pdu.getLength());
        assertEquals(32, pdu.getPduStatus());
        assertEquals(0, pdu.getPadding());

        DetonationPdu fPdu = (DetonationPdu) pdu;

        // Firing Entity ID
        assertEquals(1, fPdu.getFiringEntityID().getSiteID());
        assertEquals(2, fPdu.getFiringEntityID().getApplicationID());
        assertEquals(1, fPdu.getFiringEntityID().getEntityID());

        // Target Entity ID
        assertEquals(1, fPdu.getTargetEntityID().getSiteID());
        assertEquals(2, fPdu.getTargetEntityID().getApplicationID());
        assertEquals(21, fPdu.getTargetEntityID().getEntityID());

        // Munition Entity ID
        assertEquals(1, fPdu.getExplodingEntityID().getSiteID());
        assertEquals(2, fPdu.getExplodingEntityID().getApplicationID());
        assertEquals(2, fPdu.getExplodingEntityID().getEntityID());

        // Event ID
        assertEquals(1, fPdu.getEventID().getSimulationAddress().getSite());
        assertEquals(224, fPdu.getEventID().getSimulationAddress().getApplication());
        assertEquals(1, fPdu.getEventID().getEventNumber());

        //  Velocity
        assertEquals(4, fPdu.getVelocity().getX(), 0);
        assertEquals(5, fPdu.getVelocity().getY(), 0);
        assertEquals(6, fPdu.getVelocity().getZ(), 0);

        // Location - World Coordinates
        assertEquals(3434174.675, fPdu.getLocationInWorldCoordinates().getX(), 0.001);
        assertEquals(700499.234, fPdu.getLocationInWorldCoordinates().getY(), 0.001);
        assertEquals(5310970.613, fPdu.getLocationInWorldCoordinates().getZ(), 0.001);

        // Descriptor
        ExplosionDescriptor expDesc = (ExplosionDescriptor) fPdu.getDescriptor();

        assertEquals(2, expDesc.getExplodingObject().getEntityKind());
        assertEquals(2, expDesc.getExplodingObject().getDomain());
        assertEquals(57, expDesc.getExplodingObject().getCountry());
        assertEquals(9, expDesc.getExplodingObject().getCategory());
        assertEquals(10, expDesc.getExplodingObject().getSubcategory());
        assertEquals(11, expDesc.getExplodingObject().getSpecific());
        assertEquals(12, expDesc.getExplodingObject().getExtra());

        assertEquals(55.0f, expDesc.getExplosiveForce(), 0.001);
        assertEquals(602, expDesc.getExplosiveMaterial());

        // Location - Entity Coordinates
        assertEquals(2, fPdu.getLocationOfEntityCoordinates().getX(), 0.001);
        assertEquals(3, fPdu.getLocationOfEntityCoordinates().getY(), 0.001);
        assertEquals(1, fPdu.getLocationOfEntityCoordinates().getZ(), 0.001);

        //Detonation Result
        assertEquals(1, fPdu.getDetonationResult());

        //Variable Parameters
        assertEquals(6, fPdu.getNumberOfVariableParameters());

        final VariableParameter varParam0 = fPdu.getVariableParameters().get(0);
        assertEquals(0, varParam0.getRecordType());
        assertEquals(0, ((ArticulatedParts) varParam0).getChangeIndicator());
        assertEquals(0, ((ArticulatedParts) varParam0).getPartAttachedTo());
        assertEquals(4107, ((ArticulatedParts) varParam0).getParameterType());
        assertEquals(-1.78262, ((ArticulatedParts) varParam0).getParameterValue(), 0.001);

        final VariableParameter varParam1 = fPdu.getVariableParameters().get(1);
        assertEquals(0, varParam1.getRecordType());
        assertEquals(0, ((ArticulatedParts) varParam1).getChangeIndicator());
        assertEquals(1, ((ArticulatedParts) varParam1).getPartAttachedTo());
        assertEquals(4429, ((ArticulatedParts) varParam1).getParameterType());
        assertEquals(1.28147, ((ArticulatedParts) varParam1).getParameterValue(), 0.001);

        final VariableParameter varParam2 = fPdu.getVariableParameters().get(2);
        assertEquals(1, varParam2.getRecordType());
        assertEquals(0, ((AttachedParts) varParam2).getDetachedIndicator());
        assertEquals(0, ((AttachedParts) varParam2).getPartAttachedTo());
        assertEquals(898, ((AttachedParts) varParam2).getParameterType());
        EntityType partType = ((AttachedParts) varParam2).getAttachedPartType();
        assertEquals(2, partType.getEntityKind());
        assertEquals(2, partType.getDomain());
        assertEquals(225, partType.getCountry());
        assertEquals(2, partType.getCategory());
        assertEquals(2, partType.getSubcategory());
        assertEquals(3, partType.getSpecific());
        assertEquals(0, partType.getExtra());

        final VariableParameter varParam3 = fPdu.getVariableParameters().get(3);
        assertEquals(2, varParam3.getRecordType());
        final EntityID parentEntityID = ((SeparationVP) varParam3).getParentEntityID();
        assertEquals(55, parentEntityID.getSiteID());
        assertEquals(55, parentEntityID.getApplicationID());
        assertEquals(55, parentEntityID.getEntityID());
        assertEquals(3, ((SeparationVP) varParam3).getPreEntityIndicator());
        assertEquals(2, ((SeparationVP) varParam3).getReasonForSeparation());
        assertEquals(11, ((SeparationVP) varParam3).getStationLocation().getStationName());
        assertEquals(1, ((SeparationVP) varParam3).getStationLocation().getStationNumber());

        final VariableParameter varParam4 = fPdu.getVariableParameters().get(4);
        assertEquals(3, varParam4.getRecordType());
        assertEquals(12, ((EntityTypeVP) varParam4).getChangeIndicator());
        EntityType entTypeVp = ((EntityTypeVP) varParam4).getEntityType();
        assertEquals(1, entTypeVp.getEntityKind());
        assertEquals(2, entTypeVp.getDomain());
        assertEquals(3, entTypeVp.getCountry());
        assertEquals(4, entTypeVp.getCategory());
        assertEquals(5, entTypeVp.getSubcategory());
        assertEquals(6, entTypeVp.getSpecific());
        assertEquals(7, entTypeVp.getExtra());

        final VariableParameter varParam5 = fPdu.getVariableParameters().get(5);
        assertEquals(4, varParam5.getRecordType());
        assertEquals(1, ((EntityAssociation) varParam5).getChangeIndicator());
        assertEquals(0, ((EntityAssociation) varParam5).getAssociationStatus());
        assertEquals(5, ((EntityAssociation) varParam5).getAssociationType());
        final EntityID objectID = ((EntityAssociation) varParam5).getEntityID();
        assertEquals(56, objectID.getSiteID());
        assertEquals(56, objectID.getApplicationID());
        assertEquals(56, objectID.getEntityID());
        assertEquals(12, ((EntityAssociation) varParam5).getPhysicalConnectionType());
        assertEquals(5, ((EntityAssociation) varParam5).getGroupMemberType());
        assertEquals(17, ((EntityAssociation) varParam5).getGroupNumber());
    }
}
