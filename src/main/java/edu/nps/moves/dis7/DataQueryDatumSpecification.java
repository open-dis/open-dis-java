package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * List of fixed and variable datum ID records. Section 6.2.17 
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DataQueryDatumSpecification extends Object implements Serializable
{
   /** Number of fixed datum IDs */
   protected long  numberOfFixedDatums;

   /** Number of variable datum IDs */
   protected long  numberOfVariableDatums;

   /** variable length list fixed datum IDs */
   protected UnsignedDISInteger  fixedDatumIDList = new UnsignedDISInteger(); 

   /** variable length list variable datum IDs */
   protected UnsignedDISInteger  variableDatumIDList = new UnsignedDISInteger(); 


/** Constructor */
 public DataQueryDatumSpecification()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // numberOfFixedDatums
   marshalSize = marshalSize + 4;  // numberOfVariableDatums
   marshalSize = marshalSize + fixedDatumIDList.getMarshalledSize();  // fixedDatumIDList
   marshalSize = marshalSize + variableDatumIDList.getMarshalledSize();  // variableDatumIDList

   return marshalSize;
}


public void setNumberOfFixedDatums(long pNumberOfFixedDatums)
{ numberOfFixedDatums = pNumberOfFixedDatums;
}

public long getNumberOfFixedDatums()
{ return numberOfFixedDatums; 
}

public void setNumberOfVariableDatums(long pNumberOfVariableDatums)
{ numberOfVariableDatums = pNumberOfVariableDatums;
}

public long getNumberOfVariableDatums()
{ return numberOfVariableDatums; 
}

public void setFixedDatumIDList(UnsignedDISInteger pFixedDatumIDList)
{ fixedDatumIDList = pFixedDatumIDList;
}

public UnsignedDISInteger getFixedDatumIDList()
{ return fixedDatumIDList; 
}

public void setVariableDatumIDList(UnsignedDISInteger pVariableDatumIDList)
{ variableDatumIDList = pVariableDatumIDList;
}

public UnsignedDISInteger getVariableDatumIDList()
{ return variableDatumIDList; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)numberOfFixedDatums);
       dos.writeInt( (int)numberOfVariableDatums);
       fixedDatumIDList.marshal(dos);
       variableDatumIDList.marshal(dos);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       numberOfFixedDatums = dis.readInt();
       numberOfVariableDatums = dis.readInt();
       fixedDatumIDList.unmarshal(dis);
       variableDatumIDList.unmarshal(dis);
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
       buff.putInt( (int)numberOfFixedDatums);
       buff.putInt( (int)numberOfVariableDatums);
       fixedDatumIDList.marshal(buff);
       variableDatumIDList.marshal(buff);
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
       numberOfFixedDatums = buff.getInt();
       numberOfVariableDatums = buff.getInt();
       fixedDatumIDList.unmarshal(buff);
       variableDatumIDList.unmarshal(buff);
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

    if(!(obj instanceof DataQueryDatumSpecification))
        return false;

     final DataQueryDatumSpecification rhs = (DataQueryDatumSpecification)obj;

     if( ! (numberOfFixedDatums == rhs.numberOfFixedDatums)) ivarsEqual = false;
     if( ! (numberOfVariableDatums == rhs.numberOfVariableDatums)) ivarsEqual = false;
     if( ! (fixedDatumIDList.equals( rhs.fixedDatumIDList) )) ivarsEqual = false;
     if( ! (variableDatumIDList.equals( rhs.variableDatumIDList) )) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
