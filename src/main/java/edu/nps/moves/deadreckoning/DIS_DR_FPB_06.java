package edu.nps.moves.deadreckoning;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * 
 * (SECONDARY Methods Group) Fixed, rate of position, body coordinates || 
 * Linear motion without rotation
 * 
 * @author Sheldon L. Snyder
 * @deprecated Use {@link DeadReckoner} instead.
 */
@Deprecated
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

    void update() {
        deltaCt++;
        // solve for the new position
        Vector3D velVec = new Vector3D(entityLinearVelocity_X, entityLinearVelocity_Y, entityLinearVelocity_Z);
        // For fixed (non-rotating), R1 = changeDelta * Identity
        Vector3D updated = initOrien.applyInverseTo(velVec.scalarMultiply(changeDelta));

        // set the new position...
        entityLocation_X += updated.getX();
        entityLocation_Y += updated.getY();
        entityLocation_Z += updated.getZ();
    }

}
