package edu.nps.moves.deadreckoning;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.BeforeClass;
import org.junit.Test;

public class RotationUtilsTest {

    private static Vector3D[] vec;
    private static double[][] euler;
    private static double[][] latlon;
    private static final double DELTA = 1.0e-6;

    private static final double DRA_ORIENT_THRESH = 5.0 * Math.PI / 180.0;
    private static final double DRA_ORIENT_DELTA = 1.0e-6;
    private static final double POS_DRA_ORIENT_FAIL =  (1.0 + DRA_ORIENT_DELTA) * DRA_ORIENT_THRESH;
    private static final double POS_DRA_ORIENT_PASS =  (1.0 - DRA_ORIENT_DELTA) * DRA_ORIENT_THRESH;
    private static final double NEG_DRA_ORIENT_PASS = -(1.0 - DRA_ORIENT_DELTA) * DRA_ORIENT_THRESH;
    private static final double NEG_DRA_ORIENT_FAIL = -(1.0 + DRA_ORIENT_DELTA) * DRA_ORIENT_THRESH;
    private static final int DRA_ORIENT_THRES_ITERATION = 1000000;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Random r = new Random(0);
        vec = new Vector3D[8];
        vec[0] = new Vector3D( r.nextDouble(),  r.nextDouble(),  r.nextDouble());
        vec[1] = new Vector3D(-r.nextDouble(),  r.nextDouble(),  r.nextDouble());
        vec[2] = new Vector3D(-r.nextDouble(), -r.nextDouble(),  r.nextDouble());
        vec[3] = new Vector3D( r.nextDouble(), -r.nextDouble(),  r.nextDouble());
        vec[4] = new Vector3D( r.nextDouble(),  r.nextDouble(), -r.nextDouble());
        vec[5] = new Vector3D(-r.nextDouble(),  r.nextDouble(), -r.nextDouble());
        vec[6] = new Vector3D(-r.nextDouble(), -r.nextDouble(), -r.nextDouble());
        vec[7] = new Vector3D( r.nextDouble(), -r.nextDouble(), -r.nextDouble());

        // euler is phi,theta,psi; or roll,pitch,yaw
        euler = new double[8][3];
        euler[0] = new double[] {  r.nextDouble() * Math.PI,  r.nextDouble() * Math.PI / 2.0,  r.nextDouble() * Math.PI };
        euler[1] = new double[] { -r.nextDouble() * Math.PI,  r.nextDouble() * Math.PI / 2.0,  r.nextDouble() * Math.PI };
        euler[2] = new double[] { -r.nextDouble() * Math.PI, -r.nextDouble() * Math.PI / 2.0,  r.nextDouble() * Math.PI };
        euler[3] = new double[] {  r.nextDouble() * Math.PI, -r.nextDouble() * Math.PI / 2.0,  r.nextDouble() * Math.PI };
        euler[4] = new double[] {  r.nextDouble() * Math.PI,  r.nextDouble() * Math.PI / 2.0, -r.nextDouble() * Math.PI };
        euler[5] = new double[] { -r.nextDouble() * Math.PI,  r.nextDouble() * Math.PI / 2.0, -r.nextDouble() * Math.PI };
        euler[6] = new double[] { -r.nextDouble() * Math.PI, -r.nextDouble() * Math.PI / 2.0, -r.nextDouble() * Math.PI };
        euler[7] = new double[] {  r.nextDouble() * Math.PI, -r.nextDouble() * Math.PI / 2.0, -r.nextDouble() * Math.PI };

