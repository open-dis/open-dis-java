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
public class JtidsMidsModulationParameters {

    // Defines the fidelity level to be used (range 0 to 4)
    private short timeSlotAllocationMode;

    // Defines the primary mode of operation of the transmitting terminal
    private short transmittingTerminalPrimaryMode;

    // Defines the secondary mode of operation of the transmitting terminal
    private short transmittingTerminalSecondaryMode;

    // Defines the current synchronization state of the transmitting terminal
    private short synchronizationState;

    // Defines net id based on what TSA mode is selected.
    private int networkSynchronizationID;

    public JtidsMidsModulationParameters() {

    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 1;// Time Slot Allocation Mode
        marshalSize = marshalSize + 1;// Transmitting Terminal Primary Mode
        marshalSize = marshalSize + 1;// Transmitting Terminal Secondary Mode
        marshalSize = marshalSize + 1;// Synchronization State 
        marshalSize = marshalSize + 4;// Network Synchronization ID
        return marshalSize;
    }

    public short getTimeSlotAllocationMode() {
        return timeSlotAllocationMode;
    }

    public void setTimeSlotAllocationMode(short mode) {
        timeSlotAllocationMode = mode;
    }

    public short getTransmittingTerminalPrimaryMode() {
        return transmittingTerminalPrimaryMode;
    }

    public void setTransmittingTerminalPrimaryMode(short mode) {
        transmittingTerminalPrimaryMode = mode;
    }

    public short getTransmittingTerminalSecondaryMode() {
        return transmittingTerminalSecondaryMode;
    }

    public void setTransmittingTerminalSecondaryMode(short mode) {
        transmittingTerminalSecondaryMode = mode;
    }

    public short getSynchronizationState() {
        return synchronizationState;
    }

    public void setSynchronizationState(short state) {
        synchronizationState = state;
    }

    public int getNetworkSynchronizationID() {
        return networkSynchronizationID;
    }

    public void setNetworkSynchronizationID(int id) {
        networkSynchronizationID = id;
    }

    public void marshal(DataOutputStream dos) {
        try {
            dos.writeByte((byte) timeSlotAllocationMode);
            dos.writeByte((byte) transmittingTerminalPrimaryMode);
            dos.writeByte((byte) transmittingTerminalSecondaryMode);
            dos.writeByte((byte) synchronizationState);
            dos.writeInt((int) networkSynchronizationID);

        } catch (IOException e) {
            System.out.println("" + e);
        }
    }

    public void unmarshal(DataInputStream dis) {
        try {
            timeSlotAllocationMode = (short) dis.readByte();
            transmittingTerminalPrimaryMode = (short) dis.readByte();
            transmittingTerminalSecondaryMode = (short) dis.readByte();
            synchronizationState = (short) dis.readByte();
            networkSynchronizationID = (int) dis.readInt();
        } catch (IOException e) {
            System.out.println("" + e);
        }
    }

    public void marshal(java.nio.ByteBuffer buff) {
        buff.put((byte) timeSlotAllocationMode);
        buff.put((byte) transmittingTerminalPrimaryMode);
        buff.put((byte) transmittingTerminalSecondaryMode);
        buff.put((byte) synchronizationState);
        buff.putInt((int) networkSynchronizationID);
    }

    public void unmarshal(java.nio.ByteBuffer buff) {
        timeSlotAllocationMode = (byte) (buff.get() & 0xFF);
        transmittingTerminalPrimaryMode = (byte) (buff.get() & 0xFF);
        transmittingTerminalSecondaryMode = (byte) (buff.get() & 0xFF);
        synchronizationState = (byte) (buff.get() & 0xFF);
        networkSynchronizationID = (int) (buff.getInt() & 0xFFFFFFFF);
    }

    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof JtidsMidsModulationParameters)) {
            return false;
        }
        final JtidsMidsModulationParameters rhs = (JtidsMidsModulationParameters) obj;
        if (!(timeSlotAllocationMode == rhs.getTimeSlotAllocationMode())) {
            ivarsEqual = false;
        }
        if (!(transmittingTerminalPrimaryMode == rhs.getTransmittingTerminalPrimaryMode())) {
            ivarsEqual = false;
        }
        if (!(transmittingTerminalSecondaryMode == rhs.getTransmittingTerminalSecondaryMode())) {
            ivarsEqual = false;
        }
        if (!(synchronizationState == rhs.getSynchronizationState())) {
            ivarsEqual = false;
        }
        if (!(networkSynchronizationID == rhs.getNetworkSynchronizationID())) {
            ivarsEqual = false;
        }
        return ivarsEqual;
    }
}
