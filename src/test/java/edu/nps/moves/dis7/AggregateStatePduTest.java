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
public class AggregateStatePduTest {

    public AggregateStatePduTest() {
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
        AggregateStatePdu dpdu = new AggregateStatePdu();

        byte[] buffer = dpdu.marshal();

        assertEquals(buffer.length, dpdu.getLength());
    }

    @Test
    public void unmarshal()
            throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(edu.nps.moves.dis7.PduFileLoader.load("AggregateStatePdu.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        // Header
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(33, pdu.getPduType());
        assertEquals(7, pdu.getProtocolFamily());
        assertEquals(508, pdu.getLength());
        assertEquals(0, pdu.getPadding());
        assertEquals(12, pdu.getPduStatus());

        AggregateStatePdu espdu = (AggregateStatePdu) pdu;

        //AggregateID
        assertEquals(1, espdu.getAggregateID().getSimulationAddress().getSite());
        assertEquals(1, espdu.getAggregateID().getSimulationAddress().getApplication());
        assertEquals(555, espdu.getAggregateID().getAggregateID());

        //ForceID
        assertEquals(1, espdu.getForceID());

        //AggregateState
        assertEquals(2, espdu.getAggregateState());

        //AggregateType
        assertEquals(1, espdu.getAggregateType().getAggregateKind());
        assertEquals(1, espdu.getAggregateType().getDomain());
        assertEquals(225, espdu.getAggregateType().getCountry());
        assertEquals(3, espdu.getAggregateType().getCategory());
        assertEquals(4, espdu.getAggregateType().getSubcategory());
        assertEquals(0, espdu.getAggregateType().getSpecificInfo());
        assertEquals(0, espdu.getAggregateType().getExtra());

        //formation        
        assertEquals(4, espdu.getFormation());

        //AggregateMarking
        assertEquals(0, espdu.getAggregateMarking().getCharacterSet());
        assertEquals(31, espdu.getAggregateMarking().getCharacters().length);
        String marking = "Demo-Aggregate";
        byte[] markingBytes = marking.getBytes();
        for (int i = 0; i < markingBytes.length; i++) {
            assertEquals(markingBytes[i], espdu.getAggregateMarking().getCharacters()[i]);
        }

        //Dimensions
        assertEquals(1, espdu.getDimensions().getX(), 0.001);
        assertEquals(1, espdu.getDimensions().getY(), 0.001);
        assertEquals(1, espdu.getDimensions().getZ(), 0.001);

        //Orientation
        assertEquals(3.1415927, espdu.getOrientation().getPsi(), 0.001);
        assertEquals(-2.9628239, espdu.getOrientation().getTheta(), 0.001);
        assertEquals(-0.58487105, espdu.getOrientation().getPhi(), 0.001);

        //CenterOfMass
        assertEquals(3432718.14463848, espdu.getCenterOfMass().getX(), 0.001);
        assertEquals(685871.683305762, espdu.getCenterOfMass().getY(), 0.001);
        assertEquals(5314355.50692684, espdu.getCenterOfMass().getZ(), 0.001);

        //Velocity
        assertEquals(0, espdu.getVelocity().getX(), 0.001);
        assertEquals(0, espdu.getVelocity().getY(), 0.001);
        assertEquals(0, espdu.getVelocity().getZ(), 0.001);

        //numerof...
        assertEquals(2, espdu.getNumberOfAggregateIds());
        assertEquals(2, espdu.getNumberOfEntitiyIds());
        assertEquals(2, espdu.getNumberOfSilentAggregateSystems());
        assertEquals(1, espdu.getNumberOfSilentEntitySystems());

        //AggregateId's   
        assertEquals(1, espdu.getAggregateIDList().get(0).getSimulationAddress().getSite());
        assertEquals(202, espdu.getAggregateIDList().get(0).getSimulationAddress().getApplication());
        assertEquals(1, espdu.getAggregateIDList().get(0).getAggregateID());

        assertEquals(1, espdu.getAggregateIDList().get(1).getSimulationAddress().getSite());
        assertEquals(202, espdu.getAggregateIDList().get(1).getSimulationAddress().getApplication());
        assertEquals(3, espdu.getAggregateIDList().get(1).getAggregateID());

        //EntityId's   
        assertEquals(1, espdu.getEntityIDList().get(0).getSiteID());
        assertEquals(202, espdu.getEntityIDList().get(0).getApplicationID());
        assertEquals(1, espdu.getEntityIDList().get(0).getEntityID());

        assertEquals(1, espdu.getEntityIDList().get(1).getSiteID());
        assertEquals(202, espdu.getEntityIDList().get(1).getApplicationID());
        assertEquals(3, espdu.getEntityIDList().get(1).getEntityID());

        //SilentAggregateSystems   
        assertEquals(1, espdu.getSilentAggregateSystemList().get(0).getAggregateType().getAggregateKind());
        assertEquals(1, espdu.getSilentAggregateSystemList().get(0).getAggregateType().getDomain());
        assertEquals(225, espdu.getSilentAggregateSystemList().get(0).getAggregateType().getCountry());
        assertEquals(3, espdu.getSilentAggregateSystemList().get(0).getAggregateType().getCategory());
        assertEquals(4, espdu.getSilentAggregateSystemList().get(0).getAggregateType().getSubcategory());
        assertEquals(0, espdu.getSilentAggregateSystemList().get(0).getAggregateType().getSpecificInfo());
        assertEquals(0, espdu.getSilentAggregateSystemList().get(0).getAggregateType().getExtra());
        assertEquals(1, espdu.getSilentAggregateSystemList().get(0).getNrOfAggregates());

        assertEquals(1, espdu.getSilentAggregateSystemList().get(1).getAggregateType().getAggregateKind());
        assertEquals(1, espdu.getSilentAggregateSystemList().get(1).getAggregateType().getDomain());
        assertEquals(225, espdu.getSilentAggregateSystemList().get(1).getAggregateType().getCountry());
        assertEquals(3, espdu.getSilentAggregateSystemList().get(1).getAggregateType().getCategory());
        assertEquals(4, espdu.getSilentAggregateSystemList().get(1).getAggregateType().getSubcategory());
        assertEquals(0, espdu.getSilentAggregateSystemList().get(1).getAggregateType().getSpecificInfo());
        assertEquals(0, espdu.getSilentAggregateSystemList().get(1).getAggregateType().getExtra());
        assertEquals(1, espdu.getSilentAggregateSystemList().get(1).getNrOfAggregates());

        //Vardatums
        assertEquals(10000, espdu.getVariableDatumList().get(0).getVariableDatumID());
        byte[] byteArray = {0, 0, 0, 0x3};
        for (int i = 0; i < byteArray.length; i++) {
            assertEquals(byteArray[i], espdu.getVariableDatumList().get(0).getVariableData()[i]);
        }

        assertEquals(33000, espdu.getVariableDatumList().get(1).getVariableDatumID());
        byte[] byteArray1 = {0, (byte) 192, 0, 0};
        for (int i = 0; i < byteArray1.length; i++) {
            assertEquals(byteArray1[i], espdu.getVariableDatumList().get(1).getVariableData()[i]);
        }

        assertEquals(10000, espdu.getVariableDatumList().get(2).getVariableDatumID());
        byte[] byteArray2 = {0, 0, 0, 2, 0, 1, 11, (byte) 185, 0, 3, 0, 1, 11, (byte) 185, 0, 9};
        for (int i = 0; i < byteArray2.length; i++) {
            assertEquals(byteArray2[i], espdu.getVariableDatumList().get(2).getVariableData()[i]);
        }

        assertEquals(10000, espdu.getVariableDatumList().get(3).getVariableDatumID());
        byte[] byteArray3 = {0x00, 0x01, 0x0b, (byte) 0xb9, 0x00, 0x0d, 0x0b, 0x02, 0x00, 0x00, 0x00, 0x0e, 0x00, 0x00, 0x03, 0x00, 0x0d, 0x01, 0x00, 0x06,
            0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x04, 0x02, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x31, 0x20, 0x46,
            0x57, 0x2c, 0x20, 0x31, 0x20, 0x46, 0x6f, 0x72, 0x63, 0x65, 0x66, 0x69, 0x72, 0x65, 0x2d, 0x61, 0x74, 0x2d, 0x77, 0x69, 0x6c, 0x6c, 0x00, 0x00, 0x00,
            0x02, (byte) 0x45, (byte) 0xda, (byte) 0xc0, 0x00, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x19, 0x4d, 0x61, 0x76, 0x65, 0x72, 0x69, 0x63, 0x6b, 0x20, 0x4d, 0x69, 0x73, 0x73,
            (byte) 0x69, (byte) 0x6c, (byte) 0x65, 0x20, (byte) 0x4c, (byte) 0x61, (byte) 0x75, (byte) 0x6e, (byte) 0x63, (byte) 0x68, (byte) 0x65, (byte) 0x72, 0x46, (byte) 0x8c, (byte) 0xa0, 0x00, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1b, 0x53, 0x69,
            0x64, 0x65, 0x77, 0x69, 0x6e, 0x64, 0x65, 0x72, 0x20, 0x4d, 0x69, 0x73, 0x73, 0x69, 0x6c, 0x65, 0x20, 0x4c, 0x61, 0x75, 0x6e, 0x63, 0x68, 0x65, 0x72,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        for (int i = 0; i < byteArray3.length; i++) {
            assertEquals(byteArray3[i], espdu.getVariableDatumList().get(3).getVariableData()[i]);
        }

        assertEquals(10000, espdu.getVariableDatumList().get(4).getVariableDatumID());
        byte[] byteArray4 = {76, (byte) 105, (byte) 110, (byte) 101, 0};
        for (int i = 0; i < byteArray4.length; i++) {
            assertEquals(byteArray4[i], espdu.getVariableDatumList().get(4).getVariableData()[i]);
        }

    }
}
