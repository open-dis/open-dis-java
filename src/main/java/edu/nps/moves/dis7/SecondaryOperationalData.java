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
 * Additional operational data for an IFF emitting system and the number of IFF Fundamental Parameter Data records Section 6.2.76.
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class SecondaryOperationalData extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_SecondaryOperationalData;

   /** additional operational characteristics of the IFF emitting system. Each 8-bit field will vary depending on the system type. */
   protected short  operationalData1;

   /** additional operational characteristics of the IFF emitting system. Each 8-bit field will vary depending on the system type. */
   protected short  operationalData2;

   /** the number of IFF Fundamental Parameter Data records that follow */
   protected int  numberOfIFFFundamentalParameterRecords;


/** Constructor */
 public SecondaryOperationalData()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // operationalData1
   marshalSize = marshalSize + 1;  // operationalData2
   marshalSize = marshalSize + 2;  // numberOfIFFFundamentalParameterRecords

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_SecondaryOperationalData()
{
   return pk_SecondaryOperationalData;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_SecondaryOperationalData(long pKeyName)
{
   this.pk_SecondaryOperationalData = pKeyName;
}

public void setOperationalData1(short pOperationalData1)
{ operationalData1 = pOperationalData1;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getOperationalData1()
{ return operationalData1; 
}

public void setOperationalData2(short pOperationalData2)
{ operationalData2 = pOperationalData2;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getOperationalData2()
{ return operationalData2; 
}

public void setNumberOfIFFFundamentalParameterRecords(int pNumberOfIFFFundamentalParameterRecords)
{ numberOfIFFFundamentalParameterRecords = pNumberOfIFFFundamentalParameterRecords;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getNumberOfIFFFundamentalParameterRecords()
{ return numberOfIFFFundamentalParameterRecords; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)operationalData1);
       dos.writeByte( (byte)operationalData2);
       dos.writeShort( (short)numberOfIFFFundamentalParameterRecords);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       operationalData1 = (short)dis.readUnsignedByte();
       operationalData2 = (short)dis.readUnsignedByte();
       numberOfIFFFundamentalParameterRecords = (int)dis.readUnsignedShort();
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
       buff.put( (byte)operationalData1);
       buff.put( (byte)operationalData2);
       buff.putShort( (short)numberOfIFFFundamentalParameterRecords);
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
       operationalData1 = (short)(buff.get() & 0xFF);
       operationalData2 = (short)(buff.get() & 0xFF);
       numberOfIFFFundamentalParameterRecords = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof SecondaryOperationalData))
        return false;

     final SecondaryOperationalData rhs = (SecondaryOperationalData)obj;

     if( ! (operationalData1 == rhs.operationalData1)) ivarsEqual = false;
     if( ! (operationalData2 == rhs.operationalData2)) ivarsEqual = false;
     if( ! (numberOfIFFFundamentalParameterRecords == rhs.numberOfIFFFundamentalParameterRecords)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
