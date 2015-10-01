package edu.nps.moves.deadreckoning;

import edu.nps.moves.deadreckoning.utils.*;

/**
 * The root super class for all DIS Dead-Reckoning algorithms.
 * Based on the algrorithms from the 
 * IEEE 1278_1-1995_DIS standards found in Annex B.
 * <p>
 * Creates an abstract instance of a Dead Reckoning (DR) algorithm, defined 
 * by the concrete Dead Reckoning algorithm on the right hand side.
 * <P>
 * At each PDU update received, call the set function to update the DR 
 * algorithm with the most accurance and update information. Expected to receive 
 * a new update approx every 5 seconds or so. Each PDU is essentally a 
 * restart or reset of the DR state.
 * <p>
 * The DR wroks off the last good state (origin) and extrapulates out from that 
 * point based on the velocity and acceleration parameters from the set
 * function.
 * <P>
 * The DR algorithm updates 30 times a second. The instantiating entity
 * can get updated DR states at its leasure upto 30 times a second by calling 
 * the get function, which returns an array of 6 doubles 3 x location and 
 * 3 x orientation. With these 6 parameters the entity can redraw itslef in an
 * updatedloation and orientsation based on its projected path.
 * <p>
 * <hr>
 * <p>
 * <center><h2>Keynotes form the IEEE DIS standard about DR</h2></center>
 * <p>
 * <center><img src="..\..\RefsImgs\formulas.jpg"/></center>
 * <P>
 * DRM notation shall consist of three elements. 
 * 
 * The First element shall indicate whether the model specifies 
 * rotation as either fixed (F) or rotating (R).
 * The second element shall specify dead reckoning rates to be held 
 * constant as either rate of position (P) or rate of velocity (V).
 * The third element shall specify the coordinate system to be used with 
 * the dead reckoning algorithm as either world coordinates (W) or body axis 
 * coordinates (B).
 * 
 * <P>
 * <hr>
 * <p>
 * <b>5.2.1 Angle representation</b><br>
 * Angles shall be specfified as 32-bit floating point numbers expressed 
 * in radians.(page 55)
 * <p>
 * <b>5.2.2 Angular Velocity Vector record</b><br>
 * The angular velocity of simulated entities shall be represented by the Angular 
 * Velocity Vector record. This record shall specify the rate at which an 
 * entity's orientation is changing. The angular velocity shall be measured
 * in <u>radians per second</u> measured about each of the entity's own 
 * coordinate axes. The record shall consist of three fields. The first field 
 * shall represent velocity about the x-axis, the second about the y-axis, and 
 * the third about the z-axis [see 5.2.33 item a)]. The positive direction of 
 * the angular velocity is defined by the right-hand rule. The format of the 
 * Angular Velocity Vector record shall be shown as in table 5. (Page 55)
 * <br>
 * <center><img src="..\..\RefsImgs\angVel.jpg"/></center>
 * <p>
 * <b>5.2.17 Euler Angles record</b><br>
 * Orientation of a simulated entity shall be specified by the Euler Angles 
 * record. This record shall specify three angles as described in figure 3 and 
 * 3.1.13. These angles shall be specified with respect to the entity's 
 * coordinate system. The three angles shall each be specified by a 32-bit 
 * floating point number <u>representing radians</u>. The format of the Euler 
 * Angles record shall be as shown in table 19. (page 65)
 * <br>
 * <center><img src="..\..\RefsImgs\Eangle.jpg"></center>
 * <P>
 * <b>5.2.33 Vector record</b><br>
 * Vector values for entity coordinates, linear acceleration, and linear 
 * velocity, shall be represented using a Vector record. This record shall 
 * consist of three fields, each a 32-bit floating point number. The unit of
 * measure represented by these fields shall depend on the information 
 * represented. The values utilizing the Vector record are as follows:
 * <p>
 * a) Entity Coordinate Vector. Location with respect to a particular entity 
 * shall be specified with respect to three orthogonal axes whose origin shall 
 * be the geometric center of the bounding volume of the entity excluding its 
 * articulated and attached parts (see figure 2). The x-axis extends in the 
 * positive direction out the front of the entity. The y-axis extends in the 
 * positive direction out the right side of the entity as viewed from above, 
 * facing in the direction of the positive x-axis. The z-axis extends in the 
 * positive direction out the bottom of the entity. Each vector component 
 * shall represent meters from the origin (see figure 2).
 * <p>
 * b) Linear Acceleration Vector. Linear acceleration shall be represented as a 
 * vector with components in either world coordinate system or entity's 
 * coordinate system depending on the value in the Dead Reckoning Algorithm 
 * field. Each vector component shall represent acceleration in <u>meters per 
 * second squared</u>.
 * <P>
 * c) Linear Velocity Vector. Linear velocity shall be represented as a vector 
 * with three components in either world coordinate system or entity's 
 * coordinate system depending on the value in the Dead Reckoning Algorithm 
 * field. Each vector component shall represent velocity in <u>meters per 
 * second</u>. The format of the Vector record shall be as shown in 
 * table 30. (page 73)
 * <br>
 * <center><img src="..\..\RefsImgs\vecRec.jpg"></center>
 * <p>
 * <b>5.2.34 World Coordinates record</b><br>
 * Location of the origin of the entity's coordinate system shall be specified 
 * by a set of three coordinates: X, Y, and Z. The shape of the earth shall be 
 * specified using DMA TR 8350.2, 1987. The origin of the world coordinate 
 * system shall be the centroid of the earth, with the X-axis passing through 
 * the prime meridian at the equator, the Y-axis passing through 90° east 
 * longitude at the equator, and the Z-axis passing through the north pole 
 * (see figure 1). These coordinates shall represent meters from the centroid 
 * of the earth. A 64-bit double precision floating point number shall represent 
 * the location for each coordinate. 
 * <p>The format of the World Coordinates record shall be as shown in table 
 * 31. (page 73)
 * <br>
 * <center><img src="..\..\RefsImgs\worldCord.jpg"></center>
 * <P>
 * <B>The Dead Reckoning parameters captured from each PDU</B>
 * <center>
 * <img src="..\..\RefsImgs\drPDUinfo.jpg">
 * </center>
 * 
 * 
 * 
 * 
 * 
 * <p>
 * <hr>
 * <p> 
 * <center><h2>The IEEE specified algorithms to compute the DR for Primary 
 * Methods Group (1-5)</h2></center>
 * <p>
 * <hr>
 * <p>
 * <center><b><u>REVISED POSITION</U></B></CENTER>
  * <P>
 * <hr>
 * <p>
 *
 * The Position portion of the algorithms</b><br>
 
 * <hr>
 * <p>
 * <center><b><u>ORIENTATION SOLVER</U></B></CENTER>
 * <p>
 * Ultimately, the PSI (rotation about the y-axis), THETA (rotation about the
 * z-axis), PHI (rotation about the x-axis) need to be in the range 
 * of 0 - 2PI since the fields are in radians.
  * <P>
 * <hr>
 * <p>
 * 
 * <b><a href="..\..\RefsImgs\2_orientation.jpg">
 * The Orientation portion of the algorithms</A></b><br>
 * <center><img src="..\..\RefsImgs\2_orientation.jpg"/></center>
 * <br>
 * 
 * <p>
 * <hr>
 * <p>
 * <center><b><u>DR MATRIX SOLVER</U></B></CENTER>
  * <P>
 * <hr>
 * <p>
 * <b><a href="..\..\RefsImgs\22_drMatrix.jpg">
 * The generic DR matrix</A></b><br>
 * <center><img src="..\..\RefsImgs\22_drMatrix.jpg"/></center>
 * <br>
 * <center>
 * Graphics rotate (x,y,z) matrices
 * <br>
 * <img src="..\..\RefsImgs\rX.jpg" alt="Rotate X">
 * <img src="..\..\RefsImgs\rY.jpg" alt="Rotate Y">
 * <img src="..\..\RefsImgs\rZ.jpg" alt="Rotate Z"> = [DR]
 * <br>
 * ultimately what this is DR equation is doing but with a change of basis
 * from world to entity coordinates.
 * </center>
 * <p>
 * <b><a href="..\..\RefsImgs\23_angVelocity.jpg">
 * The angular velocity Magnitude</a></b><br>
 * <center><img src="..\..\RefsImgs\23_angVelocity.jpg"/></center>
 * <br>
 * <p>
 * 
 * <b><a href="..\..\RefsImgs\24_omegaSKEW.jpg">
 * The SKEW matrix</a></b><br>
 * <center><img src="..\..\RefsImgs\24_omegaSKEW.jpg"/></center>
 * <br>
 * 
  * <b><a href="..\..\RefsImgs\25_angVelMult.jpg">
 * The angular velocity Matrix</A></b><br>
 * <center><img src="..\..\RefsImgs\25_angVelMult.jpg"/></center>
 * <br>
 * <p>
 * NOTE - It was mentioned above that the angular velocities are contained in 
 * the Entity State PDU as body axis velocities. However, if the angular 
 * velocities are in terms of the Euler angles, then a transformation to body 
 * axis angular velocities is needed. Thus the following transformation 
 * formulas are given:
 * <p>
  * <b><a href="..\..\RefsImgs\body_world.jpg">
 * Body to Wrold Transformation</A></b><br>
 * <center><img src="..\..\RefsImgs\body_world.jpg"/></center>
 * <br>
  * <b><a href="..\..\RefsImgs\world_body.jpg">
 * World to Body Transformation</A></b><br>
 * <center><img src="..\..\RefsImgs\world_body.jpg"/></center>
 * <br>
 * <P>
 * <hr>
 * <p>
 * <center><b><u>R MATRIX SOLVER</U></B></CENTER>
  * <P>
 * <hr>
 * <p>
  * <b><a href="..\..\RefsImgs\initOrientMatrix.jpg">
 * Initial Orientation Matrix</A></b><br>
 * <center><img src="..\..\RefsImgs\initOrientMatrix.jpg"/></center>
 * <P>
 * <hr>
 * <p>
 * <center><b><u>REVISED ORIENTATION</U></B></CENTER>
  * <P>
 * <hr>
 * <p>
  * <b><a href="..\..\RefsImgs\getRevOrientation.jpg">
 * Get the Revised Orientation</A></b><br>
 * <center><img src="..\..\RefsImgs\getRevOrientation.jpg"/></center>
 * 
 * 
 * <p>
 * <hr>
 * <p> 
 * <center><h2>The IEEE specified algorithms to compute the DR for Secondary 
 * Methods Group (6-9)</h2></center>
 * <p>
 * <hr>
 * <p>
 * <i>Note: the <a href="..\..\RefsImgs\2_orientation.jpg">Rotaion formula</a>
 * where applicable is the same as that used in the Primary Methods Group (1-5), 
 * as well is the equation for getting the 
 * <a href="..\..\RefsImgs\getRevOrientation.jpg">revised orientations</a>.</i>
 * <p>
 * <b><a href="..\..\RefsImgs\pos8.jpg">
 * General position formula</A></b><br>
 * <center><img src="..\..\RefsImgs\pos8.jpg"/></center>
 *<p>
 * <b><a href="..\..\RefsImgs\r1.jpg">
 * R1 vector (though I am not sure what its really calculating)</A></b><br>
 * <center><img src="..\..\RefsImgs\r1.jpg"/></center> 
 * 
 *<p>
 * <b><a href="..\..\RefsImgs\r2.jpg">
 * R2 vector (though I am not sure what its really calculating)</A></b><br>
 * <center><img src="..\..\RefsImgs\r2.jpg"/></center> 
 * <p>
 * 
 * 
 * <p>
 * <hr>
 * <p>
 * <u>An Example:</u><br>
 * <pre>
import DIS.DeadReconing.*;

public class runTest 
{
    public static void main(String s[])
    {
        // create a DeadReconing Entity
        DIS_DeadReckoning dr = new DIS_DR_FPW_02();

        // make the arrays of location and other parameters
        //                loc      orien    lin V    Accel    Ang V
        double[] locOr = {2,3,4,   5,6,1,   1,2,1,   0,0,0,   0,0,0};
        
        // set the parameters
        dr.setNewAll(locOr);
        
        // Print out the current state
        System.out.println(dr.toString());
        System.out.println();

        try
        {
            // wait 1 second
            Thread.sleep(1000);
            
            // request an update from the DR algorith
            // should be original + 1 full value of other parameters
            // new position should be (3, 5, 5)
            double[] update = dr.getUpdatedPositionOrientation();
            
            // print the update to the screen
            System.out.println(dr.toString());        
        }
        catch(Exception e)
        {
            System.out.println("Unknow Error...?\n    " + e);
        }
        
        // terminate with exit to get out of the inf while loop
        System.exit(0);
    }
}

Resulting Output:
Current State of this Entity:
    Entity Location = (2.0, 3.0, 4.0)
    Entity Orientation = (5.0, 6.0, 1.0)
    Entity Linear Velocity = (1.0, 2.0, 1.0)
    Entity Linear Acceleration = (0.0, 0.0, 0.0)
    Entity Angular Velocity = (0.0, 0.0, 0.0)
    Delta between updates = 0.033333335

Current State of this Entity:
    Entity Location = (3.000000052154064, 5.000000104308128, 5.000000052154064)
    Entity Orientation = (5.0, 6.0, 1.0)
    Entity Linear Velocity = (1.0, 2.0, 1.0)
    Entity Linear Acceleration = (0.0, 0.0, 0.0)
    Entity Angular Velocity = (0.0, 0.0, 0.0)
    Delta between updates = 0.033333335
 
 
</pre>
 * 
 * @author Sheldon L. Snyder
 */
