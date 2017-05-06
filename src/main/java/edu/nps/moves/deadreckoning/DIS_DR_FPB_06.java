package edu.nps.moves.deadreckoning;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import edu.nps.moves.deadreckoning.utils.*;

/**
 * 
 * (SECONDARY Methods Group) Fixed, rate of position, body coordinates || 
 * Linear motion without rotation
 * <p>
 * it is coded up, but the linear motion does not seem to work....
 * <p>
 * The alogrithm is coded IAW IEEE 1278.1-1995 so perhaps it is a 
 * coordinate change of basis issue and since I am not working in both world
 * and body coordinates, it fails or limits to 0
 * 
 * @author Sheldon L. Snyder
 */
public class DIS_DR_FPB_06 extends DIS_DeadReckoning
{     
    /**
     * The driver for a DIS_DR_FPB_06 DR algorithm from the Runnable interface
     * <p>
     * linear motion only without rotation
     */
    public void run()
    {
        try
        {            
            while(true)
            {
                Thread.sleep(stall);   
                update();
            }//while(true)  
        }// try
        catch(Exception e)
        {   
            System.out.println(e);     
        }
    }//run()--------------------------------------------------------------------

    void update() throws MatrixException, Exception {
        deltaCt++;
        // solve for the new position
        Vector3D velVec = new Vector3D(entityLinearVelocity_X, entityLinearVelocity_Y, entityLinearVelocity_Z);
        Vector3D updated = initOrien.applyInverseTo(velVec.scalarMultiply(changeDelta));

        // set the new position...
        entityLocation_X += updated.getX();
        entityLocation_Y += updated.getY();
        entityLocation_Z += updated.getZ();
    }

}