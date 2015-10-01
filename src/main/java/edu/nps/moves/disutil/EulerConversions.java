package edu.nps.moves.disutil;

/**
 * Class contains methods that convert to Tait_Bryan_angles (i.e., roll, pitch
 * and yaw/heading) given the position (i.e., latitude, longitude) and the
 * euler angles (i.e., psi, theta, and phi).
 *
 * Class also has methods for the corollary: converting to psi, theta, and phi
 * given the lat/lon position and the entity's roll, pitch and yaw angles
 *
 * In this class roll, pitch and yaw are always expressed in degrees
 * whereas psi, theta, and phi are always in radians.
 *
 * Note: latitude and longitude are also expressed in radians.
 * 
 *
 * @author loyaj & bhughes
 *
 */

public class EulerConversions
{

    static double _toDegrees = 57.2957795131;
    static double _toRadians =  0.01745329252;

    /**
     * Gets a degree heading for an entity based on euler angles. All angular values passed in must be in radians.
     * @param lat Entity's latitude,    IN RADIANS
     * @param lon Entity's longitude,   IN RADIANS
     * @param psi Psi angle,            IN RADIANS
     * @param theta Theta angle,        IN RADIANS
     * @return the heading, in degrees, with 0 being north, positive angles going clockwise,
     * and negative angles going counterclockwise (i.e., 90 deg is east, -90 is west)
     */
    public static double getOrientationFromEuler(double lat, double lon, double psi, double theta)
    {
        double sinlat = Math.sin(lat);
        double sinlon = Math.sin(lon);
        double coslon = Math.cos(lon);
        double coslat = Math.cos(lat);
        double sinsin = sinlat * sinlon;

        double cosTheta = Math.cos(theta);
        double cosPsi = Math.cos(psi);
        double sinPsi = Math.sin(psi);
        double sinTheta = Math.sin(theta);


        double cosThetaCosPsi = cosTheta * cosPsi;
        double cosThetaSinPsi = cosTheta * sinPsi;
        double sincos = sinlat * coslon;

        double b11 = -sinlon * cosThetaCosPsi + coslon * cosThetaSinPsi;
        double b12 = -sincos * cosThetaCosPsi - sinsin * cosThetaSinPsi - coslat * sinTheta;

        return Math.toDegrees(Math.atan2(b11, b12));//range is -pi to pi
    }

    /**
     * Gets a degree pitch for an entity based on euler angles. All angular values passed in must be in radians.
     * @param lat Entity's latitude,    IN RADIANS
     * @param lon Entity's longitude,   IN RADIANS
     * @param psi Psi angle,            IN RADIANS
     * @param theta Theta angle,        IN RADIANS
     * @return the pitch, in degrees, with 0 being level. A negative values is when the entity's
     * nose is pointing downward, positive value is when the entity's nose is pointing upward.
     */
    public static double getPitchFromEuler(double lat, double lon, double psi, double theta)
    {
        double sinlat = Math.sin(lat);
        double sinlon = Math.sin(lon);
        double coslon = Math.cos(lon);
        double coslat = Math.cos(lat);
        double cosLatCosLon = coslat * coslon;
        double cosLatSinLon = coslat * sinlon;

        double cosTheta = Math.cos(theta);
        double cosPsi = Math.cos(psi);
        double sinPsi = Math.sin(psi);
        double sinTheta = Math.sin(theta);

        return Math.toDegrees(Math.asin(cosLatCosLon*cosTheta*cosPsi + cosLatSinLon*cosTheta*sinPsi - sinlat*sinTheta));
    }

    /**
     * Gets the degree roll for an entity based on euler angles. All angular values passed in must be in radians.
     * @param lat Entity's latitude,    IN RADIANS
     * @param lon Entity's longitude,   IN RADIANS
     * @param psi Psi angle,            IN RADIANS
     * @param theta Theta angle,        IN RADIANS
     * @param phi Phi angle,            IN RADIANS
     * @return the roll, in degrees, with 0 being level flight, + roll is clockwise when looking out the front of the entity.
     */
    public static double getRollFromEuler(double lat, double lon, double psi, double theta, double phi)
    {
        double sinlat = Math.sin(lat);
        double sinlon = Math.sin(lon);
        double coslon = Math.cos(lon);
        double coslat = Math.cos(lat);
        double cosLatCosLon = coslat * coslon;
        double cosLatSinLon = coslat * sinlon;

        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);
        double cosPsi   = Math.cos(psi);
        double sinPsi   = Math.sin(psi);
        double sinPhi   = Math.sin(phi);
        double cosPhi   = Math.cos(phi);

