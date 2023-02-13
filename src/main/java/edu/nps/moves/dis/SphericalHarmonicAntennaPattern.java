package edu.nps.moves.dis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Section 5.2.4.3. Used when the antenna pattern type in the transmitter pdu is
 * of value 2. Specified the direction and radiation pattern from a radio
 * transmitter's antenna. NOTE: this class must be hand-coded to clean up some
 * implementation details.
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class SphericalHarmonicAntennaPattern extends Object implements Serializable {

    protected byte harmonicOrder;

    protected List<FourByteChunk> coefficients = new ArrayList<FourByteChunk>();

    protected short referenceSystem;

    /**
     * Constructor
     */
    public SphericalHarmonicAntennaPattern() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 1;  // harmonicOrder
        for (int idx = 0; idx < coefficients.size(); idx++) {  // coefficients
            FourByteChunk listElement = coefficients.get(idx); // 32-bit coefficient
            marshalSize = marshalSize + listElement.getMarshalledSize();
        }
        marshalSize = marshalSize + 1;  // referenceSystem
        return marshalSize;
    }

    public void setHarmonicOrder(byte pHarmonicOrder) {
        harmonicOrder = pHarmonicOrder;
    }

    public byte getHarmonicOrder() {
        return harmonicOrder;
    }

    public void setCoefficientsList(List<FourByteChunk> pCoefficients) {
        coefficients = pCoefficients;
    }

    public List<FourByteChunk> getCoefficientsList() {
        return coefficients;
    }

    public void setReferenceSystem(short pReferenceSystem) {
        referenceSystem = pReferenceSystem;
    }

    public short getReferenceSystem() {
        return referenceSystem;
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
        buff.put((byte) harmonicOrder);
        for (int idx = 0; idx < coefficients.size(); idx++) {
            FourByteChunk aFourByteChunk = coefficients.get(idx);
            aFourByteChunk.marshal(buff);
        }
        buff.put((byte) referenceSystem);
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
        harmonicOrder = buff.get();
        int coeffientCount = (harmonicOrder * harmonicOrder + 2 * harmonicOrder + 1); // Total of N^2+2N+1 coefficients for an order of N
        for (int idx = 0; idx < coeffientCount; idx++) {
            FourByteChunk anX = new FourByteChunk();
            anX.unmarshal(buff);
            coefficients.add(anX);
        }
        referenceSystem = (short) (buff.get() & 0xFF);
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

    /**
     * Compare all fields that contribute to the state, ignoring transient and
     * static fields, for <code>this</code> and the supplied object
     *
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise.
     */
    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof SphericalHarmonicAntennaPattern)) {
            return false;
        }

        final SphericalHarmonicAntennaPattern rhs = (SphericalHarmonicAntennaPattern) obj;

        if (!(harmonicOrder == rhs.harmonicOrder)) {
            ivarsEqual = false;
        }
        for (int idx = 0; idx < coefficients.size(); idx++) {
            if (!(coefficients.get(idx).equals(rhs.coefficients.get(idx)))) {
                ivarsEqual = false;
            }
        }
        if (!(referenceSystem == rhs.referenceSystem)) {
            ivarsEqual = false;
        }
        return ivarsEqual;
    }
} // end of class
