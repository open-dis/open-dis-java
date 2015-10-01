package edu.nps.moves.spatial;

import SRM.*;  // Sedris spatial reference model version 4.4
import edu.nps.moves.dis.*;

/**
 * Represents a local, flat range area with Euclidian coordinates, which is convienient for somewhat small
 * simulated areas. This class assumes a local, flat, coordinate system
 * with an origin at (lat, lon, altitude) and positive X pointing local east, positive Y
 * pointing local north, and positive Z pointing up. Specified in WGS_84 geodesic
 * coordinate system. Altitude is distance above the ellipsoid.<p>
 *
 * The coordinate system has its origin at the given (lat, lon) and creates a
 * plane tangent and normal to the ellipsoid at that point. <p>
 *
 * There are several major reference frames that may be useful in various contexts:<p>
 *
 * Geocentric: Origin at the center of the earth. Positive X out at the intersection
 * of the equator and prime meridian, Y out at 90 deg east lon, and Z up through
 * the north pole. This is the coordinate system used by DIS world coordinates.<p>
 *
 * Geodetic: The coordinate system uses lat/lon/altitude. This is handy for positioning
 * an object on the earth (or close to it) but not so handy for describing things
 * like velocity.<p>
 *
 * Local Tangent Surface Euclidian (LTSE): Pick a lat/lon/altitude, and then at
 * that point you can define a single plane normal and tangent to the globe. Positive X points
 * local east, positive Y points local north, and positive Z points local up. This
 * is handy for describing the position of an object in, for example, a range of
 * somewhat small dimensions, perhaps 20KM X 20KM, where we don't want to get sucked
 * into the whole curved earth scene and just want to be simple.<p>
 *
 * Body Centric/Lococentric/Platform-centric: The origin is at the volumentric center
 * of an entity (in DIS); Positive
 * x points out the long axis, positive Y points to the right, and positive Z points
 * down. This is widely used to describe (roll, pitch, yaw) in aircraft. Note that you
 * need a transform from (for example) the LTSE to body coordinates to define the
 * position of the body axis origin and orientation WRT the LTSE origin.  Note that
 * the direction of the Z axis is the opposite of that used by LTSE. The axes are
 * often named (u,v,w) in this frame of reference. <p>
 *
 * We can also convert between these coordinate systems using standard libraries
 * in the SRM. <p>
 *
 * See User’s Manual for SRM Orientation, Velocity, & Acceleration
 * Transformations Version 2.0, 18 Nov 2009, available with the
 * sedris Java SDK download.
 * 
 * @author DMcG
 */
public class RangeCoordinates
{
    /** A reference frame for the earth's surface, ie an ellipsoid with coordinates
     * of the form (lat, lon, altitude). The technical term for this would be
     * geodetic.
     */
    SRF_Celestiodetic earthSurfaceReferenceFrame;
    Coord3D earthSurfaceReferenceFrameOrigin;

    /** A DIS reference frame, with a euclidian coordinate system with origin
     * at the center of of the earth. Coordinates, in (x, y, z), in meters. This
     * is the reference frame used by many DIS fields on the wire. The technical
     * term for this would be geocentric. Z is through the north pole, x out
     * the prime meridian at the equator, and y out the equator at 90 deg east.
     */
    SRF_Celestiocentric disCoordinateReferenceFrame;

    /** A local, flat, euclidian reference frame. This is tangent to a (lat, lon, altitudeOrigin)
     * on an earth that is supplied by the user in the constructor.
     * This allows users to set up a local, relatively small area
     * for moving things around without in the nuisance of worrying about curved
     * earth. The technical term for this would be Local Tangent Euclidian, ie
     * a plane tangent to the earth at the given (lat, lon, alt). Coordinate system
     * is x east, y north, z up.
     */
    SRF_LocalTangentSpaceEuclidean localTangentSurfaceReferenceFrame;

    /** The origin of the local euclidian reference frame, in sedris data structure */
    Coord3D localEuclidianOrigin;

    /** The latitude and longitude of the local, flat, euclidian coordinate system origin */
    double latitudeOrigin, longitudeOrigin;
    
    /** The altitude of the local coordinate system origin, ie the distance
     * above the ellipsoid, not distance above terrain
     */
    double altitudeOrigin;

