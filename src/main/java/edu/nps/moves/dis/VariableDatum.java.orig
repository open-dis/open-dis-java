package edu.nps.moves.dis;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.2.32. Variable Datum Record
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class VariableDatum extends Object implements Serializable
{
   /** ID of the variable datum */
   protected long  variableDatumID;

   /** length of the variable datums, in bits. Note that this is not programmatically tied to the size of the variableData. The variable data field may be 64 bits long but only 16 bits of it could actually be used. */
   protected long  variableDatumLength;

   /** data can be any length, but must increase in 8 byte quanta. This requires some postprocessing patches. Note that setting the data allocates a new internal array to account for the possibly increased size. The default initial size is 64 bits. */
   protected List< OneByteChunk > variableData = new ArrayList< OneByteChunk >(); 

/** Constructor */
 public VariableDatum()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // variableDatumID
   marshalSize = marshalSize + 4;  // variableDatumLength
   for(int idx=0; idx < variableData.size(); idx++)
   {
        OneByteChunk listElement = variableData.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setVariableDatumID(long pVariableDatumID)
{ variableDatumID = pVariableDatumID;
}

public long getVariableDatumID()
{ return variableDatumID; 
}

public long getVariableDatumLength()
{ return (long)variableData.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getvariableDatumLength method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setVariableDatumLength(long pVariableDatumLength)
{ variableDatumLength = pVariableDatumLength;
}

public void setVariableData(List<OneByteChunk> pVariableData)
{ variableData = pVariableData;
}

public List<OneByteChunk> getVariableData()
{ return variableData; }


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)variableDatumID);
       dos.writeInt( (int)variableData.size());

       for(int idx = 0; idx < variableData.size(); idx++)
       {
            OneByteChunk aOneByteChunk = variableData.get(idx);
            aOneByteChunk.marshal(dos);
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
       variableDatumID = dis.readInt();
       variableDatumLength = dis.readInt();
       for(int idx = 0; idx < variableDatumLength; idx++)
       {
           OneByteChunk anX = new OneByteChunk();
           anX.unmarshal(dis);
           variableData.add(anX);
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
       buff.putInt( (int)variableDatumID);
       buff.putInt( (int)variableData.size());

       for(int idx = 0; idx < variableData.size(); idx++)
       {
            OneByteChunk aOneByteChunk = (OneByteChunk)variableData.get(idx);
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
       variableDatumID = buff.getInt();
       variableDatumLength = buff.getInt();
       for(int idx = 0; idx < variableDatumLength; idx++)
       {
            OneByteChunk anX = new OneByteChunk();
            anX.unmarshal(buff);
            variableData.add(anX);
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

    if(!(obj instanceof VariableDatum))
        return false;

     final VariableDatum rhs = (VariableDatum)obj;

     if( ! (variableDatumID == rhs.variableDatumID)) ivarsEqual = false;
     if( ! (variableDatumLength == rhs.variableDatumLength)) ivarsEqual = false;

     for(int idx = 0; idx < variableData.size(); idx++)
     {
        if( ! ( variableData.get(idx).equals(rhs.variableData.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
