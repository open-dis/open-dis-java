package edu.nps.moves.dis7;

import java.io.*;

/**
 * Physical separation of an entity from another entity. Section 6.2.94.6
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class SeparationVP extends VariableParameter implements Serializable {

    /**
     * Reason for separation. EBV
     */
    protected short reasonForSeparation;

    /**
     * Whether the entity existed prior to separation EBV
     */
    protected short preEntityIndicator;

    /**
     * padding
     */
    protected short padding1 = (short) 0;

    /**
     * ID of parent
     */
    protected EntityID parentEntityID = new EntityID();

    /**
     * padding
     */
    protected int padding2 = (int) 0;

    /**
     * Station separated from
     */
    protected NamedLocationIdentification stationLocation = new NamedLocationIdentification();

    /**
     * Constructor
     */
    public SeparationVP() {
        recordType = (short) 2;
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = super.getMarshalledSize();
        marshalSize = marshalSize + 1;  // reasonForSeparation
        marshalSize = marshalSize + 1;  // preEntityIndicator
        marshalSize = marshalSize + 1;  // padding1
        marshalSize = marshalSize + parentEntityID.getMarshalledSize();  // parentEntityID
        marshalSize = marshalSize + 2;  // padding2
        marshalSize = marshalSize + stationLocation.getMarshalledSize();  // stationLocation

        return marshalSize;
    }

    public void setReasonForSeparation(short pReasonForSeparation) {
        reasonForSeparation = pReasonForSeparation;
    }

    public short getReasonForSeparation() {
        return reasonForSeparation;
    }

    public void setPreEntityIndicator(short pPreEntityIndicator) {
        preEntityIndicator = pPreEntityIndicator;
    }

    public short getPreEntityIndicator() {
        return preEntityIndicator;
    }

    public void setPadding1(short pPadding1) {
        padding1 = pPadding1;
    }

    public short getPadding1() {
        return padding1;
    }

    public void setParentEntityID(EntityID pParentEntityID) {
        parentEntityID = pParentEntityID;
    }

    public EntityID getParentEntityID() {
        return parentEntityID;
    }

    public void setPadding2(int pPadding2) {
        padding2 = pPadding2;
    }

    public int getPadding2() {
        return padding2;
    }

    public void setStationLocation(NamedLocationIdentification pStationLocation) {
        stationLocation = pStationLocation;
    }

    public NamedLocationIdentification getStationLocation() {
        return stationLocation;
    }

    public void marshal(DataOutputStream dos) {
        try {
            super.marshal(dos);
            dos.writeByte((byte) reasonForSeparation);
            dos.writeByte((byte) preEntityIndicator);
            dos.writeByte((byte) padding1);
            parentEntityID.marshal(dos);
            dos.writeShort((short) padding2);
            stationLocation.marshal(dos);
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        try {
            reasonForSeparation = (short) dis.readUnsignedByte();
            preEntityIndicator = (short) dis.readUnsignedByte();
            padding1 = (short) dis.readUnsignedByte();
            parentEntityID.unmarshal(dis);
            padding2 = (int) dis.readUnsignedShort();
            stationLocation.unmarshal(dis);
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
        buff.put((byte) reasonForSeparation);
        buff.put((byte) preEntityIndicator);
        buff.put((byte) padding1);
        parentEntityID.marshal(buff);
        buff.putShort((short) padding2);
        stationLocation.marshal(buff);
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
        reasonForSeparation = (short) (buff.get() & 0xFF);
        preEntityIndicator = (short) (buff.get() & 0xFF);
        padding1 = (short) (buff.get() & 0xFF);
        parentEntityID.unmarshal(buff);
        padding2 = (int) (buff.getShort() & 0xFFFF);
        stationLocation.unmarshal(buff);
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

        if (!(obj instanceof SeparationVP)) {
            return false;
        }

        final SeparationVP rhs = (SeparationVP) obj;

        if (!(recordType == rhs.recordType)) {
            ivarsEqual = false;
        }
        if (!(reasonForSeparation == rhs.reasonForSeparation)) {
            ivarsEqual = false;
        }
        if (!(preEntityIndicator == rhs.preEntityIndicator)) {
            ivarsEqual = false;
        }
        if (!(padding1 == rhs.padding1)) {
            ivarsEqual = false;
        }
        if (!(parentEntityID.equals(rhs.parentEntityID))) {
            ivarsEqual = false;
        }
        if (!(padding2 == rhs.padding2)) {
            ivarsEqual = false;
        }
        if (!(stationLocation.equalsImpl(rhs.stationLocation))) {
            ivarsEqual = false;
        }

        return ivarsEqual;
    }
} // end of class
