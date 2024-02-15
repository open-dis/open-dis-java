package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;

/**
 * Detailed information about a radio transmitter. This PDU requires manually
 * written code to complete, since the modulation parameters are of variable
 * length. Section 7.7.2 UNFINISHED
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class TransmitterPdu extends RadioCommunicationsFamilyPdu implements Serializable {

    /**
     * ID of the entitythat is the source of the communication
     */
    protected EntityID radioReferenceID = new EntityID();

    /**
     * particular radio within an entity
     */
    protected int radioNumber;

    /**
     * Type of radio
     */
    protected EntityType radioEntityType = new EntityType();

    /**
     * transmit state
     */
    protected short transmitState;

    /**
     * input source
     */
    protected short inputSource;

    /**
     * count field
     */
    protected int variableTransmitterParameterCount;

    /**
     * Location of antenna
     */
    protected Vector3Double antennaLocation = new Vector3Double();

    /**
     * relative location of antenna
     */
    protected Vector3Float relativeAntennaLocation = new Vector3Float();

    /**
     * antenna pattern type
     */
    protected int antennaPatternType;

    /**
     * atenna pattern length
     */
    protected int antennaPatternCount;

    /**
     * frequency
     */
    protected long frequency;

    /**
     * transmit frequency Bandwidth
     */
    protected float transmitFrequencyBandwidth;

    /**
     * transmission power
     */
    protected float power;

    /**
     * modulation
     */
    protected ModulationType modulationType = new ModulationType();

    /**
     * crypto system enumeration
     */
    protected int cryptoSystem;

    /**
     * crypto system key identifer
     */
    protected int cryptoKeyId;

    /**
     * how many modulation parameters we have
     */
    protected short modulationParameterCount;

    /**
     * padding2
     */
    protected int padding2 = (int) 0;

    /**
     * padding3
     */
    protected short padding3 = (short) 0;

    /**
     * modulation parameters
     */
    protected ModulationParameters modulationParameters = new ModulationParameters();
    /**
     * antenna pattern records
     */
    protected AntennaPattern antennaPattern = new AntennaPattern();

    /**
     * optional Variable Transmitter Parameters records
     */
    protected List<VariableTransmitterParameters> variableTransmitterParametersList = new ArrayList<>();

    /**
     * Constructor
     */
    public TransmitterPdu() {
        setPduType((short) 25);
    }
    
    public int getMarshalledSize() {
        int marshalSize = 0;
        
        marshalSize = super.getMarshalledSize();
        marshalSize = marshalSize + radioReferenceID.getMarshalledSize();  // radioReferenceID
        marshalSize = marshalSize + 2;  // radioNumber
        marshalSize = marshalSize + radioEntityType.getMarshalledSize();  // radioEntityType
        marshalSize = marshalSize + 1;  // transmitState
        marshalSize = marshalSize + 1;  // inputSource
        marshalSize = marshalSize + 2;  // variableTransmitterParameterCount
        marshalSize = marshalSize + antennaLocation.getMarshalledSize();  // antennaLocation
        marshalSize = marshalSize + relativeAntennaLocation.getMarshalledSize();  // relativeAntennaLocation
        marshalSize = marshalSize + 2;  // antennaPatternType
        marshalSize = marshalSize + 2;  // antennaPatternCount
        marshalSize = marshalSize + 8;  // frequency
        marshalSize = marshalSize + 4;  // transmitFrequencyBandwidth
        marshalSize = marshalSize + 4;  // power
        marshalSize = marshalSize + modulationType.getMarshalledSize();  // modulationType
        marshalSize = marshalSize + 2;  // cryptoSystem
        marshalSize = marshalSize + 2;  // cryptoKeyId
        marshalSize = marshalSize + 1;  // modulationParameterCount
        marshalSize = marshalSize + 2;  // padding2
        marshalSize = marshalSize + 1;  // padding3

        int nrOfModulationBytes = 0;
        switch (getModulationType().getRadioSystem()) {
            case 6:// CCTT SINCGARS
                if (modulationParameters instanceof CcttSincgarsModulationParameters) {
                    CcttSincgarsModulationParameters ccttSincgarsModulationParameters = (CcttSincgarsModulationParameters) modulationParameters;
                    marshalSize = marshalSize + ccttSincgarsModulationParameters.getMarshalledSize();
                    nrOfModulationBytes = nrOfModulationBytes + ccttSincgarsModulationParameters.getMarshalledSize();
                }
                break;
            case 8:// JTIDS/MIDS
                if (modulationParameters instanceof JtidsMidsModulationParameters) {
                    JtidsMidsModulationParameters jtidsMidsModulationParameters = (JtidsMidsModulationParameters) modulationParameters;
                    marshalSize = marshalSize + jtidsMidsModulationParameters.getMarshalledSize();
                    nrOfModulationBytes = nrOfModulationBytes + jtidsMidsModulationParameters.getMarshalledSize();
                }
                break;
            case 0:                                  // Other
            case 1:                                  // Generic
            case 2:                                  // HQ
            case 3:                                  // HQII
            case 4:                                  // HQIIA
            case 5:                                  // SINCGARS
            case 7:                                  // EPLRS                
            default:
                if (modulationParameters instanceof ModulationParametersGeneric) {
                    ModulationParametersGeneric modPar = (ModulationParametersGeneric) modulationParameters;
                    marshalSize = marshalSize + modPar.getMarshalledSize();
                    nrOfModulationBytes = nrOfModulationBytes + modPar.getMarshalledSize();
                }
                break;
        }
        
        if (nrOfModulationBytes % 8 > 0) {
            int remainder = nrOfModulationBytes % 8;
            switch (remainder) {
                case 1:
                    marshalSize = marshalSize + 7;
                    break;
                case 2:
                    marshalSize = marshalSize + 6;
                    break;
                case 3:
                    marshalSize = marshalSize + 5;
                    break;
                case 4:
                    marshalSize = marshalSize + 4;
                    break;
                case 5:
                    marshalSize = marshalSize + 3;
                    break;
                case 6:
                    marshalSize = marshalSize + 2;
                    break;
                case 7:
                    marshalSize = marshalSize + 1;
                    break;
            }
        }
        switch (antennaPatternType) {
            case 2:
                if (antennaPattern instanceof BeamAntennaPattern) {
                    marshalSize = marshalSize + ((BeamAntennaPattern) antennaPattern).getMarshalledSize();
                }
                break;
            case 6:
                break;
            default:
                if (antennaPattern instanceof AntennaPatternGeneric) {
                    marshalSize = marshalSize + ((AntennaPatternGeneric) antennaPattern).getMarshalledSize();
                }
                break;
        }
        Iterator<VariableTransmitterParameters> iter = variableTransmitterParametersList.iterator();
        while (iter.hasNext()) {
            marshalSize = marshalSize + iter.next().getMarshalledSize();
        }
        return marshalSize;
    }
    
    public void setRadioReferenceID(EntityID pRadioReferenceID) {
        radioReferenceID = pRadioReferenceID;
    }
    
    public EntityID getRadioReferenceID() {
        return radioReferenceID;
    }
    
    public void setRadioNumber(int pRadioNumber) {
        radioNumber = pRadioNumber;
    }
    
    public int getRadioNumber() {
        return radioNumber;
    }
    
    public void setRadioEntityType(EntityType pRadioEntityType) {
        radioEntityType = pRadioEntityType;
    }
    
    public EntityType getRadioEntityType() {
        return radioEntityType;
    }
    
    public void setTransmitState(short pTransmitState) {
        transmitState = pTransmitState;
    }
    
    public short getTransmitState() {
        return transmitState;
    }
    
    public void setInputSource(short pInputSource) {
        inputSource = pInputSource;
    }
    
    public short getInputSource() {
        return inputSource;
    }
    
    public void setVariableTransmitterParameterCount(int pVariableTransmitterParameterCount) {
        variableTransmitterParameterCount = pVariableTransmitterParameterCount;
    }
    
    public int getVariableTransmitterParameterCount() {
        return variableTransmitterParameterCount;
    }
    
    public void setAntennaLocation(Vector3Double pAntennaLocation) {
        antennaLocation = pAntennaLocation;
    }
    
    public Vector3Double getAntennaLocation() {
        return antennaLocation;
    }
    
    public void setRelativeAntennaLocation(Vector3Float pRelativeAntennaLocation) {
        relativeAntennaLocation = pRelativeAntennaLocation;
    }
    
    public Vector3Float getRelativeAntennaLocation() {
        return relativeAntennaLocation;
    }
    
    public void setAntennaPatternType(int pAntennaPatternType) {
        antennaPatternType = pAntennaPatternType;
    }
    
    public int getAntennaPatternType() {
        return antennaPatternType;
    }
    
    public int getAntennaPatternCount() {
        int antennaPatternCount = 0;
        switch (antennaPatternType) {
            case 2:
                if (antennaPattern instanceof BeamAntennaPattern) {
                    antennaPatternCount = ((BeamAntennaPattern) antennaPattern).getMarshalledSize();
                }
                break;
            case 6:
                break;
            default:
                if (antennaPattern instanceof AntennaPatternGeneric) {
                    antennaPatternCount = ((AntennaPatternGeneric) antennaPattern).getMarshalledSize();
                }
                break;
        }
        return antennaPatternCount;
    }

    /**
     * Note that setting this value will not change the marshalled value. The
     * list whose length this describes is used for that purpose. The
     * getantennaPatternCount method will also be based on the actual list
     * length rather than this value. The method is simply here for java bean
     * completeness.
     */
    public void setAntennaPatternCount(int pAntennaPatternCount) {
        antennaPatternCount = pAntennaPatternCount;
    }
    
    public void setFrequency(long pFrequency) {
        frequency = pFrequency;
    }
    
    public long getFrequency() {
        return frequency;
    }
    
    public void setTransmitFrequencyBandwidth(float pTransmitFrequencyBandwidth) {
        transmitFrequencyBandwidth = pTransmitFrequencyBandwidth;
    }
    
    public float getTransmitFrequencyBandwidth() {
        return transmitFrequencyBandwidth;
    }
    
    public void setPower(float pPower) {
        power = pPower;
    }
    
    public float getPower() {
        return power;
    }
    
    public void setModulationType(ModulationType pModulationType) {
        modulationType = pModulationType;
    }
    
    public ModulationType getModulationType() {
        return modulationType;
    }
    
    public void setCryptoSystem(int pCryptoSystem) {
        cryptoSystem = pCryptoSystem;
    }
    
    public int getCryptoSystem() {
        return cryptoSystem;
    }
    
    public void setCryptoKeyId(int pCryptoKeyId) {
        cryptoKeyId = pCryptoKeyId;
    }
    
    public int getCryptoKeyId() {
        return cryptoKeyId;
    }
    
    public short getModulationParameterCount() {
        int modulationParameterOctets = 0;
        switch (getModulationType().getRadioSystem()) {
            case 6:// CCTT SINCGARS
                if (modulationParameters instanceof CcttSincgarsModulationParameters) {
                    CcttSincgarsModulationParameters ccttSincgarsModulationParameters = (CcttSincgarsModulationParameters) modulationParameters;
                    modulationParameterOctets = ccttSincgarsModulationParameters.getMarshalledSize();
                }
                break;
            case 8:// JTIDS/MIDS
                if (modulationParameters instanceof JtidsMidsModulationParameters) {
                    JtidsMidsModulationParameters jtidsMidsModulationParameters = (JtidsMidsModulationParameters) modulationParameters;
                    modulationParameterOctets = jtidsMidsModulationParameters.getMarshalledSize();
                }
                break;
            case 0:                                  // Other
            case 1:                                  // Generic
            case 2:                                  // HQ
            case 3:                                  // HQII
            case 4:                                  // HQIIA
            case 5:                                  // SINCGARS
            case 7:                                  // EPLRS                
            default:
                if (modulationParameters instanceof ModulationParametersGeneric) {
                    modulationParameterOctets = ((ModulationParametersGeneric) modulationParameters).getMarshalledSize();
                }
                break;
        }
        return (short) modulationParameterOctets;
    }

    /**
     * Note that setting this value will not change the marshalled value. The
     * list whose length this describes is used for that purpose. The
     * getmodulationParameterCount method will also be based on the actual list
     * length rather than this value. The method is simply here for java bean
     * completeness.
     */
    public void setModulationParameterCount(short pModulationParameterCount) {
        modulationParameterCount = pModulationParameterCount;
    }
    
    public void setPadding2(int pPadding2) {
        padding2 = pPadding2;
    }
    
    public int getPadding2() {
        return padding2;
    }
    
    public void setPadding3(short pPadding3) {
        padding3 = pPadding3;
    }
    
    public short getPadding3() {
        return padding3;
    }
    
    public void setModulationParameters(ModulationParameters pModulationParametersList) {
        modulationParameters = pModulationParametersList;
    }
    
    public ModulationParameters getModulationParameters() {
        return modulationParameters;
    }
    
    public void setAntennaPattern(BeamAntennaPattern pAntennaPatternList) {
        antennaPattern = pAntennaPatternList;
    }
    
    public AntennaPattern getAntennaPattern() {
        return antennaPattern;
    }
    
    public List<VariableTransmitterParameters> getVariableTransmitterParametersList() {
        return variableTransmitterParametersList;
    }
    
    public void setVariableTransmitterParametersList(List<VariableTransmitterParameters> variableTransmitterParametersList) {
        this.variableTransmitterParametersList = variableTransmitterParametersList;
    }
    
    public void marshal(DataOutputStream dos) {
        super.marshal(dos);
        try {
            radioReferenceID.marshal(dos);
            dos.writeShort((short) radioNumber);
            radioEntityType.marshal(dos);
            dos.writeByte((byte) transmitState);
            dos.writeByte((byte) inputSource);
            dos.writeShort((short) variableTransmitterParametersList.size());
            antennaLocation.marshal(dos);
            relativeAntennaLocation.marshal(dos);
            dos.writeShort((short) antennaPatternType);
            dos.writeShort((short) getAntennaPatternCount());
            dos.writeLong((long) frequency);
            dos.writeFloat((float) transmitFrequencyBandwidth);
            dos.writeFloat((float) power);
            modulationType.marshal(dos);
            dos.writeShort((short) cryptoSystem);
            dos.writeShort((short) cryptoKeyId);
            dos.writeByte((byte) getModulationParameterCount());
            dos.writeShort((short) padding2);
            dos.writeByte((byte) padding3);
            
            int nrOfModulationBytes = 0;
            switch (getModulationType().getRadioSystem()) {
                case 6:                                  // CCTT SINCGARS
                    if (modulationParameters instanceof CcttSincgarsModulationParameters) {
                        CcttSincgarsModulationParameters ccttSincgarsModulationParameters = (CcttSincgarsModulationParameters) modulationParameters;
                        ccttSincgarsModulationParameters.marshal(dos);
                        nrOfModulationBytes = nrOfModulationBytes + ccttSincgarsModulationParameters.getMarshalledSize();
                    }
                    break;
                case 8:                                  // JTIDS/MIDS
                    if (modulationParameters instanceof JtidsMidsModulationParameters) {
                        JtidsMidsModulationParameters parameterRecord = (JtidsMidsModulationParameters) modulationParameters;
                        parameterRecord.marshal(dos);
                        nrOfModulationBytes = nrOfModulationBytes + parameterRecord.getMarshalledSize();
                    }
                    break;
                case 0:                                  // Other
                case 1:                                  // Generic
                case 2:                                  // HQ
                case 3:                                  // HQII
                case 4:                                  // HQIIA
                case 5:                                  // SINCGARS
                case 7:                                  // EPLRS
                default:
                    if (modulationParameters instanceof ModulationParametersGeneric) {
                        ModulationParametersGeneric modulationParameter = (ModulationParametersGeneric) modulationParameters;
                        modulationParameter.marshal(dos);
                        nrOfModulationBytes += nrOfModulationBytes + modulationParameter.getMarshalledSize();
                        break;
                    }
            }
            //Add padding up to 64 bit border
            if (nrOfModulationBytes % 8 > 0) {
                int remainder = 0;
                switch (nrOfModulationBytes % 8) {
                    case 1:
                        remainder = 7;
                        break;
                    case 2:
                        remainder = 6;
                        break;
                    case 3:
                        remainder = 5;
                        break;
                    case 4:
                        remainder = 4;
                        break;
                    case 5:
                        remainder = 3;
                        break;
                    case 6:
                        remainder = 2;
                        break;
                    case 7:
                        remainder = 1;
                        break;
                }
                for (int i = 1; i <= remainder; i++) {
                    dos.writeByte(0);
                }
            }
            switch (antennaPatternType) {
                case 2://Beam
                    if (antennaPattern instanceof BeamAntennaPattern) {
                        ((BeamAntennaPattern) antennaPattern).marshal(dos);
                    }
                    break;
                case 6://Omnidirectional (Toroidal Radiation Pattern)
                    break;
                default://Generic record
                    if (antennaPattern instanceof AntennaPatternGeneric) {
                        ((AntennaPatternGeneric) antennaPattern).marshal(dos);
                    }
                    break;
            }
            
            for (int idx = 0; idx < variableTransmitterParametersList.size(); idx++) {
                variableTransmitterParametersList.get(idx).marshal(dos);
            }
        } // end try  // end try  // end try  // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        super.unmarshal(dis);
        
        try {
            radioReferenceID.unmarshal(dis);
            radioNumber = (int) dis.readUnsignedShort();
            radioEntityType.unmarshal(dis);
            transmitState = (short) dis.readUnsignedByte();
            inputSource = (short) dis.readUnsignedByte();
            variableTransmitterParameterCount = (int) dis.readUnsignedShort();
            antennaLocation.unmarshal(dis);
            relativeAntennaLocation.unmarshal(dis);
            antennaPatternType = (int) dis.readUnsignedShort();
            antennaPatternCount = (int) dis.readUnsignedShort();
            frequency = dis.readLong();
            transmitFrequencyBandwidth = dis.readFloat();
            power = dis.readFloat();
            modulationType.unmarshal(dis);
            cryptoSystem = (int) dis.readUnsignedShort();
            cryptoKeyId = (int) dis.readUnsignedShort();
            modulationParameterCount = (short) dis.readUnsignedByte();
            padding2 = (int) dis.readUnsignedShort();
            padding3 = (short) dis.readUnsignedByte();
            int remainder = 0;
            int modRecordSize = 0;
            switch (getModulationType().getRadioSystem()) {
                case 6:                                  // CCTT SINCGARS
                    CcttSincgarsModulationParameters ccttSincgarsModulationParameters = new CcttSincgarsModulationParameters();
                    ccttSincgarsModulationParameters.unmarshal(dis);
                    modulationParameters = ccttSincgarsModulationParameters;
                    modRecordSize = 15;
                    break;
                case 8:                                  // JTIDS/MIDS
                    JtidsMidsModulationParameters jtidsMidsModulationParameters = new JtidsMidsModulationParameters();
                    jtidsMidsModulationParameters.unmarshal(dis);
                    modulationParameters = jtidsMidsModulationParameters;
                    modRecordSize = 8;
                    break;
                case 0:                                  // Other
                case 1:                                  // Generic
                case 2:                                  // HQ
                case 3:                                  // HQII
                case 4:                                  // HQIIA
                case 5:                                  // SINCGARS
                case 7:                                  // EPLRS
                default:
                    ModulationParametersGeneric modPar = new ModulationParametersGeneric();
                    modPar.unmarshal(dis);
                    modulationParameters = modPar;
                    modRecordSize = modPar.getMarshalledSize();
                    break;
            }
            //Calculate how many bytes need to be read to end on a 64 bit border
            if (modRecordSize % 8 != 0) {
                remainder = 8 - (modRecordSize % 8);
                for (int i = 1; i <= remainder; i++) {
                    dis.readByte();
                }
            }
            switch (antennaPatternType) {
                case 2://Beam
                    BeamAntennaPattern beamAntennaPattern = new BeamAntennaPattern();
                    beamAntennaPattern.unmarshal(dis);
                    antennaPattern = beamAntennaPattern;
                    break;
                case 6://Omnidirectional (Toroidal Radiation Pattern)
                    break;
                default://Generic record
                    AntennaPatternGeneric antennaPatternGeneric = new AntennaPatternGeneric();
                    for (int i = 0; i < antennaPatternCount; i++) {
                        antennaPatternGeneric.unmarshal(dis, antennaPatternCount);
                    }
                    antennaPattern = antennaPatternGeneric;
                    break;
            }
            for (int idx = 0; idx < variableTransmitterParameterCount; idx++) {
                VariableTransmitterParameters varTransPar = new VariableTransmitterParameters();
                varTransPar.unmarshal(dis);
                variableTransmitterParametersList.add(varTransPar);
            }
        } // end try  // end try  // end try  // end try 
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
        radioReferenceID.marshal(buff);
        buff.putShort((short) radioNumber);
        radioEntityType.marshal(buff);
        buff.put((byte) transmitState);
        buff.put((byte) inputSource);
        buff.putShort((short) variableTransmitterParametersList.size());
        antennaLocation.marshal(buff);
        relativeAntennaLocation.marshal(buff);
        buff.putShort((short) antennaPatternType);
        buff.putShort((short) getAntennaPatternCount());
        buff.putLong((long) frequency);
        buff.putFloat((float) transmitFrequencyBandwidth);
        buff.putFloat((float) power);
        modulationType.marshal(buff);
        buff.putShort((short) cryptoSystem);
        buff.putShort((short) cryptoKeyId);
        buff.put((byte) getModulationParameterCount());
        buff.putShort((short) padding2);
        buff.put((byte) padding3);
        
        int nrOfModulationBytes = 0;
        switch (getModulationType().getRadioSystem()) {
            case 6:                                  // CCTT SINCGARS
                CcttSincgarsModulationParameters ccttSincgarsModulationParameters = (CcttSincgarsModulationParameters) modulationParameters;
                ccttSincgarsModulationParameters.marshal(buff);
                nrOfModulationBytes = nrOfModulationBytes + ccttSincgarsModulationParameters.getMarshalledSize();
                break;
            case 8:                                  // JTIDS/MIDS
                JtidsMidsModulationParameters parameterRecord = (JtidsMidsModulationParameters) modulationParameters;
                parameterRecord.marshal(buff);
                nrOfModulationBytes = nrOfModulationBytes + parameterRecord.getMarshalledSize();
                break;
            case 0:                                  // Other
            case 1:                                  // Generic
            case 2:                                  // HQ
            case 3:                                  // HQII
            case 4:                                  // HQIIA
            case 5:                                  // SINCGARS
            case 7:                                  // EPLRS
            default:
                ModulationParametersGeneric modulationParameter = (ModulationParametersGeneric) modulationParameters;
                modulationParameter.marshal(buff);
                nrOfModulationBytes += nrOfModulationBytes + modulationParameter.getMarshalledSize();
                break;
        }
        //Add padding up to 64 bit border
        if (nrOfModulationBytes % 8 > 0) {
            int remainder = 0;
            switch (nrOfModulationBytes % 8) {
                case 1:
                    remainder = 7;
                    break;
                case 2:
                    remainder = 6;
                    break;
                case 3:
                    remainder = 5;
                    break;
                case 4:
                    remainder = 4;
                    break;
                case 5:
                    remainder = 3;
                    break;
                case 6:
                    remainder = 2;
                    break;
                case 7:
                    remainder = 1;
                    break;
            }
            for (int i = 1; i <= remainder; i++) {
                buff.put((byte) 0);
            }
        }
        switch (antennaPatternType) {
            case 2://Beam
                ((BeamAntennaPattern) antennaPattern).marshal(buff);
                break;
            case 6://Omnidirectional (Toroidal Radiation Pattern)
                break;
            default://generic record
                ((AntennaPatternGeneric) antennaPattern).marshal(buff);
                break;
        }
        
        for (int idx = 0; idx < variableTransmitterParametersList.size(); idx++) {
            variableTransmitterParametersList.get(idx).marshal(buff);
        }        
        
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
        
        radioReferenceID.unmarshal(buff);
        radioNumber = (int) (buff.getShort() & 0xFFFF);
        radioEntityType.unmarshal(buff);
        transmitState = (short) (buff.get() & 0xFF);
        inputSource = (short) (buff.get() & 0xFF);
        variableTransmitterParameterCount = (int) (buff.getShort() & 0xFFFF);
        antennaLocation.unmarshal(buff);
        relativeAntennaLocation.unmarshal(buff);
        antennaPatternType = (int) (buff.getShort() & 0xFFFF);
        antennaPatternCount = (int) (buff.getShort() & 0xFFFF);
        frequency = buff.getLong();
        transmitFrequencyBandwidth = buff.getFloat();
        power = buff.getFloat();
        modulationType.unmarshal(buff);
        cryptoSystem = (int) (buff.getShort() & 0xFFFF);
        cryptoKeyId = (int) (buff.getShort() & 0xFFFF);
        modulationParameterCount = (short) (buff.get() & 0xFF);
        padding2 = (int) (buff.getShort() & 0xFFFF);
        padding3 = (short) (buff.get() & 0xFF);
        int remainder = 0;
        int modRecordSize = 0;
        switch (getModulationType().getRadioSystem()) {
            case 6:                                  // CCTT SINCGARS
                CcttSincgarsModulationParameters ccttSincgarsModulationParameters = new CcttSincgarsModulationParameters();
                ccttSincgarsModulationParameters.unmarshal(buff);
                modulationParameters = ccttSincgarsModulationParameters;
                modRecordSize = 15;
                break;
            case 8:                                  // JTIDS/MIDS
                JtidsMidsModulationParameters jtidsMidsModulationParameters = new JtidsMidsModulationParameters();
                jtidsMidsModulationParameters.unmarshal(buff);
                modulationParameters = jtidsMidsModulationParameters;
                modRecordSize = 8;
                break;
            case 0:                                  // Other
            case 1:                                  // Generic
            case 2:                                  // HQ
            case 3:                                  // HQII
            case 4:                                  // HQIIA
            case 5:                                  // SINCGARS
            case 7:                                  // EPLRS
            default:
                ModulationParameters modPar = new ModulationParameters();
                modPar.unmarshal(buff);
                modulationParameters = modPar;
                modRecordSize = modPar.getMarshalledSize();
                break;
        }
        //Calculate how many bytes need to be read to end on a 64 bit border
        if (modRecordSize % 8 != 0) {
            remainder = 8 - (modRecordSize % 8);
            for (int i = 1; i <= remainder; i++) {
                buff.get();
            }
        }
        
        switch (antennaPatternType) {
            case 2://Beam
                BeamAntennaPattern beamAntennaPattern = new BeamAntennaPattern();
                beamAntennaPattern.unmarshal(buff);
                antennaPattern = beamAntennaPattern;
                break;
            
            case 6://Omnidirectional (Toroidal Radiation Pattern)
                break;
            default://generic record
                AntennaPatternGeneric antennaPatternGeneric = new AntennaPatternGeneric();
                for (int i = 0; i < antennaPatternCount; i++) {
                    antennaPatternGeneric.unmarshal(buff, antennaPatternCount);
                }
                antennaPattern = antennaPatternGeneric;
                break;
        }
        
        for (int idx = 0; idx < variableTransmitterParameterCount; idx++) {
            VariableTransmitterParameters varTransPar = new VariableTransmitterParameters();
            varTransPar.unmarshal(buff);
            variableTransmitterParametersList.add(varTransPar);
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
        
        if (!(obj instanceof TransmitterPdu)) {
            return false;
        }
        
        final TransmitterPdu rhs = (TransmitterPdu) obj;
        
        if (!(radioReferenceID.equals(rhs.radioReferenceID))) {
            ivarsEqual = false;
        }
        if (!(radioNumber == rhs.radioNumber)) {
            ivarsEqual = false;
        }
        if (!(radioEntityType.equals(rhs.radioEntityType))) {
            ivarsEqual = false;
        }
        if (!(transmitState == rhs.transmitState)) {
            ivarsEqual = false;
        }
        if (!(inputSource == rhs.inputSource)) {
            ivarsEqual = false;
        }
        if (!(variableTransmitterParameterCount == rhs.variableTransmitterParameterCount)) {
            ivarsEqual = false;
        }
        if (!(antennaLocation.equals(rhs.antennaLocation))) {
            ivarsEqual = false;
        }
        if (!(relativeAntennaLocation.equals(rhs.relativeAntennaLocation))) {
            ivarsEqual = false;
        }
        if (!(antennaPatternType == rhs.antennaPatternType)) {
            ivarsEqual = false;
        }
        if (!(antennaPatternCount == rhs.antennaPatternCount)) {
            ivarsEqual = false;
        }
        if (!(frequency == rhs.frequency)) {
            ivarsEqual = false;
        }
        if (!(transmitFrequencyBandwidth == rhs.transmitFrequencyBandwidth)) {
            ivarsEqual = false;
        }
        if (!(power == rhs.power)) {
            ivarsEqual = false;
        }
        if (!(modulationType.equals(rhs.modulationType))) {
            ivarsEqual = false;
        }
        if (!(cryptoSystem == rhs.cryptoSystem)) {
            ivarsEqual = false;
        }
        if (!(cryptoKeyId == rhs.cryptoKeyId)) {
            ivarsEqual = false;
        }
        if (!(modulationParameterCount == rhs.modulationParameterCount)) {
            ivarsEqual = false;
        }
        if (!(padding2 == rhs.padding2)) {
            ivarsEqual = false;
        }
        if (!(padding3 == rhs.padding3)) {
            ivarsEqual = false;
        }
        
        switch (getModulationType().getRadioSystem()) {
            case 6:                                  // CCTT SINCGARS
                if (!(((CcttSincgarsModulationParameters) modulationParameters).equals(((CcttSincgarsModulationParameters) rhs.modulationParameters)))) {
                    ivarsEqual = false;
                }
                break;
            case 8:                                  // JTIDS/MIDS
                if (!(((JtidsMidsModulationParameters) modulationParameters).equals(((JtidsMidsModulationParameters) rhs.modulationParameters)))) {
                    ivarsEqual = false;
                }
                break;
            case 0:                                  // Other
            case 1:                                  // Generic
            case 2:                                  // HQ
            case 3:                                  // HQII
            case 4:                                  // HQIIA
            case 5:                                  // SINCGARS
            case 7:                                  // EPLRS
            default:
                if (!((ModulationParametersGeneric) modulationParameters).equals(((ModulationParametersGeneric) rhs.modulationParameters))) {
                    ivarsEqual = false;
                }
                break;
        }
        switch (antennaPatternType) {
            case 2:
                if (!(((BeamAntennaPattern) antennaPattern).equals(((BeamAntennaPattern) rhs.antennaPattern)))) {
                    ivarsEqual = false;
                }
                break;
            case 6:
                break;
            default:
                if (!(antennaPattern.equals(rhs.antennaPattern))) {
                    ivarsEqual = false;
                }
                break;
        }
        
        for (int idx = 0; idx < variableTransmitterParametersList.size(); idx++) {
            if (!(variableTransmitterParametersList.get(idx).equals(rhs.variableTransmitterParametersList.get(idx)))) {
                ivarsEqual = false;
            }            
        }
        return ivarsEqual && super.equalsImpl(rhs);
    }
} // end of class
