package edu.nps.moves.dis7;

import java.io.*;

/**
 * A supply, and the amount of that supply. Section 6.2.86
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class SupplyQuantity extends Object implements Serializable {

    /**
     * Type of supply
     */
    protected EntityType supplyType = new EntityType();

    /**
     * the number of units of a supply type.
     */
    protected float quantity;

    /**
     * Constructor
     */
    public SupplyQuantity() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + supplyType.getMarshalledSize();  // supplyType
        marshalSize = marshalSize + 4;  // quantity

        return marshalSize;
    }

    public void setSupplyType(EntityType pSupplyType) {
        supplyType = pSupplyType;
    }

    public EntityType getSupplyType() {
        return supplyType;
    }

    public void setQuantity(float pQuantity) {
        quantity = pQuantity;
    }

    public float getQuantity() {
        return quantity;
    }

    public void marshal(DataOutputStream dos) {
        try {
            supplyType.marshal(dos);
            dos.writeFloat((float) quantity);
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        try {
            supplyType.unmarshal(dis);
            quantity = dis.readFloat();
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
        supplyType.marshal(buff);
        buff.putFloat((float) quantity);
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
        supplyType.unmarshal(buff);
        quantity = buff.getFloat();
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

        if (!(obj instanceof SupplyQuantity)) {
            return false;
        }

        final SupplyQuantity rhs = (SupplyQuantity) obj;

        if (!(supplyType.equals(rhs.supplyType))) {
            ivarsEqual = false;
        }
        if (!(quantity == rhs.quantity)) {
            ivarsEqual = false;
        }

        return ivarsEqual;
    }
} // end of class
