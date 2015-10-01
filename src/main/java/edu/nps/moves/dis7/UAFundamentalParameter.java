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
 * Regeneration parameters for active emission systems that are variable throughout a scenario. Section 6.2.91
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class UAFundamentalParameter extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_UAFundamentalParameter;

   /** Which database record shall be used. An enumeration from EBV document */
   protected int  activeEmissionParameterIndex;

   /** The type of scan pattern, If not used, zero. An enumeration from EBV document */
   protected int  scanPattern;

   /** center azimuth bearing of th emain beam. In radians. */
   protected float  beamCenterAzimuthHorizontal;

   /** Horizontal beamwidth of th emain beam Meastued at the 3dB down point of peak radiated power. In radians. */
   protected float  azimuthalBeamwidthHorizontal;

   /** center of the d/e angle of th emain beam relative to the stablised de angle of the target. In radians. */
   protected float  beamCenterDepressionElevation;

   /** vertical beamwidth of the main beam. Meastured at the 3dB down point of peak radiated power. In radians. */
   protected float  beamwidthDownElevation;


/** Constructor */
 public UAFundamentalParameter()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // activeEmissionParameterIndex
   marshalSize = marshalSize + 2;  // scanPattern
   marshalSize = marshalSize + 4;  // beamCenterAzimuthHorizontal
   marshalSize = marshalSize + 4;  // azimuthalBeamwidthHorizontal
   marshalSize = marshalSize + 4;  // beamCenterDepressionElevation
   marshalSize = marshalSize + 4;  // beamwidthDownElevation

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_UAFundamentalParameter()
{
   return pk_UAFundamentalParameter;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_UAFundamentalParameter(long pKeyName)
{
   this.pk_UAFundamentalParameter = pKeyName;
}

public void setActiveEmissionParameterIndex(int pActiveEmissionParameterIndex)
{ activeEmissionParameterIndex = pActiveEmissionParameterIndex;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getActiveEmissionParameterIndex()
{ return activeEmissionParameterIndex; 
}

public void setScanPattern(int pScanPattern)
{ scanPattern = pScanPattern;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getScanPattern()
{ return scanPattern; 
}

public void setBeamCenterAzimuthHorizontal(float pBeamCenterAzimuthHorizontal)
{ beamCenterAzimuthHorizontal = pBeamCenterAzimuthHorizontal;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getBeamCenterAzimuthHorizontal()
{ return beamCenterAzimuthHorizontal; 
}

public void setAzimuthalBeamwidthHorizontal(float pAzimuthalBeamwidthHorizontal)
{ azimuthalBeamwidthHorizontal = pAzimuthalBeamwidthHorizontal;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getAzimuthalBeamwidthHorizontal()
{ return azimuthalBeamwidthHorizontal; 
}

public void setBeamCenterDepressionElevation(float pBeamCenterDepressionElevation)
{ beamCenterDepressionElevation = pBeamCenterDepressionElevation;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getBeamCenterDepressionElevation()
{ return beamCenterDepressionElevation; 
}

public void setBeamwidthDownElevation(float pBeamwidthDownElevation)
{ beamwidthDownElevation = pBeamwidthDownElevation;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getBeamwidthDownElevation()
{ return beamwidthDownElevation; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)activeEmissionParameterIndex);
       dos.writeShort( (short)scanPattern);
       dos.writeFloat( (float)beamCenterAzimuthHorizontal);
       dos.writeFloat( (float)azimuthalBeamwidthHorizontal);
       dos.writeFloat( (float)beamCenterDepressionElevation);
       dos.writeFloat( (float)beamwidthDownElevation);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       activeEmissionParameterIndex = (int)dis.readUnsignedShort();
       scanPattern = (int)dis.readUnsignedShort();
       beamCenterAzimuthHorizontal = dis.readFloat();
       azimuthalBeamwidthHorizontal = dis.readFloat();
       beamCenterDepressionElevation = dis.readFloat();
       beamwidthDownElevation = dis.readFloat();
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
       buff.putShort( (short)activeEmissionParameterIndex);
       buff.putShort( (short)scanPattern);
       buff.putFloat( (float)beamCenterAzimuthHorizontal);
       buff.putFloat( (float)azimuthalBeamwidthHorizontal);
       buff.putFloat( (float)beamCenterDepressionElevation);
       buff.putFloat( (float)beamwidthDownElevation);
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
       activeEmissionParameterIndex = (int)(buff.getShort() & 0xFFFF);
       scanPattern = (int)(buff.getShort() & 0xFFFF);
       beamCenterAzimuthHorizontal = buff.getFloat();
       azimuthalBeamwidthHorizontal = buff.getFloat();
       beamCenterDepressionElevation = buff.getFloat();
       beamwidthDownElevation = buff.getFloat();
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

    if(!(obj instanceof UAFundamentalParameter))
        return false;

     final UAFundamentalParameter rhs = (UAFundamentalParameter)obj;

     if( ! (activeEmissionParameterIndex == rhs.activeEmissionParameterIndex)) ivarsEqual = false;
     if( ! (scanPattern == rhs.scanPattern)) ivarsEqual = false;
     if( ! (beamCenterAzimuthHorizontal == rhs.beamCenterAzimuthHorizontal)) ivarsEqual = false;
     if( ! (azimuthalBeamwidthHorizontal == rhs.azimuthalBeamwidthHorizontal)) ivarsEqual = false;
     if( ! (beamCenterDepressionElevation == rhs.beamCenterDepressionElevation)) ivarsEqual = false;
     if( ! (beamwidthDownElevation == rhs.beamwidthDownElevation)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
