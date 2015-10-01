package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Used for XML compatability. A container that holds PDUs
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class PduContainer extends Object implements Serializable
{
   /** Number of PDUs in the container list */
   protected int  numberOfPdus;

   /** record sets */
   protected List< Pdu > pdus = new ArrayList< Pdu >(); 

/** Constructor */
 public PduContainer()
 {
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // numberOfPdus
   for(int idx=0; idx < pdus.size(); idx++)
   {
        Pdu listElement = pdus.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public int getNumberOfPdus()
{ return (int)pdus.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfPdus method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfPdus(int pNumberOfPdus)
{ numberOfPdus = pNumberOfPdus;
}

public void setPdus(List<Pdu> pPdus)
{ pdus = pPdus;
}

public List<Pdu> getPdus()
{ return pdus; }


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)pdus.size());

       for(int idx = 0; idx < pdus.size(); idx++)
       {
            Pdu aPdu = pdus.get(idx);
            aPdu.marshal(dos);
       } // end of list marshalling

    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       numberOfPdus = dis.readInt();
       for(int idx = 0; idx < numberOfPdus; idx++)
       {
           Pdu anX = new Pdu();
           anX.unmarshal(dis);
           pdus.add(anX);
       }

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
       buff.putInt( (int)pdus.size());

       for(int idx = 0; idx < pdus.size(); idx++)
       {
            Pdu aPdu = (Pdu)pdus.get(idx);
            aPdu.marshal(buff);
       } // end of list marshalling

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
       numberOfPdus = buff.getInt();
       for(int idx = 0; idx < numberOfPdus; idx++)
       {
            Pdu anX = new Pdu();
            anX.unmarshal(buff);
            pdus.add(anX);
       }

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

    if(!(obj instanceof PduContainer))
        return false;

     final PduContainer rhs = (PduContainer)obj;

     if( ! (numberOfPdus == rhs.numberOfPdus)) ivarsEqual = false;

     for(int idx = 0; idx < pdus.size(); idx++)
     {
        if( ! ( pdus.get(idx).equals(rhs.pdus.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
