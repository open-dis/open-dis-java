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
 * Association or disassociation of two entities.  Section 6.2.94.5
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class EntityTypeVP extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_EntityTypeVP;

   /** the identification of the Variable Parameter record. Enumeration from EBV */
   protected short  recordType = (short)3;

   /** Indicates if this VP has changed since last issuance */
   protected short  changeIndicator = (short)0;

   /**  */
   protected EntityType  entityType = new EntityType(); 

   /** padding */
   protected int  padding = (int)0;

   /** padding */
   protected long  padding1 = (long)0;


/** Constructor */
 public EntityTypeVP()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // recordType
   marshalSize = marshalSize + 1;  // changeIndicator
   marshalSize = marshalSize + entityType.getMarshalledSize();  // entityType
   marshalSize = marshalSize + 2;  // padding
   marshalSize = marshalSize + 4;  // padding1

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_EntityTypeVP()
{
   return pk_EntityTypeVP;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_EntityTypeVP(long pKeyName)
{
   this.pk_EntityTypeVP = pKeyName;
}

public void setRecordType(short pRecordType)
{ recordType = pRecordType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getRecordType()
{ return recordType; 
}

public void setChangeIndicator(short pChangeIndicator)
{ changeIndicator = pChangeIndicator;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getChangeIndicator()
{ return changeIndicator; 
}

public void setEntityType(EntityType pEntityType)
{ entityType = pEntityType;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_entityType;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_entityType")
public EntityType getEntityType()
{ return entityType; 
}

public void setPadding(int pPadding)
{ padding = pPadding;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding()
{ return padding; 
}

public void setPadding1(long pPadding1)
{ padding1 = pPadding1;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getPadding1()
{ return padding1; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)recordType);
       dos.writeByte( (byte)changeIndicator);
       entityType.marshal(dos);
       dos.writeShort( (short)padding);
       dos.writeInt( (int)padding1);
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
       changeIndicator = (short)dis.readUnsignedByte();
       entityType.unmarshal(dis);
       padding = (int)dis.readUnsignedShort();
       padding1 = dis.readInt();
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
       buff.put( (byte)changeIndicator);
       entityType.marshal(buff);
       buff.putShort( (short)padding);
       buff.putInt( (int)padding1);
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
       changeIndicator = (short)(buff.get() & 0xFF);
       entityType.unmarshal(buff);
       padding = (int)(buff.getShort() & 0xFFFF);
       padding1 = buff.getInt();
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

    if(!(obj instanceof EntityTypeVP))
        return false;

     final EntityTypeVP rhs = (EntityTypeVP)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (changeIndicator == rhs.changeIndicator)) ivarsEqual = false;
     if( ! (entityType.equals( rhs.entityType) )) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (padding1 == rhs.padding1)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