    /**
     * Constructor for a local flat coordinate system. Takes the latitude and
     * longitude (in degrees) for WGS_84 and the height above the ellipsoid
     * and creates a local, flat coordinate system at that point.<p>
     * 
     * @param originLat Origin of the flat local coordinate system, in degrees, latitude
     * @param originLon Origin of the flat local coordinate system, in degrees, longitude
     * @param heightOffset altitudeOrigin above ellipsoid surface, in meters
     */
    public RangeCoordinates(double originLat, double originLon, double heightOffset)
    {
        latitudeOrigin = originLat;
        longitudeOrigin = originLon;
        altitudeOrigin = heightOffset;
        try
        {
            // Create a Celestiodetic SRF with WGS 1984, ie a curved coordinate
            // system (lat/lon/alt)
             earthSurfaceReferenceFrame = new SRF_Celestiodetic(SRM_ORM_Code.ORMCOD_WGS_1984,
                                           SRM_RT_Code.RTCOD_WGS_1984_IDENTITY);

            // Create a Celesticentric SRF with WGS 1984, ie a rectilinear,
            // earth-centered coordinate system as used in DIS
            disCoordinateReferenceFrame = new SRF_Celestiocentric(SRM_ORM_Code.ORMCOD_WGS_1984,
                                            SRM_RT_Code.RTCOD_WGS_1984_IDENTITY);

            double latInRadians = Math.toRadians(originLat);
            double lonInRadians = Math.toRadians(originLon);

            // Reference system for a local tangent euclidian space plane, tangent to the lat/lon
            // at a give altitude.
            localTangentSurfaceReferenceFrame =
                    new SRF_LocalTangentSpaceEuclidean(SRM_ORM_Code.ORMCOD_WGS_1984,
                    SRM_RT_Code.RTCOD_WGS_1984_IDENTITY,
                    lonInRadians, latInRadians,  // Origin (note: lon, lat)
                    0.0,            // Azimuth; can rotate axis, but don't.
                    0.0, 0.0,       // False x,y origin (can offset origin to avoid negative coordinates)
                    heightOffset);  // Height offset

            // It's handy to have this pre-created in some calculations
            localEuclidianOrigin = localTangentSurfaceReferenceFrame.createCoordinate3D(0.0, 0.0, 0.0);
            earthSurfaceReferenceFrameOrigin = earthSurfaceReferenceFrame.createCoordinate3D(latInRadians, lonInRadians, heightOffset);
        }
        catch(Exception e)
        {
            System.out.println("problem creating coordinate systems" + e);
        }

    }
    
    /** Changes a Vector3Double from the local coordinate system (flat, euclidian,
     * orgin given at (lat, lon, alt)) to a global, DIS, earth-centric coordinate
     * system. Overwrites the values currently in Vector3Double passed in.
     * 
     * @param localCoordinates Position in local euclidian coordinate system. Values are overwritten to the DIS earth-centric coordinate system on return
     */
    public void changeVectorToDisCoordFromLocalFlat(Vector3Double localCoordinates)
    {
        Vector3Double vec = this.DISCoordFromLocalFlat(localCoordinates.getX(), 
                localCoordinates.getY(), 
                localCoordinates.getZ());
        localCoordinates.setX(vec.getX());
        localCoordinates.setY(vec.getY());
        localCoordinates.setZ(vec.getZ());
    }

    /**
     * Transform from local, flat coordinate system to the DIS coordinate system.
     * All units in meters, positive x east, y north, z altitude.<p>
     * 
     * @param x x coordinate in local, flat coordinate system
     * @param y y coordinate in meters in local, flat coordinate system
     * @param z z coordinate, altitude, in meters in local flat coordinate system
     */
    public Vector3Double DISCoordFromLocalFlat(double x, double y, double z)
    {
        Vector3Double disCoordinates = new Vector3Double();
        try
        {
            // Holds coordinates in the local tangent plane
            Coord3D localCoordinates = localTangentSurfaceReferenceFrame.createCoordinate3D(x, y, z);
            // holds coordinates in the DIS (geocentric) coordinate frame
            Coord3D disCoord = disCoordinateReferenceFrame.createCoordinate3D(0.0, 0.0, 0.0);

            SRM_Coordinate_Valid_Region_Code region = disCoordinateReferenceFrame.changeCoordinateSRF(localCoordinates, disCoord);

            //System.out.println(region);

            // convert from the local tanget plane coordinates to the DIS coordinate frame
            double values[] = disCoordinateReferenceFrame.getCoordinate3DValues(disCoord);
            //System.out.println("DIS x:" + values[0] + " y:" + values[1] + " z:" + values[2] );

            // Set the values in the return object
            disCoordinates.setX(values[0]);
            disCoordinates.setY(values[1]);
            disCoordinates.setZ(values[2]);
        }
        catch(Exception e)
        {
            //Should throw exception here
            System.out.println("can't change to DIS coord " + e);
            return null;
        }

        return disCoordinates;
    }

