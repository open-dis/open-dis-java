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
 * The unique designation of an environmental object. Section 6.2.64
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class ObjectType extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_ObjectType;

   /** Domain of entity (air, surface, subsurface, space, etc) */
   protected short  domain;

   /** country to which the design of the entity is attributed */
   protected short  objectKind;

   /** category of entity */
   protected short  category;

   /** subcategory of entity */
   protected short  subcategory;


/** Constructor */
 public ObjectType()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // domain
   marshalSize = marshalSize + 1;  // objectKind
   marshalSize = marshalSize + 1;  // category
   marshalSize = marshalSize + 1;  // subcategory

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_ObjectType()
{
   return pk_ObjectType;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_ObjectType(long pKeyName)
{
   this.pk_ObjectType = pKeyName;
}

public void setDomain(short pDomain)
{ domain = pDomain;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getDomain()
{ return domain; 
}

public void setObjectKind(short pObjectKind)
{ objectKind = pObjectKind;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getObjectKind()
{ return objectKind; 
}

public void setCategory(short pCategory)
{ category = pCategory;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getCategory()
{ return category; 
}

public void setSubcategory(short pSubcategory)
{ subcategory = pSubcategory;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getSubcategory()
{ return subcategory; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)domain);
       dos.writeByte( (byte)objectKind);
       dos.writeByte( (byte)category);
       dos.writeByte( (byte)subcategory);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       domain = (short)dis.readUnsignedByte();
       objectKind = (short)dis.readUnsignedByte();
       category = (short)dis.readUnsignedByte();
       subcategory = (short)dis.readUnsignedByte();
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
       buff.put( (byte)domain);
       buff.put( (byte)objectKind);
       buff.put( (byte)category);
       buff.put( (byte)subcategory);
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
       domain = (short)(buff.get() & 0xFF);
       objectKind = (short)(buff.get() & 0xFF);
       category = (short)(buff.get() & 0xFF);
       subcategory = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof ObjectType))
        return false;

     final ObjectType rhs = (ObjectType)obj;

     if( ! (domain == rhs.domain)) ivarsEqual = false;
     if( ! (objectKind == rhs.objectKind)) ivarsEqual = false;
     if( ! (category == rhs.category)) ivarsEqual = false;
     if( ! (subcategory == rhs.subcategory)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
