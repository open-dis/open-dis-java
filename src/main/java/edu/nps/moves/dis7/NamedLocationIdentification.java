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
 * Information about the discrete positional relationship of the part entity with respect to the its host entity Section 6.2.62 
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class NamedLocationIdentification extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_NamedLocationIdentification;

   /** the station name within the host at which the part entity is located. If the part entity is On Station, this field shall specify the representation of the part’s location data fields. This field shall be specified by a 16-bit enumeration  */
   protected int  stationName;

   /** the number of the particular wing station, cargo hold etc., at which the part is attached.  */
   protected int  stationNumber;


/** Constructor */
 public NamedLocationIdentification()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // stationName
   marshalSize = marshalSize + 2;  // stationNumber

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_NamedLocationIdentification()
{
   return pk_NamedLocationIdentification;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_NamedLocationIdentification(long pKeyName)
{
   this.pk_NamedLocationIdentification = pKeyName;
}

public void setStationName(int pStationName)
{ stationName = pStationName;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getStationName()
{ return stationName; 
}

public void setStationNumber(int pStationNumber)
{ stationNumber = pStationNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getStationNumber()
{ return stationNumber; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)stationName);
       dos.writeShort( (short)stationNumber);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       stationName = (int)dis.readUnsignedShort();
       stationNumber = (int)dis.readUnsignedShort();
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
       buff.putShort( (short)stationName);
       buff.putShort( (short)stationNumber);
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
       stationName = (int)(buff.getShort() & 0xFFFF);
       stationNumber = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof NamedLocationIdentification))
        return false;

     final NamedLocationIdentification rhs = (NamedLocationIdentification)obj;

     if( ! (stationName == rhs.stationName)) ivarsEqual = false;
     if( ! (stationNumber == rhs.stationNumber)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
