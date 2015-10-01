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
 *  Information about underwater acoustic emmissions. This requires manual cleanup.  The beam data records should ALL be a the finish, rather than attached to each emitter system. Section 7.6.4. UNFINISHED
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class UaPdu extends DistributedEmissionsFamilyPdu implements Serializable
{
   /** ID of the entity that is the source of the emission */
   protected EntityID  emittingEntityID = new EntityID(); 

   /** ID of event */
   protected EventIdentifier  eventID = new EventIdentifier(); 

   /** This field shall be used to indicate whether the data in the UA PDU represent a state update or data that have changed since issuance of the last UA PDU */
   protected byte  stateChangeIndicator;

   /** padding */
   protected byte  pad;

   /** This field indicates which database record (or file) shall be used in the definition of passive signature (unintentional) emissions of the entity. The indicated database record (or  file) shall define all noise generated as a function of propulsion plant configurations and associated  auxiliaries. */
   protected int  passiveParameterIndex;

   /** This field shall specify the entity propulsion plant configuration. This field is used to determine the passive signature characteristics of an entity. */
   protected short  propulsionPlantConfiguration;

   /**  This field shall represent the number of shafts on a platform */
   protected short  numberOfShafts;

   /** This field shall indicate the number of APAs described in the current UA PDU */
   protected short  numberOfAPAs;

   /** This field shall specify the number of UA emitter systems being described in the current UA PDU */
   protected short  numberOfUAEmitterSystems;

   /** shaft RPM values. THIS IS WRONG. It has the wrong class in the list. */
   protected List< Vector3Float > shaftRPMs = new ArrayList< Vector3Float >(); 
   /** apaData. THIS IS WRONG. It has the worng class in the list. */
   protected List< Vector3Float > apaData = new ArrayList< Vector3Float >(); 
   /** THIS IS WRONG. It has the wrong class in the list. */
   protected List< Vector3Float > emitterSystems = new ArrayList< Vector3Float >(); 

/** Constructor */
 public UaPdu()
 {
    setPduType( (short)29 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + emittingEntityID.getMarshalledSize();  // emittingEntityID
   marshalSize = marshalSize + eventID.getMarshalledSize();  // eventID
   marshalSize = marshalSize + 1;  // stateChangeIndicator
   marshalSize = marshalSize + 1;  // pad
   marshalSize = marshalSize + 2;  // passiveParameterIndex
   marshalSize = marshalSize + 1;  // propulsionPlantConfiguration
   marshalSize = marshalSize + 1;  // numberOfShafts
   marshalSize = marshalSize + 1;  // numberOfAPAs
   marshalSize = marshalSize + 1;  // numberOfUAEmitterSystems
   for(int idx=0; idx < shaftRPMs.size(); idx++)
   {
        Vector3Float listElement = shaftRPMs.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   for(int idx=0; idx < apaData.size(); idx++)
   {
        Vector3Float listElement = apaData.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   for(int idx=0; idx < emitterSystems.size(); idx++)
   {
        Vector3Float listElement = emitterSystems.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setEmittingEntityID(EntityID pEmittingEntityID)
{ emittingEntityID = pEmittingEntityID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_emittingEntityID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_emittingEntityID")
public EntityID getEmittingEntityID()
{ return emittingEntityID; 
}

public void setEventID(EventIdentifier pEventID)
{ eventID = pEventID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_eventID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_eventID")
public EventIdentifier getEventID()
{ return eventID; 
}

public void setStateChangeIndicator(byte pStateChangeIndicator)
{ stateChangeIndicator = pStateChangeIndicator;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public byte getStateChangeIndicator()
{ return stateChangeIndicator; 
}

public void setPad(byte pPad)
{ pad = pPad;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public byte getPad()
{ return pad; 
}

public void setPassiveParameterIndex(int pPassiveParameterIndex)
{ passiveParameterIndex = pPassiveParameterIndex;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPassiveParameterIndex()
{ return passiveParameterIndex; 
}

public void setPropulsionPlantConfiguration(short pPropulsionPlantConfiguration)
{ propulsionPlantConfiguration = pPropulsionPlantConfiguration;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPropulsionPlantConfiguration()
{ return propulsionPlantConfiguration; 
}

@XmlAttribute
@Basic
public short getNumberOfShafts()
{ return (short)shaftRPMs.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfShafts method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfShafts(short pNumberOfShafts)
{ numberOfShafts = pNumberOfShafts;
}

@XmlAttribute
@Basic
public short getNumberOfAPAs()
{ return (short)apaData.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfAPAs method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfAPAs(short pNumberOfAPAs)
{ numberOfAPAs = pNumberOfAPAs;
}

@XmlAttribute
@Basic
public short getNumberOfUAEmitterSystems()
{ return (short)emitterSystems.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfUAEmitterSystems method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfUAEmitterSystems(short pNumberOfUAEmitterSystems)
{ numberOfUAEmitterSystems = pNumberOfUAEmitterSystems;
}

public void setShaftRPMs(List<Vector3Float> pShaftRPMs)
{ shaftRPMs = pShaftRPMs;
}

@XmlElementWrapper(name="shaftRPMsList" ) //  Jaxb
@OneToMany    // Hibernate
public List<Vector3Float> getShaftRPMs()
{ return shaftRPMs; }

public void setApaData(List<Vector3Float> pApaData)
{ apaData = pApaData;
}

@XmlElementWrapper(name="apaDataList" ) //  Jaxb
@OneToMany    // Hibernate
public List<Vector3Float> getApaData()
{ return apaData; }

public void setEmitterSystems(List<Vector3Float> pEmitterSystems)
{ emitterSystems = pEmitterSystems;
}

@XmlElementWrapper(name="emitterSystemsList" ) //  Jaxb
@OneToMany    // Hibernate
public List<Vector3Float> getEmitterSystems()
{ return emitterSystems; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       emittingEntityID.marshal(dos);
       eventID.marshal(dos);
       dos.writeByte( (byte)stateChangeIndicator);
       dos.writeByte( (byte)pad);
       dos.writeShort( (short)passiveParameterIndex);
       dos.writeByte( (byte)propulsionPlantConfiguration);
       dos.writeByte( (byte)shaftRPMs.size());
       dos.writeByte( (byte)apaData.size());
       dos.writeByte( (byte)emitterSystems.size());

       for(int idx = 0; idx < shaftRPMs.size(); idx++)
       {
            Vector3Float aVector3Float = shaftRPMs.get(idx);
            aVector3Float.marshal(dos);
       } // end of list marshalling


       for(int idx = 0; idx < apaData.size(); idx++)
       {
            Vector3Float aVector3Float = apaData.get(idx);
            aVector3Float.marshal(dos);
       } // end of list marshalling


       for(int idx = 0; idx < emitterSystems.size(); idx++)
       {
            Vector3Float aVector3Float = emitterSystems.get(idx);
            aVector3Float.marshal(dos);
       } // end of list marshalling

    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
     super.unmarshal(dis);

    try 
    {
       emittingEntityID.unmarshal(dis);
       eventID.unmarshal(dis);
       stateChangeIndicator = dis.readByte();
       pad = dis.readByte();
       passiveParameterIndex = (int)dis.readUnsignedShort();
       propulsionPlantConfiguration = (short)dis.readUnsignedByte();
       numberOfShafts = (short)dis.readUnsignedByte();
       numberOfAPAs = (short)dis.readUnsignedByte();
       numberOfUAEmitterSystems = (short)dis.readUnsignedByte();
       for(int idx = 0; idx < numberOfShafts; idx++)
       {
           Vector3Float anX = new Vector3Float();
           anX.unmarshal(dis);
           shaftRPMs.add(anX);
       }

       for(int idx = 0; idx < numberOfAPAs; idx++)
       {
           Vector3Float anX = new Vector3Float();
           anX.unmarshal(dis);
           apaData.add(anX);
       }

       for(int idx = 0; idx < numberOfUAEmitterSystems; idx++)
       {
           Vector3Float anX = new Vector3Float();
           anX.unmarshal(dis);
           emitterSystems.add(anX);
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
       super.marshal(buff);
       emittingEntityID.marshal(buff);
       eventID.marshal(buff);
       buff.put( (byte)stateChangeIndicator);
       buff.put( (byte)pad);
       buff.putShort( (short)passiveParameterIndex);
       buff.put( (byte)propulsionPlantConfiguration);
       buff.put( (byte)shaftRPMs.size());
       buff.put( (byte)apaData.size());
       buff.put( (byte)emitterSystems.size());

       for(int idx = 0; idx < shaftRPMs.size(); idx++)
       {
            Vector3Float aVector3Float = (Vector3Float)shaftRPMs.get(idx);
            aVector3Float.marshal(buff);
       } // end of list marshalling


       for(int idx = 0; idx < apaData.size(); idx++)
       {
            Vector3Float aVector3Float = (Vector3Float)apaData.get(idx);
            aVector3Float.marshal(buff);
       } // end of list marshalling


       for(int idx = 0; idx < emitterSystems.size(); idx++)
       {
            Vector3Float aVector3Float = (Vector3Float)emitterSystems.get(idx);
            aVector3Float.marshal(buff);
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
       super.unmarshal(buff);

       emittingEntityID.unmarshal(buff);
       eventID.unmarshal(buff);
       stateChangeIndicator = buff.get();
       pad = buff.get();
       passiveParameterIndex = (int)(buff.getShort() & 0xFFFF);
       propulsionPlantConfiguration = (short)(buff.get() & 0xFF);
       numberOfShafts = (short)(buff.get() & 0xFF);
       numberOfAPAs = (short)(buff.get() & 0xFF);
       numberOfUAEmitterSystems = (short)(buff.get() & 0xFF);
       for(int idx = 0; idx < numberOfShafts; idx++)
       {
            Vector3Float anX = new Vector3Float();
            anX.unmarshal(buff);
            shaftRPMs.add(anX);
       }

       for(int idx = 0; idx < numberOfAPAs; idx++)
       {
            Vector3Float anX = new Vector3Float();
            anX.unmarshal(buff);
            apaData.add(anX);
       }

       for(int idx = 0; idx < numberOfUAEmitterSystems; idx++)
       {
            Vector3Float anX = new Vector3Float();
            anX.unmarshal(buff);
            emitterSystems.add(anX);
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

@Override
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof UaPdu))
        return false;

     final UaPdu rhs = (UaPdu)obj;

     if( ! (emittingEntityID.equals( rhs.emittingEntityID) )) ivarsEqual = false;
     if( ! (eventID.equals( rhs.eventID) )) ivarsEqual = false;
     if( ! (stateChangeIndicator == rhs.stateChangeIndicator)) ivarsEqual = false;
     if( ! (pad == rhs.pad)) ivarsEqual = false;
     if( ! (passiveParameterIndex == rhs.passiveParameterIndex)) ivarsEqual = false;
     if( ! (propulsionPlantConfiguration == rhs.propulsionPlantConfiguration)) ivarsEqual = false;
     if( ! (numberOfShafts == rhs.numberOfShafts)) ivarsEqual = false;
     if( ! (numberOfAPAs == rhs.numberOfAPAs)) ivarsEqual = false;
     if( ! (numberOfUAEmitterSystems == rhs.numberOfUAEmitterSystems)) ivarsEqual = false;

     for(int idx = 0; idx < shaftRPMs.size(); idx++)
     {
        if( ! ( shaftRPMs.get(idx).equals(rhs.shaftRPMs.get(idx)))) ivarsEqual = false;
     }


     for(int idx = 0; idx < apaData.size(); idx++)
     {
        if( ! ( apaData.get(idx).equals(rhs.apaData.get(idx)))) ivarsEqual = false;
     }


     for(int idx = 0; idx < emitterSystems.size(); idx++)
     {
        if( ! ( emitterSystems.get(idx).equals(rhs.emitterSystems.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
