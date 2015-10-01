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
 * A simulation's designation associated with all Live Entity IDs contained in Live Entity PDUs. Section 6.2.55 
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class LiveSimulationAddress extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_LiveSimulationAddress;

   /** facility, installation, organizational unit or geographic location may have multiple sites associated with it. The Site Number is the first component of the Live Simulation Address, which defines a live simulation. */
   protected short  liveSiteNumber;

   /** An application associated with a live site is termed a live application. Each live application participating in an event  */
   protected short  liveApplicationNumber;


/** Constructor */
 public LiveSimulationAddress()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // liveSiteNumber
   marshalSize = marshalSize + 1;  // liveApplicationNumber

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_LiveSimulationAddress()
{
   return pk_LiveSimulationAddress;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_LiveSimulationAddress(long pKeyName)
{
   this.pk_LiveSimulationAddress = pKeyName;
}

public void setLiveSiteNumber(short pLiveSiteNumber)
{ liveSiteNumber = pLiveSiteNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getLiveSiteNumber()
{ return liveSiteNumber; 
}

public void setLiveApplicationNumber(short pLiveApplicationNumber)
{ liveApplicationNumber = pLiveApplicationNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getLiveApplicationNumber()
{ return liveApplicationNumber; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)liveSiteNumber);
       dos.writeByte( (byte)liveApplicationNumber);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       liveSiteNumber = (short)dis.readUnsignedByte();
       liveApplicationNumber = (short)dis.readUnsignedByte();
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
       buff.put( (byte)liveSiteNumber);
       buff.put( (byte)liveApplicationNumber);
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
       liveSiteNumber = (short)(buff.get() & 0xFF);
       liveApplicationNumber = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof LiveSimulationAddress))
        return false;

     final LiveSimulationAddress rhs = (LiveSimulationAddress)obj;

     if( ! (liveSiteNumber == rhs.liveSiteNumber)) ivarsEqual = false;
     if( ! (liveApplicationNumber == rhs.liveApplicationNumber)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
