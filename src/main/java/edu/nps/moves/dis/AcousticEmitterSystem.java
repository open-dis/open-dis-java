package edu.nps.moves.dis;

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
 * 5.3.35: Information about a particular UA emitter shall be represented using an Acoustic Emitter System record. This record shall consist of three fields: Acoustic Name, Function, and Acoustic ID Number
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class AcousticEmitterSystem extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_AcousticEmitterSystem;

   /** This field shall specify the system for a particular UA emitter. */
   protected int  acousticName;

   /** This field shall describe the function of the acoustic system.  */
   protected short  acousticFunction;

   /** This field shall specify the UA emitter identification number relative to a specific system. This field shall be represented by an 8-bit unsigned integer. This field allows the differentiation of multiple systems on an entity, even if in some instances two or more of the systems may be identical UA emitter types. Numbering of systems shall begin with the value 1.  */
   protected short  acousticID;


/** Constructor */
 public AcousticEmitterSystem()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // acousticName
   marshalSize = marshalSize + 1;  // acousticFunction
   marshalSize = marshalSize + 1;  // acousticID

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_AcousticEmitterSystem()
{
   return pk_AcousticEmitterSystem;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_AcousticEmitterSystem(long pKeyName)
{
   this.pk_AcousticEmitterSystem = pKeyName;
}

public void setAcousticName(int pAcousticName)
{ acousticName = pAcousticName;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getAcousticName()
{ return acousticName; 
}

public void setAcousticFunction(short pAcousticFunction)
{ acousticFunction = pAcousticFunction;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getAcousticFunction()
{ return acousticFunction; 
}

public void setAcousticID(short pAcousticID)
{ acousticID = pAcousticID;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getAcousticID()
{ return acousticID; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)acousticName);
       dos.writeByte( (byte)acousticFunction);
       dos.writeByte( (byte)acousticID);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       acousticName = (int)dis.readUnsignedShort();
       acousticFunction = (short)dis.readUnsignedByte();
       acousticID = (short)dis.readUnsignedByte();
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
       buff.putShort( (short)acousticName);
       buff.put( (byte)acousticFunction);
       buff.put( (byte)acousticID);
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
       acousticName = (int)(buff.getShort() & 0xFFFF);
       acousticFunction = (short)(buff.get() & 0xFF);
       acousticID = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof AcousticEmitterSystem))
        return false;

     final AcousticEmitterSystem rhs = (AcousticEmitterSystem)obj;

     if( ! (acousticName == rhs.acousticName)) ivarsEqual = false;
     if( ! (acousticFunction == rhs.acousticFunction)) ivarsEqual = false;
     if( ! (acousticID == rhs.acousticID)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
