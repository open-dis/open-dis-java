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
 * The specification of an individual segment of a linear segment synthetic environment object in a Linear Object State PDU Section 6.2.52
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class LinearSegmentParameter extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_LinearSegmentParameter;

   /** the individual segment of the linear segment  */
   protected short  segmentNumber;

   /**  whether a modification has been made to the point object’s location or orientation */
   protected short  segmentModification;

   /** general dynamic appearance attributes of the segment. This record shall be defined as a 16-bit record of enumerations. The values defined for this record are included in Section 12 of SISO-REF-010. */
   protected int  generalSegmentAppearance;

   /** This field shall specify specific dynamic appearance attributes of the segment. This record shall be defined as a 32-bit record of enumerations. */
   protected long  specificSegmentAppearance;

   /** This field shall specify the location of the linear segment in the simulated world and shall be represented by a World Coordinates record  */
   protected Vector3Double  segmentLocation = new Vector3Double(); 

   /** orientation of the linear segment about the segment location and shall be represented by a Euler Angles record  */
   protected EulerAngles  segmentOrientation = new EulerAngles(); 

   /** length of the linear segment, in meters, extending in the positive X direction */
   protected float  segmentLength;

   /** The total width of the linear segment, in meters, shall be specified by a 16-bit unsigned integer. One-half of the width shall extend in the positive Y direction, and one-half of the width shall extend in the negative Y direction. */
   protected float  segmentWidth;

   /** The height of the linear segment, in meters, above ground shall be specified by a 16-bit unsigned integer. */
   protected float  segmentHeight;

   /** The depth of the linear segment, in meters, below ground level  */
   protected float  segmentDepth;

   /** padding */
   protected long  padding;


