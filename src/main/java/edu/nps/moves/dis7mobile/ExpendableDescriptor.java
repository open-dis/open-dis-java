package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Burst of chaff or expendible device. Section 6.2.19.4
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class ExpendableDescriptor extends Object implements Serializable
{
   /** Type of the object that exploded */
   protected EntityType  expendableType = new EntityType(); 

   /** Padding */
   protected long  padding = (long)0;


/** Constructor */
 public ExpendableDescriptor()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + expendableType.getMarshalledSize();  // expendableType
   marshalSize = marshalSize + 8;  // padding

   return marshalSize;
}


public void setExpendableType(EntityType pExpendableType)
{ expendableType = pExpendableType;
}

public EntityType getExpendableType()
{ return expendableType; 
}

public void setPadding(long pPadding)
{ padding = pPadding;
}

public long getPadding()
{ return padding; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       expendableType.marshal(dos);
       dos.writeLong( (long)padding);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       expendableType.unmarshal(dis);
       padding = dis.readLong();
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
       expendableType.marshal(buff);
       buff.putLong( (long)padding);
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
       expendableType.unmarshal(buff);
       padding = buff.getLong();
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

    if(!(obj instanceof ExpendableDescriptor))
        return false;

     final ExpendableDescriptor rhs = (ExpendableDescriptor)obj;

     if( ! (expendableType.equals( rhs.expendableType) )) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
