package edu.nps.moves.dis7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * This field shall specify information about a particular active
 * electronicEmissisionBeamData. Section 7.6.2.
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class ElectronicEmissisionBeamData extends Object implements Serializable {

    /**
     * Specifies the length of this electronicEmissisionBeamData's data, 8 bit
     * unsigned int
     */
    protected short beamDataLength;

    /**
     * unique electronicEmissisionBeamData number, 8 bit unsigned int
     */
    protected short beamNumber;

    /**
     * Used in conjunction with the Emitter Name field as a database primary
     * key, this field shall specify a number by which receiving entities
     * reference stored database parameters required to regenerate the
     * electronicEmissisionBeamData., 16 bit unsigned int
     */
    protected int beamParameterIndex;

    /**
     * specify dynamic parameters of the emitter and shall be represented by an
     * EE Fundamental Parameter Data record (see 6.2.22).
     */
    protected EEFundamentalParameterData fundamentalParameterData = new EEFundamentalParameterData();

    /**
     * specify parameters of the electronicEmissisionBeamData and shall be
     * represented by a ElectronicEmissisionBeamData Data record (see 6.2.11)
     */
    protected BeamData beamData = new BeamData();

    /**
     * specify the intended use of a particular electronicEmissisionBeamData.
     * Typical functions include search, acquisition, tracking, illumination,
     * jamming, and so on. This field is intended to help receiving entities
     * determine the emission mode represented by the
     * electronicEmissisionBeamData. This field shall be represented by an 8-bit
     * enumeration
     */
    protected short beamFunction;

    /**
     * This field, in conjunction with the High-Density Track/Jam field, shall
     * identify, for the current PDU and emitter electronicEmissisionBeamData,
     * the number of entities tracked or under illumination (as appropriate for
     * an emitter electronicEmissisionBeamData’s function) or the number of
     * targeted emitter beams (for jammers). This field shall be represented by
     * an 8-bit unsigned integer.
     */
    protected short numberOfTargets;

    /**
     * This field shall be used to indicate that receiving simulation
     * applications can assume that all viable targets in the field of regard
     * specified by the electronicEmissisionBeamData data are being tracked or
     * jammed. This field shall be represented by an 8-bit enumeration
     */
    protected short highDensityTrackJam;

    /**
     * This field shall indicate the status of the electronicEmissisionBeamData
     * (e.g., the electronicEmissisionBeamData is active or deactivated) and
     * shall be represented by the ElectronicEmissisionBeamData Status record
     * (see 6.2.12)
     */
    protected BeamStatus beamStatus = new BeamStatus();

    /**
     * Jamming Technique. This field shall be used to identify the jamming
     * method or methods and shall be represented by a Jamming Technique record
     * (see 6.2.49).
     */
    protected JammingTechnique jammingTechnique = new JammingTechnique();

    /**
     * This field is optional for any given electronicEmissisionBeamData. Rules
     * for inclusion and use are provided in 5.7.3.3, 5.7.3.7, and 5.7.3.8. When
     * included, this field shall be represented by a series of Track/Jam Data
     * records (see 6.2.90).
     */
    protected TrackJamData trackJamData = new TrackJamData();

    /**
     * Constructor
     */
    public ElectronicEmissisionBeamData() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 1;  // beamDataLength
        marshalSize = marshalSize + 1;  // beamNumber
        marshalSize = marshalSize + 2;  // beamParameterIndex
        marshalSize = marshalSize + fundamentalParameterData.getMarshalledSize();  // fundamentalParameterData
        marshalSize = marshalSize + beamData.getMarshalledSize();  // beamData
        marshalSize = marshalSize + 1;  // beamFunction
        marshalSize = marshalSize + 1;  // numberOfTargets
        marshalSize = marshalSize + 1;  // highDensityTrackJam
        marshalSize = marshalSize + beamStatus.getMarshalledSize();  // beamStatus
        marshalSize = marshalSize + jammingTechnique.getMarshalledSize();  // jammingTechnique
        if (numberOfTargets != 0) {
            marshalSize = marshalSize + trackJamData.getMarshalledSize();  // trackJamData
        }

        return marshalSize;
    }

    public EEFundamentalParameterData getFundamentalParameterData() {
        return fundamentalParameterData;
    }

    public void setFundamentalParameterData(EEFundamentalParameterData fundamentalParameterData) {
        this.fundamentalParameterData = fundamentalParameterData;
    }

    public BeamData getBeamData() {
        return beamData;
    }

    public void setBeamData(BeamData beamData) {
        this.beamData = beamData;
    }

    public short getBeamFunction() {
        return beamFunction;
    }

    public void setBeamFunction(short beamFunction) {
        this.beamFunction = beamFunction;
    }

    public short getNumberOfTargets() {
        return numberOfTargets;
    }

    public void setNumberOfTargets(short numberOfTargets) {
        this.numberOfTargets = numberOfTargets;
    }

    public short getHighDensityTrackJam() {
        return highDensityTrackJam;
    }

    public void setHighDensityTrackJam(short highDensityTrackJam) {
        this.highDensityTrackJam = highDensityTrackJam;
    }

    public BeamStatus getBeamStatus() {
        return beamStatus;
    }

    public void setBeamStatus(BeamStatus beamStatus) {
        this.beamStatus = beamStatus;
    }

    public JammingTechnique getJammingTechnique() {
        return jammingTechnique;
    }

    public void setJammingTechnique(JammingTechnique jammingTechnique) {
        this.jammingTechnique = jammingTechnique;
    }

    public TrackJamData getTrackJamData() {
        return trackJamData;
    }

    public void setTrackJamData(TrackJamData trackJamData) {
        this.trackJamData = trackJamData;
    }

    public int getBeamDataLength() {
        return beamDataLength;
    }

    public void setBeamDataLength(short beamDataLength) {
        this.beamDataLength = beamDataLength;
    }

    public short getBeamNumber() {
        return beamNumber;
    }

    public void setBeamNumber(short pEmitterFunction) {
        beamNumber = pEmitterFunction;
    }

    public int getBeamParameterIndex() {
        return beamParameterIndex;
    }

    public void setBeamParameterIndex(short pEmitterIDNumber) {
        beamParameterIndex = pEmitterIDNumber;
    }

    public void marshal(DataOutputStream dos) {
        try {
            dos.writeByte((byte) beamDataLength);
            dos.writeByte((byte) beamNumber);
            dos.writeShort(beamParameterIndex);
            fundamentalParameterData.marshal(dos);
            beamData.marshal(dos);
            dos.writeByte((byte) beamFunction);
            dos.writeByte((byte) numberOfTargets);
            dos.writeByte((byte) highDensityTrackJam);
            beamStatus.marshal(dos);
            jammingTechnique.marshal(dos);
            if (numberOfTargets != 0) { //When the Number of Targets value is zero, this field shall be omitted
                trackJamData.marshal(dos);
            }
        } // end try
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    /**
     * Packs a Pdu into the ByteBuffer.
     *
     * @param buff The ByteBuffer at the position to begin writing
     * @throws java.nio.BufferOverflowException if buff is too small
     * @throws java.nio.ReadOnlyBufferException if buff is read only
     * @see java.nio.ByteBuffer
     * @since ??
     */
    public void marshal(java.nio.ByteBuffer buff) {
        buff.put((byte) beamDataLength);
        buff.put((byte) beamNumber);
        buff.putShort((short) beamParameterIndex);
        fundamentalParameterData.marshal(buff);
        beamData.marshal(buff);
        buff.put((byte) beamFunction);
        buff.put((byte) numberOfTargets);
        buff.put((byte) highDensityTrackJam);
        beamStatus.marshal(buff);
        jammingTechnique.marshal(buff);
        if (numberOfTargets != 0) { //When the Number of Targets value is zero, this field shall be omitted
            trackJamData.marshal(buff);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        try {
            beamDataLength = (short) dis.readUnsignedByte();
            beamNumber = (short) dis.readUnsignedByte();
            beamParameterIndex = dis.readUnsignedShort();
            fundamentalParameterData.unmarshal(dis);
            beamData.unmarshal(dis);
            beamFunction = (short) dis.readUnsignedShort();
            numberOfTargets = (short) dis.readUnsignedShort();
            highDensityTrackJam = (short) dis.readUnsignedShort();
            beamStatus.unmarshal(dis);
            jammingTechnique.unmarshal(dis);
            if (numberOfTargets != 0) { //When the Number of Targets value is zero, this field shall be omitted
                trackJamData.unmarshal(dis);
            }

        } // end try
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of unmarshal method

    /**
     * Unpacks a Pdu from the underlying data.
     *
     * @param buff The ByteBuffer at the position to begin reading
     * @throws java.nio.BufferUnderflowException if buff is too small
     * @see java.nio.ByteBuffer
     * @since ??
     */
    public void unmarshal(java.nio.ByteBuffer buff) {
        beamDataLength = (short) (buff.get() & 0xFF);
        beamNumber = (short) (buff.get() & 0xFF);
        beamParameterIndex = (buff.getShort() & 0xFFFF);
        fundamentalParameterData.unmarshal(buff);
        beamData.unmarshal(buff);
        beamFunction = (short) (buff.get() & 0xFF);
        numberOfTargets = (short) (buff.get() & 0xFF);
        highDensityTrackJam = (short) (buff.get() & 0xFF);
        beamStatus.unmarshal(buff);
        jammingTechnique.unmarshal(buff);
        if (numberOfTargets != 0) { //When the Number of Targets value is zero, this field shall be omitted
            trackJamData.unmarshal(buff);
        }
    } // end of unmarshal method

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ElectronicEmissisionBeamData electronicEmissisionBeamData = (ElectronicEmissisionBeamData) o;
        return beamDataLength == electronicEmissisionBeamData.beamDataLength
                && beamNumber == electronicEmissisionBeamData.beamNumber
                && beamParameterIndex == electronicEmissisionBeamData.beamParameterIndex
                && beamFunction == electronicEmissisionBeamData.beamFunction
                && numberOfTargets == electronicEmissisionBeamData.numberOfTargets
                && highDensityTrackJam == electronicEmissisionBeamData.highDensityTrackJam
                && Objects.equals(fundamentalParameterData, electronicEmissisionBeamData.fundamentalParameterData)
                && Objects.equals(beamData, electronicEmissisionBeamData.beamData)
                && Objects.equals(beamStatus, electronicEmissisionBeamData.beamStatus)
                && Objects.equals(jammingTechnique, electronicEmissisionBeamData.jammingTechnique)
                && Objects.equals(trackJamData, electronicEmissisionBeamData.trackJamData);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(beamDataLength, beamNumber, beamParameterIndex, fundamentalParameterData, beamData, beamFunction, numberOfTargets, highDensityTrackJam, beamStatus, jammingTechnique, trackJamData);
    }

} // end of class
