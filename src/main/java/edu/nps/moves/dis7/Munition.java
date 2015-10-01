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
 * An entity's munition (e.g., bomb, missile) information shall be represented by one or more Munition records. For each type or location of munition, this record shall specify the type, location, quantity and status of munitions that an entity contains. Section 6.2.60 
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class Munition extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_Munition;

   /**  This field shall identify the entity type of the munition. See section 6.2.30. */
   protected EntityType  munitionType = new EntityType(); 

   /** the station or launcher to which the munition is assigned. See Annex I */
   protected long  station;

   /** the quantity remaining of this munition. */
   protected int  quantity;

   /**  the status of the munition. It shall be represented by an 8-bit enumeration.  */
   protected short  munitionStatus;

   /** padding  */
   protected short  padding = (short)0;


/** Constructor */
 public Munition()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + munitionType.getMarshalledSize();  // munitionType
   marshalSize = marshalSize + 4;  // station
   marshalSize = marshalSize + 2;  // quantity
   marshalSize = marshalSize + 1;  // munitionStatus
   marshalSize = marshalSize + 1;  // padding

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_Munition()
{
   return pk_Munition;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_Munition(long pKeyName)
{
   this.pk_Munition = pKeyName;
}

public void setMunitionType(EntityType pMunitionType)
{ munitionType = pMunitionType;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_munitionType;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_munitionType")
public EntityType getMunitionType()
{ return munitionType; 
}

public void setStation(long pStation)
{ station = pStation;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getStation()
{ return station; 
}

public void setQuantity(int pQuantity)
{ quantity = pQuantity;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getQuantity()
{ return quantity; 
}

public void setMunitionStatus(short pMunitionStatus)
{ munitionStatus = pMunitionStatus;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getMunitionStatus()
{ return munitionStatus; 
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
       munitionType.marshal(dos);
       dos.writeInt( (int)station);
       dos.writeShort( (short)quantity);
       dos.writeByte( (byte)munitionStatus);
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
       munitionType.unmarshal(dis);
       station = dis.readInt();
       quantity = (int)dis.readUnsignedShort();
       munitionStatus = (short)dis.readUnsignedByte();
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
       munitionType.marshal(buff);
       buff.putInt( (int)station);
       buff.putShort( (short)quantity);
       buff.put( (byte)munitionStatus);
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
       munitionType.unmarshal(buff);
       station = buff.getInt();
       quantity = (int)(buff.getShort() & 0xFFFF);
       munitionStatus = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof Munition))
        return false;

     final Munition rhs = (Munition)obj;

     if( ! (munitionType.equals( rhs.munitionType) )) ivarsEqual = false;
     if( ! (station == rhs.station)) ivarsEqual = false;
     if( ! (quantity == rhs.quantity)) ivarsEqual = false;
     if( ! (munitionStatus == rhs.munitionStatus)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
