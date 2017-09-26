package edu.nps.moves.dis;

import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The superclass for all PDUs. This incorporates the PduHeader record, section 5.2.29.
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class Pdu extends Object implements Serializable
{
   /** The version of the protocol. 5=DIS-1995, 6=DIS-1998. */
   protected short  protocolVersion = (short)6;

   /** Exercise ID */
   protected short  exerciseID = (short)0;

   /** Type of pdu, unique for each PDU class */
   protected short  pduType;

   /** value that refers to the protocol family, eg SimulationManagement, et */
   protected short  protocolFamily;

   /** Timestamp value */
   protected long  timestamp;

   /** Length, in bytes, of the PDU. Changed name from length to avoid use of Hibernate QL reserved word */
   protected int  pduLength;

   /** zero-filled array of padding */
   protected short  padding = (short)0;


/** Constructor */
 public Pdu()
 {
 }
   
public int getLength()
{
    return this.getMarshalledSize();    
}

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // protocolVersion
   marshalSize = marshalSize + 1;  // exerciseID
   marshalSize = marshalSize + 1;  // pduType
   marshalSize = marshalSize + 1;  // protocolFamily
   marshalSize = marshalSize + 4;  // timestamp
   marshalSize = marshalSize + 2;  // pduLength
   marshalSize = marshalSize + 2;  // padding

   return marshalSize;
}


public void setProtocolVersion(short pProtocolVersion)
{ protocolVersion = pProtocolVersion;
}

public short getProtocolVersion()
{ return protocolVersion; 
}

public void setExerciseID(short pExerciseID)
{ exerciseID = pExerciseID;
}

public short getExerciseID()
{ return exerciseID; 
}

public void setPduType(short pPduType)
{ pduType = pPduType;
}

public short getPduType()
{ return pduType; 
}

/**
* Returns the PduType, an enumeration from the disenum jar file. This is an enumerated
* java type, rather than a simple short integer. This should NOT be marshalled to DIS
* or XML.
* @return this Pdu's type enumeration
*/
public PduType getPduTypeEnum() {
   return PduType.lookup[pduType];
}

public void setProtocolFamily(short pProtocolFamily)
{ protocolFamily = pProtocolFamily;
}

public short getProtocolFamily()
{ return protocolFamily; 
}

public void setTimestamp(long pTimestamp)
{ timestamp = pTimestamp;
}

public long getTimestamp()
{ return timestamp; 
}

public void setPduLength(int pPduLength)
{ pduLength = pPduLength;
}

public int getPduLength()
{ return pduLength; 
}

public void setPadding(short pPadding)
{ padding = pPadding;
}

public short getPadding()
{ return padding; 
}

private static long shiftBytes(int[] fourBytes)
{
   long value = 0;
   value  = ((long) (fourBytes[0] << 24
               | fourBytes[1] << 16
               | fourBytes[2] << 8
               | fourBytes[3]))
               & 0xFFFFFFFFL;
        
   return value;
}
    
public static long readUnsignedInt(java.nio.ByteBuffer buff)
{
        int fourBytes[] = new int[4];
        
        fourBytes[0] = toUnsignedInt(buff.get());
        fourBytes[1] = toUnsignedInt(buff.get());
        fourBytes[2] = toUnsignedInt(buff.get());
        fourBytes[3] = toUnsignedInt(buff.get());
        
        return shiftBytes(fourBytes);
    }

// TODO: replace with Byte#toUnsignedInt once Java 8 is new baseline.
public static int toUnsignedInt(byte b) {
    return b & 0xff;
}

// TODO: replace with Short#toUnsignedInt once Java 8 is new baseline.
public static int toUnsignedInt(short s) {
    return s & 0xffff;
}

