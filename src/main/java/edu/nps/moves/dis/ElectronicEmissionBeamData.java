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
 * Description of one electronic emission beam
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class ElectronicEmissionBeamData extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_ElectronicEmissionBeamData;

   /** This field shall specify the length of this beams data in 32 bit words */
   protected short  beamDataLength;

   /** This field shall specify a unique emitter database number assigned to differentiate between otherwise similar or identical emitter beams within an emitter system. */
   protected short  beamIDNumber;

   /** This field shall specify a Beam Parameter Index number that shall be used by receiving entities in conjunction with the Emitter Name field to provide a pointer to the stored database parameters required to regenerate the beam.  */
   protected int  beamParameterIndex;

   /** Fundamental parameter data such as frequency range, beam sweep, etc. */
   protected FundamentalParameterData  fundamentalParameterData = new FundamentalParameterData(); 

   /** beam function of a particular beam */
   protected short  beamFunction;

   /** Number of track/jam targets */
   protected short  numberOfTrackJamTargets;

   /** wheher or not the receiving simulation apps can assume all the targets in the scan pattern are being tracked/jammed */
   protected short  highDensityTrackJam;

   /** padding */
   protected short  pad4 = (short)0;

   /** identify jamming techniques used */
   protected long  jammingModeSequence;

   /** variable length list of track/jam targets */
   protected List< TrackJamTarget > trackJamTargets = new ArrayList< TrackJamTarget >(); 

