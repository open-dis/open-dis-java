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
 * Section 5.2.5. Articulation parameters for  movable parts and attached parts of an entity. Specifes wether or not a change has occured,  the part identifcation of the articulated part to which it is attached, and the type and value of each parameter.
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class ArticulationParameter extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_ArticulationParameter;

   protected short  parameterTypeDesignator;

   protected short  changeIndicator;

   protected int  partAttachedTo;

   protected int  parameterType;

   protected double  parameterValue;


/** Constructor */
 public ArticulationParameter()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // parameterTypeDesignator
   marshalSize = marshalSize + 1;  // changeIndicator
   marshalSize = marshalSize + 2;  // partAttachedTo
   marshalSize = marshalSize + 4;  // parameterType
   marshalSize = marshalSize + 8;  // parameterValue

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_ArticulationParameter()
{
   return pk_ArticulationParameter;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_ArticulationParameter(long pKeyName)
{
   this.pk_ArticulationParameter = pKeyName;
}

public void setParameterTypeDesignator(short pParameterTypeDesignator)
{ parameterTypeDesignator = pParameterTypeDesignator;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getParameterTypeDesignator()
{ return parameterTypeDesignator; 
}

public void setChangeIndicator(short pChangeIndicator)
{ changeIndicator = pChangeIndicator;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getChangeIndicator()
{ return changeIndicator; 
}

public void setPartAttachedTo(int pPartAttachedTo)
{ partAttachedTo = pPartAttachedTo;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPartAttachedTo()
{ return partAttachedTo; 
}

public void setParameterType(int pParameterType)
{ parameterType = pParameterType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getParameterType()
{ return parameterType; 
}

public void setParameterValue(double pParameterValue)
{ parameterValue = pParameterValue;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public double getParameterValue()
{ return parameterValue; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)parameterTypeDesignator);
       dos.writeByte( (byte)changeIndicator);
       dos.writeShort( (short)partAttachedTo);
       dos.writeInt( (int)parameterType);
       dos.writeDouble( (double)parameterValue);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       parameterTypeDesignator = (short)dis.readUnsignedByte();
       changeIndicator = (short)dis.readUnsignedByte();
       partAttachedTo = (int)dis.readUnsignedShort();
       parameterType = dis.readInt();
       parameterValue = dis.readDouble();
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
       buff.put( (byte)parameterTypeDesignator);
       buff.put( (byte)changeIndicator);
       buff.putShort( (short)partAttachedTo);
       buff.putInt( (int)parameterType);
       buff.putDouble( (double)parameterValue);
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
       parameterTypeDesignator = (short)(buff.get() & 0xFF);
       changeIndicator = (short)(buff.get() & 0xFF);
       partAttachedTo = (int)(buff.getShort() & 0xFFFF);
       parameterType = buff.getInt();
       parameterValue = buff.getDouble();
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

    if(!(obj instanceof ArticulationParameter))
        return false;

     final ArticulationParameter rhs = (ArticulationParameter)obj;

     if( ! (parameterTypeDesignator == rhs.parameterTypeDesignator)) ivarsEqual = false;
     if( ! (changeIndicator == rhs.changeIndicator)) ivarsEqual = false;
     if( ! (partAttachedTo == rhs.partAttachedTo)) ivarsEqual = false;
     if( ! (parameterType == rhs.parameterType)) ivarsEqual = false;
     if( ! (parameterValue == rhs.parameterValue)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
