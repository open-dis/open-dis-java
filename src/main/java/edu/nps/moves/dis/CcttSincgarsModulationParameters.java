/*
 * Copyright (c) 2022, The Moves Institute
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.nps.moves.dis;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author fo
 */
public class CcttSincgarsModulationParameters {

    // This field shall identify the frequency hopping network 
    private short fhNetId;

    // This field shall identify the set of frequencies used when creating a hopping pattern.
    private short hopSetId;

    //This field shall identify the set of frequencies that are excluded from the hopping pattern.
    private short lockoutSetId;

    //This field shall specify whether the radio is starting or continuing a transmission
    private short startOfMessage;

    private short reserved;

    //This field shall identify the offset to exercise time in seconds for the clock in the SINCGARS radio.
    private int fhSynchronizationTimeOffset;

    //This field shall identify the transmission security key that is used when generating the hopping pattern.
    private short transmissionSecurityKey;

    //This field shall specify that the transmission is not subject to propagation loss, interference, comsec or any other form of signal degradation.
    private short clearChannel;

    public CcttSincgarsModulationParameters() {

    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 2;// FH Net ID
        marshalSize = marshalSize + 2;// Hop Set Id
        marshalSize = marshalSize + 2;// Lockou Set ID
        marshalSize = marshalSize + 1;// Start of Message 
        marshalSize = marshalSize + 1;// Reserved
        marshalSize = marshalSize + 4;// FH Synchronization Time Offset
        marshalSize = marshalSize + 2;// Transmission Security key
        marshalSize = marshalSize + 1;// Clear Channel

        return marshalSize;
    }

    public short getFhNetId() {
        return fhNetId;
    }

    public void setFhNetId(short id) {
        fhNetId = id;
    }

    public short getHopSetId() {
        return hopSetId;
    }

    public void setHopSetId(short id) {
        hopSetId = id;
    }

    public short getLockoutSetId() {
        return lockoutSetId;
    }

    public void setLockoutSetId(short id) {
        lockoutSetId = id;
    }

    public short getStartOfMessage() {
        return startOfMessage;
    }

    public void setStartOfMessage(short som) {
        startOfMessage = som;
    }

    public short getReserved() {
        return reserved;
    }

    public void setReserved(short r) {
        reserved = r;
    }

    public int getFhSynchronizationTimeOffset() {
        return fhSynchronizationTimeOffset;
    }

    public void setFhSynchronizationTimeOffset(int offSet) {
        fhSynchronizationTimeOffset = offSet;
    }

    public short getTransmissionSecurityKey() {
        return transmissionSecurityKey;
    }

    public void setTransmissionSecurityKey(short key) {
        transmissionSecurityKey = key;
    }

    public short getClearChannel() {
        return clearChannel;
    }

    public void setClearChannel(short channel) {
        clearChannel = channel;
    }

    public void marshal(DataOutputStream dos) {
        try {
            dos.writeShort((short) fhNetId);
            dos.writeShort((short) hopSetId);
            dos.writeShort((short) lockoutSetId);
            dos.writeByte((byte) startOfMessage);
            dos.writeByte((byte) reserved);
            dos.writeInt((int) fhSynchronizationTimeOffset);
            dos.writeShort((short) transmissionSecurityKey);
            dos.writeByte((byte) clearChannel);
        } catch (IOException e) {
            System.out.println("" + e);
        }
    }

    public void unmarshal(DataInputStream dis) {
        try {
            fhNetId = (short) dis.readShort();
            hopSetId = (short) dis.readShort();
            lockoutSetId = (short) dis.readShort();
            startOfMessage = (byte) dis.readByte();
            reserved = (byte) dis.readByte();
            fhSynchronizationTimeOffset = (int) dis.readInt();
            transmissionSecurityKey = (short) dis.readShort();
            clearChannel = (byte) dis.readByte();
        } catch (IOException e) {
            System.out.println("" + e);
        }
    }

    public void marshal(java.nio.ByteBuffer buff) {
        buff.putShort((short) fhNetId);
        buff.putShort((short) hopSetId);
        buff.putShort((short) lockoutSetId);
        buff.put((byte) startOfMessage);
        buff.put((byte) reserved);
        buff.putInt((int) fhSynchronizationTimeOffset);
        buff.putShort((short) transmissionSecurityKey);
        buff.put((byte) clearChannel);
    }

    public void unmarshal(java.nio.ByteBuffer buff) {
        fhNetId = (short) (buff.getShort() & 0xFFFF);
        hopSetId = (short) (buff.getShort() & 0xFFFF);
        lockoutSetId = (short) (buff.getShort() & 0xFFFF);
        startOfMessage = (byte) (buff.get() & 0xFF);
        reserved = (byte) (buff.get() & 0xFF);
        fhSynchronizationTimeOffset = (int) (buff.getInt() & 0xFFFFFFFF);
        transmissionSecurityKey = (short) (buff.getShort() & 0xFFFF);
        clearChannel = (short) (buff.getShort() & 0xFFFF);
    }

    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof CcttSincgarsModulationParameters)) {
            return false;
        }

        final CcttSincgarsModulationParameters rhs = (CcttSincgarsModulationParameters) obj;

        if (!(fhNetId == rhs.getFhNetId())) {
            ivarsEqual = false;
        }
        if (!(hopSetId == rhs.getHopSetId())) {
            ivarsEqual = false;
        }
        if (!(lockoutSetId == rhs.getLockoutSetId())) {
            ivarsEqual = false;
        }
        if (!(startOfMessage == rhs.getStartOfMessage())) {
            ivarsEqual = false;
        }
        if (!(reserved == rhs.getReserved())) {
            ivarsEqual = false;
        }
        if (!(fhSynchronizationTimeOffset == rhs.getFhSynchronizationTimeOffset())) {
            ivarsEqual = false;
        }
        if (!(transmissionSecurityKey == rhs.getTransmissionSecurityKey())) {
            ivarsEqual = false;
        }
        if (!(clearChannel == rhs.getClearChannel())) {
            ivarsEqual = false;
        }

        return ivarsEqual;
    }
}
