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
 * Section 5.2.39. Specification of the data necessary to  describe the scan volume of an emitter.
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class BeamData extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_BeamData;

   /** Specifies the beam azimuth an elevation centers and corresponding half-angles     to describe the scan volume */
   protected float  beamAzimuthCenter;

   /** Specifies the beam azimuth sweep to determine scan volume */
   protected float  beamAzimuthSweep;

   /** Specifies the beam elevation center to determine scan volume */
   protected float  beamElevationCenter;

   /** Specifies the beam elevation sweep to determine scan volume */
   protected float  beamElevationSweep;

   /** allows receiver to synchronize its regenerated scan pattern to     that of the emmitter. Specifies the percentage of time a scan is through its pattern from its origion. */
   protected float  beamSweepSync;


/** Constructor */
 public BeamData()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // beamAzimuthCenter
   marshalSize = marshalSize + 4;  // beamAzimuthSweep
   marshalSize = marshalSize + 4;  // beamElevationCenter
   marshalSize = marshalSize + 4;  // beamElevationSweep
   marshalSize = marshalSize + 4;  // beamSweepSync

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_BeamData()
{
   return pk_BeamData;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_BeamData(long pKeyName)
{
   this.pk_BeamData = pKeyName;
}

public void setBeamAzimuthCenter(float pBeamAzimuthCenter)
{ beamAzimuthCenter = pBeamAzimuthCenter;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getBeamAzimuthCenter()
{ return beamAzimuthCenter; 
}

public void setBeamAzimuthSweep(float pBeamAzimuthSweep)
{ beamAzimuthSweep = pBeamAzimuthSweep;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getBeamAzimuthSweep()
{ return beamAzimuthSweep; 
}

public void setBeamElevationCenter(float pBeamElevationCenter)
{ beamElevationCenter = pBeamElevationCenter;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getBeamElevationCenter()
{ return beamElevationCenter; 
}

public void setBeamElevationSweep(float pBeamElevationSweep)
{ beamElevationSweep = pBeamElevationSweep;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getBeamElevationSweep()
{ return beamElevationSweep; 
}

public void setBeamSweepSync(float pBeamSweepSync)
{ beamSweepSync = pBeamSweepSync;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getBeamSweepSync()
{ return beamSweepSync; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeFloat( (float)beamAzimuthCenter);
       dos.writeFloat( (float)beamAzimuthSweep);
       dos.writeFloat( (float)beamElevationCenter);
       dos.writeFloat( (float)beamElevationSweep);
       dos.writeFloat( (float)beamSweepSync);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       beamAzimuthCenter = dis.readFloat();
       beamAzimuthSweep = dis.readFloat();
       beamElevationCenter = dis.readFloat();
       beamElevationSweep = dis.readFloat();
       beamSweepSync = dis.readFloat();
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
       buff.putFloat( (float)beamAzimuthCenter);
       buff.putFloat( (float)beamAzimuthSweep);
       buff.putFloat( (float)beamElevationCenter);
       buff.putFloat( (float)beamElevationSweep);
       buff.putFloat( (float)beamSweepSync);
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
       beamAzimuthCenter = buff.getFloat();
       beamAzimuthSweep = buff.getFloat();
       beamElevationCenter = buff.getFloat();
       beamElevationSweep = buff.getFloat();
       beamSweepSync = buff.getFloat();
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

    if(!(obj instanceof BeamData))
        return false;

     final BeamData rhs = (BeamData)obj;

     if( ! (beamAzimuthCenter == rhs.beamAzimuthCenter)) ivarsEqual = false;
     if( ! (beamAzimuthSweep == rhs.beamAzimuthSweep)) ivarsEqual = false;
     if( ! (beamElevationCenter == rhs.beamElevationCenter)) ivarsEqual = false;
     if( ! (beamElevationSweep == rhs.beamElevationSweep)) ivarsEqual = false;
     if( ! (beamSweepSync == rhs.beamSweepSync)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
