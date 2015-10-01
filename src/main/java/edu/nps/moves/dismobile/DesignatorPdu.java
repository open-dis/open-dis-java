package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.3.7.2. Handles designating operations. COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DesignatorPdu extends DistributedEmissionsFamilyPdu implements Serializable
{
   /** ID of the entity designating */
   protected EntityID  designatingEntityID = new EntityID(); 

   /** This field shall specify a unique emitter database number assigned to  differentiate between otherwise similar or identical emitter beams within an emitter system. */
   protected int  codeName;

   /** ID of the entity being designated */
   protected EntityID  designatedEntityID = new EntityID(); 

   /** This field shall identify the designator code being used by the designating entity  */
   protected int  designatorCode;

   /** This field shall identify the designator output power in watts */
   protected float  designatorPower;

   /** This field shall identify the designator wavelength in units of microns */
   protected float  designatorWavelength;

   /** designtor spot wrt the designated entity */
   protected Vector3Float  designatorSpotWrtDesignated = new Vector3Float(); 

   /** designtor spot wrt the designated entity */
   protected Vector3Double  designatorSpotLocation = new Vector3Double(); 

   /** Dead reckoning algorithm */
   protected byte  deadReckoningAlgorithm;

   /** padding */
   protected int  padding1;

   /** padding */
   protected byte  padding2;

   /** linear accelleration of entity */
   protected Vector3Float  entityLinearAcceleration = new Vector3Float(); 


/** Constructor */
 public DesignatorPdu()
 {
    setPduType( (short)24 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + designatingEntityID.getMarshalledSize();  // designatingEntityID
   marshalSize = marshalSize + 2;  // codeName
   marshalSize = marshalSize + designatedEntityID.getMarshalledSize();  // designatedEntityID
   marshalSize = marshalSize + 2;  // designatorCode
   marshalSize = marshalSize + 4;  // designatorPower
   marshalSize = marshalSize + 4;  // designatorWavelength
   marshalSize = marshalSize + designatorSpotWrtDesignated.getMarshalledSize();  // designatorSpotWrtDesignated
   marshalSize = marshalSize + designatorSpotLocation.getMarshalledSize();  // designatorSpotLocation
   marshalSize = marshalSize + 1;  // deadReckoningAlgorithm
   marshalSize = marshalSize + 2;  // padding1
   marshalSize = marshalSize + 1;  // padding2
   marshalSize = marshalSize + entityLinearAcceleration.getMarshalledSize();  // entityLinearAcceleration

   return marshalSize;
}


public void setDesignatingEntityID(EntityID pDesignatingEntityID)
{ designatingEntityID = pDesignatingEntityID;
}

public EntityID getDesignatingEntityID()
{ return designatingEntityID; 
}

public void setCodeName(int pCodeName)
{ codeName = pCodeName;
}

public int getCodeName()
{ return codeName; 
}

public void setDesignatedEntityID(EntityID pDesignatedEntityID)
{ designatedEntityID = pDesignatedEntityID;
}

public EntityID getDesignatedEntityID()
{ return designatedEntityID; 
}

public void setDesignatorCode(int pDesignatorCode)
{ designatorCode = pDesignatorCode;
}

public int getDesignatorCode()
{ return designatorCode; 
}

public void setDesignatorPower(float pDesignatorPower)
{ designatorPower = pDesignatorPower;
}

public float getDesignatorPower()
{ return designatorPower; 
}

public void setDesignatorWavelength(float pDesignatorWavelength)
{ designatorWavelength = pDesignatorWavelength;
}

public float getDesignatorWavelength()
{ return designatorWavelength; 
}

public void setDesignatorSpotWrtDesignated(Vector3Float pDesignatorSpotWrtDesignated)
{ designatorSpotWrtDesignated = pDesignatorSpotWrtDesignated;
}

public Vector3Float getDesignatorSpotWrtDesignated()
{ return designatorSpotWrtDesignated; 
}

public void setDesignatorSpotLocation(Vector3Double pDesignatorSpotLocation)
{ designatorSpotLocation = pDesignatorSpotLocation;
}

public Vector3Double getDesignatorSpotLocation()
{ return designatorSpotLocation; 
}

public void setDeadReckoningAlgorithm(byte pDeadReckoningAlgorithm)
{ deadReckoningAlgorithm = pDeadReckoningAlgorithm;
}

public byte getDeadReckoningAlgorithm()
{ return deadReckoningAlgorithm; 
}

public void setPadding1(int pPadding1)
{ padding1 = pPadding1;
}

public int getPadding1()
{ return padding1; 
}

public void setPadding2(byte pPadding2)
{ padding2 = pPadding2;
}

public byte getPadding2()
{ return padding2; 
}

public void setEntityLinearAcceleration(Vector3Float pEntityLinearAcceleration)
{ entityLinearAcceleration = pEntityLinearAcceleration;
}

public Vector3Float getEntityLinearAcceleration()
{ return entityLinearAcceleration; 
}


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       designatingEntityID.marshal(dos);
       dos.writeShort( (short)codeName);
       designatedEntityID.marshal(dos);
       dos.writeShort( (short)designatorCode);
       dos.writeFloat( (float)designatorPower);
       dos.writeFloat( (float)designatorWavelength);
       designatorSpotWrtDesignated.marshal(dos);
       designatorSpotLocation.marshal(dos);
       dos.writeByte( (byte)deadReckoningAlgorithm);
       dos.writeShort( (short)padding1);
       dos.writeByte( (byte)padding2);
       entityLinearAcceleration.marshal(dos);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
     super.unmarshal(dis);

    try 
    {
       designatingEntityID.unmarshal(dis);
       codeName = (int)dis.readUnsignedShort();
       designatedEntityID.unmarshal(dis);
       designatorCode = (int)dis.readUnsignedShort();
       designatorPower = dis.readFloat();
       designatorWavelength = dis.readFloat();
       designatorSpotWrtDesignated.unmarshal(dis);
       designatorSpotLocation.unmarshal(dis);
       deadReckoningAlgorithm = dis.readByte();
       padding1 = (int)dis.readUnsignedShort();
       padding2 = dis.readByte();
       entityLinearAcceleration.unmarshal(dis);
    } // end try 
   catch(Exception e)
    { 
      System.out.println(e); 
    }
 } // end of unmarshal method 


/**
 * Packs a Pdu into the ByteBuffer.
 * @throws java.nio.BufferOverflowException if buff is too small
 * @throws java.nio.ReadOnlyBufferException if buff is read only
 * @see java.nio.ByteBuffer
 * @param buff The ByteBuffer at the position to begin writing
 * @since ??
 */
public void marshal(java.nio.ByteBuffer buff)
{
       super.marshal(buff);
       designatingEntityID.marshal(buff);
       buff.putShort( (short)codeName);
       designatedEntityID.marshal(buff);
       buff.putShort( (short)designatorCode);
       buff.putFloat( (float)designatorPower);
       buff.putFloat( (float)designatorWavelength);
       designatorSpotWrtDesignated.marshal(buff);
       designatorSpotLocation.marshal(buff);
       buff.put( (byte)deadReckoningAlgorithm);
       buff.putShort( (short)padding1);
       buff.put( (byte)padding2);
       entityLinearAcceleration.marshal(buff);
    } // end of marshal method

/**
 * Unpacks a Pdu from the underlying data.
 * @throws java.nio.BufferUnderflowException if buff is too small
 * @see java.nio.ByteBuffer
 * @param buff The ByteBuffer at the position to begin reading
 * @since ??
 */
public void unmarshal(java.nio.ByteBuffer buff)
{
       super.unmarshal(buff);

       designatingEntityID.unmarshal(buff);
       codeName = (int)(buff.getShort() & 0xFFFF);
       designatedEntityID.unmarshal(buff);
       designatorCode = (int)(buff.getShort() & 0xFFFF);
       designatorPower = buff.getFloat();
       designatorWavelength = buff.getFloat();
       designatorSpotWrtDesignated.unmarshal(buff);
       designatorSpotLocation.unmarshal(buff);
       deadReckoningAlgorithm = buff.get();
       padding1 = (int)(buff.getShort() & 0xFFFF);
       padding2 = buff.get();
       entityLinearAcceleration.unmarshal(buff);
 } // end of unmarshal method 


 /*
  * The equals method doesn't always work--mostly it works only on classes that consist only of primitives. Be careful.
  */
@Override
 public boolean equals(Object obj)
 {

    if(this == obj){
      return true;
    }

    if(obj == null){
       return false;
    }

    if(getClass() != obj.getClass())
        return false;

    return equalsImpl(obj);
 }

@Override
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof DesignatorPdu))
        return false;

     final DesignatorPdu rhs = (DesignatorPdu)obj;

     if( ! (designatingEntityID.equals( rhs.designatingEntityID) )) ivarsEqual = false;
     if( ! (codeName == rhs.codeName)) ivarsEqual = false;
     if( ! (designatedEntityID.equals( rhs.designatedEntityID) )) ivarsEqual = false;
     if( ! (designatorCode == rhs.designatorCode)) ivarsEqual = false;
     if( ! (designatorPower == rhs.designatorPower)) ivarsEqual = false;
     if( ! (designatorWavelength == rhs.designatorWavelength)) ivarsEqual = false;
     if( ! (designatorSpotWrtDesignated.equals( rhs.designatorSpotWrtDesignated) )) ivarsEqual = false;
     if( ! (designatorSpotLocation.equals( rhs.designatorSpotLocation) )) ivarsEqual = false;
     if( ! (deadReckoningAlgorithm == rhs.deadReckoningAlgorithm)) ivarsEqual = false;
     if( ! (padding1 == rhs.padding1)) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;
     if( ! (entityLinearAcceleration.equals( rhs.entityLinearAcceleration) )) ivarsEqual = false;

    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