public abstract class DIS_DeadReckoning implements Runnable
{
    /**
     * The entity's X coordinate location with double percision 64bit 
     */
    protected double entityLocation_X;
    /**
     * The entity's Y coordinate location with double percision 64bit 
     */
    protected double entityLocation_Y;
    /**
     * The entity's Z coordinate location with double percision 64bit 
     */    
    protected double entityLocation_Z;    
    
    /**
     * The X orientation of the entity with 32bit float
     */ 
    protected float entityOrientation_psi;
    /**
     * The Y orientation of the entity with 32bit float
     */ 
    protected float entityOrientation_theta;
    /**
     * The Z orientation of the entity with 32bit float
     */     
    protected float entityOrientation_phi;        
    
    /**
     * The X linear velocity 32bit float
     */ 
    protected float entityLinearVelocity_X = 0;
    /**
     * The Y linear velocity 32bit float
     */     
    protected float entityLinearVelocity_Y = 0;
    /**
     * The Z linear velocity 32bit float
     */     
    protected float entityLinearVelocity_Z = 0;   
  
    /**
     * The linear X acceleration 32bit float
     */ 
    protected float entityLinearAcceleration_X = 0;
    /**
     * The linear Y acceleration 32bit float
     */ 
    protected float entityLinearAcceleration_Y = 0;
    /**
     * The linear Z acceleration 32bit float
     */     
    protected float entityLinearAcceleration_Z = 0;  
    
