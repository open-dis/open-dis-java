
package edu.nps.moves.spatial;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import SRM.*;               // Sedris Spatial Reference Model, v. 4.4
import edu.nps.moves.dis.*;
import edu.nps.moves.disutil.*;

/**
 *
 * @author mcgredo
 */
public class RangeCoordinatesTest {

    public RangeCoordinatesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test creation of DIS coordinates from a local frame
     */
    @Test
    public void disCoordinatesFromLocal()
    {
         // x-axis intercept: prime meridian, equator, and zero altitude. The
        // DIS coordinates we get should have a Y value of about zero, a Z value
        // of about zero, and an X value of 6 million or so.
        // Z points up through north pole, X out prime meridian at equator, Y
        // a equator & 90 deg east.
        RangeCoordinates primeMeridian = new RangeCoordinates(0.0, 0.0, 0.0);
        Vector3Double origin = primeMeridian.DISCoordFromLocalFlat(0.0, 0.0, 0.0);
        assertTrue(origin.getY() < 1.0 && origin.getY() > -1.0);
        assertTrue(origin.getZ() < 1.0 && origin.getZ() > -1.0);
        // 6378137 is the actual true value we're looking for
        assertTrue(origin.getX() < 6378138 && origin.getX() > 6378136);
        //System.out.println("X value:" + origin.getX());


        // North pole: z-axis intercept with earth surface. Z value should be 6356752.314245179,
        // x and y zero (about).
        RangeCoordinates northPole = new RangeCoordinates(90.0, 0.0, 0.0); // north pole
        origin = northPole.DISCoordFromLocalFlat(0.0, 0.0, 0.0);

        //System.out.println("Origin:" + origin.getX() + "," + origin.getY() + "," + origin.getZ());

        assertTrue(origin.getY() < 1.0 && origin.getY() > -1.0);
        assertTrue(origin.getZ() < 6356753 && origin.getZ() > 6356751);
        assertTrue(origin.getX() < 1 && origin.getX() > -1);

        // y-axis: equator, 90 deg east.  z should be near-zero,
        // x near zero, y at 6378137.0
        RangeCoordinates yAxis = new RangeCoordinates(0.0, 90, 0.0); // lat, lon, alt: y axis for DIS
        origin = yAxis.DISCoordFromLocalFlat(0.0, 0.0, 0.0);
        assertTrue(origin.getY() <6378138 && origin.getY() > 6378136);
        assertTrue(origin.getZ() < 1 && origin.getZ() > -1);
        assertTrue(origin.getX() < 1 && origin.getX() > -1);
    }

    /**
     * Tests conversion to DIS orientation from local coordinates.
     */
    /*
    @Test
    public void disOrientationFromLocal()
    {
        // Local coordinate system set up at the prime meridian and equator at zero altitude
        RangeCoordinates primeMeridian = new RangeCoordinates(0.0, 0.0, 0.0); //equator, prime meridian, zero altitude

        // The "correct" values from an independent source
        double phi = EulerConversions.getPhiFromTaitBryanAngles(0.0, //lat
                                                   0.0, //lon 
                                                   0.0, //yaw (heading)
                                                   0.0, // pitch
                                                   0.0); //roll
        
        double theta = EulerConversions.getThetaFromTaitBryanAngles(0.0, //lat
                                                   0.0, //lon 
                                                   0.0, //yaw (heading)
                                                   0.0); //pitch
        
        double psi = EulerConversions.getPsiFromTaitBryanAngles(0.0, // lat
                                                                0.0, //lon
                                                                0.0, //yaw/heading
                                                                0.0); //pitch

        // orientation in the local coordinate system
        edu.nps.moves.dis.Orientation localOrientation = new edu.nps.moves.dis.Orientation();
        localOrientation.setPhi(0.0f);
        localOrientation.setTheta(0.0f);
        localOrientation.setPsi(1.5f);

        // position in the local coordinate system
        edu.nps.moves.dis.Vector3Double localPosition = new Vector3Double();
        localPosition.setX(1.0);
        localPosition.setY(1.0);
        localPosition.setZ(0.0);

        edu.nps.moves.dis.Orientation o = primeMeridian.localRollPitchHeadingToDisEuler(localOrientation, // orientation in local coordinate system
                localPosition); // local position in local coordinate system


        System.out.println("phi is:" + o.getPhi() + " should be " + phi);
        System.out.println("theta is:" + o.getTheta() + " should be " + theta);
        System.out.println("psi is:" + o.getPsi() + " should be " + psi);
    }
     
     */

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

}
