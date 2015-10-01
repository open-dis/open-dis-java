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
 * Effect of IO on an entity. Section 6.2.49.3
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class IOEffect extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_IOEffect;

   protected long  recordType = (long)5500;

   protected int  recordLength = (int)16;

   protected short  ioStatus;

   protected short  ioLinkType;

   protected EntityID  ioEffect = new EntityID(); 

   protected short  ioEffectDutyCycle;

   protected int  ioEffectDuration;

   protected int  ioProcess;

   protected int  padding = (int)0;


/** Constructor */
 public IOEffect()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // recordType
   marshalSize = marshalSize + 2;  // recordLength
   marshalSize = marshalSize + 1;  // ioStatus
   marshalSize = marshalSize + 1;  // ioLinkType
   marshalSize = marshalSize + ioEffect.getMarshalledSize();  // ioEffect
   marshalSize = marshalSize + 1;  // ioEffectDutyCycle
   marshalSize = marshalSize + 2;  // ioEffectDuration
   marshalSize = marshalSize + 2;  // ioProcess
   marshalSize = marshalSize + 2;  // padding

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_IOEffect()
{
   return pk_IOEffect;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_IOEffect(long pKeyName)
{
   this.pk_IOEffect = pKeyName;
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

public void setIoStatus(short pIoStatus)
{ ioStatus = pIoStatus;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getIoStatus()
{ return ioStatus; 
}

public void setIoLinkType(short pIoLinkType)
{ ioLinkType = pIoLinkType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getIoLinkType()
{ return ioLinkType; 
}

public void setIoEffect(EntityID pIoEffect)
{ ioEffect = pIoEffect;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_ioEffect;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_ioEffect")
public EntityID getIoEffect()
{ return ioEffect; 
}

public void setIoEffectDutyCycle(short pIoEffectDutyCycle)
{ ioEffectDutyCycle = pIoEffectDutyCycle;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getIoEffectDutyCycle()
{ return ioEffectDutyCycle; 
}

public void setIoEffectDuration(int pIoEffectDuration)
{ ioEffectDuration = pIoEffectDuration;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getIoEffectDuration()
{ return ioEffectDuration; 
}

public void setIoProcess(int pIoProcess)
{ ioProcess = pIoProcess;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getIoProcess()
{ return ioProcess; 
}

public void setPadding(int pPadding)
{ padding = pPadding;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding()
{ return padding; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)recordType);
       dos.writeShort( (short)recordLength);
       dos.writeByte( (byte)ioStatus);
       dos.writeByte( (byte)ioLinkType);
       ioEffect.marshal(dos);
       dos.writeByte( (byte)ioEffectDutyCycle);
       dos.writeShort( (short)ioEffectDuration);
       dos.writeShort( (short)ioProcess);
       dos.writeShort( (short)padding);
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
       ioStatus = (short)dis.readUnsignedByte();
       ioLinkType = (short)dis.readUnsignedByte();
       ioEffect.unmarshal(dis);
       ioEffectDutyCycle = (short)dis.readUnsignedByte();
       ioEffectDuration = (int)dis.readUnsignedShort();
       ioProcess = (int)dis.readUnsignedShort();
       padding = (int)dis.readUnsignedShort();
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
       buff.put( (byte)ioStatus);
       buff.put( (byte)ioLinkType);
       ioEffect.marshal(buff);
       buff.put( (byte)ioEffectDutyCycle);
       buff.putShort( (short)ioEffectDuration);
       buff.putShort( (short)ioProcess);
       buff.putShort( (short)padding);
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
       ioStatus = (short)(buff.get() & 0xFF);
       ioLinkType = (short)(buff.get() & 0xFF);
       ioEffect.unmarshal(buff);
       ioEffectDutyCycle = (short)(buff.get() & 0xFF);
       ioEffectDuration = (int)(buff.getShort() & 0xFFFF);
       ioProcess = (int)(buff.getShort() & 0xFFFF);
       padding = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof IOEffect))
        return false;

     final IOEffect rhs = (IOEffect)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (recordLength == rhs.recordLength)) ivarsEqual = false;
     if( ! (ioStatus == rhs.ioStatus)) ivarsEqual = false;
     if( ! (ioLinkType == rhs.ioLinkType)) ivarsEqual = false;
     if( ! (ioEffect.equals( rhs.ioEffect) )) ivarsEqual = false;
     if( ! (ioEffectDutyCycle == rhs.ioEffectDutyCycle)) ivarsEqual = false;
     if( ! (ioEffectDuration == rhs.ioEffectDuration)) ivarsEqual = false;
     if( ! (ioProcess == rhs.ioProcess)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
