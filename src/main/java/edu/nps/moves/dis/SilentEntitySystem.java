/*
 * Copyright (c) 2022, The Moves Institute
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.nps.moves.dis;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fo-ifad
 */
public class SilentEntitySystem {

// nr of silent entities of the following type 
    protected short entityNr;

// nr of apperance records
    protected short recordNr;

    //Entity type
    protected EntityType entityType = new EntityType();

// Entity appearance
    protected List<Integer> entityAppearanceList = new ArrayList<Integer>();

// constructor
    public SilentEntitySystem() {

    }

    public int getMarshalledSize() {
        int marshalSize = 0;
        marshalSize = marshalSize + 2;  // nr of silent entities
        marshalSize = marshalSize + 2;  // nr of apperance records
        marshalSize = marshalSize + entityType.getMarshalledSize();//Aggregate type
        marshalSize = marshalSize + 4 * entityAppearanceList.size();  // entityAppearance

        return marshalSize;
    }

    public void setNrOfSilentEntities(short aNr) {
        entityNr = aNr;
    }

    public short getNrOfSilentEntities() {
        return entityNr;
    }

    public void setNumberOfAppearanceRecords(short aNr) {
        recordNr = aNr;
    }

    public short getNumberOfAppearanceRecords() {
        return recordNr;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType aggregateType) {
        this.entityType = aggregateType;
    }

    public void setEntityApperanceList(List<Integer> aList) {
        entityAppearanceList = aList;
    }

    public List<Integer> getEntityApperanceList() {
        return entityAppearanceList;
    }

    public void marshal(java.nio.ByteBuffer buff) {
        buff.putShort((short) entityNr);
        buff.putShort((short) recordNr);
        entityType.marshal(buff);
        for (int i = 0; i < entityAppearanceList.size(); i++) {
            buff.putInt(entityAppearanceList.get(i));
        }
    } // end of marshal method

    public void unmarshal(java.nio.ByteBuffer buff) {
        entityNr = (short) (buff.getShort() & 0xFF);
        recordNr = (short) (buff.getShort() & 0xFF);
        entityType.unmarshal(buff);
        for (int i = 0; i < recordNr; i++) {
            entityAppearanceList.add((int) buff.getInt());
        }
    } // end of unmarshal method 

    public boolean equalsImpl(Object obj) {
        boolean ivarsEqual = true;

        if (!(obj instanceof SilentEntitySystem)) {
            return false;
        }

        final SilentEntitySystem rhs = (SilentEntitySystem) obj;

        if (!(entityNr == rhs.entityNr)) {
            ivarsEqual = false;
        }
        if (!(recordNr == rhs.recordNr)) {
            ivarsEqual = false;
        }

        if (!(entityType == rhs.entityType)) {
            ivarsEqual = false;
        }
        for (int i = 0; i < entityAppearanceList.size(); i++) {
            if (!(entityAppearanceList.get(i) == rhs.getEntityApperanceList().get(i))) {
                ivarsEqual = false;
            }
        }
        return ivarsEqual;
    }
}
