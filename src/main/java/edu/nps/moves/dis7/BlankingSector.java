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
 * The Blanking Sector attribute record may be used to convey persistent areas within a scan volume where emitter power for a specific active emitter beam is reduced to an insignificant value. Section 6.2.21.2
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class BlankingSector extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_BlankingSector;

   protected int  recordType = (int)3500;

   protected int  recordLength = (int)40;

   protected int  padding = (int)0;

   protected short  emitterNumber;

   protected short  beamNumber;

   protected short  stateIndicator;

   protected short  padding2 = (short)0;

   protected float  leftAzimuth;

   protected float  rightAzimuth;

   protected float  lowerElevation;

   protected float  upperElevation;

   protected float  residualPower;

   protected int  padding3 = (int)0;

   protected int  padding4 = (int)0;


/** Constructor */
 public BlankingSector()
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
   marshalSize = marshalSize + 4;  // leftAzimuth
   marshalSize = marshalSize + 4;  // rightAzimuth
   marshalSize = marshalSize + 4;  // lowerElevation
   marshalSize = marshalSize + 4;  // upperElevation
   marshalSize = marshalSize + 4;  // residualPower
   marshalSize = marshalSize + 4;  // padding3
   marshalSize = marshalSize + 4;  // padding4

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_BlankingSector()
{
   return pk_BlankingSector;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_BlankingSector(long pKeyName)
{
   this.pk_BlankingSector = pKeyName;
}

public void setRecordType(int pRecordType)
{ recordType = pRecordType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getRecordType()
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

public void setLeftAzimuth(float pLeftAzimuth)
{ leftAzimuth = pLeftAzimuth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getLeftAzimuth()
{ return leftAzimuth; 
}

public void setRightAzimuth(float pRightAzimuth)
{ rightAzimuth = pRightAzimuth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getRightAzimuth()
{ return rightAzimuth; 
}

public void setLowerElevation(float pLowerElevation)
{ lowerElevation = pLowerElevation;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getLowerElevation()
{ return lowerElevation; 
}

public void setUpperElevation(float pUpperElevation)
{ upperElevation = pUpperElevation;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getUpperElevation()
{ return upperElevation; 
}

public void setResidualPower(float pResidualPower)
{ residualPower = pResidualPower;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getResidualPower()
{ return residualPower; 
}

public void setPadding3(int pPadding3)
{ padding3 = pPadding3;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding3()
{ return padding3; 
}

public void setPadding4(int pPadding4)
{ padding4 = pPadding4;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding4()
{ return padding4; 
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
       dos.writeFloat( (float)leftAzimuth);
       dos.writeFloat( (float)rightAzimuth);
       dos.writeFloat( (float)lowerElevation);
       dos.writeFloat( (float)upperElevation);
       dos.writeFloat( (float)residualPower);
       dos.writeInt( (int)padding3);
       dos.writeInt( (int)padding4);
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
       leftAzimuth = dis.readFloat();
       rightAzimuth = dis.readFloat();
       lowerElevation = dis.readFloat();
       upperElevation = dis.readFloat();
       residualPower = dis.readFloat();
       padding3 = dis.readInt();
       padding4 = dis.readInt();
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
       buff.putFloat( (float)leftAzimuth);
       buff.putFloat( (float)rightAzimuth);
       buff.putFloat( (float)lowerElevation);
       buff.putFloat( (float)upperElevation);
       buff.putFloat( (float)residualPower);
       buff.putInt( (int)padding3);
       buff.putInt( (int)padding4);
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
       leftAzimuth = buff.getFloat();
       rightAzimuth = buff.getFloat();
       lowerElevation = buff.getFloat();
       upperElevation = buff.getFloat();
       residualPower = buff.getFloat();
       padding3 = buff.getInt();
       padding4 = buff.getInt();
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

    if(!(obj instanceof BlankingSector))
        return false;

     final BlankingSector rhs = (BlankingSector)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (recordLength == rhs.recordLength)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (emitterNumber == rhs.emitterNumber)) ivarsEqual = false;
     if( ! (beamNumber == rhs.beamNumber)) ivarsEqual = false;
     if( ! (stateIndicator == rhs.stateIndicator)) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;
     if( ! (leftAzimuth == rhs.leftAzimuth)) ivarsEqual = false;
     if( ! (rightAzimuth == rhs.rightAzimuth)) ivarsEqual = false;
     if( ! (lowerElevation == rhs.lowerElevation)) ivarsEqual = false;
     if( ! (upperElevation == rhs.upperElevation)) ivarsEqual = false;
     if( ! (residualPower == rhs.residualPower)) ivarsEqual = false;
     if( ! (padding3 == rhs.padding3)) ivarsEqual = false;
     if( ! (padding4 == rhs.padding4)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
