package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 *  Track-Jam data Section 6.2.89
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class TrackJamData extends Object implements Serializable
{
   /** the entity tracked or illumated, or an emitter beam targeted with jamming */
   protected EntityID  entityID = new EntityID(); 

   /** Emitter system associated with the entity */
   protected short  emitterNumber;

   /** Beam associated with the entity */
   protected short  beamNumber;


/** Constructor */
 public TrackJamData()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + entityID.getMarshalledSize();  // entityID
   marshalSize = marshalSize + 1;  // emitterNumber
   marshalSize = marshalSize + 1;  // beamNumber

   return marshalSize;
}


public void setEntityID(EntityID pEntityID)
{ entityID = pEntityID;
}

public EntityID getEntityID()
{ return entityID; 
}

public void setEmitterNumber(short pEmitterNumber)
{ emitterNumber = pEmitterNumber;
}

public short getEmitterNumber()
{ return emitterNumber; 
}

public void setBeamNumber(short pBeamNumber)
{ beamNumber = pBeamNumber;
}

public short getBeamNumber()
{ return beamNumber; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       entityID.marshal(dos);
       dos.writeByte( (byte)emitterNumber);
       dos.writeByte( (byte)beamNumber);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       entityID.unmarshal(dis);
       emitterNumber = (short)dis.readUnsignedByte();
       beamNumber = (short)dis.readUnsignedByte();
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
       entityID.marshal(buff);
       buff.put( (byte)emitterNumber);
       buff.put( (byte)beamNumber);
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
       entityID.unmarshal(buff);
       emitterNumber = (short)(buff.get() & 0xFF);
       beamNumber = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof TrackJamData))
        return false;

     final TrackJamData rhs = (TrackJamData)obj;

     if( ! (entityID.equals( rhs.entityID) )) ivarsEqual = false;
     if( ! (emitterNumber == rhs.emitterNumber)) ivarsEqual = false;
     if( ! (beamNumber == rhs.beamNumber)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
