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
 * An entity's associations with other entities and/or locations. For each association, this record shall specify the type of the association, the associated entity's EntityID and/or the associated location's world coordinates. This record may be used (optionally) in a transfer transaction to send internal state data from the divesting simulation to the acquiring simulation (see 5.9.4). This record may also be used for other purposes. Section 6.2.9
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class Association extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_Association;

   protected short  associationType;

   protected short  padding4;

   /** identity of associated entity. If none, NO_SPECIFIC_ENTITY */
   protected EntityID  associatedEntityID = new EntityID(); 

   /** location, in world coordinates */
   protected Vector3Double  associatedLocation = new Vector3Double(); 


/** Constructor */
 public Association()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // associationType
   marshalSize = marshalSize + 1;  // padding4
   marshalSize = marshalSize + associatedEntityID.getMarshalledSize();  // associatedEntityID
   marshalSize = marshalSize + associatedLocation.getMarshalledSize();  // associatedLocation

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_Association()
{
   return pk_Association;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_Association(long pKeyName)
{
   this.pk_Association = pKeyName;
}

public void setAssociationType(short pAssociationType)
{ associationType = pAssociationType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getAssociationType()
{ return associationType; 
}

public void setPadding4(short pPadding4)
{ padding4 = pPadding4;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPadding4()
{ return padding4; 
}

public void setAssociatedEntityID(EntityID pAssociatedEntityID)
{ associatedEntityID = pAssociatedEntityID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_associatedEntityID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_associatedEntityID")
public EntityID getAssociatedEntityID()
{ return associatedEntityID; 
}

public void setAssociatedLocation(Vector3Double pAssociatedLocation)
{ associatedLocation = pAssociatedLocation;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_associatedLocation;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_associatedLocation")
public Vector3Double getAssociatedLocation()
{ return associatedLocation; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)associationType);
       dos.writeByte( (byte)padding4);
       associatedEntityID.marshal(dos);
       associatedLocation.marshal(dos);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       associationType = (short)dis.readUnsignedByte();
       padding4 = (short)dis.readUnsignedByte();
       associatedEntityID.unmarshal(dis);
       associatedLocation.unmarshal(dis);
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
       buff.put( (byte)associationType);
       buff.put( (byte)padding4);
       associatedEntityID.marshal(buff);
       associatedLocation.marshal(buff);
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
       associationType = (short)(buff.get() & 0xFF);
       padding4 = (short)(buff.get() & 0xFF);
       associatedEntityID.unmarshal(buff);
       associatedLocation.unmarshal(buff);
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

    if(!(obj instanceof Association))
        return false;

     final Association rhs = (Association)obj;

     if( ! (associationType == rhs.associationType)) ivarsEqual = false;
     if( ! (padding4 == rhs.padding4)) ivarsEqual = false;
     if( ! (associatedEntityID.equals( rhs.associatedEntityID) )) ivarsEqual = false;
     if( ! (associatedLocation.equals( rhs.associatedLocation) )) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
