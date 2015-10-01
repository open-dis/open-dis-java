package edu.nps.moves.dis7mobile;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;


/**
 * Firing of a directed energy weapon shall be communicated by issuing a Directed Energy Fire PDU Section 7.3.4  COMPLETE
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
public class DirectedEnergyFirePdu extends WarfareFamilyPdu implements Serializable
{
   /** Field shall identify the munition type enumeration for the DE weapon beam, Section 7.3.4  */
   protected EntityType  munitionType = new EntityType(); 

   /** Field shall indicate the simulation time at start of the shot, Section 7.3.4  */
   protected ClockTime  shotStartTime = new ClockTime(); 

   /** Field shall indicate the current cumulative duration of the shot, Section 7.3.4  */
   protected float  commulativeShotTime;

   /** Field shall identify the location of the DE weapon aperture/emitter, Section 7.3.4  */
   protected Vector3Float  ApertureEmitterLocation = new Vector3Float(); 

   /** Field shall identify the beam diameter at the aperture/emitter, Section 7.3.4  */
   protected float  apertureDiameter;

   /** Field shall identify the emissions wavelength in units of meters, Section 7.3.4  */
   protected float  wavelength;

   /** Field shall identify the current peak irradiance of emissions in units of Watts per square meter, Section 7.3.4  */
   protected float  peakIrradiance;

   /** field shall identify the current pulse repetition frequency in units of cycles per second (Hertz), Section 7.3.4  */
   protected float  pulseRepetitionFrequency;

   /** field shall identify the pulse width emissions in units of seconds, Section 7.3.4 */
   protected int  pulseWidth;

   /** 16bit Boolean field shall contain various flags to indicate status information needed to process a DE, Section 7.3.4  */
   protected int  flags;

   /** Field shall identify the pulse shape and shall be represented as an 8-bit enumeration, Section 7.3.4  */
   protected byte  pulseShape;

   /** padding, Section 7.3.4  */
   protected short  padding1;

   /** padding, Section 7.3.4  */
   protected long  padding2;

   /** padding, Section 7.3.4  */
   protected int  padding3;

   /** Field shall specify the number of DE records, Section 7.3.4  */
   protected int  numberOfDERecords;

   /** Fields shall contain one or more DE records, records shall conform to the variable record format (Section6.2.82), Section 7.3.4 */
   protected List< StandardVariableSpecification > dERecords = new ArrayList< StandardVariableSpecification >(); 

/** Constructor */
 public DirectedEnergyFirePdu()
 {
    setPduType( (short)68 );
 }

public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = super.getMarshalledSize();
   marshalSize = marshalSize + munitionType.getMarshalledSize();  // munitionType
   marshalSize = marshalSize + shotStartTime.getMarshalledSize();  // shotStartTime
   marshalSize = marshalSize + 4;  // commulativeShotTime
   marshalSize = marshalSize + ApertureEmitterLocation.getMarshalledSize();  // ApertureEmitterLocation
   marshalSize = marshalSize + 4;  // apertureDiameter
   marshalSize = marshalSize + 4;  // wavelength
   marshalSize = marshalSize + 4;  // peakIrradiance
   marshalSize = marshalSize + 4;  // pulseRepetitionFrequency
   marshalSize = marshalSize + 4;  // pulseWidth
   marshalSize = marshalSize + 4;  // flags
   marshalSize = marshalSize + 1;  // pulseShape
   marshalSize = marshalSize + 1;  // padding1
   marshalSize = marshalSize + 4;  // padding2
   marshalSize = marshalSize + 2;  // padding3
   marshalSize = marshalSize + 2;  // numberOfDERecords
   for(int idx=0; idx < dERecords.size(); idx++)
   {
        StandardVariableSpecification listElement = dERecords.get(idx);
        marshalSize = marshalSize + listElement.getMarshalledSize();
   }

   return marshalSize;
}


public void setMunitionType(EntityType pMunitionType)
{ munitionType = pMunitionType;
}

public EntityType getMunitionType()
{ return munitionType; 
}

public void setShotStartTime(ClockTime pShotStartTime)
{ shotStartTime = pShotStartTime;
}

public ClockTime getShotStartTime()
{ return shotStartTime; 
}

