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
 * Section 5.3.8.2. Detailed information about a radio transmitter. This PDU requires manually written code to complete. The encodingScheme field can be used in multiple ways, which requires hand-written code to finish. UNFINISHED
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class SignalPdu extends RadioCommunicationsFamilyPdu implements Serializable
{
   /** ID of the entity that is the source of the communication, ie contains the radio */
   protected EntityID  entityId = new EntityID(); 

   /** particular radio within an entity */
   protected int  radioId;

   /** encoding scheme used, and enumeration */
   protected int  encodingScheme;

   /** tdl type */
   protected int  tdlType;

   /** sample rate */
   protected long  sampleRate;

   /** length of data, in bits */
   protected int  dataLength;

   /** number of samples. If the PDU contains encoded audio, this should be zero. */
   protected int  samples;

   /** list of eight bit values. Must be padded to fall on a 32 bit boundary. */
   protected List< OneByteChunk > data = new ArrayList< OneByteChunk >(); 

/** Constructor */
 public SignalPdu()
 {
    setPduType( (short)26 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + entityId.getMarshalledSize();  // entityId
   marshalSize = marshalSize + 2;  // radioId
   marshalSize = marshalSize + 2;  // encodingScheme
   marshalSize = marshalSize + 2;  // tdlType
   marshalSize = marshalSize + 4;  // sampleRate
   marshalSize = marshalSize + 2;  // dataLength
   marshalSize = marshalSize + 2;  // samples
   for(int idx=0; idx < data.size(); idx++)
   {
        OneByteChunk listElement = data.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

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

public void setEncodingScheme(int pEncodingScheme)
{ encodingScheme = pEncodingScheme;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getEncodingScheme()
{ return encodingScheme; 
}

public void setTdlType(int pTdlType)
{ tdlType = pTdlType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getTdlType()
{ return tdlType; 
}

public void setSampleRate(long pSampleRate)
{ sampleRate = pSampleRate;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getSampleRate()
{ return sampleRate; 
}

@XmlAttribute
@Basic
public int getDataLength()
{ return (int)data.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getdataLength method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setDataLength(int pDataLength)
{ dataLength = pDataLength;
}

public void setSamples(int pSamples)
{ samples = pSamples;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getSamples()
{ return samples; 
}

public void setData(List<OneByteChunk> pData)
{ data = pData;
}

@XmlElementWrapper(name="dataList" ) //  Jaxb
@OneToMany    // Hibernate
public List<OneByteChunk> getData()
{ return data; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       entityId.marshal(dos);
       dos.writeShort( (short)radioId);
       dos.writeShort( (short)encodingScheme);
       dos.writeShort( (short)tdlType);
       dos.writeInt( (int)sampleRate);
       dos.writeShort( (short)data.size());
       dos.writeShort( (short)samples);

       for(int idx = 0; idx < data.size(); idx++)
       {
            OneByteChunk aOneByteChunk = data.get(idx);
            aOneByteChunk.marshal(dos);
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
       entityId.unmarshal(dis);
       radioId = (int)dis.readUnsignedShort();
       encodingScheme = (int)dis.readUnsignedShort();
       tdlType = (int)dis.readUnsignedShort();
       sampleRate = dis.readInt();
       dataLength = (int)dis.readUnsignedShort();
       samples = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < dataLength; idx++)
       {
           OneByteChunk anX = new OneByteChunk();
           anX.unmarshal(dis);
           data.add(anX);
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
       entityId.marshal(buff);
       buff.putShort( (short)radioId);
       buff.putShort( (short)encodingScheme);
       buff.putShort( (short)tdlType);
       buff.putInt( (int)sampleRate);
       buff.putShort( (short)data.size());
       buff.putShort( (short)samples);

       for(int idx = 0; idx < data.size(); idx++)
       {
            OneByteChunk aOneByteChunk = (OneByteChunk)data.get(idx);
            aOneByteChunk.marshal(buff);
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

       entityId.unmarshal(buff);
       radioId = (int)(buff.getShort() & 0xFFFF);
       encodingScheme = (int)(buff.getShort() & 0xFFFF);
       tdlType = (int)(buff.getShort() & 0xFFFF);
       sampleRate = buff.getInt();
       dataLength = (int)(buff.getShort() & 0xFFFF);
       samples = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < dataLength; idx++)
       {
            OneByteChunk anX = new OneByteChunk();
            anX.unmarshal(buff);
            data.add(anX);
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

    if(!(obj instanceof SignalPdu))
        return false;

     final SignalPdu rhs = (SignalPdu)obj;

     if( ! (entityId.equals( rhs.entityId) )) ivarsEqual = false;
     if( ! (radioId == rhs.radioId)) ivarsEqual = false;
     if( ! (encodingScheme == rhs.encodingScheme)) ivarsEqual = false;
     if( ! (tdlType == rhs.tdlType)) ivarsEqual = false;
     if( ! (sampleRate == rhs.sampleRate)) ivarsEqual = false;
     if( ! (dataLength == rhs.dataLength)) ivarsEqual = false;
     if( ! (samples == rhs.samples)) ivarsEqual = false;

     for(int idx = 0; idx < data.size(); idx++)
     {
        if( ! ( data.get(idx).equals(rhs.data.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
