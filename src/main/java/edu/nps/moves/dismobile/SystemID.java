package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * 5.2.58. Used in IFF ATC PDU
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class SystemID extends Object implements Serializable
{
   /** System Type */
   protected int  systemType;

   /** System name, an enumeration */
   protected int  systemName;

   /** System mode */
   protected short  systemMode;

   /** Change Options */
   protected short  changeOptions;


/** Constructor */
 public SystemID()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // systemType
   marshalSize = marshalSize + 2;  // systemName
   marshalSize = marshalSize + 1;  // systemMode
   marshalSize = marshalSize + 1;  // changeOptions

   return marshalSize;
}


public void setSystemType(int pSystemType)
{ systemType = pSystemType;
}

public int getSystemType()
{ return systemType; 
}

public void setSystemName(int pSystemName)
{ systemName = pSystemName;
}

public int getSystemName()
{ return systemName; 
}

public void setSystemMode(short pSystemMode)
{ systemMode = pSystemMode;
}

public short getSystemMode()
{ return systemMode; 
}

public void setChangeOptions(short pChangeOptions)
{ changeOptions = pChangeOptions;
}

public short getChangeOptions()
{ return changeOptions; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)systemType);
       dos.writeShort( (short)systemName);
       dos.writeByte( (byte)systemMode);
       dos.writeByte( (byte)changeOptions);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       systemType = (int)dis.readUnsignedShort();
       systemName = (int)dis.readUnsignedShort();
       systemMode = (short)dis.readUnsignedByte();
       changeOptions = (short)dis.readUnsignedByte();
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
       buff.putShort( (short)systemType);
       buff.putShort( (short)systemName);
       buff.put( (byte)systemMode);
       buff.put( (byte)changeOptions);
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
       systemType = (int)(buff.getShort() & 0xFFFF);
       systemName = (int)(buff.getShort() & 0xFFFF);
       systemMode = (short)(buff.get() & 0xFF);
       changeOptions = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof SystemID))
        return false;

     final SystemID rhs = (SystemID)obj;

     if( ! (systemType == rhs.systemType)) ivarsEqual = false;
     if( ! (systemName == rhs.systemName)) ivarsEqual = false;
     if( ! (systemMode == rhs.systemMode)) ivarsEqual = false;
     if( ! (changeOptions == rhs.changeOptions)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
