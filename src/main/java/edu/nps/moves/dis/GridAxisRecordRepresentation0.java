package edu.nps.moves.dis;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * 5.2.44: Grid data record, representation 0
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class GridAxisRecordRepresentation0 extends GridAxisRecord implements Serializable
{
   /** number of bytes of environmental state data */
   protected int  numberOfBytes;

   /** variable length variablelist of data parameters ^^^this is wrong--need padding as well */
   protected byte[] dataValues = new byte[0];

/** Constructor */
 public GridAxisRecordRepresentation0()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + 2;  // numberOfBytes
   marshalSize = marshalSize + dataValues.length;

   return marshalSize;
}


public int getNumberOfBytes()
{ return dataValues.length;
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfBytes method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfBytes(int pNumberOfBytes)
{ numberOfBytes = pNumberOfBytes;
}

public void setDataValues(byte[] pDataValues)
{ dataValues = pDataValues;
}

public byte[] getDataValues()
{ return dataValues; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       dos.write(dataValues);

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
       numberOfBytes = (int)dis.readUnsignedShort();
       dataValues = new byte[numberOfBytes];
       dis.read(dataValues);
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
       buff.putShort((short)dataValues.length);
       buff.put(dataValues);
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

       numberOfBytes = (int)(buff.getShort() & 0xFFFF);
       dataValues = new byte[numberOfBytes];
       buff.get(dataValues);
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

    if(!(obj instanceof GridAxisRecordRepresentation0))
        return false;

     final GridAxisRecordRepresentation0 rhs = (GridAxisRecordRepresentation0)obj;

     if( ! (numberOfBytes == rhs.numberOfBytes)) ivarsEqual = false;
     if( ! (Arrays.equals(dataValues, rhs.dataValues))) ivarsEqual = false;

    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
