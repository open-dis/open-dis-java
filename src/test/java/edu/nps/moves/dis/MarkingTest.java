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
    private static final int MARKING_STRING_LENGTH = 11;
    
    public void Marking()
    {

    }

    @Test
    public void MarkingToLongTest()
    {
        EntityStatePdu espdu = new EntityStatePdu();
        Marking marking = espdu.getMarking();
        final String s = new String("This is a marking that is much, much too loong");
        marking.setCharacters(s.getBytes());
        byte[] buff = marking.getCharacters();
        assertEquals(buff.length, MARKING_STRING_LENGTH);
    }

    @Test
    public void MarkingToShortTest()
    {
        EntityStatePdu espdu = new EntityStatePdu();
        Marking marking = espdu.getMarking();
        final String s = new String("short");
        marking.setCharacters(s.getBytes());
        byte[] buff = marking.getCharacters();
        assertEquals(buff.length, MARKING_STRING_LENGTH);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

}