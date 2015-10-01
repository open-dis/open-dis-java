package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * List of fixed and variable datum records. Section 6.2.17 
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DataQueryDatumSpecification extends Object implements Serializable
{
   /** Number of fixed datums */
   protected long  numberOfFixedDatums;

   /** Number of variable datums */
   protected long  numberOfVariableDatums;

   /** variable length list fixed datum IDs */
   protected List< UnsignedDISInteger > fixedDatumIDList = new ArrayList< UnsignedDISInteger >(); 
   /** variable length list variable datum IDs */
   protected List< UnsignedDISInteger > variableDatumIDList = new ArrayList< UnsignedDISInteger >(); 

/** Constructor */
 public DataQueryDatumSpecification()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // numberOfFixedDatums
   marshalSize = marshalSize + 4;  // numberOfVariableDatums
   for(int idx=0; idx < fixedDatumIDList.size(); idx++)
   {
        UnsignedDISInteger listElement = fixedDatumIDList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   for(int idx=0; idx < variableDatumIDList.size(); idx++)
   {
        UnsignedDISInteger listElement = variableDatumIDList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public long getNumberOfFixedDatums()
{ return (long)fixedDatumIDList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfFixedDatums method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfFixedDatums(long pNumberOfFixedDatums)
{ numberOfFixedDatums = pNumberOfFixedDatums;
}

public long getNumberOfVariableDatums()
{ return (long)variableDatumIDList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfVariableDatums method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfVariableDatums(long pNumberOfVariableDatums)
{ numberOfVariableDatums = pNumberOfVariableDatums;
}

public void setFixedDatumIDList(List<UnsignedDISInteger> pFixedDatumIDList)
{ fixedDatumIDList = pFixedDatumIDList;
}

public List<UnsignedDISInteger> getFixedDatumIDList()
{ return fixedDatumIDList; }

public void setVariableDatumIDList(List<UnsignedDISInteger> pVariableDatumIDList)
{ variableDatumIDList = pVariableDatumIDList;
}

public List<UnsignedDISInteger> getVariableDatumIDList()
{ return variableDatumIDList; }


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)fixedDatumIDList.size());
       dos.writeInt( (int)variableDatumIDList.size());

       for(int idx = 0; idx < fixedDatumIDList.size(); idx++)
       {
            UnsignedDISInteger aUnsignedDISInteger = fixedDatumIDList.get(idx);
            aUnsignedDISInteger.marshal(dos);
       } // end of list marshalling


       for(int idx = 0; idx < variableDatumIDList.size(); idx++)
       {
            UnsignedDISInteger aUnsignedDISInteger = variableDatumIDList.get(idx);
            aUnsignedDISInteger.marshal(dos);
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
       numberOfFixedDatums = dis.readInt();
       numberOfVariableDatums = dis.readInt();
       for(int idx = 0; idx < numberOfFixedDatums; idx++)
       {
           UnsignedDISInteger anX = new UnsignedDISInteger();
           anX.unmarshal(dis);
           fixedDatumIDList.add(anX);
       }

       for(int idx = 0; idx < numberOfVariableDatums; idx++)
       {
           UnsignedDISInteger anX = new UnsignedDISInteger();
           anX.unmarshal(dis);
           variableDatumIDList.add(anX);
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
       buff.putInt( (int)fixedDatumIDList.size());
       buff.putInt( (int)variableDatumIDList.size());

       for(int idx = 0; idx < fixedDatumIDList.size(); idx++)
       {
            UnsignedDISInteger aUnsignedDISInteger = (UnsignedDISInteger)fixedDatumIDList.get(idx);
            aUnsignedDISInteger.marshal(buff);
       } // end of list marshalling


       for(int idx = 0; idx < variableDatumIDList.size(); idx++)
       {
            UnsignedDISInteger aUnsignedDISInteger = (UnsignedDISInteger)variableDatumIDList.get(idx);
            aUnsignedDISInteger.marshal(buff);
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
       numberOfFixedDatums = buff.getInt();
       numberOfVariableDatums = buff.getInt();
       for(int idx = 0; idx < numberOfFixedDatums; idx++)
       {
            UnsignedDISInteger anX = new UnsignedDISInteger();
            anX.unmarshal(buff);
            fixedDatumIDList.add(anX);
       }

       for(int idx = 0; idx < numberOfVariableDatums; idx++)
       {
            UnsignedDISInteger anX = new UnsignedDISInteger();
            anX.unmarshal(buff);
            variableDatumIDList.add(anX);
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

    if(!(obj instanceof DataQueryDatumSpecification))
        return false;

     final DataQueryDatumSpecification rhs = (DataQueryDatumSpecification)obj;

     if( ! (numberOfFixedDatums == rhs.numberOfFixedDatums)) ivarsEqual = false;
     if( ! (numberOfVariableDatums == rhs.numberOfVariableDatums)) ivarsEqual = false;

     for(int idx = 0; idx < fixedDatumIDList.size(); idx++)
     {
        if( ! ( fixedDatumIDList.get(idx).equals(rhs.fixedDatumIDList.get(idx)))) ivarsEqual = false;
     }


     for(int idx = 0; idx < variableDatumIDList.size(); idx++)
     {
        if( ! ( variableDatumIDList.get(idx).equals(rhs.variableDatumIDList.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