    /**
     * The X angular velocity 32bit float
     */ 
    protected float entityAngularVelocity_X = 0;
    /**
     * The Y angular velocity 32bit float
     */ 
    protected float entityAngularVelocity_Y = 0;
    /**
     * The Z angular velocity 32bit float
     */     
    protected float entityAngularVelocity_Z = 0;   
    
    /**
     * how may times per second to update this entity's positon
     */
    protected float fps = 30;

    /**
     * How far to change the location/orientation per update
     */
    protected float changeDelta = 1f/fps;
    /**
     * How many updates have occured ... only used for testing
     * <p>
     * Reset to 0 with each call to setAll()
     */
    protected  int deltaCt = 0;// how many updates have been called
    /**
     * How long to wait between updates
     * <P>
     *  the delta between calls...how fast an entity will be updated
     * <ol>
     * Assumed a desired rate of 30 fps
     * Given from the standard that all parameters are in meters/s
     * To move 1 meter/second with 30 incriments = 1/30 Delta between updates
     * delay in milli seconds is 1/30 * 1000 || 1000 / 30
     * </lo>
     * <p>
     * Note from Java Doc for JDK: <br>
     * Causes the currently executing thread to sleep (temporarily cease 
     * execution) for the specified number of milliseconds, subject to the 
     * precision and accuracy of system timers and schedulers. The thread does 
     * not lose ownership of any monitors.      
     */
    protected long stall = (long)(1000/fps);
    
