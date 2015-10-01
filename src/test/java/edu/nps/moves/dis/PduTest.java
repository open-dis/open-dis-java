/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nps.moves.dis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;

import edu.nps.moves.disutil.PduFactory;

/**
 *
 * @author mcgredo
 */
public class PduTest
{

    /**
     * Tests to make sure the PDU length is being placed in the PDU whenever
     * the PDU is marshalled, automatically. This tests to make sure a patch
     * has been correctly applied.
     */
    @Test
    public void pduLengthTest()
    {
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
    public void pduTimestampNpsTest()
    {
        EntityStatePdu espdu = new EntityStatePdu();
        byte[] buffer = espdu.marshalWithNpsTimestamp();
        PduFactory factory = new PduFactory();
        Pdu aPdu = factory.createPdu(buffer);
        assertFalse(aPdu.getTimestamp()== 0);
    }

    /** Make sure the timestamp has NOT been set when marshalled with this method
     *
     */
    /*
    @Test
    public void pduTimestampTest()
    {
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

    }
     
     */

    /**
     * the PDU returns the current length rather than what has been set
     */
    @Test
    



    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

}