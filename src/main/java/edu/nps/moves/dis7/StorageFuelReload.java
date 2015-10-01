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
 * For each type or location of Storage Fuel, this record shall specify the type, location, fuel measure- ment units, reload quantity and maximum quantity for storage fuel either for the whole entity or a specific storage fuel location (tank). Section 6.2.85.
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class StorageFuelReload extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_StorageFuelReload;

   /**  the standard quantity of this fuel type normally loaded at this station/launcher if a station/launcher is specified. If the Station/Launcher field is set to zero, then this is the total quantity of this fuel type that would be present in a standard reload of all appli- cable stations/launchers associated with this entity. */
   protected long  standardQuantity;

   /** the maximum quantity of this fuel type that this sta- tion/launcher is capable of holding when a station/launcher is specified. This would be the value used when a maximum reload was desired to be set for this station/launcher. If the Station/launcher field is set to zero, then this is the maximum quantity of this fuel type that would be present on this entity at all stations/launchers that can accept this fuel type. */
   protected long  maximumQuantity;

   /** the seconds normally required to reload the standard quantity of this fuel type at this specific station/launcher. When the Station/Launcher field is set to zero, this shall be the time it takes to perform a standard quantity reload of this fuel type at all applicable stations/launchers for this entity. */
   protected short  standardQuantityReloadTime;

   /** the seconds normally required to reload the maximum possible quantity of this fuel type at this station/launcher. When the Station/Launcher field is set to zero, this shall be the time it takes to perform a maximum quantity load/reload of this fuel type at all applicable stations/launchers for this entity. */
   protected short  maximumQuantityReloadTime;

   /** the fuel measurement units. Enumeration */
   protected short  fuelMeasurementUnits;

   /** Fuel type. Enumeration */
   protected short  fuelType;

   /** Location of fuel as related to entity. See section 14 of EBV document */
   protected short  fuelLocation;

   /** padding */
   protected short  padding = (short)0;


/** Constructor */
 public StorageFuelReload()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // standardQuantity
   marshalSize = marshalSize + 4;  // maximumQuantity
   marshalSize = marshalSize + 1;  // standardQuantityReloadTime
   marshalSize = marshalSize + 1;  // maximumQuantityReloadTime
   marshalSize = marshalSize + 1;  // fuelMeasurementUnits
   marshalSize = marshalSize + 1;  // fuelType
   marshalSize = marshalSize + 1;  // fuelLocation
   marshalSize = marshalSize + 1;  // padding

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_StorageFuelReload()
{
   return pk_StorageFuelReload;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_StorageFuelReload(long pKeyName)
{
   this.pk_StorageFuelReload = pKeyName;
}

public void setStandardQuantity(long pStandardQuantity)
{ standardQuantity = pStandardQuantity;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getStandardQuantity()
{ return standardQuantity; 
}

public void setMaximumQuantity(long pMaximumQuantity)
{ maximumQuantity = pMaximumQuantity;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getMaximumQuantity()
{ return maximumQuantity; 
}

public void setStandardQuantityReloadTime(short pStandardQuantityReloadTime)
{ standardQuantityReloadTime = pStandardQuantityReloadTime;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getStandardQuantityReloadTime()
{ return standardQuantityReloadTime; 
}

public void setMaximumQuantityReloadTime(short pMaximumQuantityReloadTime)
{ maximumQuantityReloadTime = pMaximumQuantityReloadTime;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getMaximumQuantityReloadTime()
{ return maximumQuantityReloadTime; 
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
       dos.writeInt( (int)standardQuantity);
       dos.writeInt( (int)maximumQuantity);
       dos.writeByte( (byte)standardQuantityReloadTime);
       dos.writeByte( (byte)maximumQuantityReloadTime);
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
       standardQuantity = dis.readInt();
       maximumQuantity = dis.readInt();
       standardQuantityReloadTime = (short)dis.readUnsignedByte();
       maximumQuantityReloadTime = (short)dis.readUnsignedByte();
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
       buff.putInt( (int)standardQuantity);
       buff.putInt( (int)maximumQuantity);
       buff.put( (byte)standardQuantityReloadTime);
       buff.put( (byte)maximumQuantityReloadTime);
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
       standardQuantity = buff.getInt();
       maximumQuantity = buff.getInt();
       standardQuantityReloadTime = (short)(buff.get() & 0xFF);
       maximumQuantityReloadTime = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof StorageFuelReload))
        return false;

     final StorageFuelReload rhs = (StorageFuelReload)obj;

     if( ! (standardQuantity == rhs.standardQuantity)) ivarsEqual = false;
     if( ! (maximumQuantity == rhs.maximumQuantity)) ivarsEqual = false;
     if( ! (standardQuantityReloadTime == rhs.standardQuantityReloadTime)) ivarsEqual = false;
     if( ! (maximumQuantityReloadTime == rhs.maximumQuantityReloadTime)) ivarsEqual = false;
     if( ! (fuelMeasurementUnits == rhs.fuelMeasurementUnits)) ivarsEqual = false;
     if( ! (fuelType == rhs.fuelType)) ivarsEqual = false;
     if( ! (fuelLocation == rhs.fuelLocation)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
