package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;

// Jaxb and Hibernate annotations generally won't work on mobile devices. XML serialization uses jaxb, and
// javax.persistence uses the JPA JSR, aka hibernate. See the Hibernate site for details.
// To generate Java code without these, and without the annotations scattered through the
// see the XMLPG java code generator, and set the boolean useHibernateAnnotations and useJaxbAnnotions 
// to false, and then regenerate the code

import javax.xml.bind.*;            // Used for JAXB XML serialization
import javax.xml.bind.annotation.*; // Used for XML serialization annotations (the @ stuff)
import javax.persistence.*;         // Used for JPA/Hibernate SQL persistence

/**
 * DE Precision Aimpoint Record. NOT COMPLETE. Section 6.2.20.2
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class DirectedEnergyAreaAimpoint extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_DirectedEnergyAreaAimpoint;

   /** Type of Record enumeration */
   protected long  recordType = (long)4001;

   /** Length of Record */
   protected int  recordLength;

   /** Padding */
   protected int  padding = (int)0;

   /** Number of beam antenna pattern records */
   protected int  beamAntennaPatternRecordCount = (int)0;

   /** Number of DE target energy depositon records */
   protected int  directedEnergyTargetEnergyDepositionRecordCount = (int)0;

   /** list of beam antenna records. See 6.2.9.2 */
   protected List< BeamAntennaPattern > beamAntennaParameterList = new ArrayList< BeamAntennaPattern >(); 
   /** list of DE target deposition records. See 6.2.21.4 */
   protected List< DirectedEnergyTargetEnergyDeposition > directedEnergyTargetEnergyDepositionRecordList = new ArrayList< DirectedEnergyTargetEnergyDeposition >(); 

