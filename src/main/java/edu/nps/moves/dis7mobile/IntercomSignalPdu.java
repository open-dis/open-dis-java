package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 *  Actual transmission of intercome voice data. Section 7.7.5. COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class IntercomSignalPdu extends RadioCommunicationsFamilyPdu implements Serializable
{
   /** entity ID */
   protected EntityID  entityID = new EntityID(); 

   /** ID of communications device */
   protected int  communicationsDeviceID;

   /** encoding scheme */
   protected int  encodingScheme;

   /** tactical data link type */
   protected int  tdlType;

   /** sample rate */
   protected long  sampleRate;

   /** data length */
   protected int  dataLength;

   /** samples */
   protected int  samples;

   /** data bytes */
   protected List< OneByteChunk > data = new ArrayList< OneByteChunk >(); 

/** Constructor */
 public IntercomSignalPdu()
 {
    setPduType( (short)31 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + entityID.getMarshalledSize();  // entityID
   marshalSize = marshalSize + 2;  // communicationsDeviceID
   marshalSize = marshalSize + 2;  // encodingScheme
   marshalSize = marshalSize + 2;  // tdlType
   marshalSize = marshalSize + 4;  // sampleRate
   marshalSize = marshalSize + 2;  // dataLength
   marshalSize = marshalSize + 2;  // samples
   for(int idx=0; idx < data.size(); idx++)
   {
        OneByteChunk listElement = data.get(idx);
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

public void setCommunicationsDeviceID(int pCommunicationsDeviceID)
{ communicationsDeviceID = pCommunicationsDeviceID;
}

public int getCommunicationsDeviceID()
{ return communicationsDeviceID; 
}

public void setEncodingScheme(int pEncodingScheme)
{ encodingScheme = pEncodingScheme;
}

public int getEncodingScheme()
{ return encodingScheme; 
}

public void setTdlType(int pTdlType)
{ tdlType = pTdlType;
}

public int getTdlType()
{ return tdlType; 
}

public void setSampleRate(long pSampleRate)
{ sampleRate = pSampleRate;
}

public long getSampleRate()
{ return sampleRate; 
}

public int getDataLength()
{ return (int)data.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getdataLength method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setDataLength(int pDataLength)
{ dataLength = pDataLength;
}

public void setSamples(int pSamples)
{ samples = pSamples;
}

public int getSamples()
{ return samples; 
}

public void setData(List<OneByteChunk> pData)
{ data = pData;
}

public List<OneByteChunk> getData()
{ return data; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       entityID.marshal(dos);
       dos.writeShort( (short)communicationsDeviceID);
       dos.writeShort( (short)encodingScheme);
       dos.writeShort( (short)tdlType);
       dos.writeInt( (int)sampleRate);
       dos.writeShort( (short)data.size());
       dos.writeShort( (short)samples);

       for(int idx = 0; idx < data.size(); idx++)
       {
            OneByteChunk aOneByteChunk = data.get(idx);
            aOneByteChunk.marshal(dos);
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
       communicationsDeviceID = (int)dis.readUnsignedShort();
       encodingScheme = (int)dis.readUnsignedShort();
       tdlType = (int)dis.readUnsignedShort();
       sampleRate = dis.readInt();
       dataLength = (int)dis.readUnsignedShort();
       samples = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < dataLength; idx++)
       {
           OneByteChunk anX = new OneByteChunk();
           anX.unmarshal(dis);
           data.add(anX);
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
       buff.putShort( (short)communicationsDeviceID);
       buff.putShort( (short)encodingScheme);
       buff.putShort( (short)tdlType);
       buff.putInt( (int)sampleRate);
       buff.putShort( (short)data.size());
       buff.putShort( (short)samples);

       for(int idx = 0; idx < data.size(); idx++)
       {
            OneByteChunk aOneByteChunk = (OneByteChunk)data.get(idx);
            aOneByteChunk.marshal(buff);
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
       communicationsDeviceID = (int)(buff.getShort() & 0xFFFF);
       encodingScheme = (int)(buff.getShort() & 0xFFFF);
       tdlType = (int)(buff.getShort() & 0xFFFF);
       sampleRate = buff.getInt();
       dataLength = (int)(buff.getShort() & 0xFFFF);
       samples = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < dataLength; idx++)
       {
            OneByteChunk anX = new OneByteChunk();
            anX.unmarshal(buff);
            data.add(anX);
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

    if(!(obj instanceof IntercomSignalPdu))
        return false;

     final IntercomSignalPdu rhs = (IntercomSignalPdu)obj;

     if( ! (entityID.equals( rhs.entityID) )) ivarsEqual = false;
     if( ! (communicationsDeviceID == rhs.communicationsDeviceID)) ivarsEqual = false;
     if( ! (encodingScheme == rhs.encodingScheme)) ivarsEqual = false;
     if( ! (tdlType == rhs.tdlType)) ivarsEqual = false;
     if( ! (sampleRate == rhs.sampleRate)) ivarsEqual = false;
     if( ! (dataLength == rhs.dataLength)) ivarsEqual = false;
     if( ! (samples == rhs.samples)) ivarsEqual = false;

     for(int idx = 0; idx < data.size(); idx++)
     {
        if( ! ( data.get(idx).equals(rhs.data.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
