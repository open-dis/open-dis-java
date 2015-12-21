package edu.nps.moves.dis;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Non-DIS class, used to describe streams of PDUs when logging to SQL databases
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class PduStream extends Object implements Serializable
{
   /** short description of this PDU stream */
   protected byte[]  shortDescription = new byte[256]; 

   /** Longish description of this PDU stream */
   protected byte[]  longDescription = new byte[512]; 

   /** Name of person performing recording */
   protected byte[]  personRecording = new byte[128]; 

   /** Email of person performing recording */
   protected byte[]  authorEmail = new byte[128]; 

   /** Start time of recording, in Unix time */
   protected long  startTime;

   /** stop time of recording, in Unix time */
   protected long  stopTime;

   /** how many PDUs in this stream */
   protected long  pduCount;

   /** variable length list of PDUs */
   protected List< Pdu > pdusInStream = new ArrayList< Pdu >(); 

/** Constructor */
 public PduStream()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 256 * 1;  // shortDescription
   marshalSize = marshalSize + 512 * 1;  // longDescription
   marshalSize = marshalSize + 128 * 1;  // personRecording
   marshalSize = marshalSize + 128 * 1;  // authorEmail
   marshalSize = marshalSize + 8;  // startTime
   marshalSize = marshalSize + 8;  // stopTime
   marshalSize = marshalSize + 4;  // pduCount
   for(int idx=0; idx < pdusInStream.size(); idx++)
   {
        Pdu listElement = pdusInStream.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setShortDescription(byte[] pShortDescription)
{ shortDescription = pShortDescription;
}

public byte[] getShortDescription()
{ return shortDescription; }

public void setLongDescription(byte[] pLongDescription)
{ longDescription = pLongDescription;
}

public byte[] getLongDescription()
{ return longDescription; }

public void setPersonRecording(byte[] pPersonRecording)
{ personRecording = pPersonRecording;
}

public byte[] getPersonRecording()
{ return personRecording; }

public void setAuthorEmail(byte[] pAuthorEmail)
{ authorEmail = pAuthorEmail;
}

public byte[] getAuthorEmail()
{ return authorEmail; }

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

public long getPduCount()
{ return (long)pdusInStream.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getpduCount method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setPduCount(long pPduCount)
{ pduCount = pPduCount;
}

public void setPdusInStream(List<Pdu> pPdusInStream)
{ pdusInStream = pPdusInStream;
}

public List<Pdu> getPdusInStream()
{ return pdusInStream; }


public void marshal(DataOutputStream dos)
{
    try 
    {

       for(int idx = 0; idx < shortDescription.length; idx++)
       {
           dos.writeByte(shortDescription[idx]);
       } // end of array marshaling


       for(int idx = 0; idx < longDescription.length; idx++)
       {
           dos.writeByte(longDescription[idx]);
       } // end of array marshaling


       for(int idx = 0; idx < personRecording.length; idx++)
       {
           dos.writeByte(personRecording[idx]);
       } // end of array marshaling


       for(int idx = 0; idx < authorEmail.length; idx++)
       {
           dos.writeByte(authorEmail[idx]);
       } // end of array marshaling

       dos.writeLong( (long)startTime);
       dos.writeLong( (long)stopTime);
       dos.writeInt( (int)pdusInStream.size());

       for(int idx = 0; idx < pdusInStream.size(); idx++)
       {
            Pdu aPdu = pdusInStream.get(idx);
            aPdu.marshal(dos);
       } // end of list marshalling

    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       for(int idx = 0; idx < shortDescription.length; idx++)
       {
                shortDescription[idx] = dis.readByte();
       } // end of array unmarshaling
       for(int idx = 0; idx < longDescription.length; idx++)
       {
                longDescription[idx] = dis.readByte();
       } // end of array unmarshaling
       for(int idx = 0; idx < personRecording.length; idx++)
       {
                personRecording[idx] = dis.readByte();
       } // end of array unmarshaling
       for(int idx = 0; idx < authorEmail.length; idx++)
       {
                authorEmail[idx] = dis.readByte();
       } // end of array unmarshaling
       startTime = dis.readLong();
       stopTime = dis.readLong();
       pduCount = dis.readInt();
       for(int idx = 0; idx < pduCount; idx++)
       {
           Pdu anX = new Pdu();
           anX.unmarshal(dis);
           pdusInStream.add(anX);
       }

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

       for(int idx = 0; idx < shortDescription.length; idx++)
       {
           buff.put((byte)shortDescription[idx]);
       } // end of array marshaling


       for(int idx = 0; idx < longDescription.length; idx++)
       {
           buff.put((byte)longDescription[idx]);
       } // end of array marshaling


       for(int idx = 0; idx < personRecording.length; idx++)
       {
           buff.put((byte)personRecording[idx]);
       } // end of array marshaling


       for(int idx = 0; idx < authorEmail.length; idx++)
       {
           buff.put((byte)authorEmail[idx]);
       } // end of array marshaling

       buff.putLong( (long)startTime);
       buff.putLong( (long)stopTime);
       buff.putInt( (int)pdusInStream.size());

       for(int idx = 0; idx < pdusInStream.size(); idx++)
       {
            Pdu aPdu = (Pdu)pdusInStream.get(idx);
            aPdu.marshal(buff);
       } // end of list marshalling

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
       for(int idx = 0; idx < shortDescription.length; idx++)
       {
                shortDescription[idx] = buff.get();
       } // end of array unmarshaling
       for(int idx = 0; idx < longDescription.length; idx++)
       {
                longDescription[idx] = buff.get();
       } // end of array unmarshaling
       for(int idx = 0; idx < personRecording.length; idx++)
       {
                personRecording[idx] = buff.get();
       } // end of array unmarshaling
       for(int idx = 0; idx < authorEmail.length; idx++)
       {
                authorEmail[idx] = buff.get();
       } // end of array unmarshaling
       startTime = buff.getLong();
       stopTime = buff.getLong();
       pduCount = buff.getInt();
       for(int idx = 0; idx < pduCount; idx++)
       {
            Pdu anX = new Pdu();
            anX.unmarshal(buff);
            pdusInStream.add(anX);
       }

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


     for(int idx = 0; idx < 256; idx++)
     {
          if(!(shortDescription[idx] == rhs.shortDescription[idx])) ivarsEqual = false;
     }


     for(int idx = 0; idx < 512; idx++)
     {
          if(!(longDescription[idx] == rhs.longDescription[idx])) ivarsEqual = false;
     }


     for(int idx = 0; idx < 128; idx++)
     {
          if(!(personRecording[idx] == rhs.personRecording[idx])) ivarsEqual = false;
     }


     for(int idx = 0; idx < 128; idx++)
     {
          if(!(authorEmail[idx] == rhs.authorEmail[idx])) ivarsEqual = false;
     }

     if( ! (startTime == rhs.startTime)) ivarsEqual = false;
     if( ! (stopTime == rhs.stopTime)) ivarsEqual = false;
     if( ! (pduCount == rhs.pduCount)) ivarsEqual = false;

     for(int idx = 0; idx < pdusInStream.size(); idx++)
     {
        if( ! ( pdusInStream.get(idx).equals(rhs.pdusInStream.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
