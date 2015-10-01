package edu.nps.moves.dis;

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
 * Section 5.3.11.2: Information about globat, spatially varying enviornmental effects. This requires manual cleanup; the grid axis        records are variable sized. UNFINISHED
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class GriddedDataPdu extends SyntheticEnvironmentFamilyPdu implements Serializable
{
   /** environmental simulation application ID */
   protected EntityID  environmentalSimulationApplicationID = new EntityID(); 

   /** unique identifier for each piece of enviornmental data */
   protected int  fieldNumber;

   /** sequence number for the total set of PDUS used to transmit the data */
   protected int  pduNumber;

   /** Total number of PDUS used to transmit the data */
   protected int  pduTotal;

   /** coordinate system of the grid */
   protected int  coordinateSystem;

   /** number of grid axes for the environmental data */
   protected short  numberOfGridAxes;

   /** are domain grid axes identidal to those of the priveious domain update? */
   protected short  constantGrid;

   /** type of environment */
   protected EntityType  environmentType = new EntityType(); 

   /** orientation of the data grid */
   protected Orientation  orientation = new Orientation(); 

   /** valid time of the enviormental data sample, 64 bit unsigned int */
   protected long  sampleTime;

   /** total number of all data values for all pdus for an environmental sample */
   protected long  totalValues;

   /** total number of data values at each grid point. */
   protected short  vectorDimension;

   /** padding */
   protected int  padding1;

   /** padding */
   protected short  padding2;

   /** Grid data ^^^This is wrong */
   protected List< GridAxisRecord > gridDataList = new ArrayList< GridAxisRecord >(); 

/** Constructor */
 public GriddedDataPdu()
 {
    setPduType( (short)42 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + environmentalSimulationApplicationID.getMarshalledSize();  // environmentalSimulationApplicationID
   marshalSize = marshalSize + 2;  // fieldNumber
   marshalSize = marshalSize + 2;  // pduNumber
   marshalSize = marshalSize + 2;  // pduTotal
   marshalSize = marshalSize + 2;  // coordinateSystem
   marshalSize = marshalSize + 1;  // numberOfGridAxes
   marshalSize = marshalSize + 1;  // constantGrid
   marshalSize = marshalSize + environmentType.getMarshalledSize();  // environmentType
   marshalSize = marshalSize + orientation.getMarshalledSize();  // orientation
   marshalSize = marshalSize + 8;  // sampleTime
   marshalSize = marshalSize + 4;  // totalValues
   marshalSize = marshalSize + 1;  // vectorDimension
   marshalSize = marshalSize + 2;  // padding1
   marshalSize = marshalSize + 1;  // padding2
   for(int idx=0; idx < gridDataList.size(); idx++)
   {
        GridAxisRecord listElement = gridDataList.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setEnvironmentalSimulationApplicationID(EntityID pEnvironmentalSimulationApplicationID)
{ environmentalSimulationApplicationID = pEnvironmentalSimulationApplicationID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_environmentalSimulationApplicationID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_environmentalSimulationApplicationID")
public EntityID getEnvironmentalSimulationApplicationID()
{ return environmentalSimulationApplicationID; 
}

public void setFieldNumber(int pFieldNumber)
{ fieldNumber = pFieldNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getFieldNumber()
{ return fieldNumber; 
}

public void setPduNumber(int pPduNumber)
{ pduNumber = pPduNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPduNumber()
{ return pduNumber; 
}

public void setPduTotal(int pPduTotal)
{ pduTotal = pPduTotal;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPduTotal()
{ return pduTotal; 
}

public void setCoordinateSystem(int pCoordinateSystem)
{ coordinateSystem = pCoordinateSystem;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getCoordinateSystem()
{ return coordinateSystem; 
}

@XmlAttribute
@Basic
public short getNumberOfGridAxes()
{ return (short)gridDataList.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfGridAxes method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfGridAxes(short pNumberOfGridAxes)
{ numberOfGridAxes = pNumberOfGridAxes;
}

public void setConstantGrid(short pConstantGrid)
{ constantGrid = pConstantGrid;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getConstantGrid()
{ return constantGrid; 
}

public void setEnvironmentType(EntityType pEnvironmentType)
{ environmentType = pEnvironmentType;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_environmentType;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_environmentType")
public EntityType getEnvironmentType()
{ return environmentType; 
}

public void setOrientation(Orientation pOrientation)
{ orientation = pOrientation;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_orientation;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_orientation")
public Orientation getOrientation()
{ return orientation; 
}

public void setSampleTime(long pSampleTime)
{ sampleTime = pSampleTime;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getSampleTime()
{ return sampleTime; 
}

public void setTotalValues(long pTotalValues)
{ totalValues = pTotalValues;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public long getTotalValues()
{ return totalValues; 
}

public void setVectorDimension(short pVectorDimension)
{ vectorDimension = pVectorDimension;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getVectorDimension()
{ return vectorDimension; 
}

public void setPadding1(int pPadding1)
{ padding1 = pPadding1;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getPadding1()
{ return padding1; 
}

public void setPadding2(short pPadding2)
{ padding2 = pPadding2;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getPadding2()
{ return padding2; 
}

public void setGridDataList(List<GridAxisRecord> pGridDataList)
{ gridDataList = pGridDataList;
}

@XmlElementWrapper(name="gridDataListList" ) //  Jaxb
@OneToMany    // Hibernate
public List<GridAxisRecord> getGridDataList()
{ return gridDataList; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       environmentalSimulationApplicationID.marshal(dos);
       dos.writeShort( (short)fieldNumber);
       dos.writeShort( (short)pduNumber);
       dos.writeShort( (short)pduTotal);
       dos.writeShort( (short)coordinateSystem);
       dos.writeByte( (byte)gridDataList.size());
       dos.writeByte( (byte)constantGrid);
       environmentType.marshal(dos);
       orientation.marshal(dos);
       dos.writeLong( (long)sampleTime);
       dos.writeInt( (int)totalValues);
       dos.writeByte( (byte)vectorDimension);
       dos.writeShort( (short)padding1);
       dos.writeByte( (byte)padding2);

       for(int idx = 0; idx < gridDataList.size(); idx++)
       {
            GridAxisRecord aGridAxisRecord = gridDataList.get(idx);
            aGridAxisRecord.marshal(dos);
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
       environmentalSimulationApplicationID.unmarshal(dis);
       fieldNumber = (int)dis.readUnsignedShort();
       pduNumber = (int)dis.readUnsignedShort();
       pduTotal = (int)dis.readUnsignedShort();
       coordinateSystem = (int)dis.readUnsignedShort();
       numberOfGridAxes = (short)dis.readUnsignedByte();
       constantGrid = (short)dis.readUnsignedByte();
       environmentType.unmarshal(dis);
       orientation.unmarshal(dis);
       sampleTime = dis.readLong();
       totalValues = dis.readInt();
       vectorDimension = (short)dis.readUnsignedByte();
       padding1 = (int)dis.readUnsignedShort();
       padding2 = (short)dis.readUnsignedByte();
       for(int idx = 0; idx < numberOfGridAxes; idx++)
       {
           GridAxisRecord anX = new GridAxisRecord();
           anX.unmarshal(dis);
           gridDataList.add(anX);
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
       environmentalSimulationApplicationID.marshal(buff);
       buff.putShort( (short)fieldNumber);
       buff.putShort( (short)pduNumber);
       buff.putShort( (short)pduTotal);
       buff.putShort( (short)coordinateSystem);
       buff.put( (byte)gridDataList.size());
       buff.put( (byte)constantGrid);
       environmentType.marshal(buff);
       orientation.marshal(buff);
       buff.putLong( (long)sampleTime);
       buff.putInt( (int)totalValues);
       buff.put( (byte)vectorDimension);
       buff.putShort( (short)padding1);
       buff.put( (byte)padding2);

       for(int idx = 0; idx < gridDataList.size(); idx++)
       {
            GridAxisRecord aGridAxisRecord = (GridAxisRecord)gridDataList.get(idx);
            aGridAxisRecord.marshal(buff);
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

       environmentalSimulationApplicationID.unmarshal(buff);
       fieldNumber = (int)(buff.getShort() & 0xFFFF);
       pduNumber = (int)(buff.getShort() & 0xFFFF);
       pduTotal = (int)(buff.getShort() & 0xFFFF);
       coordinateSystem = (int)(buff.getShort() & 0xFFFF);
       numberOfGridAxes = (short)(buff.get() & 0xFF);
       constantGrid = (short)(buff.get() & 0xFF);
       environmentType.unmarshal(buff);
       orientation.unmarshal(buff);
       sampleTime = buff.getLong();
       totalValues = buff.getInt();
       vectorDimension = (short)(buff.get() & 0xFF);
       padding1 = (int)(buff.getShort() & 0xFFFF);
       padding2 = (short)(buff.get() & 0xFF);
       for(int idx = 0; idx < numberOfGridAxes; idx++)
       {
            GridAxisRecord anX = new GridAxisRecord();
            anX.unmarshal(buff);
            gridDataList.add(anX);
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

    if(!(obj instanceof GriddedDataPdu))
        return false;

     final GriddedDataPdu rhs = (GriddedDataPdu)obj;

     if( ! (environmentalSimulationApplicationID.equals( rhs.environmentalSimulationApplicationID) )) ivarsEqual = false;
     if( ! (fieldNumber == rhs.fieldNumber)) ivarsEqual = false;
     if( ! (pduNumber == rhs.pduNumber)) ivarsEqual = false;
     if( ! (pduTotal == rhs.pduTotal)) ivarsEqual = false;
     if( ! (coordinateSystem == rhs.coordinateSystem)) ivarsEqual = false;
     if( ! (numberOfGridAxes == rhs.numberOfGridAxes)) ivarsEqual = false;
     if( ! (constantGrid == rhs.constantGrid)) ivarsEqual = false;
     if( ! (environmentType.equals( rhs.environmentType) )) ivarsEqual = false;
     if( ! (orientation.equals( rhs.orientation) )) ivarsEqual = false;
     if( ! (sampleTime == rhs.sampleTime)) ivarsEqual = false;
     if( ! (totalValues == rhs.totalValues)) ivarsEqual = false;
     if( ! (vectorDimension == rhs.vectorDimension)) ivarsEqual = false;
     if( ! (padding1 == rhs.padding1)) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;

     for(int idx = 0; idx < gridDataList.size(); idx++)
     {
        if( ! ( gridDataList.get(idx).equals(rhs.gridDataList.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
