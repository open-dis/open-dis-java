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
 * Unique designation of an attached or unattached intercom in an event or exercirse. Section 6.2.48
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class IntercomIdentifier extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_IntercomIdentifier;

   protected int  siteNumber;

   protected int  applicationNumber;

   protected int  referenceNumber;

   protected int  intercomNumber;


/** Constructor */
 public IntercomIdentifier()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // siteNumber
   marshalSize = marshalSize + 2;  // applicationNumber
   marshalSize = marshalSize + 2;  // referenceNumber
   marshalSize = marshalSize + 2;  // intercomNumber

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_IntercomIdentifier()
{
   return pk_IntercomIdentifier;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_IntercomIdentifier(long pKeyName)
{
   this.pk_IntercomIdentifier = pKeyName;
}

public void setSiteNumber(int pSiteNumber)
{ siteNumber = pSiteNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getSiteNumber()
{ return siteNumber; 
}

public void setApplicationNumber(int pApplicationNumber)
{ applicationNumber = pApplicationNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getApplicationNumber()
{ return applicationNumber; 
}

public void setReferenceNumber(int pReferenceNumber)
{ referenceNumber = pReferenceNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getReferenceNumber()
{ return referenceNumber; 
}

public void setIntercomNumber(int pIntercomNumber)
{ intercomNumber = pIntercomNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getIntercomNumber()
{ return intercomNumber; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)siteNumber);
       dos.writeShort( (short)applicationNumber);
       dos.writeShort( (short)referenceNumber);
       dos.writeShort( (short)intercomNumber);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       siteNumber = (int)dis.readUnsignedShort();
       applicationNumber = (int)dis.readUnsignedShort();
       referenceNumber = (int)dis.readUnsignedShort();
       intercomNumber = (int)dis.readUnsignedShort();
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
       buff.putShort( (short)siteNumber);
       buff.putShort( (short)applicationNumber);
       buff.putShort( (short)referenceNumber);
       buff.putShort( (short)intercomNumber);
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
       siteNumber = (int)(buff.getShort() & 0xFFFF);
       applicationNumber = (int)(buff.getShort() & 0xFFFF);
       referenceNumber = (int)(buff.getShort() & 0xFFFF);
       intercomNumber = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof IntercomIdentifier))
        return false;

     final IntercomIdentifier rhs = (IntercomIdentifier)obj;

     if( ! (siteNumber == rhs.siteNumber)) ivarsEqual = false;
     if( ! (applicationNumber == rhs.applicationNumber)) ivarsEqual = false;
     if( ! (referenceNumber == rhs.referenceNumber)) ivarsEqual = false;
     if( ! (intercomNumber == rhs.intercomNumber)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
