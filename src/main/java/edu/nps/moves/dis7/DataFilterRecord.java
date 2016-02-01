package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * identify which of the optional data fields are contained in the Minefield Data PDU or requested in the Minefield Query PDU. This is a 32-bit record. For each field, true denotes that the data is requested or present and false denotes that the data is neither requested nor present. Section 6.2.16
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DataFilterRecord extends Object implements Serializable
{
   /** Bitflags field */
   protected long  bitFlags;


/** Constructor */
 public DataFilterRecord()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // bitFlags

   return marshalSize;
}


public void setBitFlags(long pBitFlags)
{ bitFlags = pBitFlags;
}

public long getBitFlags()
{ return bitFlags; 
}


/**
 * boolean
 */
public int getBitFlags_groundBurialDepthOffset()
{
    long val = (long)(this.bitFlags   & (long)0x1);
    return (int)(val >> 0);
}


/** 
 * boolean
 */
public void setBitFlags_groundBurialDepthOffset(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x1); // clear bits
    aVal = (long)(val << 0);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_waterBurialDepthOffset()
{
    long val = (long)(this.bitFlags   & (long)0x2);
    return (int)(val >> 1);
}


/** 
 * boolean
 */
public void setBitFlags_waterBurialDepthOffset(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x2); // clear bits
    aVal = (long)(val << 1);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_snowBurialDepthOffset()
{
    long val = (long)(this.bitFlags   & (long)0x4);
    return (int)(val >> 2);
}


/** 
 * boolean
 */
public void setBitFlags_snowBurialDepthOffset(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x4); // clear bits
    aVal = (long)(val << 2);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_mineOrientation()
{
    long val = (long)(this.bitFlags   & (long)0x8);
    return (int)(val >> 3);
}


/** 
 * boolean
 */
public void setBitFlags_mineOrientation(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x8); // clear bits
    aVal = (long)(val << 3);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_thermalContrast()
{
    long val = (long)(this.bitFlags   & (long)0x10);
    return (int)(val >> 4);
}


/** 
 * boolean
 */
public void setBitFlags_thermalContrast(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x10); // clear bits
    aVal = (long)(val << 4);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_reflectance()
{
    long val = (long)(this.bitFlags   & (long)0x20);
    return (int)(val >> 5);
}


/** 
 * boolean
 */
public void setBitFlags_reflectance(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x20); // clear bits
    aVal = (long)(val << 5);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_mineEmplacementTime()
{
    long val = (long)(this.bitFlags   & (long)0x40);
    return (int)(val >> 6);
}


/** 
 * boolean
 */
public void setBitFlags_mineEmplacementTime(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x40); // clear bits
    aVal = (long)(val << 6);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_tripDetonationWire()
{
    long val = (long)(this.bitFlags   & (long)0x80);
    return (int)(val >> 7);
}


/** 
 * boolean
 */
public void setBitFlags_tripDetonationWire(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x80); // clear bits
    aVal = (long)(val << 7);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_fusing()
{
    long val = (long)(this.bitFlags   & (long)0x100);
    return (int)(val >> 8);
}


/** 
 * boolean
 */
public void setBitFlags_fusing(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x100); // clear bits
    aVal = (long)(val << 8);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_scalarDetectionCoefficient()
{
    long val = (long)(this.bitFlags   & (long)0x200);
    return (int)(val >> 9);
}


/** 
 * boolean
 */
public void setBitFlags_scalarDetectionCoefficient(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x200); // clear bits
    aVal = (long)(val << 9);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * boolean
 */
public int getBitFlags_paintScheme()
{
    long val = (long)(this.bitFlags   & (long)0x400);
    return (int)(val >> 10);
}


/** 
 * boolean
 */
public void setBitFlags_paintScheme(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0x400); // clear bits
    aVal = (long)(val << 10);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


/**
 * padding
 */
public int getBitFlags_padding()
{
    long val = (long)(this.bitFlags   & (long)0xff800);
    return (int)(val >> 11);
}


/** 
 * padding
 */
public void setBitFlags_padding(int val)
{
    long  aVal = 0;
    this.bitFlags &= (long)(~0xff800); // clear bits
    aVal = (long)(val << 11);
    this.bitFlags = (long)(this.bitFlags | aVal);
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)bitFlags);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       bitFlags = dis.readInt();
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
       buff.putInt( (int)bitFlags);
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
       bitFlags = buff.getInt();
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

    if(!(obj instanceof DataFilterRecord))
        return false;

     final DataFilterRecord rhs = (DataFilterRecord)obj;

     if( ! (bitFlags == rhs.bitFlags)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
