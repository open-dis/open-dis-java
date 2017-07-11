/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nps.moves.disenum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.nps.moves.disenum.*;
/**
 *
 * @author DMcG
 */
public class PduTypeTest
{

    public PduTypeTest()
    {
    }

    /**
     * Make sure the enumerations we test have the expected values and
     * settings.
     */
    @Test
    public void testPduEnum()
    {
        PduType espdu = PduType.ENTITY_STATE;
        assertEquals(espdu.getDescription(), "Entity State");
        assertEquals(espdu.getValue(), 1);

    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

}