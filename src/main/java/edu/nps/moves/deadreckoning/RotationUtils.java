package edu.nps.moves.deadreckoning;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.RotationOrder;


/**
 * Implementation of various algorithms from IEEE Std 1278.1-2012
 * using Apache Commons Math Rotation class.
 * 
 * Uses the convention of passing radian angles in x,y,z axis rotation order
 * (e.g. phi,theta,psi or roll,pitch,yaw)
 * even though that many not be the order of rotation.
 * 
 *
 */
public class RotationUtils {

    /**
     * Transform a vector from ecef (world) to body coordinates (see IEEE Std. 1278).
     * Equivalent to out = A * in, where A =
     *          cos(theta)*cos(psi)                             cos(theta)*sin(psi)                            -sin(theta)
     * sin(phi)*sin(theta)*cos(psi)-cos(phi)*sin(psi)  sin(phi)*sin(theta)*sin(psi)+cos(phi)*cos(psi)  sin(phi)*cos(theta)
     * cos(phi)*sin(theta)*cos(psi)+sin(phi)*sin(psi)  cos(phi)*sin(theta)*sin(psi)-sin(phi)*cos(psi)  cos(phi)*cos(theta)
     * 
     * @param phi x'' rotation in radians
     * @param theta y' rotation in radians
     * @param psi z rotation in radians
     * @return rotation matrix
     */
    public static Rotation ecef2body(double phi, double theta, double psi) {
        return new Rotation(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM, psi, theta, phi);
    }

    /**
     * Transform a vector from body to ecef (world) coordinates (see IEEE Std. 1278).
     * Equivalent to out = A * in, where A =
     *  cos(theta)*cos(psi)  sin(phi)*sin(theta)*cos(psi)-cos(phi)*sin(psi)  cos(phi)*sin(theta)*cos(psi)+sin(phi)*sin(psi)
     *  cos(theta)*sin(psi)  sin(phi)*sin(theta)*sin(psi)+cos(phi)*cos(psi)  cos(phi)*sin(theta)*sin(psi)-sin(phi)*cos(psi)
     * -sin(theta)           sin(phi)*cos(theta)                             cos(phi)*cos(theta)
     *
     * @param phi x'' rotation in radians
     * @param theta y' rotation in radians
     * @param psi z rotation in radians
     * @return rotation matrix
     */
    public static Rotation body2ecef(double phi, double theta, double psi) {
        return new Rotation(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM, psi, theta, phi).revert();
    }

    /**
     * Transform a vector from ned to body coordinates (see IEEE Std. 1278).
     * Equivalent to out = A * in, where A =
     *           cos(pitch)*cos(yaw)                               cos(pitch)*sin(yaw)                              -sin(pitch)
     * sin(roll)*sin(pitch)*cos(yaw)-cos(roll)*sin(yaw)  sin(roll)*sin(pitch)*sin(yaw)+cos(roll)*cos(yaw)  sin(roll)*cos(pitch)
     * cos(roll)*sin(pitch)*cos(yaw)+sin(roll)*sin(yaw)  cos(roll)*sin(pitch)*sin(yaw)-sin(roll)*cos(yaw)  cos(roll)*cos(pitch)
     * 
     * @param roll x'' rotation in radians
     * @param pitch y' rotation in radians
     * @param yaw z rotation in radians
     * @return rotation matrix
     */
    public static Rotation ned2body(double roll, double pitch, double yaw) {
        return new Rotation(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM, yaw, pitch, roll);
    }

    /**
     * Transform a vector from body to ned coordinates (see IEEE Std. 1278).
     * Equivalent to out = A * in, where A =
     *  cos(pitch)*cos(yaw)  sin(roll)*sin(pitch)*cos(yaw)-cos(roll)*sin(yaw)  cos(roll)*sin(pitch)*cos(yaw)+sin(roll)*sin(yaw)
     *  cos(pitch)*sin(yaw)  sin(roll)*sin(pitch)*sin(yaw)+cos(roll)*cos(yaw)  cos(roll)*sin(pitch)*sin(yaw)-sin(roll)*cos(yaw)
     * -sin(pitch)           sin(roll)*cos(pitch)                              cos(roll)*cos(pitch)
     *
     * @param roll x'' rotation in radians
     * @param pitch y' rotation in radians
     * @param yaw z rotation in radians
     * @return rotation matrix
     */
    public static Rotation body2ned(double roll, double pitch, double yaw) {
        return new Rotation(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM,
                yaw, pitch, roll).revert();
    }

