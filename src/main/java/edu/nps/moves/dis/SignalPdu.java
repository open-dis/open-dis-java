package edu.nps.moves.dis;

import java.util.*;
import java.io.*;

/**
 * Section 5.3.8.2. Detailed information about a radio transmitter. This PDU
 * requires manually written code to complete. The encodingScheme field can be
 * used in multiple ways, which requires hand-written code to finish. UNFINISHED
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class SignalPdu extends RadioCommunicationsFamilyPdu implements Serializable {

    /**
     * ID of the entity that is the source of the communication, ie contains the
     * radio
     */
    protected EntityID entityId = new EntityID();

    /**
     * particular radio within an entity
     */
    protected int radioId;

    /**
     * encoding scheme used, and enumeration
     */
    protected int encodingScheme;

    /**
     * encoding class: bits 14 - 15 of encodingScheme
     */
    protected int encodingClass = 0;

    /**
     * encoding type or number of TDL messages: bits 0 - 13 of encodingScheme
     */
    protected int encodingType = 0;
    /**
     * tdl type
     */
    protected int tdlType;

    /**
     * sample rate
     */
    protected long sampleRate;

    /**
     * length of data, in bits
     */
    protected int dataLength;

    /**
     * number of samples. If the PDU contains encoded audio, this should be
     * zero.
     */
    protected int samples;

    /**
     * list of eight bit values. Must be padded to fall on a 32 bit boundary.
     */
    protected byte[] data = new byte[0];

    /**
     * Constructor
     */
    public SignalPdu() {
        setPduType((short) 26);
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = super.getMarshalledSize();
        marshalSize = marshalSize + entityId.getMarshalledSize();  // entityId
        marshalSize = marshalSize + 2;  // radioId
        marshalSize = marshalSize + 2;  // encodingScheme
        marshalSize = marshalSize + 2;  // tdlType
        marshalSize = marshalSize + 4;  // sampleRate
        marshalSize = marshalSize + 2;  // dataLength
        marshalSize = marshalSize + 2;  // samples  
        marshalSize = marshalSize + data.length;

        switch (data.length % 4) {
            case 0:
                break;//No padding needed
            case 1:
                marshalSize = marshalSize + 3;
                break;//adding 3 byte padding
            case 2:
                marshalSize = marshalSize + 2;
                break;//adding 2 byte padding
            case 3:
                marshalSize = marshalSize + 1;
                break;//adding 1 byte padding
        }
        return marshalSize;
    }

    public void setEntityId(EntityID pEntityId) {
        entityId = pEntityId;
    }

    public EntityID getEntityId() {
        return entityId;
    }

    public void setRadioId(int pRadioId) {
        radioId = pRadioId;
    }

    public int getRadioId() {
        return radioId;
    }

    public void setEncodingScheme(int pEncodingScheme) {
        encodingScheme = pEncodingScheme;
    }

    public int getEncodingScheme() {
        return encodingScheme;
    }

    public void setEncodingClass(int pEncodingClass) {
        int newEncodingClass = 0;
        int newEncodingScheme = 0;
        // Save encoding class and create the encoding scheme
        encodingClass = pEncodingClass;
        newEncodingClass = pEncodingClass << 14;                 // Move bits 0 - 1 to bit position 14 - 15
        newEncodingScheme = newEncodingClass | this.getEncodingType();
        this.setEncodingScheme(newEncodingScheme);
    }

    public int getEncodingClass() {
        int extractEncodingClass = 0;
        extractEncodingClass = this.getEncodingScheme() & 0xC000;  // Lose bits 0 - 13
        extractEncodingClass = extractEncodingClass >>> 14;                // Move bits 14 - 15 to bit position 0 - 1
        return extractEncodingClass;
    }

    public void setEncodingType(int pEncodingType) {
        int newEncodingScheme = 0;
        // Save encoding type and create the encoding scheme
        encodingType = pEncodingType;
        newEncodingScheme = this.getEncodingScheme() & 0xC000;
        newEncodingScheme = newEncodingScheme | pEncodingType;
        this.setEncodingScheme(newEncodingScheme);  //
    }

    public int getEncodingType() {
        int extractEncodingType = 0;
        extractEncodingType = this.getEncodingScheme() & 0x3FFF; // Lose bits 14 - 15
        return extractEncodingType;
    }

    public void setNumberofTDLMessages(int pEncodingType) {
        int newEncodingScheme = 0;
        // Save number of TDLs and create the encoding scheme
        encodingType = pEncodingType;
        newEncodingScheme = this.getEncodingScheme() & 0xC000; // Lose bits 0 - 13
        newEncodingScheme = newEncodingScheme | pEncodingType;
        this.setEncodingScheme(newEncodingScheme); //

    }

    public int getNumberofTDLMessages() {
        int extractEncodingType = 0;
        extractEncodingType = this.getEncodingScheme() & 0x3FFF; // Lose bits 14 - 15
        return extractEncodingType;

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

    public int getDataLength() {
        return dataLength;
    }

    /**
     * Note that setting this value will not change the marshalled value. The
     * list whose length this describes is used for that purpose. The
     * getdataLength method will also be based on the actual list length rather
     * than this value. The method is simply here for java bean completeness.
     */
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
        entityId.marshal(buff);
        buff.putShort((short) radioId);
        buff.putShort((short) encodingScheme);
        buff.putShort((short) tdlType);
        buff.putInt((int) sampleRate);
        buff.putShort((short) dataLength);
        buff.putShort((short) samples);
        int nrOfBytes = 0;
        nrOfBytes = dataLength / Byte.SIZE;

        buff.put(data);
        int paddingBytes = nrOfBytes % 4;//Padding to hit 32 bit boundry
        switch (paddingBytes) {
            case 0:
                break;//No padding needed
            case 1:
                buff.put((byte) 0);
                buff.putShort((short) 0);
                break;//adding 3 byte padding
            case 2:
                buff.putShort((short) 0);
                break;//adding 2 byte padding
            case 3:
                buff.put((byte) 0);
                break;//adding 1 byte padding
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

        entityId.unmarshal(buff);
        radioId = (int) (buff.getShort() & 0xFFFF);
        encodingScheme = (int) (buff.getShort() & 0xFFFF);
        tdlType = (int) (buff.getShort() & 0xFFFF);
        sampleRate = buff.getInt();
        dataLength = (int) (buff.getShort() & 0xFFFF);
        samples = (int) (buff.getShort() & 0xFFFF);
        final int dataLengthBytes = dataLength / Byte.SIZE;
        data = new byte[dataLengthBytes];
        buff.get(data);
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

    @Override
    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof SignalPdu)) {
            return false;
        }

        final SignalPdu rhs = (SignalPdu) obj;

        if (!(entityId.equals(rhs.entityId))) {
            ivarsEqual = false;
        }
        if (!(radioId == rhs.radioId)) {
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
