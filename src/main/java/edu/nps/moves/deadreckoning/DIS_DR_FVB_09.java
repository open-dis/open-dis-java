package edu.nps.moves.deadreckoning;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * (SECONDARY Methods Group) Fixed, rate of velocity, body coordinates || 
 * Linear Motion without Rotation
 *  
 * @author Sheldon L. Snyder
 * @deprecated Use {@link DeadReckoner} instead.
 */
@Deprecated
public class DIS_DR_FVB_09 extends DIS_DeadReckoning
{

    /**
     * The driver for a DIS_DR_FVB_09 DR algorithm from the Runnable interface
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

        Vector3D velVec = new Vector3D(entityLinearVelocity_X,     entityLinearVelocity_Y,     entityLinearVelocity_Z);
        Vector3D accVec = new Vector3D(entityLinearAcceleration_X, entityLinearAcceleration_Y, entityLinearAcceleration_Z);

        // add the R1 and R2
        // Solve for new position 
        // For fixed (non-rotating), R1 = changeDelta * Identity and R2 = 0.5 * changeDelta^2 * Identity
        Vector3D updated = initOrien.applyInverseTo(velVec.add(0.5 * changeDelta, accVec).scalarMultiply(changeDelta));

        // Set the new position
        entityLocation_X += updated.getX();
        entityLocation_Y += updated.getY();
        entityLocation_Z += updated.getZ();                    

        entityLinearVelocity_X += entityLinearAcceleration_X * changeDelta;
        entityLinearVelocity_Y += entityLinearAcceleration_Y * changeDelta;
        entityLinearVelocity_Z += entityLinearAcceleration_Z * changeDelta;
    }

}
