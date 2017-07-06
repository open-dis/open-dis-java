package edu.nps.moves.dis;

import java.io.*;
import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: dunstan
 * Date: Mar 11, 2008
 * Time: 3:48:25 PM
 *
 * Unit tests for IntercomSignalPdu.
 * @version $Id:$
 */
public class IntercomSignalPduUnitTest {

    private EntityID eid;
    private IntercomSignalPdu isp;

    /**
     *  Method that runs before every unit test, annotated by a @Test method.
     */
    @Before
    public void setUp() {
        eid = new EntityID();
        eid.setApplication(100);
        eid.setEntity(101);
        eid.setSite(102);

        byte[] signalData = new byte[100];

        isp = new IntercomSignalPdu();
        isp.setCommunicationsDeviceID(1);
        isp.setData(signalData);
        isp.setEncodingScheme(2);
        isp.setExerciseID((short) 3);
        //isp.setRadioId(4);
        isp.setSampleRate(5);
        isp.setSamples(6);
        isp.setTdlType(7);
        isp.setTimestamp(8);

        isp.setEntityId(eid);
    }

    /**
     * Test field retrieval
     */
    @Test
    public void testFields() {
        IntercomSignalPdu blah = isp;

        assertEquals("CommunicationsDeviceID", isp.getCommunicationsDeviceID(), 1);

        byte[] retrievedData = isp.getData();
        assertEquals("Signal data length wrong", isp.getDataLength(), 100);

        // Check that each value in the variable-length array is the same as we set
        assertArrayEquals("Signal data mismatch", retrievedData, new byte[100]);

        assertEquals("EncodingScheme", isp.getEncodingScheme(), 2);

        assertEquals("Site ID", isp.getEntityId().getSite(), 102);
        assertEquals("Application ID", isp.getEntityId().getApplication(), 100);
        assertEquals("Entity ID", isp.getEntityId().getEntity(), 101);

        assertEquals("ExerciseID((short", isp.getExerciseID(), 3);
        //assertEquals("RadioId", isp.getRadioId(), 4);
        assertEquals("SampleRate", isp.getSampleRate(), 5);
        assertEquals("Samples", isp.getSamples(), 6);
        assertEquals("TdlType", isp.getTdlType(), 7);
        assertEquals("Timestamp", isp.getTimestamp(), 8);
    }

    /**
     * Test marshalling and unmarshalling
     */
    @Test
    public void testMarshalUnmarshal() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        isp.marshal(dos);

        IntercomSignalPdu newIsp = new IntercomSignalPdu();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DataInputStream dis = new DataInputStream(bais);
        newIsp.unmarshal(dis);

        assertEquals("CommunicationsDeviceID", isp.getCommunicationsDeviceID(), newIsp.getCommunicationsDeviceID());

        byte[] oldData = isp.getData();
        byte[] retrievedData = newIsp.getData();
        assertEquals("Signal data length wrong", isp.getDataLength(), newIsp.getDataLength());
        assertArrayEquals("Signal data mismatch", oldData, retrievedData);

        assertEquals("EncodingScheme", isp.getEncodingScheme(), newIsp.getEncodingScheme());

        EntityID oldId = isp.getEntityId();
        EntityID newId = newIsp.getEntityId();
        assertTrue(oldId.equals(newId));
        // assertEquals(newId,  oldId); // They're .equals(), yet this fails.  Why?
        assertEquals("EntityID application", oldId.getApplication(), newId.getApplication());
        assertEquals("EntityID entity", oldId.getEntity(), newId.getEntity());
        assertEquals("EntityID site", oldId.getSite(), newId.getSite());

        assertEquals("ExerciseID((short", isp.getExerciseID(), newIsp.getExerciseID());
        //assertEquals("RadioId", isp.getRadioId(), newIsp.getRadioId());
        assertEquals("SampleRate", isp.getSampleRate(), newIsp.getSampleRate());
        assertEquals("Samples", isp.getSamples(), newIsp.getSamples());
        assertEquals("TdlType", isp.getTdlType(), newIsp.getTdlType());
        assertEquals("Timestamp", isp.getTimestamp(), newIsp.getTimestamp());
    }
}