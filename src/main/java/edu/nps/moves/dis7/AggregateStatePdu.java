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
public class AggregateStatePdu extends EntityManagementFamilyPdu implements Serializable {

    /**
     * ID of aggregate
     */
    protected AggregateIdentifier aggregateID = new AggregateIdentifier();

    /**
     * force ID
     */
    protected short forceID;

    /**
     * state of aggregate
     */
    protected short aggregateState;

    /**
     * Type of the aggregate
     */
    protected AggregateType aggregateType = new AggregateType();

    /**
     * formation of aggregated entities
     */
    protected long formation;

    /**
     * marking for aggregate; first char is charset type, rest is char data
     */
    protected AggregateMarking aggregateMarking = new AggregateMarking();

    /**
     * dimensions of bounding box for the aggregated entities, origin at the
     * center of mass
     */
    protected Vector3Float dimensions = new Vector3Float();

    /**
     * orientation of the bounding box
     */
    protected Orientation orientation = new Orientation();

    /**
     * center of mass of the aggregation
     */
    protected Vector3Double centerOfMass = new Vector3Double();

    /**
     * velocity of aggregation
     */
    protected Vector3Float velocity = new Vector3Float();

    /**
     * number of aggregates
     */
    protected int numberOfAggregateIds;

    /**
     * number of entities
     */
    protected int numberOfEntitiyIds;

    /**
     * number of silent aggregate types
     */
    protected int numberOfSilentAggregateSystems;

    /**
     * number of silent entity types
     */
    protected int numberOfSilentEntitySystems;
    /**
     * aggregates list
     */
    protected List<AggregateIdentifier> aggregateIDList = new ArrayList<AggregateIdentifier>();
    /**
     * entity ID list
     */
    protected List<EntityID> entityIDList = new ArrayList<EntityID>();
    /**
     * ^^^padding to put the start of the next list on a 32 bit boundary. This
     * needs to be fixed
     */
    protected short pad2;

    /**
     * silent entity types
     */
    protected List<SilentAggregateSystem> silentAggregateSystemList = new ArrayList<SilentAggregateSystem>();
    /**
     * silent entity types
     */
    protected List<SilentEntitySystem> silentEntitySystemList = new ArrayList<SilentEntitySystem>();

    /**
     * number of variable datum records
     */
    protected long numberOfVariableDatumRecords;

    /**
     * variableDatums
     */
    protected List< VariableDatum> variableDatumList = new ArrayList< VariableDatum>();

    public AggregateStatePdu() {
        setPduType((short) 33);
    }

    public int getMarshalledSize() {
        int marshalSize = 0;
        
        marshalSize = marshalSize + super.getMarshalledSize();
        marshalSize = marshalSize + aggregateID.getMarshalledSize();//aggregateID
        marshalSize = marshalSize + 1;//forceID
        marshalSize = marshalSize + 1;//aggregateState
        marshalSize = marshalSize + aggregateType.getMarshalledSize();//aggregateType
        marshalSize = marshalSize + 4;//formation
        marshalSize = marshalSize + aggregateMarking.getMarshalledSize();//aggregateMarking
        marshalSize = marshalSize + dimensions.getMarshalledSize();//dimensions
        marshalSize = marshalSize + orientation.getMarshalledSize();//orientation
        marshalSize = marshalSize + centerOfMass.getMarshalledSize();//centerOfMass
        marshalSize = marshalSize + velocity.getMarshalledSize();//velocity
        marshalSize = marshalSize + 2;  // numberOfDisAggregates
        marshalSize = marshalSize + 2;  // numberOfDisEntities
        marshalSize = marshalSize + 2;  // numberOfSilentAggregateTypes
        marshalSize = marshalSize + 2;  // numberOfSilentEntityTypes
        for (int idx = 0; idx < aggregateIDList.size(); idx++) {
            AggregateIdentifier listElement = aggregateIDList.get(idx);
            marshalSize = marshalSize + listElement.getMarshalledSize();
        }
        for (int idx = 0; idx < entityIDList.size(); idx++) {
            EntityID listElement = entityIDList.get(idx);
            marshalSize = marshalSize + listElement.getMarshalledSize();
        }
        marshalSize = marshalSize + (getPad2Bits() / 8);  // pad2
        for (int idx = 0; idx < silentAggregateSystemList.size(); idx++) {
            SilentAggregateSystem listElement = silentAggregateSystemList.get(idx);
            marshalSize = marshalSize + listElement.getMarshalledSize();
        }
        for (int idx = 0; idx < silentEntitySystemList.size(); idx++) {
            SilentEntitySystem listElement = silentEntitySystemList.get(idx);
            marshalSize = marshalSize + listElement.getMarshalledSize();
        }
        marshalSize = marshalSize + 4;  // numberOfVariableDatumRecords
        for (int idx = 0; idx < variableDatumList.size(); idx++) {
            VariableDatum listElement = variableDatumList.get(idx);
            marshalSize = marshalSize + listElement.getMarshalledSize();
        }

        return marshalSize;
    }

