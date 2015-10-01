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
 * 5.2.47.  Layer header.
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class LayerHeader extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_LayerHeader;

   /** Layer number */
   protected short  layerNumber;

   /** Layer speccific information enumeration */
   protected short  layerSpecificInformaiton;

   /** information length */
   protected int  length;


/** Constructor */
 public LayerHeader()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // layerNumber
   marshalSize = marshalSize + 1;  // layerSpecificInformaiton
   marshalSize = marshalSize + 2;  // length

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_LayerHeader()
{
   return pk_LayerHeader;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_LayerHeader(long pKeyName)
{
   this.pk_LayerHeader = pKeyName;
}

public void setLayerNumber(short pLayerNumber)
{ layerNumber = pLayerNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getLayerNumber()
{ return layerNumber; 
}

public void setLayerSpecificInformaiton(short pLayerSpecificInformaiton)
{ layerSpecificInformaiton = pLayerSpecificInformaiton;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getLayerSpecificInformaiton()
{ return layerSpecificInformaiton; 
}

public void setLength(int pLength)
{ length = pLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getLength()
{ return length; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)layerNumber);
       dos.writeByte( (byte)layerSpecificInformaiton);
       dos.writeShort( (short)length);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       layerNumber = (short)dis.readUnsignedByte();
       layerSpecificInformaiton = (short)dis.readUnsignedByte();
       length = (int)dis.readUnsignedShort();
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
       buff.put( (byte)layerNumber);
       buff.put( (byte)layerSpecificInformaiton);
       buff.putShort( (short)length);
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
       layerNumber = (short)(buff.get() & 0xFF);
       layerSpecificInformaiton = (short)(buff.get() & 0xFF);
       length = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof LayerHeader))
        return false;

     final LayerHeader rhs = (LayerHeader)obj;

     if( ! (layerNumber == rhs.layerNumber)) ivarsEqual = false;
     if( ! (layerSpecificInformaiton == rhs.layerSpecificInformaiton)) ivarsEqual = false;
     if( ! (length == rhs.length)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
