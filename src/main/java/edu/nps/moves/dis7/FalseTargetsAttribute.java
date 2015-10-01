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
 * The False Targets attribute record shall be used to communicate discrete values that are associated with false targets jamming that cannot be referenced to an emitter mode. The values provided in the False Targets attri- bute record shall be considered valid only for the victim radar beams listed in the jamming beam's Track/Jam Data records (provided in the associated Electromagnetic Emission PDU). Section 6.2.21.3
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class FalseTargetsAttribute extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_FalseTargetsAttribute;

   protected long  recordType = (long)3502;

   protected int  recordLength = (int)40;

   protected int  padding = (int)0;

   protected short  emitterNumber;

   protected short  beamNumber;

   protected short  stateIndicator;

   protected short  padding2 = (short)0;

   protected int  falseTargetCount;

   protected float  walkSpeed;

   protected float  walkAcceleration;

   protected float  maximumWalkDistance;

   protected float  keepTime;

   protected float  echoSpacing;


/** Constructor */
 public FalseTargetsAttribute()
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
   marshalSize = marshalSize + 2;  // falseTargetCount
   marshalSize = marshalSize + 4;  // walkSpeed
   marshalSize = marshalSize + 4;  // walkAcceleration
   marshalSize = marshalSize + 4;  // maximumWalkDistance
   marshalSize = marshalSize + 4;  // keepTime
   marshalSize = marshalSize + 4;  // echoSpacing

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_FalseTargetsAttribute()
{
   return pk_FalseTargetsAttribute;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_FalseTargetsAttribute(long pKeyName)
{
   this.pk_FalseTargetsAttribute = pKeyName;
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

public void setFalseTargetCount(int pFalseTargetCount)
{ falseTargetCount = pFalseTargetCount;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getFalseTargetCount()
{ return falseTargetCount; 
}

public void setWalkSpeed(float pWalkSpeed)
{ walkSpeed = pWalkSpeed;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getWalkSpeed()
{ return walkSpeed; 
}

public void setWalkAcceleration(float pWalkAcceleration)
{ walkAcceleration = pWalkAcceleration;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getWalkAcceleration()
{ return walkAcceleration; 
}

public void setMaximumWalkDistance(float pMaximumWalkDistance)
{ maximumWalkDistance = pMaximumWalkDistance;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getMaximumWalkDistance()
{ return maximumWalkDistance; 
}

public void setKeepTime(float pKeepTime)
{ keepTime = pKeepTime;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getKeepTime()
{ return keepTime; 
}

public void setEchoSpacing(float pEchoSpacing)
{ echoSpacing = pEchoSpacing;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getEchoSpacing()
{ return echoSpacing; 
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
       dos.writeShort( (short)falseTargetCount);
       dos.writeFloat( (float)walkSpeed);
       dos.writeFloat( (float)walkAcceleration);
       dos.writeFloat( (float)maximumWalkDistance);
       dos.writeFloat( (float)keepTime);
       dos.writeFloat( (float)echoSpacing);
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
       falseTargetCount = (int)dis.readUnsignedShort();
       walkSpeed = dis.readFloat();
       walkAcceleration = dis.readFloat();
       maximumWalkDistance = dis.readFloat();
       keepTime = dis.readFloat();
       echoSpacing = dis.readFloat();
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
       buff.putShort( (short)falseTargetCount);
       buff.putFloat( (float)walkSpeed);
       buff.putFloat( (float)walkAcceleration);
       buff.putFloat( (float)maximumWalkDistance);
       buff.putFloat( (float)keepTime);
       buff.putFloat( (float)echoSpacing);
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
       falseTargetCount = (int)(buff.getShort() & 0xFFFF);
       walkSpeed = buff.getFloat();
       walkAcceleration = buff.getFloat();
       maximumWalkDistance = buff.getFloat();
       keepTime = buff.getFloat();
       echoSpacing = buff.getFloat();
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

    if(!(obj instanceof FalseTargetsAttribute))
        return false;

     final FalseTargetsAttribute rhs = (FalseTargetsAttribute)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (recordLength == rhs.recordLength)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (emitterNumber == rhs.emitterNumber)) ivarsEqual = false;
     if( ! (beamNumber == rhs.beamNumber)) ivarsEqual = false;
     if( ! (stateIndicator == rhs.stateIndicator)) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;
     if( ! (falseTargetCount == rhs.falseTargetCount)) ivarsEqual = false;
     if( ! (walkSpeed == rhs.walkSpeed)) ivarsEqual = false;
     if( ! (walkAcceleration == rhs.walkAcceleration)) ivarsEqual = false;
     if( ! (maximumWalkDistance == rhs.maximumWalkDistance)) ivarsEqual = false;
     if( ! (keepTime == rhs.keepTime)) ivarsEqual = false;
     if( ! (echoSpacing == rhs.echoSpacing)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