    /**
     * Thread for the DR algorithm update timing (1/30 second)
     */
    protected Thread aThread;
    
    /**
     * the inital orientation, constant between delta T
     * Only changes when a setNewAll is called
     */
    Matrix initOrien = new Matrix(3);    
      
    /**
     * SKEW matrix, constant between delta T
     * Only changes when a setNewAll is called     * Only changes when a setNewAll is called
     */
    Matrix skewOmega = new Matrix(3);
    
    /**
     * Angular velocity matrix, constant between delta T
     * Only changes when a setNewAll is called
     */
    Matrix ww = new Matrix(3);
    
    /**
     * Angular velocity magnitude, constant between delta T
     * Only changes when a setNewAll is called
     */
    double wMag;    
    /**
     * Magnatutd of angular velocity squared
     */
    double wSq;
    /**
     * Float of PI for moduls rounding as needed
     */
    float myPI = 3.1415926f;
 
    /***************************************************************************
     * Constructor for all DR algorithms...
     * <P>
     * Each subclass DR algorithm has a no arguments constructor, but all it 
     * does is call the super, i.e. this constructor, which establishes the
     * Thread
     */
    public DIS_DeadReckoning()
    {
        aThread = new Thread(this);
        aThread.start();
    }//DIS_DeadReckoning()------------------------------------------------------
    
