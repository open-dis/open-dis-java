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
 * First part of a simulation management (SIMAN) PDU and SIMAN-Reliability (SIMAN-R) PDU. Sectionn 6.2.81
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class SimulationManagementPduHeader extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_SimulationManagementPduHeader;

   /** Conventional PDU header */
   protected PduHeader  pduHeader = new PduHeader(); 

   /** IDs the simulation or entity, etiehr a simulation or an entity. Either 6.2.80 or 6.2.28 */
   protected SimulationIdentifier  originatingID = new SimulationIdentifier(); 

   /** simulation, all simulations, a special ID, or an entity. See 5.6.5 and 5.12.4 */
   protected SimulationIdentifier  receivingID = new SimulationIdentifier(); 


/** Constructor */
 public SimulationManagementPduHeader()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + pduHeader.getMarshalledSize();  // pduHeader
   marshalSize = marshalSize + originatingID.getMarshalledSize();  // originatingID
   marshalSize = marshalSize + receivingID.getMarshalledSize();  // receivingID

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_SimulationManagementPduHeader()
{
   return pk_SimulationManagementPduHeader;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_SimulationManagementPduHeader(long pKeyName)
{
   this.pk_SimulationManagementPduHeader = pKeyName;
}

public void setPduHeader(PduHeader pPduHeader)
{ pduHeader = pPduHeader;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_pduHeader;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_pduHeader")
public PduHeader getPduHeader()
{ return pduHeader; 
}

public void setOriginatingID(SimulationIdentifier pOriginatingID)
{ originatingID = pOriginatingID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_originatingID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_originatingID")
public SimulationIdentifier getOriginatingID()
{ return originatingID; 
}

public void setReceivingID(SimulationIdentifier pReceivingID)
{ receivingID = pReceivingID;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_receivingID;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_receivingID")
public SimulationIdentifier getReceivingID()
{ return receivingID; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       pduHeader.marshal(dos);
       originatingID.marshal(dos);
       receivingID.marshal(dos);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       pduHeader.unmarshal(dis);
       originatingID.unmarshal(dis);
       receivingID.unmarshal(dis);
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
       pduHeader.marshal(buff);
       originatingID.marshal(buff);
       receivingID.marshal(buff);
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
       pduHeader.unmarshal(buff);
       originatingID.unmarshal(buff);
       receivingID.unmarshal(buff);
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

    if(!(obj instanceof SimulationManagementPduHeader))
        return false;

     final SimulationManagementPduHeader rhs = (SimulationManagementPduHeader)obj;

     if( ! (pduHeader.equals( rhs.pduHeader) )) ivarsEqual = false;
     if( ! (originatingID.equals( rhs.originatingID) )) ivarsEqual = false;
     if( ! (receivingID.equals( rhs.receivingID) )) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class
