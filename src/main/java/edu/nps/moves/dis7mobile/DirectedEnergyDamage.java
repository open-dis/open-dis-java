package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Damage sustained by an entity due to directed energy. Location of the damage based on a relative x,y,z location from the center of the entity. Section 6.2.15.2
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DirectedEnergyDamage extends Object implements Serializable
{
   /** DE Record Type. */
   protected long  recordType = (long)4500;

   /** DE Record Length (bytes). */
   protected int  recordLength = (int)40;

   /** padding. */
   protected int  padding = (int)0;

   /** location of damage, relative to center of entity */
   protected Vector3Float  damageLocation = new Vector3Float(); 

   /** Size of damaged area, in meters. */
   protected float  damageDiameter;

   /** average temp of the damaged area, in degrees celsius. If firing entitty does not model this, use a value of -273.15 */
   protected float  temperature = (float)-273.15;

   /** enumeration */
   protected short  componentIdentification;

   /** enumeration */
   protected short  componentDamageStatus;

   /** enumeration */
   protected short  componentVisualDamageStatus;

   /** enumeration */
   protected short  componentVisualSmokeColor;

   /** For any component damage resulting this field shall be set to the fire event ID from that PDU. */
   protected EventIdentifier  fireEventID = new EventIdentifier(); 

   /** padding */
   protected int  padding2 = (int)0;


/** Constructor */
 public DirectedEnergyDamage()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // recordType
   marshalSize = marshalSize + 2;  // recordLength
   marshalSize = marshalSize + 2;  // padding
   marshalSize = marshalSize + damageLocation.getMarshalledSize();  // damageLocation
   marshalSize = marshalSize + 4;  // damageDiameter
   marshalSize = marshalSize + 4;  // temperature
   marshalSize = marshalSize + 1;  // componentIdentification
   marshalSize = marshalSize + 1;  // componentDamageStatus
   marshalSize = marshalSize + 1;  // componentVisualDamageStatus
   marshalSize = marshalSize + 1;  // componentVisualSmokeColor
   marshalSize = marshalSize + fireEventID.getMarshalledSize();  // fireEventID
   marshalSize = marshalSize + 2;  // padding2

   return marshalSize;
}


public void setRecordType(long pRecordType)
{ recordType = pRecordType;
}

public long getRecordType()
{ return recordType; 
}

public void setRecordLength(int pRecordLength)
{ recordLength = pRecordLength;
}

public int getRecordLength()
{ return recordLength; 
}

public void setPadding(int pPadding)
{ padding = pPadding;
}

public int getPadding()
{ return padding; 
}

public void setDamageLocation(Vector3Float pDamageLocation)
{ damageLocation = pDamageLocation;
}

public Vector3Float getDamageLocation()
{ return damageLocation; 
}

public void setDamageDiameter(float pDamageDiameter)
{ damageDiameter = pDamageDiameter;
}

public float getDamageDiameter()
{ return damageDiameter; 
}

public void setTemperature(float pTemperature)
{ temperature = pTemperature;
}

public float getTemperature()
{ return temperature; 
}

public void setComponentIdentification(short pComponentIdentification)
{ componentIdentification = pComponentIdentification;
}

public short getComponentIdentification()
{ return componentIdentification; 
}

public void setComponentDamageStatus(short pComponentDamageStatus)
{ componentDamageStatus = pComponentDamageStatus;
}

public short getComponentDamageStatus()
{ return componentDamageStatus; 
}

public void setComponentVisualDamageStatus(short pComponentVisualDamageStatus)
{ componentVisualDamageStatus = pComponentVisualDamageStatus;
}

public short getComponentVisualDamageStatus()
{ return componentVisualDamageStatus; 
}

public void setComponentVisualSmokeColor(short pComponentVisualSmokeColor)
{ componentVisualSmokeColor = pComponentVisualSmokeColor;
}

public short getComponentVisualSmokeColor()
{ return componentVisualSmokeColor; 
}

public void setFireEventID(EventIdentifier pFireEventID)
{ fireEventID = pFireEventID;
}

public EventIdentifier getFireEventID()
{ return fireEventID; 
}

public void setPadding2(int pPadding2)
{ padding2 = pPadding2;
}

public int getPadding2()
{ return padding2; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)recordType);
       dos.writeShort( (short)recordLength);
       dos.writeShort( (short)padding);
       damageLocation.marshal(dos);
       dos.writeFloat( (float)damageDiameter);
       dos.writeFloat( (float)temperature);
       dos.writeByte( (byte)componentIdentification);
       dos.writeByte( (byte)componentDamageStatus);
       dos.writeByte( (byte)componentVisualDamageStatus);
       dos.writeByte( (byte)componentVisualSmokeColor);
       fireEventID.marshal(dos);
       dos.writeShort( (short)padding2);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       recordType = dis.readInt();
       recordLength = (int)dis.readUnsignedShort();
       padding = (int)dis.readUnsignedShort();
       damageLocation.unmarshal(dis);
       damageDiameter = dis.readFloat();
       temperature = dis.readFloat();
       componentIdentification = (short)dis.readUnsignedByte();
       componentDamageStatus = (short)dis.readUnsignedByte();
       componentVisualDamageStatus = (short)dis.readUnsignedByte();
       componentVisualSmokeColor = (short)dis.readUnsignedByte();
       fireEventID.unmarshal(dis);
       padding2 = (int)dis.readUnsignedShort();
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
       buff.putInt( (int)recordType);
       buff.putShort( (short)recordLength);
       buff.putShort( (short)padding);
       damageLocation.marshal(buff);
       buff.putFloat( (float)damageDiameter);
       buff.putFloat( (float)temperature);
       buff.put( (byte)componentIdentification);
       buff.put( (byte)componentDamageStatus);
       buff.put( (byte)componentVisualDamageStatus);
       buff.put( (byte)componentVisualSmokeColor);
       fireEventID.marshal(buff);
       buff.putShort( (short)padding2);
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
       recordType = buff.getInt();
       recordLength = (int)(buff.getShort() & 0xFFFF);
       padding = (int)(buff.getShort() & 0xFFFF);
       damageLocation.unmarshal(buff);
       damageDiameter = buff.getFloat();
       temperature = buff.getFloat();
       componentIdentification = (short)(buff.get() & 0xFF);
       componentDamageStatus = (short)(buff.get() & 0xFF);
       componentVisualDamageStatus = (short)(buff.get() & 0xFF);
       componentVisualSmokeColor = (short)(buff.get() & 0xFF);
       fireEventID.unmarshal(buff);
       padding2 = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof DirectedEnergyDamage))
        return false;

     final DirectedEnergyDamage rhs = (DirectedEnergyDamage)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (recordLength == rhs.recordLength)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (damageLocation.equals( rhs.damageLocation) )) ivarsEqual = false;
     if( ! (damageDiameter == rhs.damageDiameter)) ivarsEqual = false;
     if( ! (temperature == rhs.temperature)) ivarsEqual = false;
     if( ! (componentIdentification == rhs.componentIdentification)) ivarsEqual = false;
     if( ! (componentDamageStatus == rhs.componentDamageStatus)) ivarsEqual = false;
     if( ! (componentVisualDamageStatus == rhs.componentVisualDamageStatus)) ivarsEqual = false;
     if( ! (componentVisualSmokeColor == rhs.componentVisualSmokeColor)) ivarsEqual = false;
     if( ! (fireEventID.equals( rhs.fireEventID) )) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
