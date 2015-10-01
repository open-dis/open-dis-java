package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Represents the postion and state of one entity in the world. Section 7.2.2. COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class EntityStatePdu extends EntityInformationFamilyPdu implements Serializable
{
   /** Unique ID for an entity that is tied to this state information */
   protected EntityID  entityID = new EntityID(); 

   /** What force this entity is affiliated with, eg red, blue, neutral, etc */
   protected short  forceId;

   /** How many variable parameters are in the variable length list. In earlier versions of DIS these were known as articulation parameters */
   protected short  numberOfVariableParameters;

   /** Describes the type of entity in the world */
   protected EntityType  entityType = new EntityType(); 

   protected EntityType  alternativeEntityType = new EntityType(); 

   /** Describes the speed of the entity in the world */
   protected Vector3Float  entityLinearVelocity = new Vector3Float(); 

   /** describes the location of the entity in the world */
   protected Vector3Double  entityLocation = new Vector3Double(); 

   /** describes the orientation of the entity, in euler angles */
   protected EulerAngles  entityOrientation = new EulerAngles(); 

   /** a series of bit flags that are used to help draw the entity, such as smoking, on fire, etc. */
   protected long  entityAppearance;

   /** parameters used for dead reckoning */
   protected DeadReckoningParameters  deadReckoningParameters = new DeadReckoningParameters(); 

   /** characters that can be used for debugging, or to draw unique strings on the side of entities in the world */
   protected EntityMarking  marking = new EntityMarking(); 

   /** a series of bit flags */
   protected long  capabilities;

   /** variable length list of variable parameters. In earlier DIS versions this was articulation parameters. */
   protected List< VariableParameter > variableParameters = new ArrayList< VariableParameter >(); 

/** Constructor */
 public EntityStatePdu()
 {
    setPduType( (short)1 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + entityID.getMarshalledSize();  // entityID
   marshalSize = marshalSize + 1;  // forceId
   marshalSize = marshalSize + 1;  // numberOfVariableParameters
   marshalSize = marshalSize + entityType.getMarshalledSize();  // entityType
   marshalSize = marshalSize + alternativeEntityType.getMarshalledSize();  // alternativeEntityType
   marshalSize = marshalSize + entityLinearVelocity.getMarshalledSize();  // entityLinearVelocity
   marshalSize = marshalSize + entityLocation.getMarshalledSize();  // entityLocation
   marshalSize = marshalSize + entityOrientation.getMarshalledSize();  // entityOrientation
   marshalSize = marshalSize + 4;  // entityAppearance
   marshalSize = marshalSize + deadReckoningParameters.getMarshalledSize();  // deadReckoningParameters
   marshalSize = marshalSize + marking.getMarshalledSize();  // marking
   marshalSize = marshalSize + 4;  // capabilities
   for(int idx=0; idx < variableParameters.size(); idx++)
   {
        VariableParameter listElement = variableParameters.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setEntityID(EntityID pEntityID)
{ entityID = pEntityID;
}

public EntityID getEntityID()
{ return entityID; 
}

public void setForceId(short pForceId)
{ forceId = pForceId;
}

public short getForceId()
{ return forceId; 
}

public short getNumberOfVariableParameters()
{ return (short)variableParameters.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfVariableParameters method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfVariableParameters(short pNumberOfVariableParameters)
{ numberOfVariableParameters = pNumberOfVariableParameters;
}

public void setEntityType(EntityType pEntityType)
{ entityType = pEntityType;
}

public EntityType getEntityType()
{ return entityType; 
}

public void setAlternativeEntityType(EntityType pAlternativeEntityType)
{ alternativeEntityType = pAlternativeEntityType;
}

public EntityType getAlternativeEntityType()
{ return alternativeEntityType; 
}

public void setEntityLinearVelocity(Vector3Float pEntityLinearVelocity)
{ entityLinearVelocity = pEntityLinearVelocity;
}

public Vector3Float getEntityLinearVelocity()
{ return entityLinearVelocity; 
}

public void setEntityLocation(Vector3Double pEntityLocation)
{ entityLocation = pEntityLocation;
}

public Vector3Double getEntityLocation()
{ return entityLocation; 
}

public void setEntityOrientation(EulerAngles pEntityOrientation)
{ entityOrientation = pEntityOrientation;
}

public EulerAngles getEntityOrientation()
{ return entityOrientation; 
}

public void setEntityAppearance(long pEntityAppearance)
{ entityAppearance = pEntityAppearance;
}

public long getEntityAppearance()
{ return entityAppearance; 
}

public void setDeadReckoningParameters(DeadReckoningParameters pDeadReckoningParameters)
{ deadReckoningParameters = pDeadReckoningParameters;
}

public DeadReckoningParameters getDeadReckoningParameters()
{ return deadReckoningParameters; 
}

public void setMarking(EntityMarking pMarking)
{ marking = pMarking;
}

public EntityMarking getMarking()
{ return marking; 
}

public void setCapabilities(long pCapabilities)
{ capabilities = pCapabilities;
}

public long getCapabilities()
{ return capabilities; 
}

public void setVariableParameters(List<VariableParameter> pVariableParameters)
{ variableParameters = pVariableParameters;
}

public List<VariableParameter> getVariableParameters()
{ return variableParameters; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       entityID.marshal(dos);
       dos.writeByte( (byte)forceId);
       dos.writeByte( (byte)variableParameters.size());
       entityType.marshal(dos);
       alternativeEntityType.marshal(dos);
       entityLinearVelocity.marshal(dos);
       entityLocation.marshal(dos);
       entityOrientation.marshal(dos);
       dos.writeInt( (int)entityAppearance);
       deadReckoningParameters.marshal(dos);
       marking.marshal(dos);
       dos.writeInt( (int)capabilities);

       for(int idx = 0; idx < variableParameters.size(); idx++)
       {
            VariableParameter aVariableParameter = variableParameters.get(idx);
            aVariableParameter.marshal(dos);
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
       entityID.unmarshal(dis);
       forceId = (short)dis.readUnsignedByte();
       numberOfVariableParameters = (short)dis.readUnsignedByte();
       entityType.unmarshal(dis);
       alternativeEntityType.unmarshal(dis);
       entityLinearVelocity.unmarshal(dis);
       entityLocation.unmarshal(dis);
       entityOrientation.unmarshal(dis);
       entityAppearance = dis.readInt();
       deadReckoningParameters.unmarshal(dis);
       marking.unmarshal(dis);
       capabilities = dis.readInt();
       for(int idx = 0; idx < numberOfVariableParameters; idx++)
       {
           VariableParameter anX = new VariableParameter();
           anX.unmarshal(dis);
           variableParameters.add(anX);
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
       entityID.marshal(buff);
       buff.put( (byte)forceId);
       buff.put( (byte)variableParameters.size());
       entityType.marshal(buff);
       alternativeEntityType.marshal(buff);
       entityLinearVelocity.marshal(buff);
       entityLocation.marshal(buff);
       entityOrientation.marshal(buff);
       buff.putInt( (int)entityAppearance);
       deadReckoningParameters.marshal(buff);
       marking.marshal(buff);
       buff.putInt( (int)capabilities);

       for(int idx = 0; idx < variableParameters.size(); idx++)
       {
            VariableParameter aVariableParameter = (VariableParameter)variableParameters.get(idx);
            aVariableParameter.marshal(buff);
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

       entityID.unmarshal(buff);
       forceId = (short)(buff.get() & 0xFF);
       numberOfVariableParameters = (short)(buff.get() & 0xFF);
       entityType.unmarshal(buff);
       alternativeEntityType.unmarshal(buff);
       entityLinearVelocity.unmarshal(buff);
       entityLocation.unmarshal(buff);
       entityOrientation.unmarshal(buff);
       entityAppearance = buff.getInt();
       deadReckoningParameters.unmarshal(buff);
       marking.unmarshal(buff);
       capabilities = buff.getInt();
       for(int idx = 0; idx < numberOfVariableParameters; idx++)
       {
            VariableParameter anX = new VariableParameter();
            anX.unmarshal(buff);
            variableParameters.add(anX);
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

    if(!(obj instanceof EntityStatePdu))
        return false;

     final EntityStatePdu rhs = (EntityStatePdu)obj;

     if( ! (entityID.equals( rhs.entityID) )) ivarsEqual = false;
     if( ! (forceId == rhs.forceId)) ivarsEqual = false;
     if( ! (numberOfVariableParameters == rhs.numberOfVariableParameters)) ivarsEqual = false;
     if( ! (entityType.equals( rhs.entityType) )) ivarsEqual = false;
     if( ! (alternativeEntityType.equals( rhs.alternativeEntityType) )) ivarsEqual = false;
     if( ! (entityLinearVelocity.equals( rhs.entityLinearVelocity) )) ivarsEqual = false;
     if( ! (entityLocation.equals( rhs.entityLocation) )) ivarsEqual = false;
     if( ! (entityOrientation.equals( rhs.entityOrientation) )) ivarsEqual = false;
     if( ! (entityAppearance == rhs.entityAppearance)) ivarsEqual = false;
     if( ! (deadReckoningParameters.equals( rhs.deadReckoningParameters) )) ivarsEqual = false;
     if( ! (marking.equals( rhs.marking) )) ivarsEqual = false;
     if( ! (capabilities == rhs.capabilities)) ivarsEqual = false;

     for(int idx = 0; idx < variableParameters.size(); idx++)
     {
        if( ! ( variableParameters.get(idx).equals(rhs.variableParameters.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
