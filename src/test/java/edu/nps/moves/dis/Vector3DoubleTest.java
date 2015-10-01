
package edu.nps.moves.dis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcgredo
 */
public class Vector3DoubleTest
{

    /**
     * Tests to make sure values are set correctly.
     */
    @Test
    public void setValueTest()
    {
        Vector3Double vec;
        vec = new Vector3Double();
        vec.setX(1.0);
        vec.setY(2.0);
        vec.setZ(3.0);

        // Doing double value equality comparisions in inherently dangerous due
        // to digital roundoff, but for THESE SPECIFIC VALUES I think it's ok.
        // You're on your own for other values.
        assertTrue(vec.getX() == 1.0);
        assertTrue(vec.getY() == 2.0);
        assertTrue(vec.getZ() == 3.0);
    }

    /**
     * Should return a latitude and longitude at the prime meridian and the
     * equator, at altitude 0 meters, or close to it. 
     */
    @Test
    public void getLatLonMeridianTest()
    {
       Vector3Double vec = new Vector3Double();

       // Diameter of the earth is about 12,742 KM. DIS has X out the prime
       // merdian at the equator, y out the equator at 90 deg east, and
       // z out the north pole.
       vec.setY(0.0);
       vec.setZ(0.0);
       vec.setX(6371000.0); // half diameter of earth; on x-axis this should put
                            // us at (0,0,0) in lat, lon, altitude, or close to it
    }

    /**
     * Should return a latitude and longitude at 90 deg east and the
     * equator, at altitude zero, or close to it.
     */
    @Test
    public void getLatLon90EastTest()
    {
       Vector3Double vec = new Vector3Double();

       // Diameter of the earth is about 12,742 KM. DIS has X out the prime
       // merdian at the equator, y out the equator at 90 deg east, and
       // z out the north pole.
       vec.setX(0.0);
       vec.setZ(0.0);
       vec.setY(6371000.0); // half diameter of earth; on y-axis this should put
                            // us at (0,90,0) in lat, lon, altitude, or close to it
     
    }

    /**
     * Should return a latitude and longitude at 90 deg east and the
     * equator, at altitude zero, or close to it.
     */
    @Test
    public void getLatLon45DegNorthPrimeMeridian()
    {
       Vector3Double vec = new Vector3Double();
       Vector3Double result;

       // Diameter of the earth is about 12,742 KM. DIS has X out the prime
       // merdian at the equator, y out the equator at 90 deg east, and
       // z out the north pole.
       vec.setY(0.0);
       vec.setX(6371000.0 * Math.cos( (45.0 * Math.PI)/180.0)); // half diameter of earth; on x-axis this should put
                                                                // us at 45 deg north at the prime meridian
       vec.setZ(6371000.0 * Math.cos( (45.0 * Math.PI)/180.0) );
   
    }

    /**
     * Test converting degrees lat/lon to DIS x,y,z.
     */
    @Test
    public void getXYZforEquatorPrimeMeridian()
    {
        Vector3Double vec = new Vector3Double();
        vec.setX(0.0); // lat
        vec.setY(0.0); // lon
        vec.setZ(0.0); // altitude (m)
    }

    /**
     * Test converting degrees lat/lon to DIS x,y,z.
     */
    @Test
    public void getXYZfor45NorthPrimeMeridian()
    {
        Vector3Double vec = new Vector3Double();
        vec.setX(45.0); // lat
        vec.setY(0.0);  // lon
        vec.setZ(0.0);  // altitude (m)
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

}