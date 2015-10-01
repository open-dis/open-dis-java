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
 * Section 5.3.7.1. Information about active electronic warfare (EW) emissions and active EW countermeasures shall be communicated using an Electromagnetic Emission PDU. NOT COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class ElectronicEmissionsPdu extends DistributedEmissionsFamilyPdu implements Serializable
{
   /** ID of the entity emitting */
   protected EntityID  emittingEntityID = new EntityID(); 

   /** ID of event */
   protected EventIdentifier  eventID = new EventIdentifier(); 

   /** This field shall be used to indicate if the data in the PDU represents a state update or just data that has changed since issuance of the last Electromagnetic Emission PDU [relative to the identified entity and emission system(s)]. */
   protected short  stateUpdateIndicator;

   /** This field shall specify the number of emission systems being described in the current PDU. */
   protected short  numberOfSystems;

   /** padding */
   protected int  paddingForEmissionsPdu;

   /**  this field shall specify the length of this emitter system's data in 32-bit words. */
   protected short  systemDataLength;

   /** the number of beams being described in the current PDU for the emitter system being described.  */
   protected short  numberOfBeams;

   /**  information about a particular emitter system and shall be represented by an Emitter System record (see 6.2.23). */
   protected EmitterSystem  emitterSystem = new EmitterSystem(); 

   /** the location of the antenna beam source with respect to the emitting entity's coordinate system. This location shall be the origin of the emitter coordinate system that shall have the same orientation as the entity coordinate system. This field shall be represented by an Entity Coordinate Vector record see 6.2.95  */
   protected Vector3Float  location = new Vector3Float(); 

   /** Electronic emmissions systems THIS IS WRONG. It has the WRONG class type and will cause problems in any marshalling. */
   protected List< Vector3Float > systems = new ArrayList< Vector3Float >(); 

/** Constructor */
 public ElectronicEmissionsPdu()
 {
    setPduType( (short)23 );
    setPaddingForEmissionsPdu( (int)0 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + emittingEntityID.getMarshalledSize();  // emittingEntityID
   marshalSize = marshalSize + eventID.getMarshalledSize();  // eventID
   marshalSize = marshalSize + 1;  // stateUpdateIndicator
   marshalSize = marshalSize + 1;  // numberOfSystems
   marshalSize = marshalSize + 2;  // paddingForEmissionsPdu
   marshalSize = marshalSize + 1;  // systemDataLength
   marshalSize = marshalSize + 1;  // numberOfBeams
   marshalSize = marshalSize + emitterSystem.getMarshalledSize();  // emitterSystem
   marshalSize = marshalSize + location.getMarshalledSize();  // location
   for(int idx=0; idx < systems.size(); idx++)
   {
        Vector3Float listElement = systems.get(idx);
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

public void setStateUpdateIndicator(short pStateUpdateIndicator)
{ stateUpdateIndicator = pStateUpdateIndicator;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getStateUpdateIndicator()
{ return stateUpdateIndicator; 
}

@XmlAttribute
@Basic
public short getNumberOfSystems()
{ return (short)systems.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfSystems method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfSystems(short pNumberOfSystems)
{ numberOfSystems = pNumberOfSystems;
}

public void setPaddingForEmissionsPdu(int pPaddingForEmissionsPdu)
{ paddingForEmissionsPdu = pPaddingForEmissionsPdu;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPaddingForEmissionsPdu()
{ return paddingForEmissionsPdu; 
}

public void setSystemDataLength(short pSystemDataLength)
{ systemDataLength = pSystemDataLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getSystemDataLength()
{ return systemDataLength; 
}

public void setNumberOfBeams(short pNumberOfBeams)
{ numberOfBeams = pNumberOfBeams;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getNumberOfBeams()
{ return numberOfBeams; 
}

public void setEmitterSystem(EmitterSystem pEmitterSystem)
{ emitterSystem = pEmitterSystem;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_emitterSystem;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_emitterSystem")
public EmitterSystem getEmitterSystem()
{ return emitterSystem; 
}

public void setLocation(Vector3Float pLocation)
{ location = pLocation;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_location;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_location")
public Vector3Float getLocation()
{ return location; 
}

public void setSystems(List<Vector3Float> pSystems)
{ systems = pSystems;
}

@XmlElementWrapper(name="systemsList" ) //  Jaxb
@OneToMany    // Hibernate
public List<Vector3Float> getSystems()
{ return systems; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       emittingEntityID.marshal(dos);
       eventID.marshal(dos);
       dos.writeByte( (byte)stateUpdateIndicator);
       dos.writeByte( (byte)systems.size());
       dos.writeShort( (short)paddingForEmissionsPdu);
       dos.writeByte( (byte)systemDataLength);
       dos.writeByte( (byte)numberOfBeams);
       emitterSystem.marshal(dos);
       location.marshal(dos);

       for(int idx = 0; idx < systems.size(); idx++)
       {
            Vector3Float aVector3Float = systems.get(idx);
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
       stateUpdateIndicator = (short)dis.readUnsignedByte();
       numberOfSystems = (short)dis.readUnsignedByte();
       paddingForEmissionsPdu = (int)dis.readUnsignedShort();
       systemDataLength = (short)dis.readUnsignedByte();
       numberOfBeams = (short)dis.readUnsignedByte();
       emitterSystem.unmarshal(dis);
       location.unmarshal(dis);
       for(int idx = 0; idx < numberOfSystems; idx++)
       {
           Vector3Float anX = new Vector3Float();
           anX.unmarshal(dis);
           systems.add(anX);
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
       buff.put( (byte)stateUpdateIndicator);
       buff.put( (byte)systems.size());
       buff.putShort( (short)paddingForEmissionsPdu);
       buff.put( (byte)systemDataLength);
       buff.put( (byte)numberOfBeams);
       emitterSystem.marshal(buff);
       location.marshal(buff);

       for(int idx = 0; idx < systems.size(); idx++)
       {
            Vector3Float aVector3Float = (Vector3Float)systems.get(idx);
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
       stateUpdateIndicator = (short)(buff.get() & 0xFF);
       numberOfSystems = (short)(buff.get() & 0xFF);
       paddingForEmissionsPdu = (int)(buff.getShort() & 0xFFFF);
       systemDataLength = (short)(buff.get() & 0xFF);
       numberOfBeams = (short)(buff.get() & 0xFF);
       emitterSystem.unmarshal(buff);
       location.unmarshal(buff);
       for(int idx = 0; idx < numberOfSystems; idx++)
       {
            Vector3Float anX = new Vector3Float();
            anX.unmarshal(buff);
            systems.add(anX);
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

    if(!(obj instanceof ElectronicEmissionsPdu))
        return false;

     final ElectronicEmissionsPdu rhs = (ElectronicEmissionsPdu)obj;

     if( ! (emittingEntityID.equals( rhs.emittingEntityID) )) ivarsEqual = false;
     if( ! (eventID.equals( rhs.eventID) )) ivarsEqual = false;
     if( ! (stateUpdateIndicator == rhs.stateUpdateIndicator)) ivarsEqual = false;
     if( ! (numberOfSystems == rhs.numberOfSystems)) ivarsEqual = false;
     if( ! (paddingForEmissionsPdu == rhs.paddingForEmissionsPdu)) ivarsEqual = false;
     if( ! (systemDataLength == rhs.systemDataLength)) ivarsEqual = false;
     if( ! (numberOfBeams == rhs.numberOfBeams)) ivarsEqual = false;
     if( ! (emitterSystem.equals( rhs.emitterSystem) )) ivarsEqual = false;
     if( ! (location.equals( rhs.location) )) ivarsEqual = false;

     for(int idx = 0; idx < systems.size(); idx++)
     {
        if( ! ( systems.get(idx).equals(rhs.systems.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