/** Constructor */
 public DirectedEnergyAreaAimpoint()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + 4;  // recordType
   marshalSize = marshalSize + 2;  // recordLength
   marshalSize = marshalSize + 2;  // padding
   marshalSize = marshalSize + 2;  // beamAntennaPatternRecordCount
   marshalSize = marshalSize + 2;  // directedEnergyTargetEnergyDepositionRecordCount
   for(int idx=0; idx < beamAntennaParameterList.size(); idx++)
   {
        BeamAntennaPattern listElement = beamAntennaParameterList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   for(int idx=0; idx < directedEnergyTargetEnergyDepositionRecordList.size(); idx++)
   {
        DirectedEnergyTargetEnergyDeposition listElement = directedEnergyTargetEnergyDepositionRecordList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_DirectedEnergyAreaAimpoint()
{
   return pk_DirectedEnergyAreaAimpoint;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_DirectedEnergyAreaAimpoint(long pKeyName)
{
   this.pk_DirectedEnergyAreaAimpoint = pKeyName;
}

public void setRecordType(long pRecordType)
{ recordType = pRecordType;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getRecordType()
{ return recordType; 
}

public void setRecordLength(int pRecordLength)
{ recordLength = pRecordLength;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getRecordLength()
{ return recordLength; 
}

public void setPadding(int pPadding)
{ padding = pPadding;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding()
{ return padding; 
}

@XmlAttribute
@Basic
public int getBeamAntennaPatternRecordCount()
{ return (int)beamAntennaParameterList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getbeamAntennaPatternRecordCount method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setBeamAntennaPatternRecordCount(int pBeamAntennaPatternRecordCount)
{ beamAntennaPatternRecordCount = pBeamAntennaPatternRecordCount;
}

@XmlAttribute
@Basic
public int getDirectedEnergyTargetEnergyDepositionRecordCount()
{ return (int)directedEnergyTargetEnergyDepositionRecordList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getdirectedEnergyTargetEnergyDepositionRecordCount method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setDirectedEnergyTargetEnergyDepositionRecordCount(int pDirectedEnergyTargetEnergyDepositionRecordCount)
{ directedEnergyTargetEnergyDepositionRecordCount = pDirectedEnergyTargetEnergyDepositionRecordCount;
}

public void setBeamAntennaParameterList(List<BeamAntennaPattern> pBeamAntennaParameterList)
{ beamAntennaParameterList = pBeamAntennaParameterList;
}

@XmlElementWrapper(name="beamAntennaParameterListList" ) //  Jaxb
@OneToMany    // Hibernate
public List<BeamAntennaPattern> getBeamAntennaParameterList()
{ return beamAntennaParameterList; }

public void setDirectedEnergyTargetEnergyDepositionRecordList(List<DirectedEnergyTargetEnergyDeposition> pDirectedEnergyTargetEnergyDepositionRecordList)
{ directedEnergyTargetEnergyDepositionRecordList = pDirectedEnergyTargetEnergyDepositionRecordList;
}

@XmlElementWrapper(name="directedEnergyTargetEnergyDepositionRecordListList" ) //  Jaxb
@OneToMany    // Hibernate
public List<DirectedEnergyTargetEnergyDeposition> getDirectedEnergyTargetEnergyDepositionRecordList()
{ return directedEnergyTargetEnergyDepositionRecordList; }


public void marshal(DataOutputStream dos)
{
    try 
    {
       dos.writeInt( (int)recordType);
       dos.writeShort( (short)recordLength);
       dos.writeShort( (short)padding);
       dos.writeShort( (short)beamAntennaParameterList.size());
       dos.writeShort( (short)directedEnergyTargetEnergyDepositionRecordList.size());

       for(int idx = 0; idx < beamAntennaParameterList.size(); idx++)
       {
            BeamAntennaPattern aBeamAntennaPattern = beamAntennaParameterList.get(idx);
            aBeamAntennaPattern.marshal(dos);
       } // end of list marshalling


       for(int idx = 0; idx < directedEnergyTargetEnergyDepositionRecordList.size(); idx++)
       {
            DirectedEnergyTargetEnergyDeposition aDirectedEnergyTargetEnergyDeposition = directedEnergyTargetEnergyDepositionRecordList.get(idx);
            aDirectedEnergyTargetEnergyDeposition.marshal(dos);
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
       recordType = dis.readInt();
       recordLength = (int)dis.readUnsignedShort();
       padding = (int)dis.readUnsignedShort();
       beamAntennaPatternRecordCount = (int)dis.readUnsignedShort();
       directedEnergyTargetEnergyDepositionRecordCount = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < beamAntennaPatternRecordCount; idx++)
       {
           BeamAntennaPattern anX = new BeamAntennaPattern();
           anX.unmarshal(dis);
           beamAntennaParameterList.add(anX);
       }

       for(int idx = 0; idx < directedEnergyTargetEnergyDepositionRecordCount; idx++)
       {
           DirectedEnergyTargetEnergyDeposition anX = new DirectedEnergyTargetEnergyDeposition();
           anX.unmarshal(dis);
           directedEnergyTargetEnergyDepositionRecordList.add(anX);
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
       buff.putInt( (int)recordType);
       buff.putShort( (short)recordLength);
       buff.putShort( (short)padding);
       buff.putShort( (short)beamAntennaParameterList.size());
       buff.putShort( (short)directedEnergyTargetEnergyDepositionRecordList.size());

       for(int idx = 0; idx < beamAntennaParameterList.size(); idx++)
       {
            BeamAntennaPattern aBeamAntennaPattern = (BeamAntennaPattern)beamAntennaParameterList.get(idx);
            aBeamAntennaPattern.marshal(buff);
       } // end of list marshalling


       for(int idx = 0; idx < directedEnergyTargetEnergyDepositionRecordList.size(); idx++)
       {
            DirectedEnergyTargetEnergyDeposition aDirectedEnergyTargetEnergyDeposition = (DirectedEnergyTargetEnergyDeposition)directedEnergyTargetEnergyDepositionRecordList.get(idx);
            aDirectedEnergyTargetEnergyDeposition.marshal(buff);
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
       recordType = buff.getInt();
       recordLength = (int)(buff.getShort() & 0xFFFF);
       padding = (int)(buff.getShort() & 0xFFFF);
       beamAntennaPatternRecordCount = (int)(buff.getShort() & 0xFFFF);
       directedEnergyTargetEnergyDepositionRecordCount = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < beamAntennaPatternRecordCount; idx++)
       {
            BeamAntennaPattern anX = new BeamAntennaPattern();
            anX.unmarshal(buff);
            beamAntennaParameterList.add(anX);
       }

       for(int idx = 0; idx < directedEnergyTargetEnergyDepositionRecordCount; idx++)
       {
            DirectedEnergyTargetEnergyDeposition anX = new DirectedEnergyTargetEnergyDeposition();
            anX.unmarshal(buff);
            directedEnergyTargetEnergyDepositionRecordList.add(anX);
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

    if(!(obj instanceof DirectedEnergyAreaAimpoint))
        return false;

     final DirectedEnergyAreaAimpoint rhs = (DirectedEnergyAreaAimpoint)obj;

     if( ! (recordType == rhs.recordType)) ivarsEqual = false;
     if( ! (recordLength == rhs.recordLength)) ivarsEqual = false;
     if( ! (padding == rhs.padding)) ivarsEqual = false;
     if( ! (beamAntennaPatternRecordCount == rhs.beamAntennaPatternRecordCount)) ivarsEqual = false;
     if( ! (directedEnergyTargetEnergyDepositionRecordCount == rhs.directedEnergyTargetEnergyDepositionRecordCount)) ivarsEqual = false;

     for(int idx = 0; idx < beamAntennaParameterList.size(); idx++)
     {
        if( ! ( beamAntennaParameterList.get(idx).equals(rhs.beamAntennaParameterList.get(idx)))) ivarsEqual = false;
     }


     for(int idx = 0; idx < directedEnergyTargetEnergyDepositionRecordList.size(); idx++)
     {
        if( ! ( directedEnergyTargetEnergyDepositionRecordList.get(idx).equals(rhs.directedEnergyTargetEnergyDepositionRecordList.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual;
 }
} // end of class