    // ECEF / NED / ENU at latitude and longitude
    // Implemented as rotations (took some effort to get the rotations correct!).

    /**
     * Transform a vector from ecef to ned coordinates.
     * Equivalent to out = A * in, where A =
     * -sin(lat)*cos(lon)  -sin(lat)*sin(lon)   cos(lat)
     *          -sin(lon)            cos(lon)      0
     * -cos(lat)*cos(lon)  -cos(lat)*sin(lon)  -sin(lat)
     * 
     * @param latitude in radians
     * @param longitude in radians
     * @return rotation matrix
     */
    public static Rotation ecefvec2nedvec(double latitude, double longitude) {
        return new Rotation(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM,
                longitude, -latitude - Math.PI / 2.0, 0.0);
    }

    /**
     * Transform a vector from ned to ecef coordinates.
     * Equivalent to out = A * in, where A =
     * -sin(lat)*cos(lon)  -sin(lon)  -cos(lat)*cos(lon)
     * -sin(lat)*sin(lon)   cos(lon)  -cos(lat)*sin(lon)
     *  cos(lat)               0      -sin(lat)     
     * 
     * @param latitude in radians
     * @param longitude in radians
     * @return rotation matrix
     */
    public static Rotation nedvec2ecefvec(double latitude, double longitude) {
        return new Rotation(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM,
                longitude, -latitude - Math.PI / 2.0, 0.0).revert();
    }

    /**
     * Transform a vector from ecef to enu coordinates.
     * Equivalent to out = A * in, where A =
     *          -sin(lon)            cos(lon)     0
     * -sin(lat)*cos(lon)  -sin(lat)*sin(lon)  cos(lat)
     *  cos(lat)*cos(lon)   cos(lat)*sin(lon)  sin(lat)
     * 
     * @param latitude in radians
     * @param longitude in radians
     * @return rotation matrix
     */
    public static Rotation ecefvec2enuvec(double latitude, double longitude) {
        return new Rotation(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM,
                longitude + Math.PI / 2.0, 0.0, (Math.PI / 2.0) - latitude);
    }

    /**
     * Transform a vector from enu to ecef coordinates.
     * Equivalent to out = A * in, where A =
     * -sin(lon)  -sin(lat)*cos(lon)  cos(lat)*cos(lon)
     *  cos(lon)  -sin(lat)*sin(lon)  cos(lat)*sin(lon)
     *     0       cos(lat)           sin(lat)
     * 
     * @param latitude in radians
     * @param longitude in radians
     * @return rotation matrix
     */
    public static Rotation enuvec2ecefvec(double latitude, double longitude) {
        return new Rotation(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM,
                longitude + Math.PI / 2.0, 0.0, (Math.PI / 2.0) - latitude).revert();
    }

    // Angle conversions

    /**
     * Convert angles from ned to ecef
     * @param roll
     * @param pitch
     * @param yaw
     * @param latitude
     * @param longitude
     * @return length 3 vector phi,theta,psi
     */
    public static double[] ned2ecefAngles (double roll, double pitch, double yaw,
            double latitude, double longitude) {
        double[] eulerAngles = ned2body(roll, pitch, yaw)
                .applyTo(ecefvec2nedvec(latitude, longitude))
                .getAngles(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM);
        double[] ecefAngles = new double[3];
        ecefAngles[0] = eulerAngles[2];
        ecefAngles[1] = eulerAngles[1];
        ecefAngles[2] = eulerAngles[0];

        return ecefAngles;
    }

    /**
     * Convert angles from ecef to ned
     * @param phi
     * @param theta
     * @param psi
     * @param latitude
     * @param longitude
     * @return length 3 vector roll,pitch,yaw
     */
    public static double[] ecef2nedAngles (double phi, double theta, double psi,
            double latitude, double longitude) {
        double[] eulerAngles = ecef2body(phi, theta, psi)
                .applyTo(nedvec2ecefvec(latitude, longitude))
                .getAngles(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM);
        double[] bodyAngles = new double[3];
        bodyAngles[0] = eulerAngles[2];
        bodyAngles[1] = eulerAngles[1];
        bodyAngles[2] = eulerAngles[0];
        return bodyAngles;
    }

    // Angular velocity conversions