public void marshal(DataOutputStream dos)
{
    try {
        dos.write(marshal());
    } catch (IOException ex) {
        Logger.getLogger(Pdu.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void unmarshal(DataInputStream dis)
{
    try {
        // Read the pdu header to determine the pdu length
        byte[] header = new byte[12];
        dis.mark(header.length);
        dis.read(header, 0, header.length);
        pduLength = (int) ByteBuffer.wrap(header).getShort(8);
        dis.reset();
        
        // Now allocate enough space for full pdu
        byte[] pdu = new byte[pduLength];
        dis.read(pdu, 0, pduLength);
        unmarshal(ByteBuffer.wrap(pdu));
    } catch (IOException ex) {
        Logger.getLogger(EntityStatePdu.class.getName()).log(Level.SEVERE, null, ex);
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
       buff.put( (byte)protocolVersion);
       buff.put( (byte)exerciseID);
       buff.put( (byte)pduType);
       buff.put( (byte)protocolFamily);
       buff.putInt( (int)timestamp);
       buff.putShort( (short)this.getLength());
       buff.putShort( (short)padding);
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
       protocolVersion = (short)(toUnsignedInt(buff.get()));
       exerciseID = (short)(toUnsignedInt(buff.get()));
       pduType = (short)(toUnsignedInt(buff.get()));
       protocolFamily = (short)(toUnsignedInt(buff.get()));
       timestamp = buff.getInt();
       pduLength = toUnsignedInt(buff.getShort());
       padding = buff.getShort();
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
    
    /**
     * A convieneince method to marshal to a byte array with the timestamp set to
     * the DIS standard for absolute timestamps (which works only if the host is
     * slaved to NTP). This means the timestamp will roll over every hour.
     * @return IEEE format byte array, with the timestamp set to the current DIS time
     */
    public byte[] marshalWithDisAbsoluteTimestamp() {
        DisTime disTime = DisTime.getInstance();
        this.setTimestamp(disTime.getDisAbsoluteTimestamp());
        return this.marshal();
    }
    
    public void marshalWithDisAbsoluteTimestamp(java.nio.ByteBuffer buff) {
        DisTime disTime = DisTime.getInstance();
        this.setTimestamp(disTime.getDisAbsoluteTimestamp());
        this.marshal(buff);
    }
    
    
    /**
     * A convieneince method to marshal to a byte array with the timestamp set to
     * the DIS standard for relative timestamps. The timestamp will roll over every
     * hour
     * @return IEEE format byte array, with the timestamp set to relative DIS time
     */
    public byte[] marshalWithDisRelativeTimestamp() {
        DisTime disTime = DisTime.getInstance();
        this.setTimestamp(disTime.getDisRelativeTimestamp());
        return this.marshal();
    }
    
    public void marshalWithDisRelativeTimestamp(java.nio.ByteBuffer buff) {
        DisTime disTime = DisTime.getInstance();
        this.setTimestamp(disTime.getDisRelativeTimestamp());
        this.marshal(buff);
    }
    
    /**
     * A convienience method to marshal a PDU using the NPS-specific format for
     * timestamps, which is hundredths of a second since the start of the year.
     * This effectively eliminates the rollover issues from a practical standpoint.
     * @return IEEE format byte array, with the timestamp set to hundredths of a second since the start of the year
     */
    public byte[] marshalWithNpsTimestamp() {
        DisTime disTime = DisTime.getInstance();
        this.setTimestamp(disTime.getNpsTimestamp());
        return this.marshal();
    }
    
    public void marshalWithNpsTimestamp(java.nio.ByteBuffer buff) {
        DisTime disTime = DisTime.getInstance();
        this.setTimestamp(disTime.getNpsTimestamp());
        this.marshal(buff);
    }
    
    /**
     * Another option for marshalling with the timestamp field set automatically. The UNIX
     * time is conventionally seconds since January 1, 1970. UTC time is used, and leap seconds
     * are excluded. This approach is popular in the wild, but the time resolution is not very
     * good for high frequency updates, such as aircraft. An entity updating at 30 PDUs/second
     * would see 30 PDUs sent out with the same timestamp, and have 29 of them discarded as
     * duplicate packets.
     *
     * Note that there are other "Unix times", such milliseconds since 1/1/1970, saved in a long.
     * This cannot be used, since the value is saved in a long. Java's System.getCurrentTimeMillis()
     * uses this value.
     * @return IEEE format byte array, with the timetamp set to seconds since 1970
     */
    public byte[] marshalWithUnixTimestamp() {
        DisTime disTime = DisTime.getInstance();
        this.setTimestamp(disTime.getUnixTimestamp());
        return this.marshal();
    }
    
    public void marshalWithUnixTimestamp(java.nio.ByteBuffer buff) {
        DisTime disTime = DisTime.getInstance();
        this.setTimestamp(disTime.getUnixTimestamp());
        this.marshal(buff);
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

 /**
  * Compare all fields that contribute to the state, ignoring
 transient and static fields, for <code>this</code> and the supplied object
  * @param obj the object to compare to
  * @return true if the objects are equal, false otherwise.
  */
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof Pdu))
        return false;

     final Pdu rhs = (Pdu)obj;

     if( ! (protocolVersion == rhs.protocolVersion)) ivarsEqual = false;
     if( ! (exerciseID == rhs.exerciseID)) ivarsEqual = false;
     if( ! (pduType == rhs.pduType)) ivarsEqual = false;
     if( ! (protocolFamily == rhs.protocolFamily)) ivarsEqual = false;
     if( ! (timestamp == rhs.timestamp)) ivarsEqual = false;
     if( ! (pduLength == rhs.pduLength)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
