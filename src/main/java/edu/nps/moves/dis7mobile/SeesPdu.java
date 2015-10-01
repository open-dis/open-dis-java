package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 *  SEES PDU, supplemental emissions entity state information. Section 7.6.6 COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class SeesPdu extends DistributedEmissionsFamilyPdu implements Serializable
{
   /** Originating entity ID */
   protected EntityID  orginatingEntityID = new EntityID(); 

   /** IR Signature representation index */
   protected int  infraredSignatureRepresentationIndex;

   /** acoustic Signature representation index */
   protected int  acousticSignatureRepresentationIndex;

   /** radar cross section representation index */
   protected int  radarCrossSectionSignatureRepresentationIndex;

   /** how many propulsion systems */
   protected int  numberOfPropulsionSystems;

   /** how many vectoring nozzle systems */
   protected int  numberOfVectoringNozzleSystems;

   /** variable length list of propulsion system data */
   protected List< PropulsionSystemData > propulsionSystemData = new ArrayList< PropulsionSystemData >(); 
   /** variable length list of vectoring system data */
   protected List< VectoringNozzleSystem > vectoringSystemData = new ArrayList< VectoringNozzleSystem >(); 

/** Constructor */
 public SeesPdu()
 {
    setPduType( (short)30 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + orginatingEntityID.getMarshalledSize();  // orginatingEntityID
   marshalSize = marshalSize + 2;  // infraredSignatureRepresentationIndex
   marshalSize = marshalSize + 2;  // acousticSignatureRepresentationIndex
   marshalSize = marshalSize + 2;  // radarCrossSectionSignatureRepresentationIndex
   marshalSize = marshalSize + 2;  // numberOfPropulsionSystems
   marshalSize = marshalSize + 2;  // numberOfVectoringNozzleSystems
   for(int idx=0; idx < propulsionSystemData.size(); idx++)
   {
        PropulsionSystemData listElement = propulsionSystemData.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   for(int idx=0; idx < vectoringSystemData.size(); idx++)
   {
        VectoringNozzleSystem listElement = vectoringSystemData.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setOrginatingEntityID(EntityID pOrginatingEntityID)
{ orginatingEntityID = pOrginatingEntityID;
}

public EntityID getOrginatingEntityID()
{ return orginatingEntityID; 
}

public void setInfraredSignatureRepresentationIndex(int pInfraredSignatureRepresentationIndex)
{ infraredSignatureRepresentationIndex = pInfraredSignatureRepresentationIndex;
}

public int getInfraredSignatureRepresentationIndex()
{ return infraredSignatureRepresentationIndex; 
}

public void setAcousticSignatureRepresentationIndex(int pAcousticSignatureRepresentationIndex)
{ acousticSignatureRepresentationIndex = pAcousticSignatureRepresentationIndex;
}

public int getAcousticSignatureRepresentationIndex()
{ return acousticSignatureRepresentationIndex; 
}

public void setRadarCrossSectionSignatureRepresentationIndex(int pRadarCrossSectionSignatureRepresentationIndex)
{ radarCrossSectionSignatureRepresentationIndex = pRadarCrossSectionSignatureRepresentationIndex;
}

public int getRadarCrossSectionSignatureRepresentationIndex()
{ return radarCrossSectionSignatureRepresentationIndex; 
}

public int getNumberOfPropulsionSystems()
{ return (int)propulsionSystemData.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfPropulsionSystems method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfPropulsionSystems(int pNumberOfPropulsionSystems)
{ numberOfPropulsionSystems = pNumberOfPropulsionSystems;
}

public int getNumberOfVectoringNozzleSystems()
{ return (int)vectoringSystemData.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfVectoringNozzleSystems method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfVectoringNozzleSystems(int pNumberOfVectoringNozzleSystems)
{ numberOfVectoringNozzleSystems = pNumberOfVectoringNozzleSystems;
}

public void setPropulsionSystemData(List<PropulsionSystemData> pPropulsionSystemData)
{ propulsionSystemData = pPropulsionSystemData;
}

public List<PropulsionSystemData> getPropulsionSystemData()
{ return propulsionSystemData; }

public void setVectoringSystemData(List<VectoringNozzleSystem> pVectoringSystemData)
{ vectoringSystemData = pVectoringSystemData;
}

public List<VectoringNozzleSystem> getVectoringSystemData()
{ return vectoringSystemData; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       orginatingEntityID.marshal(dos);
       dos.writeShort( (short)infraredSignatureRepresentationIndex);
       dos.writeShort( (short)acousticSignatureRepresentationIndex);
       dos.writeShort( (short)radarCrossSectionSignatureRepresentationIndex);
       dos.writeShort( (short)propulsionSystemData.size());
       dos.writeShort( (short)vectoringSystemData.size());

       for(int idx = 0; idx < propulsionSystemData.size(); idx++)
       {
            PropulsionSystemData aPropulsionSystemData = propulsionSystemData.get(idx);
            aPropulsionSystemData.marshal(dos);
       } // end of list marshalling


       for(int idx = 0; idx < vectoringSystemData.size(); idx++)
       {
            VectoringNozzleSystem aVectoringNozzleSystem = vectoringSystemData.get(idx);
            aVectoringNozzleSystem.marshal(dos);
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
       orginatingEntityID.unmarshal(dis);
       infraredSignatureRepresentationIndex = (int)dis.readUnsignedShort();
       acousticSignatureRepresentationIndex = (int)dis.readUnsignedShort();
       radarCrossSectionSignatureRepresentationIndex = (int)dis.readUnsignedShort();
       numberOfPropulsionSystems = (int)dis.readUnsignedShort();
       numberOfVectoringNozzleSystems = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < numberOfPropulsionSystems; idx++)
       {
           PropulsionSystemData anX = new PropulsionSystemData();
           anX.unmarshal(dis);
           propulsionSystemData.add(anX);
       }

       for(int idx = 0; idx < numberOfVectoringNozzleSystems; idx++)
       {
           VectoringNozzleSystem anX = new VectoringNozzleSystem();
           anX.unmarshal(dis);
           vectoringSystemData.add(anX);
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
       orginatingEntityID.marshal(buff);
       buff.putShort( (short)infraredSignatureRepresentationIndex);
       buff.putShort( (short)acousticSignatureRepresentationIndex);
       buff.putShort( (short)radarCrossSectionSignatureRepresentationIndex);
       buff.putShort( (short)propulsionSystemData.size());
       buff.putShort( (short)vectoringSystemData.size());

       for(int idx = 0; idx < propulsionSystemData.size(); idx++)
       {
            PropulsionSystemData aPropulsionSystemData = (PropulsionSystemData)propulsionSystemData.get(idx);
            aPropulsionSystemData.marshal(buff);
       } // end of list marshalling


       for(int idx = 0; idx < vectoringSystemData.size(); idx++)
       {
            VectoringNozzleSystem aVectoringNozzleSystem = (VectoringNozzleSystem)vectoringSystemData.get(idx);
            aVectoringNozzleSystem.marshal(buff);
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

       orginatingEntityID.unmarshal(buff);
       infraredSignatureRepresentationIndex = (int)(buff.getShort() & 0xFFFF);
       acousticSignatureRepresentationIndex = (int)(buff.getShort() & 0xFFFF);
       radarCrossSectionSignatureRepresentationIndex = (int)(buff.getShort() & 0xFFFF);
       numberOfPropulsionSystems = (int)(buff.getShort() & 0xFFFF);
       numberOfVectoringNozzleSystems = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < numberOfPropulsionSystems; idx++)
       {
            PropulsionSystemData anX = new PropulsionSystemData();
            anX.unmarshal(buff);
            propulsionSystemData.add(anX);
       }

       for(int idx = 0; idx < numberOfVectoringNozzleSystems; idx++)
       {
            VectoringNozzleSystem anX = new VectoringNozzleSystem();
            anX.unmarshal(buff);
            vectoringSystemData.add(anX);
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

    if(!(obj instanceof SeesPdu))
        return false;

     final SeesPdu rhs = (SeesPdu)obj;

     if( ! (orginatingEntityID.equals( rhs.orginatingEntityID) )) ivarsEqual = false;
     if( ! (infraredSignatureRepresentationIndex == rhs.infraredSignatureRepresentationIndex)) ivarsEqual = false;
     if( ! (acousticSignatureRepresentationIndex == rhs.acousticSignatureRepresentationIndex)) ivarsEqual = false;
     if( ! (radarCrossSectionSignatureRepresentationIndex == rhs.radarCrossSectionSignatureRepresentationIndex)) ivarsEqual = false;
     if( ! (numberOfPropulsionSystems == rhs.numberOfPropulsionSystems)) ivarsEqual = false;
     if( ! (numberOfVectoringNozzleSystems == rhs.numberOfVectoringNozzleSystems)) ivarsEqual = false;

     for(int idx = 0; idx < propulsionSystemData.size(); idx++)
     {
        if( ! ( propulsionSystemData.get(idx).equals(rhs.propulsionSystemData.get(idx)))) ivarsEqual = false;
     }


     for(int idx = 0; idx < vectoringSystemData.size(); idx++)
     {
        if( ! ( vectoringSystemData.get(idx).equals(rhs.vectoringSystemData.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
