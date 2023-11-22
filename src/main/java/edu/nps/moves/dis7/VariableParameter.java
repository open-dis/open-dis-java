package edu.nps.moves.dis7;

import java.io.*;

/**
 * specification of additional information associated with an entity or
 * detonation, not otherwise accounted for in a PDU 6.2.94.1
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class VariableParameter extends Object implements Serializable {
    /**
     * the identification of the Variable Parameter record. Enumeration from EBV
     */
    protected short recordType;

    public VariableParameter() {
    }

        public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 1;  // recordType
        return marshalSize;
    }
    
    public void setRecordType(short pRecordType) {
        recordType = pRecordType;
    }

    public short getRecordType() {
        return recordType;
    } 
    
    public void marshal(DataOutputStream dos) {
        try {
            dos.writeByte((byte) recordType);
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        try {
            recordType = (short) dis.readUnsignedByte();
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
        buff.put((byte) recordType);
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
        recordType = (short) (buff.get() & 0xFF);
    }     
} // end of class
