package edu.nps.moves.deadreckoning;

/**
 *
 * (PRIMARY Methods group) Fixed, Rate of Positon, World || Constant Linear motion
 * 
 * @author Sheldon L. Snyder
 */
public class DIS_DR_FPW_02 extends DIS_DeadReckoning
{

    /***************************************************************************
     * The driver for a DIS_DR_FPW_02 DR algorithm from the Runnable interface
     * <p>
     * Updates the position of this entity 
     * <P>
     * P_new = P_original + LinVel * delta * t
     * <p>
     * called by thread.start() in the super class
     * <p>
     * Velocity is the speed that a entity is moving...linear constant speed
     */
    public void run()
    {
        while(true)
        {
            try
            {    Thread.sleep(stall);    }catch (Exception e){}     
            update();
        }
    }//run()--------------------------------------------------------------------

    void update() {
        deltaCt++;   
        entityLocation_X += entityLinearVelocity_X * changeDelta;
        entityLocation_Y += entityLinearVelocity_Y * changeDelta;
        entityLocation_Z += entityLinearVelocity_Z * changeDelta;

    }
}