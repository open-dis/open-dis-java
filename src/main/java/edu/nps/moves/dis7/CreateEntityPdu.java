package edu.nps.moves.dis7;

import java.io.*;

/**
 * Section 7.5.2. Create a new entity. COMPLETE
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class CreateEntityPdu extends SimulationManagementFamilyPdu implements Serializable {


    /**
     * Identifier for the request. See 6.2.75
     */
    protected long requestID;

    /**
     * Constructor
     */
    public CreateEntityPdu() {
        setPduType((short) 11);
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = super.getMarshalledSize();
        marshalSize = marshalSize + 4;  // requestID

        return marshalSize;
    }

    public void setOriginatingID(EntityID pOriginatingID) {
        setOriginatingEntityID(pOriginatingID);
    }

    public EntityID getOriginatingID() {
        return getOriginatingEntityID();
    }

    public void setReceivingID(EntityID pReceivingID) {
        setReceivingEntityID(pReceivingID);
    }

    public EntityID getReceivingID() {
        return getReceivingEntityID();
    }

    public void setRequestID(long pRequestID) {
        requestID = pRequestID;
    }

    public long getRequestID() {
        return requestID;
    }

    public void marshal(DataOutputStream dos) {
        super.marshal(dos);
        try {
            dos.writeInt((int) requestID);
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        super.unmarshal(dis);

        try {
            requestID = dis.readInt();
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
        super.marshal(buff);
        buff.putInt((int) requestID);
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

        requestID = buff.getInt();
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

        if (!(obj instanceof CreateEntityPdu)) {
            return false;
        }

        final CreateEntityPdu rhs = (CreateEntityPdu) obj;

        if (!(getOriginatingEntityID().equals(rhs.getOriginatingEntityID()))) {
            ivarsEqual = false;
        }
        if (!(getReceivingEntityID().equals(rhs.getReceivingEntityID()))) {
            ivarsEqual = false;
        }
        if (!(requestID == rhs.requestID)) {
            ivarsEqual = false;
        }

        return ivarsEqual && super.equalsImpl(rhs);
    }
} // end of class
