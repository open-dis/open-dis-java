package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Identifies an event in the world. Use this format for ONLY the LiveEntityPdu. Section 6.2.34.
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class EventIdentifierLiveEntity extends Object implements Serializable
{
   protected short  siteNumber;

   protected short  applicationNumber;

   protected int  eventNumber;


/** Constructor */
 public EventIdentifierLiveEntity()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // siteNumber
   marshalSize = marshalSize + 1;  // applicationNumber
   marshalSize = marshalSize + 2;  // eventNumber

   return marshalSize;
}


public void setSiteNumber(short pSiteNumber)
{ siteNumber = pSiteNumber;
}

public short getSiteNumber()
{ return siteNumber; 
}

public void setApplicationNumber(short pApplicationNumber)
{ applicationNumber = pApplicationNumber;
}

public short getApplicationNumber()
{ return applicationNumber; 
}

public void setEventNumber(int pEventNumber)
{ eventNumber = pEventNumber;
}

public int getEventNumber()
{ return eventNumber; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)siteNumber);
       dos.writeByte( (byte)applicationNumber);
       dos.writeShort( (short)eventNumber);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       siteNumber = (short)dis.readUnsignedByte();
       applicationNumber = (short)dis.readUnsignedByte();
       eventNumber = (int)dis.readUnsignedShort();
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
       buff.put( (byte)siteNumber);
       buff.put( (byte)applicationNumber);
       buff.putShort( (short)eventNumber);
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
       siteNumber = (short)(buff.get() & 0xFF);
       applicationNumber = (short)(buff.get() & 0xFF);
       eventNumber = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof EventIdentifierLiveEntity))
        return false;

     final EventIdentifierLiveEntity rhs = (EventIdentifierLiveEntity)obj;

     if( ! (siteNumber == rhs.siteNumber)) ivarsEqual = false;
     if( ! (applicationNumber == rhs.applicationNumber)) ivarsEqual = false;
     if( ! (eventNumber == rhs.eventNumber)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
