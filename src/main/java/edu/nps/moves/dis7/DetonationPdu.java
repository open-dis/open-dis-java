package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;

/**
 * Detonation or impact of munitions, as well as, non-munition explosions, the
 * burst or initial bloom of chaff, and the ignition of a flare shall be
 * indicated. Section 7.3.3 COMPLETE
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DetonationPdu extends WarfareFamilyPdu implements Serializable {

    /**
     * ID of the expendable entity, Section 7.3.3
     */
    protected EntityID explodingEntityID = new EntityID();

    /**
     * ID of event, Section 7.3.3
     */
    protected EventIdentifier eventID = new EventIdentifier();

    /**
     * velocity of the munition immediately before detonation/impact, Section
     * 7.3.3
     */
    protected Vector3Float velocity = new Vector3Float();

    /**
     * location of the munition detonation, the expendable detonation, Section
     * 7.3.3
     */
    protected Vector3Double locationInWorldCoordinates = new Vector3Double();

    /**
     * Describes the detonation represented, Section 7.3.3
     */
    protected Descriptor descriptor = new MunitionDescriptor();

    /**
     * Velocity of the ammunition, Section 7.3.3
     */
    protected Vector3Float locationOfEntityCoordinates = new Vector3Float();

    /**
     * result of the detonation, Section 7.3.3
     */
    protected short detonationResult;

    /**
     * How many articulation parameters we have, Section 7.3.3
     */
    protected short numberOfVariableParameters;

    /**
     * padding
     */
    protected int pad;

    /**
     * specify the parameter values for each Variable Parameter record, Section
     * 7.3.3
     */
    protected List< VariableParameter> variableParameters = new ArrayList< VariableParameter>();

    /**
     * Constructor
     */
    public DetonationPdu() {
        setPduType((short) 3);
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = super.getMarshalledSize();
        marshalSize = marshalSize + explodingEntityID.getMarshalledSize();  // explodingEntityID
        marshalSize = marshalSize + eventID.getMarshalledSize();  // eventID
        marshalSize = marshalSize + velocity.getMarshalledSize();  // velocity
        marshalSize = marshalSize + locationInWorldCoordinates.getMarshalledSize();  // locationInWorldCoordinates
        int detonationTypeIndicator = getDetonationTypeIndicator();
        switch (detonationTypeIndicator){
            case 0:
            default:
                marshalSize = marshalSize + ((MunitionDescriptor) descriptor).getMarshalledSize();  //Munition descriptor                
                break;
            case 1:
            marshalSize = marshalSize + ((ExpendableDescriptor) descriptor).getMarshalledSize();  //Expendable descriptor                
                break;
            case 2:
            marshalSize = marshalSize + ((ExplosionDescriptor) descriptor).getMarshalledSize();  //Expendable descriptor                
                break;
        }
        marshalSize = marshalSize + locationOfEntityCoordinates.getMarshalledSize();  // locationOfEntityCoordinates
        marshalSize = marshalSize + 1;  // detonationResult
        marshalSize = marshalSize + 1;  // numberOfVariableParameters
        marshalSize = marshalSize + 2;  // pad
         for (int idx = 0; idx < variableParameters.size(); idx++) {
            VariableParameter listElement = variableParameters.get(idx);
            switch (listElement.recordType) {
                case 0:
                    marshalSize = marshalSize + ((ArticulatedParts) listElement).getMarshalledSize();
                    break;
                case 1:
                    marshalSize = marshalSize + ((AttachedParts) listElement).getMarshalledSize();
                    break;
                case 2:
                    marshalSize = marshalSize + ((SeparationVP) listElement).getMarshalledSize();
                    break;
                case 3:
                    marshalSize = marshalSize + ((EntityTypeVP) listElement).getMarshalledSize();
                    break;
                case 4:
                    marshalSize = marshalSize + ((EntityAssociation) listElement).getMarshalledSize();
                    break;
            }
        }  

        return marshalSize;
    }

    public void setExplodingEntityID(EntityID pExplodingEntityID) {
        explodingEntityID = pExplodingEntityID;
    }

    public EntityID getExplodingEntityID() {
        return explodingEntityID;
    }

    public void setEventID(EventIdentifier pEventID) {
        eventID = pEventID;
    }

    public EventIdentifier getEventID() {
        return eventID;
    }

    public void setVelocity(Vector3Float pVelocity) {
        velocity = pVelocity;
    }

    public Vector3Float getVelocity() {
        return velocity;
    }

    public void setLocationInWorldCoordinates(Vector3Double pLocationInWorldCoordinates) {
        locationInWorldCoordinates = pLocationInWorldCoordinates;
    }

    public Vector3Double getLocationInWorldCoordinates() {
        return locationInWorldCoordinates;
    }

    public void setDescriptor(Descriptor pDescriptor) {
        descriptor = pDescriptor;
    }

    public Descriptor getDescriptor() {
        return descriptor;
    }

    public void setLocationOfEntityCoordinates(Vector3Float pLocationOfEntityCoordinates) {
        locationOfEntityCoordinates = pLocationOfEntityCoordinates;
    }

    public Vector3Float getLocationOfEntityCoordinates() {
        return locationOfEntityCoordinates;
    }

    public void setDetonationResult(short pDetonationResult) {
        detonationResult = pDetonationResult;
    }

    public short getDetonationResult() {
        return detonationResult;
    }

    public short getNumberOfVariableParameters() {
        return (short) variableParameters.size();
    }

    /**
     * Note that setting this value will not change the marshalled value. The
     * list whose length this describes is used for that purpose. The
     * getnumberOfVariableParameters method will also be based on the actual
     * list length rather than this value. The method is simply here for java
     * bean completeness.
     */
    public void setNumberOfVariableParameters(short pNumberOfVariableParameters) {
        numberOfVariableParameters = pNumberOfVariableParameters;
    }

    public void setPad(int pPad) {
        pad = pPad;
    }

    public int getPad() {
        return pad;
    }

    public void setVariableParameters(List<VariableParameter> pVariableParameters) {
        variableParameters = pVariableParameters;
    }

    public List<VariableParameter> getVariableParameters() {
        return variableParameters;
    }

    public void marshal(DataOutputStream dos) {
        super.marshal(dos);
        try {
            explodingEntityID.marshal(dos);
            eventID.marshal(dos);
            velocity.marshal(dos);
            locationInWorldCoordinates.marshal(dos);
            int detonationTypeIndicator = getDetonationTypeIndicator();
            switch (detonationTypeIndicator) {
                case 0:
                default:
                    ((MunitionDescriptor) descriptor).marshal(dos);                    
                    break;
                case 1:
                    ((ExpendableDescriptor) descriptor).marshal(dos);
                    break;
                case 2:
                    ((ExplosionDescriptor) descriptor).marshal(dos);
                    break;
            }
            
            locationOfEntityCoordinates.marshal(dos);
            dos.writeByte((byte) detonationResult);
            dos.writeByte((byte) variableParameters.size());
            dos.writeShort((short) pad);

            for (int idx = 0; idx < variableParameters.size(); idx++) {
                VariableParameter aVariableParameter = variableParameters.get(idx);
                switch (aVariableParameter.recordType) {
                    case 0:
                        ((ArticulatedParts) aVariableParameter).marshal(dos);
                        break;
                    case 1:
                        ((AttachedParts) aVariableParameter).marshal(dos);
                        break;
                    case 2:
                        ((SeparationVP) aVariableParameter).marshal(dos);
                        break;
                    case 3:
                        ((EntityTypeVP) aVariableParameter).marshal(dos);
                        break;
                    case 4:
                        ((EntityAssociation) aVariableParameter).marshal(dos);
                        break;
                }

            } // end of list marshalling


        } // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method

    public void unmarshal(DataInputStream dis) {
        super.unmarshal(dis);

        try {
            explodingEntityID.unmarshal(dis);
            eventID.unmarshal(dis);
            velocity.unmarshal(dis);
            locationInWorldCoordinates.unmarshal(dis);
            int detonationTypeIndicator = getDetonationTypeIndicator();            
            switch (detonationTypeIndicator) {
                case 0:
                default:
                descriptor = new MunitionDescriptor();
                ((MunitionDescriptor) descriptor).unmarshal(dis);                    
                    break;
                case 1:
                descriptor = new ExpendableDescriptor();
                ((ExpendableDescriptor) descriptor).unmarshal(dis);                    
                    break;
                case 2:
                descriptor = new ExplosionDescriptor();
                ((ExplosionDescriptor) descriptor).unmarshal(dis);                    
                    break;
            }
            locationOfEntityCoordinates.unmarshal(dis);
            detonationResult = (short) dis.readUnsignedByte();
            numberOfVariableParameters = (short) dis.readUnsignedByte();
            pad = (int) dis.readUnsignedShort();
            for (int idx = 0; idx < numberOfVariableParameters; idx++) {
                VariableParameter anX = new VariableParameter();
                anX.unmarshal(dis);
                switch (anX.getRecordType()) {
                    case 0:
                        anX = new ArticulatedParts();
                        ((ArticulatedParts) anX).unmarshal(dis);
                        break;
                    case 1:
                        anX = new AttachedParts();
                        ((AttachedParts) anX).unmarshal(dis);
                        break;
                    case 2:
                        anX = new SeparationVP();
                        ((SeparationVP) anX).unmarshal(dis);
                        break;
                    case 3:
                        anX = new EntityTypeVP();
                        ((EntityTypeVP) anX).unmarshal(dis);
                        break;
                    case 4:
                        anX = new EntityAssociation();
                        ((EntityAssociation) anX).unmarshal(dis);
                        break;
                }

                variableParameters.add(anX);
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
        explodingEntityID.marshal(buff);
        eventID.marshal(buff);
        velocity.marshal(buff);
        locationInWorldCoordinates.marshal(buff);
        int detonationTypeIndicator = getDetonationTypeIndicator();
        switch (detonationTypeIndicator) {
            case 0:
            default:
                ((MunitionDescriptor) descriptor).marshal(buff);
                break;
            case 1:
                ((ExpendableDescriptor) descriptor).marshal(buff);
                break;
            case 2:
                ((ExplosionDescriptor) descriptor).marshal(buff);
                break;
        }      
        locationOfEntityCoordinates.marshal(buff);
        buff.put((byte) detonationResult);
        buff.put((byte) variableParameters.size());
        buff.putShort((short) pad);

        for (int idx = 0; idx < variableParameters.size(); idx++) {
            VariableParameter aVariableParameter = variableParameters.get(idx);
            switch (aVariableParameter.getRecordType()) {
                case 0:
                    ((ArticulatedParts) aVariableParameter).marshal(buff);
                    break;
                case 1:
                    ((AttachedParts) aVariableParameter).marshal(buff);
                    break;
                case 2:
                    ((SeparationVP) aVariableParameter).marshal(buff);
                    break;
                case 3:
                    ((EntityTypeVP) aVariableParameter).marshal(buff);
                    break;
                case 4:
                    ((EntityAssociation) aVariableParameter).marshal(buff);
                    break;
            }

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

        explodingEntityID.unmarshal(buff);
        eventID.unmarshal(buff);
        velocity.unmarshal(buff);
        locationInWorldCoordinates.unmarshal(buff);
        int detonationTypeIndicator = getDetonationTypeIndicator();
        switch (detonationTypeIndicator) {
            case 0:
            default:
                descriptor = new MunitionDescriptor();
                ((MunitionDescriptor) descriptor).unmarshal(buff);
                break;
            case 1:
                descriptor = new ExpendableDescriptor();
                ((ExpendableDescriptor) descriptor).unmarshal(buff);
                break;
            case 2:
                descriptor = new ExplosionDescriptor();
                ((ExplosionDescriptor) descriptor).unmarshal(buff);
                break;
        }       
        locationOfEntityCoordinates.unmarshal(buff);
        detonationResult = (short) (buff.get() & 0xFF);
        numberOfVariableParameters = (short) (buff.get() & 0xFF);
        pad = (int) (buff.getShort() & 0xFFFF);
        for (int idx = 0; idx < numberOfVariableParameters; idx++) {
            VariableParameter anX = new VariableParameter();
            anX.unmarshal(buff);
            switch (anX.getRecordType()) {
                case 0:
                    anX = new ArticulatedParts();
                    ((ArticulatedParts) anX).unmarshal(buff);
                    break;
                case 1:
                    anX = new AttachedParts();
                    ((AttachedParts) anX).unmarshal(buff);
                    break;
                case 2:
                    anX = new SeparationVP();
                    ((SeparationVP) anX).unmarshal(buff);
                    break;
                case 3:
                    anX = new EntityTypeVP();
                    ((EntityTypeVP) anX).unmarshal(buff);
                    break;
                case 4:
                    anX = new EntityAssociation();
                    ((EntityAssociation) anX).unmarshal(buff);
                    break;
            }

            variableParameters.add(anX);
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

        if (!(obj instanceof DetonationPdu)) {
            return false;
        }

        final DetonationPdu rhs = (DetonationPdu) obj;

        if (!(explodingEntityID.equals(rhs.explodingEntityID))) {
            ivarsEqual = false;
        }
        if (!(eventID.equals(rhs.eventID))) {
            ivarsEqual = false;
        }
        if (!(velocity.equals(rhs.velocity))) {
            ivarsEqual = false;
        }
        if (!(locationInWorldCoordinates.equals(rhs.locationInWorldCoordinates))) {
            ivarsEqual = false;
        }
        int detonationTypeIndicator = getDetonationTypeIndicator();
        switch (detonationTypeIndicator) {
            case 0:
            default:
                if (!(((MunitionDescriptor) descriptor).equals((MunitionDescriptor) rhs.descriptor))) {
                    ivarsEqual = false;
                }
                break;
            case 1:
                if (!(((ExpendableDescriptor) descriptor).equals((ExpendableDescriptor) rhs.descriptor))) {
                    ivarsEqual = false;
                }
                break;
            case 2:
                if (!(((ExplosionDescriptor) descriptor).equals((ExplosionDescriptor) rhs.descriptor))) {
                    ivarsEqual = false;
                }
                break;
        }
        if (!(descriptor.equals(rhs.descriptor))) {
            ivarsEqual = false;
        }
        if (!(locationOfEntityCoordinates.equals(rhs.locationOfEntityCoordinates))) {
            ivarsEqual = false;
        }
        if (!(detonationResult == rhs.detonationResult)) {
            ivarsEqual = false;
        }
        if (!(numberOfVariableParameters == rhs.numberOfVariableParameters)) {
            ivarsEqual = false;
        }
        if (!(pad == rhs.pad)) {
            ivarsEqual = false;
        }

        for (int idx = 0; idx < variableParameters.size(); idx++) {
            if (variableParameters.get(idx).getRecordType() != rhs.variableParameters.get(idx).getRecordType()) {
                ivarsEqual = false;
            } else {
                switch (variableParameters.get(idx).getRecordType()) {
                    case 0:
                        if (!((ArticulatedParts) variableParameters.get(idx)).equalsImpl(((ArticulatedParts) rhs.variableParameters.get(idx)))) {
                            ivarsEqual = false;
                        }
                        break;
                    case 1:
                        if (!((AttachedParts) variableParameters.get(idx)).equalsImpl(((AttachedParts) rhs.variableParameters.get(idx)))) {
                            ivarsEqual = false;
                        }
                        break;
                    case 2:
                        if (!((SeparationVP) variableParameters.get(idx)).equalsImpl(((AttachedParts) rhs.variableParameters.get(idx)))) {
                            ivarsEqual = false;
                        }
                        break;
                    case 3:
                        if (!((EntityTypeVP) variableParameters.get(idx)).equalsImpl(((EntityTypeVP) rhs.variableParameters.get(idx)))) {
                            ivarsEqual = false;
                        }
                        break;
                    case 4:
                        if (!((EntityAssociation) variableParameters.get(idx)).equalsImpl(((EntityAssociation) rhs.variableParameters.get(idx)))) {
                            ivarsEqual = false;
                        }
                        break;
                }
            }
        }

        return ivarsEqual && super.equalsImpl(rhs);
    }
    
    private int getDetonationTypeIndicator(){
        return (pduStatus & 0x30) >> 4;
    }    
} // end of class
