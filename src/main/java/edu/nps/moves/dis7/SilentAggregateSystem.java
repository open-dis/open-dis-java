package edu.nps.moves.dis7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author fo
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

    public void marshal(DataOutputStream dos) {
        try {
            dos.writeShort((short) nrOfAggregates);
            dos.writeShort((short) pad1);
            aggregateType.marshal(dos);
        } // end of marshal method
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void unmarshal(DataInputStream dis) {
        try {
            nrOfAggregates = (short) dis.readShort() ;
            pad1 = (short) dis.readShort();
            aggregateType.unmarshal(dis);
        } // end of unmarshal method
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

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