    /** 
     * Changes the world-coordinates vector3double to the local euclidian flat
     * coordinate system. Overwrites the values in worldCoordinates.
     * 
     * @param worldCoordinates 
     */
   public void changeVectorToLocalCoordFromDIS(Vector3Double worldCoordinates)
   {
       Vector3Double vec = this.localCoordFromDis(worldCoordinates.getX(), 
               worldCoordinates.getY(), 
               worldCoordinates.getZ());
       worldCoordinates.setX(vec.getX());
       worldCoordinates.setY(vec.getY());
       worldCoordinates.setZ(vec.getZ());
   }
    
    /**
     * Given DIS coordinates, convert to the local euclidian plane coordinates.
     * 
     * @param x
     * @param y
     * @param z
     */
    public Vector3Double localCoordFromDis(double x, double y, double z)
    {
        Vector3Double local = new Vector3Double();
        
        try
        {
            // Holds position in the local tangent plane
            Coord3D localCoordinates = localTangentSurfaceReferenceFrame.createCoordinate3D(0.0, 0.0, 0.0);
            // Holds position in the DIS reference frame
            Coord3D disCoord = disCoordinateReferenceFrame.createCoordinate3D(x, y, z);

            SRM_Coordinate_Valid_Region_Code region = localTangentSurfaceReferenceFrame.changeCoordinateSRF(disCoord, localCoordinates);

            //System.out.println("Region:" + region);

            // Get the position in the local tangent place (ie, range coordinates) from DIS coordinates (ie, geocentric)
            double values[] = localTangentSurfaceReferenceFrame.getCoordinate3DValues(localCoordinates);
            //System.out.println("-->Local x:" + values[0] + " y:" + values[1] + " z:" + values[2] );
            local.setX(values[0]);
            local.setY(values[1]);
            local.setZ(values[2]);
        }
        catch(Exception e)
        {
            System.out.println("can't change from DIS coord to Local" + e);
            return null;
        }

        return local;
    }

