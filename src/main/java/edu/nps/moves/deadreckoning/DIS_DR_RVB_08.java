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
 * (SECONDARY Methods Group) Rotating, rate of velocity, body coordinates || 
 * Linear motion with Rotation
 * <p>
 * it is coded up, but the linear motion does not seem to work....rotation
 * works but linear motion fails...not sure why they are calculating the 
 * linear motion the way they are...
 *  <p>
 * The alogrithm is coded IAW IEEE 1278.1-1995 so perhaps it is a 
 * coordinate change of basis issue and since I am not working in both world
 * and body coordinates, it fails or limits to 0
 * 
 * @author Sheldon L. Snyder
 */
public class DIS_DR_RVB_08 extends DIS_DeadReckoning
{
    // put these in main abstract class...?
    RealMatrix DR;
    RealMatrix R1;
    RealMatrix R2;

    /**
     * The driver for a DIS_DR_RVB_08 DR algorithm from the Runnable interface
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

        RealVector velVec = MatrixUtils.createRealVector(new double[]
                {entityLinearVelocity_X, entityLinearVelocity_Y, entityLinearVelocity_Z});
        RealVector accVec = MatrixUtils.createRealVector(new double[]
                {entityLinearAcceleration_X, entityLinearAcceleration_Y, entityLinearAcceleration_Z});

        Rotation currOrien = new Rotation(
                RotationOrder.ZYX,
                RotationConvention.FRAME_TRANSFORM,
                entityOrientation_psi,
                entityOrientation_theta,
                entityOrientation_phi);

        // make the R1 and R2 matrices
        makeR1();
        makeR2();

        RealVector updated1 = R1.operate(velVec);
        RealVector updated2 = R2.operate(accVec);

        // add the R1 and R2
        // Solve for new position 
        Vector3D updated = currOrien.applyInverseTo(new Vector3D(updated1.add(updated2).toArray()));

        // Set the new position
        entityLocation_X += updated.getX();
        entityLocation_Y += updated.getY();
        entityLocation_Z += updated.getZ();                    

        entityLinearVelocity_X += entityLinearAcceleration_X * changeDelta;
        entityLinearVelocity_Y += entityLinearAcceleration_Y * changeDelta;
        entityLinearVelocity_Z += entityLinearAcceleration_Z * changeDelta;

        // make the rotation information updates...same as 1-4                
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
     */
    private void makeThisDR()
    {
        RealMatrix ident = MatrixUtils.createRealIdentityMatrix(3);  
        double wDelta = wMag * changeDelta * deltaCt;  
        double cosWdelta = Math.cos(wDelta);

        double wwScale = (1 - cosWdelta) / wSq; 
        double identScalar = cosWdelta;
        double skewScale = Math.sin(wDelta) / wMag;

        RealMatrix wwTmp = ww.scalarMultiply(wwScale);
        RealMatrix identTmp = ident.scalarMultiply(identScalar);
        RealMatrix skwTmp = skewOmega.scalarMultiply(skewScale);

        DR = wwTmp.add(identTmp);
        DR = DR.subtract(skwTmp);
    }


    /***************************************************************************
     * Makes the R2 vector
     * @return - the r2 vector
     */
    private void makeR2()
    {
        RealMatrix ident = MatrixUtils.createRealIdentityMatrix(3);  

        // common factors
        // double wDelta = wMag * changeDelta * deltaCt;  
        double wDelta = wMag * changeDelta;  

        // matrix scalars
        // double wwScale = .5*wSq*changeDelta * deltaCt*changeDelta * deltaCt;
        double wwScale = .5 * wSq * changeDelta * changeDelta;
        wwScale -= Math.cos(wDelta);
        wwScale -= wDelta*Math.sin(wDelta);
        wwScale++;
        wwScale /= wSq * wSq;

        double identScalar = Math.cos(wDelta) + wDelta*Math.sin(wDelta) - 1;
        identScalar /= wSq;

        double skewScale = Math.sin(wDelta) - wDelta*Math.cos(wDelta);
        skewScale /= wSq * wMag;

        // scaled matrixes
        RealMatrix wwTmp = ww.scalarMultiply(wwScale);
        RealMatrix identTmp = ident.scalarMultiply(identScalar);
        RealMatrix skwTmp = skewOmega.scalarMultiply(skewScale);

        R2 = wwTmp.add(identTmp);
        R2 = R2.add(skwTmp);
    }



    /***************************************************************************
     * Makes the R1 matrix
     * @return - the vector R1
     */
    private void makeR1()
    {
        RealMatrix ident = MatrixUtils.createRealIdentityMatrix(3);  

        // common factors
        // double wDelta = wMag * changeDelta * deltaCt;  
        double wDelta = wMag * changeDelta;  

        // matrix scalars
        double wwScale = (wDelta-Math.sin(wDelta)) / (wSq * wMag); 
        double identScalar = Math.sin(wDelta) / wMag;
        // NOTE: corrected from the SISO std, which contained an error. See
        // http://discussions.sisostds.org/default.asp?action=9&read=44981&fid=32
        // Thanks to Ian Gilles for the correction.
        double skewScale = (1 - Math.cos(wDelta)) / wSq;

        // scaled matrixes
        RealMatrix wwTmp = ww.scalarMultiply(wwScale);
        RealMatrix identTmp = ident.scalarMultiply(identScalar);
        RealMatrix skwTmp = skewOmega.scalarMultiply(skewScale);

        R1 = wwTmp.add(identTmp);
        R1 = R1.add(skwTmp);
    }    

}
