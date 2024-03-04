package edu.nps.moves.dis7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 *
 * @author fo
 */
public class IFFPdu extends DistributedEmissionsFamilyPdu implements Serializable {

    /**
     * Basic System Data. This is the only layer that is required to be included
     * in every IFF PDU regardless of the system type
     */
    protected IFFLayer1 layer1 = new IFFLayer1();

    /**
     * Basic Emissions Data. This layer is used for basic emissions data when
     * required to support simulations that need detailed IFF electromagnetic
     * characteristics
     */
    protected IFFLayer2 layer2 = null;

    // Layers not yet implemented
//    protected IFFLayer3 layer3 = null;
//    protected IFFLayer4 layer4 = null;
//    protected IFFLayer5 layer5 = null;
//    protected IFFLayer6 layer6 = null;
//    protected IFFLayer7 layer7 = null;

    public IFFPdu() {
        setPduType((short) 28);
    }
    
    public int getMarshalledSize() {
        int marshalSize = 0;
        marshalSize = super.getMarshalledSize();
        marshalSize = marshalSize + layer1.getMarshalledSize();//emittingEntityId
        if (isLayerPresent(2)) {
            marshalSize = marshalSize + layer2.getMarshalledSize();
        }
        
        return marshalSize;
    }

    public IFFLayer1 getLayer1() {
        return layer1;
    }

    public void setLayer1(IFFLayer1 layer1) {
        this.layer1 = layer1;
    }

    public IFFLayer2 getLayer2() {
        return layer2;
    }

    public void setLayer2(IFFLayer2 layer2) {
        this.layer2 = layer2;
        updateInformationLayersPresent();
    }

    private void updateInformationLayersPresent() {
        short informationLayersPresent = 1;
        if (layer2 != null) {
            informationLayersPresent = (short) (informationLayersPresent | 0x02);
        }
        layer1.getFundamentalOperationalData().setInformationLayers(informationLayersPresent);
    }

    public void marshal(DataOutputStream dos) {
        super.marshal(dos);
        try {
            layer1.marshal(dos);
            if (layer2 != null) {
                updateInformationLayersPresent();
                layer2.marshal(dos);
            }
        } // end try  // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method 

    public void unmarshal(DataInputStream dis) {
        super.unmarshal(dis);
        try {
            layer1.unmarshal(dis);
            if (isLayerPresent(2)) {
                layer2 = new IFFLayer2();
                layer2.unmarshal(dis);
            }
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of unmarshal method 

    public void marshal(java.nio.ByteBuffer buff) {
        super.marshal(buff);
        layer1.marshal(buff);
        if (layer2 != null) {
            updateInformationLayersPresent();
            layer2.marshal(buff);
        }
    } // end of marshal method

    public void unmarshal(java.nio.ByteBuffer buff) {
        super.unmarshal(buff);
        layer1.unmarshal(buff);
        if (isLayerPresent(2) ) {
            layer2 = new IFFLayer2();
            layer2.unmarshal(buff);
        }
    } // end of unmarshal method  

    private boolean isLayerPresent(int layer) {
        return (layer1.getFundamentalOperationalData().getInformationLayers() & layer) > 0;
    }

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

        if (!(obj instanceof IFFPdu)) {
            return false;
        }
        final IFFPdu rhs = (IFFPdu) obj;

        if (!(layer1.equals(rhs.layer1))) {
            ivarsEqual = false;
        }
        if (layer2 != null || rhs.layer2 != null) {
            if (!(layer2 == null && rhs.layer2 == null)) {
                ivarsEqual = false;
            }
        }
        if (!(layer2.equals(rhs.layer2))) {
            ivarsEqual = false;
        }

        return ivarsEqual && super.equalsImpl(rhs);
    }
}
