package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Not specified in the standard. This is used by the ESPDU
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DeadReckoningParameters extends Object implements Serializable
{
   /** Algorithm to use in computing dead reckoning. See EBV doc. */
   protected short  deadReckoningAlgorithm;

   /** Dead reckoning parameters. Contents depends on algorithm. */
   protected short[]  parameters = new short[15]; 

   /** Linear acceleration of the entity */
   protected Vector3Float  entityLinearAcceleration = new Vector3Float(); 

   /** Angular velocity of the entity */
   protected Vector3Float  entityAngularVelocity = new Vector3Float(); 


/** Constructor */
 public DeadReckoningParameters()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 1;  // deadReckoningAlgorithm
   marshalSize = marshalSize + 15 * 1;  // parameters
   marshalSize = marshalSize + entityLinearAcceleration.getMarshalledSize();  // entityLinearAcceleration
   marshalSize = marshalSize + entityAngularVelocity.getMarshalledSize();  // entityAngularVelocity

   return marshalSize;
}


public void setDeadReckoningAlgorithm(short pDeadReckoningAlgorithm)
{ deadReckoningAlgorithm = pDeadReckoningAlgorithm;
}

public short getDeadReckoningAlgorithm()
{ return deadReckoningAlgorithm; 
}

public void setParameters(short[] pParameters)
{ parameters = pParameters;
}

public short[] getParameters()
{ return parameters; }

public void setEntityLinearAcceleration(Vector3Float pEntityLinearAcceleration)
{ entityLinearAcceleration = pEntityLinearAcceleration;
}

public Vector3Float getEntityLinearAcceleration()
{ return entityLinearAcceleration; 
}

public void setEntityAngularVelocity(Vector3Float pEntityAngularVelocity)
{ entityAngularVelocity = pEntityAngularVelocity;
}

public Vector3Float getEntityAngularVelocity()
{ return entityAngularVelocity; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeByte( (byte)deadReckoningAlgorithm);

       for(int idx = 0; idx < parameters.length; idx++)
       {
           dos.writeByte(parameters[idx]);
       } // end of array marshaling

       entityLinearAcceleration.marshal(dos);
       entityAngularVelocity.marshal(dos);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       deadReckoningAlgorithm = (short)dis.readUnsignedByte();
       for(int idx = 0; idx < parameters.length; idx++)
       {
                parameters[idx] = dis.readByte();
       } // end of array unmarshaling
       entityLinearAcceleration.unmarshal(dis);
       entityAngularVelocity.unmarshal(dis);
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
       buff.put( (byte)deadReckoningAlgorithm);

       for(int idx = 0; idx < parameters.length; idx++)
       {
           buff.put((byte)parameters[idx]);
       } // end of array marshaling

       entityLinearAcceleration.marshal(buff);
       entityAngularVelocity.marshal(buff);
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
       deadReckoningAlgorithm = (short)(buff.get() & 0xFF);
       for(int idx = 0; idx < parameters.length; idx++)
       {
                parameters[idx] = buff.get();
       } // end of array unmarshaling
       entityLinearAcceleration.unmarshal(buff);
       entityAngularVelocity.unmarshal(buff);
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

 /**
  * Compare all fields that contribute to the state, ignoring
 transient and static fields, for <code>this</code> and the supplied object
  * @param obj the object to compare to
  * @return true if the objects are equal, false otherwise.
  */
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof DeadReckoningParameters))
        return false;

     final DeadReckoningParameters rhs = (DeadReckoningParameters)obj;

     if( ! (deadReckoningAlgorithm == rhs.deadReckoningAlgorithm)) ivarsEqual = false;

     for(int idx = 0; idx < 15; idx++)
     {
          if(!(parameters[idx] == rhs.parameters[idx])) ivarsEqual = false;
     }

     if( ! (entityLinearAcceleration.equals( rhs.entityLinearAcceleration) )) ivarsEqual = false;
     if( ! (entityAngularVelocity.equals( rhs.entityAngularVelocity) )) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