    /***************************************************************************
     * Gets the revised position and orientation of this entity
     * <p>
     * Applies the required DR formula to the initial position and orientation 
     * of this entity and returns the updated locaiton and position.
     * <p>
     * This function does not actually perform the computations, it only returns
     * the current state of the entity. The entity state is updated byt the 
     * specified DR alorithm within the DR class behind the scenes. Updates are
     * crated every 1/30 seconds.
     * <ol>
     * Assume a desire of 30 fps
     * All parameters are in meters/s
     * to move 1 meter/second with 30 incriments = 1/30 Delta between updates
     * 
     * <p>
     * Only returns an array of location and orientation because that
     * is all that is needed to update the location of the entity.  All other 
     * DR inputs are parameters for solving the locaiton and orientation and so
     * are not returned, only set.
     * <p>
     * Order of the retruned array elements
     * <ol> 
     * entityLocation_X
     * entityLocation_Y
     * entityLocation_Z
     * entityOrientation_psi
     * entityOrientation_theta
     * entityOrientation_phi
     * 
     * <p>
     * @return -  6 doubles of location and orientation
     */
    public double[] getUpdatedPositionOrientation()
    {    
        double[] newLoc = {entityLocation_X, entityLocation_Y, entityLocation_Z,
                    entityOrientation_psi, entityOrientation_theta, entityOrientation_phi};
        return newLoc;
    }//getUpdatedPositionOrientation()------------------------------------------
    
    
    
    /***************************************************************************
     * Sets the refresh rate for the scene.
     * <p>
     * Default is 30 but can be changed throught this function call
     * 
     * @param frames - the number of updats per second to make
     */
    public void setFPS(int frames)
    {
        fps = frames;
        changeDelta = 1/fps;
    }//setFPS(int frames)-------------------------------------------------------
    
    
    
