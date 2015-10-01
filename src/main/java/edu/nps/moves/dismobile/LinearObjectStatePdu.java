package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.3.11.4: Information abut the addition or modification of a synthecic enviroment object that      is anchored to the terrain with a single point and has size or orientation. COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class LinearObjectStatePdu extends SyntheticEnvironmentFamilyPdu implements Serializable
{
   /** Object in synthetic environment */
   protected EntityID  objectID = new EntityID(); 

   /** Object with which this point object is associated */
   protected EntityID  referencedObjectID = new EntityID(); 

   /** unique update number of each state transition of an object */
   protected int  updateNumber;

   /** force ID */
   protected short  forceID;

   /** number of linear segment parameters */
   protected short  numberOfSegments;

   /** requesterID */
   protected SimulationAddress  requesterID = new SimulationAddress(); 

   /** receiver ID */
   protected SimulationAddress  receivingID = new SimulationAddress(); 

   /** Object type */
   protected ObjectType  objectType = new ObjectType(); 

   /** Linear segment parameters */
   protected List< LinearSegmentParameter > linearSegmentParameters = new ArrayList< LinearSegmentParameter >(); 

/** Constructor */
 public LinearObjectStatePdu()
 {
    setPduType( (short)44 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + objectID.getMarshalledSize();  // objectID
   marshalSize = marshalSize + referencedObjectID.getMarshalledSize();  // referencedObjectID
   marshalSize = marshalSize + 2;  // updateNumber
   marshalSize = marshalSize + 1;  // forceID
   marshalSize = marshalSize + 1;  // numberOfSegments
   marshalSize = marshalSize + requesterID.getMarshalledSize();  // requesterID
   marshalSize = marshalSize + receivingID.getMarshalledSize();  // receivingID
   marshalSize = marshalSize + objectType.getMarshalledSize();  // objectType
   for(int idx=0; idx < linearSegmentParameters.size(); idx++)
   {
        LinearSegmentParameter listElement = linearSegmentParameters.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setObjectID(EntityID pObjectID)
{ objectID = pObjectID;
}

public EntityID getObjectID()
{ return objectID; 
}

public void setReferencedObjectID(EntityID pReferencedObjectID)
{ referencedObjectID = pReferencedObjectID;
}

public EntityID getReferencedObjectID()
{ return referencedObjectID; 
}

public void setUpdateNumber(int pUpdateNumber)
{ updateNumber = pUpdateNumber;
}

public int getUpdateNumber()
{ return updateNumber; 
}

public void setForceID(short pForceID)
{ forceID = pForceID;
}

public short getForceID()
{ return forceID; 
}

public short getNumberOfSegments()
{ return (short)linearSegmentParameters.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfSegments method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfSegments(short pNumberOfSegments)
{ numberOfSegments = pNumberOfSegments;
}

public void setRequesterID(SimulationAddress pRequesterID)
{ requesterID = pRequesterID;
}

public SimulationAddress getRequesterID()
{ return requesterID; 
}

public void setReceivingID(SimulationAddress pReceivingID)
{ receivingID = pReceivingID;
}

public SimulationAddress getReceivingID()
{ return receivingID; 
}

public void setObjectType(ObjectType pObjectType)
{ objectType = pObjectType;
}

public ObjectType getObjectType()
{ return objectType; 
}

public void setLinearSegmentParameters(List<LinearSegmentParameter> pLinearSegmentParameters)
{ linearSegmentParameters = pLinearSegmentParameters;
}

public List<LinearSegmentParameter> getLinearSegmentParameters()
{ return linearSegmentParameters; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       objectID.marshal(dos);
       referencedObjectID.marshal(dos);
       dos.writeShort( (short)updateNumber);
       dos.writeByte( (byte)forceID);
       dos.writeByte( (byte)linearSegmentParameters.size());
       requesterID.marshal(dos);
       receivingID.marshal(dos);
       objectType.marshal(dos);

       for(int idx = 0; idx < linearSegmentParameters.size(); idx++)
       {
            LinearSegmentParameter aLinearSegmentParameter = linearSegmentParameters.get(idx);
            aLinearSegmentParameter.marshal(dos);
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
       objectID.unmarshal(dis);
       referencedObjectID.unmarshal(dis);
       updateNumber = (int)dis.readUnsignedShort();
       forceID = (short)dis.readUnsignedByte();
       numberOfSegments = (short)dis.readUnsignedByte();
       requesterID.unmarshal(dis);
       receivingID.unmarshal(dis);
       objectType.unmarshal(dis);
       for(int idx = 0; idx < numberOfSegments; idx++)
       {
           LinearSegmentParameter anX = new LinearSegmentParameter();
           anX.unmarshal(dis);
           linearSegmentParameters.add(anX);
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
       objectID.marshal(buff);
       referencedObjectID.marshal(buff);
       buff.putShort( (short)updateNumber);
       buff.put( (byte)forceID);
       buff.put( (byte)linearSegmentParameters.size());
       requesterID.marshal(buff);
       receivingID.marshal(buff);
       objectType.marshal(buff);

       for(int idx = 0; idx < linearSegmentParameters.size(); idx++)
       {
            LinearSegmentParameter aLinearSegmentParameter = (LinearSegmentParameter)linearSegmentParameters.get(idx);
            aLinearSegmentParameter.marshal(buff);
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

       objectID.unmarshal(buff);
       referencedObjectID.unmarshal(buff);
       updateNumber = (int)(buff.getShort() & 0xFFFF);
       forceID = (short)(buff.get() & 0xFF);
       numberOfSegments = (short)(buff.get() & 0xFF);
       requesterID.unmarshal(buff);
       receivingID.unmarshal(buff);
       objectType.unmarshal(buff);
       for(int idx = 0; idx < numberOfSegments; idx++)
       {
            LinearSegmentParameter anX = new LinearSegmentParameter();
            anX.unmarshal(buff);
            linearSegmentParameters.add(anX);
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

    if(!(obj instanceof LinearObjectStatePdu))
        return false;

     final LinearObjectStatePdu rhs = (LinearObjectStatePdu)obj;

     if( ! (objectID.equals( rhs.objectID) )) ivarsEqual = false;
     if( ! (referencedObjectID.equals( rhs.referencedObjectID) )) ivarsEqual = false;
     if( ! (updateNumber == rhs.updateNumber)) ivarsEqual = false;
     if( ! (forceID == rhs.forceID)) ivarsEqual = false;
     if( ! (numberOfSegments == rhs.numberOfSegments)) ivarsEqual = false;
     if( ! (requesterID.equals( rhs.requesterID) )) ivarsEqual = false;
     if( ! (receivingID.equals( rhs.receivingID) )) ivarsEqual = false;
     if( ! (objectType.equals( rhs.objectType) )) ivarsEqual = false;

     for(int idx = 0; idx < linearSegmentParameters.size(); idx++)
     {
        if( ! ( linearSegmentParameters.get(idx).equals(rhs.linearSegmentParameters.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
