package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.3.4.2. Information about stuff exploding. COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DetonationPdu extends WarfareFamilyPdu implements Serializable
{
   /** ID of muntion that was fired */
   protected EntityID  munitionID = new EntityID(); 

   /** ID firing event */
   protected EventID  eventID = new EventID(); 

   /** ID firing event */
   protected Vector3Float  velocity = new Vector3Float(); 

   /** where the detonation is, in world coordinates */
   protected Vector3Double  locationInWorldCoordinates = new Vector3Double(); 

   /** Describes munition used */
   protected BurstDescriptor  burstDescriptor = new BurstDescriptor(); 

   /** location of the detonation or impact in the target entity's coordinate system. This information should be used for damage assessment. */
   protected Vector3Float  locationInEntityCoordinates = new Vector3Float(); 

   /** result of the explosion */
   protected short  detonationResult;

   /** How many articulation parameters we have */
   protected short  numberOfArticulationParameters;

   /** padding */
   protected short  pad = (short)0;

   protected List< ArticulationParameter > articulationParameters = new ArrayList< ArticulationParameter >(); 

/** Constructor */
 public DetonationPdu()
 {
    setPduType( (short)3 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + munitionID.getMarshalledSize();  // munitionID
   marshalSize = marshalSize + eventID.getMarshalledSize();  // eventID
   marshalSize = marshalSize + velocity.getMarshalledSize();  // velocity
   marshalSize = marshalSize + locationInWorldCoordinates.getMarshalledSize();  // locationInWorldCoordinates
   marshalSize = marshalSize + burstDescriptor.getMarshalledSize();  // burstDescriptor
   marshalSize = marshalSize + locationInEntityCoordinates.getMarshalledSize();  // locationInEntityCoordinates
   marshalSize = marshalSize + 1;  // detonationResult
   marshalSize = marshalSize + 1;  // numberOfArticulationParameters
   marshalSize = marshalSize + 2;  // pad
   for(int idx=0; idx < articulationParameters.size(); idx++)
   {
        ArticulationParameter listElement = articulationParameters.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

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

public void setVelocity(Vector3Float pVelocity)
{ velocity = pVelocity;
}

public Vector3Float getVelocity()
{ return velocity; 
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

public void setLocationInEntityCoordinates(Vector3Float pLocationInEntityCoordinates)
{ locationInEntityCoordinates = pLocationInEntityCoordinates;
}

public Vector3Float getLocationInEntityCoordinates()
{ return locationInEntityCoordinates; 
}

public void setDetonationResult(short pDetonationResult)
{ detonationResult = pDetonationResult;
}

public short getDetonationResult()
{ return detonationResult; 
}

public short getNumberOfArticulationParameters()
{ return (short)articulationParameters.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfArticulationParameters method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfArticulationParameters(short pNumberOfArticulationParameters)
{ numberOfArticulationParameters = pNumberOfArticulationParameters;
}

public void setPad(short pPad)
{ pad = pPad;
}

public short getPad()
{ return pad; 
}

public void setArticulationParameters(List<ArticulationParameter> pArticulationParameters)
{ articulationParameters = pArticulationParameters;
}

public List<ArticulationParameter> getArticulationParameters()
{ return articulationParameters; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       munitionID.marshal(dos);
       eventID.marshal(dos);
       velocity.marshal(dos);
       locationInWorldCoordinates.marshal(dos);
       burstDescriptor.marshal(dos);
       locationInEntityCoordinates.marshal(dos);
       dos.writeByte( (byte)detonationResult);
       dos.writeByte( (byte)articulationParameters.size());
       dos.writeShort( (short)pad);

       for(int idx = 0; idx < articulationParameters.size(); idx++)
       {
            ArticulationParameter aArticulationParameter = articulationParameters.get(idx);
            aArticulationParameter.marshal(dos);
       } // end of list marshalling

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
       velocity.unmarshal(dis);
       locationInWorldCoordinates.unmarshal(dis);
       burstDescriptor.unmarshal(dis);
       locationInEntityCoordinates.unmarshal(dis);
       detonationResult = (short)dis.readUnsignedByte();
       numberOfArticulationParameters = (short)dis.readUnsignedByte();
       pad = dis.readShort();
       for(int idx = 0; idx < numberOfArticulationParameters; idx++)
       {
           ArticulationParameter anX = new ArticulationParameter();
           anX.unmarshal(dis);
           articulationParameters.add(anX);
       }

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
       velocity.marshal(buff);
       locationInWorldCoordinates.marshal(buff);
       burstDescriptor.marshal(buff);
       locationInEntityCoordinates.marshal(buff);
       buff.put( (byte)detonationResult);
       buff.put( (byte)articulationParameters.size());
       buff.putShort( (short)pad);

       for(int idx = 0; idx < articulationParameters.size(); idx++)
       {
            ArticulationParameter aArticulationParameter = (ArticulationParameter)articulationParameters.get(idx);
            aArticulationParameter.marshal(buff);
       } // end of list marshalling

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
       velocity.unmarshal(buff);
       locationInWorldCoordinates.unmarshal(buff);
       burstDescriptor.unmarshal(buff);
       locationInEntityCoordinates.unmarshal(buff);
       detonationResult = (short)(buff.get() & 0xFF);
       numberOfArticulationParameters = (short)(buff.get() & 0xFF);
       pad = buff.getShort();
       for(int idx = 0; idx < numberOfArticulationParameters; idx++)
       {
            ArticulationParameter anX = new ArticulationParameter();
            anX.unmarshal(buff);
            articulationParameters.add(anX);
       }

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

    if(!(obj instanceof DetonationPdu))
        return false;

     final DetonationPdu rhs = (DetonationPdu)obj;

     if( ! (munitionID.equals( rhs.munitionID) )) ivarsEqual = false;
     if( ! (eventID.equals( rhs.eventID) )) ivarsEqual = false;
     if( ! (velocity.equals( rhs.velocity) )) ivarsEqual = false;
     if( ! (locationInWorldCoordinates.equals( rhs.locationInWorldCoordinates) )) ivarsEqual = false;
     if( ! (burstDescriptor.equals( rhs.burstDescriptor) )) ivarsEqual = false;
     if( ! (locationInEntityCoordinates.equals( rhs.locationInEntityCoordinates) )) ivarsEqual = false;
     if( ! (detonationResult == rhs.detonationResult)) ivarsEqual = false;
     if( ! (numberOfArticulationParameters == rhs.numberOfArticulationParameters)) ivarsEqual = false;
     if( ! (pad == rhs.pad)) ivarsEqual = false;

     for(int idx = 0; idx < articulationParameters.size(); idx++)
     {
        if( ! ( articulationParameters.get(idx).equals(rhs.articulationParameters.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
