package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.2.8. Time measurements that exceed one hour. Hours is the number of           hours since January 1, 1970, UTC
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class ClockTime extends Object implements Serializable
{
   /** Hours in UTC */
   protected int  hour;

   /** Time past the hour */
   protected long  timePastHour;


/** Constructor */
 public ClockTime()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // hour
   marshalSize = marshalSize + 4;  // timePastHour

   return marshalSize;
}


public void setHour(int pHour)
{ hour = pHour;
}

public int getHour()
{ return hour; 
}

public void setTimePastHour(long pTimePastHour)
{ timePastHour = pTimePastHour;
}

public long getTimePastHour()
{ return timePastHour; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)hour);
       dos.writeInt( (int)timePastHour);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       hour = dis.readInt();
       timePastHour = dis.readInt();
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
       buff.putInt( (int)hour);
       buff.putInt( (int)timePastHour);
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
       hour = buff.getInt();
       timePastHour = buff.getInt();
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

    if(!(obj instanceof ClockTime))
        return false;

     final ClockTime rhs = (ClockTime)obj;

     if( ! (hour == rhs.hour)) ivarsEqual = false;
     if( ! (timePastHour == rhs.timePastHour)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
