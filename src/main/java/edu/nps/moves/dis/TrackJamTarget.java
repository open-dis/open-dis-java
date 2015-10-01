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
 * One track/jam target
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class TrackJamTarget extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_TrackJamTarget;

   /** track/jam target */
   protected EntityID  trackJam = new EntityID(); 

   /** Emitter ID */
   protected short  emitterID;

   /** beam ID */
   protected short  beamID;


/** Constructor */
 public TrackJamTarget()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + trackJam.getMarshalledSize();  // trackJam
   marshalSize = marshalSize + 1;  // emitterID
   marshalSize = marshalSize + 1;  // beamID

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_TrackJamTarget()
{
   return pk_TrackJamTarget;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_TrackJamTarget(long pKeyName)
{
   this.pk_TrackJamTarget = pKeyName;
}

public void setTrackJam(EntityID pTrackJam)
{ trackJam = pTrackJam;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_trackJam;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_trackJam")
public EntityID getTrackJam()
{ return trackJam; 
}

public void setEmitterID(short pEmitterID)
{ emitterID = pEmitterID;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getEmitterID()
{ return emitterID; 
}

public void setBeamID(short pBeamID)
{ beamID = pBeamID;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getBeamID()
{ return beamID; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       trackJam.marshal(dos);
       dos.writeByte( (byte)emitterID);
       dos.writeByte( (byte)beamID);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       trackJam.unmarshal(dis);
       emitterID = (short)dis.readUnsignedByte();
       beamID = (short)dis.readUnsignedByte();
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
       trackJam.marshal(buff);
       buff.put( (byte)emitterID);
       buff.put( (byte)beamID);
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
       trackJam.unmarshal(buff);
       emitterID = (short)(buff.get() & 0xFF);
       beamID = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof TrackJamTarget))
        return false;

     final TrackJamTarget rhs = (TrackJamTarget)obj;

     if( ! (trackJam.equals( rhs.trackJam) )) ivarsEqual = false;
     if( ! (emitterID == rhs.emitterID)) ivarsEqual = false;
     if( ! (beamID == rhs.beamID)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
