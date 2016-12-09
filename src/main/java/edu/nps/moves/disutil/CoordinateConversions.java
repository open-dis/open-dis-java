
package edu.nps.moves.disutil;

/**
 * Converts DIS (x,y,z) rectilinear coordinates (earth-centered RH coordinate system)
 * to latitude and longitude, in radians. 
 * @author loyaj
 */
public class CoordinateConversions
{
    public static final double RADIANS_TO_DEGREES = 180.0/Math.PI;
    public static final double DEGREES_TO_RADIANS = Math.PI/180.0;
    
    private CoordinateConversions()
    {
    }
    /**
     * Converts DIS xyz world coordinates to latitude and longitude (IN RADIANS). This algorithm may not be 100% accurate
     * near the poles. Uses WGS84 , though you can change the ellipsoid constants a and b if you want to use something
     * else. These formulas were obtained from Military Handbook 600008
     * @param xyz A double array with the x, y, and z coordinates, in that order.
     * @return An array with the lat, long, and elevation corresponding to those coordinates.
     * Elevation is in meters, lat and long are in radians
     */
    public static double[] xyzToLatLonRadians(double[] xyz)
    {
        double x = xyz[0];
        double y = xyz[1];
        double z = xyz[2];
        double answer[] = new double[3];
        double a = 6378137.0; //semi major axis
        double b = 6356752.3142; //semi minor axis

        double eSquared; //first eccentricity squared
        double rSubN; //radius of the curvature of the prime vertical
        double ePrimeSquared;//second eccentricity squared
        double W = Math.sqrt((x*x + y*y));
               
        eSquared = (a*a - b*b) / (a*a);
        ePrimeSquared = (a*a - b*b) / (b*b);

        /**
         * Get the longitude.
         */
        if(x >= 0 )
        {
            answer[1] = Math.atan(y/x);
        }
        else if(x < 0 && y >= 0)
        {
            answer[1] = Math.atan(y/x) + Math.PI;
        }
        else
        {
            answer[1] = Math.atan(y/x) - Math.PI;
        }

        /**
         * Longitude calculation done. Now calculate latitude.
         * NOTE: The handbook mentions using the calculated phi (latitude) value to recalculate B
         * using tan B = (1-f) tan phi and then performing the entire calculation again to get more accurate values.
         * However, for terrestrial applications, one iteration is accurate to .1 millimeter on the surface  of the
         * earth (Rapp, 1984, p.124), so one iteration is enough for our purposes
         */

        double tanBZero = (a*z) / (b * W);
        double BZero = Math.atan((tanBZero));
        double tanPhi = (z + (ePrimeSquared * b * (Math.pow(Math.sin(BZero), 3))) ) /(W - (a * eSquared * (Math.pow(Math.cos(BZero), 3))));
        double phi = Math.atan(tanPhi);
        answer[0] = phi;
        /**
         * Latitude done, now get the elevation. Note: The handbook states that near the poles, it is preferable to use
         * h = (Z / sin phi ) - rSubN + (eSquared * rSubN). Our applications are never near the poles, so this formula
         * was left unimplemented.
         */
        rSubN = (a*a) / Math.sqrt(((a*a) * (Math.cos(phi)*Math.cos(phi)) + ((b*b) * (Math.sin(phi)*Math.sin(phi)))));

        answer[2] = (W / Math.cos(phi)) - rSubN;

        return answer;
    }
    
    /**
     * Converts DIS xyz world coordinates to latitude and longitude (IN DEGREES). This algorithm may not be 100% accurate
     * near the poles. Uses WGS84 , though you can change the ellipsoid constants a and b if you want to use something
     * else. These formulas were obtained from Military Handbook 600008
     * @param xyz A double array with the x, y, and z coordinates, in that order.
     * @return An array with the lat, lon, and elevation corresponding to those coordinates.
     * Elevation is in meters, lat and long are in degrees
     */
    public static double[] xyzToLatLonDegrees(double[] xyz)
    {
        double degrees[] = CoordinateConversions.xyzToLatLonRadians(xyz);
        
        degrees[0] = degrees[0] * CoordinateConversions.RADIANS_TO_DEGREES;
        degrees[1] = degrees[1] * CoordinateConversions.RADIANS_TO_DEGREES;
        
        return degrees;

    }
    
    /**
     * Converts lat long and geodetic height (elevation) into DIS XYZ
     * This algorithm also uses the WGS84 ellipsoid, though you can change the values
     * of a and b for a different ellipsoid. Adapted from Military Handbook 600008
     * @param latitude The latitude, IN RADIANS
     * @param longitude The longitude, in RADIANS
     * @param height The elevation, in meters
     * @return a double array with the calculated X, Y, and Z values, in that order
     */
    public static double[] getXYZfromLatLonRadians(double latitude, double longitude, double height)
    {
        double a = 6378137.0; //semi major axis
        double b = 6356752.3142; //semi minor axis
        double cosLat = Math.cos(latitude);
        double sinLat = Math.sin(latitude);
		
		
        double rSubN = (a*a) / Math.sqrt(((a*a) * (cosLat*cosLat) + ((b*b) * (sinLat*sinLat))));
		
        double X = (rSubN + height) * cosLat * Math.cos(longitude);
        double Y = (rSubN + height) * cosLat * Math.sin(longitude);
        double Z = ((((b*b) / (a*a)) * rSubN) + height) * sinLat;
		
        return new double[] {X, Y, Z};
    }
    
    /**
     * Converts lat long IN DEGREES and geodetic height (elevation) into DIS XYZ
     * This algorithm also uses the WGS84 ellipsoid, though you can change the values
     * of a and b for a different ellipsoid. Adapted from Military Handbook 600008
     * @param latitude The latitude, IN DEGREES
     * @param longitude The longitude, in DEGREES
     * @param height The elevation, in meters
     * @return a double array with the calculated X, Y, and Z values, in that order
     */
    public static double[] getXYZfromLatLonDegrees(double latitude, double longitude, double height)
    {
        double degrees[] = CoordinateConversions.getXYZfromLatLonRadians(latitude * CoordinateConversions.DEGREES_TO_RADIANS, 
                                                                   longitude * CoordinateConversions.DEGREES_TO_RADIANS, 
                                                                   height);
        
        return degrees;
    }
}
