package edu.nps.moves.dis;

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
public class AggregateMarkingTest {

    private static final int MARKING_STRING_LENGTH = 31;

    public AggregateMarkingTest() {
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
