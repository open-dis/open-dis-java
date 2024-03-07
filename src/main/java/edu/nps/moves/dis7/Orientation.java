package edu.nps.moves.dis7;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author fo
 */
public class Orientation {

    protected float psi;

    protected float theta;

    protected float phi;

    /**
     * Constructor
     */
    public Orientation() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 4;  // psi
        marshalSize = marshalSize + 4;  // theta
        marshalSize = marshalSize + 4;  // phi

        return marshalSize;
    }

    public void setPsi(float pPsi) {
        psi = pPsi;
    }

    public float getPsi() {
        return psi;
    }

    public void setTheta(float pTheta) {
        theta = pTheta;
    }

    public float getTheta() {
        return theta;
    }

    public void setPhi(float pPhi) {
        phi = pPhi;
    }

    public float getPhi() {
        return phi;
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
        buff.putFloat((float) psi);
        buff.putFloat((float) theta);
        buff.putFloat((float) phi);
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
        psi = buff.getFloat();
        theta = buff.getFloat();
        phi = buff.getFloat();
    } // end of unmarshal method 

    public void marshal(DataOutputStream dos) {

        try {
            dos.writeFloat(psi);
            dos.writeFloat(theta);
            dos.writeFloat(phi);
        } // end try  // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void unmarshal(DataInputStream dis) {
        try {
            psi = dis.readFloat();
            theta = dis.readFloat();
            phi = dis.readFloat();
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
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

        if (!(obj instanceof Orientation)) {
            return false;
        }

        final Orientation rhs = (Orientation) obj;

        if (!(psi == rhs.psi)) {
            ivarsEqual = false;
        }
        if (!(theta == rhs.theta)) {
            ivarsEqual = false;
        }
        if (!(phi == rhs.phi)) {
            ivarsEqual = false;
        }

        return ivarsEqual;
    }
}
