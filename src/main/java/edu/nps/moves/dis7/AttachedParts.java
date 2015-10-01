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
 * Removable parts that may be attached to an entity.  Section 6.2.93.3
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class AttachedParts extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_AttachedParts;

   /** the identification of the Variable Parameter record. Enumeration from EBV */
   protected short  recordType = (short)1;

   /** 0 = attached, 1 = detached. See I.2.3.1 for state transition diagram */
   protected short  detachedIndicator = (short)0;

   /** the identification of the articulated part to which this articulation parameter is attached. This field shall be specified by a 16-bit unsigned integer. This field shall contain the value zero if the articulated part is attached directly to the entity. */
   protected int  partAttachedTo = (int)0;

   /** The location or station to which the part is attached */
   protected long  parameterType;

   /** The definition of the 64 bits shall be determined based on the type of parameter specified in the Parameter Type field  */
   protected long  parameterValue;


/** Constructor */
 public AttachedParts()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // recordType
   marshalSize = marshalSize + 1;  // detachedIndicator
   marshalSize = marshalSize + 2;  // partAttachedTo
   marshalSize = marshalSize + 4;  // parameterType
   marshalSize = marshalSize + 8;  // parameterValue

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_AttachedParts()
{
   return pk_AttachedParts;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_AttachedParts(long pKeyName)
{
   this.pk_AttachedParts = pKeyName;
}

public void setRecordType(short pRecordType)
{ recordType = pRecordType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getRecordType()
{ return recordType; 
}

public void setDetachedIndicator(short pDetachedIndicator)
{ detachedIndicator = pDetachedIndicator;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getDetachedIndicator()
{ return detachedIndicator; 
}

public void setPartAttachedTo(int pPartAttachedTo)
{ partAttachedTo = pPartAttachedTo;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPartAttachedTo()
{ return partAttachedTo; 
}

public void setParameterType(long pParameterType)
{ parameterType = pParameterType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getParameterType()
{ return parameterType; 
}

public void setParameterValue(long pParameterValue)
{ parameterValue = pParameterValue;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getParameterValue()
{ return parameterValue; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)recordType);
       dos.writeByte( (byte)detachedIndicator);
       dos.writeShort( (short)partAttachedTo);
       dos.writeInt( (int)parameterType);
       dos.writeLong( (long)parameterValue);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       recordType = (short)dis.readUnsignedByte();
       detachedIndicator = (short)dis.readUnsignedByte();
       partAttachedTo = (int)dis.readUnsignedShort();
       parameterType = dis.readInt();
       parameterValue = dis.readLong();
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
       buff.put( (byte)recordType);
       buff.put( (byte)detachedIndicator);
       buff.putShort( (short)partAttachedTo);
       buff.putInt( (int)parameterType);
       buff.putLong( (long)parameterValue);
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
       recordType = (short)(buff.get() & 0xFF);
       detachedIndicator = (short)(buff.get() & 0xFF);
       partAttachedTo = (int)(buff.getShort() & 0xFFFF);
       parameterType = buff.getInt();
       parameterValue = buff.getLong();
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

    if(!(obj instanceof AttachedParts))
        return false;

     final AttachedParts rhs = (AttachedParts)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (detachedIndicator == rhs.detachedIndicator)) ivarsEqual = false;
     if( ! (partAttachedTo == rhs.partAttachedTo)) ivarsEqual = false;
     if( ! (parameterType == rhs.parameterType)) ivarsEqual = false;
     if( ! (parameterValue == rhs.parameterValue)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
