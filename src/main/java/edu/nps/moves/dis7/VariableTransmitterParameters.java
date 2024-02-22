package edu.nps.moves.dis7;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Relates to radios. NOT COMPLETE. Section 6.2.94
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class VariableTransmitterParameters extends Object implements Serializable {

    /**
     * Type of VTP. Enumeration from EBV
     */
    protected long recordType;

    /**
     * Length, in bytes
     */
    protected long recordLength = (long) 4;

    /**
     * transmitter parameters
     */
    protected List<Byte> recordSpecificFieldsList = new ArrayList<>();

    private final int RECORD_TYPE_FIELD_SIZE = 4;
    
    private final int RECORD_LENGTH_FIELD_SIZE = 2;
    /**
     * Constructor
     */
    public VariableTransmitterParameters() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 4;  // recordType
        marshalSize = marshalSize + 2;  // recordLength
        marshalSize = marshalSize + recordSpecificFieldsList.size(); // recordParameters
        int remainder = marshalSize % 8;
        if (remainder > 0) {
            marshalSize = marshalSize + calculatePaddingByteNr(remainder);
        }

        return marshalSize;
    }

    public void setRecordType(long pRecordType) {
        recordType = pRecordType;
    }

    public long getRecordType() {
        return recordType;
    }

    public void setRecordLength(long pRecordLength) {
        recordLength = pRecordLength;
    }

    public long getRecordLength() {
        return recordLength;
    }

    public List<Byte> getRecordSpecificFieldsList() {
        return recordSpecificFieldsList;
    }

    public void setRecordSpecificFieldsList(List<Byte> recordSpecificFieldsList) {
        this.recordSpecificFieldsList = recordSpecificFieldsList;
    }

    private int calculatePaddingByteNr(final int remainder) {
        int paddingByteNr = 0;
        switch (remainder) {
            case 1:
                paddingByteNr = 7;
                break;
            case 2:
                paddingByteNr = 6;
                break;
            case 3:
                paddingByteNr = 5;
                break;
            case 4:
                paddingByteNr = 4;
                break;
            case 5:
                paddingByteNr = 3;
                break;
            case 6:
                paddingByteNr = 2;
                break;
            case 7:
                paddingByteNr = 1;
                break;
        }
        return paddingByteNr;
    }

    public void marshal(DataOutputStream dos) {
        try {
            dos.writeInt((int) recordType);
            dos.writeShort((int) getMarshalledSize());
            Iterator<Byte> iter = recordSpecificFieldsList.iterator();
            while (iter.hasNext()) {
                byte nextByte = iter.next();
                dos.writeByte(nextByte);
            }
            final int remainder = (recordSpecificFieldsList.size() + RECORD_TYPE_FIELD_SIZE + RECORD_LENGTH_FIELD_SIZE) % 8;
            if (remainder > 0) {
                int paddingByteNr = 0;
                paddingByteNr = calculatePaddingByteNr(remainder);
                for (int i = 0; i < paddingByteNr; i++) {
                    dos.writeByte(0);
                }
            }
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        try {
            recordType = dis.readInt();
            recordLength = dis.readShort();
            final long dataLength = recordLength - RECORD_TYPE_FIELD_SIZE - RECORD_LENGTH_FIELD_SIZE;
            for (int i = 0; i < (dataLength); i++) {
                byte nextByte = dis.readByte();
                recordSpecificFieldsList.add(nextByte);
            }
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
        buff.putShort((short) getMarshalledSize());
        Iterator<Byte> iter = recordSpecificFieldsList.iterator();
        while (iter.hasNext()) {
            byte nextByte = iter.next();
            buff.put(nextByte);
        }
        final int remainder = recordSpecificFieldsList.size() % 8;
        if (remainder > 0) {
            int paddingByteNr = 0;
            paddingByteNr = calculatePaddingByteNr(remainder);
            for (int i = 0; i < paddingByteNr; i++) {
                buff.put((byte) 0);
            }
        }
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
        recordLength = buff.getShort();
        final long dataLength = recordLength - RECORD_TYPE_FIELD_SIZE - RECORD_LENGTH_FIELD_SIZE;
        for (int i = 0; i < dataLength; i++) {
            byte nextByte = buff.get();
            recordSpecificFieldsList.add(nextByte);
        }
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

        if (!(obj instanceof VariableTransmitterParameters)) {
            return false;
        }

        final VariableTransmitterParameters rhs = (VariableTransmitterParameters) obj;

        if (!(recordType == rhs.recordType)) {
            ivarsEqual = false;
        }
        if (!(recordLength == rhs.recordLength)) {
            ivarsEqual = false;
        }

        for (int idx = 0; idx < recordSpecificFieldsList.size(); idx++) {
            if (!(recordSpecificFieldsList.get(idx).equals(rhs.recordSpecificFieldsList.get(idx)))) {
                ivarsEqual = false;
            }
        }

        return ivarsEqual;
    }
} // end of class
