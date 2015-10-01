package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.3.6.8. Request for data from an entity. COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DataQueryPdu extends SimulationManagementFamilyPdu implements Serializable
{
   /** ID of request */
   protected long  requestID;

   /** time issues between issues of Data PDUs. Zero means send once only. */
   protected long  timeInterval;

   /** Number of fixed datum records */
   protected long  numberOfFixedDatumRecords;

   /** Number of variable datum records */
   protected long  numberOfVariableDatumRecords;

   /** variable length list of fixed datums */
   protected List< UnsignedIntegerWrapper > fixedDatums = new ArrayList< UnsignedIntegerWrapper >(); 
   /** variable length list of variable length datums */
   protected List< UnsignedIntegerWrapper > variableDatums = new ArrayList< UnsignedIntegerWrapper >(); 

/** Constructor */
 public DataQueryPdu()
 {
    setPduType( (short)18 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + 4;  // requestID
   marshalSize = marshalSize + 4;  // timeInterval
   marshalSize = marshalSize + 4;  // numberOfFixedDatumRecords
   marshalSize = marshalSize + 4;  // numberOfVariableDatumRecords
   for(int idx=0; idx < fixedDatums.size(); idx++)
   {
        UnsignedIntegerWrapper listElement = fixedDatums.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   for(int idx=0; idx < variableDatums.size(); idx++)
   {
        UnsignedIntegerWrapper listElement = variableDatums.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setRequestID(long pRequestID)
{ requestID = pRequestID;
}

public long getRequestID()
{ return requestID; 
}

public void setTimeInterval(long pTimeInterval)
{ timeInterval = pTimeInterval;
}

public long getTimeInterval()
{ return timeInterval; 
}

public long getNumberOfFixedDatumRecords()
{ return (long)fixedDatums.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfFixedDatumRecords method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfFixedDatumRecords(long pNumberOfFixedDatumRecords)
{ numberOfFixedDatumRecords = pNumberOfFixedDatumRecords;
}

public long getNumberOfVariableDatumRecords()
{ return (long)variableDatums.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfVariableDatumRecords method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfVariableDatumRecords(long pNumberOfVariableDatumRecords)
{ numberOfVariableDatumRecords = pNumberOfVariableDatumRecords;
}

public void setFixedDatums(List<UnsignedIntegerWrapper> pFixedDatums)
{ fixedDatums = pFixedDatums;
}

public List<UnsignedIntegerWrapper> getFixedDatums()
{ return fixedDatums; }

public void setVariableDatums(List<UnsignedIntegerWrapper> pVariableDatums)
{ variableDatums = pVariableDatums;
}

public List<UnsignedIntegerWrapper> getVariableDatums()
{ return variableDatums; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       dos.writeInt( (int)requestID);
       dos.writeInt( (int)timeInterval);
       dos.writeInt( (int)fixedDatums.size());
       dos.writeInt( (int)variableDatums.size());

       for(int idx = 0; idx < fixedDatums.size(); idx++)
       {
            UnsignedIntegerWrapper aUnsignedIntegerWrapper = fixedDatums.get(idx);
            aUnsignedIntegerWrapper.marshal(dos);
       } // end of list marshalling


       for(int idx = 0; idx < variableDatums.size(); idx++)
       {
            UnsignedIntegerWrapper aUnsignedIntegerWrapper = variableDatums.get(idx);
            aUnsignedIntegerWrapper.marshal(dos);
       } // end of list marshalling

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
       requestID = dis.readInt();
       timeInterval = dis.readInt();
       numberOfFixedDatumRecords = dis.readInt();
       numberOfVariableDatumRecords = dis.readInt();
       for(int idx = 0; idx < numberOfFixedDatumRecords; idx++)
       {
           UnsignedIntegerWrapper anX = new UnsignedIntegerWrapper();
           anX.unmarshal(dis);
           fixedDatums.add(anX);
       }

       for(int idx = 0; idx < numberOfVariableDatumRecords; idx++)
       {
           UnsignedIntegerWrapper anX = new UnsignedIntegerWrapper();
           anX.unmarshal(dis);
           variableDatums.add(anX);
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
       super.marshal(buff);
       buff.putInt( (int)requestID);
       buff.putInt( (int)timeInterval);
       buff.putInt( (int)fixedDatums.size());
       buff.putInt( (int)variableDatums.size());

       for(int idx = 0; idx < fixedDatums.size(); idx++)
       {
            UnsignedIntegerWrapper aUnsignedIntegerWrapper = (UnsignedIntegerWrapper)fixedDatums.get(idx);
            aUnsignedIntegerWrapper.marshal(buff);
       } // end of list marshalling


       for(int idx = 0; idx < variableDatums.size(); idx++)
       {
            UnsignedIntegerWrapper aUnsignedIntegerWrapper = (UnsignedIntegerWrapper)variableDatums.get(idx);
            aUnsignedIntegerWrapper.marshal(buff);
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
       super.unmarshal(buff);

       requestID = buff.getInt();
       timeInterval = buff.getInt();
       numberOfFixedDatumRecords = buff.getInt();
       numberOfVariableDatumRecords = buff.getInt();
       for(int idx = 0; idx < numberOfFixedDatumRecords; idx++)
       {
            UnsignedIntegerWrapper anX = new UnsignedIntegerWrapper();
            anX.unmarshal(buff);
            fixedDatums.add(anX);
       }

       for(int idx = 0; idx < numberOfVariableDatumRecords; idx++)
       {
            UnsignedIntegerWrapper anX = new UnsignedIntegerWrapper();
            anX.unmarshal(buff);
            variableDatums.add(anX);
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

@Override
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof DataQueryPdu))
        return false;

     final DataQueryPdu rhs = (DataQueryPdu)obj;

     if( ! (requestID == rhs.requestID)) ivarsEqual = false;
     if( ! (timeInterval == rhs.timeInterval)) ivarsEqual = false;
     if( ! (numberOfFixedDatumRecords == rhs.numberOfFixedDatumRecords)) ivarsEqual = false;
     if( ! (numberOfVariableDatumRecords == rhs.numberOfVariableDatumRecords)) ivarsEqual = false;

     for(int idx = 0; idx < fixedDatums.size(); idx++)
     {
        if( ! ( fixedDatums.get(idx).equals(rhs.fixedDatums.get(idx)))) ivarsEqual = false;
     }


     for(int idx = 0; idx < variableDatums.size(); idx++)
     {
        if( ! ( variableDatums.get(idx).equals(rhs.variableDatums.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
