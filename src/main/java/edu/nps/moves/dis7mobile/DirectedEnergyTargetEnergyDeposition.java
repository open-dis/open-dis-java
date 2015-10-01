package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * DE energy depostion properties for a target entity. Section 6.2.20.4
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DirectedEnergyTargetEnergyDeposition extends Object implements Serializable
{
   /** Unique ID of the target entity. */
   protected EntityID  targetEntityID = new EntityID(); 

   /** padding */
   protected int  padding = (int)0;

   /** Peak irrandiance */
   protected float  peakIrradiance;


/** Constructor */
 public DirectedEnergyTargetEnergyDeposition()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + targetEntityID.getMarshalledSize();  // targetEntityID
   marshalSize = marshalSize + 2;  // padding
   marshalSize = marshalSize + 4;  // peakIrradiance

   return marshalSize;
}


public void setTargetEntityID(EntityID pTargetEntityID)
{ targetEntityID = pTargetEntityID;
}

public EntityID getTargetEntityID()
{ return targetEntityID; 
}

public void setPadding(int pPadding)
{ padding = pPadding;
}

public int getPadding()
{ return padding; 
}

public void setPeakIrradiance(float pPeakIrradiance)
{ peakIrradiance = pPeakIrradiance;
}

public float getPeakIrradiance()
{ return peakIrradiance; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       targetEntityID.marshal(dos);
       dos.writeShort( (short)padding);
       dos.writeFloat( (float)peakIrradiance);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       targetEntityID.unmarshal(dis);
       padding = (int)dis.readUnsignedShort();
       peakIrradiance = dis.readFloat();
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
       targetEntityID.marshal(buff);
       buff.putShort( (short)padding);
       buff.putFloat( (float)peakIrradiance);
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
       targetEntityID.unmarshal(buff);
       padding = (int)(buff.getShort() & 0xFFFF);
       peakIrradiance = buff.getFloat();
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

    if(!(obj instanceof DirectedEnergyTargetEnergyDeposition))
        return false;

     final DirectedEnergyTargetEnergyDeposition rhs = (DirectedEnergyTargetEnergyDeposition)obj;

     if( ! (targetEntityID.equals( rhs.targetEntityID) )) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (peakIrradiance == rhs.peakIrradiance)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
