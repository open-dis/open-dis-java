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
 * Explosion of a non-munition. Section 6.2.19.3
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class ExplosionDescriptor extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_ExplosionDescriptor;

   /** Type of the object that exploded. See 6.2.30 */
   protected EntityType  explodingObject = new EntityType(); 

   /** Material that exploded. Can be grain dust, tnt, gasoline, etc. Enumeration */
   protected int  explosiveMaterial;

   /** padding */
   protected int  padding = (int)0;

   /** Force of explosion, in equivalent KG of TNT */
   protected float  explosiveForce;


/** Constructor */
 public ExplosionDescriptor()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + explodingObject.getMarshalledSize();  // explodingObject
   marshalSize = marshalSize + 2;  // explosiveMaterial
   marshalSize = marshalSize + 2;  // padding
   marshalSize = marshalSize + 4;  // explosiveForce

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_ExplosionDescriptor()
{
   return pk_ExplosionDescriptor;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_ExplosionDescriptor(long pKeyName)
{
   this.pk_ExplosionDescriptor = pKeyName;
}

public void setExplodingObject(EntityType pExplodingObject)
{ explodingObject = pExplodingObject;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_explodingObject;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_explodingObject")
public EntityType getExplodingObject()
{ return explodingObject; 
}

public void setExplosiveMaterial(int pExplosiveMaterial)
{ explosiveMaterial = pExplosiveMaterial;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getExplosiveMaterial()
{ return explosiveMaterial; 
}

public void setPadding(int pPadding)
{ padding = pPadding;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding()
{ return padding; 
}

public void setExplosiveForce(float pExplosiveForce)
{ explosiveForce = pExplosiveForce;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getExplosiveForce()
{ return explosiveForce; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       explodingObject.marshal(dos);
       dos.writeShort( (short)explosiveMaterial);
       dos.writeShort( (short)padding);
       dos.writeFloat( (float)explosiveForce);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       explodingObject.unmarshal(dis);
       explosiveMaterial = (int)dis.readUnsignedShort();
       padding = (int)dis.readUnsignedShort();
       explosiveForce = dis.readFloat();
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
       explodingObject.marshal(buff);
       buff.putShort( (short)explosiveMaterial);
       buff.putShort( (short)padding);
       buff.putFloat( (float)explosiveForce);
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
       explodingObject.unmarshal(buff);
       explosiveMaterial = (int)(buff.getShort() & 0xFFFF);
       padding = (int)(buff.getShort() & 0xFFFF);
       explosiveForce = buff.getFloat();
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

    if(!(obj instanceof ExplosionDescriptor))
        return false;

     final ExplosionDescriptor rhs = (ExplosionDescriptor)obj;

     if( ! (explodingObject.equals( rhs.explodingObject) )) ivarsEqual = false;
     if( ! (explosiveMaterial == rhs.explosiveMaterial)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (explosiveForce == rhs.explosiveForce)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
