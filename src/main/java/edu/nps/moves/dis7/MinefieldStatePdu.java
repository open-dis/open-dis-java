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
 * information about the complete minefield. The minefield presence, perimiter, etc. Section 7.9.2 COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class MinefieldStatePdu extends MinefieldFamilyPdu implements Serializable
{
   /** Minefield ID */
   protected MinefieldIdentifier  minefieldID = new MinefieldIdentifier(); 

   /** Minefield sequence */
   protected int  minefieldSequence;

   /** force ID */
   protected short  forceID;

   /** Number of permieter points */
   protected short  numberOfPerimeterPoints;

   /** type of minefield */
   protected EntityType  minefieldType = new EntityType(); 

   /** how many mine types */
   protected int  numberOfMineTypes;

   /** location of center of minefield in world coords */
   protected Vector3Double  minefieldLocation = new Vector3Double(); 

   /** orientation of minefield */
   protected EulerAngles  minefieldOrientation = new EulerAngles(); 

   /** appearance bitflags */
   protected int  appearance;

   /** protocolMode. First two bits are the protocol mode, 14 bits reserved. */
   protected int  protocolMode;

   /** perimeter points for the minefield */
   protected List< Vector2Float > perimeterPoints = new ArrayList< Vector2Float >(); 
   /** Type of mines */
   protected List< EntityType > mineType = new ArrayList< EntityType >(); 

/** Constructor */
 public MinefieldStatePdu()
 {
    setPduType( (short)37 );
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + minefieldID.getMarshalledSize();  // minefieldID
   marshalSize = marshalSize + 2;  // minefieldSequence
   marshalSize = marshalSize + 1;  // forceID
   marshalSize = marshalSize + 1;  // numberOfPerimeterPoints
   marshalSize = marshalSize + minefieldType.getMarshalledSize();  // minefieldType
   marshalSize = marshalSize + 2;  // numberOfMineTypes
   marshalSize = marshalSize + minefieldLocation.getMarshalledSize();  // minefieldLocation
   marshalSize = marshalSize + minefieldOrientation.getMarshalledSize();  // minefieldOrientation
   marshalSize = marshalSize + 2;  // appearance
   marshalSize = marshalSize + 2;  // protocolMode
   for(int idx=0; idx < perimeterPoints.size(); idx++)
   {
        Vector2Float listElement = perimeterPoints.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }
   for(int idx=0; idx < mineType.size(); idx++)
   {
        EntityType listElement = mineType.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setMinefieldID(MinefieldIdentifier pMinefieldID)
{ minefieldID = pMinefieldID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_minefieldID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_minefieldID")
public MinefieldIdentifier getMinefieldID()
{ return minefieldID; 
}

public void setMinefieldSequence(int pMinefieldSequence)
{ minefieldSequence = pMinefieldSequence;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getMinefieldSequence()
{ return minefieldSequence; 
}

public void setForceID(short pForceID)
{ forceID = pForceID;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public short getForceID()
{ return forceID; 
}

@XmlAttribute
@Basic
public short getNumberOfPerimeterPoints()
{ return (short)perimeterPoints.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfPerimeterPoints method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfPerimeterPoints(short pNumberOfPerimeterPoints)
{ numberOfPerimeterPoints = pNumberOfPerimeterPoints;
}

public void setMinefieldType(EntityType pMinefieldType)
{ minefieldType = pMinefieldType;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_minefieldType;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_minefieldType")
public EntityType getMinefieldType()
{ return minefieldType; 
}

@XmlAttribute
@Basic
public int getNumberOfMineTypes()
{ return (int)mineType.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfMineTypes method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfMineTypes(int pNumberOfMineTypes)
{ numberOfMineTypes = pNumberOfMineTypes;
}

public void setMinefieldLocation(Vector3Double pMinefieldLocation)
{ minefieldLocation = pMinefieldLocation;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_minefieldLocation;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_minefieldLocation")
public Vector3Double getMinefieldLocation()
{ return minefieldLocation; 
}

public void setMinefieldOrientation(EulerAngles pMinefieldOrientation)
{ minefieldOrientation = pMinefieldOrientation;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_minefieldOrientation;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_minefieldOrientation")
public EulerAngles getMinefieldOrientation()
{ return minefieldOrientation; 
}

public void setAppearance(int pAppearance)
{ appearance = pAppearance;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getAppearance()
{ return appearance; 
}

public void setProtocolMode(int pProtocolMode)
{ protocolMode = pProtocolMode;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getProtocolMode()
{ return protocolMode; 
}

public void setPerimeterPoints(List<Vector2Float> pPerimeterPoints)
{ perimeterPoints = pPerimeterPoints;
}

@XmlElementWrapper(name="perimeterPointsList" ) //  Jaxb
@OneToMany    // Hibernate
public List<Vector2Float> getPerimeterPoints()
{ return perimeterPoints; }

public void setMineType(List<EntityType> pMineType)
{ mineType = pMineType;
}

@XmlElementWrapper(name="mineTypeList" ) //  Jaxb
@OneToMany    // Hibernate
public List<EntityType> getMineType()
{ return mineType; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       minefieldID.marshal(dos);
       dos.writeShort( (short)minefieldSequence);
       dos.writeByte( (byte)forceID);
       dos.writeByte( (byte)perimeterPoints.size());
       minefieldType.marshal(dos);
       dos.writeShort( (short)mineType.size());
       minefieldLocation.marshal(dos);
       minefieldOrientation.marshal(dos);
       dos.writeShort( (short)appearance);
       dos.writeShort( (short)protocolMode);

       for(int idx = 0; idx < perimeterPoints.size(); idx++)
       {
            Vector2Float aVector2Float = perimeterPoints.get(idx);
            aVector2Float.marshal(dos);
       } // end of list marshalling


       for(int idx = 0; idx < mineType.size(); idx++)
       {
            EntityType aEntityType = mineType.get(idx);
            aEntityType.marshal(dos);
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
       minefieldID.unmarshal(dis);
       minefieldSequence = (int)dis.readUnsignedShort();
       forceID = (short)dis.readUnsignedByte();
       numberOfPerimeterPoints = (short)dis.readUnsignedByte();
       minefieldType.unmarshal(dis);
       numberOfMineTypes = (int)dis.readUnsignedShort();
       minefieldLocation.unmarshal(dis);
       minefieldOrientation.unmarshal(dis);
       appearance = (int)dis.readUnsignedShort();
       protocolMode = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < numberOfPerimeterPoints; idx++)
       {
           Vector2Float anX = new Vector2Float();
           anX.unmarshal(dis);
           perimeterPoints.add(anX);
       }

       for(int idx = 0; idx < numberOfMineTypes; idx++)
       {
           EntityType anX = new EntityType();
           anX.unmarshal(dis);
           mineType.add(anX);
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
       minefieldID.marshal(buff);
       buff.putShort( (short)minefieldSequence);
       buff.put( (byte)forceID);
       buff.put( (byte)perimeterPoints.size());
       minefieldType.marshal(buff);
       buff.putShort( (short)mineType.size());
       minefieldLocation.marshal(buff);
       minefieldOrientation.marshal(buff);
       buff.putShort( (short)appearance);
       buff.putShort( (short)protocolMode);

       for(int idx = 0; idx < perimeterPoints.size(); idx++)
       {
            Vector2Float aVector2Float = (Vector2Float)perimeterPoints.get(idx);
            aVector2Float.marshal(buff);
       } // end of list marshalling


       for(int idx = 0; idx < mineType.size(); idx++)
       {
            EntityType aEntityType = (EntityType)mineType.get(idx);
            aEntityType.marshal(buff);
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

       minefieldID.unmarshal(buff);
       minefieldSequence = (int)(buff.getShort() & 0xFFFF);
       forceID = (short)(buff.get() & 0xFF);
       numberOfPerimeterPoints = (short)(buff.get() & 0xFF);
       minefieldType.unmarshal(buff);
       numberOfMineTypes = (int)(buff.getShort() & 0xFFFF);
       minefieldLocation.unmarshal(buff);
       minefieldOrientation.unmarshal(buff);
       appearance = (int)(buff.getShort() & 0xFFFF);
       protocolMode = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < numberOfPerimeterPoints; idx++)
       {
            Vector2Float anX = new Vector2Float();
            anX.unmarshal(buff);
            perimeterPoints.add(anX);
       }

       for(int idx = 0; idx < numberOfMineTypes; idx++)
       {
            EntityType anX = new EntityType();
            anX.unmarshal(buff);
            mineType.add(anX);
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

    if(!(obj instanceof MinefieldStatePdu))
        return false;

     final MinefieldStatePdu rhs = (MinefieldStatePdu)obj;

     if( ! (minefieldID.equals( rhs.minefieldID) )) ivarsEqual = false;
     if( ! (minefieldSequence == rhs.minefieldSequence)) ivarsEqual = false;
     if( ! (forceID == rhs.forceID)) ivarsEqual = false;
     if( ! (numberOfPerimeterPoints == rhs.numberOfPerimeterPoints)) ivarsEqual = false;
     if( ! (minefieldType.equals( rhs.minefieldType) )) ivarsEqual = false;
     if( ! (numberOfMineTypes == rhs.numberOfMineTypes)) ivarsEqual = false;
     if( ! (minefieldLocation.equals( rhs.minefieldLocation) )) ivarsEqual = false;
     if( ! (minefieldOrientation.equals( rhs.minefieldOrientation) )) ivarsEqual = false;
     if( ! (appearance == rhs.appearance)) ivarsEqual = false;
     if( ! (protocolMode == rhs.protocolMode)) ivarsEqual = false;

     for(int idx = 0; idx < perimeterPoints.size(); idx++)
     {
        if( ! ( perimeterPoints.get(idx).equals(rhs.perimeterPoints.get(idx)))) ivarsEqual = false;
     }


     for(int idx = 0; idx < mineType.size(); idx++)
     {
        if( ! ( mineType.get(idx).equals(rhs.mineType.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
