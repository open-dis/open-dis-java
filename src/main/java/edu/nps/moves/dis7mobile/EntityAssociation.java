package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Association or disassociation of two entities.  Section 6.2.94.4.3
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class EntityAssociation extends Object implements Serializable
{
   /** the identification of the Variable Parameter record. Enumeration from EBV */
   protected short  recordType = (short)4;

   /** Indicates if this VP has changed since last issuance */
   protected short  changeIndicator = (short)0;

   /** Indicates association status between two entities; 8 bit enum */
   protected short  associationStatus = (short)0;

   /** Type of association; 8 bit enum */
   protected short  associationType = (short)0;

   /** Object ID of entity associated with this entity */
   protected EntityID  entityID = new EntityID(); 

   /** Station location on one's own entity. EBV doc. */
   protected int  ownStationLocation;

   /** Type of physical connection. EBV doc */
   protected short  physicalConnectionType;

   /** Type of member the entity is within th egroup */
   protected short  groupMemberType;

   /** Group if any to which the entity belongs */
   protected int  groupNumber;


/** Constructor */
 public EntityAssociation()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // recordType
   marshalSize = marshalSize + 1;  // changeIndicator
   marshalSize = marshalSize + 1;  // associationStatus
   marshalSize = marshalSize + 1;  // associationType
   marshalSize = marshalSize + entityID.getMarshalledSize();  // entityID
   marshalSize = marshalSize + 2;  // ownStationLocation
   marshalSize = marshalSize + 1;  // physicalConnectionType
   marshalSize = marshalSize + 1;  // groupMemberType
   marshalSize = marshalSize + 2;  // groupNumber

   return marshalSize;
}


public void setRecordType(short pRecordType)
{ recordType = pRecordType;
}

public short getRecordType()
{ return recordType; 
}

public void setChangeIndicator(short pChangeIndicator)
{ changeIndicator = pChangeIndicator;
}

public short getChangeIndicator()
{ return changeIndicator; 
}

public void setAssociationStatus(short pAssociationStatus)
{ associationStatus = pAssociationStatus;
}

public short getAssociationStatus()
{ return associationStatus; 
}

public void setAssociationType(short pAssociationType)
{ associationType = pAssociationType;
}

public short getAssociationType()
{ return associationType; 
}

public void setEntityID(EntityID pEntityID)
{ entityID = pEntityID;
}

public EntityID getEntityID()
{ return entityID; 
}

public void setOwnStationLocation(int pOwnStationLocation)
{ ownStationLocation = pOwnStationLocation;
}

public int getOwnStationLocation()
{ return ownStationLocation; 
}

public void setPhysicalConnectionType(short pPhysicalConnectionType)
{ physicalConnectionType = pPhysicalConnectionType;
}

public short getPhysicalConnectionType()
{ return physicalConnectionType; 
}

public void setGroupMemberType(short pGroupMemberType)
{ groupMemberType = pGroupMemberType;
}

public short getGroupMemberType()
{ return groupMemberType; 
}

public void setGroupNumber(int pGroupNumber)
{ groupNumber = pGroupNumber;
}

public int getGroupNumber()
{ return groupNumber; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)recordType);
       dos.writeByte( (byte)changeIndicator);
       dos.writeByte( (byte)associationStatus);
       dos.writeByte( (byte)associationType);
       entityID.marshal(dos);
       dos.writeShort( (short)ownStationLocation);
       dos.writeByte( (byte)physicalConnectionType);
       dos.writeByte( (byte)groupMemberType);
       dos.writeShort( (short)groupNumber);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       recordType = (short)dis.readUnsignedByte();
       changeIndicator = (short)dis.readUnsignedByte();
       associationStatus = (short)dis.readUnsignedByte();
       associationType = (short)dis.readUnsignedByte();
       entityID.unmarshal(dis);
       ownStationLocation = (int)dis.readUnsignedShort();
       physicalConnectionType = (short)dis.readUnsignedByte();
       groupMemberType = (short)dis.readUnsignedByte();
       groupNumber = (int)dis.readUnsignedShort();
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
       buff.put( (byte)recordType);
       buff.put( (byte)changeIndicator);
       buff.put( (byte)associationStatus);
       buff.put( (byte)associationType);
       entityID.marshal(buff);
       buff.putShort( (short)ownStationLocation);
       buff.put( (byte)physicalConnectionType);
       buff.put( (byte)groupMemberType);
       buff.putShort( (short)groupNumber);
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
       recordType = (short)(buff.get() & 0xFF);
       changeIndicator = (short)(buff.get() & 0xFF);
       associationStatus = (short)(buff.get() & 0xFF);
       associationType = (short)(buff.get() & 0xFF);
       entityID.unmarshal(buff);
       ownStationLocation = (int)(buff.getShort() & 0xFFFF);
       physicalConnectionType = (short)(buff.get() & 0xFF);
       groupMemberType = (short)(buff.get() & 0xFF);
       groupNumber = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof EntityAssociation))
        return false;

     final EntityAssociation rhs = (EntityAssociation)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (changeIndicator == rhs.changeIndicator)) ivarsEqual = false;
     if( ! (associationStatus == rhs.associationStatus)) ivarsEqual = false;
     if( ! (associationType == rhs.associationType)) ivarsEqual = false;
     if( ! (entityID.equals( rhs.entityID) )) ivarsEqual = false;
     if( ! (ownStationLocation == rhs.ownStationLocation)) ivarsEqual = false;
     if( ! (physicalConnectionType == rhs.physicalConnectionType)) ivarsEqual = false;
     if( ! (groupMemberType == rhs.groupMemberType)) ivarsEqual = false;
     if( ! (groupNumber == rhs.groupNumber)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
