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
 * Not used. The PDU Header Record is directly incoroporated into the PDU class. Here for completness only. Section 6.2.66
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class PduHeader extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_PduHeader;

   /** The version of the protocol. 5=DIS-1995, 6=DIS-1998, 7=DIS-2009. */
   protected short  protocolVersion = (short)7;

   /** Exercise ID */
   protected short  exerciseID = (short)0;

   /** Type of pdu, unique for each PDU class */
   protected short  pduType;

   /** value that refers to the protocol family, eg SimulationManagement, etc */
   protected short  protocolFamily;

   /** Timestamp value */
   protected long  timestamp;

   /** Length, in bytes, of the PDU. Changed name from length to avoid use of Hibernate QL reserved word. */
   protected short  pduLength;

   /** PDU Status Record. Described in 6.2.67. This field is not present in earlier DIS versions  */
   protected int  pduStatus;

   /** zero filled array of padding */
   protected short  padding = (short)0;


/** Constructor */
 public PduHeader()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // protocolVersion
   marshalSize = marshalSize + 1;  // exerciseID
   marshalSize = marshalSize + 1;  // pduType
   marshalSize = marshalSize + 1;  // protocolFamily
   marshalSize = marshalSize + 4;  // timestamp
   marshalSize = marshalSize + 1;  // pduLength
   marshalSize = marshalSize + 2;  // pduStatus
   marshalSize = marshalSize + 1;  // padding

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_PduHeader()
{
   return pk_PduHeader;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_PduHeader(long pKeyName)
{
   this.pk_PduHeader = pKeyName;
}

public void setProtocolVersion(short pProtocolVersion)
{ protocolVersion = pProtocolVersion;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getProtocolVersion()
{ return protocolVersion; 
}

public void setExerciseID(short pExerciseID)
{ exerciseID = pExerciseID;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getExerciseID()
{ return exerciseID; 
}

public void setPduType(short pPduType)
{ pduType = pPduType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPduType()
{ return pduType; 
}

public void setProtocolFamily(short pProtocolFamily)
{ protocolFamily = pProtocolFamily;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getProtocolFamily()
{ return protocolFamily; 
}

public void setTimestamp(long pTimestamp)
{ timestamp = pTimestamp;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getTimestamp()
{ return timestamp; 
}

public void setPduLength(short pPduLength)
{ pduLength = pPduLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPduLength()
{ return pduLength; 
}

public void setPduStatus(int pPduStatus)
{ pduStatus = pPduStatus;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPduStatus()
{ return pduStatus; 
}

public void setPadding(short pPadding)
{ padding = pPadding;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPadding()
{ return padding; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)protocolVersion);
       dos.writeByte( (byte)exerciseID);
       dos.writeByte( (byte)pduType);
       dos.writeByte( (byte)protocolFamily);
       dos.writeInt( (int)timestamp);
       dos.writeByte( (byte)pduLength);
       dos.writeShort( (short)pduStatus);
       dos.writeByte( (byte)padding);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       protocolVersion = (short)dis.readUnsignedByte();
       exerciseID = (short)dis.readUnsignedByte();
       pduType = (short)dis.readUnsignedByte();
       protocolFamily = (short)dis.readUnsignedByte();
       timestamp = dis.readInt();
       pduLength = (short)dis.readUnsignedByte();
       pduStatus = (int)dis.readUnsignedShort();
       padding = (short)dis.readUnsignedByte();
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
       buff.put( (byte)protocolVersion);
       buff.put( (byte)exerciseID);
       buff.put( (byte)pduType);
       buff.put( (byte)protocolFamily);
       buff.putInt( (int)timestamp);
       buff.put( (byte)pduLength);
       buff.putShort( (short)pduStatus);
       buff.put( (byte)padding);
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
       protocolVersion = (short)(buff.get() & 0xFF);
       exerciseID = (short)(buff.get() & 0xFF);
       pduType = (short)(buff.get() & 0xFF);
       protocolFamily = (short)(buff.get() & 0xFF);
       timestamp = buff.getInt();
       pduLength = (short)(buff.get() & 0xFF);
       pduStatus = (int)(buff.getShort() & 0xFFFF);
       padding = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof PduHeader))
        return false;

     final PduHeader rhs = (PduHeader)obj;

     if( ! (protocolVersion == rhs.protocolVersion)) ivarsEqual = false;
     if( ! (exerciseID == rhs.exerciseID)) ivarsEqual = false;
     if( ! (pduType == rhs.pduType)) ivarsEqual = false;
     if( ! (protocolFamily == rhs.protocolFamily)) ivarsEqual = false;
     if( ! (timestamp == rhs.timestamp)) ivarsEqual = false;
     if( ! (pduLength == rhs.pduLength)) ivarsEqual = false;
     if( ! (pduStatus == rhs.pduStatus)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