public void setCommulativeShotTime(float pCommulativeShotTime)
{ commulativeShotTime = pCommulativeShotTime;
}

public float getCommulativeShotTime()
{ return commulativeShotTime; 
}

public void setApertureEmitterLocation(Vector3Float pApertureEmitterLocation)
{ ApertureEmitterLocation = pApertureEmitterLocation;
}

public Vector3Float getApertureEmitterLocation()
{ return ApertureEmitterLocation; 
}

public void setApertureDiameter(float pApertureDiameter)
{ apertureDiameter = pApertureDiameter;
}

public float getApertureDiameter()
{ return apertureDiameter; 
}

public void setWavelength(float pWavelength)
{ wavelength = pWavelength;
}

public float getWavelength()
{ return wavelength; 
}

public void setPeakIrradiance(float pPeakIrradiance)
{ peakIrradiance = pPeakIrradiance;
}

public float getPeakIrradiance()
{ return peakIrradiance; 
}

public void setPulseRepetitionFrequency(float pPulseRepetitionFrequency)
{ pulseRepetitionFrequency = pPulseRepetitionFrequency;
}

public float getPulseRepetitionFrequency()
{ return pulseRepetitionFrequency; 
}

public void setPulseWidth(int pPulseWidth)
{ pulseWidth = pPulseWidth;
}

public int getPulseWidth()
{ return pulseWidth; 
}

public void setFlags(int pFlags)
{ flags = pFlags;
}

public int getFlags()
{ return flags; 
}

public void setPulseShape(byte pPulseShape)
{ pulseShape = pPulseShape;
}

public byte getPulseShape()
{ return pulseShape; 
}

public void setPadding1(short pPadding1)
{ padding1 = pPadding1;
}

public short getPadding1()
{ return padding1; 
}

public void setPadding2(long pPadding2)
{ padding2 = pPadding2;
}

public long getPadding2()
{ return padding2; 
}

public void setPadding3(int pPadding3)
{ padding3 = pPadding3;
}

public int getPadding3()
{ return padding3; 
}

public int getNumberOfDERecords()
{ return (int)dERecords.size();
}

/** Note that setting this value will not change the marshalled value. The list whose length this describes is used for that purpose.
 * The getnumberOfDERecords method will also be based on the actual list length rather than this value. 
 * The method is simply here for java bean completeness.
 */
public void setNumberOfDERecords(int pNumberOfDERecords)
{ numberOfDERecords = pNumberOfDERecords;
}

public void setDERecords(List<StandardVariableSpecification> pDERecords)
{ dERecords = pDERecords;
}

public List<StandardVariableSpecification> getDERecords()
{ return dERecords; }


