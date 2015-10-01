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
 * Fundamental IFF atc data. Section 6.2.45
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class IFFFundamentalParameterData extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_IFFFundamentalParameterData;

   /** ERP */
   protected float  erp;

   /** frequency */
   protected float  frequency;

   /** pgrf */
   protected float  pgrf;

   /** Pulse width */
   protected float  pulseWidth;

   /** Burst length */
   protected long  burstLength;

   /** Applicable modes enumeration */
   protected short  applicableModes;

   /** System-specific data */
   protected short[]  systemSpecificData = new short[3]; 


/** Constructor */
 public IFFFundamentalParameterData()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // erp
   marshalSize = marshalSize + 4;  // frequency
   marshalSize = marshalSize + 4;  // pgrf
   marshalSize = marshalSize + 4;  // pulseWidth
   marshalSize = marshalSize + 4;  // burstLength
   marshalSize = marshalSize + 1;  // applicableModes
   marshalSize = marshalSize + 3 * 1;  // systemSpecificData

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_IFFFundamentalParameterData()
{
   return pk_IFFFundamentalParameterData;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_IFFFundamentalParameterData(long pKeyName)
{
   this.pk_IFFFundamentalParameterData = pKeyName;
}

public void setErp(float pErp)
{ erp = pErp;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getErp()
{ return erp; 
}

public void setFrequency(float pFrequency)
{ frequency = pFrequency;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getFrequency()
{ return frequency; 
}

public void setPgrf(float pPgrf)
{ pgrf = pPgrf;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getPgrf()
{ return pgrf; 
}

public void setPulseWidth(float pPulseWidth)
{ pulseWidth = pPulseWidth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getPulseWidth()
{ return pulseWidth; 
}

public void setBurstLength(long pBurstLength)
{ burstLength = pBurstLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getBurstLength()
{ return burstLength; 
}

public void setApplicableModes(short pApplicableModes)
{ applicableModes = pApplicableModes;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getApplicableModes()
{ return applicableModes; 
}

public void setSystemSpecificData(short[] pSystemSpecificData)
{ systemSpecificData = pSystemSpecificData;
}

@XmlElement(name="systemSpecificData" )
@Basic
public short[] getSystemSpecificData()
{ return systemSpecificData; }


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeFloat( (float)erp);
       dos.writeFloat( (float)frequency);
       dos.writeFloat( (float)pgrf);
       dos.writeFloat( (float)pulseWidth);
       dos.writeInt( (int)burstLength);
       dos.writeByte( (byte)applicableModes);

       for(int idx = 0; idx < systemSpecificData.length; idx++)
       {
           dos.writeByte(systemSpecificData[idx]);
       } // end of array marshaling

    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       erp = dis.readFloat();
       frequency = dis.readFloat();
       pgrf = dis.readFloat();
       pulseWidth = dis.readFloat();
       burstLength = dis.readInt();
       applicableModes = (short)dis.readUnsignedByte();
       for(int idx = 0; idx < systemSpecificData.length; idx++)
       {
                systemSpecificData[idx] = dis.readByte();
       } // end of array unmarshaling
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
       buff.putFloat( (float)erp);
       buff.putFloat( (float)frequency);
       buff.putFloat( (float)pgrf);
       buff.putFloat( (float)pulseWidth);
       buff.putInt( (int)burstLength);
       buff.put( (byte)applicableModes);

       for(int idx = 0; idx < systemSpecificData.length; idx++)
       {
           buff.put((byte)systemSpecificData[idx]);
       } // end of array marshaling

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
       erp = buff.getFloat();
       frequency = buff.getFloat();
       pgrf = buff.getFloat();
       pulseWidth = buff.getFloat();
       burstLength = buff.getInt();
       applicableModes = (short)(buff.get() & 0xFF);
       for(int idx = 0; idx < systemSpecificData.length; idx++)
       {
                systemSpecificData[idx] = buff.get();
       } // end of array unmarshaling
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

    if(!(obj instanceof IFFFundamentalParameterData))
        return false;

     final IFFFundamentalParameterData rhs = (IFFFundamentalParameterData)obj;

     if( ! (erp == rhs.erp)) ivarsEqual = false;
     if( ! (frequency == rhs.frequency)) ivarsEqual = false;
     if( ! (pgrf == rhs.pgrf)) ivarsEqual = false;
     if( ! (pulseWidth == rhs.pulseWidth)) ivarsEqual = false;
     if( ! (burstLength == rhs.burstLength)) ivarsEqual = false;
     if( ! (applicableModes == rhs.applicableModes)) ivarsEqual = false;

     for(int idx = 0; idx < 3; idx++)
     {
          if(!(systemSpecificData[idx] == rhs.systemSpecificData[idx])) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
