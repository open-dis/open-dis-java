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
 * Section 5.2.4.2. Used when the antenna pattern type field has a value of 1. Specifies           the direction, patter, and polarization of radiation from an antenna.
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

   /** The rotation that transformst he reference coordinate sytem     into the beam coordinate system. Either world coordinates or entity coordinates may be used as the     reference coordinate system, as specified by teh reference system field of the antenna pattern record. */
   protected Orientation  beamDirection = new Orientation(); 

   protected float  azimuthBeamwidth = (float)0;

   protected float  elevationBeamwidth = (float)0;

   protected float  referenceSystem = (float)0;

   protected short  padding1 = (short)0;

   protected byte  padding2 = (byte)0;

   /** Magnigute of the z-component in beam coordinates at some arbitrary      single point in the mainbeam      and in the far field of the antenna. */
   protected float  ez;

   /** Magnigute of the x-component in beam coordinates at some arbitrary      single point in the mainbeam      and in the far field of the antenna. */
   protected float  ex;

   /** THe phase angle between Ez and Ex in radians. */
   protected float  phase;


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
   marshalSize = marshalSize + 4;  // referenceSystem
   marshalSize = marshalSize + 2;  // padding1
   marshalSize = marshalSize + 1;  // padding2
   marshalSize = marshalSize + 4;  // ez
   marshalSize = marshalSize + 4;  // ex
   marshalSize = marshalSize + 4;  // phase

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

public void setBeamDirection(Orientation pBeamDirection)
{ beamDirection = pBeamDirection;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_beamDirection;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_beamDirection")
public Orientation getBeamDirection()
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

public void setReferenceSystem(float pReferenceSystem)
{ referenceSystem = pReferenceSystem;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getReferenceSystem()
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

public void setPadding2(byte pPadding2)
{ padding2 = pPadding2;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public byte getPadding2()
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


public void marshal(DataOutputStream dos)
{
    try 
    {
       beamDirection.marshal(dos);
       dos.writeFloat( (float)azimuthBeamwidth);
       dos.writeFloat( (float)elevationBeamwidth);
       dos.writeFloat( (float)referenceSystem);
       dos.writeShort( (short)padding1);
       dos.writeByte( (byte)padding2);
       dos.writeFloat( (float)ez);
       dos.writeFloat( (float)ex);
       dos.writeFloat( (float)phase);
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
       referenceSystem = dis.readFloat();
       padding1 = dis.readShort();
       padding2 = dis.readByte();
       ez = dis.readFloat();
       ex = dis.readFloat();
       phase = dis.readFloat();
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
       buff.putFloat( (float)referenceSystem);
       buff.putShort( (short)padding1);
       buff.put( (byte)padding2);
       buff.putFloat( (float)ez);
       buff.putFloat( (float)ex);
       buff.putFloat( (float)phase);
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
       referenceSystem = buff.getFloat();
       padding1 = buff.getShort();
       padding2 = buff.get();
       ez = buff.getFloat();
       ex = buff.getFloat();
       phase = buff.getFloat();
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

    return ivarsEqual;
 }
} // end of class
