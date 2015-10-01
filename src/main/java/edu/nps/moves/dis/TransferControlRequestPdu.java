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
 * Section 5.3.9.3 Information initiating the dyanic allocation and control of simulation entities         between two simulation applications. Requires manual cleanup. The padding between record sets is variable. UNFINISHED
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class TransferControlRequestPdu extends EntityManagementFamilyPdu implements Serializable
{
   /** ID of entity originating request */
   protected EntityID  orginatingEntityID = new EntityID(); 

   /** ID of entity receiving request */
   protected EntityID  recevingEntityID = new EntityID(); 

   /** ID ofrequest */
   protected long  requestID;

   /** required level of reliabliity service. */
   protected short  requiredReliabilityService;

   /** type of transfer desired */
   protected short  tranferType;

   /** The entity for which control is being requested to transfer */
   protected EntityID  transferEntityID = new EntityID(); 

   /** number of record sets to transfer */
   protected short  numberOfRecordSets;

   /** ^^^This is wrong--the RecordSet class needs more work */
   protected List< RecordSet > recordSets = new ArrayList< RecordSet >(); 

/** Constructor */
 public TransferControlRequestPdu()
 {
    setPduType( (short)35 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + orginatingEntityID.getMarshalledSize();  // orginatingEntityID
   marshalSize = marshalSize + recevingEntityID.getMarshalledSize();  // recevingEntityID
   marshalSize = marshalSize + 4;  // requestID
   marshalSize = marshalSize + 1;  // requiredReliabilityService
   marshalSize = marshalSize + 1;  // tranferType
   marshalSize = marshalSize + transferEntityID.getMarshalledSize();  // transferEntityID
   marshalSize = marshalSize + 1;  // numberOfRecordSets
   for(int idx=0; idx < recordSets.size(); idx++)
   {
        RecordSet listElement = recordSets.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setOrginatingEntityID(EntityID pOrginatingEntityID)
{ orginatingEntityID = pOrginatingEntityID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_orginatingEntityID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_orginatingEntityID")
public EntityID getOrginatingEntityID()
{ return orginatingEntityID; 
}

public void setRecevingEntityID(EntityID pRecevingEntityID)
{ recevingEntityID = pRecevingEntityID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_recevingEntityID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_recevingEntityID")
public EntityID getRecevingEntityID()
{ return recevingEntityID; 
}

public void setRequestID(long pRequestID)
{ requestID = pRequestID;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getRequestID()
{ return requestID; 
}

public void setRequiredReliabilityService(short pRequiredReliabilityService)
{ requiredReliabilityService = pRequiredReliabilityService;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getRequiredReliabilityService()
{ return requiredReliabilityService; 
}

public void setTranferType(short pTranferType)
{ tranferType = pTranferType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getTranferType()
{ return tranferType; 
}

public void setTransferEntityID(EntityID pTransferEntityID)
{ transferEntityID = pTransferEntityID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_transferEntityID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_transferEntityID")
public EntityID getTransferEntityID()
{ return transferEntityID; 
}

@XmlAttribute
@Basic
public short getNumberOfRecordSets()
{ return (short)recordSets.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfRecordSets method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfRecordSets(short pNumberOfRecordSets)
{ numberOfRecordSets = pNumberOfRecordSets;
}

public void setRecordSets(List<RecordSet> pRecordSets)
{ recordSets = pRecordSets;
}

@XmlElementWrapper(name="recordSetsList" ) //  Jaxb
@OneToMany    // Hibernate
public List<RecordSet> getRecordSets()
{ return recordSets; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       orginatingEntityID.marshal(dos);
       recevingEntityID.marshal(dos);
       dos.writeInt( (int)requestID);
       dos.writeByte( (byte)requiredReliabilityService);
       dos.writeByte( (byte)tranferType);
       transferEntityID.marshal(dos);
       dos.writeByte( (byte)recordSets.size());

       for(int idx = 0; idx < recordSets.size(); idx++)
       {
            RecordSet aRecordSet = recordSets.get(idx);
            aRecordSet.marshal(dos);
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
       orginatingEntityID.unmarshal(dis);
       recevingEntityID.unmarshal(dis);
       requestID = dis.readInt();
       requiredReliabilityService = (short)dis.readUnsignedByte();
       tranferType = (short)dis.readUnsignedByte();
       transferEntityID.unmarshal(dis);
       numberOfRecordSets = (short)dis.readUnsignedByte();
       for(int idx = 0; idx < numberOfRecordSets; idx++)
       {
           RecordSet anX = new RecordSet();
           anX.unmarshal(dis);
           recordSets.add(anX);
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
       orginatingEntityID.marshal(buff);
       recevingEntityID.marshal(buff);
       buff.putInt( (int)requestID);
       buff.put( (byte)requiredReliabilityService);
       buff.put( (byte)tranferType);
       transferEntityID.marshal(buff);
       buff.put( (byte)recordSets.size());

       for(int idx = 0; idx < recordSets.size(); idx++)
       {
            RecordSet aRecordSet = (RecordSet)recordSets.get(idx);
            aRecordSet.marshal(buff);
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

       orginatingEntityID.unmarshal(buff);
       recevingEntityID.unmarshal(buff);
       requestID = buff.getInt();
       requiredReliabilityService = (short)(buff.get() & 0xFF);
       tranferType = (short)(buff.get() & 0xFF);
       transferEntityID.unmarshal(buff);
       numberOfRecordSets = (short)(buff.get() & 0xFF);
       for(int idx = 0; idx < numberOfRecordSets; idx++)
       {
            RecordSet anX = new RecordSet();
            anX.unmarshal(buff);
            recordSets.add(anX);
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

    if(!(obj instanceof TransferControlRequestPdu))
        return false;

     final TransferControlRequestPdu rhs = (TransferControlRequestPdu)obj;

     if( ! (orginatingEntityID.equals( rhs.orginatingEntityID) )) ivarsEqual = false;
     if( ! (recevingEntityID.equals( rhs.recevingEntityID) )) ivarsEqual = false;
     if( ! (requestID == rhs.requestID)) ivarsEqual = false;
     if( ! (requiredReliabilityService == rhs.requiredReliabilityService)) ivarsEqual = false;
     if( ! (tranferType == rhs.tranferType)) ivarsEqual = false;
     if( ! (transferEntityID.equals( rhs.transferEntityID) )) ivarsEqual = false;
     if( ! (numberOfRecordSets == rhs.numberOfRecordSets)) ivarsEqual = false;

     for(int idx = 0; idx < recordSets.size(); idx++)
     {
        if( ! ( recordSets.get(idx).equals(rhs.recordSets.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
