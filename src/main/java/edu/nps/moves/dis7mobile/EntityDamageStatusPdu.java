package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * shall be used to communicate detailed damage information sustained by an entity regardless of the source of the damage Section 7.3.5  COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class EntityDamageStatusPdu extends WarfareFamilyPdu implements Serializable
{
   /** Field shall identify the damaged entity (see 6.2.28), Section 7.3.4 COMPLETE */
   protected EntityID  damagedEntityID = new EntityID(); 

   /** Padding. */
   protected int  padding1 = (int)0;

   /** Padding. */
   protected int  padding2 = (int)0;

   /** field shall specify the number of Damage Description records, Section 7.3.5 */
   protected int  numberOfDamageDescription = (int)0;

   /** Fields shall contain one or more Damage Description records (see 6.2.17) and may contain other Standard Variable records, Section 7.3.5 */
   protected List< DirectedEnergyDamage > damageDescriptionRecords = new ArrayList< DirectedEnergyDamage >(); 

/** Constructor */
 public EntityDamageStatusPdu()
 {
    setPduType( (short)69 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + damagedEntityID.getMarshalledSize();  // damagedEntityID
   marshalSize = marshalSize + 2;  // padding1
   marshalSize = marshalSize + 2;  // padding2
   marshalSize = marshalSize + 2;  // numberOfDamageDescription
   for(int idx=0; idx < damageDescriptionRecords.size(); idx++)
   {
        DirectedEnergyDamage listElement = damageDescriptionRecords.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setDamagedEntityID(EntityID pDamagedEntityID)
{ damagedEntityID = pDamagedEntityID;
}

public EntityID getDamagedEntityID()
{ return damagedEntityID; 
}

public void setPadding1(int pPadding1)
{ padding1 = pPadding1;
}

public int getPadding1()
{ return padding1; 
}

public void setPadding2(int pPadding2)
{ padding2 = pPadding2;
}

public int getPadding2()
{ return padding2; 
}

public int getNumberOfDamageDescription()
{ return (int)damageDescriptionRecords.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfDamageDescription method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfDamageDescription(int pNumberOfDamageDescription)
{ numberOfDamageDescription = pNumberOfDamageDescription;
}

public void setDamageDescriptionRecords(List<DirectedEnergyDamage> pDamageDescriptionRecords)
{ damageDescriptionRecords = pDamageDescriptionRecords;
}

public List<DirectedEnergyDamage> getDamageDescriptionRecords()
{ return damageDescriptionRecords; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       damagedEntityID.marshal(dos);
       dos.writeShort( (short)padding1);
       dos.writeShort( (short)padding2);
       dos.writeShort( (short)damageDescriptionRecords.size());

       for(int idx = 0; idx < damageDescriptionRecords.size(); idx++)
       {
            DirectedEnergyDamage aDirectedEnergyDamage = damageDescriptionRecords.get(idx);
            aDirectedEnergyDamage.marshal(dos);
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
       damagedEntityID.unmarshal(dis);
       padding1 = (int)dis.readUnsignedShort();
       padding2 = (int)dis.readUnsignedShort();
       numberOfDamageDescription = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < numberOfDamageDescription; idx++)
       {
           DirectedEnergyDamage anX = new DirectedEnergyDamage();
           anX.unmarshal(dis);
           damageDescriptionRecords.add(anX);
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
       damagedEntityID.marshal(buff);
       buff.putShort( (short)padding1);
       buff.putShort( (short)padding2);
       buff.putShort( (short)damageDescriptionRecords.size());

       for(int idx = 0; idx < damageDescriptionRecords.size(); idx++)
       {
            DirectedEnergyDamage aDirectedEnergyDamage = (DirectedEnergyDamage)damageDescriptionRecords.get(idx);
            aDirectedEnergyDamage.marshal(buff);
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

       damagedEntityID.unmarshal(buff);
       padding1 = (int)(buff.getShort() & 0xFFFF);
       padding2 = (int)(buff.getShort() & 0xFFFF);
       numberOfDamageDescription = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < numberOfDamageDescription; idx++)
       {
            DirectedEnergyDamage anX = new DirectedEnergyDamage();
            anX.unmarshal(buff);
            damageDescriptionRecords.add(anX);
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

    if(!(obj instanceof EntityDamageStatusPdu))
        return false;

     final EntityDamageStatusPdu rhs = (EntityDamageStatusPdu)obj;

     if( ! (damagedEntityID.equals( rhs.damagedEntityID) )) ivarsEqual = false;
     if( ! (padding1 == rhs.padding1)) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;
     if( ! (numberOfDamageDescription == rhs.numberOfDamageDescription)) ivarsEqual = false;

     for(int idx = 0; idx < damageDescriptionRecords.size(); idx++)
     {
        if( ! ( damageDescriptionRecords.get(idx).equals(rhs.damageDescriptionRecords.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