        double sinPhiSinTheta = sinPhi * sinTheta;
        double cosPhiSinTheta = cosPhi * sinTheta;

        double b23 = cosLatCosLon*(-cosPhi*sinPsi + sinPhiSinTheta*cosPsi) +
                     cosLatSinLon*( cosPhi*cosPsi + sinPhiSinTheta*sinPsi) +
                     sinlat * (sinPhi * cosTheta);

        double b33 = cosLatCosLon*( sinPhi*sinPsi + cosPhiSinTheta*cosPsi) +
                     cosLatSinLon*(-sinPhi*cosPsi + cosPhiSinTheta*sinPsi) +
                     sinlat * (cosPhi * cosTheta);

        return Math.toDegrees(Math.atan2(-b23, -b33));
    }

    /**
     * Gets the Euler Theta value (in radians) from position and Tait-Brayn yaw and roll angles
     * @param lat Entity's latitude,    IN RADIANS
     * @param lon Entity's longitude,   IN RADIANS
     * @param yaw   entity's yaw angle (also know as the entity's bearing or heading angle), in degrees
     * @param pitch entity's pitch angle, in degrees
     * @return the Theta value in radians
     */
    public static double getThetaFromTaitBryanAngles(double lat, double lon, double yaw, double pitch)
    {
        double sinLat = Math.sin(lat);
        double cosLat = Math.cos(lat);

        double cosPitch = Math.cos(pitch*_toRadians);
        double sinPitch = Math.sin(pitch*_toRadians);
        double cosYaw   = Math.cos(yaw*_toRadians);

        return Math.asin(  -cosLat * cosYaw * cosPitch - sinLat * sinPitch );
    }

    /**
     * Gets the Euler Psi value (in radians) from position and Tait-Brayn yaw and roll angles
     * @param lat Entity's latitude,    IN RADIANS
     * @param lon Entity's longitude,   IN RADIANS
     * @param yaw   ettity's yaw angle (also know as the entity's bearing or heading angle), in degrees
     * @param pitch entity's pitch angle, in degrees
     * @return the Psi value in radians
     */
    public static double getPsiFromTaitBryanAngles(double lat, double lon, double yaw, double pitch){

        double sinLat = Math.sin(lat);
        double sinLon = Math.sin(lon);
        double cosLon = Math.cos(lon);
        double cosLat = Math.cos(lat);
        double cosLatCosLon = cosLat * cosLon;
        double cosLatSinLon = cosLat * sinLon;
        double sinLatCosLon = sinLat * cosLon;
        double sinLatSinLon = sinLat * sinLon;

        double cosPitch = Math.cos(pitch*_toRadians);
        double sinPitch = Math.sin(pitch*_toRadians);
        double sinYaw   = Math.sin(yaw*_toRadians);
        double cosYaw   = Math.cos(yaw*_toRadians);

        double a_11 = -sinLon * sinYaw * cosPitch - sinLatCosLon * cosYaw * cosPitch + cosLatCosLon * sinPitch;
        double a_12 =  cosLon * sinYaw * cosPitch - sinLatSinLon * cosYaw * cosPitch + cosLatSinLon * sinPitch;

        return Math.atan2(a_12, a_11);
    }

    /**
     * Gets the Euler Phi value (in radians) from position and Tait-Brayn yaw, pitch and roll angles
     * @param lat Entity's latitude,    IN RADIANS
     * @param lon Entity's longitude,   IN RADIANS
     * @param yaw yaw angle (also know as the entity's bearing or heading angle), in degrees
     * @param pitch entity's pitch angle, in degrees
     * @param roll  entity's roll angle (0 is level flight, + roll is clockwise looking out the nose), in degrees
     * @return the Phi value in radians
     */
    public static double getPhiFromTaitBryanAngles(double lat, double lon, double yaw, double pitch, double roll){

        double sinLat = Math.sin(lat);
        double cosLat = Math.cos(lat);

        double cosRoll  = Math.cos(roll*_toRadians);
        double sinRoll  = Math.sin(roll*_toRadians);
        double cosPitch = Math.cos(pitch*_toRadians);
        double sinPitch = Math.sin(pitch*_toRadians);
        double sinYaw   = Math.sin(yaw*_toRadians);
        double cosYaw   = Math.cos(yaw*_toRadians);

        double a_23 = cosLat * (-sinYaw * cosRoll + cosYaw * sinPitch * sinRoll) - sinLat * cosPitch * sinRoll;
        double a_33 = cosLat * ( sinYaw * sinRoll + cosYaw * sinPitch * cosRoll) - sinLat * cosPitch * cosRoll;

        return Math.atan2(a_23, a_33);
    }

}
