package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;

/**
 * repeating element if IFF Data specification record
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class IFFData extends Object implements Serializable {

    /**
     * enumeration for type of record
     */
    protected long recordType;

    /**
     * length of record. Should be padded to 32 bit boundary.
     */
    protected int recordLength;

    /**
     * IFF data.
     */
    protected byte[] iffData = new byte[0];

    /**
     * Constructor
     */
    public IFFData() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 4;  // recordType
        marshalSize = marshalSize + 2;  // recordLength
        marshalSize = marshalSize + iffData.length;

        return marshalSize;
    }

    public void setRecordType(long pRecordType) {
        recordType = pRecordType;
    }

    public long getRecordType() {
        return recordType;
    }

    public int getRecordLength() {
        return iffData.length;
    }

    /**
     * Note that setting this value will not change the marshalled value. The
     * list whose length this describes is used for that purpose. The
     * getrecordLength method will also be based on the actual list length
     * rather than this value. The method is simply here for java bean
     * completeness.
     */
    public void setRecordLength(int pRecordLength) {
        recordLength = pRecordLength;
    }

    public void setIffData(byte[] pIffData) {
        iffData = pIffData;
    }

    public byte[] getIffData() {
        return iffData;
    }

    public void marshal(DataOutputStream dos) {
        try {
            dos.writeInt((int) recordType);
            dos.writeShort((short) iffData.length);
            dos.write(iffData);
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        try {
            recordType = dis.readInt();
            recordLength = (int) dis.readUnsignedShort();
            iffData = new byte[recordLength];
            dis.read(iffData);
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of unmarshal method 

    /**
     * Packs a Pdu into the ByteBuffer.
     *
     * @throws java.nio.BufferOverflowException if buff is too small
     * @throws java.nio.ReadOnlyBufferException if buff is read only
     * @see java.nio.ByteBuffer
     * @param buff The ByteBuffer at the position to begin writing
     * @since ??
     */
    public void marshal(java.nio.ByteBuffer buff) {
        buff.putInt((int) recordType);
        buff.putShort((short) iffData.length);
        buff.put(iffData);
    } // end of marshal method

    /**
     * Unpacks a Pdu from the underlying data.
     *
     * @throws java.nio.BufferUnderflowException if buff is too small
     * @see java.nio.ByteBuffer
     * @param buff The ByteBuffer at the position to begin reading
     * @since ??
     */
    public void unmarshal(java.nio.ByteBuffer buff) {
        recordType = buff.getInt();
        recordLength = (int) (buff.getShort() & 0xFFFF);
        iffData = new byte[recordLength];
        buff.get(iffData);
    } // end of unmarshal method 


    /*
  * The equals method doesn't always work--mostly it works only on classes that consist only of primitives. Be careful.
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        return equalsImpl(obj);
    }

    /**
     * Compare all fields that contribute to the state, ignoring transient and
     * static fields, for <code>this</code> and the supplied object
     *
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise.
     */
    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof IFFData)) {
            return false;
        }

        final IFFData rhs = (IFFData) obj;

        if (!(recordType == rhs.recordType)) {
            ivarsEqual = false;
        }
        if (!(recordLength == rhs.recordLength)) {
            ivarsEqual = false;
        }
        if (!(Arrays.equals(iffData, rhs.iffData))) {
            ivarsEqual = false;
        }

        return ivarsEqual;
    }
} // end of class
