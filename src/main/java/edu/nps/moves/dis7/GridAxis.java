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
 * Grid axis record for fixed data. Section 6.2.41
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class GridAxis extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_GridAxis;

   /** coordinate of the grid origin or initial value */
   protected double  domainInitialXi;

   /** coordinate of the endpoint or final value */
   protected double  domainFinalXi;

   /** The number of grid points along the Xi domain axis for the enviornmental state data */
   protected int  domainPointsXi;

   /** interleaf factor along the domain axis. */
   protected short  interleafFactor;

   /** type of grid axis */
   protected short  axisType;

   /** Number of grid locations along Xi axis */
   protected int  numberOfPointsOnXiAxis;

   /** initial grid point for the current pdu */
   protected int  initialIndex;


/** Constructor */
 public GridAxis()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 8;  // domainInitialXi
   marshalSize = marshalSize + 8;  // domainFinalXi
   marshalSize = marshalSize + 2;  // domainPointsXi
   marshalSize = marshalSize + 1;  // interleafFactor
   marshalSize = marshalSize + 1;  // axisType
   marshalSize = marshalSize + 2;  // numberOfPointsOnXiAxis
   marshalSize = marshalSize + 2;  // initialIndex

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_GridAxis()
{
   return pk_GridAxis;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_GridAxis(long pKeyName)
{
   this.pk_GridAxis = pKeyName;
}

public void setDomainInitialXi(double pDomainInitialXi)
{ domainInitialXi = pDomainInitialXi;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public double getDomainInitialXi()
{ return domainInitialXi; 
}

public void setDomainFinalXi(double pDomainFinalXi)
{ domainFinalXi = pDomainFinalXi;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public double getDomainFinalXi()
{ return domainFinalXi; 
}

public void setDomainPointsXi(int pDomainPointsXi)
{ domainPointsXi = pDomainPointsXi;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getDomainPointsXi()
{ return domainPointsXi; 
}

public void setInterleafFactor(short pInterleafFactor)
{ interleafFactor = pInterleafFactor;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getInterleafFactor()
{ return interleafFactor; 
}

public void setAxisType(short pAxisType)
{ axisType = pAxisType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getAxisType()
{ return axisType; 
}

public void setNumberOfPointsOnXiAxis(int pNumberOfPointsOnXiAxis)
{ numberOfPointsOnXiAxis = pNumberOfPointsOnXiAxis;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getNumberOfPointsOnXiAxis()
{ return numberOfPointsOnXiAxis; 
}

public void setInitialIndex(int pInitialIndex)
{ initialIndex = pInitialIndex;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getInitialIndex()
{ return initialIndex; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeDouble( (double)domainInitialXi);
       dos.writeDouble( (double)domainFinalXi);
       dos.writeShort( (short)domainPointsXi);
       dos.writeByte( (byte)interleafFactor);
       dos.writeByte( (byte)axisType);
       dos.writeShort( (short)numberOfPointsOnXiAxis);
       dos.writeShort( (short)initialIndex);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       domainInitialXi = dis.readDouble();
       domainFinalXi = dis.readDouble();
       domainPointsXi = (int)dis.readUnsignedShort();
       interleafFactor = (short)dis.readUnsignedByte();
       axisType = (short)dis.readUnsignedByte();
       numberOfPointsOnXiAxis = (int)dis.readUnsignedShort();
       initialIndex = (int)dis.readUnsignedShort();
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
       buff.putDouble( (double)domainInitialXi);
       buff.putDouble( (double)domainFinalXi);
       buff.putShort( (short)domainPointsXi);
       buff.put( (byte)interleafFactor);
       buff.put( (byte)axisType);
       buff.putShort( (short)numberOfPointsOnXiAxis);
       buff.putShort( (short)initialIndex);
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
       domainInitialXi = buff.getDouble();
       domainFinalXi = buff.getDouble();
       domainPointsXi = (int)(buff.getShort() & 0xFFFF);
       interleafFactor = (short)(buff.get() & 0xFF);
       axisType = (short)(buff.get() & 0xFF);
       numberOfPointsOnXiAxis = (int)(buff.getShort() & 0xFFFF);
       initialIndex = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof GridAxis))
        return false;

     final GridAxis rhs = (GridAxis)obj;

     if( ! (domainInitialXi == rhs.domainInitialXi)) ivarsEqual = false;
     if( ! (domainFinalXi == rhs.domainFinalXi)) ivarsEqual = false;
     if( ! (domainPointsXi == rhs.domainPointsXi)) ivarsEqual = false;
     if( ! (interleafFactor == rhs.interleafFactor)) ivarsEqual = false;
     if( ! (axisType == rhs.axisType)) ivarsEqual = false;
     if( ! (numberOfPointsOnXiAxis == rhs.numberOfPointsOnXiAxis)) ivarsEqual = false;
     if( ! (initialIndex == rhs.initialIndex)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