    /***************************************************************************
     * Set the parameters for this entity's DR function based on the most
     * recent PDU.
     * <p>
     * This ic called by the entity anytime the entity receives an updated
     * ESPDU for this entity.
     * <P>
     * This can be the first and initialization call or update. 
     * <P>
     * The folowing (triples) are set with each call:
     * <OL>
     * <LI>Entity Locaiton 64bit
     * <LI>Entity Orientation 32bit
     * Entity Linear Velocity 32bit
     * Entity Linear Acceleration 32bit
     * Entity Angular Velocity 32bit
       
     * <P>
     * entityLocation_X = allDis[0];<br>
     * entityLocation_Y = allDis[1];<br>
     * entityLocation_Z = allDis[2];<br>
     * <p>
     * entityOrientation_psi = (float)allDis[3];<br>
     * entityOrientation_theta = (float)allDis[4];<br>
     * entityOrientation_phi = (float)allDis[5];<br>
     * <p>
     * entityLinearVelocity_X = (float)allDis[6];<br>
     * entityLinearVelocity_Y = (float)allDis[7];<br>
     * entityLinearVelocity_Z = (float)allDis[8];<br>
     * <p>
     * entityLinearAcceleration_X = (float)allDis[9];<br>
     * entityLinearAcceleration_Y = (float)allDis[10];<br>
     * entityLinearAcceleration_Z = (float)allDis[11];<br>
     * <p>
     * entityAngularVelocity_X = (float)allDis[12];<br>
     * entityAngularVelocity_Y = (float)allDis[13];<br>
     * entityAngularVelocity_Z = (float)allDis[14];<br>
     * <P>
     * DR fields from a PDU update or initial 
     * 
     * @param allDis - 15 double percisions representing the above in order of the above
     * @throws Exception
     */
    public void setNewAll(double[] allDis) throws Exception
    {
        entityLocation_X = allDis[0];
        entityLocation_Y = allDis[1];
        entityLocation_Z = allDis[2];
        
        entityOrientation_psi = (float)allDis[3];
        entityOrientation_theta = (float)allDis[4];
        entityOrientation_phi = (float)allDis[5];
        
        entityLinearVelocity_X = (float)allDis[6];
        entityLinearVelocity_Y = (float)allDis[7];
        entityLinearVelocity_Z = (float)allDis[8];
        
        entityLinearAcceleration_X = (float)allDis[9];
        entityLinearAcceleration_Y = (float)allDis[10];
        entityLinearAcceleration_Z = (float)allDis[11];
        
        entityAngularVelocity_X = (float)allDis[12];
        entityAngularVelocity_Y = (float)allDis[13];
        entityAngularVelocity_Z = (float)allDis[14];
        
        // solve for magnatude
        wMag = Math.sqrt(entityAngularVelocity_X * entityAngularVelocity_X + 
                entityAngularVelocity_Y * entityAngularVelocity_Y +
                entityAngularVelocity_Z * entityAngularVelocity_Z);
        
        wSq = wMag * wMag;

        //System.out.println("wMag print");
        //System.out.println(wMag);
        //System.out.println();
        
        // build the skew matrix
        setOmega();
        //System.out.println("skewOmega print");
        //skewOmega.print();
        //System.out.println();
        
        // build the angular velocity matrix
        setWW();
        //System.out.println("ww print");
        //ww.print();
        //System.out.println();
        
        // reset delta count given this new update        
        setInitOrient();
        //System.out.println("init Orient print");
        //initOrien.print();
        //System.out.println();        
        
        deltaCt = 0;
    }//setNewAll(double[] allDis)-----------------------------------------------
    
    
    
    /***************************************************************************
     * With each setNewAll() makes the new initial orientation matrix given the
     * new parameters
     * @throws java.lang.Exception
     */    
    private void setInitOrient() throws Exception
    {
        double cosPsi = Math.cos(entityOrientation_psi);
        double sinPsi = Math.sin(entityOrientation_psi);        
        double cosTheta = Math.cos(entityOrientation_theta);
        double sinTheta = Math.sin(entityOrientation_theta);
        double cosPhi = Math.cos(entityOrientation_phi);
        double sinPhi = Math.sin(entityOrientation_phi);        
        
        initOrien.setCell(0, 0, cosTheta*cosPsi);
        initOrien.setCell(0, 1, cosTheta*sinPsi);
        initOrien.setCell(0, 2, -sinTheta);  
        
        initOrien.setCell(1, 0, sinPhi*sinTheta*cosPsi - cosPhi*sinPsi);
        initOrien.setCell(1, 1, sinPhi*sinTheta*sinPsi + cosPhi*cosPsi);
        initOrien.setCell(1, 2, sinPhi*cosTheta);
        
        initOrien.setCell(2, 0, cosPhi*sinTheta*cosPsi + sinPhi*sinPsi);
        initOrien.setCell(2, 1, cosPhi*sinTheta*sinPsi - sinPhi*cosPsi);
        initOrien.setCell(2, 2, cosPhi*cosTheta);        
    }//setInitOrient() throws Exception-----------------------------------------
    
    
    
