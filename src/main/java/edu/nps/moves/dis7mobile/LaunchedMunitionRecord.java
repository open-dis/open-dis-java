package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Identity of a communications node. Section 6.2.50
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class LaunchedMunitionRecord extends Object implements Serializable
{
   protected EventIdentifier  fireEventID = new EventIdentifier(); 

   protected int  padding;

   protected EventIdentifier  firingEntityID = new EventIdentifier(); 

   protected int  padding2;

   protected EventIdentifier  targetEntityID = new EventIdentifier(); 

   protected int  padding3;

   protected Vector3Double  targetLocation = new Vector3Double(); 


/** Constructor */
 public LaunchedMunitionRecord()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + fireEventID.getMarshalledSize();  // fireEventID
   marshalSize = marshalSize + 2;  // padding
   marshalSize = marshalSize + firingEntityID.getMarshalledSize();  // firingEntityID
   marshalSize = marshalSize + 2;  // padding2
   marshalSize = marshalSize + targetEntityID.getMarshalledSize();  // targetEntityID
   marshalSize = marshalSize + 2;  // padding3
   marshalSize = marshalSize + targetLocation.getMarshalledSize();  // targetLocation

   return marshalSize;
}


public void setFireEventID(EventIdentifier pFireEventID)
{ fireEventID = pFireEventID;
}

public EventIdentifier getFireEventID()
{ return fireEventID; 
}

public void setPadding(int pPadding)
{ padding = pPadding;
}

public int getPadding()
{ return padding; 
}

public void setFiringEntityID(EventIdentifier pFiringEntityID)
{ firingEntityID = pFiringEntityID;
}

public EventIdentifier getFiringEntityID()
{ return firingEntityID; 
}

public void setPadding2(int pPadding2)
{ padding2 = pPadding2;
}

public int getPadding2()
{ return padding2; 
}

public void setTargetEntityID(EventIdentifier pTargetEntityID)
{ targetEntityID = pTargetEntityID;
}

public EventIdentifier getTargetEntityID()
{ return targetEntityID; 
}

public void setPadding3(int pPadding3)
{ padding3 = pPadding3;
}

public int getPadding3()
{ return padding3; 
}

public void setTargetLocation(Vector3Double pTargetLocation)
{ targetLocation = pTargetLocation;
}

public Vector3Double getTargetLocation()
{ return targetLocation; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       fireEventID.marshal(dos);
       dos.writeShort( (short)padding);
       firingEntityID.marshal(dos);
       dos.writeShort( (short)padding2);
       targetEntityID.marshal(dos);
       dos.writeShort( (short)padding3);
       targetLocation.marshal(dos);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       fireEventID.unmarshal(dis);
       padding = (int)dis.readUnsignedShort();
       firingEntityID.unmarshal(dis);
       padding2 = (int)dis.readUnsignedShort();
       targetEntityID.unmarshal(dis);
       padding3 = (int)dis.readUnsignedShort();
       targetLocation.unmarshal(dis);
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
       fireEventID.marshal(buff);
       buff.putShort( (short)padding);
       firingEntityID.marshal(buff);
       buff.putShort( (short)padding2);
       targetEntityID.marshal(buff);
       buff.putShort( (short)padding3);
       targetLocation.marshal(buff);
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
       fireEventID.unmarshal(buff);
       padding = (int)(buff.getShort() & 0xFFFF);
       firingEntityID.unmarshal(buff);
       padding2 = (int)(buff.getShort() & 0xFFFF);
       targetEntityID.unmarshal(buff);
       padding3 = (int)(buff.getShort() & 0xFFFF);
       targetLocation.unmarshal(buff);
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

    if(!(obj instanceof LaunchedMunitionRecord))
        return false;

     final LaunchedMunitionRecord rhs = (LaunchedMunitionRecord)obj;

     if( ! (fireEventID.equals( rhs.fireEventID) )) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (firingEntityID.equals( rhs.firingEntityID) )) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;
     if( ! (targetEntityID.equals( rhs.targetEntityID) )) ivarsEqual = false;
     if( ! (padding3 == rhs.padding3)) ivarsEqual = false;
     if( ! (targetLocation.equals( rhs.targetLocation) )) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
