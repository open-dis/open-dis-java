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
 * Information about the type of modulation used for radio transmission. 6.2.59 
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class ModulationType extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_ModulationType;

   /** This field shall indicate the spread spectrum technique or combination of spread spectrum techniques in use. Bit field. 0=freq hopping, 1=psuedo noise, time hopping=2, reamining bits unused */
   protected int  spreadSpectrum;

   /** the major classification of the modulation type.  */
   protected int  majorModulation;

   /** provide certain detailed information depending upon the major modulation type */
   protected int  detail;

   /** the radio system associated with this Transmitter PDU and shall be used as the basis to interpret other fields whose values depend on a specific radio system. */
   protected int  radioSystem;


/** Constructor */
 public ModulationType()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // spreadSpectrum
   marshalSize = marshalSize + 2;  // majorModulation
   marshalSize = marshalSize + 2;  // detail
   marshalSize = marshalSize + 2;  // radioSystem

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_ModulationType()
{
   return pk_ModulationType;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_ModulationType(long pKeyName)
{
   this.pk_ModulationType = pKeyName;
}

public void setSpreadSpectrum(int pSpreadSpectrum)
{ spreadSpectrum = pSpreadSpectrum;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getSpreadSpectrum()
{ return spreadSpectrum; 
}

public void setMajorModulation(int pMajorModulation)
{ majorModulation = pMajorModulation;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getMajorModulation()
{ return majorModulation; 
}

public void setDetail(int pDetail)
{ detail = pDetail;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getDetail()
{ return detail; 
}

public void setRadioSystem(int pRadioSystem)
{ radioSystem = pRadioSystem;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getRadioSystem()
{ return radioSystem; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)spreadSpectrum);
       dos.writeShort( (short)majorModulation);
       dos.writeShort( (short)detail);
       dos.writeShort( (short)radioSystem);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       spreadSpectrum = (int)dis.readUnsignedShort();
       majorModulation = (int)dis.readUnsignedShort();
       detail = (int)dis.readUnsignedShort();
       radioSystem = (int)dis.readUnsignedShort();
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
       buff.putShort( (short)spreadSpectrum);
       buff.putShort( (short)majorModulation);
       buff.putShort( (short)detail);
       buff.putShort( (short)radioSystem);
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
       spreadSpectrum = (int)(buff.getShort() & 0xFFFF);
       majorModulation = (int)(buff.getShort() & 0xFFFF);
       detail = (int)(buff.getShort() & 0xFFFF);
       radioSystem = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof ModulationType))
        return false;

     final ModulationType rhs = (ModulationType)obj;

     if( ! (spreadSpectrum == rhs.spreadSpectrum)) ivarsEqual = false;
     if( ! (majorModulation == rhs.majorModulation)) ivarsEqual = false;
     if( ! (detail == rhs.detail)) ivarsEqual = false;
     if( ! (radioSystem == rhs.radioSystem)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
