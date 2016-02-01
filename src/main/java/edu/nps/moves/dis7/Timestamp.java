package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * LSB is absolute or relative timestamp. Scale is 2^31 - 1 divided into one hour.
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class Timestamp extends Object implements Serializable
{
   /** timestamp */
   protected long  timestamp;


/** Constructor */
 public Timestamp()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // timestamp

   return marshalSize;
}


public void setTimestamp(long pTimestamp)
{ timestamp = pTimestamp;
}

public long getTimestamp()
{ return timestamp; 
}


/**
 * 0 relative timestamp, 1 host synchronized timestamp
 */
public int getTimestamp_timestampType()
{
    long val = (long)(this.timestamp   & (long)0x1);
    return (int)(val >> 0);
}


/** 
 * 0 relative timestamp, 1 host synchronized timestamp
 */
public void setTimestamp_timestampType(int val)
{
    long  aVal = 0;
    this.timestamp &= (long)(~0x1); // clear bits
    aVal = (long)(val << 0);
    this.timestamp = (long)(this.timestamp | aVal);
}


/**
 * 2^31-1 per hour time units
 */
public int getTimestamp_timestampValue()
{
    long val = (long)(this.timestamp   & (long)0xFE);
    return (int)(val >> 1);
}


/** 
 * 2^31-1 per hour time units
 */
public void setTimestamp_timestampValue(int val)
{
    long  aVal = 0;
    this.timestamp &= (long)(~0xFE); // clear bits
    aVal = (long)(val << 1);
    this.timestamp = (long)(this.timestamp | aVal);
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)timestamp);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       timestamp = dis.readInt();
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
       buff.putInt( (int)timestamp);
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
       timestamp = buff.getInt();
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

    if(!(obj instanceof Timestamp))
        return false;

     final Timestamp rhs = (Timestamp)obj;

     if( ! (timestamp == rhs.timestamp)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
