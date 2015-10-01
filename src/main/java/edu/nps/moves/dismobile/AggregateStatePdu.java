package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.3.9.1 informationa bout aggregating entities anc communicating information about the aggregated entities.        requires manual intervention to fix the padding between entityID lists and silent aggregate sysem lists--this padding        is dependent on how many entityIDs there are, and needs to be on a 32 bit word boundary. UNFINISHED
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class AggregateStatePdu extends EntityManagementFamilyPdu implements Serializable
{
   /** ID of aggregated entities */
   protected EntityID  aggregateID = new EntityID(); 

   /** force ID */
   protected short  forceID;

   /** state of aggregate */
   protected short  aggregateState;

   /** entity type of the aggregated entities */
   protected EntityType  aggregateType = new EntityType(); 

   /** formation of aggregated entities */
   protected long  formation;

   /** marking for aggregate; first char is charset type, rest is char data */
   protected AggregateMarking  aggregateMarking = new AggregateMarking(); 

   /** dimensions of bounding box for the aggregated entities, origin at the center of mass */
   protected Vector3Float  dimensions = new Vector3Float(); 

   /** orientation of the bounding box */
   protected Orientation  orientation = new Orientation(); 

   /** center of mass of the aggregation */
   protected Vector3Double  centerOfMass = new Vector3Double(); 

   /** velocity of aggregation */
   protected Vector3Float  velocity = new Vector3Float(); 

   /** number of aggregates */
   protected int  numberOfDisAggregates;

   /** number of entities */
   protected int  numberOfDisEntities;

   /** number of silent aggregate types */
   protected int  numberOfSilentAggregateTypes;

   /** number of silent entity types */
   protected int  numberOfSilentEntityTypes;

   /** aggregates  list */
   protected List< AggregateID > aggregateIDList = new ArrayList< AggregateID >(); 
   /** entity ID list */
   protected List< EntityID > entityIDList = new ArrayList< EntityID >(); 
   /** ^^^padding to put the start of the next list on a 32 bit boundary. This needs to be fixed */
   protected short  pad2;

   /** silent entity types */
   protected List< EntityType > silentAggregateSystemList = new ArrayList< EntityType >(); 
   /** silent entity types */
   protected List< EntityType > silentEntitySystemList = new ArrayList< EntityType >(); 
   /** number of variable datum records */
   protected long  numberOfVariableDatumRecords;

   /** variableDatums */
   protected List< VariableDatum > variableDatumList = new ArrayList< VariableDatum >(); 

/** Constructor */
 public AggregateStatePdu()
 {
    setPduType( (short)33 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + aggregateID.getMarshalledSize();  // aggregateID
   marshalSize = marshalSize + 1;  // forceID
   marshalSize = marshalSize + 1;  // aggregateState
   marshalSize = marshalSize + aggregateType.getMarshalledSize();  // aggregateType
   marshalSize = marshalSize + 4;  // formation
   marshalSize = marshalSize + aggregateMarking.getMarshalledSize();  // aggregateMarking
   marshalSize = marshalSize + dimensions.getMarshalledSize();  // dimensions
   marshalSize = marshalSize + orientation.getMarshalledSize();  // orientation
   marshalSize = marshalSize + centerOfMass.getMarshalledSize();  // centerOfMass
   marshalSize = marshalSize + velocity.getMarshalledSize();  // velocity
   marshalSize = marshalSize + 2;  // numberOfDisAggregates
   marshalSize = marshalSize + 2;  // numberOfDisEntities
   marshalSize = marshalSize + 2;  // numberOfSilentAggregateTypes
   marshalSize = marshalSize + 2;  // numberOfSilentEntityTypes
   for(int idx=0; idx < aggregateIDList.size(); idx++)
   {
        AggregateID listElement = aggregateIDList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   for(int idx=0; idx < entityIDList.size(); idx++)
   {
        EntityID listElement = entityIDList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   marshalSize = marshalSize + 1;  // pad2
   for(int idx=0; idx < silentAggregateSystemList.size(); idx++)
   {
        EntityType listElement = silentAggregateSystemList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   for(int idx=0; idx < silentEntitySystemList.size(); idx++)
   {
        EntityType listElement = silentEntitySystemList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   marshalSize = marshalSize + 4;  // numberOfVariableDatumRecords
   for(int idx=0; idx < variableDatumList.size(); idx++)
   {
        VariableDatum listElement = variableDatumList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setAggregateID(EntityID pAggregateID)
{ aggregateID = pAggregateID;
}

public EntityID getAggregateID()
{ return aggregateID; 
}

public void setForceID(short pForceID)
{ forceID = pForceID;
}

public short getForceID()
{ return forceID; 
}

public void setAggregateState(short pAggregateState)
{ aggregateState = pAggregateState;
}

public short getAggregateState()
{ return aggregateState; 
}

public void setAggregateType(EntityType pAggregateType)
{ aggregateType = pAggregateType;
}

public EntityType getAggregateType()
{ return aggregateType; 
}

public void setFormation(long pFormation)
{ formation = pFormation;
}

public long getFormation()
{ return formation; 
}

public void setAggregateMarking(AggregateMarking pAggregateMarking)
{ aggregateMarking = pAggregateMarking;
}

public AggregateMarking getAggregateMarking()
{ return aggregateMarking; 
}

public void setDimensions(Vector3Float pDimensions)
{ dimensions = pDimensions;
}

public Vector3Float getDimensions()
{ return dimensions; 
}

public void setOrientation(Orientation pOrientation)
{ orientation = pOrientation;
}

public Orientation getOrientation()
{ return orientation; 
}

public void setCenterOfMass(Vector3Double pCenterOfMass)
{ centerOfMass = pCenterOfMass;
}

public Vector3Double getCenterOfMass()
{ return centerOfMass; 
}

public void setVelocity(Vector3Float pVelocity)
{ velocity = pVelocity;
}

public Vector3Float getVelocity()
{ return velocity; 
}

public int getNumberOfDisAggregates()
{ return (int)aggregateIDList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfDisAggregates method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfDisAggregates(int pNumberOfDisAggregates)
{ numberOfDisAggregates = pNumberOfDisAggregates;
}

public int getNumberOfDisEntities()
{ return (int)entityIDList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfDisEntities method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfDisEntities(int pNumberOfDisEntities)
{ numberOfDisEntities = pNumberOfDisEntities;
}

public int getNumberOfSilentAggregateTypes()
{ return (int)silentAggregateSystemList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfSilentAggregateTypes method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfSilentAggregateTypes(int pNumberOfSilentAggregateTypes)
{ numberOfSilentAggregateTypes = pNumberOfSilentAggregateTypes;
}

public int getNumberOfSilentEntityTypes()
{ return (int)silentEntitySystemList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfSilentEntityTypes method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfSilentEntityTypes(int pNumberOfSilentEntityTypes)
{ numberOfSilentEntityTypes = pNumberOfSilentEntityTypes;
}

public void setAggregateIDList(List<AggregateID> pAggregateIDList)
{ aggregateIDList = pAggregateIDList;
}

public List<AggregateID> getAggregateIDList()
{ return aggregateIDList; }

public void setEntityIDList(List<EntityID> pEntityIDList)
{ entityIDList = pEntityIDList;
}

public List<EntityID> getEntityIDList()
{ return entityIDList; }

public void setPad2(short pPad2)
{ pad2 = pPad2;
}

public short getPad2()
{ return pad2; 
}

public void setSilentAggregateSystemList(List<EntityType> pSilentAggregateSystemList)
{ silentAggregateSystemList = pSilentAggregateSystemList;
}

public List<EntityType> getSilentAggregateSystemList()
{ return silentAggregateSystemList; }

public void setSilentEntitySystemList(List<EntityType> pSilentEntitySystemList)
{ silentEntitySystemList = pSilentEntitySystemList;
}

public List<EntityType> getSilentEntitySystemList()
{ return silentEntitySystemList; }

public long getNumberOfVariableDatumRecords()
{ return (long)variableDatumList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfVariableDatumRecords method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfVariableDatumRecords(long pNumberOfVariableDatumRecords)
{ numberOfVariableDatumRecords = pNumberOfVariableDatumRecords;
}

public void setVariableDatumList(List<VariableDatum> pVariableDatumList)
{ variableDatumList = pVariableDatumList;
}

public List<VariableDatum> getVariableDatumList()
{ return variableDatumList; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       aggregateID.marshal(dos);
       dos.writeByte( (byte)forceID);
       dos.writeByte( (byte)aggregateState);
       aggregateType.marshal(dos);
       dos.writeInt( (int)formation);
       aggregateMarking.marshal(dos);
       dimensions.marshal(dos);
       orientation.marshal(dos);
       centerOfMass.marshal(dos);
       velocity.marshal(dos);
       dos.writeShort( (short)aggregateIDList.size());
       dos.writeShort( (short)entityIDList.size());
       dos.writeShort( (short)silentAggregateSystemList.size());
       dos.writeShort( (short)silentEntitySystemList.size());

       for(int idx = 0; idx < aggregateIDList.size(); idx++)
       {
            AggregateID aAggregateID = aggregateIDList.get(idx);
            aAggregateID.marshal(dos);
       } // end of list marshalling


       for(int idx = 0; idx < entityIDList.size(); idx++)
       {
            EntityID aEntityID = entityIDList.get(idx);
            aEntityID.marshal(dos);
       } // end of list marshalling

       dos.writeByte( (byte)pad2);

       for(int idx = 0; idx < silentAggregateSystemList.size(); idx++)
       {
            EntityType aEntityType = silentAggregateSystemList.get(idx);
            aEntityType.marshal(dos);
       } // end of list marshalling


       for(int idx = 0; idx < silentEntitySystemList.size(); idx++)
       {
            EntityType aEntityType = silentEntitySystemList.get(idx);
            aEntityType.marshal(dos);
       } // end of list marshalling

       dos.writeInt( (int)variableDatumList.size());

       for(int idx = 0; idx < variableDatumList.size(); idx++)
       {
            VariableDatum aVariableDatum = variableDatumList.get(idx);
            aVariableDatum.marshal(dos);
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
       aggregateID.unmarshal(dis);
       forceID = (short)dis.readUnsignedByte();
       aggregateState = (short)dis.readUnsignedByte();
       aggregateType.unmarshal(dis);
       formation = dis.readInt();
       aggregateMarking.unmarshal(dis);
       dimensions.unmarshal(dis);
       orientation.unmarshal(dis);
       centerOfMass.unmarshal(dis);
       velocity.unmarshal(dis);
       numberOfDisAggregates = (int)dis.readUnsignedShort();
       numberOfDisEntities = (int)dis.readUnsignedShort();
       numberOfSilentAggregateTypes = (int)dis.readUnsignedShort();
       numberOfSilentEntityTypes = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < numberOfDisAggregates; idx++)
       {
           AggregateID anX = new AggregateID();
           anX.unmarshal(dis);
           aggregateIDList.add(anX);
       }

       for(int idx = 0; idx < numberOfDisEntities; idx++)
       {
           EntityID anX = new EntityID();
           anX.unmarshal(dis);
           entityIDList.add(anX);
       }

       pad2 = (short)dis.readUnsignedByte();
       for(int idx = 0; idx < numberOfSilentAggregateTypes; idx++)
       {
           EntityType anX = new EntityType();
           anX.unmarshal(dis);
           silentAggregateSystemList.add(anX);
       }

       for(int idx = 0; idx < numberOfSilentEntityTypes; idx++)
       {
           EntityType anX = new EntityType();
           anX.unmarshal(dis);
           silentEntitySystemList.add(anX);
       }

       numberOfVariableDatumRecords = dis.readInt();
       for(int idx = 0; idx < numberOfVariableDatumRecords; idx++)
       {
           VariableDatum anX = new VariableDatum();
           anX.unmarshal(dis);
           variableDatumList.add(anX);
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
       aggregateID.marshal(buff);
       buff.put( (byte)forceID);
       buff.put( (byte)aggregateState);
       aggregateType.marshal(buff);
       buff.putInt( (int)formation);
       aggregateMarking.marshal(buff);
       dimensions.marshal(buff);
       orientation.marshal(buff);
       centerOfMass.marshal(buff);
       velocity.marshal(buff);
       buff.putShort( (short)aggregateIDList.size());
       buff.putShort( (short)entityIDList.size());
       buff.putShort( (short)silentAggregateSystemList.size());
       buff.putShort( (short)silentEntitySystemList.size());

       for(int idx = 0; idx < aggregateIDList.size(); idx++)
       {
            AggregateID aAggregateID = (AggregateID)aggregateIDList.get(idx);
            aAggregateID.marshal(buff);
       } // end of list marshalling


       for(int idx = 0; idx < entityIDList.size(); idx++)
       {
            EntityID aEntityID = (EntityID)entityIDList.get(idx);
            aEntityID.marshal(buff);
       } // end of list marshalling

       buff.put( (byte)pad2);

       for(int idx = 0; idx < silentAggregateSystemList.size(); idx++)
       {
            EntityType aEntityType = (EntityType)silentAggregateSystemList.get(idx);
            aEntityType.marshal(buff);
       } // end of list marshalling


       for(int idx = 0; idx < silentEntitySystemList.size(); idx++)
       {
            EntityType aEntityType = (EntityType)silentEntitySystemList.get(idx);
            aEntityType.marshal(buff);
       } // end of list marshalling

       buff.putInt( (int)variableDatumList.size());

       for(int idx = 0; idx < variableDatumList.size(); idx++)
       {
            VariableDatum aVariableDatum = (VariableDatum)variableDatumList.get(idx);
            aVariableDatum.marshal(buff);
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

       aggregateID.unmarshal(buff);
       forceID = (short)(buff.get() & 0xFF);
       aggregateState = (short)(buff.get() & 0xFF);
       aggregateType.unmarshal(buff);
       formation = buff.getInt();
       aggregateMarking.unmarshal(buff);
       dimensions.unmarshal(buff);
       orientation.unmarshal(buff);
       centerOfMass.unmarshal(buff);
       velocity.unmarshal(buff);
       numberOfDisAggregates = (int)(buff.getShort() & 0xFFFF);
       numberOfDisEntities = (int)(buff.getShort() & 0xFFFF);
       numberOfSilentAggregateTypes = (int)(buff.getShort() & 0xFFFF);
       numberOfSilentEntityTypes = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < numberOfDisAggregates; idx++)
       {
            AggregateID anX = new AggregateID();
            anX.unmarshal(buff);
            aggregateIDList.add(anX);
       }

       for(int idx = 0; idx < numberOfDisEntities; idx++)
       {
            EntityID anX = new EntityID();
            anX.unmarshal(buff);
            entityIDList.add(anX);
       }

       pad2 = (short)(buff.get() & 0xFF);
       for(int idx = 0; idx < numberOfSilentAggregateTypes; idx++)
       {
            EntityType anX = new EntityType();
            anX.unmarshal(buff);
            silentAggregateSystemList.add(anX);
       }

       for(int idx = 0; idx < numberOfSilentEntityTypes; idx++)
       {
            EntityType anX = new EntityType();
            anX.unmarshal(buff);
            silentEntitySystemList.add(anX);
       }

       numberOfVariableDatumRecords = buff.getInt();
       for(int idx = 0; idx < numberOfVariableDatumRecords; idx++)
       {
            VariableDatum anX = new VariableDatum();
            anX.unmarshal(buff);
            variableDatumList.add(anX);
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

    if(!(obj instanceof AggregateStatePdu))
        return false;

     final AggregateStatePdu rhs = (AggregateStatePdu)obj;

     if( ! (aggregateID.equals( rhs.aggregateID) )) ivarsEqual = false;
     if( ! (forceID == rhs.forceID)) ivarsEqual = false;
     if( ! (aggregateState == rhs.aggregateState)) ivarsEqual = false;
     if( ! (aggregateType.equals( rhs.aggregateType) )) ivarsEqual = false;
     if( ! (formation == rhs.formation)) ivarsEqual = false;
     if( ! (aggregateMarking.equals( rhs.aggregateMarking) )) ivarsEqual = false;
     if( ! (dimensions.equals( rhs.dimensions) )) ivarsEqual = false;
     if( ! (orientation.equals( rhs.orientation) )) ivarsEqual = false;
     if( ! (centerOfMass.equals( rhs.centerOfMass) )) ivarsEqual = false;
     if( ! (velocity.equals( rhs.velocity) )) ivarsEqual = false;
     if( ! (numberOfDisAggregates == rhs.numberOfDisAggregates)) ivarsEqual = false;
     if( ! (numberOfDisEntities == rhs.numberOfDisEntities)) ivarsEqual = false;
     if( ! (numberOfSilentAggregateTypes == rhs.numberOfSilentAggregateTypes)) ivarsEqual = false;
     if( ! (numberOfSilentEntityTypes == rhs.numberOfSilentEntityTypes)) ivarsEqual = false;

     for(int idx = 0; idx < aggregateIDList.size(); idx++)
     {
        if( ! ( aggregateIDList.get(idx).equals(rhs.aggregateIDList.get(idx)))) ivarsEqual = false;
     }


     for(int idx = 0; idx < entityIDList.size(); idx++)
     {
        if( ! ( entityIDList.get(idx).equals(rhs.entityIDList.get(idx)))) ivarsEqual = false;
     }

     if( ! (pad2 == rhs.pad2)) ivarsEqual = false;

     for(int idx = 0; idx < silentAggregateSystemList.size(); idx++)
     {
        if( ! ( silentAggregateSystemList.get(idx).equals(rhs.silentAggregateSystemList.get(idx)))) ivarsEqual = false;
     }


     for(int idx = 0; idx < silentEntitySystemList.size(); idx++)
     {
        if( ! ( silentEntitySystemList.get(idx).equals(rhs.silentEntitySystemList.get(idx)))) ivarsEqual = false;
     }

     if( ! (numberOfVariableDatumRecords == rhs.numberOfVariableDatumRecords)) ivarsEqual = false;

     for(int idx = 0; idx < variableDatumList.size(); idx++)
     {
        if( ! ( variableDatumList.get(idx).equals(rhs.variableDatumList.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
