package edu.nps.moves.dis;

import java.io.*;

/**
 * Each entity in a given DIS simulation application shall be given an entity
 * identifier number unique to all other entities in that application. This
 * identifier number is valid for the duration of the exercise; however, entity
 * identifier numbers may be reused when all possible numbers have been
 * exhausted. No entity shall have an entity identifier number of NO_ENTITY,
 * ALL_ENTITIES, or RQST_ASSIGN_ID. The entity iden- tifier number need not be
 * registered or retained for future exercises. The entity identifier number
 * shall be specified by a 16-bit unsigned integer. An entity identifier number
 * equal to zero with valid site and application identification shall address a
 * simulation application. An entity identifier number equal to ALL_ENTITIES
 * shall mean all entities within the specified site and application. An entity
 * identifier number equal to RQST_ASSIGN_ID allows the receiver of the create
 * entity to define the entity identifier number of the new entity.
 *
 * Copyright (c) 2008-2016, MOVES Institute, Naval Postgraduate School. All
 * rights reserved. This work is licensed under the BSD open source license,
 * available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class EntityID extends Object implements Serializable {

    /**
     * The site ID
     */
    protected int site;

    /**
     * The application ID
     */
    protected int application;

    /**
     * the entity ID
     */
    protected int entity;

    /**
     * Constructor
     */
    public EntityID() {
    }

    public int getMarshalledSize() {
        int marshalSize = 0;

        marshalSize = marshalSize + 2;  // site
        marshalSize = marshalSize + 2;  // application
        marshalSize = marshalSize + 2;  // entity

        return marshalSize;
    }

    public void setSite(int pSite) {
        site = pSite;
    }

    public int getSite() {
        return site;
    }

    public void setApplication(int pApplication) {
        application = pApplication;
    }

    public int getApplication() {
        return application;
    }

    public void setEntity(int pEntity) {
        entity = pEntity;
    }

    public int getEntity() {
        return entity;
    }

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
        buff.putShort((short) site);
        buff.putShort((short) application);
        buff.putShort((short) entity);
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
        site = (int) (buff.getShort() & 0xFFFF);
        application = (int) (buff.getShort() & 0xFFFF);
        entity = (int) (buff.getShort() & 0xFFFF);
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

        if (!(obj instanceof EntityID)) {
            return false;
        }

        final EntityID rhs = (EntityID) obj;

        if (!(site == rhs.site)) {
            ivarsEqual = false;
        }
        if (!(application == rhs.application)) {
            ivarsEqual = false;
        }
        if (!(entity == rhs.entity)) {
            ivarsEqual = false;
        }

        return ivarsEqual;
    }

    /**
     * Override of base class hashCode. This is convienent to use when looking
     * up entities in a hash table. It uses the application and entity IDs to
     * fill out the 32 bits. The idea is to get a "pretty close" to unique
     * hashcode for each entity ID in 32 bits, and the best way to do that is
     * leave out the site ID while keeping the rest, which tends to be more
     * unique.
     */
    @Override
    public int hashCode() {
        int hashcode = application;
        hashcode = hashcode << 16;
        hashcode = hashcode + entity;
        return hashcode;
    }

} // end of class
