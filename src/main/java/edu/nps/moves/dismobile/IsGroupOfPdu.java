package edu.nps.moves.dismobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Section 5.3.9.2 Information about a particular group of entities grouped together for the purposes of netowrk bandwidth         reduction or aggregation. Needs manual cleanup. The GED size requires a database lookup. UNFINISHED
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class IsGroupOfPdu extends EntityManagementFamilyPdu implements Serializable
{
   /** ID of aggregated entities */
   protected EntityID  groupEntityID = new EntityID(); 

   /** type of entities constituting the group */
   protected short  groupedEntityCategory;

   /** Number of individual entities constituting the group */
   protected short  numberOfGroupedEntities;

   /** padding */
   protected long  pad2;

   /** latitude */
   protected double  latitude;

   /** longitude */
   protected double  longitude;

   /** GED records about each individual entity in the group. ^^^this is wrong--need a database lookup to find the actual size of the list elements */
   protected List< VariableDatum > groupedEntityDescriptions = new ArrayList< VariableDatum >(); 

/** Constructor */
 public IsGroupOfPdu()
 {
    setPduType( (short)34 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + groupEntityID.getMarshalledSize();  // groupEntityID
   marshalSize = marshalSize + 1;  // groupedEntityCategory
   marshalSize = marshalSize + 1;  // numberOfGroupedEntities
   marshalSize = marshalSize + 4;  // pad2
   marshalSize = marshalSize + 8;  // latitude
   marshalSize = marshalSize + 8;  // longitude
   for(int idx=0; idx < groupedEntityDescriptions.size(); idx++)
   {
        VariableDatum listElement = groupedEntityDescriptions.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setGroupEntityID(EntityID pGroupEntityID)
{ groupEntityID = pGroupEntityID;
}

public EntityID getGroupEntityID()
{ return groupEntityID; 
}

public void setGroupedEntityCategory(short pGroupedEntityCategory)
{ groupedEntityCategory = pGroupedEntityCategory;
}

public short getGroupedEntityCategory()
{ return groupedEntityCategory; 
}

public short getNumberOfGroupedEntities()
{ return (short)groupedEntityDescriptions.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfGroupedEntities method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfGroupedEntities(short pNumberOfGroupedEntities)
{ numberOfGroupedEntities = pNumberOfGroupedEntities;
}

public void setPad2(long pPad2)
{ pad2 = pPad2;
}

public long getPad2()
{ return pad2; 
}

public void setLatitude(double pLatitude)
{ latitude = pLatitude;
}

public double getLatitude()
{ return latitude; 
}

public void setLongitude(double pLongitude)
{ longitude = pLongitude;
}

public double getLongitude()
{ return longitude; 
}

public void setGroupedEntityDescriptions(List<VariableDatum> pGroupedEntityDescriptions)
{ groupedEntityDescriptions = pGroupedEntityDescriptions;
}

public List<VariableDatum> getGroupedEntityDescriptions()
{ return groupedEntityDescriptions; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       groupEntityID.marshal(dos);
       dos.writeByte( (byte)groupedEntityCategory);
       dos.writeByte( (byte)groupedEntityDescriptions.size());
       dos.writeInt( (int)pad2);
       dos.writeDouble( (double)latitude);
       dos.writeDouble( (double)longitude);

       for(int idx = 0; idx < groupedEntityDescriptions.size(); idx++)
       {
            VariableDatum aVariableDatum = groupedEntityDescriptions.get(idx);
            aVariableDatum.marshal(dos);
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
       groupEntityID.unmarshal(dis);
       groupedEntityCategory = (short)dis.readUnsignedByte();
       numberOfGroupedEntities = (short)dis.readUnsignedByte();
       pad2 = dis.readInt();
       latitude = dis.readDouble();
       longitude = dis.readDouble();
       for(int idx = 0; idx < numberOfGroupedEntities; idx++)
       {
           VariableDatum anX = new VariableDatum();
           anX.unmarshal(dis);
           groupedEntityDescriptions.add(anX);
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
       groupEntityID.marshal(buff);
       buff.put( (byte)groupedEntityCategory);
       buff.put( (byte)groupedEntityDescriptions.size());
       buff.putInt( (int)pad2);
       buff.putDouble( (double)latitude);
       buff.putDouble( (double)longitude);

       for(int idx = 0; idx < groupedEntityDescriptions.size(); idx++)
       {
            VariableDatum aVariableDatum = (VariableDatum)groupedEntityDescriptions.get(idx);
            aVariableDatum.marshal(buff);
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

       groupEntityID.unmarshal(buff);
       groupedEntityCategory = (short)(buff.get() & 0xFF);
       numberOfGroupedEntities = (short)(buff.get() & 0xFF);
       pad2 = buff.getInt();
       latitude = buff.getDouble();
       longitude = buff.getDouble();
       for(int idx = 0; idx < numberOfGroupedEntities; idx++)
       {
            VariableDatum anX = new VariableDatum();
            anX.unmarshal(buff);
            groupedEntityDescriptions.add(anX);
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

    if(!(obj instanceof IsGroupOfPdu))
        return false;

     final IsGroupOfPdu rhs = (IsGroupOfPdu)obj;

     if( ! (groupEntityID.equals( rhs.groupEntityID) )) ivarsEqual = false;
     if( ! (groupedEntityCategory == rhs.groupedEntityCategory)) ivarsEqual = false;
     if( ! (numberOfGroupedEntities == rhs.numberOfGroupedEntities)) ivarsEqual = false;
     if( ! (pad2 == rhs.pad2)) ivarsEqual = false;
     if( ! (latitude == rhs.latitude)) ivarsEqual = false;
     if( ! (longitude == rhs.longitude)) ivarsEqual = false;

     for(int idx = 0; idx < groupedEntityDescriptions.size(); idx++)
     {
        if( ! ( groupedEntityDescriptions.get(idx).equals(rhs.groupedEntityDescriptions.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
