package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * 5.2.44: Grid data record, representation 1
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class GridAxisRecordRepresentation1 extends GridAxisRecord implements Serializable
{
   /** constant scale factor */
   protected float  fieldScale;

   /** constant offset used to scale grid data */
   protected float  fieldOffset;

   /** Number of data values */
   protected int  numberOfValues;

   /** variable length list of data parameters ^^^this is wrong--need padding as well */
   protected List< TwoByteChunk > dataValues = new ArrayList< TwoByteChunk >(); 

/** Constructor */
 public GridAxisRecordRepresentation1()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + 4;  // fieldScale
   marshalSize = marshalSize + 4;  // fieldOffset
   marshalSize = marshalSize + 2;  // numberOfValues
   for(int idx=0; idx < dataValues.size(); idx++)
   {
        TwoByteChunk listElement = dataValues.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setFieldScale(float pFieldScale)
{ fieldScale = pFieldScale;
}

public float getFieldScale()
{ return fieldScale; 
}

public void setFieldOffset(float pFieldOffset)
{ fieldOffset = pFieldOffset;
}

public float getFieldOffset()
{ return fieldOffset; 
}

public int getNumberOfValues()
{ return (int)dataValues.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfValues method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfValues(int pNumberOfValues)
{ numberOfValues = pNumberOfValues;
}

public void setDataValues(List<TwoByteChunk> pDataValues)
{ dataValues = pDataValues;
}

public List<TwoByteChunk> getDataValues()
{ return dataValues; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       dos.writeFloat( (float)fieldScale);
       dos.writeFloat( (float)fieldOffset);
       dos.writeShort( (short)dataValues.size());

       for(int idx = 0; idx < dataValues.size(); idx++)
       {
            TwoByteChunk aTwoByteChunk = dataValues.get(idx);
            aTwoByteChunk.marshal(dos);
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
       fieldScale = dis.readFloat();
       fieldOffset = dis.readFloat();
       numberOfValues = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < numberOfValues; idx++)
       {
           TwoByteChunk anX = new TwoByteChunk();
           anX.unmarshal(dis);
           dataValues.add(anX);
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
       buff.putFloat( (float)fieldScale);
       buff.putFloat( (float)fieldOffset);
       buff.putShort( (short)dataValues.size());

       for(int idx = 0; idx < dataValues.size(); idx++)
       {
            TwoByteChunk aTwoByteChunk = (TwoByteChunk)dataValues.get(idx);
            aTwoByteChunk.marshal(buff);
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

       fieldScale = buff.getFloat();
       fieldOffset = buff.getFloat();
       numberOfValues = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < numberOfValues; idx++)
       {
            TwoByteChunk anX = new TwoByteChunk();
            anX.unmarshal(buff);
            dataValues.add(anX);
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

    if(!(obj instanceof GridAxisRecordRepresentation1))
        return false;

     final GridAxisRecordRepresentation1 rhs = (GridAxisRecordRepresentation1)obj;

     if( ! (fieldScale == rhs.fieldScale)) ivarsEqual = false;
     if( ! (fieldOffset == rhs.fieldOffset)) ivarsEqual = false;
     if( ! (numberOfValues == rhs.numberOfValues)) ivarsEqual = false;

     for(int idx = 0; idx < dataValues.size(); idx++)
     {
        if( ! ( dataValues.get(idx).equals(rhs.dataValues.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
