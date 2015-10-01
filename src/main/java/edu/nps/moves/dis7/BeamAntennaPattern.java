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
 * Used when the antenna pattern type field has a value of 1. Specifies the direction, pattern, and polarization of radiation from an antenna. Section 6.2.9.2
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class BeamAntennaPattern extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_BeamAntennaPattern;

   /** The rotation that transforms the reference coordinate sytem into the beam coordinate system. Either world coordinates or entity coordinates may be used as the reference coordinate system, as specified by the reference system field of the antenna pattern record. */
   protected EulerAngles  beamDirection = new EulerAngles(); 

   protected float  azimuthBeamwidth = (float)0;

   protected float  elevationBeamwidth = (float)0;

   protected short  referenceSystem = (short)0;

   protected short  padding1 = (short)0;

   protected int  padding2 = (int)0;

   /** This field shall specify the magnitude of the Z-component (in beam coordinates) of the Electrical field at some arbitrary single point in the main beam and in the far field of the antenna.  */
   protected float  ez = (float)0.0;

   /** This field shall specify the magnitude of the X-component (in beam coordinates) of the Electri- cal field at some arbitrary single point in the main beam and in the far field of the antenna. */
   protected float  ex = (float)0.0;

   /** This field shall specify the phase angle between EZ and EX in radians. If fully omni-direc- tional antenna is modeled using beam pattern type one, the omni-directional antenna shall be repre- sented by beam direction Euler angles psi, theta, and phi of zero, an azimuth beamwidth of 2PI, and an elevation beamwidth of PI */
   protected float  phase = (float)0.0;

   /** padding */
   protected long  padding3 = (long)0;


/** Constructor */
 public BeamAntennaPattern()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + beamDirection.getMarshalledSize();  // beamDirection
   marshalSize = marshalSize + 4;  // azimuthBeamwidth
   marshalSize = marshalSize + 4;  // elevationBeamwidth
   marshalSize = marshalSize + 1;  // referenceSystem
   marshalSize = marshalSize + 1;  // padding1
   marshalSize = marshalSize + 2;  // padding2
   marshalSize = marshalSize + 4;  // ez
   marshalSize = marshalSize + 4;  // ex
   marshalSize = marshalSize + 4;  // phase
   marshalSize = marshalSize + 4;  // padding3

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_BeamAntennaPattern()
{
   return pk_BeamAntennaPattern;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_BeamAntennaPattern(long pKeyName)
{
   this.pk_BeamAntennaPattern = pKeyName;
}

public void setBeamDirection(EulerAngles pBeamDirection)
{ beamDirection = pBeamDirection;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_beamDirection;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_beamDirection")
public EulerAngles getBeamDirection()
{ return beamDirection; 
}

public void setAzimuthBeamwidth(float pAzimuthBeamwidth)
{ azimuthBeamwidth = pAzimuthBeamwidth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getAzimuthBeamwidth()
{ return azimuthBeamwidth; 
}

public void setElevationBeamwidth(float pElevationBeamwidth)
{ elevationBeamwidth = pElevationBeamwidth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getElevationBeamwidth()
{ return elevationBeamwidth; 
}

public void setReferenceSystem(short pReferenceSystem)
{ referenceSystem = pReferenceSystem;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getReferenceSystem()
{ return referenceSystem; 
}

public void setPadding1(short pPadding1)
{ padding1 = pPadding1;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPadding1()
{ return padding1; 
}

public void setPadding2(int pPadding2)
{ padding2 = pPadding2;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding2()
{ return padding2; 
}

public void setEz(float pEz)
{ ez = pEz;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getEz()
{ return ez; 
}

public void setEx(float pEx)
{ ex = pEx;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getEx()
{ return ex; 
}

public void setPhase(float pPhase)
{ phase = pPhase;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getPhase()
{ return phase; 
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
       beamDirection.marshal(dos);
       dos.writeFloat( (float)azimuthBeamwidth);
       dos.writeFloat( (float)elevationBeamwidth);
       dos.writeByte( (byte)referenceSystem);
       dos.writeByte( (byte)padding1);
       dos.writeShort( (short)padding2);
       dos.writeFloat( (float)ez);
       dos.writeFloat( (float)ex);
       dos.writeFloat( (float)phase);
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
       beamDirection.unmarshal(dis);
       azimuthBeamwidth = dis.readFloat();
       elevationBeamwidth = dis.readFloat();
       referenceSystem = (short)dis.readUnsignedByte();
       padding1 = (short)dis.readUnsignedByte();
       padding2 = (int)dis.readUnsignedShort();
       ez = dis.readFloat();
       ex = dis.readFloat();
       phase = dis.readFloat();
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
       beamDirection.marshal(buff);
       buff.putFloat( (float)azimuthBeamwidth);
       buff.putFloat( (float)elevationBeamwidth);
       buff.put( (byte)referenceSystem);
       buff.put( (byte)padding1);
       buff.putShort( (short)padding2);
       buff.putFloat( (float)ez);
       buff.putFloat( (float)ex);
       buff.putFloat( (float)phase);
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
       beamDirection.unmarshal(buff);
       azimuthBeamwidth = buff.getFloat();
       elevationBeamwidth = buff.getFloat();
       referenceSystem = (short)(buff.get() & 0xFF);
       padding1 = (short)(buff.get() & 0xFF);
       padding2 = (int)(buff.getShort() & 0xFFFF);
       ez = buff.getFloat();
       ex = buff.getFloat();
       phase = buff.getFloat();
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

    if(!(obj instanceof BeamAntennaPattern))
        return false;

     final BeamAntennaPattern rhs = (BeamAntennaPattern)obj;

     if( ! (beamDirection.equals( rhs.beamDirection) )) ivarsEqual = false;
     if( ! (azimuthBeamwidth == rhs.azimuthBeamwidth)) ivarsEqual = false;
     if( ! (elevationBeamwidth == rhs.elevationBeamwidth)) ivarsEqual = false;
     if( ! (referenceSystem == rhs.referenceSystem)) ivarsEqual = false;
     if( ! (padding1 == rhs.padding1)) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;
     if( ! (ez == rhs.ez)) ivarsEqual = false;
     if( ! (ex == rhs.ex)) ivarsEqual = false;
     if( ! (phase == rhs.phase)) ivarsEqual = false;
     if( ! (padding3 == rhs.padding3)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
