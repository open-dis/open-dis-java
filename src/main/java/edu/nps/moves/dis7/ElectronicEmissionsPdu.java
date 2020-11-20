package edu.nps.moves.dis7;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Section 5.3.7.1. Information about active electronic warfare (EW) emissions and active EW countermeasures shall be communicated using an
 * Electromagnetic Emission PDU. NOT COMPLETE
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All rights reserved. This work is licensed under the BSD open source
 * license, available at https://www.movesinstitute.org/licenses/bsd.html
 * @author DMcG
 */
public class ElectronicEmissionsPdu extends DistributedEmissionsFamilyPdu implements Serializable
{
    /** ID of the entity emitting */
    protected EntityID emittingEntityID = new EntityID();

    /** ID of event */
    protected EventIdentifier eventID = new EventIdentifier();

    /**
     * This field shall be used to indicate if the data in the PDU represents a state update or just data that has changed since issuance of
     * the last Electromagnetic Emission PDU [relative to the identified entity and emission system(s)].
     */
    protected short stateUpdateIndicator;

    /** This field shall specify the number of emission systems being described in the current PDU. */
    protected short numberOfSystems;

    /** padding */
    protected int paddingForEmissionsPdu;

    /** this field shall specify the length of this emitter system's data in 32-bit words. */
    protected short systemDataLength;

    /** the number of beams being described in the current PDU for the emitter system being described. */
    protected short numberOfBeams;

    /** information about a particular emitter system and shall be represented by an Emitter System record (see 6.2.23). */
    protected EmitterSystem emitterSystem = new EmitterSystem();

    /**
     * the location of the antenna beam source with respect to the emitting entity's coordinate system. This location shall be the origin of
     * the emitter coordinate system that shall have the same orientation as the entity coordinate system. This field shall be represented
     * by an Entity Coordinate Vector record see 6.2.95
     */
    protected Vector3Float location = new Vector3Float();

    /** Electronic emmissions systems THIS IS WRONG. It has the WRONG class type and will cause problems in any marshalling. */
    protected Beam beam = new Beam();

    /** Constructor */
    public ElectronicEmissionsPdu()
    {
        setPduType((short) 23);
        setPaddingForEmissionsPdu((int) 0);
    }

    public int getMarshalledSize()
    {
        int marshalSize = 0;

        marshalSize = super.getMarshalledSize();
        marshalSize = marshalSize + emittingEntityID.getMarshalledSize();  // emittingEntityID
        marshalSize = marshalSize + eventID.getMarshalledSize();  // eventID
        marshalSize = marshalSize + 1;  // stateUpdateIndicator
        marshalSize = marshalSize + 1;  // numberOfSystems
        marshalSize = marshalSize + 2;  // paddingForEmissionsPdu
        marshalSize = marshalSize + 1;  // systemDataLength
        marshalSize = marshalSize + 1;  // numberOfBeams
        marshalSize = marshalSize + 2;  // paddingForEmissionsPdu
        marshalSize = marshalSize + emitterSystem.getMarshalledSize();  // emitterSystem
        marshalSize = marshalSize + location.getMarshalledSize();  // location
        marshalSize = marshalSize + beam.getMarshalledSize(); // beam

        return marshalSize;
    }

    public EntityID getEmittingEntityID()
    {
        return emittingEntityID;
    }

    public void setEmittingEntityID(EntityID pEmittingEntityID)
    {
        emittingEntityID = pEmittingEntityID;
    }

    public EventIdentifier getEventID()
    {
        return eventID;
    }

    public void setEventID(EventIdentifier pEventID)
    {
        eventID = pEventID;
    }

    public short getStateUpdateIndicator()
    {
        return stateUpdateIndicator;
    }

    public void setStateUpdateIndicator(short pStateUpdateIndicator)
    {
        stateUpdateIndicator = pStateUpdateIndicator;
    }

    public short getNumberOfSystems()
    {
        return numberOfSystems;
    }

    public Beam getBeam()
    {
        return beam;
    }

    public void setBeam(Beam beam)
    {
        this.beam = beam;
    }

    /**
     * Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose. The
     * getnumberOfSystems method will also be based on the actual list length rather than this value. The method is simply here for java
     * bean completeness.
     */
    public void setNumberOfSystems(short pNumberOfSystems)
    {
        numberOfSystems = pNumberOfSystems;
    }

    public int getPaddingForEmissionsPdu()
    {
        return paddingForEmissionsPdu;
    }

    public void setPaddingForEmissionsPdu(int pPaddingForEmissionsPdu)
    {
        paddingForEmissionsPdu = pPaddingForEmissionsPdu;
    }

    public short getSystemDataLength()
    {
        return systemDataLength;
    }

    public void setSystemDataLength(short pSystemDataLength)
    {
        systemDataLength = pSystemDataLength;
    }

    public short getNumberOfBeams()
    {
        return numberOfBeams;
    }

    public void setNumberOfBeams(short pNumberOfBeams)
    {
        numberOfBeams = pNumberOfBeams;
    }