/** Constructor */
 public ElectronicEmissionBeamData()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // beamDataLength
   marshalSize = marshalSize + 1;  // beamIDNumber
   marshalSize = marshalSize + 2;  // beamParameterIndex
   marshalSize = marshalSize + fundamentalParameterData.getMarshalledSize();  // fundamentalParameterData
   marshalSize = marshalSize + 1;  // beamFunction
   marshalSize = marshalSize + 1;  // numberOfTrackJamTargets
   marshalSize = marshalSize + 1;  // highDensityTrackJam
   marshalSize = marshalSize + 1;  // pad4
   marshalSize = marshalSize + 4;  // jammingModeSequence
   for(int idx=0; idx < trackJamTargets.size(); idx++)
   {
        TrackJamTarget listElement = trackJamTargets.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_ElectronicEmissionBeamData()
{
   return pk_ElectronicEmissionBeamData;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_ElectronicEmissionBeamData(long pKeyName)
{
   this.pk_ElectronicEmissionBeamData = pKeyName;
}

public void setBeamDataLength(short pBeamDataLength)
{ beamDataLength = pBeamDataLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getBeamDataLength()
{ return beamDataLength; 
}

public void setBeamIDNumber(short pBeamIDNumber)
{ beamIDNumber = pBeamIDNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getBeamIDNumber()
{ return beamIDNumber; 
}

public void setBeamParameterIndex(int pBeamParameterIndex)
{ beamParameterIndex = pBeamParameterIndex;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getBeamParameterIndex()
{ return beamParameterIndex; 
}

public void setFundamentalParameterData(FundamentalParameterData pFundamentalParameterData)
{ fundamentalParameterData = pFundamentalParameterData;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_fundamentalParameterData;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_fundamentalParameterData")
public FundamentalParameterData getFundamentalParameterData()
{ return fundamentalParameterData; 
}

public void setBeamFunction(short pBeamFunction)
{ beamFunction = pBeamFunction;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getBeamFunction()
{ return beamFunction; 
}

@XmlAttribute
@Basic
public short getNumberOfTrackJamTargets()
{ return (short)trackJamTargets.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfTrackJamTargets method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfTrackJamTargets(short pNumberOfTrackJamTargets)
{ numberOfTrackJamTargets = pNumberOfTrackJamTargets;
}

public void setHighDensityTrackJam(short pHighDensityTrackJam)
{ highDensityTrackJam = pHighDensityTrackJam;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getHighDensityTrackJam()
{ return highDensityTrackJam; 
}

public void setPad4(short pPad4)
{ pad4 = pPad4;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPad4()
{ return pad4; 
}

public void setJammingModeSequence(long pJammingModeSequence)
{ jammingModeSequence = pJammingModeSequence;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getJammingModeSequence()
{ return jammingModeSequence; 
}

public void setTrackJamTargets(List<TrackJamTarget> pTrackJamTargets)
{ trackJamTargets = pTrackJamTargets;
}

@XmlElementWrapper(name="trackJamTargetsList" ) //  Jaxb
@OneToMany    // Hibernate
public List<TrackJamTarget> getTrackJamTargets()
{ return trackJamTargets; }


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)beamDataLength);
       dos.writeByte( (byte)beamIDNumber);
       dos.writeShort( (short)beamParameterIndex);
       fundamentalParameterData.marshal(dos);
       dos.writeByte( (byte)beamFunction);
       dos.writeByte( (byte)trackJamTargets.size());
       dos.writeByte( (byte)highDensityTrackJam);
       dos.writeByte( (byte)pad4);
       dos.writeInt( (int)jammingModeSequence);

       for(int idx = 0; idx < trackJamTargets.size(); idx++)
       {
            TrackJamTarget aTrackJamTarget = trackJamTargets.get(idx);
            aTrackJamTarget.marshal(dos);
       } // end of list marshalling

    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       beamDataLength = (short)dis.readUnsignedByte();
       beamIDNumber = (short)dis.readUnsignedByte();
       beamParameterIndex = (int)dis.readUnsignedShort();
       fundamentalParameterData.unmarshal(dis);
       beamFunction = (short)dis.readUnsignedByte();
       numberOfTrackJamTargets = (short)dis.readUnsignedByte();
       highDensityTrackJam = (short)dis.readUnsignedByte();
       pad4 = (short)dis.readUnsignedByte();
       jammingModeSequence = dis.readInt();
       for(int idx = 0; idx < numberOfTrackJamTargets; idx++)
       {
           TrackJamTarget anX = new TrackJamTarget();
           anX.unmarshal(dis);
           trackJamTargets.add(anX);
       }

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
       buff.put( (byte)beamDataLength);
       buff.put( (byte)beamIDNumber);
       buff.putShort( (short)beamParameterIndex);
       fundamentalParameterData.marshal(buff);
       buff.put( (byte)beamFunction);
       buff.put( (byte)trackJamTargets.size());
       buff.put( (byte)highDensityTrackJam);
       buff.put( (byte)pad4);
       buff.putInt( (int)jammingModeSequence);

       for(int idx = 0; idx < trackJamTargets.size(); idx++)
       {
            TrackJamTarget aTrackJamTarget = (TrackJamTarget)trackJamTargets.get(idx);
            aTrackJamTarget.marshal(buff);
       } // end of list marshalling

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
       beamDataLength = (short)(buff.get() & 0xFF);
       beamIDNumber = (short)(buff.get() & 0xFF);
       beamParameterIndex = (int)(buff.getShort() & 0xFFFF);
       fundamentalParameterData.unmarshal(buff);
       beamFunction = (short)(buff.get() & 0xFF);
       numberOfTrackJamTargets = (short)(buff.get() & 0xFF);
       highDensityTrackJam = (short)(buff.get() & 0xFF);
       pad4 = (short)(buff.get() & 0xFF);
       jammingModeSequence = buff.getInt();
       for(int idx = 0; idx < numberOfTrackJamTargets; idx++)
       {
            TrackJamTarget anX = new TrackJamTarget();
            anX.unmarshal(buff);
            trackJamTargets.add(anX);
       }

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

    if(!(obj instanceof ElectronicEmissionBeamData))
        return false;

     final ElectronicEmissionBeamData rhs = (ElectronicEmissionBeamData)obj;

     if( ! (beamDataLength == rhs.beamDataLength)) ivarsEqual = false;
     if( ! (beamIDNumber == rhs.beamIDNumber)) ivarsEqual = false;
     if( ! (beamParameterIndex == rhs.beamParameterIndex)) ivarsEqual = false;
     if( ! (fundamentalParameterData.equals( rhs.fundamentalParameterData) )) ivarsEqual = false;
     if( ! (beamFunction == rhs.beamFunction)) ivarsEqual = false;
     if( ! (numberOfTrackJamTargets == rhs.numberOfTrackJamTargets)) ivarsEqual = false;
     if( ! (highDensityTrackJam == rhs.highDensityTrackJam)) ivarsEqual = false;
     if( ! (pad4 == rhs.pad4)) ivarsEqual = false;
     if( ! (jammingModeSequence == rhs.jammingModeSequence)) ivarsEqual = false;

     for(int idx = 0; idx < trackJamTargets.size(); idx++)
     {
        if( ! ( trackJamTargets.get(idx).equals(rhs.trackJamTargets.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
