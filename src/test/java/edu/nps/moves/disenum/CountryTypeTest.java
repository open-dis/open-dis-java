
package edu.nps.moves.disenum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test case for country type. Does very simple test on a few enums, on the theory
 * that anything really bad will show up in those enums.
 * 
 * @author DMcG
 */
public class CountryTypeTest
{

    public CountryTypeTest() 
    {
    }

    /**
     * Test to make sure the US country enumeration exists and has the expected
     * values.
     */
    @Test
    public void usExists()
    {
        //System.out.println("running usExists test");
      CountryType us = CountryType.UNITED_STATES;
      assertEquals(us.getDescription(), "United States");
      assertEquals(us.getValue(),  225);
      assertEquals(us.getInternetDomainCode(), "US");
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