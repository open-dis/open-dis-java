package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.2.7. Specifies the type of muntion fired, the type of warhead, the         type of fuse, the number of rounds fired, and the rate at which the roudns are fired in         rounds per minute.
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class BurstDescriptor extends Object implements Serializable
{
   /** What munition was used in the burst */
   protected EntityType  munition = new EntityType(); 

   /** type of warhead */
   protected int  warhead;

   /** type of fuse used */
   protected int  fuse;

   /** how many of the munition were fired */
   protected int  quantity;

   /** rate at which the munition was fired */
   protected int  rate;


/** Constructor */
 public BurstDescriptor()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + munition.getMarshalledSize();  // munition
   marshalSize = marshalSize + 2;  // warhead
   marshalSize = marshalSize + 2;  // fuse
   marshalSize = marshalSize + 2;  // quantity
   marshalSize = marshalSize + 2;  // rate

   return marshalSize;
}


public void setMunition(EntityType pMunition)
{ munition = pMunition;
}

public EntityType getMunition()
{ return munition; 
}

public void setWarhead(int pWarhead)
{ warhead = pWarhead;
}

public int getWarhead()
{ return warhead; 
}

public void setFuse(int pFuse)
{ fuse = pFuse;
}

public int getFuse()
{ return fuse; 
}

public void setQuantity(int pQuantity)
{ quantity = pQuantity;
}

public int getQuantity()
{ return quantity; 
}

public void setRate(int pRate)
{ rate = pRate;
}

public int getRate()
{ return rate; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       munition.marshal(dos);
       dos.writeShort( (short)warhead);
       dos.writeShort( (short)fuse);
       dos.writeShort( (short)quantity);
       dos.writeShort( (short)rate);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       munition.unmarshal(dis);
       warhead = (int)dis.readUnsignedShort();
       fuse = (int)dis.readUnsignedShort();
       quantity = (int)dis.readUnsignedShort();
       rate = (int)dis.readUnsignedShort();
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
       munition.marshal(buff);
       buff.putShort( (short)warhead);
       buff.putShort( (short)fuse);
       buff.putShort( (short)quantity);
       buff.putShort( (short)rate);
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
       munition.unmarshal(buff);
       warhead = (int)(buff.getShort() & 0xFFFF);
       fuse = (int)(buff.getShort() & 0xFFFF);
       quantity = (int)(buff.getShort() & 0xFFFF);
       rate = (int)(buff.getShort() & 0xFFFF);
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

    if(!(obj instanceof BurstDescriptor))
        return false;

     final BurstDescriptor rhs = (BurstDescriptor)obj;

     if( ! (munition.equals( rhs.munition) )) ivarsEqual = false;
     if( ! (warhead == rhs.warhead)) ivarsEqual = false;
     if( ! (fuse == rhs.fuse)) ivarsEqual = false;
     if( ! (quantity == rhs.quantity)) ivarsEqual = false;
     if( ! (rate == rhs.rate)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