public void marshal(DataOutputStream dos)
{
    super.marshal(dos);
    try 
    {
       munitionType.marshal(dos);
       shotStartTime.marshal(dos);
       dos.writeFloat( (float)commulativeShotTime);
       ApertureEmitterLocation.marshal(dos);
       dos.writeFloat( (float)apertureDiameter);
       dos.writeFloat( (float)wavelength);
       dos.writeFloat( (float)peakIrradiance);
       dos.writeFloat( (float)pulseRepetitionFrequency);
       dos.writeInt( (int)pulseWidth);
       dos.writeInt( (int)flags);
       dos.writeByte( (byte)pulseShape);
       dos.writeByte( (byte)padding1);
       dos.writeInt( (int)padding2);
       dos.writeShort( (short)padding3);
       dos.writeShort( (short)dERecords.size());

       for(int idx = 0; idx < dERecords.size(); idx++)
       {
            StandardVariableSpecification aStandardVariableSpecification = dERecords.get(idx);
            aStandardVariableSpecification.marshal(dos);
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
       munitionType.unmarshal(dis);
       shotStartTime.unmarshal(dis);
       commulativeShotTime = dis.readFloat();
       ApertureEmitterLocation.unmarshal(dis);
       apertureDiameter = dis.readFloat();
       wavelength = dis.readFloat();
       peakIrradiance = dis.readFloat();
       pulseRepetitionFrequency = dis.readFloat();
       pulseWidth = dis.readInt();
       flags = dis.readInt();
       pulseShape = dis.readByte();
       padding1 = (short)dis.readUnsignedByte();
       padding2 = dis.readInt();
       padding3 = (int)dis.readUnsignedShort();
       numberOfDERecords = (int)dis.readUnsignedShort();
       for(int idx = 0; idx < numberOfDERecords; idx++)
       {
           StandardVariableSpecification anX = new StandardVariableSpecification();
           anX.unmarshal(dis);
           dERecords.add(anX);
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
       munitionType.marshal(buff);
       shotStartTime.marshal(buff);
       buff.putFloat( (float)commulativeShotTime);
       ApertureEmitterLocation.marshal(buff);
       buff.putFloat( (float)apertureDiameter);
       buff.putFloat( (float)wavelength);
       buff.putFloat( (float)peakIrradiance);
       buff.putFloat( (float)pulseRepetitionFrequency);
       buff.putInt( (int)pulseWidth);
       buff.putInt( (int)flags);
       buff.put( (byte)pulseShape);
       buff.put( (byte)padding1);
       buff.putInt( (int)padding2);
       buff.putShort( (short)padding3);
       buff.putShort( (short)dERecords.size());

       for(int idx = 0; idx < dERecords.size(); idx++)
       {
            StandardVariableSpecification aStandardVariableSpecification = (StandardVariableSpecification)dERecords.get(idx);
            aStandardVariableSpecification.marshal(buff);
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

       munitionType.unmarshal(buff);
       shotStartTime.unmarshal(buff);
       commulativeShotTime = buff.getFloat();
       ApertureEmitterLocation.unmarshal(buff);
       apertureDiameter = buff.getFloat();
       wavelength = buff.getFloat();
       peakIrradiance = buff.getFloat();
       pulseRepetitionFrequency = buff.getFloat();
       pulseWidth = buff.getInt();
       flags = buff.getInt();
       pulseShape = buff.get();
       padding1 = (short)(buff.get() & 0xFF);
       padding2 = buff.getInt();
       padding3 = (int)(buff.getShort() & 0xFFFF);
       numberOfDERecords = (int)(buff.getShort() & 0xFFFF);
       for(int idx = 0; idx < numberOfDERecords; idx++)
       {
            StandardVariableSpecification anX = new StandardVariableSpecification();
            anX.unmarshal(buff);
            dERecords.add(anX);
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

    if(!(obj instanceof DirectedEnergyFirePdu))
        return false;

     final DirectedEnergyFirePdu rhs = (DirectedEnergyFirePdu)obj;

     if( ! (munitionType.equals( rhs.munitionType) )) ivarsEqual = false;
     if( ! (shotStartTime.equals( rhs.shotStartTime) )) ivarsEqual = false;
     if( ! (commulativeShotTime == rhs.commulativeShotTime)) ivarsEqual = false;
     if( ! (ApertureEmitterLocation.equals( rhs.ApertureEmitterLocation) )) ivarsEqual = false;
     if( ! (apertureDiameter == rhs.apertureDiameter)) ivarsEqual = false;
     if( ! (wavelength == rhs.wavelength)) ivarsEqual = false;
     if( ! (peakIrradiance == rhs.peakIrradiance)) ivarsEqual = false;
     if( ! (pulseRepetitionFrequency == rhs.pulseRepetitionFrequency)) ivarsEqual = false;
     if( ! (pulseWidth == rhs.pulseWidth)) ivarsEqual = false;
     if( ! (flags == rhs.flags)) ivarsEqual = false;
     if( ! (pulseShape == rhs.pulseShape)) ivarsEqual = false;
     if( ! (padding1 == rhs.padding1)) ivarsEqual = false;
     if( ! (padding2 == rhs.padding2)) ivarsEqual = false;
     if( ! (padding3 == rhs.padding3)) ivarsEqual = false;
     if( ! (numberOfDERecords == rhs.numberOfDERecords)) ivarsEqual = false;

     for(int idx = 0; idx < dERecords.size(); idx++)
     {
        if( ! ( dERecords.get(idx).equals(rhs.dERecords.get(idx)))) ivarsEqual = false;
     }


    return ivarsEqual && super.equalsImpl(rhs);
 }
} // end of class
