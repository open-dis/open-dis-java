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
 * used to convey entity and conflict status information associated with transferring ownership of an entity. Section 6.2.65
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class OwnershipStatus extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_OwnershipStatus;

   /** EntityID */
   protected EntityID  entityId = new EntityID(); 

   /** The ownership and/or ownership conflict status of the entity represented by the Entity ID field. */
   protected short  ownershipStatus;

   /** padding */
   protected short  padding = (short)0;


/** Constructor */
 public OwnershipStatus()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + entityId.getMarshalledSize();  // entityId
   marshalSize = marshalSize + 1;  // ownershipStatus
   marshalSize = marshalSize + 1;  // padding

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_OwnershipStatus()
{
   return pk_OwnershipStatus;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_OwnershipStatus(long pKeyName)
{
   this.pk_OwnershipStatus = pKeyName;
}

public void setEntityId(EntityID pEntityId)
{ entityId = pEntityId;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_entityId;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_entityId")
public EntityID getEntityId()
{ return entityId; 
}

public void setOwnershipStatus(short pOwnershipStatus)
{ ownershipStatus = pOwnershipStatus;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getOwnershipStatus()
{ return ownershipStatus; 
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
       entityId.marshal(dos);
       dos.writeByte( (byte)ownershipStatus);
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
       entityId.unmarshal(dis);
       ownershipStatus = (short)dis.readUnsignedByte();
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
       entityId.marshal(buff);
       buff.put( (byte)ownershipStatus);
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
       entityId.unmarshal(buff);
       ownershipStatus = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof OwnershipStatus))
        return false;

     final OwnershipStatus rhs = (OwnershipStatus)obj;

     if( ! (entityId.equals( rhs.entityId) )) ivarsEqual = false;
     if( ! (ownershipStatus == rhs.ownershipStatus)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
