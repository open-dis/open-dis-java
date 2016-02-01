package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Non-DIS class, used on SQL databases. This is not in the DIS standard but can be helpful when saving DIS to a SQL database, particularly in Java.
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class PduStream extends Object implements Serializable
{
   /** Longish description of this PDU stream */
   protected byte[]  description = new byte[512]; 

   /** short description of this PDU stream */
   protected byte[]  name = new byte[256]; 

   /** Start time of recording, in Unix time (seconds since epoch) */
   protected long  startTime;

   /** stop time of recording, in Unix time (seconds since epoch) */
   protected long  stopTime;


/** Constructor */
 public PduStream()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 512 * 1;  // description
   marshalSize = marshalSize + 256 * 1;  // name
   marshalSize = marshalSize + 8;  // startTime
   marshalSize = marshalSize + 8;  // stopTime

   return marshalSize;
}


public void setDescription(byte[] pDescription)
{ description = pDescription;
}

public byte[] getDescription()
{ return description; }

public void setName(byte[] pName)
{ name = pName;
}

public byte[] getName()
{ return name; }

public void setStartTime(long pStartTime)
{ startTime = pStartTime;
}

public long getStartTime()
{ return startTime; 
}

public void setStopTime(long pStopTime)
{ stopTime = pStopTime;
}

public long getStopTime()
{ return stopTime; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {

       for(int idx = 0; idx < description.length; idx++)
       {
           dos.writeByte(description[idx]);
       } // end of array marshaling


       for(int idx = 0; idx < name.length; idx++)
       {
           dos.writeByte(name[idx]);
       } // end of array marshaling

       dos.writeLong( (long)startTime);
       dos.writeLong( (long)stopTime);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       for(int idx = 0; idx < description.length; idx++)
       {
                description[idx] = dis.readByte();
       } // end of array unmarshaling
       for(int idx = 0; idx < name.length; idx++)
       {
                name[idx] = dis.readByte();
       } // end of array unmarshaling
       startTime = dis.readLong();
       stopTime = dis.readLong();
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

       for(int idx = 0; idx < description.length; idx++)
       {
           buff.put((byte)description[idx]);
       } // end of array marshaling


       for(int idx = 0; idx < name.length; idx++)
       {
           buff.put((byte)name[idx]);
       } // end of array marshaling

       buff.putLong( (long)startTime);
       buff.putLong( (long)stopTime);
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
       for(int idx = 0; idx < description.length; idx++)
       {
                description[idx] = buff.get();
       } // end of array unmarshaling
       for(int idx = 0; idx < name.length; idx++)
       {
                name[idx] = buff.get();
       } // end of array unmarshaling
       startTime = buff.getLong();
       stopTime = buff.getLong();
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

    if(!(obj instanceof PduStream))
        return false;

     final PduStream rhs = (PduStream)obj;


     for(int idx = 0; idx < 512; idx++)
     {
          if(!(description[idx] == rhs.description[idx])) ivarsEqual = false;
     }


     for(int idx = 0; idx < 256; idx++)
     {
          if(!(name[idx] == rhs.name[idx])) ivarsEqual = false;
     }

     if( ! (startTime == rhs.startTime)) ivarsEqual = false;
     if( ! (stopTime == rhs.stopTime)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
