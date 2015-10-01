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
     *  Method that runs before every unit test, annoated by a @Test method.
     */
    @Before
    public void setUp() {
        eid = new EntityID();
        eid.setApplication(100);
        eid.setEntity(101);
        eid.setSite(102);

        /**
         *  Ugh. The data for each chunk is a one byte array, rather than just
         * a byte. Why did I do this?
         */
        List<OneByteChunk> signalData = new ArrayList<OneByteChunk>();
        for (int i = 0; i < 100; i++) {
            OneByteChunk chunk = new OneByteChunk();
            byte data[] = new byte[1];
            data[0] = (byte) i;
            chunk.setOtherParameters(data);
            signalData.add(chunk);
        }

        isp = new IntercomSignalPdu();
        isp.setCommunicationsDeviceID(1);
        isp.setData(signalData);
        isp.setEncodingScheme(2);
        isp.setExerciseID((short) 3);
        isp.setRadioId(4);
        isp.setSampleRate(5);
        isp.setSamples(6);
        isp.setTdlType(7);
        isp.setTimestamp(8);

        isp.setEntityID(eid);
    }

    /**
     * Test field retrieval
     */
    @Test
    public void testFields() {
        IntercomSignalPdu blah = isp;

        assertEquals("CommunicationsDeviceID", isp.getCommunicationsDeviceID(), 1);

        List<OneByteChunk> retrievedData = isp.getData();
        assertEquals("Signal data length wrong", isp.getDataLength(), 100);

        // Check that each value in the variable-length array is the same as we set
        for (int i = 0; i < 100; i++) {
            OneByteChunk chunk = retrievedData.get(i);
            byte[] data = chunk.getOtherParameters();
            assertEquals("Signal data mismatch", data[0], i);
        }

        assertEquals("EncodingScheme", isp.getEncodingScheme(), 2);

        assertEquals("Site ID", isp.getEntityID().getSite(), 102);
        assertEquals("Application ID", isp.getEntityID().getApplication(), 100);
        assertEquals("Entity ID", isp.getEntityID().getEntity(), 101);

        assertEquals("ExerciseID((short", isp.getExerciseID(), 3);
        assertEquals("RadioId", isp.getRadioId(), 4);
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

        List<OneByteChunk> oldData = isp.getData();
        List<OneByteChunk> retrievedData = newIsp.getData();
        assertEquals("Signal data length wrong", isp.getDataLength(), newIsp.getDataLength());
        for (int idx = 0; idx < 100; idx++) {
            OneByteChunk oldChunk = oldData.get(idx);
            OneByteChunk newChunk = retrievedData.get(idx);
            assertEquals("Signal data mismatch", oldChunk.getOtherParameters()[0], newChunk.getOtherParameters()[0]);
        }

        assertEquals("EncodingScheme", isp.getEncodingScheme(), newIsp.getEncodingScheme());

        EntityID oldId = isp.getEntityID();
        EntityID newId = newIsp.getEntityID();
        assertTrue(oldId.equals(newId));
        // assertEquals(newId,  oldId); // They're .equals(), yet this fails.  Why?
        assertEquals("EntityID application", oldId.getApplication(), newId.getApplication());
        assertEquals("EntityID entity", oldId.getEntity(), newId.getEntity());
        assertEquals("EntityID site", oldId.getSite(), newId.getSite());

        assertEquals("ExerciseID((short", isp.getExerciseID(), newIsp.getExerciseID());
        assertEquals("RadioId", isp.getRadioId(), newIsp.getRadioId());
        assertEquals("SampleRate", isp.getSampleRate(), newIsp.getSampleRate());
        assertEquals("Samples", isp.getSamples(), newIsp.getSamples());
        assertEquals("TdlType", isp.getTdlType(), newIsp.getTdlType());
        assertEquals("Timestamp", isp.getTimestamp(), newIsp.getTimestamp());
    }
}