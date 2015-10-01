package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * specification of additional information associated with an entity or detonation, not otherwise accounted for in a PDU 6.2.94.1
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class VariableParameter extends Object implements Serializable
{
   /** the identification of the Variable Parameter record. Enumeration from EBV */
   protected short  recordType;

   /** Variable parameter data fields. Two doubles minus one byte */
   protected double  variableParameterFields1;

   /** Variable parameter data fields.  */
   protected long  variableParameterFields2;

   /** Variable parameter data fields.  */
   protected int  variableParameterFields3;

   /** Variable parameter data fields.  */
   protected short  variableParameterFields4;


/** Constructor */
 public VariableParameter()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // recordType
   marshalSize = marshalSize + 8;  // variableParameterFields1
   marshalSize = marshalSize + 4;  // variableParameterFields2
   marshalSize = marshalSize + 2;  // variableParameterFields3
   marshalSize = marshalSize + 1;  // variableParameterFields4

   return marshalSize;
}


public void setRecordType(short pRecordType)
{ recordType = pRecordType;
}

public short getRecordType()
{ return recordType; 
}

public void setVariableParameterFields1(double pVariableParameterFields1)
{ variableParameterFields1 = pVariableParameterFields1;
}

public double getVariableParameterFields1()
{ return variableParameterFields1; 
}

public void setVariableParameterFields2(long pVariableParameterFields2)
{ variableParameterFields2 = pVariableParameterFields2;
}

public long getVariableParameterFields2()
{ return variableParameterFields2; 
}

public void setVariableParameterFields3(int pVariableParameterFields3)
{ variableParameterFields3 = pVariableParameterFields3;
}

public int getVariableParameterFields3()
{ return variableParameterFields3; 
}

public void setVariableParameterFields4(short pVariableParameterFields4)
{ variableParameterFields4 = pVariableParameterFields4;
}

public short getVariableParameterFields4()
{ return variableParameterFields4; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)recordType);
       dos.writeDouble( (double)variableParameterFields1);
       dos.writeInt( (int)variableParameterFields2);
       dos.writeShort( (short)variableParameterFields3);
       dos.writeByte( (byte)variableParameterFields4);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       recordType = (short)dis.readUnsignedByte();
       variableParameterFields1 = dis.readDouble();
       variableParameterFields2 = dis.readInt();
       variableParameterFields3 = (int)dis.readUnsignedShort();
       variableParameterFields4 = (short)dis.readUnsignedByte();
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
       buff.put( (byte)recordType);
       buff.putDouble( (double)variableParameterFields1);
       buff.putInt( (int)variableParameterFields2);
       buff.putShort( (short)variableParameterFields3);
       buff.put( (byte)variableParameterFields4);
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
       recordType = (short)(buff.get() & 0xFF);
       variableParameterFields1 = buff.getDouble();
       variableParameterFields2 = buff.getInt();
       variableParameterFields3 = (int)(buff.getShort() & 0xFFFF);
       variableParameterFields4 = (short)(buff.get() & 0xFF);
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

    if(!(obj instanceof VariableParameter))
        return false;

     final VariableParameter rhs = (VariableParameter)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (variableParameterFields1 == rhs.variableParameterFields1)) ivarsEqual = false;
     if( ! (variableParameterFields2 == rhs.variableParameterFields2)) ivarsEqual = false;
     if( ! (variableParameterFields3 == rhs.variableParameterFields3)) ivarsEqual = false;
     if( ! (variableParameterFields4 == rhs.variableParameterFields4)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
