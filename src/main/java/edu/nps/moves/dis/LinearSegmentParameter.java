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
 * 5.2.48: Linear segment parameters
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

   /** number of segments */
   protected short  segmentNumber;

   /** segment appearance */
   protected SixByteChunk  segmentAppearance = new SixByteChunk(); 

   /** location */
   protected Vector3Double  location = new Vector3Double(); 

   /** orientation */
   protected Orientation  orientation = new Orientation(); 

   /** segmentLength */
   protected int  segmentLength;

   /** segmentWidth */
   protected int  segmentWidth;

   /** segmentHeight */
   protected int  segmentHeight;

   /** segment Depth */
   protected int  segmentDepth;

   /** segment Depth */
   protected long  pad1;


/** Constructor */
 public LinearSegmentParameter()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // segmentNumber
   marshalSize = marshalSize + segmentAppearance.getMarshalledSize();  // segmentAppearance
   marshalSize = marshalSize + location.getMarshalledSize();  // location
   marshalSize = marshalSize + orientation.getMarshalledSize();  // orientation
   marshalSize = marshalSize + 2;  // segmentLength
   marshalSize = marshalSize + 2;  // segmentWidth
   marshalSize = marshalSize + 2;  // segmentHeight
   marshalSize = marshalSize + 2;  // segmentDepth
   marshalSize = marshalSize + 4;  // pad1

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

public void setSegmentAppearance(SixByteChunk pSegmentAppearance)
{ segmentAppearance = pSegmentAppearance;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_segmentAppearance;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_segmentAppearance")
public SixByteChunk getSegmentAppearance()
{ return segmentAppearance; 
}

public void setLocation(Vector3Double pLocation)
{ location = pLocation;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_location;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_location")
public Vector3Double getLocation()
{ return location; 
}

public void setOrientation(Orientation pOrientation)
{ orientation = pOrientation;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_orientation;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_orientation")
public Orientation getOrientation()
{ return orientation; 
}

public void setSegmentLength(int pSegmentLength)
{ segmentLength = pSegmentLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getSegmentLength()
{ return segmentLength; 
}

public void setSegmentWidth(int pSegmentWidth)
{ segmentWidth = pSegmentWidth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getSegmentWidth()
{ return segmentWidth; 
}

public void setSegmentHeight(int pSegmentHeight)
{ segmentHeight = pSegmentHeight;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getSegmentHeight()
{ return segmentHeight; 
}

public void setSegmentDepth(int pSegmentDepth)
{ segmentDepth = pSegmentDepth;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getSegmentDepth()
{ return segmentDepth; 
}

public void setPad1(long pPad1)
{ pad1 = pPad1;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getPad1()
{ return pad1; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)segmentNumber);
       segmentAppearance.marshal(dos);
       location.marshal(dos);
       orientation.marshal(dos);
       dos.writeShort( (short)segmentLength);
       dos.writeShort( (short)segmentWidth);
       dos.writeShort( (short)segmentHeight);
       dos.writeShort( (short)segmentDepth);
       dos.writeInt( (int)pad1);
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
       segmentAppearance.unmarshal(dis);
       location.unmarshal(dis);
       orientation.unmarshal(dis);
       segmentLength = (int)dis.readUnsignedShort();
       segmentWidth = (int)dis.readUnsignedShort();
       segmentHeight = (int)dis.readUnsignedShort();
       segmentDepth = (int)dis.readUnsignedShort();
       pad1 = dis.readInt();
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
       segmentAppearance.marshal(buff);
       location.marshal(buff);
       orientation.marshal(buff);
       buff.putShort( (short)segmentLength);
       buff.putShort( (short)segmentWidth);
       buff.putShort( (short)segmentHeight);
       buff.putShort( (short)segmentDepth);
       buff.putInt( (int)pad1);
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
       segmentAppearance.unmarshal(buff);
       location.unmarshal(buff);
       orientation.unmarshal(buff);
       segmentLength = (int)(buff.getShort() & 0xFFFF);
       segmentWidth = (int)(buff.getShort() & 0xFFFF);
       segmentHeight = (int)(buff.getShort() & 0xFFFF);
       segmentDepth = (int)(buff.getShort() & 0xFFFF);
       pad1 = buff.getInt();
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
     if( ! (segmentAppearance.equals( rhs.segmentAppearance) )) ivarsEqual = false;
     if( ! (location.equals( rhs.location) )) ivarsEqual = false;
     if( ! (orientation.equals( rhs.orientation) )) ivarsEqual = false;
     if( ! (segmentLength == rhs.segmentLength)) ivarsEqual = false;
     if( ! (segmentWidth == rhs.segmentWidth)) ivarsEqual = false;
     if( ! (segmentHeight == rhs.segmentHeight)) ivarsEqual = false;
     if( ! (segmentDepth == rhs.segmentDepth)) ivarsEqual = false;
     if( ! (pad1 == rhs.pad1)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