    /**
     * Convert angular velocities from body to world
     * @param omegaX
     * @param omegaY
     * @param omegaZ
     * @param phi
     * @param theta
     * @return length 3 vector dPhiDt,dThetaDt,dPsiDt
     */
    public static double[] body2worldAngularVelocities (double omegaX, double omegaY, double omegaZ,
            double phi, double theta) {
        double cosPhi = Math.cos(phi);
        double sinPhi = Math.sin(phi);
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double dThetaDt = omegaY * cosPhi - omegaZ * sinPhi;
        double dPsiDt = (omegaY * sinPhi + omegaZ * cosPhi) / cosTheta;
        double dPhiDt = omegaX + dPsiDt * sinTheta;

        return new double[] { dPhiDt, dThetaDt, dPsiDt };
    }

    /**
     * Convert angular velocities from world to body
     * @param dPhiDt
     * @param dThetaDt
     * @param dPsiDt
     * @param phi
     * @param theta
     * @return omegaX,omegaY,omegaZ
     */
    public static double[] world2bodyAngularVelocities (double dPhiDt, double dThetaDt, double dPsiDt,
            double phi, double theta) {
        double cosPhi = Math.cos(phi);
        double sinPhi = Math.sin(phi);
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double omegaX = dPhiDt - dPsiDt * sinTheta;
        double omegaY = dThetaDt * cosPhi + dPsiDt * sinPhi * cosTheta;
        double omegaZ = -dThetaDt * sinPhi + dPsiDt * cosPhi * cosTheta;

        return new double[] { omegaX, omegaY, omegaZ };
    }
    
    // Rotation thresholds, see IEEE Std 1278.1-2012 E.7.5
    
    /**
     * Check if rotation threshold is exceeded, see IEEE Std 1278.1-2012. 
     * @param RA rotation matrix
     * @param RD rotation matrix
     * @param DRA_ORIENT_THRESH radian threshold
     * @return true if exceeded
     * @deprecated use epsilonQuaternionThreshold 
     * @deprecated Use {@link #epsilonQuaternionThreshold} and {@link #quaternionThresholdExceeded} for efficiency (speed).
     */
    @Deprecated
    public static boolean rotationThresholdExceeded(Rotation RA, Rotation RD, double DRA_ORIENT_THRESH) {
        return Rotation.distance(RA, RD) > DRA_ORIENT_THRESH;
    }

    /**
     * Calculate threshold for matrix trace calculation, see IEEE Std 1278.1-2012.
     * @param DRA_ORIENT_THRESH radian threshold
     * @return matrix trace threshold = 2-2*cos(DRA_ORIENT_THRESH)
     * @deprecated Use {@link #epsilonQuaternionThreshold} and {@link #quaternionThresholdExceeded} for efficiency (speed).
     */
    public static double deltaMatrixThreshold (double DRA_ORIENT_THRESH) {
        return 2.0 - 2.0 * Math.cos(DRA_ORIENT_THRESH);
    }
    
    /**
     * Check if matrix threshold is exceeded, see IEEE Std 1278.1-2012. 
     * @param RA rotation matrix
     * @param RD rotation matrix
     * @param delta threshold for 3-trace
     * @return true if exceeded
     * @deprecated Use {@link #epsilonQuaternionThreshold} and {@link #quaternionThresholdExceeded} for efficiency (speed).
     */
    public static boolean matrixThresholdExceeded(Rotation RA, Rotation RD, double delta) {
        Rotation RE = RD.applyInverseTo(RA);
        double[][] ME = RE.getMatrix();
        double trace = ME[0][0] + ME[1][1] + ME[2][2]; 
        return 3.0 - trace > delta;
    }

    /**
     * Calculate threshold for quaternion calculation, see IEEE Std 1278.1-2012.
     * @param DRA_ORIENT_THRESH radian threshold
     * @return quaternion threshold = 1-cos(DRA_ORIENT_THRESH/2)
     */
    public static double epsilonQuaternionThreshold(double DRA_ORIENT_THRESH) {
        return 1.0 - Math.cos(DRA_ORIENT_THRESH / 2.0);
    }
    
    /**
     * Check if quaternion threshold is exceeded, see IEE Std 1278.1-2012.
     * @param RA rotation matrix
     * @param RD rotation matrix
     * @param epsilon threshold for 1-S
     * @return true if exceeded
     */
    public static boolean quaternionThresholdExceeded(Rotation RA, Rotation RD, double epsilon) {
        double S = RA.getQ0() * RD.getQ0() + RA.getQ1() * RD.getQ1() + RA.getQ2() * RD.getQ2() + RA.getQ3() * RD.getQ3();
        return 1 - S > epsilon; 
    }
}
