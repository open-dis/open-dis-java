package edu.nps.moves.deadreckoning;

import edu.nps.moves.deadreckoning.utils.*;

/**
 * (SECONDARY Methods Group) Fixed, rate of velocity, body coordinates || 
 * Linear Motion with Rotation
 * <p>
 * it is coded up, but the linear motion does not seem to work....
 * <p>
 * The alogrithm is coded IAW IEEE 1278.1-1995 so perhaps it is a 
 * coordinate change of basis issue and since I am not working in both world
 * and body coordinates, it fails or limits to 0
 *  
 * @author Sheldon L. Snyder
 */
public class DIS_DR_FVB_09 extends DIS_DeadReckoning
{
    Matrix initInv;
    double[] velVec = {entityLinearVelocity_X, entityLinearVelocity_Y, entityLinearVelocity_Z};
    double[] angVec = {entityAngularVelocity_X,entityAngularVelocity_Y,entityAngularVelocity_Z};
    
    double[] updated1 = new double[3]; 
    double[] updated2 = new double[3]; 
    double[] updated = new double[3];

    /**
     * The driver for a DIS_DR_FVB_09 DR algorithm from the Runnable interface
     * <p>
     * linear motion only without rotation
     */
    public void run()
    {
        try
        {           
            initInv = Matrix.transpose(initOrien);              
            
            while(true)
            {
                deltaCt++;
                Thread.sleep(stall);    
                
                updated1 = makeR1();
                updated2 = makeR2();
                
                updated[0] = updated1[0] + updated2[0];
                updated[1] = updated1[1] + updated2[1];
                updated[2] = updated1[2] + updated2[2];                
                
                updated = Matrix.multVec(initInv, updated);

                entityLocation_X += updated[0];
                entityLocation_Y += updated[1];
                entityLocation_Z += updated[2];                         
            }//while(true)  
        }// try
        catch(Exception e)
        {   
            System.out.println(e);     
        }
    }//run()--------------------------------------------------------------------
    
    
    
    /***************************************************************************
     * Makes the R2 vector
     * @return - the r2 vector
     * @throws java.lang.Exception
     */
    private double[] makeR2() throws Exception
    {
        Matrix R2 = new Matrix(3); 
        Matrix ident = new Matrix(3);  
        
        // common factors
        double wDelta = wMag * changeDelta * deltaCt;  
        
        // matrix scalars
        double wwScale = .5*wSq*changeDelta * deltaCt*changeDelta * deltaCt;
            wwScale -= Math.cos(wDelta);
            wwScale -= wDelta*Math.sin(wDelta);
            wwScale++;
            wwScale /= wSq * wSq;
     
        double identScalar = Math.cbrt(wDelta) + wDelta*Math.sin(wDelta) - 1;
            identScalar /= wSq;
            
        double skewScale = Math.sin(wDelta) - wDelta*Math.cos(wDelta);
            skewScale /= wSq * wMag;
                
        // scaled matrixes
        Matrix wwTmp = ww.mult(wwScale);
        Matrix identTmp = ident.mult(identScalar);
        Matrix skwTmp = skewOmega.mult(skewScale);
        
        R2 = Matrix.add(wwTmp, identTmp);
        R2 = Matrix.add(R2, skwTmp);  
                
        return Matrix.multVec(R2, angVec);
    }//makeR2() throws Exception------------------------------------------------
    
    
    
    /***************************************************************************
     * Makes the R1 matrix
     * @return - the vector R1
     * @throws java.lang.Exception
     */
    private double[] makeR1() throws Exception
    {
        Matrix R1 = new Matrix(3); 
        Matrix ident = new Matrix(3);  
        
        // common factors
        double wDelta = wMag * changeDelta * deltaCt;  
        
        // matrix scalars
        double wwScale = (wDelta-Math.sin(wDelta)) / (wSq * wMag); 
        double identScalar = Math.sin(wDelta) / wMag;
        double skewScale = 1 - (Math.cos(wDelta) / wSq);
                
        // scaled matrixes
        Matrix wwTmp = ww.mult(wwScale);
        Matrix identTmp = ident.mult(identScalar);
        Matrix skwTmp = skewOmega.mult(skewScale);
        
        R1 = Matrix.add(wwTmp, identTmp);
        R1 = Matrix.subtract(R1, skwTmp);  
               
        return Matrix.multVec(R1, velVec);
    }//makeR1() throws Exception------------------------------------------------
}