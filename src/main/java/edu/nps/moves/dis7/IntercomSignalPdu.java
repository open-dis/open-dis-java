package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;

/**
 * Actual transmission of intercome voice data. Section 7.7.5. COMPLETE
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class IntercomSignalPdu extends RadioCommunicationsFamilyPdu implements Serializable {

    /**
     * entity ID
     */
    protected EntityID entityID = new EntityID();

    /**
     * ID of communications device
     */
    protected int communicationsDeviceID;

    /**
     * encoding scheme
     */
    protected int encodingScheme;

    /**
     * tactical data link type
     */
    protected int tdlType;

    /**
     * sample rate
     */
    protected long sampleRate;

    /**
     * data length
     */
    protected int dataLength;

    /**
     * samples
     */
    protected int samples;

    /**
     * data bytes
     */
    protected byte[] data = new byte[0];

    /**
     * Constructor
     */
    public IntercomSignalPdu() {
        setPduType((short) 31);
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = super.getMarshalledSize();
        marshalSize = marshalSize + entityID.getMarshalledSize(); // entityID
        marshalSize = marshalSize + 2; // communicationsDeviceID
        marshalSize = marshalSize + 2; // encodingScheme
        marshalSize = marshalSize + 2; // tdlType
        marshalSize = marshalSize + 4; // sampleRate
        marshalSize = marshalSize + 2; // dataLength
        marshalSize = marshalSize + 2; // samples
        marshalSize = marshalSize + data.length;

        switch (data.length % 4) {
            case 0:
                break;// No padding needed
            case 1:
                marshalSize = marshalSize + 3;
                break;// adding 3 byte padding
            case 2:
                marshalSize = marshalSize + 2;
                break;// adding 2 byte padding
            case 3:
                marshalSize = marshalSize + 1;
                break;// adding 1 byte padding
        }
        return marshalSize;
    }

    public void setEntityID(EntityID pEntityID) {
        entityID = pEntityID;
    }

    public EntityID getEntityID() {
        return entityID;
    }

    public void setCommunicationsDeviceID(int pCommunicationsDeviceID) {
        communicationsDeviceID = pCommunicationsDeviceID;
    }

    public int getCommunicationsDeviceID() {
        return communicationsDeviceID;
    }

    public void setEncodingScheme(int pEncodingScheme) {
        encodingScheme = pEncodingScheme;
    }

    public int getEncodingScheme() {
        return encodingScheme;
    }

    public void setTdlType(int pTdlType) {
        tdlType = pTdlType;
    }

    public int getTdlType() {
        return tdlType;
    }

    public void setSampleRate(long pSampleRate) {
        sampleRate = pSampleRate;
    }

    public long getSampleRate() {
        return sampleRate;
    }

    /**
     * IAW IEEE 1278.1, this field shall specify the number of **bits** of digital
     * voice audio or digital data being sent in this Signal PDU.
     */
    public short getDataLength() {
        if (dataLength == 0) {
            return (short) (data.length * 8);
        }
        return (short) dataLength;
    }

    public void setDataLength(int pDataLength) {
        dataLength = pDataLength;
    }

    public void setSamples(int pSamples) {
        samples = pSamples;
    }

    public int getSamples() {
        return samples;
    }

    public void setData(byte[] pData) {
        data = pData;
    }

    public byte[] getData() {
        return data;
    }

    public void marshal(DataOutputStream dos) {
        super.marshal(dos);
        try {
            entityID.marshal(dos);
            dos.writeShort((short) communicationsDeviceID);
            dos.writeShort((short) encodingScheme);
            dos.writeShort((short) tdlType);
            dos.writeInt((int) sampleRate);
            dos.writeShort((short) dataLength);
            dos.writeShort((short) samples);
            dos.write(data);

            int nrOfBytes = dataLength / Byte.SIZE;
            int paddingBytes = nrOfBytes % 4;// Padding to hit 32 bit boundry
            switch (paddingBytes) {
                case 0:
                    break;// No padding needed
                case 1:
                    dos.write((byte) 0);
                    dos.writeShort((short) 0);
                    break;// adding 3 byte padding
                case 2:
                    dos.writeShort((short) 0);
                    break;// adding 2 byte padding
                case 3:
                    dos.write((byte) 0);
                    break;// adding 1 byte padding
            }
        } // end try
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        super.unmarshal(dis);

        try {
            entityID.unmarshal(dis);
            communicationsDeviceID = (int) dis.readUnsignedShort();
            encodingScheme = (int) dis.readUnsignedShort();
            tdlType = (int) dis.readUnsignedShort();
            sampleRate = dis.readInt();
            dataLength = (int) dis.readUnsignedShort();
            samples = (int) dis.readUnsignedShort();
            data = new byte[dataLength / Byte.SIZE];
            dis.read(data);
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
        super.marshal(buff);
        entityID.marshal(buff);
        buff.putShort((short) communicationsDeviceID);
        buff.putShort((short) encodingScheme);
        buff.putShort((short) tdlType);
        buff.putInt((int) sampleRate);
        buff.putShort((short) dataLength);
        buff.putShort((short) samples);
        buff.put(data);

        int nrOfBytes = dataLength / Byte.SIZE;
        int paddingBytes = nrOfBytes % 4;// Padding to hit 32 bit boundry
        switch (paddingBytes) {
            case 0:
                break;// No padding needed
            case 1:
                buff.put((byte) 0);
                buff.putShort((short) 0);
                break;// adding 3 byte padding
            case 2:
                buff.putShort((short) 0);
                break;// adding 2 byte padding
            case 3:
                buff.put((byte) 0);
                break;// adding 1 byte padding
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
        super.unmarshal(buff);

        entityID.unmarshal(buff);
        communicationsDeviceID = (int) (buff.getShort() & 0xFFFF);
        encodingScheme = (int) (buff.getShort() & 0xFFFF);
        tdlType = (int) (buff.getShort() & 0xFFFF);
        sampleRate = buff.getInt();
        dataLength = (int) (buff.getShort() & 0xFFFF);
        samples = (int) (buff.getShort() & 0xFFFF);
        data = new byte[dataLength / Byte.SIZE];
        buff.get(data);
    } // end of unmarshal method

    /*
     * The equals method doesn't always work--mostly it works only on classes that
     * consist only of primitives. Be careful.
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

    @Override
    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof IntercomSignalPdu)) {
            return false;
        }

        final IntercomSignalPdu rhs = (IntercomSignalPdu) obj;

        if (!(entityID.equals(rhs.entityID))) {
            ivarsEqual = false;
        }
        if (!(communicationsDeviceID == rhs.communicationsDeviceID)) {
            ivarsEqual = false;
        }
        if (!(encodingScheme == rhs.encodingScheme)) {
            ivarsEqual = false;
        }
        if (!(tdlType == rhs.tdlType)) {
            ivarsEqual = false;
        }
        if (!(sampleRate == rhs.sampleRate)) {
            ivarsEqual = false;
        }
        if (!(dataLength == rhs.dataLength)) {
            ivarsEqual = false;
        }
        if (!(samples == rhs.samples)) {
            ivarsEqual = false;
        }
        if (!(Arrays.equals(data, rhs.data))) {
            ivarsEqual = false;
        }

        return ivarsEqual && super.equalsImpl(rhs);
    }
} // end of class