    /***************************************************************************
     * With each setNewAll() makes the new angular velocity matrix given the
     * new parameters
     * @throws java.lang.Exception
     */
    private void setWW() throws Exception
    {
        ww.setCell(0, 0, entityAngularVelocity_X * entityAngularVelocity_X);
        ww.setCell(0, 1, entityAngularVelocity_X * entityAngularVelocity_Y);
        ww.setCell(0, 2, entityAngularVelocity_X * entityAngularVelocity_Z);                
        ww.setCell(1, 0, entityAngularVelocity_Y * entityAngularVelocity_X);
        ww.setCell(1, 1, entityAngularVelocity_Y * entityAngularVelocity_Y);
        ww.setCell(1, 2, entityAngularVelocity_Y * entityAngularVelocity_Z);
        ww.setCell(2, 0, entityAngularVelocity_Z * entityAngularVelocity_X);
        ww.setCell(2, 1, entityAngularVelocity_Z * entityAngularVelocity_Y);
        ww.setCell(2, 2, entityAngularVelocity_Z * entityAngularVelocity_Z);        
    }//setWW() throws Exception-------------------------------------------------
    
    
    
    /***************************************************************************
     * With each setNewAll() makes the new skew matrix given the
     * new parameters
     * @throws java.lang.Exception
     */
    private void setOmega() throws Exception
    {
        skewOmega.setCell(0, 0, 0);
        skewOmega.setCell(1, 1, 0);
        skewOmega.setCell(2, 2, 0);        
        skewOmega.setCell(1, 0, entityAngularVelocity_Z);
        skewOmega.setCell(2, 0, -entityAngularVelocity_Y);
        skewOmega.setCell(2, 1, entityAngularVelocity_X);              
        skewOmega.setCell(0, 1, -entityAngularVelocity_Z);
        skewOmega.setCell(0, 2, entityAngularVelocity_Y);
        skewOmega.setCell(1, 2, -entityAngularVelocity_X);          
    }//setOmega() throws Exception----------------------------------------------
    
    
    
    /***************************************************************************
     * Pretty print the current state of this Dead Reckoning object
     * <p>
     * Updates are not included in this call, this is only the state. 
     * 
     * @return - String of pretty print of this DR entity
     */
    public String toString()
    {
        String buff = "Current State of this Entity:\n" +
            "    Entity Location = (" + entityLocation_X + ", " +
            entityLocation_Y + ", " + entityLocation_Z + ")\n" +
                    
            "    Entity Orientation = (" + entityOrientation_psi + ", " +
            entityOrientation_theta + ", " + entityOrientation_phi + ")\n" +
                    
            "    Entity Linear Velocity = (" + entityLinearVelocity_X + ", " +
            entityLinearVelocity_Y + ", " + entityLinearVelocity_Z + ")\n" +

            "    Entity Linear Acceleration = (" + entityLinearAcceleration_X + ", " +
            entityLinearAcceleration_Y + ", " + entityLinearAcceleration_Z + ")\n" +

            "    Entity Angular Velocity = (" + entityAngularVelocity_X + ", " + 
            entityAngularVelocity_Y + ", " + entityAngularVelocity_Z + ")\n" +
                    
            "    Delta between updates = " + changeDelta;
       
        return buff;
    }// toString()--------------------------------------------------------------
}// DIS_DeadReckoning-----------------------------------------------------------