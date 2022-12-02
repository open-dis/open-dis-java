package edu.nps.moves.dis;

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
/**
 *
 * @author fo-ifad
 */
public class SilentAggregateSystem {

    // nr of silent aggregates of the following type 
    protected short nrOfAggregates;

    // padding
    protected short pad1 = 0xFF;

    //Aggregate type
    protected AggregateType aggregateType = new AggregateType();

    // Constructor
    public SilentAggregateSystem() {

    }

    public int getMarshalledSize() {
        int marshalSize = 0;
        marshalSize = marshalSize + 2;  // nrOfAggregates
        marshalSize = marshalSize + 2;  // paddingl
        marshalSize = marshalSize + aggregateType.getMarshalledSize();//Aggregate type

        return marshalSize;
    }

    public void setNrOfAggregates(short aNr) {
        nrOfAggregates = aNr;
    }

    public short getNrOfAggregates() {
        return nrOfAggregates;
    }

    public void setPadding(short aPad) {
        pad1 = aPad;
    }

    public short getPadding() {
        return pad1;
    }

    public AggregateType getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(AggregateType aggregateType) {
        this.aggregateType = aggregateType;
    }

    public void marshal(java.nio.ByteBuffer buff) {
        buff.putShort((short) nrOfAggregates);
        buff.putShort((short) pad1);
        aggregateType.marshal(buff);
    } // end of marshal method

    public void unmarshal(java.nio.ByteBuffer buff) {
        nrOfAggregates = (short) (buff.getShort() & 0xFF);
        pad1 = (short) (buff.getShort() & 0xFF);
        aggregateType.unmarshal(buff);
    } // end of unmarshal method 

    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof SilentAggregateSystem)) {
            return false;
        }

        final SilentAggregateSystem rhs = (SilentAggregateSystem) obj;

        if (!(nrOfAggregates == rhs.nrOfAggregates)) {
            ivarsEqual = false;
        }
        if (!(pad1 == rhs.pad1)) {
            ivarsEqual = false;
        }
        if (!(aggregateType == rhs.aggregateType)) {
            ivarsEqual = false;
        }
        return ivarsEqual;
    }
}
