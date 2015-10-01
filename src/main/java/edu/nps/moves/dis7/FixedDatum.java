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
 * Fixed Datum Record. Section 6.2.38
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class FixedDatum extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_FixedDatum;

   /** ID of the fixed datum, an enumeration */
   protected long  fixedDatumID;

   /** Value for the fixed datum */
   protected long  fixedDatumValue;


/** Constructor */
 public FixedDatum()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // fixedDatumID
   marshalSize = marshalSize + 4;  // fixedDatumValue

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_FixedDatum()
{
   return pk_FixedDatum;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_FixedDatum(long pKeyName)
{
   this.pk_FixedDatum = pKeyName;
}

public void setFixedDatumID(long pFixedDatumID)
{ fixedDatumID = pFixedDatumID;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getFixedDatumID()
{ return fixedDatumID; 
}

public void setFixedDatumValue(long pFixedDatumValue)
{ fixedDatumValue = pFixedDatumValue;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getFixedDatumValue()
{ return fixedDatumValue; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)fixedDatumID);
       dos.writeInt( (int)fixedDatumValue);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       fixedDatumID = dis.readInt();
       fixedDatumValue = dis.readInt();
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
       buff.putInt( (int)fixedDatumID);
       buff.putInt( (int)fixedDatumValue);
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
       fixedDatumID = buff.getInt();
       fixedDatumValue = buff.getInt();
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

    if(!(obj instanceof FixedDatum))
        return false;

     final FixedDatum rhs = (FixedDatum)obj;

     if( ! (fixedDatumID == rhs.fixedDatumID)) ivarsEqual = false;
     if( ! (fixedDatumValue == rhs.fixedDatumValue)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
