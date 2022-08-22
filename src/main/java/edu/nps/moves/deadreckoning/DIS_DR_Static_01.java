package edu.nps.moves.deadreckoning;

/**
 * (PRIMARY Methods group) Static DR, no movement || No motion
 * @author Sheldon L. Snyder
 * @deprecated Use {@link DeadReckoner} instead.
 */
@Deprecated(since = "5.1", forRemoval = true)
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