    public EmitterSystem getEmitterSystem()
    {
        return emitterSystem;
    }

    public void setEmitterSystem(EmitterSystem pEmitterSystem)
    {
        emitterSystem = pEmitterSystem;
    }

    public Vector3Float getLocation()
    {
        return location;
    }

    public void setLocation(Vector3Float pLocation)
    {
        location = pLocation;
    }


    public void marshal(DataOutputStream dos)
    {
        super.marshal(dos);
        try {
            emittingEntityID.marshal(dos);
            eventID.marshal(dos);
            dos.writeByte((byte) stateUpdateIndicator);
            dos.writeByte((byte) numberOfSystems);
            dos.writeShort((short) paddingForEmissionsPdu);
            dos.writeByte((byte) systemDataLength);
            dos.writeByte((byte) numberOfBeams);
            emitterSystem.marshal(dos);
            location.marshal(dos);
            beam.marshal(dos);

        } // end try
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis)
    {
        super.unmarshal(dis);

        try {
            emittingEntityID.unmarshal(dis);
            eventID.unmarshal(dis);
            stateUpdateIndicator = (short) dis.readUnsignedByte();
            numberOfSystems = (short) dis.readUnsignedByte();
            paddingForEmissionsPdu = (int) dis.readUnsignedShort();
            systemDataLength = (short) dis.readUnsignedByte();
            numberOfBeams = (short) dis.readUnsignedByte();
            emitterSystem.unmarshal(dis);
            location.unmarshal(dis);
            beam.unmarshal(dis);

        } // end try
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of unmarshal method


    /**
     * Packs a Pdu into the ByteBuffer.
     * @param buff The ByteBuffer at the position to begin writing
     * @throws java.nio.BufferOverflowException if buff is too small
     * @throws java.nio.ReadOnlyBufferException if buff is read only
     * @see java.nio.ByteBuffer
     * @since ??
     */
    public void marshal(java.nio.ByteBuffer buff)
    {
        super.marshal(buff);
        emittingEntityID.marshal(buff);
        eventID.marshal(buff);
        buff.put((byte) stateUpdateIndicator);
        buff.put((byte) numberOfSystems);
        buff.putShort((short) paddingForEmissionsPdu);
        buff.put((byte) systemDataLength);
        buff.put((byte) numberOfBeams);
        emitterSystem.marshal(buff);
        location.marshal(buff);
        beam.marshal(buff);

    } // end of marshal method

    /**
     * Unpacks a Pdu from the underlying data.
     * @param buff The ByteBuffer at the position to begin reading
     * @throws java.nio.BufferUnderflowException if buff is too small
     * @see java.nio.ByteBuffer
     * @since ??
     */
    public void unmarshal(java.nio.ByteBuffer buff)
    {
        super.unmarshal(buff);

        emittingEntityID.unmarshal(buff);
        eventID.unmarshal(buff);
        stateUpdateIndicator = (short) ( buff.get() & 0xFF );
        numberOfSystems = (short) ( buff.get() & 0xFF );
        paddingForEmissionsPdu = (int) ( buff.getShort() & 0xFFFF );
        systemDataLength = (short) ( buff.get() & 0xFF );
        numberOfBeams = (short) ( buff.get() & 0xFF );
        buff.getShort(); // remove padding
        emitterSystem.unmarshal(buff);
        location.unmarshal(buff);
        beam.unmarshal(buff);

    } // end of unmarshal method


    /*
     * The equals method doesn't always work--mostly it works only on classes that consist only of primitives. Be careful.
     */
    @Override
    public boolean equals(Object obj)
    {

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
    public boolean equalsImpl(Object obj)
    {
        boolean ivarsEqual = true;

        if (!( obj instanceof ElectronicEmissionsPdu )) {
            return false;
        }

        final ElectronicEmissionsPdu rhs = (ElectronicEmissionsPdu) obj;

        if (!( emittingEntityID.equals(rhs.emittingEntityID) )) {
            ivarsEqual = false;
        }
        if (!( eventID.equals(rhs.eventID) )) {
            ivarsEqual = false;
        }
        if (!( stateUpdateIndicator == rhs.stateUpdateIndicator )) {
            ivarsEqual = false;
        }
        if (!( numberOfSystems == rhs.numberOfSystems )) {
            ivarsEqual = false;
        }
        if (!( paddingForEmissionsPdu == rhs.paddingForEmissionsPdu )) {
            ivarsEqual = false;
        }
        if (!( systemDataLength == rhs.systemDataLength )) {
            ivarsEqual = false;
        }
        if (!( numberOfBeams == rhs.numberOfBeams )) {
            ivarsEqual = false;
        }
        if (!( emitterSystem.equals(rhs.emitterSystem) )) {
            ivarsEqual = false;
        }
        if (!( location.equals(rhs.location) )) {
            ivarsEqual = false;
        }
        if (!( beam.equals(rhs.beam) )) {
            ivarsEqual = false;
        }



        return ivarsEqual && super.equalsImpl(rhs);
    }
} // end of class
