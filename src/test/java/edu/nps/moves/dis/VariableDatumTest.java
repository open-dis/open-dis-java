
package edu.nps.moves.dis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This primarily checks to make sure the units are correct on a post-processing patch applied
 * to variable datums. Variable datums measure size in bits rather than bytes.<p>
 
 * @author DMcG
 */
public class VariableDatumTest
{

    public VariableDatumTest() 
	{
    }

    @Test
    public void VariableDatumPatchTest()
    {
        VariableDatum vd = new VariableDatum();

        final String datum = "1234567"; // seven bytes
        byte[] datumBytes = datum.getBytes();
        vd.setVariableData(datumBytes);
		
        assertEquals(56, vd.getVariableDatumLength()); // Ensure length is in bits.
        
        byte[] data = new byte[vd.getMarshalledSize()];
        java.nio.ByteBuffer buff = java.nio.ByteBuffer.wrap(data);
        vd.marshal(buff);
        
        assertEquals(16, buff.position()); // Ensure it's padded properly when marshalled.
    }
    
    @Test
    public void nullTest()
    {
        assert(true);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

}