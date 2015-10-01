package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Adds some fields to the the classic PDU
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class Pdu extends PduSuperclass implements Serializable
{
   /** PDU Status Record. Described in 6.2.67. This field is not present in earlier DIS versions  */
   protected short  pduStatus;

   /** zero-filled array of padding */
   protected short  padding = (short)0;


/** Constructor */
 public Pdu()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + 1;  // pduStatus
   marshalSize = marshalSize + 1;  // padding

   return marshalSize;
}


public void setPduStatus(short pPduStatus)
{ pduStatus = pPduStatus;
}

public short getPduStatus()
{ return pduStatus; 
}

public void setPadding(short pPadding)
{ padding = pPadding;
}

public short getPadding()
{ return padding; 
}


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       dos.writeByte( (byte)pduStatus);
       dos.writeByte( (byte)padding);
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
       pduStatus = (short)dis.readUnsignedByte();
       padding = (short)dis.readUnsignedByte();
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
       buff.put( (byte)pduStatus);
       buff.put( (byte)padding);
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

       pduStatus = (short)(buff.get() & 0xFF);
       padding = (short)(buff.get() & 0xFF);
 } // end of unmarshal method 


/**
 * A convenience method for marshalling to a byte array.
 * This is not as efficient as reusing a ByteBuffer, but it <em>is</em> easy.
 * @return a byte array with the marshalled {@link Pdu}
 * @since ??
 */
public byte[] marshal()
{
    byte[] data = new byte[getMarshalledSize()];
    java.nio.ByteBuffer buff = java.nio.ByteBuffer.wrap(data);
    marshal(buff);
    return data;
}

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

    if(!(obj instanceof Pdu))
        return false;

     final Pdu rhs = (Pdu)obj;

     if( ! (pduStatus == rhs.pduStatus)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
