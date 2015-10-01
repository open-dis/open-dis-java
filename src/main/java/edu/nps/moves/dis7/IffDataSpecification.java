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
 * Requires hand coding to be useful. Section 6.2.43
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class IffDataSpecification extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_IffDataSpecification;

   /** Number of iff records */
   protected int  numberOfIffDataRecords;

   /** IFF data records */
   protected List< IFFData > iffDataRecords = new ArrayList< IFFData >(); 

/** Constructor */
 public IffDataSpecification()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // numberOfIffDataRecords
   for(int idx=0; idx < iffDataRecords.size(); idx++)
   {
        IFFData listElement = iffDataRecords.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_IffDataSpecification()
{
   return pk_IffDataSpecification;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_IffDataSpecification(long pKeyName)
{
   this.pk_IffDataSpecification = pKeyName;
}

@XmlAttribute
@Basic
public int getNumberOfIffDataRecords()
{ return (int)iffDataRecords.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfIffDataRecords method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfIffDataRecords(int pNumberOfIffDataRecords)
{ numberOfIffDataRecords = pNumberOfIffDataRecords;
}

public void setIffDataRecords(List<IFFData> pIffDataRecords)
{ iffDataRecords = pIffDataRecords;
}

@XmlElementWrapper(name="iffDataRecordsList" ) //  Jaxb
@OneToMany    // Hibernate
public List<IFFData> getIffDataRecords()
{ return iffDataRecords; }


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)iffDataRecords.size());

       for(int idx = 0; idx < iffDataRecords.size(); idx++)
       {
            IFFData aIFFData = iffDataRecords.get(idx);
            aIFFData.marshal(dos);
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
       numberOfIffDataRecords = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < numberOfIffDataRecords; idx++)
       {
           IFFData anX = new IFFData();
           anX.unmarshal(dis);
           iffDataRecords.add(anX);
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
       buff.putShort( (short)iffDataRecords.size());

       for(int idx = 0; idx < iffDataRecords.size(); idx++)
       {
            IFFData aIFFData = (IFFData)iffDataRecords.get(idx);
            aIFFData.marshal(buff);
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
       numberOfIffDataRecords = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < numberOfIffDataRecords; idx++)
       {
            IFFData anX = new IFFData();
            anX.unmarshal(buff);
            iffDataRecords.add(anX);
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

    if(!(obj instanceof IffDataSpecification))
        return false;

     final IffDataSpecification rhs = (IffDataSpecification)obj;

     if( ! (numberOfIffDataRecords == rhs.numberOfIffDataRecords)) ivarsEqual = false;

     for(int idx = 0; idx < iffDataRecords.size(); idx++)
     {
        if( ! ( iffDataRecords.get(idx).equals(rhs.iffDataRecords.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
