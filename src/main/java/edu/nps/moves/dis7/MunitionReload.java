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
 * indicate weapons (munitions) previously communicated via the Munition record. Section 6.2.61 
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class MunitionReload extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_MunitionReload;

   /**  This field shall identify the entity type of the munition. See section 6.2.30. */
   protected EntityType  munitionType = new EntityType(); 

   /** the station or launcher to which the munition is assigned. See Annex I */
   protected long  station;

   /** the standard quantity of this munition type normally loaded at this station/launcher if a station/launcher is specified. */
   protected int  standardQuantity;

   /** the maximum quantity of this munition type that this station/launcher is capable of holding when a station/launcher is specified  */
   protected int  maximumQuantity;

   /** numer of seconds of sim time required to reload the std qty */
   protected long  standardQuantityReloadTime;

   /** the number of seconds of sim time required to reload the max possible quantity */
   protected long  maximumQuantityReloadTime;


/** Constructor */
 public MunitionReload()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + munitionType.getMarshalledSize();  // munitionType
   marshalSize = marshalSize + 4;  // station
   marshalSize = marshalSize + 2;  // standardQuantity
   marshalSize = marshalSize + 2;  // maximumQuantity
   marshalSize = marshalSize + 4;  // standardQuantityReloadTime
   marshalSize = marshalSize + 4;  // maximumQuantityReloadTime

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_MunitionReload()
{
   return pk_MunitionReload;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_MunitionReload(long pKeyName)
{
   this.pk_MunitionReload = pKeyName;
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

public void setStandardQuantity(int pStandardQuantity)
{ standardQuantity = pStandardQuantity;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getStandardQuantity()
{ return standardQuantity; 
}

public void setMaximumQuantity(int pMaximumQuantity)
{ maximumQuantity = pMaximumQuantity;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getMaximumQuantity()
{ return maximumQuantity; 
}

public void setStandardQuantityReloadTime(long pStandardQuantityReloadTime)
{ standardQuantityReloadTime = pStandardQuantityReloadTime;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getStandardQuantityReloadTime()
{ return standardQuantityReloadTime; 
}

public void setMaximumQuantityReloadTime(long pMaximumQuantityReloadTime)
{ maximumQuantityReloadTime = pMaximumQuantityReloadTime;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getMaximumQuantityReloadTime()
{ return maximumQuantityReloadTime; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       munitionType.marshal(dos);
       dos.writeInt( (int)station);
       dos.writeShort( (short)standardQuantity);
       dos.writeShort( (short)maximumQuantity);
       dos.writeInt( (int)standardQuantityReloadTime);
       dos.writeInt( (int)maximumQuantityReloadTime);
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
       standardQuantity = (int)dis.readUnsignedShort();
       maximumQuantity = (int)dis.readUnsignedShort();
       standardQuantityReloadTime = dis.readInt();
       maximumQuantityReloadTime = dis.readInt();
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
       buff.putShort( (short)standardQuantity);
       buff.putShort( (short)maximumQuantity);
       buff.putInt( (int)standardQuantityReloadTime);
       buff.putInt( (int)maximumQuantityReloadTime);
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
       standardQuantity = (int)(buff.getShort() & 0xFFFF);
       maximumQuantity = (int)(buff.getShort() & 0xFFFF);
       standardQuantityReloadTime = buff.getInt();
       maximumQuantityReloadTime = buff.getInt();
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

    if(!(obj instanceof MunitionReload))
        return false;

     final MunitionReload rhs = (MunitionReload)obj;

     if( ! (munitionType.equals( rhs.munitionType) )) ivarsEqual = false;
     if( ! (station == rhs.station)) ivarsEqual = false;
     if( ! (standardQuantity == rhs.standardQuantity)) ivarsEqual = false;
     if( ! (maximumQuantity == rhs.maximumQuantity)) ivarsEqual = false;
     if( ! (standardQuantityReloadTime == rhs.standardQuantityReloadTime)) ivarsEqual = false;
     if( ! (maximumQuantityReloadTime == rhs.maximumQuantityReloadTime)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
