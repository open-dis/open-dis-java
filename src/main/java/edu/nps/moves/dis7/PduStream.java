package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;

// Jaxb and Hibernate annotations generally won't work on mobile devices. XML serialization uses jaxb, and
// javax.persistence uses the JPA JSR, aka hibernate. See the Hibernate site for details.
// To generate Java code without these, and without the annotations scattered through the
// see the XMLPG java code generator, and set the boolean useHibernateAnnotations and useJaxbAnnotions 
// to false, and then regenerate the code

import javax.xml.bind.*;            // Used for JAXB XML serialization
import javax.xml.bind.annotation.*; // Used for XML serialization annotations (the @ stuff)
import javax.persistence.*;         // Used for JPA/Hibernate SQL persistence

/**
 * Non-DIS class, used to describe streams of PDUS when logging data to a SQL database. This is not in the DIS standard but can be helpful when logging to a Hibernate sql database
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class PduStream extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_PduStream;

   /** Longish description of this PDU stream */
   protected byte[]  description = new byte[512]; 

   /** short description of this PDU stream */
   protected byte[]  name = new byte[256]; 

   /** Start time of recording, in Unix time (seconds since epoch) */
   protected long  startTime;

   /** stop time of recording, in Unix time (seconds since epoch) */
   protected long  stopTime;

   /** how many PDUs in this stream */
   protected long  pduCount;

   /** variable length list of PDUs */
   protected List< Pdu > pdusInStream = new ArrayList< Pdu >(); 

/** Constructor */
 public PduStream()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 512 * 1;  // description
   marshalSize = marshalSize + 256 * 1;  // name
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


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_PduStream()
{
   return pk_PduStream;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_PduStream(long pKeyName)
{
   this.pk_PduStream = pKeyName;
}

public void setDescription(byte[] pDescription)
{ description = pDescription;
}

@XmlElement(name="description" )
@Basic
public byte[] getDescription()
{ return description; }

public void setName(byte[] pName)
{ name = pName;
}

@XmlElement(name="name" )
@Basic
public byte[] getName()
{ return name; }

public void setStartTime(long pStartTime)
{ startTime = pStartTime;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getStartTime()
{ return startTime; 
}

public void setStopTime(long pStopTime)
{ stopTime = pStopTime;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getStopTime()
{ return stopTime; 
}

@XmlAttribute
@Basic
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

@XmlElementWrapper(name="pdusInStreamList" ) //  Jaxb
@OneToMany    // Hibernate
public List<Pdu> getPdusInStream()
{ return pdusInStream; }


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
     if( ! (pduCount == rhs.pduCount)) ivarsEqual = false;

     for(int idx = 0; idx < pdusInStream.size(); idx++)
     {
        if( ! ( pdusInStream.get(idx).equals(rhs.pdusInStream.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
