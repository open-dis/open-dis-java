package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    private static final int MARKING_STRING_LENGTH = 31;
    
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
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("AggregateStatePduVrForces.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        // Header
        assertEquals(6, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(33, pdu.getPduType());
        assertEquals(7, pdu.getProtocolFamily());
        assertEquals(468, pdu.getLength());
        assertEquals(0, pdu.getPadding());

        AggregateStatePdu aspdu = (AggregateStatePdu) pdu;

        //Aggregate ID
        assertEquals(1, aspdu.getAggregateID().getSite());
        assertEquals(3001, aspdu.getAggregateID().getApplication());
        assertEquals(21, aspdu.getAggregateID().getEntity());

        // Force ID
        assertEquals(1, aspdu.getForceID());

        //Aggregate State
        assertEquals(2, aspdu.getAggregateState());

        //Aggregate Type
        EntityType type = new EntityType();
        type.setEntityKind((short) 11);
        type.setDomain((short) 2);
        type.setCountry(225);
        type.setCategory((short) 0);
        type.setSubcategory((short) 14);
        type.setSpec((short) 0);
        type.setExtra((short) 0);
        assertEquals(type, aspdu.getAggregateType());

        //Formation
        assertEquals(4, aspdu.getFormation());

        //Aggregate Marking
        assertEquals("FW 1", new String(aspdu.getAggregateMarking().getCharacters()).trim());

        //Dimensions
        Vector3Float dim = new Vector3Float();
        dim.setX((float) 1);
        dim.setY((float) 1);
        dim.setZ((float) 1);
        assertEquals(dim, aspdu.getDimensions());

        //Orientation
        Orientation ori = new Orientation();
        ori.setPsi((float) -2.9476495);
        ori.setTheta((float) -0.5830021);
        ori.setPhi((float) 3.1415927);
        assertEquals(ori, aspdu.getOrientation());

        //Center of Mass
        Vector3Double cen = new Vector3Double();
        cen.setX(3454692.1905806717);
        cen.setY(678542.6887741421);
        cen.setZ(5302970.178170408);
        assertEquals(cen, aspdu.getCenterOfMass());

        //Velocity
        Vector3Float vel = new Vector3Float();
        vel.setX((float) 0);
        vel.setY((float) 0);
        vel.setZ((float) 0);
        assertEquals(vel, aspdu.getVelocity());

        //number of Aggregates/Entities in Aggregate
        assertEquals(0, aspdu.getNumberOfDisAggregates());
        assertEquals(3, aspdu.getNumberOfDisEntities());
        assertEquals(0, aspdu.getNumberOfSilentAggregateTypes());
        assertEquals(0, aspdu.getNumberOfSilentEntityTypes());

        //Entity ID List
        List<EntityID> eidList = new ArrayList<>();
        EntityID id1 = new EntityID();
        id1.setSite(1);
        id1.setApplication(3001);
        id1.setEntity(7);
        eidList.add(id1);
        EntityID id2 = new EntityID();
        id2.setSite(1);
        id2.setApplication(3001);
        id2.setEntity(12);
        eidList.add(id2);
        EntityID id3 = new EntityID();
        id3.setSite(1);
        id3.setApplication(3001);
        id3.setEntity(17);
        eidList.add(id3);
        assertEquals(eidList, aspdu.getEntityIDList());
        
        //Variable Datums
        assertEquals(5, aspdu.getNumberOfVariableDatumRecords());
        List<VariableDatum> listVariableDatum = new ArrayList<>();
        VariableDatum datum0 = new VariableDatum(512, 32);
        byte[] byteDatum0 = {0, 0, 0, 3};
        datum0.setPayload(byteDatum0);
        VariableDatum datum1 = new VariableDatum(33000, 32);
        byte[] byteDatum1 = {0, 0, 0, 0, 0, 0, 0, 32};
        datum1.setPayload(byteDatum1);
        VariableDatum datum2 = new VariableDatum(513, 192);
        byte[] byteDatum2 = {0, 0, 0, 3, 0, 1, 11, -71, 0, 7, 0, 1, 1, -71, 0, 12, 0, 1, 11, -71, 0, 17, 0, 0};
        datum2.setPayload(byteDatum2);
        VariableDatum datum3 = new VariableDatum(514, 1792);
        byte[] byteDatum3 = {0, 1, 11, -71, 0, 21, 11, 2, 0, -31, 0, 14, 0, 0, 3, 0, 13, 1, 0, 6, 0, 0, 1, 0, 0, 0, 1, 0, 4, 2, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 49, 32, 70, 87, 44, 32, 49, 32, 70, 111, 114, 99, 101, 102, 105, 114, 101, 45, 97, 116, 45, 119, 105, 108, 108, 0, 0, 0, 2, 69, -38, -64, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25, 77, 97, 118, 101, 114, 105, 99, 107, 32, 77, 105, 115, 115, 105, 108, 101, 32, 76, 97, 117, 110, 99, 104, 101, 114, 70, -116, -96, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 83, 105, 100, 101, 119, 105, 110, 100, 101, 114, 32, 77, 105, 115, 115, 105, 108, 101, 32, 76, 97, 117, 110, 99, 104, 101, 114, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        datum3.setPayload(byteDatum3);
        VariableDatum datum4 = new VariableDatum(515, 40);
        byte[] byteDatum4 = {76, 105, 110, 101, 0};
        datum4.setPayload(byteDatum4);
        listVariableDatum.add(datum0);
        listVariableDatum.add(datum1);
        listVariableDatum.add(datum2);
        listVariableDatum.add(datum3);
        listVariableDatum.add(datum4);
        List<VariableDatum> pduListVardatum = aspdu.getVariableDatumList();
        Iterator<VariableDatum> iter = pduListVardatum.iterator();
        assertTrue(aspdu.getVariableDatumList().containsAll(pduListVardatum));
    }

    @Test
    public void unmarshalVardatumLength() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("AggregateStatePduJCATS.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        // Header
        assertEquals(6, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(33, pdu.getPduType());
        assertEquals(7, pdu.getProtocolFamily());
        assertEquals(224, pdu.getLength());
        assertEquals(0, pdu.getPadding());

        AggregateStatePdu aspdu = (AggregateStatePdu) pdu;

        //Aggregate ID
        assertEquals(201, aspdu.getAggregateID().getSite());
        assertEquals(2, aspdu.getAggregateID().getApplication());
        assertEquals(535, aspdu.getAggregateID().getEntity());

        // Force ID
        assertEquals(1, aspdu.getForceID());

        //Aggregate State
        assertEquals(2, aspdu.getAggregateState());

        //Aggregate Type
        EntityType type = new EntityType();
        type.setEntityKind((short) 1);
        type.setDomain((short) 1);
        type.setCountry(225);
        type.setCategory((short) 5);
        type.setSubcategory((short) 3);
        type.setSpec((short) 0);
        type.setExtra((short) 0);
        assertEquals(type, aspdu.getAggregateType());

        //Formation
        assertEquals(3, aspdu.getFormation());

        //Aggregate Marking
        assertEquals("535", new String(aspdu.getAggregateMarking().getCharacters()).trim());

        //Dimensions
        Vector3Float dim = new Vector3Float();
        dim.setX((float) 636.396);
        dim.setY((float) 989.95);
        dim.setZ((float) 179.332);
        assertEquals(dim.getX(), aspdu.getDimensions().getX(), 0.1);
        assertEquals(dim.getY(), aspdu.getDimensions().getY(), 0.1);
        assertEquals(dim.getZ(), aspdu.getDimensions().getZ(), 0.1);
        
        //Orientation
        Orientation ori = new Orientation();
        ori.setPsi((float) -2.52703);
        ori.setTheta((float) -1.0777);
        ori.setPhi((float) -2.8237);
        assertEquals(ori.getPsi(), aspdu.getOrientation().getPsi(), 0.1);
        assertEquals(ori.getTheta(), aspdu.getOrientation().getTheta(), 0.1);
        assertEquals(ori.getPhi(), aspdu.getOrientation().getPhi(), 0.1);
        
        //Center of Mass
        Vector3Double cen = new Vector3Double();
        cen.setX(3213067.76697673);
        cen.setY(4709330.86088639);
        cen.setZ(2850658.3344315);
        assertEquals(cen.getX(), aspdu.getCenterOfMass().getX(), 0.1);
        assertEquals(cen.getY(), aspdu.getCenterOfMass().getY(), 0.1);
        assertEquals(cen.getZ(), aspdu.getCenterOfMass().getZ(), 0.1);

        //Velocity
        Vector3Float vel = new Vector3Float();
        vel.setX((float) 0);
        vel.setY((float) 0);
        vel.setZ((float) 0);
        assertEquals(vel.getX(), aspdu.getVelocity().getX(), 0.1);
        assertEquals(vel.getY(), aspdu.getVelocity().getY(), 0.1);
        assertEquals(vel.getZ(), aspdu.getVelocity().getZ(), 0.1);
        //number of Aggregates/Entities in Aggregate
        assertEquals(4, aspdu.getNumberOfDisAggregates());
        assertEquals(0, aspdu.getNumberOfDisEntities());
        assertEquals(0, aspdu.getNumberOfSilentAggregateTypes());
        assertEquals(0, aspdu.getNumberOfSilentEntityTypes());

        //Entity ID List
        List<AggregateID> aggIdList = new ArrayList<>();
        AggregateID id1 = new AggregateID();
        id1.setSite(201);
        id1.setApplication(2);
        id1.setAggregateID(536);
        aggIdList.add(id1);
        AggregateID id2 = new AggregateID();
        id2.setSite(201);
        id2.setApplication(2);
        id2.setAggregateID(552);
        aggIdList.add(id2);
        AggregateID id3 = new AggregateID();
        id3.setSite(201);
        id3.setApplication(2);
        id3.setAggregateID(606);
        aggIdList.add(id3);
        AggregateID id4 = new AggregateID();
        id4.setSite(201);
        id4.setApplication(2);
        id4.setAggregateID(660);
        aggIdList.add(id4);
        for (int i = 0; i < aspdu.getAggregateIDList().size(); i++) {
            assertTrue(aggIdList.get(i).equals(aspdu.getAggregateIDList().get(i)));
        }
        //Variable Datums
        assertEquals(1, aspdu.getNumberOfVariableDatumRecords());
        List<VariableDatum> listVariableDatum = new ArrayList<>();
        VariableDatum datum0 = new VariableDatum(512, 32);
        
        byte[] byteDatum0 = {(byte) 0, (byte) 4, (byte) 0, (byte) 0, (byte) 65, (byte) 187, (byte) 103, (byte) 127, (byte) 67, (byte) 11, (byte) 119, (byte) 188, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 67, (byte) 11, (byte) 119, (byte) 188, (byte) 193, (byte) 187, (byte) 103, (byte) 127, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 195, (byte) 11, (byte) 119, (byte) 188, (byte) 65, (byte) 187, (byte) 103, (byte) 127, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 67, (byte) 127, (byte) 130, (byte) 137, (byte) 195, (byte) 58, (byte) 81, (byte) 156, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};

        datum0.setVariableDatumID(999315);
        datum0.setPayload(byteDatum0);
        datum0.setVariableDatumLength(512);
        listVariableDatum.add(datum0);
        List<VariableDatum> pduListVardatum = aspdu.getVariableDatumList();
       
        assertTrue(aspdu.getVariableDatumList().containsAll(listVariableDatum));
    }

    @Test
    public void marshal() {
        AggregateStatePdu aspdu = new AggregateStatePdu();

        byte[] buffer = aspdu.marshal();

        assertEquals(buffer.length, aspdu.getLength());
    }
    
    @Test
    public void AggregateMarkingToLongTest() {
        AggregateStatePdu aspdu = new AggregateStatePdu();
        AggregateMarking marking = aspdu.getAggregateMarking();
        final String s = new String("This is a marking that is much, much too loong");
        marking.setCharacters(s.getBytes());
        byte[] buff = marking.getCharacters();
        assertEquals(buff.length, MARKING_STRING_LENGTH);
    }

    @Test
    public void AggregateMarkingToShortTest() {
        AggregateStatePdu aspdu = new AggregateStatePdu();
        AggregateMarking marking = aspdu.getAggregateMarking();
        final String s = new String("short");
        marking.setCharacters(s.getBytes());
        byte[] buff = marking.getCharacters();
        assertEquals(buff.length, MARKING_STRING_LENGTH);
    }
}
