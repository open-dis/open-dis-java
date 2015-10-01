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
 * Section 5.3.12.13: A request for one or more records of data from an entity. COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class RecordQueryReliablePdu extends SimulationManagementWithReliabilityFamilyPdu implements Serializable
{
   /** request ID */
   protected long  requestID;

   /** level of reliability service used for this transaction */
   protected short  requiredReliabilityService;

   /** padding. The spec is unclear and contradictory here. */
   protected int  pad1;

   /** padding */
   protected short  pad2;

   /** event type */
   protected int  eventType;

   /** time */
   protected long  time;

   /** numberOfRecords */
   protected long  numberOfRecords;

   /** record IDs */
   protected List< FourByteChunk > recordIDs = new ArrayList< FourByteChunk >(); 

/** Constructor */
 public RecordQueryReliablePdu()
 {
    setPduType( (short)63 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + 4;  // requestID
   marshalSize = marshalSize + 1;  // requiredReliabilityService
   marshalSize = marshalSize + 2;  // pad1
   marshalSize = marshalSize + 1;  // pad2
   marshalSize = marshalSize + 2;  // eventType
   marshalSize = marshalSize + 4;  // time
   marshalSize = marshalSize + 4;  // numberOfRecords
   for(int idx=0; idx < recordIDs.size(); idx++)
   {
        FourByteChunk listElement = recordIDs.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setRequestID(long pRequestID)
{ requestID = pRequestID;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getRequestID()
{ return requestID; 
}

public void setRequiredReliabilityService(short pRequiredReliabilityService)
{ requiredReliabilityService = pRequiredReliabilityService;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getRequiredReliabilityService()
{ return requiredReliabilityService; 
}

public void setPad1(int pPad1)
{ pad1 = pPad1;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPad1()
{ return pad1; 
}

public void setPad2(short pPad2)
{ pad2 = pPad2;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPad2()
{ return pad2; 
}

public void setEventType(int pEventType)
{ eventType = pEventType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getEventType()
{ return eventType; 
}

public void setTime(long pTime)
{ time = pTime;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getTime()
{ return time; 
}

@XmlAttribute
@Basic
public long getNumberOfRecords()
{ return (long)recordIDs.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfRecords method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfRecords(long pNumberOfRecords)
{ numberOfRecords = pNumberOfRecords;
}

public void setRecordIDs(List<FourByteChunk> pRecordIDs)
{ recordIDs = pRecordIDs;
}

@XmlElementWrapper(name="recordIDsList" ) //  Jaxb
@OneToMany    // Hibernate
public List<FourByteChunk> getRecordIDs()
{ return recordIDs; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       dos.writeInt( (int)requestID);
       dos.writeByte( (byte)requiredReliabilityService);
       dos.writeShort( (short)pad1);
       dos.writeByte( (byte)pad2);
       dos.writeShort( (short)eventType);
       dos.writeInt( (int)time);
       dos.writeInt( (int)recordIDs.size());

       for(int idx = 0; idx < recordIDs.size(); idx++)
       {
            FourByteChunk aFourByteChunk = recordIDs.get(idx);
            aFourByteChunk.marshal(dos);
       } // end of list marshalling

    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
     super.unmarshal(dis);

    try 
    {
       requestID = dis.readInt();
       requiredReliabilityService = (short)dis.readUnsignedByte();
       pad1 = (int)dis.readUnsignedShort();
       pad2 = (short)dis.readUnsignedByte();
       eventType = (int)dis.readUnsignedShort();
       time = dis.readInt();
       numberOfRecords = dis.readInt();
       for(int idx = 0; idx < numberOfRecords; idx++)
       {
           FourByteChunk anX = new FourByteChunk();
           anX.unmarshal(dis);
           recordIDs.add(anX);
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
       super.marshal(buff);
       buff.putInt( (int)requestID);
       buff.put( (byte)requiredReliabilityService);
       buff.putShort( (short)pad1);
       buff.put( (byte)pad2);
       buff.putShort( (short)eventType);
       buff.putInt( (int)time);
       buff.putInt( (int)recordIDs.size());

       for(int idx = 0; idx < recordIDs.size(); idx++)
       {
            FourByteChunk aFourByteChunk = (FourByteChunk)recordIDs.get(idx);
            aFourByteChunk.marshal(buff);
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
       super.unmarshal(buff);

       requestID = buff.getInt();
       requiredReliabilityService = (short)(buff.get() & 0xFF);
       pad1 = (int)(buff.getShort() & 0xFFFF);
       pad2 = (short)(buff.get() & 0xFF);
       eventType = (int)(buff.getShort() & 0xFFFF);
       time = buff.getInt();
       numberOfRecords = buff.getInt();
       for(int idx = 0; idx < numberOfRecords; idx++)
       {
            FourByteChunk anX = new FourByteChunk();
            anX.unmarshal(buff);
            recordIDs.add(anX);
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

@Override
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof RecordQueryReliablePdu))
        return false;

     final RecordQueryReliablePdu rhs = (RecordQueryReliablePdu)obj;

     if( ! (requestID == rhs.requestID)) ivarsEqual = false;
     if( ! (requiredReliabilityService == rhs.requiredReliabilityService)) ivarsEqual = false;
     if( ! (pad1 == rhs.pad1)) ivarsEqual = false;
     if( ! (pad2 == rhs.pad2)) ivarsEqual = false;
     if( ! (eventType == rhs.eventType)) ivarsEqual = false;
     if( ! (time == rhs.time)) ivarsEqual = false;
     if( ! (numberOfRecords == rhs.numberOfRecords)) ivarsEqual = false;

     for(int idx = 0; idx < recordIDs.size(); idx++)
     {
        if( ! ( recordIDs.get(idx).equals(rhs.recordIDs.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
