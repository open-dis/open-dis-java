package edu.nps.moves.deadreckoning;

import edu.nps.moves.deadreckoning.utils.*;


/**
 * (PRIMARY Methods group) Rotating, rate of position, world coordinates || Constant Linear motion with
 * Rotation (Alternative non-IEEE)
 * <p>
 * Doesn't follow the IEEE standard algorithms
 * <p>
 * Takes a more straightforward approach to rotation
 * 
 * @author Sheldon L. Snyder
 */
public class DIS_DR_RPW_03b extends DIS_DeadReckoning
{

    /**
     * The driver for a DIS_DR_RPW_03b DR algorithm from the Runnable interface
     * <p>
     * Rotation and linear motion
     */
    public void run()
    {        
        try
        {
            while(true)
            {
                deltaCt++;
                Thread.sleep(stall);    

                entityLocation_X += entityLinearVelocity_X * changeDelta;
                entityLocation_Y += entityLinearVelocity_Y * changeDelta;
                entityLocation_Z += entityLinearVelocity_Z * changeDelta;
                
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

