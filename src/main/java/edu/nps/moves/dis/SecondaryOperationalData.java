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

import java.io.DataInput;
import java.io.DataOutput;
import java.nio.ByteBuffer;

/**
 *
 * @author fo-ifad
 */
public class SecondaryOperationalData {

    /**
     * Additional characteristics of the IFF/ATC/NAVAIDS emitter system
     */
    protected short parameter1;

    /**
     * Additional characteristics of the IFF/ATC/NAVAIDS emitter system
     */
    protected short parameter2;

    /**
     * Number of Fundamental Parameter data sets
     */
    protected int numberofFundamentalParameterDataSets;

    public SecondaryOperationalData() {

    }

    public int getMarshalledSize() {

        int marshalSize = 0;

        marshalSize = marshalSize + 1;  // parameter1
        marshalSize = marshalSize + 1;  // parameter2
        marshalSize = marshalSize + 2;  // numberofFundamentalParameterDataSets

        return marshalSize;
    }

    public void setParameter1(short pParameter1) {
        this.parameter1 = pParameter1;
    }

    public short getParameter1() {
        return this.parameter1;
    }

    public void setParameter2(short pParameter2) {
        this.parameter2 = pParameter2;
    }

    public short getParameter2() {
        return this.parameter2;
    }

    public void setNumberofFundamentalParameterDataSets(int pNumberofFundamentalParameterDataSets) {
        this.numberofFundamentalParameterDataSets = pNumberofFundamentalParameterDataSets;
    }

    public int getNumberofFundamentalParameterDataSets() {
        return this.numberofFundamentalParameterDataSets;
    }

    public void marshal(DataOutput dos) {
        try {
            dos.writeByte((byte) parameter1);
            dos.writeByte((byte) parameter2);
            dos.writeShort((short) numberofFundamentalParameterDataSets);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void unmarshal(DataInput dis) {

        try {
            this.parameter1 = (short) dis.readUnsignedByte();
            this.parameter2 = (short) dis.readUnsignedByte();
            this.numberofFundamentalParameterDataSets = (int) dis.readUnsignedShort();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Packs a Pdu into the ByteBuffer.
     *
     * @throws java.nio.BufferOverflowException if buff is too small
     * @throws java.nio.ReadOnlyBufferException if buff is read only
     * @see java.nio.ByteBuffer
     * @param buff The ByteBuffer at the position to begin writing
     */
    public void marshal(ByteBuffer buff) {

        buff.put((byte) parameter1);
        buff.put((byte) parameter2);
        buff.putShort((short) numberofFundamentalParameterDataSets);
    }

    /**
     * Unpacks a Pdu from the underlying data.
     *
     * @throws java.nio.BufferUnderflowException if buff is too small
     * @see java.nio.ByteBuffer
     * @param buff The ByteBuffer at the position to begin reading
     */
    public void unmarshal(ByteBuffer buff) {

        this.parameter1 = (short) (buff.get() & 0xFF);
        this.parameter2 = (short) (buff.get() & 0xFF);
        this.numberofFundamentalParameterDataSets = (int) (buff.getShort() & 0xFFFF);
    }

    /**
     * The equals method doesn't always work--mostly it works only on classes
     * that consist only of primitives. Be careful.
     */
    public boolean equals(SecondaryOperationalData rhs) {

        boolean ivarsEqual = true;

        if (rhs.getClass() != this.getClass()) {
            return false;
        }

        if (!(parameter1 == rhs.parameter1)) {
            ivarsEqual = false;
        }
        if (!(parameter2 == rhs.parameter2)) {
            ivarsEqual = false;
        }
        if (!(numberofFundamentalParameterDataSets == rhs.numberofFundamentalParameterDataSets)) {
            ivarsEqual = false;
        }

        return ivarsEqual;
    }
}
