package edu.nps.moves.deadreckoning;

/**
 * (PRIMARY Methods group) Static DR, no movement || No motion
 * @author Sheldon L. Snyder
 */
public class DIS_DR_Static_01 extends DIS_DeadReckoning
{
    /**
     * The driver for a DIS_DR_Static_01 DR algorithm from the Runnable interface
     * <p>
     * No motion or rotation
     */
    public void run()    {    }    

    void update() {
        deltaCt++;   
    }
}
