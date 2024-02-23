/*
 * Copyright (c) 2024, The Moves Institute
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
package edu.nps.moves.dis7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AntennaPatternGeneric extends AntennaPattern {

    List<Byte> listAntennaPatternOctets = new ArrayList<>();

    public AntennaPatternGeneric() {
        super();
    }

    public int getMarshalledSize() {
        int marshalSize = 0;
        marshalSize = listAntennaPatternOctets.size();
        int remainder = listAntennaPatternOctets.size() % 8;
        if (remainder > 0) {
            marshalSize = marshalSize + calculatePaddingByteNr(remainder);
        }
        return marshalSize;
    }

    public List<Byte> getListAntennaPatternOctets() {
        return listAntennaPatternOctets;
    }

    public void setListAntennaPatternOctets(List<Byte> listAntennaPatternOctets) {
        this.listAntennaPatternOctets = listAntennaPatternOctets;
    }

    public void marshal(DataOutputStream dos) {
        try {
            Iterator<Byte> iter = listAntennaPatternOctets.iterator();
            while (iter.hasNext()) {
                byte nextByte = iter.next();
                dos.writeByte(nextByte);
            }
            final int remainder = listAntennaPatternOctets.size() % 8;
            if (remainder > 0) {
                int paddingByteNr = 0;
                paddingByteNr = calculatePaddingByteNr(remainder);
                for (int i = 0; i < paddingByteNr; i++) {
                    dos.writeByte(0);
                }
            }
        } // end try  // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis, int byteCount) {
        try {
            for (int i = 0; i < byteCount; i++) {
                byte nextByte = dis.readByte();
                listAntennaPatternOctets.add(nextByte);
            }
            final int remainder = byteCount % 8;
            if (remainder > 0) {
                int paddingByteNr = 0;
                paddingByteNr = calculatePaddingByteNr(remainder);
                for (int i = 0; i < paddingByteNr; i++) {
                    dis.readByte();
                }
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
        Iterator<Byte> iter = listAntennaPatternOctets.iterator();
        while (iter.hasNext()) {
            byte nextByte = iter.next();
            buff.put(nextByte);
        }
        final int remainder = listAntennaPatternOctets.size() % 8;
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
    public void unmarshal(java.nio.ByteBuffer buff, int byteCount) {
        try {
            for (int i = 0; i < byteCount; i++) {
                byte nextByte = buff.get();
                listAntennaPatternOctets.add(nextByte);
            }
            final int remainder = byteCount % 8;
            if (remainder > 0) {
                int paddingByteNr = 0;
                paddingByteNr = calculatePaddingByteNr(remainder);
                for (int i = 0; i < paddingByteNr; i++) {
                    buff.get();
                }
            }            
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of unmarshal method 

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

        if (!(obj instanceof AntennaPattern)) {
            return false;
        }

        final AntennaPatternGeneric rhs = (AntennaPatternGeneric) obj;

        for (int idx = 0; idx < listAntennaPatternOctets.size(); idx++) {
            if (!(listAntennaPatternOctets.get(idx).equals(rhs.listAntennaPatternOctets.get(idx)))) {
                ivarsEqual = false;
            }
        }
        return ivarsEqual;
    }
}
