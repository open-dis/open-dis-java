package edu.nps.moves.deadreckoning;

import edu.nps.moves.deadreckoning.utils.*;

/**
 * (PRIMARY Methods group) Rotating, rate of velocity, world coordinates || Linear Motion with 
 * Acceleration and rotation (Alterntive non-IEEE)
 * 
 * @author Sheldon L. Snyder
 */
public class DIS_DR_RVW_04b extends DIS_DeadReckoning
{
    
    /**
     * The driver for a DIS_DR_RVW_04 DR algorithm from the Runnable interface
     * <p>
     * Rotation and linear motion with acceleration
     */
    public void run()
    {
        try
        {
            while(true)
            {
                deltaCt++;
                Thread.sleep(stall);    

                entityLocation_X += (entityLinearVelocity_X * changeDelta) + (.5 * entityLinearAcceleration_X * changeDelta * changeDelta);
                entityLocation_Y += (entityLinearVelocity_Y * changeDelta) + (.5 * entityLinearAcceleration_Y * changeDelta * changeDelta);
                entityLocation_Z += (entityLinearVelocity_Z * changeDelta) + (.5 * entityLinearAcceleration_Z * changeDelta * changeDelta);
                
                entityOrientation_psi = (entityOrientation_psi + entityAngularVelocity_X * changeDelta) % (2*myPI);
                entityOrientation_theta = (entityOrientation_theta + entityAngularVelocity_Y * changeDelta) % (2*myPI);
                entityOrientation_phi = (entityOrientation_phi + entityAngularVelocity_Z * changeDelta) % (2*myPI);
            }//while(true)  
        }// try
        catch(Exception e)
        {   
            System.out.println(e);     
        }
    }//run()--------------------------------------------------------------------
}

