

package edu.nps.moves.spatial;

import SRM.*;

/**
 * Local coordinate system for an entity, eg relative to one UAV. This
 * is typically embedded in another coordinate system, such as a range
 * coordinate system. The origin of the local coordinate system should be
 * the center of mass of the entity. The u axis points out the front, v axis
 * out the right side of the entity, and the w axis down. The SRF is specified
 * via a point (in the reference frame of the parent) and two unit vectors
 * in the parent SRF, which are parallel to the entity u,v plane.  Directions, 
 * orientations, and vector
 * quantities are independent of the position of the lococenter WRT the
 * parent SRF; if you're concerned only with directions, orientations, and
 * vector quantities it doesn't matter where the origin is, so you can 
 * pick someplace handy, like the origin of the parent SRF.
 * 
 * @author DMcG
 */
public class EntityBodyCoordinates 
{
    /** body-centric coordinate system for the entity */
    SRF_LococentricEuclidean3D bodySRF;
    
    /** The coordinate system in which this body-centric coordinate system is embedded */
    BaseSRF_3D parentSRF;
    
    /** origin of body-centric coordinate system, in the parent SRF */
    Coord3D lococenter; 
    
   
    /** Create a new lococentric euclidian reference frame embedded in a parent SRF.
     * The origin of the lococentric coordinate system is specified, along with 
     * two unit vectors, parallel to the u and v axes.
     */
    public EntityBodyCoordinates(BaseSRF_3D parentSRF, 
                                 float x, float y, float z, // lococenter, in parent SRF
                                 float primaryDirectionX, float primaryDirectionY, float primaryDirectionZ, // Unit vector parallel to u axis
                                 float secondaryDirectionX, float secondaryDirectionY, float secondaryDirectionZ) // unit vector parallel to v axis
    {
        try
        {
            this.parentSRF = parentSRF;
            this.lococenter = parentSRF.createCoordinate3D(x, y, z); 
            
            // Unit vector along entity u axis in parent SRF
            double[] d1 = {primaryDirectionX, primaryDirectionY, primaryDirectionZ};
            Direction primaryAxisDirection = parentSRF.createDirection(lococenter, d1);
            
            // Unit vector along v axis in parent SRF
            double[] d2 = {secondaryDirectionX, secondaryDirectionY, secondaryDirectionZ};
            Direction secondaryAxisDirection = parentSRF.createDirection(lococenter, d2);
            
            this.bodySRF = parentSRF.createLococentricEuclidean3DSRF(lococenter, primaryAxisDirection, primaryAxisDirection);     
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
    
}