/** Constructor */
 public LinearSegmentParameter()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // segmentNumber
   marshalSize = marshalSize + 1;  // segmentModification
   marshalSize = marshalSize + 2;  // generalSegmentAppearance
   marshalSize = marshalSize + 4;  // specificSegmentAppearance
   marshalSize = marshalSize + segmentLocation.getMarshalledSize();  // segmentLocation
   marshalSize = marshalSize + segmentOrientation.getMarshalledSize();  // segmentOrientation
   marshalSize = marshalSize + 4;  // segmentLength
   marshalSize = marshalSize + 4;  // segmentWidth
   marshalSize = marshalSize + 4;  // segmentHeight
   marshalSize = marshalSize + 4;  // segmentDepth
   marshalSize = marshalSize + 4;  // padding

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_LinearSegmentParameter()
{
   return pk_LinearSegmentParameter;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_LinearSegmentParameter(long pKeyName)
{
   this.pk_LinearSegmentParameter = pKeyName;
}

public void setSegmentNumber(short pSegmentNumber)
{ segmentNumber = pSegmentNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getSegmentNumber()
{ return segmentNumber; 
}

public void setSegmentModification(short pSegmentModification)
{ segmentModification = pSegmentModification;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getSegmentModification()
{ return segmentModification; 
}

public void setGeneralSegmentAppearance(int pGeneralSegmentAppearance)
{ generalSegmentAppearance = pGeneralSegmentAppearance;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getGeneralSegmentAppearance()
{ return generalSegmentAppearance; 
}

public void setSpecificSegmentAppearance(long pSpecificSegmentAppearance)
{ specificSegmentAppearance = pSpecificSegmentAppearance;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getSpecificSegmentAppearance()
{ return specificSegmentAppearance; 
}

public void setSegmentLocation(Vector3Double pSegmentLocation)
{ segmentLocation = pSegmentLocation;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_segmentLocation;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_segmentLocation")
public Vector3Double getSegmentLocation()
{ return segmentLocation; 
}

public void setSegmentOrientation(EulerAngles pSegmentOrientation)
{ segmentOrientation = pSegmentOrientation;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_segmentOrientation;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_segmentOrientation")
public EulerAngles getSegmentOrientation()
{ return segmentOrientation; 
}

public void setSegmentLength(float pSegmentLength)
{ segmentLength = pSegmentLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getSegmentLength()
{ return segmentLength; 
}

public void setSegmentWidth(float pSegmentWidth)
{ segmentWidth = pSegmentWidth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getSegmentWidth()
{ return segmentWidth; 
}

public void setSegmentHeight(float pSegmentHeight)
{ segmentHeight = pSegmentHeight;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getSegmentHeight()
{ return segmentHeight; 
}

public void setSegmentDepth(float pSegmentDepth)
{ segmentDepth = pSegmentDepth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public float getSegmentDepth()
{ return segmentDepth; 
}

public void setPadding(long pPadding)
{ padding = pPadding;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getPadding()
{ return padding; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)segmentNumber);
       dos.writeByte( (byte)segmentModification);
       dos.writeShort( (short)generalSegmentAppearance);
       dos.writeInt( (int)specificSegmentAppearance);
       segmentLocation.marshal(dos);
       segmentOrientation.marshal(dos);
       dos.writeFloat( (float)segmentLength);
       dos.writeFloat( (float)segmentWidth);
       dos.writeFloat( (float)segmentHeight);
       dos.writeFloat( (float)segmentDepth);
       dos.writeInt( (int)padding);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       segmentNumber = (short)dis.readUnsignedByte();
       segmentModification = (short)dis.readUnsignedByte();
       generalSegmentAppearance = (int)dis.readUnsignedShort();
       specificSegmentAppearance = dis.readInt();
       segmentLocation.unmarshal(dis);
       segmentOrientation.unmarshal(dis);
       segmentLength = dis.readFloat();
       segmentWidth = dis.readFloat();
       segmentHeight = dis.readFloat();
       segmentDepth = dis.readFloat();
       padding = dis.readInt();
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
       buff.put( (byte)segmentNumber);
       buff.put( (byte)segmentModification);
       buff.putShort( (short)generalSegmentAppearance);
       buff.putInt( (int)specificSegmentAppearance);
       segmentLocation.marshal(buff);
       segmentOrientation.marshal(buff);
       buff.putFloat( (float)segmentLength);
       buff.putFloat( (float)segmentWidth);
       buff.putFloat( (float)segmentHeight);
       buff.putFloat( (float)segmentDepth);
       buff.putInt( (int)padding);
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
       segmentNumber = (short)(buff.get() & 0xFF);
       segmentModification = (short)(buff.get() & 0xFF);
       generalSegmentAppearance = (int)(buff.getShort() & 0xFFFF);
       specificSegmentAppearance = buff.getInt();
       segmentLocation.unmarshal(buff);
       segmentOrientation.unmarshal(buff);
       segmentLength = buff.getFloat();
       segmentWidth = buff.getFloat();
       segmentHeight = buff.getFloat();
       segmentDepth = buff.getFloat();
       padding = buff.getInt();
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

    if(!(obj instanceof LinearSegmentParameter))
        return false;

     final LinearSegmentParameter rhs = (LinearSegmentParameter)obj;

     if( ! (segmentNumber == rhs.segmentNumber)) ivarsEqual = false;
     if( ! (segmentModification == rhs.segmentModification)) ivarsEqual = false;
     if( ! (generalSegmentAppearance == rhs.generalSegmentAppearance)) ivarsEqual = false;
     if( ! (specificSegmentAppearance == rhs.specificSegmentAppearance)) ivarsEqual = false;
     if( ! (segmentLocation.equals( rhs.segmentLocation) )) ivarsEqual = false;
     if( ! (segmentOrientation.equals( rhs.segmentOrientation) )) ivarsEqual = false;
     if( ! (segmentLength == rhs.segmentLength)) ivarsEqual = false;
     if( ! (segmentWidth == rhs.segmentWidth)) ivarsEqual = false;
     if( ! (segmentHeight == rhs.segmentHeight)) ivarsEqual = false;
     if( ! (segmentDepth == rhs.segmentDepth)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
