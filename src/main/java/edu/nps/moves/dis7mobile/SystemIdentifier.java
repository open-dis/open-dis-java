package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * The ID of the IFF emitting system. NOT COMPLETE. Section 6.2.87
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class SystemIdentifier extends Object implements Serializable
{
   /** general type of emitting system, an enumeration */
   protected int  systemType;

   /** named type of system, an enumeration */
   protected int  systemName;

   /** mode of operation for the system, an enumeration */
   protected int  systemMode;

   /** status of this PDU, see section 6.2.15 */
   protected ChangeOptions  changeOptions = new ChangeOptions(); 


/** Constructor */
 public SystemIdentifier()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // systemType
   marshalSize = marshalSize + 2;  // systemName
   marshalSize = marshalSize + 2;  // systemMode
   marshalSize = marshalSize + changeOptions.getMarshalledSize();  // changeOptions

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

public void setSystemMode(int pSystemMode)
{ systemMode = pSystemMode;
}

public int getSystemMode()
{ return systemMode; 
}

public void setChangeOptions(ChangeOptions pChangeOptions)
{ changeOptions = pChangeOptions;
}

public ChangeOptions getChangeOptions()
{ return changeOptions; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)systemType);
       dos.writeShort( (short)systemName);
       dos.writeShort( (short)systemMode);
       changeOptions.marshal(dos);
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
       systemMode = (int)dis.readUnsignedShort();
       changeOptions.unmarshal(dis);
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
       buff.putShort( (short)systemMode);
       changeOptions.marshal(buff);
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
       systemMode = (int)(buff.getShort() & 0xFFFF);
       changeOptions.unmarshal(buff);
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

    if(!(obj instanceof SystemIdentifier))
        return false;

     final SystemIdentifier rhs = (SystemIdentifier)obj;

     if( ! (systemType == rhs.systemType)) ivarsEqual = false;
     if( ! (systemName == rhs.systemName)) ivarsEqual = false;
     if( ! (systemMode == rhs.systemMode)) ivarsEqual = false;
     if( ! (changeOptions.equals( rhs.changeOptions) )) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
