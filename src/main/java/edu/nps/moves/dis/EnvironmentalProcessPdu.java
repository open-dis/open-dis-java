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
 * Section 5.3.11.1: Information about environmental effects and processes. This requires manual cleanup. the environmental        record is variable, as is the padding. UNFINISHED
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class EnvironmentalProcessPdu extends SyntheticEnvironmentFamilyPdu implements Serializable
{
   /** Environmental process ID */
   protected EntityID  environementalProcessID = new EntityID(); 

   /** Environment type */
   protected EntityType  environmentType = new EntityType(); 

   /** model type */
   protected short  modelType;

   /** Environment status */
   protected short  environmentStatus;

   /** number of environment records  */
   protected short  numberOfEnvironmentRecords;

   /** PDU sequence number for the environmentla process if pdu sequencing required */
   protected int  sequenceNumber;

   /** environemt records */
   protected List< Environment > environmentRecords = new ArrayList< Environment >(); 

/** Constructor */
 public EnvironmentalProcessPdu()
 {
    setPduType( (short)41 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + environementalProcessID.getMarshalledSize();  // environementalProcessID
   marshalSize = marshalSize + environmentType.getMarshalledSize();  // environmentType
   marshalSize = marshalSize + 1;  // modelType
   marshalSize = marshalSize + 1;  // environmentStatus
   marshalSize = marshalSize + 1;  // numberOfEnvironmentRecords
   marshalSize = marshalSize + 2;  // sequenceNumber
   for(int idx=0; idx < environmentRecords.size(); idx++)
   {
        Environment listElement = environmentRecords.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setEnvironementalProcessID(EntityID pEnvironementalProcessID)
{ environementalProcessID = pEnvironementalProcessID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_environementalProcessID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_environementalProcessID")
public EntityID getEnvironementalProcessID()
{ return environementalProcessID; 
}

public void setEnvironmentType(EntityType pEnvironmentType)
{ environmentType = pEnvironmentType;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_environmentType;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_environmentType")
public EntityType getEnvironmentType()
{ return environmentType; 
}

public void setModelType(short pModelType)
{ modelType = pModelType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getModelType()
{ return modelType; 
}

public void setEnvironmentStatus(short pEnvironmentStatus)
{ environmentStatus = pEnvironmentStatus;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getEnvironmentStatus()
{ return environmentStatus; 
}

@XmlAttribute
@Basic
public short getNumberOfEnvironmentRecords()
{ return (short)environmentRecords.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfEnvironmentRecords method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfEnvironmentRecords(short pNumberOfEnvironmentRecords)
{ numberOfEnvironmentRecords = pNumberOfEnvironmentRecords;
}

public void setSequenceNumber(int pSequenceNumber)
{ sequenceNumber = pSequenceNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getSequenceNumber()
{ return sequenceNumber; 
}

public void setEnvironmentRecords(List<Environment> pEnvironmentRecords)
{ environmentRecords = pEnvironmentRecords;
}

@XmlElementWrapper(name="environmentRecordsList" ) //  Jaxb
@OneToMany    // Hibernate
public List<Environment> getEnvironmentRecords()
{ return environmentRecords; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       environementalProcessID.marshal(dos);
       environmentType.marshal(dos);
       dos.writeByte( (byte)modelType);
       dos.writeByte( (byte)environmentStatus);
       dos.writeByte( (byte)environmentRecords.size());
       dos.writeShort( (short)sequenceNumber);

       for(int idx = 0; idx < environmentRecords.size(); idx++)
       {
            Environment aEnvironment = environmentRecords.get(idx);
            aEnvironment.marshal(dos);
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
       environementalProcessID.unmarshal(dis);
       environmentType.unmarshal(dis);
       modelType = (short)dis.readUnsignedByte();
       environmentStatus = (short)dis.readUnsignedByte();
       numberOfEnvironmentRecords = (short)dis.readUnsignedByte();
       sequenceNumber = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < numberOfEnvironmentRecords; idx++)
       {
           Environment anX = new Environment();
           anX.unmarshal(dis);
           environmentRecords.add(anX);
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
       environementalProcessID.marshal(buff);
       environmentType.marshal(buff);
       buff.put( (byte)modelType);
       buff.put( (byte)environmentStatus);
       buff.put( (byte)environmentRecords.size());
       buff.putShort( (short)sequenceNumber);

       for(int idx = 0; idx < environmentRecords.size(); idx++)
       {
            Environment aEnvironment = (Environment)environmentRecords.get(idx);
            aEnvironment.marshal(buff);
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

       environementalProcessID.unmarshal(buff);
       environmentType.unmarshal(buff);
       modelType = (short)(buff.get() & 0xFF);
       environmentStatus = (short)(buff.get() & 0xFF);
       numberOfEnvironmentRecords = (short)(buff.get() & 0xFF);
       sequenceNumber = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < numberOfEnvironmentRecords; idx++)
       {
            Environment anX = new Environment();
            anX.unmarshal(buff);
            environmentRecords.add(anX);
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

    if(!(obj instanceof EnvironmentalProcessPdu))
        return false;

     final EnvironmentalProcessPdu rhs = (EnvironmentalProcessPdu)obj;

     if( ! (environementalProcessID.equals( rhs.environementalProcessID) )) ivarsEqual = false;
     if( ! (environmentType.equals( rhs.environmentType) )) ivarsEqual = false;
     if( ! (modelType == rhs.modelType)) ivarsEqual = false;
     if( ! (environmentStatus == rhs.environmentStatus)) ivarsEqual = false;
     if( ! (numberOfEnvironmentRecords == rhs.numberOfEnvironmentRecords)) ivarsEqual = false;
     if( ! (sequenceNumber == rhs.sequenceNumber)) ivarsEqual = false;

     for(int idx = 0; idx < environmentRecords.size(); idx++)
     {
        if( ! ( environmentRecords.get(idx).equals(rhs.environmentRecords.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