    /**
     * Converts a roll, pitch, and heading/yaw in the local flat coordinate system to DIS euler
     * angles. Input orientation is in units of radians. DIS uses euler angles to describe
     * the orientation of an object, using an earth-centered coordinate system, with
     * successive rotations about the original x, y, and z axes. You need to be careful
     * here because there are all sorts of conventions for "euler angles" including
     * the order in which the axes are rotated about. <p>
     *
     * phi = roll, theta = pitch, psi = yaw/heading<p>, by one popular convention.
     * All units are in radians.<p>
     *
     * Note that we also need the postion of the object in the local coordinate system.
     * The DIS euler angles will vary depending on not just the roll/pitch/heading,
     * but also where in the local coordinate frame the object is. Also, the pitch/roll/heading
     * are in the local coordinate system, NOT the coordinate system of the object.<p>
     * 
     * @param pitchRollHeading
     */
    public edu.nps.moves.dis.Orientation localRollPitchHeadingToDisEuler(edu.nps.moves.dis.Orientation pitchRollHeading,
                                                edu.nps.moves.dis.Vector3Double localPosition)
    {
        // Tait-Bryan angles is jargon/correct terminology for the roll, pitch, and yaw. It refers
        // to rotation about the original x, y, and z axes of the reference frame in use; it's also
        // called cardano angles or tait-bryan. DIS uses rotation about the earth-centric origin. In
        // a body-centric reference frame we have roll, pitch, and yaw. In the local frame of reference
        // it is rotation about x (phi), y (theta) and z (psi).

        // Recall that the local frame of reference has X pointing east, y pointing north, and
        // z pointing up. We are NOT using a body-centric reference frame here; we are using the
        // range reference frame.

        try
        {
            // DIS euler angles in open-dis object. This is returned by the method.
            edu.nps.moves.dis.Orientation openDisOrientation = new edu.nps.moves.dis.Orientation();

            // Local coordinate system orientation, in tait-bryan 
            OrientationTaitBryanAngles localOrientation =
                    new OrientationTaitBryanAngles(pitchRollHeading.getPhi(),
                                                   pitchRollHeading.getTheta(),
                                                   pitchRollHeading.getPsi());

            // Local frame of reference position
            Coord3D localSRMPosition = localTangentSurfaceReferenceFrame.createCoordinate3D(localPosition.getX(), localPosition.getY(), localPosition.getZ());

            // DIS frame of reference position
            Vector3Double disLocation = this.DISCoordFromLocalFlat(localPosition.getX(), localPosition.getY(), localPosition.getZ());
            //Coord3D disCoord = disCoordinateFrame.createCoordinate3D(disLocation.getX(), disLocation.getY(), disLocation.getZ());
            Coord3D disCoord = disCoordinateReferenceFrame.createCoordinate3D(6378137.0, 0.0, 0.0);
            //double[] pos = disCoord.getValues();
           // System.out.println("DIS position for orienation change:" + pos[0] + "," + pos[1] + "," + pos[2]);

            // An empty object passed into the method call below. When returned it's filled out with
            // the DIS euler angles.
            SRM.Orientation disOrientation = new OrientationTaitBryanAngles();
            
                                                   
            disCoordinateReferenceFrame.transformOrientation(localSRMPosition,    // INPUT: Local coordinate frame location
                                                    localOrientation,    // INPUT: Local coordinate frame orientation (roll, pitch, heading)
                                                    disCoord,            // INPUT: DIS coordinate frame location
                                                    disOrientation);     // Output: DIS orientation in euler angles (xyz/Tait-Bryan)

            SRM_Tait_Bryan_Angles_Params tait = disOrientation.getTaitBryanAngles();

            openDisOrientation.setPhi((float)tait.pitch);
            openDisOrientation.setTheta((float)tait.roll);
            openDisOrientation.setPsi((float)tait.yaw);

            System.out.println("disOrientation:" + disOrientation.getTaitBryanAngles());
            System.out.println("DIS coordinates:" + disCoord);
            
            return openDisOrientation;

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        return null;

    }

    public void c(double lat, double lon, double alt,
            double bank, double pitch, double head)
    {
        try
        {
            // The geocentric aircraft location
               Coord3D gd_coord = earthSurfaceReferenceFrame.createCoordinate3D(Math.toRadians(lon), Math.toRadians(lat), alt);
		//The geocentric location to be computed.
               Coord3D gc_coord = disCoordinateReferenceFrame.createCoordinate3D(0.0, 0.0, 0.0);
               disCoordinateReferenceFrame.changeCoordinate3DSRF(gd_coord, gc_coord);

               System.out.println("Geocentric coordinates for location " + lat + "," + lon + " :" + gc_coord);

               // Create Space-fixed Entity-to-NED Tait-Bryan Orientation ...
               OrientationTaitBryanAngles tbE2NED_ori = new OrientationTaitBryanAngles(Math.toRadians(bank),
                                                                Math.toRadians(pitch),
                                                                Math.toRadians(head));  //AirCraft

               System.out.println("Orientation for bank, pitch, heading:" + tbE2NED_ori);

                // Transform GC Identity to GD to get the World-to-LTSE matrix
               OrientationMatrix mW2LTSE_ori = new OrientationMatrix(1,0,0, 0,1,0, 0,0,1); //result target
               earthSurfaceReferenceFrame.transformOrientation(gc_coord, mW2LTSE_ori, gd_coord, mW2LTSE_ori);

               OrientationMatrix mNED2LTSE_ori = new OrientationMatrix(0,1,0,   1,0,0,    0,0,-1);

               // The DIS orientation is the composition of 3 orientations: tbE2NED_ori  and mNED2LTSE_ori and  mNED2LTP_ori
		//  Let tbDIS_ori =( tbE2NED_ori o mNED2LTSE_ori o mNED2LTP_ori )

               System.out.println("tbE2NED_ori:" + tbE2NED_ori);
               System.out.println("tbE2NED_ori:" + tbE2NED_ori);
               System.out.println("mW2LTSE_ori:" + mW2LTSE_ori);
               //OrientationTaitBryanAngles
               //        tbDIS_ori = new OrientationTaitBryanAngles(tbE2NED_ori.composeWith(tbE2NED_ori.composeWith(mW2LTSE_ori)).getTaitBryanAngles());

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void change(double localX, double localY, double localZ,
            double bank, double noseUp, double bearing)
    {

        // bearing, in degrees, clockwise from true north is positive
        // noseUp, in degrees, angle at which the body is WRT the local flat plane, zero is level, positive is up angle
        // bank, degrees from level, positive is right bank
        // bearing to LTSE: 360 - bearing - 270 = yaw in LTSE
        // noseUp in LTSE: 0 + positive up angle from horizon
        // roll in ltse: -180 + bank angle
        
        try
        {
            Coord3D localLTSEPosition = localTangentSurfaceReferenceFrame.createCoordinate3D(localZ, localY, localZ);
            Coord3D localLTSEOrigin = localTangentSurfaceReferenceFrame.createCoordinate3D(0.0, 0.0, 0.0);
            Coord3D disPosition = disCoordinateReferenceFrame.createCoordinate3D();
            Coord3D disOrigin = disCoordinateReferenceFrame.createCoordinate3D();
            //Coord3D geodeticPosition = earthSurfaceReferenceFrame.createCoordinate3D(latitudeOrigin, longitudeOrigin, altitudeOrigin);

            // changes the contents of disPosition to reflect the geocentric coordinates of the LTSE position
            SRM_Coordinate_Valid_Region_Code region = disCoordinateReferenceFrame.changeCoordinateSRF(localLTSEPosition, disPosition);
            disCoordinateReferenceFrame.changeCoordinateSRF(localLTSEOrigin, disOrigin);

            //double values[] = disCoordinateReferenceFrame.getCoordinate3DValues(disPosition);
            System.out.println("DIS position, should be equal to DIS coords for LTSE orig:" + disPosition);
            System.out.println("ORigin of LTSE in DIS coordinates:" + disOrigin);

            //disCoordinateReferenceFrame.changeCoordinateSRF(geodeticPosition, disPosition);
            //disCoordinateReferenceFrame.getCoordinate3DValues(disPosition);
            //System.out.println("Geodetic position:" + geodeticPosition);

 

           OrientationTaitBryanAngles taitBryanOrientation =
                   new OrientationTaitBryanAngles (Math.toRadians(-180.0 + bank),
                                                   Math.toRadians(noseUp),
                                                   Math.toRadians(360.0 - bearing -270.0));
           System.out.println("tb orientation:" + taitBryanOrientation);

           // Will hold output, the orientation in DIS reference frame
           OrientationTaitBryanAngles taitBryanDis = new OrientationTaitBryanAngles();

           localTangentSurfaceReferenceFrame.transformOrientation(disOrigin, // position of object in DIS
                                                            taitBryanOrientation, // Orientation of body, wrt TB->LTSE
                                                            localLTSEPosition, // Position, in LTSE coordinates
                                                            taitBryanDis); // output: the orientation in DIS ref frame
           System.out.println("orientation in DIS:" + taitBryanDis.toString());


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
 
    }

    public static void main(String args[])
    {


        // x-axis intercept: prime meridian, equator, and zero altitude.
        RangeCoordinates primeMeridian = new RangeCoordinates(0.0, 0.0, 0.0);
        primeMeridian.DISCoordFromLocalFlat(0.0, 0.0, 0.0);
        primeMeridian.c(0.0, 0.0, 0.0, // lat lon alt
                0.0, // bank angle
                0.0, // noseup angle
                180.0); //bearing

        // North pole: z-axis intercept with earth surface
        RangeCoordinates northPole = new RangeCoordinates(0.0, 180.0, 0.0); // north pole
        northPole.DISCoordFromLocalFlat(0.0, 0.0, 0.0);

        // y-axis: equator, 90 deg east. x and z should be near-zero
        RangeCoordinates yAxis = new RangeCoordinates(90.0, 0.0, 0.0); // y axis
        yAxis.DISCoordFromLocalFlat(0.0, 0.0, 0.0);

        // Move west a bit from the equator/prime meridian
        RangeCoordinates westALittle = new RangeCoordinates(0.0, -1.0, 0.0);
        westALittle.DISCoordFromLocalFlat(0.0, 0.0, 0.0);
    }

  public SRF_LococentricEuclidean3D getPlatformReferenceFrame(Vector3Double rangePositionCoordinates)
    {
      try
      {
          // The x,y,z location of the platform in range coordinates (ie, the LTSE).
        Coord3D lococenter = localTangentSurfaceReferenceFrame.createCoordinate3D(rangePositionCoordinates.getX(),
                rangePositionCoordinates.getY(),
                rangePositionCoordinates.getZ());

        // We also need two unit vectors to describe the orientation of the platform in the LTSE

        // primary axis direction--x, along the long axis of the platform
        double[] orientationX = new double[3];

        Direction xAxis = localTangentSurfaceReferenceFrame.createDirection(localEuclidianOrigin, orientationX);
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
      return null;
  }
}
