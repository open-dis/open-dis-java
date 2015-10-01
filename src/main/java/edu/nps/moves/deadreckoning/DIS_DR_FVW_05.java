package edu.nps.moves.deadreckoning;

import edu.nps.moves.deadreckoning.utils.*;

/**
 * (PRIMARY Methods group) Fixed, rate of velocity, world coordinates || Linear Motion with 
 * Acceleration but no rotation
 * 
 * @author Sheldon L. Snyder
 */
public class DIS_DR_FVW_05 extends DIS_DeadReckoning
{
    /**
     * The driver for a DIS_DR_FVW_05 DR algorithm from the Runnable interface
     * <p>
     * Linear motion with acceleration without rotation
     */
    public void run()
    {
        while(true)
        {
            deltaCt++;   
            try
            {    Thread.sleep(stall);    }catch (Exception e){}     
            
            entityLocation_X += (entityLinearVelocity_X * changeDelta) + (.5 * entityLinearAcceleration_X * changeDelta * changeDelta);
            entityLocation_Y += (entityLinearVelocity_Y * changeDelta) + (.5 * entityLinearAcceleration_Y * changeDelta * changeDelta);
            entityLocation_Z += (entityLinearVelocity_Z * changeDelta) + (.5 * entityLinearAcceleration_Z * changeDelta * changeDelta);
        }
    }//run()--------------------------------------------------------------------
}
