package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;

/**
 * Information used to communicate the offer of supplies by a supplying entity
 * to a receiving entity. Section 7.4.3 COMPLETE
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class ResupplyOfferPdu extends LogisticsFamilyPdu implements Serializable {

    /**
     * Field identifies the Entity and respective Entity Record ID that is
     * receiving service (see 6.2.28), Section 7.4.3
     */
    protected EntityID receivingEntityID = new EntityID();

    /**
     * Identifies the Entity and respective Entity ID Record that is supplying
     * (see 6.2.28), Section 7.4.3
     */
    protected EntityID supplyingEntityID = new EntityID();

    /**
     * How many supplies types are being offered, Section 7.4.3
     */
    protected short numberOfSupplyTypes;

    /**
     * padding
     */
    protected byte padding1 = (byte) 0;

    /**
     * padding
     */
    protected short padding2 = (short) 0;

    /**
     * A Reord that Specifies the type of supply and the amount of that supply
     * for each of the supply types in numberOfSupplyTypes (see 6.2.85), Section
     * 7.4.3
     */
    protected List< SupplyQuantity> supplies = new ArrayList< SupplyQuantity>();

    /**
     * Constructor
     */
    public ResupplyOfferPdu() {
        setPduType((short) 6);
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = super.getMarshalledSize();
        marshalSize = marshalSize + receivingEntityID.getMarshalledSize();  // receivingEntityID
        marshalSize = marshalSize + supplyingEntityID.getMarshalledSize();  // supplyingEntityID
        marshalSize = marshalSize + 1;  // numberOfSupplyTypes
        marshalSize = marshalSize + 1;  // padding1
        marshalSize = marshalSize + 2;  // padding2
        for (int idx = 0; idx < supplies.size(); idx++) {
            SupplyQuantity listElement = supplies.get(idx);
            marshalSize = marshalSize + listElement.getMarshalledSize();
        }

        return marshalSize;
    }

    public void setReceivingEntityID(EntityID pReceivingEntityID) {
        receivingEntityID = pReceivingEntityID;
    }

    public EntityID getReceivingEntityID() {
        return receivingEntityID;
    }

    public void setSupplyingEntityID(EntityID pSupplyingEntityID) {
        supplyingEntityID = pSupplyingEntityID;
    }

    public EntityID getSupplyingEntityID() {
        return supplyingEntityID;
    }

    public short getNumberOfSupplyTypes() {
        return (short) supplies.size();
    }

    /**
     * Note that setting this value will not change the marshalled value. The
     * list whose length this describes is used for that purpose. The
     * getnumberOfSupplyTypes method will also be based on the actual list
     * length rather than this value. The method is simply here for java bean
     * completeness.
     */
    public void setNumberOfSupplyTypes(short pNumberOfSupplyTypes) {
        numberOfSupplyTypes = pNumberOfSupplyTypes;
    }

    public void setPadding1(byte pPadding1) {
        padding1 = pPadding1;
    }

    public byte getPadding1() {
        return padding1;
    }

    public void setPadding2(short pPadding2) {
        padding2 = pPadding2;
    }

    public short getPadding2() {
        return padding2;
    }

    public void setSupplies(List<SupplyQuantity> pSupplies) {
        supplies = pSupplies;
    }

    public List<SupplyQuantity> getSupplies() {
        return supplies;
    }

    public void marshal(DataOutputStream dos) {
        super.marshal(dos);
        try {
            receivingEntityID.marshal(dos);
            supplyingEntityID.marshal(dos);
            dos.writeByte((byte) supplies.size());
            dos.writeByte((byte) padding1);
            dos.writeShort((short) padding2);

            for (int idx = 0; idx < supplies.size(); idx++) {
                SupplyQuantity aSupplyQuantity = supplies.get(idx);
                aSupplyQuantity.marshal(dos);
            } // end of list marshalling

        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        super.unmarshal(dis);

        try {
            receivingEntityID.unmarshal(dis);
            supplyingEntityID.unmarshal(dis);
            numberOfSupplyTypes = (short) dis.readUnsignedByte();
            padding1 = dis.readByte();
            padding2 = dis.readShort();
            for (int idx = 0; idx < numberOfSupplyTypes; idx++) {
                SupplyQuantity anX = new SupplyQuantity();
                anX.unmarshal(dis);
                supplies.add(anX);
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
        super.marshal(buff);
        receivingEntityID.marshal(buff);
        supplyingEntityID.marshal(buff);
        buff.put((byte) supplies.size());
        buff.put((byte) padding1);
        buff.putShort((short) padding2);

        for (int idx = 0; idx < supplies.size(); idx++) {
            SupplyQuantity aSupplyQuantity = (SupplyQuantity) supplies.get(idx);
            aSupplyQuantity.marshal(buff);
        } // end of list marshalling

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

        receivingEntityID.unmarshal(buff);
        supplyingEntityID.unmarshal(buff);
        numberOfSupplyTypes = (short) (buff.get() & 0xFF);
        padding1 = buff.get();
        padding2 = buff.getShort();
        for (int idx = 0; idx < numberOfSupplyTypes; idx++) {
            SupplyQuantity anX = new SupplyQuantity();
            anX.unmarshal(buff);
            supplies.add(anX);
        }

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

        if (!(obj instanceof ResupplyOfferPdu)) {
            return false;
        }

        final ResupplyOfferPdu rhs = (ResupplyOfferPdu) obj;

        if (!(receivingEntityID.equals(rhs.receivingEntityID))) {
            ivarsEqual = false;
        }
        if (!(supplyingEntityID.equals(rhs.supplyingEntityID))) {
            ivarsEqual = false;
        }
        if (!(numberOfSupplyTypes == rhs.numberOfSupplyTypes)) {
            ivarsEqual = false;
        }
        if (!(padding1 == rhs.padding1)) {
            ivarsEqual = false;
        }
        if (!(padding2 == rhs.padding2)) {
            ivarsEqual = false;
        }

        for (int idx = 0; idx < supplies.size(); idx++) {
            if (!(supplies.get(idx).equals(rhs.supplies.get(idx)))) {
                ivarsEqual = false;
            }
        }

        return ivarsEqual && super.equalsImpl(rhs);
    }
} // end of class
