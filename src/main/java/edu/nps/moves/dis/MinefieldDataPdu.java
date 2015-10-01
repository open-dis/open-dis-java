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
 * Section 5.3.10.3 Information about individual mines within a minefield. This is very, very wrong. UNFINISHED
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class MinefieldDataPdu extends MinefieldFamilyPdu implements Serializable
{
   /** Minefield ID */
   protected EntityID  minefieldID = new EntityID(); 

   /** ID of entity making request */
   protected EntityID  requestingEntityID = new EntityID(); 

   /** Minefield sequence number */
   protected int  minefieldSequenceNumbeer;

   /** request ID */
   protected short  requestID;

   /** pdu sequence number */
   protected short  pduSequenceNumber;

   /** number of pdus in response */
   protected short  numberOfPdus;

   /** how many mines are in this PDU */
   protected short  numberOfMinesInThisPdu;

   /** how many sensor type are in this PDU */
   protected short  numberOfSensorTypes;

   /** padding */
   protected short  pad2 = (short)0;

   /** 32 boolean fields */
   protected long  dataFilter;

   /** Mine type */
   protected EntityType  mineType = new EntityType(); 

   /** Sensor types, each 16 bits long */
   protected List< TwoByteChunk > sensorTypes = new ArrayList< TwoByteChunk >(); 
   /** Padding to get things 32-bit aligned. ^^^this is wrong--dyanmically sized padding needed */
   protected short  pad3;

   /** Mine locations */
   protected List< Vector3Float > mineLocation = new ArrayList< Vector3Float >(); 

/** Constructor */
 public MinefieldDataPdu()
 {
    setPduType( (short)39 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + minefieldID.getMarshalledSize();  // minefieldID
   marshalSize = marshalSize + requestingEntityID.getMarshalledSize();  // requestingEntityID
   marshalSize = marshalSize + 2;  // minefieldSequenceNumbeer
   marshalSize = marshalSize + 1;  // requestID
   marshalSize = marshalSize + 1;  // pduSequenceNumber
   marshalSize = marshalSize + 1;  // numberOfPdus
   marshalSize = marshalSize + 1;  // numberOfMinesInThisPdu
   marshalSize = marshalSize + 1;  // numberOfSensorTypes
   marshalSize = marshalSize + 1;  // pad2
   marshalSize = marshalSize + 4;  // dataFilter
   marshalSize = marshalSize + mineType.getMarshalledSize();  // mineType
   for(int idx=0; idx < sensorTypes.size(); idx++)
   {
        TwoByteChunk listElement = sensorTypes.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   marshalSize = marshalSize + 1;  // pad3
   for(int idx=0; idx < mineLocation.size(); idx++)
   {
        Vector3Float listElement = mineLocation.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setMinefieldID(EntityID pMinefieldID)
{ minefieldID = pMinefieldID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_minefieldID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_minefieldID")
public EntityID getMinefieldID()
{ return minefieldID; 
}

public void setRequestingEntityID(EntityID pRequestingEntityID)
{ requestingEntityID = pRequestingEntityID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_requestingEntityID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_requestingEntityID")
public EntityID getRequestingEntityID()
{ return requestingEntityID; 
}

public void setMinefieldSequenceNumbeer(int pMinefieldSequenceNumbeer)
{ minefieldSequenceNumbeer = pMinefieldSequenceNumbeer;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getMinefieldSequenceNumbeer()
{ return minefieldSequenceNumbeer; 
}

public void setRequestID(short pRequestID)
{ requestID = pRequestID;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getRequestID()
{ return requestID; 
}

public void setPduSequenceNumber(short pPduSequenceNumber)
{ pduSequenceNumber = pPduSequenceNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPduSequenceNumber()
{ return pduSequenceNumber; 
}

public void setNumberOfPdus(short pNumberOfPdus)
{ numberOfPdus = pNumberOfPdus;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getNumberOfPdus()
{ return numberOfPdus; 
}

@XmlAttribute
@Basic
public short getNumberOfMinesInThisPdu()
{ return (short)mineLocation.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfMinesInThisPdu method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfMinesInThisPdu(short pNumberOfMinesInThisPdu)
{ numberOfMinesInThisPdu = pNumberOfMinesInThisPdu;
}

@XmlAttribute
@Basic
public short getNumberOfSensorTypes()
{ return (short)sensorTypes.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfSensorTypes method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfSensorTypes(short pNumberOfSensorTypes)
{ numberOfSensorTypes = pNumberOfSensorTypes;
}

public void setPad2(short pPad2)
{ pad2 = pPad2;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPad2()
{ return pad2; 
}

public void setDataFilter(long pDataFilter)
{ dataFilter = pDataFilter;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getDataFilter()
{ return dataFilter; 
}

public void setMineType(EntityType pMineType)
{ mineType = pMineType;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_mineType;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_mineType")
public EntityType getMineType()
{ return mineType; 
}

public void setSensorTypes(List<TwoByteChunk> pSensorTypes)
{ sensorTypes = pSensorTypes;
}

@XmlElementWrapper(name="sensorTypesList" ) //  Jaxb
@OneToMany    // Hibernate
public List<TwoByteChunk> getSensorTypes()
{ return sensorTypes; }

public void setPad3(short pPad3)
{ pad3 = pPad3;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPad3()
{ return pad3; 
}

public void setMineLocation(List<Vector3Float> pMineLocation)
{ mineLocation = pMineLocation;
}

@XmlElementWrapper(name="mineLocationList" ) //  Jaxb
@OneToMany    // Hibernate
public List<Vector3Float> getMineLocation()
{ return mineLocation; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       minefieldID.marshal(dos);
       requestingEntityID.marshal(dos);
       dos.writeShort( (short)minefieldSequenceNumbeer);
       dos.writeByte( (byte)requestID);
       dos.writeByte( (byte)pduSequenceNumber);
       dos.writeByte( (byte)numberOfPdus);
       dos.writeByte( (byte)mineLocation.size());
       dos.writeByte( (byte)sensorTypes.size());
       dos.writeByte( (byte)pad2);
       dos.writeInt( (int)dataFilter);
       mineType.marshal(dos);

       for(int idx = 0; idx < sensorTypes.size(); idx++)
       {
            TwoByteChunk aTwoByteChunk = sensorTypes.get(idx);
            aTwoByteChunk.marshal(dos);
       } // end of list marshalling

       dos.writeByte( (byte)pad3);

       for(int idx = 0; idx < mineLocation.size(); idx++)
       {
            Vector3Float aVector3Float = mineLocation.get(idx);
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
       minefieldID.unmarshal(dis);
       requestingEntityID.unmarshal(dis);
       minefieldSequenceNumbeer = (int)dis.readUnsignedShort();
       requestID = (short)dis.readUnsignedByte();
       pduSequenceNumber = (short)dis.readUnsignedByte();
       numberOfPdus = (short)dis.readUnsignedByte();
       numberOfMinesInThisPdu = (short)dis.readUnsignedByte();
       numberOfSensorTypes = (short)dis.readUnsignedByte();
       pad2 = (short)dis.readUnsignedByte();
       dataFilter = dis.readInt();
       mineType.unmarshal(dis);
       for(int idx = 0; idx < numberOfSensorTypes; idx++)
       {
           TwoByteChunk anX = new TwoByteChunk();
           anX.unmarshal(dis);
           sensorTypes.add(anX);
       }

       pad3 = (short)dis.readUnsignedByte();
       for(int idx = 0; idx < numberOfMinesInThisPdu; idx++)
       {
           Vector3Float anX = new Vector3Float();
           anX.unmarshal(dis);
           mineLocation.add(anX);
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
       minefieldID.marshal(buff);
       requestingEntityID.marshal(buff);
       buff.putShort( (short)minefieldSequenceNumbeer);
       buff.put( (byte)requestID);
       buff.put( (byte)pduSequenceNumber);
       buff.put( (byte)numberOfPdus);
       buff.put( (byte)mineLocation.size());
       buff.put( (byte)sensorTypes.size());
       buff.put( (byte)pad2);
       buff.putInt( (int)dataFilter);
       mineType.marshal(buff);

       for(int idx = 0; idx < sensorTypes.size(); idx++)
       {
            TwoByteChunk aTwoByteChunk = (TwoByteChunk)sensorTypes.get(idx);
            aTwoByteChunk.marshal(buff);
       } // end of list marshalling

       buff.put( (byte)pad3);

       for(int idx = 0; idx < mineLocation.size(); idx++)
       {
            Vector3Float aVector3Float = (Vector3Float)mineLocation.get(idx);
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

       minefieldID.unmarshal(buff);
       requestingEntityID.unmarshal(buff);
       minefieldSequenceNumbeer = (int)(buff.getShort() & 0xFFFF);
       requestID = (short)(buff.get() & 0xFF);
       pduSequenceNumber = (short)(buff.get() & 0xFF);
       numberOfPdus = (short)(buff.get() & 0xFF);
       numberOfMinesInThisPdu = (short)(buff.get() & 0xFF);
       numberOfSensorTypes = (short)(buff.get() & 0xFF);
       pad2 = (short)(buff.get() & 0xFF);
       dataFilter = buff.getInt();
       mineType.unmarshal(buff);
       for(int idx = 0; idx < numberOfSensorTypes; idx++)
       {
            TwoByteChunk anX = new TwoByteChunk();
            anX.unmarshal(buff);
            sensorTypes.add(anX);
       }

       pad3 = (short)(buff.get() & 0xFF);
       for(int idx = 0; idx < numberOfMinesInThisPdu; idx++)
       {
            Vector3Float anX = new Vector3Float();
            anX.unmarshal(buff);
            mineLocation.add(anX);
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

    if(!(obj instanceof MinefieldDataPdu))
        return false;

     final MinefieldDataPdu rhs = (MinefieldDataPdu)obj;

     if( ! (minefieldID.equals( rhs.minefieldID) )) ivarsEqual = false;
     if( ! (requestingEntityID.equals( rhs.requestingEntityID) )) ivarsEqual = false;
     if( ! (minefieldSequenceNumbeer == rhs.minefieldSequenceNumbeer)) ivarsEqual = false;
     if( ! (requestID == rhs.requestID)) ivarsEqual = false;
     if( ! (pduSequenceNumber == rhs.pduSequenceNumber)) ivarsEqual = false;
     if( ! (numberOfPdus == rhs.numberOfPdus)) ivarsEqual = false;
     if( ! (numberOfMinesInThisPdu == rhs.numberOfMinesInThisPdu)) ivarsEqual = false;
     if( ! (numberOfSensorTypes == rhs.numberOfSensorTypes)) ivarsEqual = false;
     if( ! (pad2 == rhs.pad2)) ivarsEqual = false;
     if( ! (dataFilter == rhs.dataFilter)) ivarsEqual = false;
     if( ! (mineType.equals( rhs.mineType) )) ivarsEqual = false;

     for(int idx = 0; idx < sensorTypes.size(); idx++)
     {
        if( ! ( sensorTypes.get(idx).equals(rhs.sensorTypes.get(idx)))) ivarsEqual = false;
     }

     if( ! (pad3 == rhs.pad3)) ivarsEqual = false;

     for(int idx = 0; idx < mineLocation.size(); idx++)
     {
        if( ! ( mineLocation.get(idx).equals(rhs.mineLocation.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
