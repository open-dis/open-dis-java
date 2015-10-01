package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Sectioin 5.3.4.1. Information about someone firing something. COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class FirePdu extends WarfareFamilyPdu implements Serializable
{
   /** ID of the munition that is being shot */
   protected EntityID  munitionID = new EntityID(); 

   /** ID of event */
   protected EventID  eventID = new EventID(); 

   protected int  fireMissionIndex;

   /** location of the firing event */
   protected Vector3Double  locationInWorldCoordinates = new Vector3Double(); 

   /** Describes munitions used in the firing event */
   protected BurstDescriptor  burstDescriptor = new BurstDescriptor(); 

   /** Velocity of the ammunition */
   protected Vector3Float  velocity = new Vector3Float(); 

   /** range to the target. Note the word range is a SQL reserved word. */
   protected float  rangeToTarget;


/** Constructor */
 public FirePdu()
 {
    setPduType( (short)2 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + munitionID.getMarshalledSize();  // munitionID
   marshalSize = marshalSize + eventID.getMarshalledSize();  // eventID
   marshalSize = marshalSize + 4;  // fireMissionIndex
   marshalSize = marshalSize + locationInWorldCoordinates.getMarshalledSize();  // locationInWorldCoordinates
   marshalSize = marshalSize + burstDescriptor.getMarshalledSize();  // burstDescriptor
   marshalSize = marshalSize + velocity.getMarshalledSize();  // velocity
   marshalSize = marshalSize + 4;  // rangeToTarget

   return marshalSize;
}


public void setMunitionID(EntityID pMunitionID)
{ munitionID = pMunitionID;
}

public EntityID getMunitionID()
{ return munitionID; 
}

public void setEventID(EventID pEventID)
{ eventID = pEventID;
}

public EventID getEventID()
{ return eventID; 
}

public void setFireMissionIndex(int pFireMissionIndex)
{ fireMissionIndex = pFireMissionIndex;
}

public int getFireMissionIndex()
{ return fireMissionIndex; 
}

public void setLocationInWorldCoordinates(Vector3Double pLocationInWorldCoordinates)
{ locationInWorldCoordinates = pLocationInWorldCoordinates;
}

public Vector3Double getLocationInWorldCoordinates()
{ return locationInWorldCoordinates; 
}

public void setBurstDescriptor(BurstDescriptor pBurstDescriptor)
{ burstDescriptor = pBurstDescriptor;
}

public BurstDescriptor getBurstDescriptor()
{ return burstDescriptor; 
}

public void setVelocity(Vector3Float pVelocity)
{ velocity = pVelocity;
}

public Vector3Float getVelocity()
{ return velocity; 
}

public void setRangeToTarget(float pRangeToTarget)
{ rangeToTarget = pRangeToTarget;
}

public float getRangeToTarget()
{ return rangeToTarget; 
}


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       munitionID.marshal(dos);
       eventID.marshal(dos);
       dos.writeInt( (int)fireMissionIndex);
       locationInWorldCoordinates.marshal(dos);
       burstDescriptor.marshal(dos);
       velocity.marshal(dos);
       dos.writeFloat( (float)rangeToTarget);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
     super.unmarshal(dis);

    try 
    {
       munitionID.unmarshal(dis);
       eventID.unmarshal(dis);
       fireMissionIndex = dis.readInt();
       locationInWorldCoordinates.unmarshal(dis);
       burstDescriptor.unmarshal(dis);
       velocity.unmarshal(dis);
       rangeToTarget = dis.readFloat();
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
       super.marshal(buff);
       munitionID.marshal(buff);
       eventID.marshal(buff);
       buff.putInt( (int)fireMissionIndex);
       locationInWorldCoordinates.marshal(buff);
       burstDescriptor.marshal(buff);
       velocity.marshal(buff);
       buff.putFloat( (float)rangeToTarget);
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
       super.unmarshal(buff);

       munitionID.unmarshal(buff);
       eventID.unmarshal(buff);
       fireMissionIndex = buff.getInt();
       locationInWorldCoordinates.unmarshal(buff);
       burstDescriptor.unmarshal(buff);
       velocity.unmarshal(buff);
       rangeToTarget = buff.getFloat();
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

@Override
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof FirePdu))
        return false;

     final FirePdu rhs = (FirePdu)obj;

     if( ! (munitionID.equals( rhs.munitionID) )) ivarsEqual = false;
     if( ! (eventID.equals( rhs.eventID) )) ivarsEqual = false;
     if( ! (fireMissionIndex == rhs.fireMissionIndex)) ivarsEqual = false;
     if( ! (locationInWorldCoordinates.equals( rhs.locationInWorldCoordinates) )) ivarsEqual = false;
     if( ! (burstDescriptor.equals( rhs.burstDescriptor) )) ivarsEqual = false;
     if( ! (velocity.equals( rhs.velocity) )) ivarsEqual = false;
     if( ! (rangeToTarget == rhs.rangeToTarget)) ivarsEqual = false;

    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
