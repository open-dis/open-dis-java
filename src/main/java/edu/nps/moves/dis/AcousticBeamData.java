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
 * Used in UA PDU
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class AcousticBeamData extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_AcousticBeamData;

   /** beam data length */
   protected int  beamDataLength;

   /** beamIDNumber */
   protected short  beamIDNumber;

   /** padding */
   protected int  pad2;

   /** fundamental data parameters */
   protected AcousticBeamFundamentalParameter  fundamentalDataParameters = new AcousticBeamFundamentalParameter(); 


/** Constructor */
 public AcousticBeamData()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // beamDataLength
   marshalSize = marshalSize + 1;  // beamIDNumber
   marshalSize = marshalSize + 2;  // pad2
   marshalSize = marshalSize + fundamentalDataParameters.getMarshalledSize();  // fundamentalDataParameters

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_AcousticBeamData()
{
   return pk_AcousticBeamData;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_AcousticBeamData(long pKeyName)
{
   this.pk_AcousticBeamData = pKeyName;
}

public void setBeamDataLength(int pBeamDataLength)
{ beamDataLength = pBeamDataLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getBeamDataLength()
{ return beamDataLength; 
}

public void setBeamIDNumber(short pBeamIDNumber)
{ beamIDNumber = pBeamIDNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getBeamIDNumber()
{ return beamIDNumber; 
}

public void setPad2(int pPad2)
{ pad2 = pPad2;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPad2()
{ return pad2; 
}

public void setFundamentalDataParameters(AcousticBeamFundamentalParameter pFundamentalDataParameters)
{ fundamentalDataParameters = pFundamentalDataParameters;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_fundamentalDataParameters;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_fundamentalDataParameters")
public AcousticBeamFundamentalParameter getFundamentalDataParameters()
{ return fundamentalDataParameters; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)beamDataLength);
       dos.writeByte( (byte)beamIDNumber);
       dos.writeShort( (short)pad2);
       fundamentalDataParameters.marshal(dos);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       beamDataLength = (int)dis.readUnsignedShort();
       beamIDNumber = (short)dis.readUnsignedByte();
       pad2 = (int)dis.readUnsignedShort();
       fundamentalDataParameters.unmarshal(dis);
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
       buff.putShort( (short)beamDataLength);
       buff.put( (byte)beamIDNumber);
       buff.putShort( (short)pad2);
       fundamentalDataParameters.marshal(buff);
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
       beamDataLength = (int)(buff.getShort() & 0xFFFF);
       beamIDNumber = (short)(buff.get() & 0xFF);
       pad2 = (int)(buff.getShort() & 0xFFFF);
       fundamentalDataParameters.unmarshal(buff);
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

    if(!(obj instanceof AcousticBeamData))
        return false;

     final AcousticBeamData rhs = (AcousticBeamData)obj;

     if( ! (beamDataLength == rhs.beamDataLength)) ivarsEqual = false;
     if( ! (beamIDNumber == rhs.beamIDNumber)) ivarsEqual = false;
     if( ! (pad2 == rhs.pad2)) ivarsEqual = false;
     if( ! (fundamentalDataParameters.equals( rhs.fundamentalDataParameters) )) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
