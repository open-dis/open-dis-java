/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nps.moves.dis;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcgredo
 */
public class EntityStatePduTest
{
    
    public EntityStatePduTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void testMobility()
    {
        EntityStatePdu espdu = new EntityStatePdu();
        int mobility = espdu.getEntityAppearance_mobility();
        assert(mobility == 0);
        
        espdu.setEntityAppearance_mobility(1);
        mobility = espdu.getEntityAppearance_mobility();
        assert(mobility == 1);
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
