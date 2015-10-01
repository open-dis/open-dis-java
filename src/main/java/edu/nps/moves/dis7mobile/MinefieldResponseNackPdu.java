package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * proivde the means to request a retransmit of a minefield data pdu. Section 7.9.5 COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class MinefieldResponseNackPdu extends MinefieldFamilyPdu implements Serializable
{
   /** Minefield ID */
   protected EntityID  minefieldID = new EntityID(); 

   /** entity ID making the request */
   protected EntityID  requestingEntityID = new EntityID(); 

   /** request ID */
   protected short  requestID;

   /** how many pdus were missing */
   protected short  numberOfMissingPdus;

   /** PDU sequence numbers that were missing */
   protected List< EightByteChunk > missingPduSequenceNumbers = new ArrayList< EightByteChunk >(); 

/** Constructor */
 public MinefieldResponseNackPdu()
 {
    setPduType( (short)40 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + minefieldID.getMarshalledSize();  // minefieldID
   marshalSize = marshalSize + requestingEntityID.getMarshalledSize();  // requestingEntityID
   marshalSize = marshalSize + 1;  // requestID
   marshalSize = marshalSize + 1;  // numberOfMissingPdus
   for(int idx=0; idx < missingPduSequenceNumbers.size(); idx++)
   {
        EightByteChunk listElement = missingPduSequenceNumbers.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setMinefieldID(EntityID pMinefieldID)
{ minefieldID = pMinefieldID;
}

public EntityID getMinefieldID()
{ return minefieldID; 
}

public void setRequestingEntityID(EntityID pRequestingEntityID)
{ requestingEntityID = pRequestingEntityID;
}

public EntityID getRequestingEntityID()
{ return requestingEntityID; 
}

public void setRequestID(short pRequestID)
{ requestID = pRequestID;
}

public short getRequestID()
{ return requestID; 
}

public short getNumberOfMissingPdus()
{ return (short)missingPduSequenceNumbers.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfMissingPdus method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfMissingPdus(short pNumberOfMissingPdus)
{ numberOfMissingPdus = pNumberOfMissingPdus;
}

public void setMissingPduSequenceNumbers(List<EightByteChunk> pMissingPduSequenceNumbers)
{ missingPduSequenceNumbers = pMissingPduSequenceNumbers;
}

public List<EightByteChunk> getMissingPduSequenceNumbers()
{ return missingPduSequenceNumbers; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       minefieldID.marshal(dos);
       requestingEntityID.marshal(dos);
       dos.writeByte( (byte)requestID);
       dos.writeByte( (byte)missingPduSequenceNumbers.size());

       for(int idx = 0; idx < missingPduSequenceNumbers.size(); idx++)
       {
            EightByteChunk aEightByteChunk = missingPduSequenceNumbers.get(idx);
            aEightByteChunk.marshal(dos);
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
       minefieldID.unmarshal(dis);
       requestingEntityID.unmarshal(dis);
       requestID = (short)dis.readUnsignedByte();
       numberOfMissingPdus = (short)dis.readUnsignedByte();
       for(int idx = 0; idx < numberOfMissingPdus; idx++)
       {
           EightByteChunk anX = new EightByteChunk();
           anX.unmarshal(dis);
           missingPduSequenceNumbers.add(anX);
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
       minefieldID.marshal(buff);
       requestingEntityID.marshal(buff);
       buff.put( (byte)requestID);
       buff.put( (byte)missingPduSequenceNumbers.size());

       for(int idx = 0; idx < missingPduSequenceNumbers.size(); idx++)
       {
            EightByteChunk aEightByteChunk = (EightByteChunk)missingPduSequenceNumbers.get(idx);
            aEightByteChunk.marshal(buff);
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

       minefieldID.unmarshal(buff);
       requestingEntityID.unmarshal(buff);
       requestID = (short)(buff.get() & 0xFF);
       numberOfMissingPdus = (short)(buff.get() & 0xFF);
       for(int idx = 0; idx < numberOfMissingPdus; idx++)
       {
            EightByteChunk anX = new EightByteChunk();
            anX.unmarshal(buff);
            missingPduSequenceNumbers.add(anX);
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

    if(!(obj instanceof MinefieldResponseNackPdu))
        return false;

     final MinefieldResponseNackPdu rhs = (MinefieldResponseNackPdu)obj;

     if( ! (minefieldID.equals( rhs.minefieldID) )) ivarsEqual = false;
     if( ! (requestingEntityID.equals( rhs.requestingEntityID) )) ivarsEqual = false;
     if( ! (requestID == rhs.requestID)) ivarsEqual = false;
     if( ! (numberOfMissingPdus == rhs.numberOfMissingPdus)) ivarsEqual = false;

     for(int idx = 0; idx < missingPduSequenceNumbers.size(); idx++)
     {
        if( ! ( missingPduSequenceNumbers.get(idx).equals(rhs.missingPduSequenceNumbers.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
