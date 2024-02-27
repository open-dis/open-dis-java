package edu.nps.moves.dis7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fo
 */
public class IFFLayer2 extends Object implements Serializable {

    /**
     * Layer Header
     */
    protected LayerHeader layerHeader = new LayerHeader();

    /**
     * Beam Data, specifies beam-specific data
     */
    protected BeamData beamData = new BeamData();

    /**
     * identifies certain secondary operational data for the interrogator or
     * transponder emitting system
     */
    protected SecondaryOperationalData secondaryOperationalData = new SecondaryOperationalData();

    /**
     * The fundamental energy radiation emissions characteristics of the mode(s)
     * for an IFF system type shall be represented by an IFF Fundamental
     * Parameter Data record. At least one IFF Fundamental Parameter Data record
     * is required.
     */
    protected List<IFFFundamentalParameterData> iffFundamentalParameterData = new ArrayList<>();

    public IFFLayer2() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;
        marshalSize = marshalSize + layerHeader.getMarshalledSize();
        marshalSize = marshalSize + beamData.getMarshalledSize();
        marshalSize = marshalSize + secondaryOperationalData.getMarshalledSize();
        for (int idx = 0; idx < iffFundamentalParameterData.size(); idx++) {
            marshalSize = marshalSize + iffFundamentalParameterData.get(idx).getMarshalledSize();
        }
        return marshalSize;
    }

    public void marshal(DataOutputStream dos) {
        try {
            updateLayerHeader();
            layerHeader.marshal(dos);
            beamData.marshal(dos);
            updateIffFundamentalParameterRecordNumber();
            secondaryOperationalData.marshal(dos);
            for (int idx = 0; idx < iffFundamentalParameterData.size(); idx++) {
                iffFundamentalParameterData.get(idx).marshal(dos);
            }

        } // end try  // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method 

    public void unmarshal(DataInputStream dis) {
        try {
            layerHeader.unmarshal(dis);
            beamData.unmarshal(dis);
            secondaryOperationalData.unmarshal(dis);
            for (int idx = 0; idx < secondaryOperationalData.getNumberOfIFFFundamentalParameterRecords(); idx++) {
                IFFFundamentalParameterData paramData = new IFFFundamentalParameterData();
                paramData.unmarshal(dis);
                iffFundamentalParameterData.add(paramData);
            }
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of unmarshal method

    public void marshal(java.nio.ByteBuffer buff) {
        updateLayerHeader();
        layerHeader.marshal(buff);
        beamData.marshal(buff);
        updateIffFundamentalParameterRecordNumber();
        secondaryOperationalData.marshal(buff);
        for (int idx = 0; idx < iffFundamentalParameterData.size(); idx++) {
            iffFundamentalParameterData.get(idx).marshal(buff);
        }
    } // end of marshal method

    private void updateLayerHeader() {
        layerHeader.setLayerNumber((short) 2);
        layerHeader.setLength(getMarshalledSize());
    }

    private void updateIffFundamentalParameterRecordNumber() {
        secondaryOperationalData.setNumberOfIFFFundamentalParameterRecords(iffFundamentalParameterData.size());
    }

    public void unmarshal(java.nio.ByteBuffer buff) {
        layerHeader.unmarshal(buff);
        beamData.unmarshal(buff);
        secondaryOperationalData.unmarshal(buff);
        for (int idx = 0; idx < secondaryOperationalData.getNumberOfIFFFundamentalParameterRecords(); idx++) {
            IFFFundamentalParameterData paramData = new IFFFundamentalParameterData();
            paramData.unmarshal(buff);
            iffFundamentalParameterData.add(paramData);
        }
    } // end of unmarshal method         

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

    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof IFFLayer2)) {
            return false;
        }

        final IFFLayer2 rhs = (IFFLayer2) obj;

        if (!(layerHeader.equals(rhs.layerHeader))) {
            ivarsEqual = false;
        }
        if (!(beamData.equals(rhs.beamData))) {
            ivarsEqual = false;
        }
        if (!(secondaryOperationalData.equals(rhs.secondaryOperationalData))) {
            ivarsEqual = false;
        }
 
        for (int idx = 0; idx < iffFundamentalParameterData.size(); idx++) {
            if (!(iffFundamentalParameterData.get(idx).equals(rhs.iffFundamentalParameterData.get(idx)))) {
                ivarsEqual = false;
            }
        }
        return ivarsEqual;
    }
}
