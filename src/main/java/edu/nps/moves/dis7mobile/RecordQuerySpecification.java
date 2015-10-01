package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * The identification of the records being queried 6.2.72
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class RecordQuerySpecification extends Object implements Serializable
{
   protected long  numberOfRecords;

   /** variable length list of 32 bit records */
   protected List< FourByteChunk > records = new ArrayList< FourByteChunk >(); 

/** Constructor */
 public RecordQuerySpecification()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // numberOfRecords
   for(int idx=0; idx < records.size(); idx++)
   {
        FourByteChunk listElement = records.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public long getNumberOfRecords()
{ return (long)records.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfRecords method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfRecords(long pNumberOfRecords)
{ numberOfRecords = pNumberOfRecords;
}

public void setRecords(List<FourByteChunk> pRecords)
{ records = pRecords;
}

public List<FourByteChunk> getRecords()
{ return records; }


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)records.size());

       for(int idx = 0; idx < records.size(); idx++)
       {
            FourByteChunk aFourByteChunk = records.get(idx);
            aFourByteChunk.marshal(dos);
       } // end of list marshalling

    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       numberOfRecords = dis.readInt();
       for(int idx = 0; idx < numberOfRecords; idx++)
       {
           FourByteChunk anX = new FourByteChunk();
           anX.unmarshal(dis);
           records.add(anX);
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
       buff.putInt( (int)records.size());

       for(int idx = 0; idx < records.size(); idx++)
       {
            FourByteChunk aFourByteChunk = (FourByteChunk)records.get(idx);
            aFourByteChunk.marshal(buff);
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
       numberOfRecords = buff.getInt();
       for(int idx = 0; idx < numberOfRecords; idx++)
       {
            FourByteChunk anX = new FourByteChunk();
            anX.unmarshal(buff);
            records.add(anX);
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

 /**
  * Compare all fields that contribute to the state, ignoring
 transient and static fields, for <code>this</code> and the supplied object
  * @param obj the object to compare to
  * @return true if the objects are equal, false otherwise.
  */
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof RecordQuerySpecification))
        return false;

     final RecordQuerySpecification rhs = (RecordQuerySpecification)obj;

     if( ! (numberOfRecords == rhs.numberOfRecords)) ivarsEqual = false;

     for(int idx = 0; idx < records.size(); idx++)
     {
        if( ! ( records.get(idx).equals(rhs.records.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
