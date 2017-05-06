package edu.nps.moves.deadreckoning;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.RotationOrder;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
/**
 * (PRIMARY Methods group) Rotating, rate of velocity, world coordinates || Linear Motion with 
 * Acceleration and rotation
 * 
 * @author Sheldon L. Snyder
 */
public class DIS_DR_RVW_04 extends DIS_DeadReckoning
{
    // put these in main abstract class...?
    RealMatrix DR;

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
                Thread.sleep(stall);    
                update();
            }//while(true)  
        }// try
        catch(Exception e)
        {   
            System.out.println(e);     
        }
    }//run()--------------------------------------------------------------------

    void update() throws Exception {
        deltaCt++;
        entityLocation_X += (entityLinearVelocity_X * changeDelta) + (.5 * entityLinearAcceleration_X * changeDelta * changeDelta);
        entityLocation_Y += (entityLinearVelocity_Y * changeDelta) + (.5 * entityLinearAcceleration_Y * changeDelta * changeDelta);
        entityLocation_Z += (entityLinearVelocity_Z * changeDelta) + (.5 * entityLinearAcceleration_Z * changeDelta * changeDelta);

        makeThisDR();

        Rotation DRR = new Rotation(DR.getData(), 1e-15).applyTo(initOrien);

        double[] eulerAngles = DRR.getAngles(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM);

        entityOrientation_theta = (float) eulerAngles[1];   
        //System.out.println(entityOrientation_theta);
        entityOrientation_psi = (float) eulerAngles[0];
        entityOrientation_phi = (float) eulerAngles[2];

        if(Double.isNaN(entityOrientation_psi))
            entityOrientation_psi = 0;
        if(Double.isNaN(entityOrientation_theta))
            entityOrientation_theta = 0;
        if(Double.isNaN(entityOrientation_phi))
            entityOrientation_phi = 0;
    }

    /***************************************************************************
     * Makes this iterations DR matrix
     * @throws java.lang.Exception
     */
    private void makeThisDR() throws Exception
    {
        double wDelta = wMag * changeDelta * deltaCt;  
        double cosWdelta = Math.cos(wDelta);

        double wwScale = (1 - cosWdelta) / wSq; 
        double identScalar = cosWdelta;
        double skewScale = Math.sin(wDelta) / wMag;

        RealMatrix wwTmp = ww.scalarMultiply(wwScale);
        RealMatrix identTmp = MatrixUtils.createRealIdentityMatrix(3).scalarMultiply(identScalar);
        RealMatrix skwTmp = skewOmega.scalarMultiply(skewScale);

        DR = wwTmp.add(identTmp);
        DR = DR.subtract(skwTmp);
    }//makeThisDR() throws Exception--------------------------------------------

}