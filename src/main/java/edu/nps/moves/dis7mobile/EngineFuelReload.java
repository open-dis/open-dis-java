package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * For each type or location of engine fuell, this record specifies the type, location, fuel measurement units, and reload quantity and maximum quantity. Section 6.2.25.
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class EngineFuelReload extends Object implements Serializable
{
   /** standard quantity of fuel loaded */
   protected long  standardQuantity;

   /** maximum quantity of fuel loaded */
   protected long  maximumQuantity;

   /** seconds normally required to to reload standard qty */
   protected long  standardQuantityReloadTime;

   /** seconds normally required to to reload maximum qty */
   protected long  maximumQuantityReloadTime;

   /** Units of measure */
   protected short  fuelMeasurmentUnits;

   /** fuel  location as related to the entity */
   protected short  fuelLocation;

   /** padding */
   protected short  padding = (short)0;


/** Constructor */
 public EngineFuelReload()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // standardQuantity
   marshalSize = marshalSize + 4;  // maximumQuantity
   marshalSize = marshalSize + 4;  // standardQuantityReloadTime
   marshalSize = marshalSize + 4;  // maximumQuantityReloadTime
   marshalSize = marshalSize + 1;  // fuelMeasurmentUnits
   marshalSize = marshalSize + 1;  // fuelLocation
   marshalSize = marshalSize + 1;  // padding

   return marshalSize;
}


public void setStandardQuantity(long pStandardQuantity)
{ standardQuantity = pStandardQuantity;
}

public long getStandardQuantity()
{ return standardQuantity; 
}

public void setMaximumQuantity(long pMaximumQuantity)
{ maximumQuantity = pMaximumQuantity;
}

public long getMaximumQuantity()
{ return maximumQuantity; 
}

public void setStandardQuantityReloadTime(long pStandardQuantityReloadTime)
{ standardQuantityReloadTime = pStandardQuantityReloadTime;
}

public long getStandardQuantityReloadTime()
{ return standardQuantityReloadTime; 
}

public void setMaximumQuantityReloadTime(long pMaximumQuantityReloadTime)
{ maximumQuantityReloadTime = pMaximumQuantityReloadTime;
}

public long getMaximumQuantityReloadTime()
{ return maximumQuantityReloadTime; 
}

public void setFuelMeasurmentUnits(short pFuelMeasurmentUnits)
{ fuelMeasurmentUnits = pFuelMeasurmentUnits;
}

public short getFuelMeasurmentUnits()
{ return fuelMeasurmentUnits; 
}

public void setFuelLocation(short pFuelLocation)
{ fuelLocation = pFuelLocation;
}

public short getFuelLocation()
{ return fuelLocation; 
}

public void setPadding(short pPadding)
{ padding = pPadding;
}

public short getPadding()
{ return padding; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)standardQuantity);
       dos.writeInt( (int)maximumQuantity);
       dos.writeInt( (int)standardQuantityReloadTime);
       dos.writeInt( (int)maximumQuantityReloadTime);
       dos.writeByte( (byte)fuelMeasurmentUnits);
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
       standardQuantityReloadTime = dis.readInt();
       maximumQuantityReloadTime = dis.readInt();
       fuelMeasurmentUnits = (short)dis.readUnsignedByte();
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
       buff.putInt( (int)standardQuantityReloadTime);
       buff.putInt( (int)maximumQuantityReloadTime);
       buff.put( (byte)fuelMeasurmentUnits);
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
       standardQuantityReloadTime = buff.getInt();
       maximumQuantityReloadTime = buff.getInt();
       fuelMeasurmentUnits = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof EngineFuelReload))
        return false;

     final EngineFuelReload rhs = (EngineFuelReload)obj;

     if( ! (standardQuantity == rhs.standardQuantity)) ivarsEqual = false;
     if( ! (maximumQuantity == rhs.maximumQuantity)) ivarsEqual = false;
     if( ! (standardQuantityReloadTime == rhs.standardQuantityReloadTime)) ivarsEqual = false;
     if( ! (maximumQuantityReloadTime == rhs.maximumQuantityReloadTime)) ivarsEqual = false;
     if( ! (fuelMeasurmentUnits == rhs.fuelMeasurmentUnits)) ivarsEqual = false;
     if( ! (fuelLocation == rhs.fuelLocation)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
