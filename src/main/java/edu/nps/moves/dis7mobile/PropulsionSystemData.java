package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * contains information describing the propulsion systems of the entity. This information shall be provided for each active propulsion system defined. Section 6.2.68
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class PropulsionSystemData extends Object implements Serializable
{
   /** powerSetting */
   protected float  powerSetting;

   /** engine RPMs */
   protected float  engineRpm;


/** Constructor */
 public PropulsionSystemData()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // powerSetting
   marshalSize = marshalSize + 4;  // engineRpm

   return marshalSize;
}


public void setPowerSetting(float pPowerSetting)
{ powerSetting = pPowerSetting;
}

public float getPowerSetting()
{ return powerSetting; 
}

public void setEngineRpm(float pEngineRpm)
{ engineRpm = pEngineRpm;
}

public float getEngineRpm()
{ return engineRpm; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeFloat( (float)powerSetting);
       dos.writeFloat( (float)engineRpm);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       powerSetting = dis.readFloat();
       engineRpm = dis.readFloat();
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
       buff.putFloat( (float)powerSetting);
       buff.putFloat( (float)engineRpm);
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
       powerSetting = buff.getFloat();
       engineRpm = buff.getFloat();
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

    if(!(obj instanceof PropulsionSystemData))
        return false;

     final PropulsionSystemData rhs = (PropulsionSystemData)obj;

     if( ! (powerSetting == rhs.powerSetting)) ivarsEqual = false;
     if( ! (engineRpm == rhs.engineRpm)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
