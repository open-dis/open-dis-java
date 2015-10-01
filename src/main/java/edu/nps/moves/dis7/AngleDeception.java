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
 * The Angle Deception attribute record may be used to communicate discrete values that are associated with angle deception jamming that cannot be referenced to an emitter mode. The values provided in the record records (provided in the associated Electromagnetic Emission PDU). (The victim radar beams are those that are targeted by the jammer.) Section 6.2.21.2.2
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class AngleDeception extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_AngleDeception;

   protected long  recordType = (long)3501;

   protected int  recordLength = (int)48;

   protected int  padding = (int)0;

   protected short  emitterNumber;

   protected short  beamNumber;

   protected short  stateIndicator;

   protected short  padding2 = (short)0;

   protected float  azimuthOffset;

   protected float  azimuthWidth;

   protected float  azimuthPullRate;

   protected float  azimuthPullAcceleration;

   protected float  elevationOffset;

   protected float  elevationWidth;

   protected float  elevationPullRate;

   protected float  elevationPullAcceleration;

   protected long  padding3 = (long)0;


/** Constructor */
 public AngleDeception()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // recordType
   marshalSize = marshalSize + 2;  // recordLength
   marshalSize = marshalSize + 2;  // padding
   marshalSize = marshalSize + 1;  // emitterNumber
   marshalSize = marshalSize + 1;  // beamNumber
   marshalSize = marshalSize + 1;  // stateIndicator
   marshalSize = marshalSize + 1;  // padding2
   marshalSize = marshalSize + 4;  // azimuthOffset
   marshalSize = marshalSize + 4;  // azimuthWidth
   marshalSize = marshalSize + 4;  // azimuthPullRate
   marshalSize = marshalSize + 4;  // azimuthPullAcceleration
   marshalSize = marshalSize + 4;  // elevationOffset
   marshalSize = marshalSize + 4;  // elevationWidth
   marshalSize = marshalSize + 4;  // elevationPullRate
   marshalSize = marshalSize + 4;  // elevationPullAcceleration
   marshalSize = marshalSize + 4;  // padding3

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_AngleDeception()
{
   return pk_AngleDeception;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_AngleDeception(long pKeyName)
{
   this.pk_AngleDeception = pKeyName;
}

public void setRecordType(long pRecordType)
{ recordType = pRecordType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getRecordType()
{ return recordType; 
}

public void setRecordLength(int pRecordLength)
{ recordLength = pRecordLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getRecordLength()
{ return recordLength; 
}

public void setPadding(int pPadding)
{ padding = pPadding;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding()
{ return padding; 
}

public void setEmitterNumber(short pEmitterNumber)
{ emitterNumber = pEmitterNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getEmitterNumber()
{ return emitterNumber; 
}

public void setBeamNumber(short pBeamNumber)
{ beamNumber = pBeamNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getBeamNumber()
{ return beamNumber; 
}

public void setStateIndicator(short pStateIndicator)
{ stateIndicator = pStateIndicator;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getStateIndicator()
{ return stateIndicator; 
}

public void setPadding2(short pPadding2)
{ padding2 = pPadding2;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPadding2()
{ return padding2; 
}

public void setAzimuthOffset(float pAzimuthOffset)
{ azimuthOffset = pAzimuthOffset;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getAzimuthOffset()
{ return azimuthOffset; 
}

public void setAzimuthWidth(float pAzimuthWidth)
{ azimuthWidth = pAzimuthWidth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getAzimuthWidth()
{ return azimuthWidth; 
}

public void setAzimuthPullRate(float pAzimuthPullRate)
{ azimuthPullRate = pAzimuthPullRate;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getAzimuthPullRate()
{ return azimuthPullRate; 
}

public void setAzimuthPullAcceleration(float pAzimuthPullAcceleration)
{ azimuthPullAcceleration = pAzimuthPullAcceleration;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getAzimuthPullAcceleration()
{ return azimuthPullAcceleration; 
}

public void setElevationOffset(float pElevationOffset)
{ elevationOffset = pElevationOffset;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getElevationOffset()
{ return elevationOffset; 
}

public void setElevationWidth(float pElevationWidth)
{ elevationWidth = pElevationWidth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getElevationWidth()
{ return elevationWidth; 
}

public void setElevationPullRate(float pElevationPullRate)
{ elevationPullRate = pElevationPullRate;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getElevationPullRate()
{ return elevationPullRate; 
}

public void setElevationPullAcceleration(float pElevationPullAcceleration)
{ elevationPullAcceleration = pElevationPullAcceleration;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getElevationPullAcceleration()
{ return elevationPullAcceleration; 
}

public void setPadding3(long pPadding3)
{ padding3 = pPadding3;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getPadding3()
{ return padding3; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)recordType);
       dos.writeShort( (short)recordLength);
       dos.writeShort( (short)padding);
       dos.writeByte( (byte)emitterNumber);
       dos.writeByte( (byte)beamNumber);
       dos.writeByte( (byte)stateIndicator);
       dos.writeByte( (byte)padding2);
       dos.writeFloat( (float)azimuthOffset);
       dos.writeFloat( (float)azimuthWidth);
       dos.writeFloat( (float)azimuthPullRate);
       dos.writeFloat( (float)azimuthPullAcceleration);
       dos.writeFloat( (float)elevationOffset);
       dos.writeFloat( (float)elevationWidth);
       dos.writeFloat( (float)elevationPullRate);
       dos.writeFloat( (float)elevationPullAcceleration);
       dos.writeInt( (int)padding3);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       recordType = dis.readInt();
       recordLength = (int)dis.readUnsignedShort();
       padding = (int)dis.readUnsignedShort();
       emitterNumber = (short)dis.readUnsignedByte();
       beamNumber = (short)dis.readUnsignedByte();
       stateIndicator = (short)dis.readUnsignedByte();
       padding2 = (short)dis.readUnsignedByte();
       azimuthOffset = dis.readFloat();
       azimuthWidth = dis.readFloat();
       azimuthPullRate = dis.readFloat();
       azimuthPullAcceleration = dis.readFloat();
       elevationOffset = dis.readFloat();
       elevationWidth = dis.readFloat();
       elevationPullRate = dis.readFloat();
       elevationPullAcceleration = dis.readFloat();
       padding3 = dis.readInt();
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
       buff.putInt( (int)recordType);
       buff.putShort( (short)recordLength);
       buff.putShort( (short)padding);
       buff.put( (byte)emitterNumber);
       buff.put( (byte)beamNumber);
       buff.put( (byte)stateIndicator);
       buff.put( (byte)padding2);
       buff.putFloat( (float)azimuthOffset);
       buff.putFloat( (float)azimuthWidth);
       buff.putFloat( (float)azimuthPullRate);
       buff.putFloat( (float)azimuthPullAcceleration);
       buff.putFloat( (float)elevationOffset);
       buff.putFloat( (float)elevationWidth);
       buff.putFloat( (float)elevationPullRate);
       buff.putFloat( (float)elevationPullAcceleration);
       buff.putInt( (int)padding3);
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
       recordType = buff.getInt();
       recordLength = (int)(buff.getShort() & 0xFFFF);
       padding = (int)(buff.getShort() & 0xFFFF);
       emitterNumber = (short)(buff.get() & 0xFF);
       beamNumber = (short)(buff.get() & 0xFF);
       stateIndicator = (short)(buff.get() & 0xFF);
       padding2 = (short)(buff.get() & 0xFF);
       azimuthOffset = buff.getFloat();
       azimuthWidth = buff.getFloat();
       azimuthPullRate = buff.getFloat();
       azimuthPullAcceleration = buff.getFloat();
       elevationOffset = buff.getFloat();
       elevationWidth = buff.getFloat();
       elevationPullRate = buff.getFloat();
       elevationPullAcceleration = buff.getFloat();
       padding3 = buff.getInt();
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

    if(!(obj instanceof AngleDeception))
        return false;

     final AngleDeception rhs = (AngleDeception)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (recordLength == rhs.recordLength)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (emitterNumber == rhs.emitterNumber)) ivarsEqual = false;
     if( ! (beamNumber == rhs.beamNumber)) ivarsEqual = false;
     if( ! (stateIndicator == rhs.stateIndicator)) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;
     if( ! (azimuthOffset == rhs.azimuthOffset)) ivarsEqual = false;
     if( ! (azimuthWidth == rhs.azimuthWidth)) ivarsEqual = false;
     if( ! (azimuthPullRate == rhs.azimuthPullRate)) ivarsEqual = false;
     if( ! (azimuthPullAcceleration == rhs.azimuthPullAcceleration)) ivarsEqual = false;
     if( ! (elevationOffset == rhs.elevationOffset)) ivarsEqual = false;
     if( ! (elevationWidth == rhs.elevationWidth)) ivarsEqual = false;
     if( ! (elevationPullRate == rhs.elevationPullRate)) ivarsEqual = false;
     if( ! (elevationPullAcceleration == rhs.elevationPullAcceleration)) ivarsEqual = false;
     if( ! (padding3 == rhs.padding3)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
