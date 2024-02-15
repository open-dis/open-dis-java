package edu.nps.moves.dis7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author fo
 */
public class ModulationParametersGeneric extends ModulationParameters {

    private List<Byte> modulationParametersList = new ArrayList();

    public ModulationParametersGeneric() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = super.getMarshalledSize();

        marshalSize = marshalSize + modulationParametersList.size() ;
        int remainder = modulationParametersList.size() % 8;
        if (remainder > 0) {
            marshalSize = marshalSize + calculatePaddingByteNr(remainder);
        }
        return marshalSize;
    }

    public List<Byte> getModulationParametersList() {
        return modulationParametersList;
    }

    public void setModulationParametersList(List<Byte> modulationParametersList) {
        this.modulationParametersList = modulationParametersList;
    }

    private int calculatePaddingByteNr(final int remainder) {
        int paddingByteNr = 0;
        switch (remainder) {
            case 1:
                paddingByteNr = 7;
                break;
            case 2:
                paddingByteNr = 6;
                break;
            case 3:
                paddingByteNr = 5;
                break;
            case 4:
                paddingByteNr = 4;
                break;
            case 5:
                paddingByteNr = 3;
                break;
            case 6:
                paddingByteNr = 2;
                break;
            case 7:
                paddingByteNr = 1;
                break;
        }
        return paddingByteNr;
    }
    
    public void marshal(DataOutputStream dos) {
        try {
            for (int idx = 0; idx < modulationParametersList.size(); idx++) {
                byte modulationParameter = modulationParametersList.get(idx);
                dos.writeByte(modulationParameter);
            } // end of list marshalling
        } // end try  // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void unmarshal(DataInputStream dis, int byteCount) {
        try {
            for (int i = 0; i < byteCount; i++) {
                byte nextByte = dis.readByte();
                modulationParametersList.add(nextByte);
            }
            final int remainder = byteCount % 8;
            if (remainder > 0) {
                int paddingByteNr = 0;
                paddingByteNr = calculatePaddingByteNr(remainder);
                for (int i = 0; i < paddingByteNr; i++) {
                    dis.readByte();
                }
            }
        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of unmarshal method
    
    public void marshal(java.nio.ByteBuffer buff) {
        Iterator<Byte> iter = modulationParametersList.iterator();
        while (iter.hasNext()) {
            byte nextByte = iter.next();
            buff.put(nextByte);
        }
        final int remainder = modulationParametersList.size() % 8;
        if (remainder > 0) {
            int paddingByteNr = 0;
            paddingByteNr = calculatePaddingByteNr(remainder);
            for (int i = 0; i < paddingByteNr; i++) {
                buff.put((byte) 0);
            }
        }
    } // end of marshal method    
    
    public void unmarshal(java.nio.ByteBuffer buff, int byteCount) {
        try {
            for (int i = 0; i < byteCount; i++) {
                byte nextByte = buff.get();
                modulationParametersList.add(nextByte);
            }
            final int remainder = byteCount % 8;
            if (remainder > 0) {
                int paddingByteNr = 0;
                paddingByteNr = calculatePaddingByteNr(remainder);
                for (int i = 0; i < paddingByteNr; i++) {
                    buff.get();
                }
            }            
        } // end try 
        catch (Exception e) {
            System.out.println(e);
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

        if (!(obj instanceof ModulationParametersGeneric)) {
            return false;
        }

        final ModulationParametersGeneric rhs = (ModulationParametersGeneric) obj;

        for (int idx = 0; idx < modulationParametersList.size(); idx++) {
            if (!(modulationParametersList.get(idx).equals(rhs.modulationParametersList.get(idx)))) {
                ivarsEqual = false;
            }
        }
        return ivarsEqual;
    }    
}