    public short getPad2Bits() {
        int val = 16 * ((aggregateIDList.size() + entityIDList.size()) % 2);
        return (short) val;
    }

    public AggregateIdentifier getAggregateID() {
        return aggregateID;
    }

    public void setAggregateID(AggregateIdentifier aggregateID) {
        this.aggregateID = aggregateID;
    }

    public short getForceID() {
        return forceID;
    }

    public void setForceID(short forceID) {
        this.forceID = forceID;
    }

    public short getAggregateState() {
        return aggregateState;
    }

    public void setAggregateState(short aggregateState) {
        this.aggregateState = aggregateState;
    }

    public AggregateType getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(AggregateType aggregateType) {
        this.aggregateType = aggregateType;
    }

    public long getFormation() {
        return formation;
    }

    public void setFormation(long formation) {
        this.formation = formation;
    }

    public AggregateMarking getAggregateMarking() {
        return aggregateMarking;
    }

    public void setAggregateMarking(AggregateMarking aggregateMarking) {
        this.aggregateMarking = aggregateMarking;
    }

    public Vector3Float getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vector3Float dimensions) {
        this.dimensions = dimensions;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Vector3Double getCenterOfMass() {
        return centerOfMass;
    }

    public void setCenterOfMass(Vector3Double centerOfMass) {
        this.centerOfMass = centerOfMass;
    }

    public Vector3Float getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3Float velocity) {
        this.velocity = velocity;
    }

    public List<AggregateIdentifier> getAggregateIDList() {
        return aggregateIDList;
    }

    public void setAggregateIDList(List<AggregateIdentifier> aggregateIDList) {
        this.aggregateIDList = aggregateIDList;
    }

    public List<EntityID> getEntityIDList() {
        return entityIDList;
    }

    public void setEntityIDList(List<EntityID> entityIDList) {
        this.entityIDList = entityIDList;
    }

    public short getPad2() {
        return pad2;
    }

    public void setPad2(short pad2) {
        this.pad2 = pad2;
    }

    public List<SilentAggregateSystem> getSilentAggregateSystemList() {
        return silentAggregateSystemList;
    }

    public void setSilentAggregateSystemList(List<SilentAggregateSystem> silentAggregateSystemList) {
        this.silentAggregateSystemList = silentAggregateSystemList;
    }

    public List<SilentEntitySystem> getSilentEntitySystemList() {
        return silentEntitySystemList;
    }

    public void setSilentEntitySystemList(List<SilentEntitySystem> silentEntitySystemList) {
        this.silentEntitySystemList = silentEntitySystemList;
    }

    public List<VariableDatum> getVariableDatumList() {
        return variableDatumList;
    }

    public void setVariableDatumList(List<VariableDatum> variableDatumList) {
        this.variableDatumList = variableDatumList;
    }

    public int getNumberOfAggregateIds() {
        return numberOfAggregateIds;
    }

    public int getNumberOfEntitiyIds() {
        return numberOfEntitiyIds;
    }

    public int getNumberOfSilentAggregateSystems() {
        return numberOfSilentAggregateSystems;
    }

    public int getNumberOfSilentEntitySystems() {
        return numberOfSilentEntitySystems;
    }

    public long getNumberOfVariableDatumRecords() {
        return numberOfVariableDatumRecords;
    }

    public void marshal(java.nio.ByteBuffer buff) {
        super.marshal(buff);
        aggregateID.marshal(buff);
        buff.put((byte) forceID);
        buff.put((byte) aggregateState);
        aggregateType.marshal(buff);
        buff.putInt((int) formation);
        aggregateMarking.marshal(buff);
        dimensions.marshal(buff);
        orientation.marshal(buff);
        centerOfMass.marshal(buff);
        velocity.marshal(buff); 
        buff.putShort((short) aggregateIDList.size());
        buff.putShort((short) entityIDList.size());
        buff.putShort((short) silentAggregateSystemList.size());
        buff.putShort((short) silentEntitySystemList.size());
        for (int idx = 0; idx < aggregateIDList.size(); idx++) {
            AggregateIdentifier aAggregateID = (AggregateIdentifier) aggregateIDList.get(idx);
            aAggregateID.marshal(buff);
        } // end of list marshalling

        for (int idx = 0; idx < entityIDList.size(); idx++) {
            EntityID aEntityID = (EntityID) entityIDList.get(idx);
            aEntityID.marshal(buff);
        } // end of list marshalling

        if ((getPad2Bits() / 8) == 2) {
            buff.putShort(pad2);
        }

        for (int idx = 0; idx < silentAggregateSystemList.size(); idx++) {
            SilentAggregateSystem aSilentAggregateSystem = (SilentAggregateSystem) silentAggregateSystemList.get(idx);
            aSilentAggregateSystem.marshal(buff);
        } // end of list marshalling

        for (int idx = 0; idx < silentEntitySystemList.size(); idx++) {
            SilentEntitySystem aSilentEntitySystem = (SilentEntitySystem) silentEntitySystemList.get(idx);
            aSilentEntitySystem.marshal(buff);
        } // end of list marshalling 

        buff.putInt((int) variableDatumList.size());

        for (int idx = 0; idx < variableDatumList.size(); idx++) {
            VariableDatum aVariableDatum = (VariableDatum) variableDatumList.get(idx);
            aVariableDatum.marshal(buff);
        } // end of list marshalling

    } // end of marshal method

    public void unmarshal(java.nio.ByteBuffer buff) {
        super.unmarshal(buff);
        aggregateID.unmarshal(buff);
        forceID = (short) (buff.get() & 0xFF);
        aggregateState = (short) (buff.get() & 0xFF);
        aggregateType.unmarshal(buff);
        formation = buff.getInt();
        aggregateMarking.unmarshal(buff);
        dimensions.unmarshal(buff);
        orientation.unmarshal(buff);
        centerOfMass.unmarshal(buff);
        velocity.unmarshal(buff);
        numberOfAggregateIds = (int) (buff.getShort() & 0xFFFF);
        numberOfEntitiyIds = (int) (buff.getShort() & 0xFFFF);
        numberOfSilentAggregateSystems = (int) (buff.getShort() & 0xFFFF);
        numberOfSilentEntitySystems = (int) (buff.getShort() & 0xFFFF);
        for (int idx = 0; idx < numberOfAggregateIds; idx++) {
            AggregateIdentifier anX = new AggregateIdentifier();
            anX.unmarshal(buff);
            aggregateIDList.add(anX);
        }

        for (int idx = 0; idx < numberOfEntitiyIds; idx++) {
            EntityID anX = new EntityID();
            anX.unmarshal(buff);
            entityIDList.add(anX);
        }
        //Determine if pad2 is present
        int padBits = 16 * ((numberOfAggregateIds + numberOfEntitiyIds) % 2);
        if ((padBits / 8) == 2) {
            pad2 = buff.getShort();
        }
        for (int idx = 0; idx < numberOfSilentAggregateSystems; idx++) {
            SilentAggregateSystem anX = new SilentAggregateSystem();
            anX.unmarshal(buff);
            silentAggregateSystemList.add(anX);
        }

        for (int idx = 0; idx < numberOfSilentEntitySystems; idx++) {
            SilentEntitySystem anX = new SilentEntitySystem();
            anX.unmarshal(buff);
            silentEntitySystemList.add(anX);
        }

        numberOfVariableDatumRecords = buff.getInt();
        for (int idx = 0; idx < numberOfVariableDatumRecords; idx++) {
            VariableDatum anX = new VariableDatum();
            anX.unmarshal(buff);
            variableDatumList.add(anX);
        }
    } // end of unmarshal method 

    public void marshal(DataOutputStream dos) {
        super.marshal(dos);
        try {
            aggregateID.marshal(dos);
            dos.writeByte(forceID);
            dos.writeByte(aggregateState);
            aggregateType.marshal(dos);
            dos.writeInt((int) formation);
            aggregateMarking.marshal(dos);
            dimensions.marshal(dos);
            orientation.marshal(dos);
            centerOfMass.marshal(dos);
            velocity.marshal(dos);
            dos.writeShort((short) aggregateIDList.size());
            dos.writeShort((short) entityIDList.size());
            dos.writeShort((short) silentAggregateSystemList.size());
            dos.writeShort((short) silentEntitySystemList.size());
            for (int idx = 0; idx < aggregateIDList.size(); idx++) {
                AggregateIdentifier aAggregateID = (AggregateIdentifier) aggregateIDList.get(idx);
                aAggregateID.marshal(dos);
            } // end of list marshalling

            for (int idx = 0; idx < entityIDList.size(); idx++) {
                EntityID aEntityID = (EntityID) entityIDList.get(idx);
                aEntityID.marshal(dos);
            } // end of list marshalling

            if ((getPad2Bits() / 8) == 2) {
                dos.writeShort(pad2);
            }

            for (int idx = 0; idx < silentAggregateSystemList.size(); idx++) {
                SilentAggregateSystem aSilentAggregateSystem = (SilentAggregateSystem) silentAggregateSystemList.get(idx);
                aSilentAggregateSystem.marshal(dos);
            } // end of list marshalling

            for (int idx = 0; idx < silentEntitySystemList.size(); idx++) {
                SilentEntitySystem aSilentEntitySystem = (SilentEntitySystem) silentEntitySystemList.get(idx);
                aSilentEntitySystem.marshal(dos);
            } // end of list marshalling 

            dos.writeInt((int) variableDatumList.size());

            for (int idx = 0; idx < variableDatumList.size(); idx++) {
                VariableDatum aVariableDatum = (VariableDatum) variableDatumList.get(idx);
                aVariableDatum.marshal(dos);
            } // end of list marshalling            
        } // end try  // end try 
        catch (Exception e) {
            System.out.println(e);
        }
    } // end of marshal method 

    public void unmarshal(DataInputStream dis) {
        super.unmarshal(dis);

        try {
            aggregateID.unmarshal(dis);
            forceID = dis.readByte();
            aggregateState = dis.readByte();
            aggregateType.unmarshal(dis);
            formation = dis.readInt();
            aggregateMarking.unmarshal(dis);
            dimensions.unmarshal(dis);
            orientation.unmarshal(dis);
            centerOfMass.unmarshal(dis);
            velocity.unmarshal(dis);
            numberOfAggregateIds = (int) dis.readShort();
            numberOfEntitiyIds = (int) dis.readShort();
            numberOfSilentAggregateSystems = (int) dis.readShort();
            numberOfSilentEntitySystems = (int) dis.readShort();
            for (int idx = 0; idx < numberOfAggregateIds; idx++) {
                AggregateIdentifier anX = new AggregateIdentifier();
                anX.unmarshal(dis);
                aggregateIDList.add(anX);
            }

            for (int idx = 0; idx < numberOfEntitiyIds; idx++) {
                EntityID anX = new EntityID();
                anX.unmarshal(dis);
                entityIDList.add(anX);
            }
            //Determine if pad2 is present
            int padBits = 16 * ((numberOfAggregateIds + numberOfEntitiyIds) % 2);
            if ((padBits / 8) == 2) {
                pad2 = dis.readShort();
            }
            for (int idx = 0; idx < numberOfSilentAggregateSystems; idx++) {
                SilentAggregateSystem anX = new SilentAggregateSystem();
                anX.unmarshal(dis);
                silentAggregateSystemList.add(anX);
            }

            for (int idx = 0; idx < numberOfSilentEntitySystems; idx++) {
                SilentEntitySystem anX = new SilentEntitySystem();
                anX.unmarshal(dis);
                silentEntitySystemList.add(anX);
            }

            numberOfVariableDatumRecords = dis.readInt();
            for (int idx = 0; idx < numberOfVariableDatumRecords; idx++) {
                VariableDatum anX = new VariableDatum();
                anX.unmarshal(dis);
                variableDatumList.add(anX);
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

    @Override
    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof AggregateStatePdu)) {
            return false;
        }

        final AggregateStatePdu rhs = (AggregateStatePdu) obj;

        if (!(aggregateID.equals(rhs.aggregateID))) {
            ivarsEqual = false;
        }
        if (!(forceID == rhs.forceID)) {
            ivarsEqual = false;
        }
        if (!(aggregateState == rhs.aggregateState)) {
            ivarsEqual = false;
        }
        if (!(aggregateType.equals(rhs.aggregateType))) {
            ivarsEqual = false;
        }
        if (!(formation == rhs.formation)) {
            ivarsEqual = false;
        }
        if (!(aggregateMarking.equals(rhs.aggregateMarking))) {
            ivarsEqual = false;
        }
        if (!(dimensions.equals(rhs.dimensions))) {
            ivarsEqual = false;
        }
        if (!(orientation.equals(rhs.orientation))) {
            ivarsEqual = false;
        }
        if (!(centerOfMass.equals(rhs.centerOfMass))) {
            ivarsEqual = false;
        }
        if (!(velocity.equals(rhs.velocity))) {
            ivarsEqual = false;
        }
        if (!(numberOfAggregateIds == rhs.numberOfAggregateIds)) {
            ivarsEqual = false;
        }
        if (!(numberOfEntitiyIds == rhs.numberOfEntitiyIds)) {
            ivarsEqual = false;
        }
        if (!(numberOfSilentAggregateSystems == rhs.numberOfSilentAggregateSystems)) {
            ivarsEqual = false;
        }
        if (!(numberOfSilentEntitySystems == rhs.numberOfSilentEntitySystems)) {
            ivarsEqual = false;
        }
        for (int idx = 0; idx < aggregateIDList.size(); idx++) {
            if (!(aggregateIDList.get(idx).equals(rhs.aggregateIDList.get(idx)))) {
                ivarsEqual = false;
            }
        }
        for (int idx = 0; idx < entityIDList.size(); idx++) {
            if (!(entityIDList.get(idx).equals(rhs.entityIDList.get(idx)))) {
                ivarsEqual = false;
            }
        }
        if (!(pad2 == rhs.pad2)) {
            ivarsEqual = false;
        }
        for (int idx = 0; idx < silentAggregateSystemList.size(); idx++) {
            if (!(silentAggregateSystemList.get(idx).equals(rhs.silentAggregateSystemList.get(idx)))) {
                ivarsEqual = false;
            }
        }
        for (int idx = 0; idx < silentEntitySystemList.size(); idx++) {
            if (!(silentEntitySystemList.get(idx).equals(rhs.silentEntitySystemList.get(idx)))) {
                ivarsEqual = false;
            }
        }
        if (!(numberOfVariableDatumRecords == rhs.numberOfVariableDatumRecords)) {
            ivarsEqual = false;
        }

        for (int idx = 0; idx < variableDatumList.size(); idx++) {
            if (!(variableDatumList.get(idx).equals(rhs.variableDatumList.get(idx)))) {
                ivarsEqual = false;
            }
        }
        
        return ivarsEqual && super.equalsImpl(rhs);
    }
}
