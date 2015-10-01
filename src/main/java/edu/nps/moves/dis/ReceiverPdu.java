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
 * Section 5.3.8.3. Communication of a receiver state. COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class ReceiverPdu extends RadioCommunicationsFamilyPdu implements Serializable
{
   /** ID of the entity that is the source of the communication, ie contains the radio */
   protected EntityID  entityId = new EntityID(); 

   /** particular radio within an entity */
   protected int  radioId;

   /** encoding scheme used, and enumeration */
   protected int  receiverState;

   /** padding */
   protected int  padding1;

   /** received power */
   protected float  receivedPower;

   /** ID of transmitter */
   protected EntityID  transmitterEntityId = new EntityID(); 

   /** ID of transmitting radio */
   protected int  transmitterRadioId;


/** Constructor */
 public ReceiverPdu()
 {
    setPduType( (short)27 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + entityId.getMarshalledSize();  // entityId
   marshalSize = marshalSize + 2;  // radioId
   marshalSize = marshalSize + 2;  // receiverState
   marshalSize = marshalSize + 2;  // padding1
   marshalSize = marshalSize + 4;  // receivedPower
   marshalSize = marshalSize + transmitterEntityId.getMarshalledSize();  // transmitterEntityId
   marshalSize = marshalSize + 2;  // transmitterRadioId

   return marshalSize;
}


public void setEntityId(EntityID pEntityId)
{ entityId = pEntityId;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_entityId;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_entityId")
public EntityID getEntityId()
{ return entityId; 
}

public void setRadioId(int pRadioId)
{ radioId = pRadioId;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getRadioId()
{ return radioId; 
}

public void setReceiverState(int pReceiverState)
{ receiverState = pReceiverState;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getReceiverState()
{ return receiverState; 
}

public void setPadding1(int pPadding1)
{ padding1 = pPadding1;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding1()
{ return padding1; 
}

public void setReceivedPower(float pReceivedPower)
{ receivedPower = pReceivedPower;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getReceivedPower()
{ return receivedPower; 
}

public void setTransmitterEntityId(EntityID pTransmitterEntityId)
{ transmitterEntityId = pTransmitterEntityId;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_transmitterEntityId;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_transmitterEntityId")
public EntityID getTransmitterEntityId()
{ return transmitterEntityId; 
}

public void setTransmitterRadioId(int pTransmitterRadioId)
{ transmitterRadioId = pTransmitterRadioId;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getTransmitterRadioId()
{ return transmitterRadioId; 
}


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       entityId.marshal(dos);
       dos.writeShort( (short)radioId);
       dos.writeShort( (short)receiverState);
       dos.writeShort( (short)padding1);
       dos.writeFloat( (float)receivedPower);
       transmitterEntityId.marshal(dos);
       dos.writeShort( (short)transmitterRadioId);
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
       entityId.unmarshal(dis);
       radioId = (int)dis.readUnsignedShort();
       receiverState = (int)dis.readUnsignedShort();
       padding1 = (int)dis.readUnsignedShort();
       receivedPower = dis.readFloat();
       transmitterEntityId.unmarshal(dis);
       transmitterRadioId = (int)dis.readUnsignedShort();
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
       entityId.marshal(buff);
       buff.putShort( (short)radioId);
       buff.putShort( (short)receiverState);
       buff.putShort( (short)padding1);
       buff.putFloat( (float)receivedPower);
       transmitterEntityId.marshal(buff);
       buff.putShort( (short)transmitterRadioId);
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

       entityId.unmarshal(buff);
       radioId = (int)(buff.getShort() & 0xFFFF);
       receiverState = (int)(buff.getShort() & 0xFFFF);
       padding1 = (int)(buff.getShort() & 0xFFFF);
       receivedPower = buff.getFloat();
       transmitterEntityId.unmarshal(buff);
       transmitterRadioId = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof ReceiverPdu))
        return false;

     final ReceiverPdu rhs = (ReceiverPdu)obj;

     if( ! (entityId.equals( rhs.entityId) )) ivarsEqual = false;
     if( ! (radioId == rhs.radioId)) ivarsEqual = false;
     if( ! (receiverState == rhs.receiverState)) ivarsEqual = false;
     if( ! (padding1 == rhs.padding1)) ivarsEqual = false;
     if( ! (receivedPower == rhs.receivedPower)) ivarsEqual = false;
     if( ! (transmitterEntityId.equals( rhs.transmitterEntityId) )) ivarsEqual = false;
     if( ! (transmitterRadioId == rhs.transmitterRadioId)) ivarsEqual = false;

    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
