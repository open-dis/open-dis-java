package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.3.34. Three double precision floating point values, x, y, and z
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class Vector3Double extends Object implements Serializable
{
   /** X value */
   protected double  x;

   /** Y value */
   protected double  y;

   /** Z value */
   protected double  z;


/** Constructor */
 public Vector3Double()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 8;  // x
   marshalSize = marshalSize + 8;  // y
   marshalSize = marshalSize + 8;  // z

   return marshalSize;
}


public void setX(double pX)
{ x = pX;
}

public double getX()
{ return x; 
}

public void setY(double pY)
{ y = pY;
}

public double getY()
{ return y; 
}

public void setZ(double pZ)
{ z = pZ;
}

public double getZ()
{ return z; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeDouble( (double)x);
       dos.writeDouble( (double)y);
       dos.writeDouble( (double)z);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       x = dis.readDouble();
       y = dis.readDouble();
       z = dis.readDouble();
    } // end try 
   catch(Exception e)
    { 
      System.out.println(e); 
    }
 } // end of unmarshal method 


/**
 * Packs a Pdu into the ByteBuffer.
 * @throws java.nio.BufferOverflowException if buff is too small
 * @throws java.nio.ReadOnlyBufferException if buff is read only
 * @see java.nio.ByteBuffer
 * @param buff The ByteBuffer at the position to begin writing
 * @since ??
 */
public void marshal(java.nio.ByteBuffer buff)
{
       buff.putDouble( (double)x);
       buff.putDouble( (double)y);
       buff.putDouble( (double)z);
    } // end of marshal method

/**
 * Unpacks a Pdu from the underlying data.
 * @throws java.nio.BufferUnderflowException if buff is too small
 * @see java.nio.ByteBuffer
 * @param buff The ByteBuffer at the position to begin reading
 * @since ??
 */
public void unmarshal(java.nio.ByteBuffer buff)
{
       x = buff.getDouble();
       y = buff.getDouble();
       z = buff.getDouble();
 } // end of unmarshal method 


 /*
  * The equals method doesn't always work--mostly it works only on classes that consist only of primitives. Be careful.
  */
@Override
 public boolean equals(Object obj)
 {

    if(this == obj){
      return true;
    }

    if(obj == null){
       return false;
    }

    if(getClass() != obj.getClass())
        return false;

    return equalsImpl(obj);
 }

 /**
  * Compare all fields that contribute to the state, ignoring
 transient and static fields, for <code>this</code> and the supplied object
  * @param obj the object to compare to
  * @return true if the objects are equal, false otherwise.
  */
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof Vector3Double))
        return false;

     final Vector3Double rhs = (Vector3Double)obj;

     if( ! (x == rhs.x)) ivarsEqual = false;
     if( ! (y == rhs.y)) ivarsEqual = false;
     if( ! (z == rhs.z)) ivarsEqual = false;

    return ivarsEqual;
 }
    
    /**
     * Assuming that the x,y,z values of this Vector3Double are x=latitude,
     * y=longitude, (in degrees) and z=altitude (in meters), converts them
     * to DIS coordinates<p>
     *
     * Vector3Double is very often ued for setting the entity position. This is a
     * convienience method that allows the programmer to set a latitude, longitude,
     * and altitude, and have it  converted to the DIS coordiinate system<p>
     *
     * The DIS standard uses an earth-centered, rectilinear coordinate system with
     * the Z axis pointing through the north pole, the X axis pointing out at the
     * intersection of the equator and prime meridian, and the Y axis pointing out
     * at the equator and 90 deg east. Since pretty much no one else uses this,
     * it can make finding the "DIS standard" x,y,z difficult if you have only
     * latitude, longitude, and altitude. This method does the converstion from those
     * three values to the DIS coordinate system.<p>
     *
     * The conversion is always somewhat problematic, depending on what model of the
     * earth's surface you use. This uses the WGS84 elipsoid model, and may not be
     * accurate around the poles.<p>
     */
    
    public void convertLatitudeLongitudeAltitudeToDis()
    {
        double radiansLat;
        double radiansLon;
        double xyz[] = new double[3];
        
        radiansLat = (Math.PI * x) / 180.0;
        radiansLon = (Math.PI * y) / 180.0;
        
        // Do the conversion
        xyz = CoordinateConversions.getXYZfromLatLonRadians(radiansLat, radiansLon, z);
        
        // Set the values
        this.setX(xyz[0]);
        this.setY(xyz[1]);
        this.setZ(xyz[2]);
    }
    
    /**
     * Assuming that the Vector3Double contains DIS coordinate system values, converts
     * them in place to latitude in the x value, longitude in the y value, (in degrees)
     * and altitude in meters for the z value.
     *
     */
    public void convertDisToLatitudeLongitudeAltitude()
    {
        Vector3Double location = new Vector3Double();
        double[] xyz = new double[3];
        double[] result;
        
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = z;
        
        result = CoordinateConversions.xyzToLatLonRadians(xyz);
        
        // Convert radians in the result to degrees
        result[0] = (result[0] * 180.0)/Math.PI; // latitude
        result[1] = (result[1] * 180.0)/Math.PI; // longitude
        
        this.setX(result[0]);
        this.setY(result[1]);
        this.setZ(result[2]);
    }
    
    
} // end of class
