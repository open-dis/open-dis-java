package edu.nps.moves.dis7;

import java.io.*;

/**
 * Basic operational data for IFF. Section 6.2.40.
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class FundamentalOperationalData extends Object implements Serializable {

    /**
     * system status
     */
    protected short systemStatus;

    /**
     * data field 1
     */
    protected short dataField1;

    /**
     * eight boolean fields
     */
    protected short informationLayers;

    /**
     * enumeration
     */
    protected short dataField2;

    /**
     * parameter, enumeration
     */
    protected int parameter1;

    /**
     * parameter, enumeration
     */
    protected int parameter2;

    /**
     * parameter, enumeration
     */
    protected int parameter3;

    /**
     * parameter, enumeration
     */
    protected int parameter4;

    /**
     * parameter, enumeration
     */
    protected int parameter5;

    /**
     * parameter, enumeration
     */
    protected int parameter6;

    /**
     * Constructor
     */
    public FundamentalOperationalData() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 1;  // systemStatus
        marshalSize = marshalSize + 1;  // dataField1
        marshalSize = marshalSize + 1;  // informationLayers
        marshalSize = marshalSize + 1;  // dataField2
        marshalSize = marshalSize + 2;  // parameter1
        marshalSize = marshalSize + 2;  // parameter2
        marshalSize = marshalSize + 2;  // parameter3
        marshalSize = marshalSize + 2;  // parameter4
        marshalSize = marshalSize + 2;  // parameter5
        marshalSize = marshalSize + 2;  // parameter6

        return marshalSize;
    }

    public void setSystemStatus(short pSystemStatus) {
        systemStatus = pSystemStatus;
    }

    public short getSystemStatus() {
        return systemStatus;
    }

    public void setDataField1(short pDataField1) {
        dataField1 = pDataField1;
    }

    public short getDataField1() {
        return dataField1;
    }

    public void setInformationLayers(short pInformationLayers) {
        informationLayers = pInformationLayers;
    }

    public short getInformationLayers() {
        return informationLayers;
    }

    public void setDataField2(short pDataField2) {
        dataField2 = pDataField2;
    }

    public short getDataField2() {
        return dataField2;
    }

    public void setParameter1(int pParameter1) {
        parameter1 = pParameter1;
    }

    public int getParameter1() {
        return parameter1;
    }

    public void setParameter2(int pParameter2) {
        parameter2 = pParameter2;
    }

    public int getParameter2() {
        return parameter2;
    }

    public void setParameter3(int pParameter3) {
        parameter3 = pParameter3;
    }

    public int getParameter3() {
        return parameter3;
    }

    public void setParameter4(int pParameter4) {
        parameter4 = pParameter4;
    }

    public int getParameter4() {
        return parameter4;
    }

    public void setParameter5(int pParameter5) {
        parameter5 = pParameter5;
    }

    public int getParameter5() {
        return parameter5;
    }

    public void setParameter6(int pParameter6) {
        parameter6 = pParameter6;
    }

    public int getParameter6() {
        return parameter6;
    }

    public void marshal(DataOutputStream dos) {
        try {
            dos.writeByte((byte) systemStatus);
            dos.writeByte((byte) dataField1);
            dos.writeByte((byte) informationLayers);
            dos.writeByte((byte) dataField2);
            dos.writeShort((short) parameter1);
            dos.writeShort((short) parameter2);
            dos.writeShort((short) parameter3);
            dos.writeShort((short) parameter4);
            dos.writeShort((short) parameter5);
            dos.writeShort((short) parameter6);
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        try {
            systemStatus = (short) dis.readUnsignedByte();
            dataField1 = (short) dis.readUnsignedByte();
            informationLayers = (short) dis.readUnsignedByte();
            dataField2 = (short) dis.readUnsignedByte();
            parameter1 = (int) dis.readUnsignedShort();
            parameter2 = (int) dis.readUnsignedShort();
            parameter3 = (int) dis.readUnsignedShort();
            parameter4 = (int) dis.readUnsignedShort();
            parameter5 = (int) dis.readUnsignedShort();
            parameter6 = (int) dis.readUnsignedShort();
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
        buff.put((byte) systemStatus);
        buff.put((byte) dataField1);
        buff.put((byte) informationLayers);
        buff.put((byte) dataField2);
        buff.putShort((short) parameter1);
        buff.putShort((short) parameter2);
        buff.putShort((short) parameter3);
        buff.putShort((short) parameter4);
        buff.putShort((short) parameter5);
        buff.putShort((short) parameter6);
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
        systemStatus = (short) (buff.get() & 0xFF);
        dataField1 = (short) (buff.get() & 0xFF);
        informationLayers = (short) (buff.get() & 0xFF);
        dataField2 = (short) (buff.get() & 0xFF);
        parameter1 = (int) (buff.getShort() & 0xFFFF);
        parameter2 = (int) (buff.getShort() & 0xFFFF);
        parameter3 = (int) (buff.getShort() & 0xFFFF);
        parameter4 = (int) (buff.getShort() & 0xFFFF);
        parameter5 = (int) (buff.getShort() & 0xFFFF);
        parameter6 = (int) (buff.getShort() & 0xFFFF);
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

        if (!(obj instanceof FundamentalOperationalData)) {
            return false;
        }

        final FundamentalOperationalData rhs = (FundamentalOperationalData) obj;

        if (!(systemStatus == rhs.systemStatus)) {
            ivarsEqual = false;
        }
        if (!(dataField1 == rhs.dataField1)) {
            ivarsEqual = false;
        }
        if (!(informationLayers == rhs.informationLayers)) {
            ivarsEqual = false;
        }
        if (!(dataField2 == rhs.dataField2)) {
            ivarsEqual = false;
        }
        if (!(parameter1 == rhs.parameter1)) {
            ivarsEqual = false;
        }
        if (!(parameter2 == rhs.parameter2)) {
            ivarsEqual = false;
        }
        if (!(parameter3 == rhs.parameter3)) {
            ivarsEqual = false;
        }
        if (!(parameter4 == rhs.parameter4)) {
            ivarsEqual = false;
        }
        if (!(parameter5 == rhs.parameter5)) {
            ivarsEqual = false;
        }
        if (!(parameter6 == rhs.parameter6)) {
            ivarsEqual = false;
        }

        return ivarsEqual;
    }
} // end of class
