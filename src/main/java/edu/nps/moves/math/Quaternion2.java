package edu.nps.moves.math;

/**
 * The Quaternion2 class executes quaternion operations.
 *
 * @author Ildeniz Duman
 * @version 1.0  13 Dec 98
 *
 *            DIS                        VRML - OpenGl
 *       +---------->x                     ^y
 *      /|                                 |
 *     / |                                 |
 *    /  |                ======>          |
 *   y   |                                 +-------------->x
 *       z                                /
 *                                       /
 *                                      /
 *                                     z
 */
public class Quaternion2 {

   /**
     * Elements of a quaternion.
     */
	private double w,x,y,z;

   /**
     * Name of the quaternion.
     */
   private String name;

/**
* Default constructor
* @param myName Name of the quaternion
* @param ww w value of the quaternion
* @param xx x value of the quaternion
* @param yy y value of the quaternion
* @param zz z value of the quaternion
*/
public Quaternion2 (String myName, double ww, double xx,double yy, double zz){
	name = new String (myName);
   w=ww;
   x=xx;
   y=yy;
   z=zz;
}


/**
* Default constructor
* @param myName Name of the quaternion
*/
public Quaternion2 (String myName){
	name = new String (myName);
   w=1.0f;
   x=0.0f;
   y=0.0f;
   z=0.0f;
}


/**
* Default constructor
*/
public Quaternion2 (){
	name = new String ("Quaternion2");
   w=1.0f;
   x=0.0f;
   y=0.0f;
   z=0.0f;
}


/**
* Sets the quaternion values
* @param ww w value of the quaternion
* @param xx x value of the quaternion
* @param yy y value of the quaternion
* @param zz z value of the quaternion
*/
public void setQuaternion2 (double ww, double xx, double yy, double zz)
{
	w=ww;
	x=xx;
	y=yy;
	z=zz;
	return;
}// end setQuaternion2()

/**
* Returns the w value of quaternion.
*/
public double getW(){return w;}
/**
* Returns the x value of quaternion.
*/
public double getX(){return x;}
/**
* Returns the y value of quaternion.
*/
public double getY(){return y;}
/**
* Returns the z value of quaternion.
*/
public double getZ(){return z;}


/**
* Multiplies two quaternions
* @param QUAT second quaternion
* @return new Quaternion2
*/
public Quaternion2 multiply(final Quaternion2 QUAT)
{
	Quaternion2 dest = new Quaternion2 ("Quaternion2 Product");
	dest.w  =	QUAT.w * w - QUAT.x * x - QUAT.y * y - QUAT.z * z;
	dest.x  = 	QUAT.w * x + QUAT.x * w - QUAT.y * z + QUAT.z * y;
	dest.y  =	QUAT.w * y + QUAT.y * w - QUAT.z * x + QUAT.x * z;
	dest.z  =	QUAT.w * z + QUAT.z * w - QUAT.x * y + QUAT.y * x;

	return (dest);
}//end multiply()


/**
* Multiplies this quaternion with a scalar
* @param NUM is a scalar
* @return modified Quaternion2
*/
public Quaternion2 multiply(final double NUM)
{
	w=NUM*w;
	x=NUM*x;
	y=NUM*y;
	z=NUM*z;
	return (this);
}//end multiply()


/**
* Adds two quaternions
* @param QUAT is a quaternion to add
* @return new Quaternion2
*/
public Quaternion2 add(final Quaternion2 QUAT)
{
	Quaternion2 addTo = new Quaternion2 ("addition");
	addTo.w = w + QUAT.w;
	addTo.x = x + QUAT.x;
	addTo.y = y + QUAT.y;
	addTo.z = z + QUAT.z;

	return (addTo);
}// end add()


/**
* Substracts two quaternions
* @param QUAT is a quaternion to substract
* @return new Quaternion2
*/
public Quaternion2 substract(final Quaternion2 QUAT)
{
	Quaternion2 s  = new Quaternion2 ("substraction");
	s.w = w - QUAT.w;
	s.x = x - QUAT.x;
	s.y = y - QUAT.y;
	s.z = z - QUAT.z;

	return (s);
}// end substract()


/**
* Finds the inverse (conjugate) of a quaternion
* @return new Quaternion2
*/
public Quaternion2 invert()
{
	Quaternion2 temp = new Quaternion2 ("Conjugate");
	temp.w = w;
	temp.x = -x;
	temp.y = -y;
	temp.z = -z;
	return (temp);
}//end invert()


/**
* Rotates a vector by quaternions
* @param QUAT4 a vector in quaternion form
* @return Rotated vector in quaternion form
*/
public Quaternion2 rotate(final Quaternion2 QUAT4)
{
	Quaternion2 temp = new Quaternion2 ("Rotation");
   temp = this.multiply(QUAT4.multiply(this.invert()));
 	return (temp);
}//end rotate()


/** Converts to body coordinates
* @param QUAT a vector in quaternion form to convert to body coordinates
* @return Converted vector in quaternion form
*/
public Quaternion2 toBody(final Quaternion2 QUAT)
{
	Quaternion2 temp = new Quaternion2 ("to Body");
   temp = (this.invert()).multiply(QUAT.multiply(this));
	return (temp);
}//end toBody()


/** Finds the dot product of two quaternions
* @param QUAT5 a quaternion
* @return Dot product value
*/
public double dotProduct(final Quaternion2 QUAT5)
{
	double dotproduct;
	dotproduct = (w * QUAT5.w) + (x * QUAT5.x) + (y * QUAT5.y) + (z * QUAT5.z);
	return (dotproduct);
} //end dotProduct()


/** Normalizes the quaternion
* modifies the current quaternion
*/
public void normalize()
{
	double sq = Math.sqrt(dotProduct(this));
	x /= sq;
	y /= sq;
	z /= sq;
	w /= sq;
	return;
}//end normalize()


/**  Calculates the axis angles of quaternion for drawing,
*    results must be used in glRotatef() by using get() functions
* @return Result in quaternion form
*/
public Quaternion2 toAxisAngles()
{
	Quaternion2 axisAngle = new Quaternion2 ("To Axis Angle ");

	double scalarNumber,temp;
	temp = Math.acos(w) * 2.0;
	scalarNumber = Math.sin(temp / 2.0);
	axisAngle.x = x / scalarNumber;
	// aligning the axes
	axisAngle.y = -1 * z / scalarNumber;
	axisAngle.z = y / scalarNumber;
	axisAngle.w = (temp * (360 / 2.0)) / Math.PI;

	return (axisAngle);
}//end toAxisAngles()


/**  Calculates the quaternion value of three rotations
* Current object is a 3D vector with rotation angles in quaternion form
@return new Quaternion2
*/
public Quaternion2 toQuaternion2()
{
	//current object is a 3D vector with rotation angles at x,y,z
	double radX,radY,radZ,tempx,tempy,tempz;
  	double cosx,cosy,cosz,sinx,siny,sinz,cosc,coss,sinc,sins;

	Quaternion2 quat = new Quaternion2 ("Euler To Quaternion2");

	// convert angles to radians
	radX =  (x * Math.PI) / (360.0 / 2.0);
	radY =  (y * Math.PI) / (360.0 / 2.0);
	radZ =  (z * Math.PI) / (360.0 / 2.0);
	// half angles
	tempx = radX * 0.5;
	tempy = radY * 0.5;
	tempz = radZ * 0.5;
	cosx = Math.cos(tempx);
	cosy = Math.cos(tempy);
	cosz = Math.cos(tempz);
	sinx = Math.sin(tempx);
	siny = Math.sin(tempy);
	sinz = Math.sin(tempz);

	cosc = cosx * cosz;
	coss = cosx * sinz;
	sinc = sinx * cosz;
	sins = sinx * sinz;

	quat.x = (cosy * sinc) - (siny * coss);
	quat.y = (cosy * sins) + (siny * cosc);
	quat.z = (cosy * coss) - (siny * sinc);
	quat.w = (cosy * cosc) + (siny * sins);

	quat.normalize();
	return(quat);
}// end toQuaternion2


/** Converts a quaternion into Euler angles
* Warning : This conversion is inherently ill-defined
* @return new Quaternion2
*/
public Quaternion2 toEulerAngles()
{
	Quaternion2 euler = new Quaternion2 ("Quat to euler");
	double sint,cost,sinv,cosv,sinf,cosf,ex,ey,ez;

	sint = (2*w*y)-(2*x*z);
	cost = Math.sqrt(1- Math.pow(sint,2));
	if (cost != 0.0){
		sinv = ((2*y*z)+(2*w*x))/cost;
		cosv = (1-(2*x*x)-(2*y*y))/cost;
		sinf = (1-(2*x*x)-(2*y*y))/cost;
		cosf = (1-(2*y*y)-(2*z*z))/cost;
	}else {
		sinv = (2*w*x)-(2*y*z);
		cosv = 1-(2*x*x)-(2*z*z);
		sinf = 0.0;
		cosf = 1.0;
	}

	ex = Math.atan2(sinv,cosv);
	ey = Math.atan2(sint,cost);
	ez = Math.atan2(sinf,cosf);  //range -pi +pi

	ex *= 180.0/Math.PI;
	ey *= 180.0/Math.PI;
	ez *= 180.0/Math.PI;

	euler.setQuaternion2(0.0, ex,ey,ez);
	return (euler);
}//end toEulerAngles()


/** Overrides the toString method
* @return String object
*/
public String toString()
{
	return ("[ "+name+" w="+String.valueOf(w)+" x="+String.valueOf(x)+" y="+
   		String.valueOf(y)+" z="+String.valueOf(z)+" ]");
}  // end toString()


/** Converts quaternion's values into float
* @return Float []
*/
public float[] toFloat()
{
	float myFloat [] = new float[4];
   myFloat[0] = (float)w;
   myFloat[1] = (float)x;
   myFloat[2] = (float)y;
   myFloat[3] = (float)z;
   return myFloat;
} // end toFloat()


/** Main method for testing
*/
public static void main(String ags[]){
	Quaternion2 one=new Quaternion2 ("one");
	Quaternion2 two=new Quaternion2 ("two",5, 1, 2, 3);
	System.out.println(two);
	float f[]=new float[4];
	f=two.toFloat();
	System.out.println("floats"+" "+f[0]+" "+f[1]+" "+f[2]+" "+f[3]);

	System.out.println(one + "\n"+two);
	two.normalize();

	System.out.println(two);

	System.out.println(one);
	System.out.println(one.add(two));
	Quaternion2 q=new Quaternion2();
	System.out.println(q);
	q=one.multiply(two);
	q.normalize();
	System.out.println(q);

	q=two.rotate(one);
	System.out.println(one + "\n "+two+"\n " + q);
}  // end main()

} // end Quaternion2 class
//end of file Quaternion2.java


/* Copyright (c) 1997-2003, Naval Postgraduate School
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the Naval Postgraduate School nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
