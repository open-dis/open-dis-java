package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * A communications node that is part of a simulted communcations network. Section 6.2.49.2
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class IOCommunicationsNode extends Object implements Serializable
{
   protected long  recordType = (long)5501;

   protected int  recordLength = (int)16;

   protected short  communcationsNodeType;

   protected short  padding;

   protected CommunicationsNodeID  communicationsNodeID = new CommunicationsNodeID(); 


/** Constructor */
 public IOCommunicationsNode()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // recordType
   marshalSize = marshalSize + 2;  // recordLength
   marshalSize = marshalSize + 1;  // communcationsNodeType
   marshalSize = marshalSize + 1;  // padding
   marshalSize = marshalSize + communicationsNodeID.getMarshalledSize();  // communicationsNodeID

   return marshalSize;
}


public void setRecordType(long pRecordType)
{ recordType = pRecordType;
}

public long getRecordType()
{ return recordType; 
}

public void setRecordLength(int pRecordLength)
{ recordLength = pRecordLength;
}

public int getRecordLength()
{ return recordLength; 
}

public void setCommuncationsNodeType(short pCommuncationsNodeType)
{ communcationsNodeType = pCommuncationsNodeType;
}

public short getCommuncationsNodeType()
{ return communcationsNodeType; 
}

public void setPadding(short pPadding)
{ padding = pPadding;
}

public short getPadding()
{ return padding; 
}

public void setCommunicationsNodeID(CommunicationsNodeID pCommunicationsNodeID)
{ communicationsNodeID = pCommunicationsNodeID;
}

public CommunicationsNodeID getCommunicationsNodeID()
{ return communicationsNodeID; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)recordType);
       dos.writeShort( (short)recordLength);
       dos.writeByte( (byte)communcationsNodeType);
       dos.writeByte( (byte)padding);
       communicationsNodeID.marshal(dos);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       recordType = dis.readInt();
       recordLength = (int)dis.readUnsignedShort();
       communcationsNodeType = (short)dis.readUnsignedByte();
       padding = (short)dis.readUnsignedByte();
       communicationsNodeID.unmarshal(dis);
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
       buff.putInt( (int)recordType);
       buff.putShort( (short)recordLength);
       buff.put( (byte)communcationsNodeType);
       buff.put( (byte)padding);
       communicationsNodeID.marshal(buff);
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
       recordType = buff.getInt();
       recordLength = (int)(buff.getShort() & 0xFFFF);
       communcationsNodeType = (short)(buff.get() & 0xFF);
       padding = (short)(buff.get() & 0xFF);
       communicationsNodeID.unmarshal(buff);
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

    if(!(obj instanceof IOCommunicationsNode))
        return false;

     final IOCommunicationsNode rhs = (IOCommunicationsNode)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (recordLength == rhs.recordLength)) ivarsEqual = false;
     if( ! (communcationsNodeType == rhs.communcationsNodeType)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (communicationsNodeID.equals( rhs.communicationsNodeID) )) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
