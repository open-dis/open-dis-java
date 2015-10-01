package edu.nps.moves.dis;

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
 * 5.2.44: Grid data record, a common abstract superclass for several subtypes 
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class GridAxisRecord extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_GridAxisRecord;

   /** type of environmental sample */
   protected int  sampleType;

   /** value that describes data representation */
   protected int  dataRepresentation;


/** Constructor */
 public GridAxisRecord()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // sampleType
   marshalSize = marshalSize + 2;  // dataRepresentation

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_GridAxisRecord()
{
   return pk_GridAxisRecord;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_GridAxisRecord(long pKeyName)
{
   this.pk_GridAxisRecord = pKeyName;
}

public void setSampleType(int pSampleType)
{ sampleType = pSampleType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getSampleType()
{ return sampleType; 
}

public void setDataRepresentation(int pDataRepresentation)
{ dataRepresentation = pDataRepresentation;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getDataRepresentation()
{ return dataRepresentation; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)sampleType);
       dos.writeShort( (short)dataRepresentation);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       sampleType = (int)dis.readUnsignedShort();
       dataRepresentation = (int)dis.readUnsignedShort();
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
       buff.putShort( (short)sampleType);
       buff.putShort( (short)dataRepresentation);
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
       sampleType = (int)(buff.getShort() & 0xFFFF);
       dataRepresentation = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof GridAxisRecord))
        return false;

     final GridAxisRecord rhs = (GridAxisRecord)obj;

     if( ! (sampleType == rhs.sampleType)) ivarsEqual = false;
     if( ! (dataRepresentation == rhs.dataRepresentation)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
