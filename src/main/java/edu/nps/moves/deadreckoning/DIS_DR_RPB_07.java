package edu.nps.moves.deadreckoning;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.RotationOrder;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * 
 * (SECONDARY Methods Group) Rotating, rate of position, body coordinates || 
 * Linear motion with Rotation
 * 
 * @author Sheldon L. Snyder
 */
public class DIS_DR_RPB_07 extends DIS_DeadReckoning
{
    RealMatrix DR;
    RealMatrix R1;

    /**
     * The driver for a DIS_DR_RPB_07 DR algorithm from the Runnable interface
     * <p>
     * linear motion and rotation
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
        makeR1();

        RealVector velVec = MatrixUtils.createRealVector(new double[]
                {entityLinearVelocity_X, entityLinearVelocity_Y, entityLinearVelocity_Z});
        Rotation currOrien = new Rotation(
                RotationOrder.ZYX,
                RotationConvention.FRAME_TRANSFORM,
                entityOrientation_psi,
                entityOrientation_theta,
                entityOrientation_phi);
        Vector3D updated = currOrien.applyInverseTo(new Vector3D(R1.operate(velVec).toArray()));

        // set new positons
        entityLocation_X += updated.getX();
        entityLocation_Y += updated.getY();
        entityLocation_Z += updated.getZ();                

        // make the rotation same as 1-4 rotations
        makeThisDR();                
        Rotation DRR = new Rotation(DR.getData(), 1e-15).applyTo(initOrien);

        double[] eulerAngles = DRR.getAngles(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM);
        // Update ESPDU Euler angle values

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
     */
    private void makeThisDR()
    {
        if (wMag < MIN_ROTATION_RATE) {
            DR = MatrixUtils.createRealIdentityMatrix(3);
            return;
        }
        
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
    }


    /***************************************************************************
     * Makes the R1 matrix
     * @return - the vector R1
     */
    private void makeR1()
    {
        if (wMag < MIN_ROTATION_RATE) {
            R1 = MatrixUtils.createRealIdentityMatrix(3).scalarMultiply(changeDelta * deltaCt);
            return;
        }
        
        RealMatrix ident = MatrixUtils.createRealIdentityMatrix(3);  

        // common factors
        // double wDelta = wMag * changeDelta * deltaCt;  
        double wDelta = wMag * changeDelta;  

        // matrix scalars
        double wwScale = (wDelta-Math.sin(wDelta)) / (wSq * wMag); 
        double identScalar = Math.sin(wDelta) / wMag;
        double skewScale = (1.0 - Math.cos(wDelta)) / wSq;

        // scaled matrixes
        RealMatrix wwTmp = ww.scalarMultiply(wwScale);
        RealMatrix identTmp = ident.scalarMultiply(identScalar);
        RealMatrix skwTmp = skewOmega.scalarMultiply(skewScale);

        R1 = wwTmp.add(identTmp);
        R1 = R1.add(skwTmp);
    }


}
