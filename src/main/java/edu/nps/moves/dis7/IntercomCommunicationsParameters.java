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
 * Intercom communcations parameters. Section 6.2.47.  This requires hand coding
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class IntercomCommunicationsParameters extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_IntercomCommunicationsParameters;

   /** Type of intercom parameters record */
   protected int  recordType;

   /** length of record */
   protected int  recordLength;

   /** This is a placeholder. */
   protected long  recordSpecificField;


/** Constructor */
 public IntercomCommunicationsParameters()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // recordType
   marshalSize = marshalSize + 2;  // recordLength
   marshalSize = marshalSize + 4;  // recordSpecificField

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_IntercomCommunicationsParameters()
{
   return pk_IntercomCommunicationsParameters;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_IntercomCommunicationsParameters(long pKeyName)
{
   this.pk_IntercomCommunicationsParameters = pKeyName;
}

public void setRecordType(int pRecordType)
{ recordType = pRecordType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getRecordType()
{ return recordType; 
}

public void setRecordLength(int pRecordLength)
{ recordLength = pRecordLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getRecordLength()
{ return recordLength; 
}

public void setRecordSpecificField(long pRecordSpecificField)
{ recordSpecificField = pRecordSpecificField;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getRecordSpecificField()
{ return recordSpecificField; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)recordType);
       dos.writeShort( (short)recordLength);
       dos.writeInt( (int)recordSpecificField);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       recordType = (int)dis.readUnsignedShort();
       recordLength = (int)dis.readUnsignedShort();
       recordSpecificField = dis.readInt();
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
       buff.putShort( (short)recordType);
       buff.putShort( (short)recordLength);
       buff.putInt( (int)recordSpecificField);
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
       recordType = (int)(buff.getShort() & 0xFFFF);
       recordLength = (int)(buff.getShort() & 0xFFFF);
       recordSpecificField = buff.getInt();
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

    if(!(obj instanceof IntercomCommunicationsParameters))
        return false;

     final IntercomCommunicationsParameters rhs = (IntercomCommunicationsParameters)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (recordLength == rhs.recordLength)) ivarsEqual = false;
     if( ! (recordSpecificField == rhs.recordSpecificField)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
