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
 * Information about an entity's engine fuel. Section 6.2.84.
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class StorageFuel extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_StorageFuel;

   /** Fuel quantity, units specified by next field */
   protected long  fuelQuantity;

   /** Units in which the fuel is measured */
   protected short  fuelMeasurementUnits;

   /** Type of fuel */
   protected short  fuelType;

   /** Location of fuel as related to entity. See section 14 of EBV document */
   protected short  fuelLocation;

   /** padding */
   protected short  padding = (short)0;


/** Constructor */
 public StorageFuel()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // fuelQuantity
   marshalSize = marshalSize + 1;  // fuelMeasurementUnits
   marshalSize = marshalSize + 1;  // fuelType
   marshalSize = marshalSize + 1;  // fuelLocation
   marshalSize = marshalSize + 1;  // padding

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_StorageFuel()
{
   return pk_StorageFuel;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_StorageFuel(long pKeyName)
{
   this.pk_StorageFuel = pKeyName;
}

public void setFuelQuantity(long pFuelQuantity)
{ fuelQuantity = pFuelQuantity;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getFuelQuantity()
{ return fuelQuantity; 
}

public void setFuelMeasurementUnits(short pFuelMeasurementUnits)
{ fuelMeasurementUnits = pFuelMeasurementUnits;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getFuelMeasurementUnits()
{ return fuelMeasurementUnits; 
}

public void setFuelType(short pFuelType)
{ fuelType = pFuelType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getFuelType()
{ return fuelType; 
}

public void setFuelLocation(short pFuelLocation)
{ fuelLocation = pFuelLocation;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getFuelLocation()
{ return fuelLocation; 
}

public void setPadding(short pPadding)
{ padding = pPadding;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPadding()
{ return padding; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)fuelQuantity);
       dos.writeByte( (byte)fuelMeasurementUnits);
       dos.writeByte( (byte)fuelType);
       dos.writeByte( (byte)fuelLocation);
       dos.writeByte( (byte)padding);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       fuelQuantity = dis.readInt();
       fuelMeasurementUnits = (short)dis.readUnsignedByte();
       fuelType = (short)dis.readUnsignedByte();
       fuelLocation = (short)dis.readUnsignedByte();
       padding = (short)dis.readUnsignedByte();
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
       buff.putInt( (int)fuelQuantity);
       buff.put( (byte)fuelMeasurementUnits);
       buff.put( (byte)fuelType);
       buff.put( (byte)fuelLocation);
       buff.put( (byte)padding);
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
       fuelQuantity = buff.getInt();
       fuelMeasurementUnits = (short)(buff.get() & 0xFF);
       fuelType = (short)(buff.get() & 0xFF);
       fuelLocation = (short)(buff.get() & 0xFF);
       padding = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof StorageFuel))
        return false;

     final StorageFuel rhs = (StorageFuel)obj;

     if( ! (fuelQuantity == rhs.fuelQuantity)) ivarsEqual = false;
     if( ! (fuelMeasurementUnits == rhs.fuelMeasurementUnits)) ivarsEqual = false;
     if( ! (fuelType == rhs.fuelType)) ivarsEqual = false;
     if( ! (fuelLocation == rhs.fuelLocation)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
