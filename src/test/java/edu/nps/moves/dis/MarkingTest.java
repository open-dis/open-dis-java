/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.nps.moves.dis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcgredo
 */
public class MarkingTest
{
    public void Marking()
    {

    }

    @Test
    public void MarkingToLongTest()
    {
        EntityStatePdu espdu = new EntityStatePdu();
        Marking marking = espdu.getMarking();
        marking.setCharacters("This is a marking that is much, much too loong");
        byte[] buff = marking.getCharacters();
        assertEquals(buff.length, 11);
    }

    @Test
    public void MarkingToShortTest()
    {
        EntityStatePdu espdu = new EntityStatePdu();
        Marking marking = espdu.getMarking();
        marking.setCharacters("short");
        byte[] buff = marking.getCharacters();
        assertEquals(buff.length, 11);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

}