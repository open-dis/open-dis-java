package edu.nps.moves.dis;

import java.util.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Section 5.2.32. Variable Datum Record
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class VariableDatum extends Object implements Serializable {

    /**
     * ID of the variable datum
     */
    protected long variableDatumID;

    /**
     * length of the variable datums, in bits. Note that this is not
     * programmatically tied to the size of the variableData. The variable data
     * field may be 64 bits long but only 16 bits of it could actually be used.
     */
    protected long variableDatumLength;

    /**
     * The data payload
     */
    private byte[] payload = EMPTY;
    /**
     * The padding required to 64 bit boundary
     */
    private byte[] padding = EMPTY;
    private static final byte[] EMPTY = new byte[0];

    /**
     * Constructor
     */
    public VariableDatum() {
    }

    // additional constructors + payload setting
    public VariableDatum(long id, double d) {
        this.variableDatumID = id;
        setPayload(d);
    }

    public VariableDatum(long id, long l) {
        this.variableDatumID = id;
        setPayload(l);
    }

    public VariableDatum(long id, String s) {
        this.variableDatumID = id;
        setPayload(s);
    }

    public VariableDatum(long id, byte[] payload) {
        this.variableDatumID = id;
        setPayload(payload);
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 4;  // variableDatumID
        marshalSize = marshalSize + 4;  // variableDatumLength
        marshalSize = marshalSize + payload.length; // payload length
        marshalSize = marshalSize + padding.length; // padding

        return marshalSize;
    }

    public void setVariableDatumID(long pVariableDatumID) {
        variableDatumID = pVariableDatumID;
    }

    public long getVariableDatumID() {
        return variableDatumID;
    }

    /**
     * Return length of the variable datum, in bytes. Does not account for
     * padding
     */
    public long getVariableDatumLength() {
        return (long) variableDatumLength;       // * Byte.SIZE;
    }

    /**
     * Note that setting this value will not change the marshalled value. The
     * list whose length this describes is used for that purpose. The
     * getvariableDatumLength method will also be based on the actual list
     * length rather than this value. The method is simply here for java bean
     * completeness.
     */
    public void setVariableDatumLength(long pVariableDatumLength) {
        variableDatumLength = pVariableDatumLength;
    }

    public void setVariableData(byte[] pVariableData) {
        setPayload(pVariableData);
    }

    public byte[] getVariableData() {
        return getPayload();
    }

    public double getPayloadAsDouble() {
        return Double.longBitsToDouble(getPayloadAsLong());
    }

    public long getPayloadAsLong() {
        return (((long) payload[0] << 56)
                + ((long) (payload[1] & 255) << 48)
                + ((long) (payload[2] & 255) << 40)
                + ((long) (payload[3] & 255) << 32)
                + ((long) (payload[4] & 255) << 24)
                + ((payload[5] & 255) << 16)
                + ((payload[6] & 255) << 8)
                + (payload[7] & 255));
    }

    public String getPayloadAsString() {
        return new String(payload);
    }

    public byte[] getPayload() {
        return payload;
    }

    /**
     * Sets the payload as a double
     *
     * @param d
     * @return True if valid, False otherwise
     */
    public final boolean setPayload(double d) {
        return setPayload(Double.doubleToRawLongBits(d));
    }

    /**
     * Sets the payload as a long
     *
     * @param l
     * @return True if valid, False otherwise
     */
    public final boolean setPayload(long l) {
        byte[] bytes = new byte[8];

        bytes[0] = (byte) (l >>> 56);
        bytes[1] = (byte) (l >>> 48);
        bytes[2] = (byte) (l >>> 40);
        bytes[3] = (byte) (l >>> 32);
        bytes[4] = (byte) (l >>> 24);
        bytes[5] = (byte) (l >>> 16);
        bytes[6] = (byte) (l >>> 8);
        bytes[7] = (byte) (l);

        return setPayload(bytes);
    }

    /**
     * Sets the payload as a String
     *
     * @param s
     * @return True if valid, False otherwise
     */
    public final boolean setPayload(String s) {
        if (s != null) {
            return setPayload(s.getBytes(Charset.forName("US-ASCII")));
        } else {
            return false;
        }
    }

    /**
     * Sets the payload
     *
     * @param payload
     * @return True if valid, False otherwise
     */
    public final boolean setPayload(byte[] payload) {
        if (payload != null) {
            return setPayload(payload, payload.length * 8);
        } else {
            return false;
        }
    }

    /**
     * Sets the payload
     *
     * @param payload
     * @param lengthBits
     * @return True if valid, False otherwise
     */
    public final boolean setPayload(byte[] payload, long lengthBits) {
        if (payload != null) {
            int payloadBits = payload.length * 8;

            if (lengthBits >= 0 && lengthBits <= payloadBits && lengthBits > payloadBits - 8) {
                this.payload = payload;
                this.variableDatumLength = lengthBits;

                padding = createPadding(payload.length);

                return true;
            }
        }

        return false;
    }

    /**
     * Creates a byte array of padding based on the input
     *
     * @param payloadBytes
     * @return The proper padding array
     */
    private static byte[] createPadding(int payloadBytes) {
        int mod = payloadBytes % 8;

        if (mod != 0) {
            return new byte[8 - mod];
        } else {
            return EMPTY;
        }
    }

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
        buff.putInt((int) variableDatumID);
        buff.putInt((int) getVariableDatumLength());

        buff.put(payload);
        buff.put(padding);
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
        unmarshalPayloadLenght(buff, false);
    } // end of unmarshal method 

    public void unmarshalPayloadLenght(ByteBuffer buff, boolean removeIdAndLengthFieldsFromVaribleDatumLength) {
        variableDatumID = buff.getInt();
        variableDatumLength = buff.getInt();
        int payloadBytes = (int) (variableDatumLength / 8);
        if (removeIdAndLengthFieldsFromVaribleDatumLength) {
            payloadBytes -= 8;
        }
        if (variableDatumLength % 8 > 0) {
            payloadBytes++;
        }

        payload = new byte[payloadBytes];
        buff.get(payload);

        padding = createPadding(payloadBytes);
        buff.get(padding);
    }


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

        if (!(obj instanceof VariableDatum)) {
            return false;
        }

        final VariableDatum rhs = (VariableDatum) obj;

        if (!(variableDatumID == rhs.variableDatumID)) {
            ivarsEqual = false;
        }
        if (!(variableDatumLength == rhs.variableDatumLength)) {
            ivarsEqual = false;
        }
        if (!(Arrays.equals(payload, rhs.payload))) {
            ivarsEqual = false;
        }
        if (!(Arrays.equals(padding, rhs.padding))) {
            ivarsEqual = false;
        }

        return ivarsEqual;
    }

} // end of class
