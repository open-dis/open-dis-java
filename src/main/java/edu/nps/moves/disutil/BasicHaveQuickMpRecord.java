/*
 * Copyright (c) 2017, The Moves Institute
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
package edu.nps.moves.disutil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Annex C.4 in the IEEE 1278.1-2012 (version 7) of the DIS standard now defines
 * a mechanism for achieving interoperability between HAVEQUICK radios. This
 * mechanism identifies a basic and high-fidelity way of exchanging HAVEQUICK
 * information in the Transmitter PDU. The basic option is based on an earlier,
 * nonstandardized approach used by a number of existing audio solutions under
 * version 6 of DIS
 *
 * @author leif.gruenwoldt
 */
public class BasicHaveQuickMpRecord {

    short mwodIndex;
    int timeOfDay;

    int netIdNetNumber;
    int netIdFrequencyTable;
    int netIdMode;

    public int getMarshalledSize() {
        return 16; // 128 bits
    }

    public short getMwodIndex() {
        return mwodIndex;
    }

    public int getTimeOfDay() {
        return timeOfDay;
    }

    public int getNetIdNetNumber() {
        return netIdNetNumber;
    }

    public int getNetIdFrequencyTable() {
        return netIdFrequencyTable;
    }

    public int getNetIdMode() {
        return netIdMode;
    }

    public void setMwodIndex(short mwodIndex) {
        this.mwodIndex = mwodIndex;
    }

    public void setTimeOfDay(int timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setNetIdNetNumber(int netIdNetNumber) {
        this.netIdNetNumber = netIdNetNumber;
    }

    public void setNetIdFrequencyTable(int netIdFrequencyTable) {
        this.netIdFrequencyTable = netIdFrequencyTable;
    }

    public void setNetIdMode(int netIdMode) {
        this.netIdMode = netIdMode;
    }

    public void unmarshal(List<Short> modulationParmeters) {
        ByteBuffer bb = ByteBuffer.allocate(modulationParmeters.size() * 2);
        for (Short s : modulationParmeters) {
            bb.putShort(s);
        }
        bb.rewind();
        unmarshal(bb);
    }

    public List<Short> marshal() {
        ByteBuffer bb = ByteBuffer.allocate(getMarshalledSize());
        marshal(bb);
        bb.rewind();

        ArrayList<Short> modulationParameters = new ArrayList();

        for (int i = 0; i < getMarshalledSize() / 2; i++) {
            Short s = bb.getShort();
            modulationParameters.add(s);
        }
        return modulationParameters;
    }

    private void marshal(java.nio.ByteBuffer bb) {
        short netIdRecord = 0;
        netIdRecord = (short) (netIdRecord | (netIdMode << 11));
        netIdRecord = (short) (netIdRecord | (netIdFrequencyTable << 9));
        netIdRecord = (short) (netIdRecord | (netIdNetNumber));

        bb.putShort(netIdRecord);
        bb.putShort(mwodIndex);
        bb.putShort((short) 0);
        bb.put((byte) 0);
        bb.put((byte) 0);
        bb.putInt(timeOfDay);
        bb.putInt(0);
    }

    private void unmarshal(java.nio.ByteBuffer bb) {

        short haveQuickNetIdRecord = bb.getShort();
        mwodIndex = bb.getShort();
        short haveQuickReserved1 = bb.getShort();
        Byte haveQuickReserved2 = bb.get();
        Byte haveQuickReserved3 = bb.get();
        timeOfDay = bb.getInt();
        int haveQuickPadding = bb.getInt();

        // parse NET ID Record
        netIdNetNumber = (haveQuickNetIdRecord) & 0b1111111111; // 10 bits
        netIdFrequencyTable = (haveQuickNetIdRecord >> 10) & 0b11; // 2 bits
        netIdMode = (haveQuickNetIdRecord >> 12) & 0b11; // 2 bits
        int haveQuickNetIdPadding = (haveQuickNetIdRecord >> 14) & 0b11; // 2 bits
    }

}