        // latlon is lat,lon
        latlon = new double[8][2];
        latlon[0] = new double[] {  r.nextDouble() * Math.PI / 2.0,   r.nextDouble() * Math.PI };
        latlon[1] = new double[] { -r.nextDouble() * Math.PI / 2.0,   r.nextDouble() * Math.PI };
        latlon[2] = new double[] {  r.nextDouble() * Math.PI / 2.0,  -r.nextDouble() * Math.PI };
        latlon[3] = new double[] { -r.nextDouble() * Math.PI / 2.0,  -r.nextDouble() * Math.PI };
    }

    // I don't need tests for the simple rotations, because they're simple LOL

    /**
     * Transform a vector from world (ecef) to body coordinates (see IEEE Std. 1278).
     * NOTE: angles are passed in order of application.
     * An inefficient implementation for testing purposes. 
     * @param psi radians
     * @param theta radians
     * @param phi radians
     * @return
     */
    private RealMatrix world2body(double phi, double theta, double psi) {
        double cPsi = Math.cos(psi);
        double sPsi = Math.sin(psi);
        double cTht = Math.cos(theta);
        double sTht = Math.sin(theta);
        double cPhi = Math.cos(phi);
        double sPhi = Math.sin(phi);

        RealMatrix out = MatrixUtils.createRealMatrix(3, 3);
        out.setEntry(0, 0, cTht * cPsi);
        out.setEntry(0, 1, cTht * sPsi);
        out.setEntry(0, 2, -sTht);
        out.setEntry(1, 0, sPhi * sTht * cPsi - cPhi * sPsi);
        out.setEntry(1, 1, sPhi * sTht * sPsi + cPhi * cPsi);
        out.setEntry(1, 2, sPhi * cTht);
        out.setEntry(2, 0, cPhi * sTht * cPsi + sPhi * sPsi);
        out.setEntry(2, 1, cPhi * sTht * sPsi - sPhi * cPsi);
        out.setEntry(2, 2, cPhi * cTht);
        return out;
    }

    private RealMatrix world2ned(double latitude, double longitude) {
        double cLat = Math.cos(latitude);
        double sLat = Math.sin(latitude);
        double cLon = Math.cos(longitude);
        double sLon = Math.sin(longitude);

        RealMatrix out = MatrixUtils.createRealMatrix(3, 3);
        out.setEntry(0, 0, -sLat * cLon);
        out.setEntry(0, 1, -sLat * sLon);
        out.setEntry(0, 2, cLat);
        out.setEntry(1, 0, -sLon);
        out.setEntry(1, 1, cLon);
        out.setEntry(1, 2, 0.0);
        out.setEntry(2, 0, -cLat * cLon);
        out.setEntry(2, 1, -cLat * sLon);
        out.setEntry(2, 2, -sLat);
        return out;
    }

    private RealMatrix world2enu(double latitude, double longitude) {
        double cLat = Math.cos(latitude);
        double sLat = Math.sin(latitude);
        double cLon = Math.cos(longitude);
        double sLon = Math.sin(longitude);

        RealMatrix out = MatrixUtils.createRealMatrix(3, 3);
        out.setEntry(0, 0, -sLon);
        out.setEntry(0, 1,  cLon);
        out.setEntry(0, 2,  0.0);
        out.setEntry(1, 0,  -sLat * cLon);
        out.setEntry(1, 1,  -sLat * sLon);
        out.setEntry(1, 2,  cLat);
        out.setEntry(2, 0,  cLat * cLon);
        out.setEntry(2, 1,  cLat * sLon);
        out.setEntry(2, 2, sLat);
        return out;
    }

    @Test
    public void testEcef2body() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertArrayEquals(world2body(euler[j][0], euler[j][1], euler[j][2]).operate(vec[i].toArray()),
                        RotationUtils.ecef2body(euler[j][0], euler[j][1], euler[j][2]).applyTo(vec[i]).toArray(), DELTA);
            }
        }
    }

    @Test
    public void testBody2ecef() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertArrayEquals(world2body(euler[j][0], euler[j][1], euler[j][2]).transpose().operate(vec[i].toArray()),
                        RotationUtils.body2ecef(euler[j][0], euler[j][1], euler[j][2]).applyTo(vec[i]).toArray(), DELTA);
            }
        }
    }

    @Test
    public void testNed2body() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertArrayEquals(world2body(euler[j][0], euler[j][1], euler[j][2]).operate(vec[i].toArray()),
                        RotationUtils.ned2body(euler[j][0], euler[j][1], euler[j][2]).applyTo(vec[i]).toArray(), DELTA);
            }
        }
    }

    @Test
    public void testBody2ned() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertArrayEquals(world2body(euler[j][0], euler[j][1], euler[j][2]).transpose().operate(vec[i].toArray()),
                        RotationUtils.body2ned(euler[j][0], euler[j][1], euler[j][2]).applyTo(vec[i]).toArray(), DELTA);
            }
        }

        /*
         * Some additional simple checks of IEEE Std 1278.1-2012 conventions
         * Body axes x,y,z are front,right,bottom
         */

        // Yaw is heading from true North, positive to the right.
        assertTrue(RotationUtils.body2ned(0, 0, Math.PI / 4.0).applyTo(new Vector3D(1, 0, 0)).getX() > 0);  // North
        assertTrue(RotationUtils.body2ned(0, 0, Math.PI / 4.0).applyTo(new Vector3D(1, 0, 0)).getY() > 0);  // East

        // Pitch is positive up.
        assertTrue(RotationUtils.body2ned(0, Math.PI / 4.0, 0).applyTo(new Vector3D(1, 0, 0)).getZ() < 0);  // Negative down -> Positive up

        // Roll is positive tilt to the right (right wing down).
        assertTrue(RotationUtils.body2ned(Math.PI / 4.0, 0, 0).applyTo(new Vector3D(0, 1, 0)).getZ() > 0);  // Positive down
    }

    @Test
    public void testNedvec2ecefvec() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                assertArrayEquals(world2ned(latlon[j][0], latlon[j][1]).transpose().operate(vec[i].toArray()),
                        RotationUtils.nedvec2ecefvec(latlon[j][0], latlon[j][1]).applyTo(vec[i]).toArray(), DELTA);
            }
        }
    }

    @Test
    public void testEcefvec2nedvec() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                assertArrayEquals(world2ned(latlon[j][0], latlon[j][1]).operate(vec[i].toArray()),
                        RotationUtils.ecefvec2nedvec(latlon[j][0], latlon[j][1]).applyTo(vec[i]).toArray(), DELTA);
            }
        }
    }

    @Test
    public void testEnuvec2ecefvec() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                assertArrayEquals(world2enu(latlon[j][0], latlon[j][1]).transpose().operate(vec[i].toArray()),
                        RotationUtils.enuvec2ecefvec(latlon[j][0], latlon[j][1]).applyTo(vec[i]).toArray(), DELTA);
            }
        }
    }

    @Test
    public void testEcefvec2enuvec() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                assertArrayEquals(world2enu(latlon[j][0], latlon[j][1]).operate(vec[i].toArray()),
                        RotationUtils.ecefvec2enuvec(latlon[j][0], latlon[j][1]).applyTo(vec[i]).toArray(), DELTA);
            }
        }
    }

    @Test
    public void testAngles() {
        for (int i = 0; i < 8; i++) {
            double[] ecefAngles = RotationUtils.ned2ecefAngles(euler[i][0], euler[i][1], euler[i][2],
                    latlon[i][0], latlon[i][1]);
            double[] bodyAngles = RotationUtils.ecef2nedAngles(ecefAngles[0], ecefAngles[1], ecefAngles[2],
                    latlon[i][0], latlon[i][1]);
            assertArrayEquals(euler[i], bodyAngles, DELTA);
        }
    }

    @Test
    public void testAngularVelocities() {
        for (int i = 0; i < 8; i++) {
            double[] ecefAngularVelocities = RotationUtils.body2worldAngularVelocities(vec[i].getX(), vec[i].getY(), vec[i].getZ(),
                    latlon[i][0], latlon[i][1]);
            double[] bodyAngularVelocities = RotationUtils.world2bodyAngularVelocities(ecefAngularVelocities[0], ecefAngularVelocities[1], ecefAngularVelocities[2],
                    latlon[i][0], latlon[i][1]);
            assertArrayEquals(vec[i].toArray(), bodyAngularVelocities, DELTA);
        }
    }

    /*
     * Use vec[0-3] as axis of rotation, euler[0-3][1] as angle for RD
     * Use vec[4-7] as axis of differential rotation, angle close to threshold, for RE
     */

    @Test
    public void testRotationThreshold() {
        Rotation[] RD = new Rotation[4];
        Rotation[][] RA = new Rotation[4][4];

        for (int i = 0; i < 4; i++) {
            RD[i] = new Rotation(vec[i].normalize(), euler[i][1], RotationConvention.VECTOR_OPERATOR);
            Rotation RE;

            RE = new Rotation(vec[i + 4].normalize(), POS_DRA_ORIENT_FAIL, RotationConvention.VECTOR_OPERATOR);
            RA[i][0] = RD[i].applyTo(RE);

            RE = new Rotation(vec[i + 4].normalize(), POS_DRA_ORIENT_PASS, RotationConvention.VECTOR_OPERATOR);
            RA[i][1] = RD[i].applyTo(RE);

            RE = new Rotation(vec[i + 4].normalize(), NEG_DRA_ORIENT_PASS, RotationConvention.VECTOR_OPERATOR);
            RA[i][2] = RD[i].applyTo(RE);

            RE = new Rotation(vec[i + 4].normalize(), NEG_DRA_ORIENT_FAIL, RotationConvention.VECTOR_OPERATOR);
            RA[i][3] = RD[i].applyTo(RE);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < DRA_ORIENT_THRES_ITERATION; j++) {
                assertTrue(RotationUtils.rotationThresholdExceeded(RA[i][0],  RD[i], DRA_ORIENT_THRESH));
                assertFalse(RotationUtils.rotationThresholdExceeded(RA[i][1], RD[i], DRA_ORIENT_THRESH));
                assertFalse(RotationUtils.rotationThresholdExceeded(RA[i][2], RD[i], DRA_ORIENT_THRESH));
                assertTrue(RotationUtils.rotationThresholdExceeded(RA[i][3],  RD[i], DRA_ORIENT_THRESH));
            }
        }
    }

    @Test
    public void testMatrixThreshold() {
        Rotation[] RD = new Rotation[4];
        Rotation[][] RA = new Rotation[4][4];

        for (int i = 0; i < 4; i++) {
            RD[i] = new Rotation(vec[i].normalize(), euler[i][1], RotationConvention.VECTOR_OPERATOR);
            Rotation RE;

            RE = new Rotation(vec[i + 4].normalize(), POS_DRA_ORIENT_FAIL, RotationConvention.VECTOR_OPERATOR);
            RA[i][0] = RD[i].applyTo(RE);

            RE = new Rotation(vec[i + 4].normalize(), POS_DRA_ORIENT_PASS, RotationConvention.VECTOR_OPERATOR);
            RA[i][1] = RD[i].applyTo(RE);

            RE = new Rotation(vec[i + 4].normalize(), NEG_DRA_ORIENT_PASS, RotationConvention.VECTOR_OPERATOR);
            RA[i][2] = RD[i].applyTo(RE);

            RE = new Rotation(vec[i + 4].normalize(), NEG_DRA_ORIENT_FAIL, RotationConvention.VECTOR_OPERATOR);
            RA[i][3] = RD[i].applyTo(RE);
        }
        
        double delta = RotationUtils.deltaMatrixThreshold(DRA_ORIENT_THRESH);
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < DRA_ORIENT_THRES_ITERATION; j++) {
                assertTrue(RotationUtils.matrixThresholdExceeded(RA[i][0],  RD[i], delta));
                assertFalse(RotationUtils.matrixThresholdExceeded(RA[i][1], RD[i], delta));
                assertFalse(RotationUtils.matrixThresholdExceeded(RA[i][2], RD[i], delta));
                assertTrue(RotationUtils.matrixThresholdExceeded(RA[i][3],  RD[i], delta));
            }
        }
    }

    @Test
    public void testQuaternionThreshold() {
        Rotation[] RD = new Rotation[4];
        Rotation[][] RA = new Rotation[4][4];

        for (int i = 0; i < 4; i++) {
            RD[i] = new Rotation(vec[i].normalize(), euler[i][1], RotationConvention.VECTOR_OPERATOR);
            Rotation RE;

            RE = new Rotation(vec[i + 4].normalize(), POS_DRA_ORIENT_FAIL, RotationConvention.VECTOR_OPERATOR);
            RA[i][0] = RD[i].applyTo(RE);

            RE = new Rotation(vec[i + 4].normalize(), POS_DRA_ORIENT_PASS, RotationConvention.VECTOR_OPERATOR);
            RA[i][1] = RD[i].applyTo(RE);

            RE = new Rotation(vec[i + 4].normalize(), NEG_DRA_ORIENT_PASS, RotationConvention.VECTOR_OPERATOR);
            RA[i][2] = RD[i].applyTo(RE);

            RE = new Rotation(vec[i + 4].normalize(), NEG_DRA_ORIENT_FAIL, RotationConvention.VECTOR_OPERATOR);
            RA[i][3] = RD[i].applyTo(RE);
        }
        
        double epsilon = RotationUtils.epsilonQuaternionThreshold(DRA_ORIENT_THRESH);
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < DRA_ORIENT_THRES_ITERATION; j++) {
                assertTrue(RotationUtils.quaternionThresholdExceeded(RA[i][0],  RD[i], epsilon));
                assertFalse(RotationUtils.quaternionThresholdExceeded(RA[i][1], RD[i], epsilon));
                assertFalse(RotationUtils.quaternionThresholdExceeded(RA[i][2], RD[i], epsilon));
                assertTrue(RotationUtils.quaternionThresholdExceeded(RA[i][3],  RD[i], epsilon));
            }
        }
    }
    
    // NOTE: quaternionThreshold is fastest, rotationThreshold is slowest.

}
