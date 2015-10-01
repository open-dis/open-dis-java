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
 * Detonation or impact of munitions, as well as, non-munition explosions, the burst or initial bloom of chaff, and the ignition of a flare shall be indicated. Section 7.3.3  COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class DetonationPdu extends WarfareFamilyPdu implements Serializable
{
   /** ID of the expendable entity, Section 7.3.3  */
   protected EntityID  explodingEntityID = new EntityID(); 

   /** ID of event, Section 7.3.3 */
   protected EventIdentifier  eventID = new EventIdentifier(); 

   /** velocity of the munition immediately before detonation/impact, Section 7.3.3  */
   protected Vector3Float  velocity = new Vector3Float(); 

   /** location of the munition detonation, the expendable detonation, Section 7.3.3  */
   protected Vector3Double  locationInWorldCoordinates = new Vector3Double(); 

   /** Describes the detonation represented, Section 7.3.3  */
   protected MunitionDescriptor  descriptor = new MunitionDescriptor(); 

   /** Velocity of the ammunition, Section 7.3.3  */
   protected Vector3Float  locationOfEntityCoordinates = new Vector3Float(); 

   /** result of the detonation, Section 7.3.3  */
   protected short  detonationResult;

   /** How many articulation parameters we have, Section 7.3.3  */
   protected short  numberOfVariableParameters;

   /** padding */
   protected int  pad;

   /** specify the parameter values for each Variable Parameter record, Section 7.3.3  */
   protected List< VariableParameter > variableParameters = new ArrayList< VariableParameter >(); 

/** Constructor */
 public DetonationPdu()
 {
    setPduType( (short)3 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + explodingEntityID.getMarshalledSize();  // explodingEntityID
   marshalSize = marshalSize + eventID.getMarshalledSize();  // eventID
   marshalSize = marshalSize + velocity.getMarshalledSize();  // velocity
   marshalSize = marshalSize + locationInWorldCoordinates.getMarshalledSize();  // locationInWorldCoordinates
   marshalSize = marshalSize + descriptor.getMarshalledSize();  // descriptor
   marshalSize = marshalSize + locationOfEntityCoordinates.getMarshalledSize();  // locationOfEntityCoordinates
   marshalSize = marshalSize + 1;  // detonationResult
   marshalSize = marshalSize + 1;  // numberOfVariableParameters
   marshalSize = marshalSize + 2;  // pad
   for(int idx=0; idx < variableParameters.size(); idx++)
   {
        VariableParameter listElement = variableParameters.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setExplodingEntityID(EntityID pExplodingEntityID)
{ explodingEntityID = pExplodingEntityID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_explodingEntityID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_explodingEntityID")
public EntityID getExplodingEntityID()
{ return explodingEntityID; 
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

public void setVelocity(Vector3Float pVelocity)
{ velocity = pVelocity;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_velocity;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_velocity")
public Vector3Float getVelocity()
{ return velocity; 
}

public void setLocationInWorldCoordinates(Vector3Double pLocationInWorldCoordinates)
{ locationInWorldCoordinates = pLocationInWorldCoordinates;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_locationInWorldCoordinates;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_locationInWorldCoordinates")
public Vector3Double getLocationInWorldCoordinates()
{ return locationInWorldCoordinates; 
}

public void setDescriptor(MunitionDescriptor pDescriptor)
{ descriptor = pDescriptor;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_descriptor;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_descriptor")
public MunitionDescriptor getDescriptor()
{ return descriptor; 
}

public void setLocationOfEntityCoordinates(Vector3Float pLocationOfEntityCoordinates)
{ locationOfEntityCoordinates = pLocationOfEntityCoordinates;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_locationOfEntityCoordinates;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_locationOfEntityCoordinates")
public Vector3Float getLocationOfEntityCoordinates()
{ return locationOfEntityCoordinates; 
}

public void setDetonationResult(short pDetonationResult)
{ detonationResult = pDetonationResult;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getDetonationResult()
{ return detonationResult; 
}

@XmlAttribute
@Basic
public short getNumberOfVariableParameters()
{ return (short)variableParameters.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfVariableParameters method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfVariableParameters(short pNumberOfVariableParameters)
{ numberOfVariableParameters = pNumberOfVariableParameters;
}

public void setPad(int pPad)
{ pad = pPad;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPad()
{ return pad; 
}

public void setVariableParameters(List<VariableParameter> pVariableParameters)
{ variableParameters = pVariableParameters;
}

@XmlElementWrapper(name="variableParametersList" ) //  Jaxb
@OneToMany    // Hibernate
public List<VariableParameter> getVariableParameters()
{ return variableParameters; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       explodingEntityID.marshal(dos);
       eventID.marshal(dos);
       velocity.marshal(dos);
       locationInWorldCoordinates.marshal(dos);
       descriptor.marshal(dos);
       locationOfEntityCoordinates.marshal(dos);
       dos.writeByte( (byte)detonationResult);
       dos.writeByte( (byte)variableParameters.size());
       dos.writeShort( (short)pad);

       for(int idx = 0; idx < variableParameters.size(); idx++)
       {
            VariableParameter aVariableParameter = variableParameters.get(idx);
            aVariableParameter.marshal(dos);
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
       explodingEntityID.unmarshal(dis);
       eventID.unmarshal(dis);
       velocity.unmarshal(dis);
       locationInWorldCoordinates.unmarshal(dis);
       descriptor.unmarshal(dis);
       locationOfEntityCoordinates.unmarshal(dis);
       detonationResult = (short)dis.readUnsignedByte();
       numberOfVariableParameters = (short)dis.readUnsignedByte();
       pad = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < numberOfVariableParameters; idx++)
       {
           VariableParameter anX = new VariableParameter();
           anX.unmarshal(dis);
           variableParameters.add(anX);
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
       explodingEntityID.marshal(buff);
       eventID.marshal(buff);
       velocity.marshal(buff);
       locationInWorldCoordinates.marshal(buff);
       descriptor.marshal(buff);
       locationOfEntityCoordinates.marshal(buff);
       buff.put( (byte)detonationResult);
       buff.put( (byte)variableParameters.size());
       buff.putShort( (short)pad);

       for(int idx = 0; idx < variableParameters.size(); idx++)
       {
            VariableParameter aVariableParameter = (VariableParameter)variableParameters.get(idx);
            aVariableParameter.marshal(buff);
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

       explodingEntityID.unmarshal(buff);
       eventID.unmarshal(buff);
       velocity.unmarshal(buff);
       locationInWorldCoordinates.unmarshal(buff);
       descriptor.unmarshal(buff);
       locationOfEntityCoordinates.unmarshal(buff);
       detonationResult = (short)(buff.get() & 0xFF);
       numberOfVariableParameters = (short)(buff.get() & 0xFF);
       pad = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < numberOfVariableParameters; idx++)
       {
            VariableParameter anX = new VariableParameter();
            anX.unmarshal(buff);
            variableParameters.add(anX);
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

    if(!(obj instanceof DetonationPdu))
        return false;

     final DetonationPdu rhs = (DetonationPdu)obj;

     if( ! (explodingEntityID.equals( rhs.explodingEntityID) )) ivarsEqual = false;
     if( ! (eventID.equals( rhs.eventID) )) ivarsEqual = false;
     if( ! (velocity.equals( rhs.velocity) )) ivarsEqual = false;
     if( ! (locationInWorldCoordinates.equals( rhs.locationInWorldCoordinates) )) ivarsEqual = false;
     if( ! (descriptor.equals( rhs.descriptor) )) ivarsEqual = false;
     if( ! (locationOfEntityCoordinates.equals( rhs.locationOfEntityCoordinates) )) ivarsEqual = false;
     if( ! (detonationResult == rhs.detonationResult)) ivarsEqual = false;
     if( ! (numberOfVariableParameters == rhs.numberOfVariableParameters)) ivarsEqual = false;
     if( ! (pad == rhs.pad)) ivarsEqual = false;

     for(int idx = 0; idx < variableParameters.size(); idx++)
     {
        if( ! ( variableParameters.get(idx).equals(rhs.variableParameters.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
