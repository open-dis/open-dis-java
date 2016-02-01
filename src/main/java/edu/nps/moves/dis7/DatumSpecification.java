package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * List of fixed and variable datum records. Section 6.2.18 
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DatumSpecification extends Object implements Serializable
{
   /** Number of fixed datums */
   protected long  numberOfFixedDatums;

   /** Number of variable datums */
   protected long  numberOfVariableDatums;

   /** variable length list fixed datums */
   protected FixedDatum  fixedDatumList = new FixedDatum(); 

   /** variable length list variable datums. See 6.2.93 */
   protected VariableDatum  variableDatumList = new VariableDatum(); 


/** Constructor */
 public DatumSpecification()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // numberOfFixedDatums
   marshalSize = marshalSize + 4;  // numberOfVariableDatums
   marshalSize = marshalSize + fixedDatumList.getMarshalledSize();  // fixedDatumList
   marshalSize = marshalSize + variableDatumList.getMarshalledSize();  // variableDatumList

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

public void setFixedDatumList(FixedDatum pFixedDatumList)
{ fixedDatumList = pFixedDatumList;
}

public FixedDatum getFixedDatumList()
{ return fixedDatumList; 
}

public void setVariableDatumList(VariableDatum pVariableDatumList)
{ variableDatumList = pVariableDatumList;
}

public VariableDatum getVariableDatumList()
{ return variableDatumList; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)numberOfFixedDatums);
       dos.writeInt( (int)numberOfVariableDatums);
       fixedDatumList.marshal(dos);
       variableDatumList.marshal(dos);
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
       fixedDatumList.unmarshal(dis);
       variableDatumList.unmarshal(dis);
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
       fixedDatumList.marshal(buff);
       variableDatumList.marshal(buff);
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
       fixedDatumList.unmarshal(buff);
       variableDatumList.unmarshal(buff);
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

    if(!(obj instanceof DatumSpecification))
        return false;

     final DatumSpecification rhs = (DatumSpecification)obj;

     if( ! (numberOfFixedDatums == rhs.numberOfFixedDatums)) ivarsEqual = false;
     if( ! (numberOfVariableDatums == rhs.numberOfVariableDatums)) ivarsEqual = false;
     if( ! (fixedDatumList.equals( rhs.fixedDatumList) )) ivarsEqual = false;
     if( ! (variableDatumList.equals( rhs.variableDatumList) )) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
