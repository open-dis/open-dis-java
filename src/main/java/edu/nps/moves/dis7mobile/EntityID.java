package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * more laconically named EntityIdentifier
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class EntityID extends Object implements Serializable
{
   /** Site ID */
   protected int  siteID;

   /** application number ID */
   protected int  applicationID;

   /** Entity number ID */
   protected int  entityID;


/** Constructor */
 public EntityID()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 2;  // siteID
   marshalSize = marshalSize + 2;  // applicationID
   marshalSize = marshalSize + 2;  // entityID

   return marshalSize;
}


public void setSiteID(int pSiteID)
{ siteID = pSiteID;
}

public int getSiteID()
{ return siteID; 
}

public void setApplicationID(int pApplicationID)
{ applicationID = pApplicationID;
}

public int getApplicationID()
{ return applicationID; 
}

public void setEntityID(int pEntityID)
{ entityID = pEntityID;
}

public int getEntityID()
{ return entityID; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeShort( (short)siteID);
       dos.writeShort( (short)applicationID);
       dos.writeShort( (short)entityID);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       siteID = (int)dis.readUnsignedShort();
       applicationID = (int)dis.readUnsignedShort();
       entityID = (int)dis.readUnsignedShort();
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
       buff.putShort( (short)siteID);
       buff.putShort( (short)applicationID);
       buff.putShort( (short)entityID);
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
       siteID = (int)(buff.getShort() & 0xFFFF);
       applicationID = (int)(buff.getShort() & 0xFFFF);
       entityID = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof EntityID))
        return false;

     final EntityID rhs = (EntityID)obj;

     if( ! (siteID == rhs.siteID)) ivarsEqual = false;
     if( ! (applicationID == rhs.applicationID)) ivarsEqual = false;
     if( ! (entityID == rhs.entityID)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
