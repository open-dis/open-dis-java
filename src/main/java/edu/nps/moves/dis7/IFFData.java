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
 * repeating element if IFF Data specification record
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class IFFData extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_IFFData;

   /** enumeration for type of record */
   protected long  recordType;

   /** length of record. Should be padded to 32 bit boundary. */
   protected int  recordLength;

   /** IFF data. */
   protected List< OneByteChunk > iffData = new ArrayList< OneByteChunk >(); 

/** Constructor */
 public IFFData()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // recordType
   marshalSize = marshalSize + 2;  // recordLength
   for(int idx=0; idx < iffData.size(); idx++)
   {
        OneByteChunk listElement = iffData.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_IFFData()
{
   return pk_IFFData;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_IFFData(long pKeyName)
{
   this.pk_IFFData = pKeyName;
}

public void setRecordType(long pRecordType)
{ recordType = pRecordType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getRecordType()
{ return recordType; 
}

@XmlAttribute
@Basic
public int getRecordLength()
{ return (int)iffData.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getrecordLength method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setRecordLength(int pRecordLength)
{ recordLength = pRecordLength;
}

public void setIffData(List<OneByteChunk> pIffData)
{ iffData = pIffData;
}

@XmlElementWrapper(name="iffDataList" ) //  Jaxb
@OneToMany    // Hibernate
public List<OneByteChunk> getIffData()
{ return iffData; }


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)recordType);
       dos.writeShort( (short)iffData.size());

       for(int idx = 0; idx < iffData.size(); idx++)
       {
            OneByteChunk aOneByteChunk = iffData.get(idx);
            aOneByteChunk.marshal(dos);
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
       recordType = dis.readInt();
       recordLength = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < recordLength; idx++)
       {
           OneByteChunk anX = new OneByteChunk();
           anX.unmarshal(dis);
           iffData.add(anX);
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
       buff.putInt( (int)recordType);
       buff.putShort( (short)iffData.size());

       for(int idx = 0; idx < iffData.size(); idx++)
       {
            OneByteChunk aOneByteChunk = (OneByteChunk)iffData.get(idx);
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
       recordType = buff.getInt();
       recordLength = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < recordLength; idx++)
       {
            OneByteChunk anX = new OneByteChunk();
            anX.unmarshal(buff);
            iffData.add(anX);
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

    if(!(obj instanceof IFFData))
        return false;

     final IFFData rhs = (IFFData)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (recordLength == rhs.recordLength)) ivarsEqual = false;

     for(int idx = 0; idx < iffData.size(); idx++)
     {
        if( ! ( iffData.get(idx).equals(rhs.iffData.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
