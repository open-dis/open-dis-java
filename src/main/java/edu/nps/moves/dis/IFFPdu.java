//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nps.moves.dis;

import edu.nps.moves.dis7.DistributedEmissionsFamilyPdu;
import edu.nps.moves.dis7.EntityID;
import edu.nps.moves.dis7.EventIdentifier;
import edu.nps.moves.dis7.FundamentalOperationalData;
import edu.nps.moves.dis7.SystemIdentifier;
import edu.nps.moves.dis7.Vector3Float;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;

public class IFFPdu extends DistributedEmissionsFamilyPdu implements Serializable {
  protected edu.nps.moves.dis7.EntityID emittingEntityID = new edu.nps.moves.dis7.EntityID();
  protected EventIdentifier eventID = new EventIdentifier();
  protected edu.nps.moves.dis7.Vector3Float location = new edu.nps.moves.dis7.Vector3Float();
  protected SystemIdentifier systemID = new SystemIdentifier();
  protected byte systemDesignator;
  protected byte systemSpecificData;
  protected FundamentalOperationalData fundamentalParameters = new FundamentalOperationalData();

  public IFFPdu() {
    this.setPduType((short)68);
  }

  public int getMarshalledSize()
  {
    int marshalSize = 0;

    marshalSize = super.getMarshalledSize();
    marshalSize = marshalSize + emittingEntityID.getMarshalledSize();  // emittingEntityID
    marshalSize = marshalSize + eventID.getMarshalledSize();  // eventID
    marshalSize = marshalSize + location.getMarshalledSize(); // location
    marshalSize = marshalSize + systemID.getMarshalledSize(); // systemID
    marshalSize = marshalSize + fundamentalParameters.getMarshalledSize(); // fundamentalParameters
    marshalSize = marshalSize + 1;  // systemDesignator
    marshalSize = marshalSize + 1;  // systemSpecificData

    return marshalSize;
  }
  public edu.nps.moves.dis7.EntityID getEmittingEntityID() {
    return emittingEntityID;
  }

  public void setEmittingEntityID(EntityID emittingEntityID) {
    this.emittingEntityID = emittingEntityID;
  }

  public EventIdentifier getEventID() {
    return eventID;
  }

  public void setEventID(EventIdentifier eventID) {
    this.eventID = eventID;
  }

  public edu.nps.moves.dis7.Vector3Float getLocation() {
    return location;
  }

  public void setLocation(Vector3Float location) {
    this.location = location;
  }

  public SystemIdentifier getSystemID() {
    return systemID;
  }

  public void setSystemID(SystemIdentifier systemID) {
    this.systemID = systemID;
  }

  public byte getSystemDesignator() {
    return systemDesignator;
  }

  public void setSystemDesignator(byte systemDesignator) {
    this.systemDesignator = systemDesignator;
  }

  public byte getSystemSpecificData() {
    return systemSpecificData;
  }

  public void setSystemSpecificData(byte systemSpecificData) {
    this.systemSpecificData = systemSpecificData;
  }

  public FundamentalOperationalData getFundamentalParameters() {
    return fundamentalParameters;
  }

  public void setFundamentalParameters(FundamentalOperationalData fundamentalParameters) {
    this.fundamentalParameters = fundamentalParameters;
  }

  public void marshal(DataOutputStream dos)
  {
    super.marshal(dos);
    try {
      emittingEntityID.marshal(dos);
      eventID.marshal(dos);
      location.marshal(dos);
      systemID.marshal(dos);
      fundamentalParameters.marshal(dos);
      dos.writeByte(systemDesignator);
      dos.writeByte(systemSpecificData);

    } // end try
    catch (Exception e) {
      System.out.println(e);
    }
  } // end of marshal method

  public void unmarshal(DataInputStream dis)
  {
    super.unmarshal(dis);

    try {
      emittingEntityID.unmarshal(dis);
      eventID.unmarshal(dis);
      location.unmarshal(dis);
      systemID.unmarshal(dis);
      fundamentalParameters.unmarshal(dis);
      systemDesignator = (byte) dis.readUnsignedByte();
      systemSpecificData = (byte) dis.readUnsignedByte();

    } // end try
    catch (Exception e) {
      System.out.println(e);
    }
  } // end of unmarshal method


  /**
   * Packs a Pdu into the ByteBuffer.
   * @param buff The ByteBuffer at the position to begin writing
   * @throws java.nio.BufferOverflowException if buff is too small
   * @throws java.nio.ReadOnlyBufferException if buff is read only
   * @see java.nio.ByteBuffer
   * @since ??
   */
  public void marshal(java.nio.ByteBuffer buff)
  {
    super.marshal(buff);
    emittingEntityID.marshal(buff);
    eventID.marshal(buff);
    location.marshal(buff);
    systemID.marshal(buff);
    fundamentalParameters.marshal(buff);
    buff.put(systemDesignator);
    buff.put(systemSpecificData);

  } // end of marshal method

  /**
   * Unpacks a Pdu from the underlying data.
   * @param buff The ByteBuffer at the position to begin reading
   * @throws java.nio.BufferUnderflowException if buff is too small
   * @see java.nio.ByteBuffer
   * @since ??
   */
  public void unmarshal(java.nio.ByteBuffer buff)
  {
    super.unmarshal(buff);

    emittingEntityID.unmarshal(buff);
    eventID.unmarshal(buff);
    location.unmarshal(buff);
    systemID.unmarshal(buff);
    fundamentalParameters.unmarshal(buff);
    systemDesignator = (byte)(buff.get() & 255);
    systemSpecificData = (byte)(buff.get() & 255);

  } // end of unmarshal method

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj == null) {
      return false;
    } else {
      return this.getClass().isAssignableFrom(obj.getClass()) && this.equalsImpl(obj);
    }
  }

  public boolean equalsImpl(Object obj) {
    boolean ivarsEqual = true;
    IFFPdu rhs = (IFFPdu)obj;
    if (!this.emittingEntityID.equals(rhs.emittingEntityID)) {
      ivarsEqual = false;
    }

    if (!this.eventID.equals(rhs.eventID)) {
      ivarsEqual = false;
    }

    if (!this.location.equals(rhs.location)) {
      ivarsEqual = false;
    }

    if (!this.systemID.equals(rhs.systemID)) {
      ivarsEqual = false;
    }

    if (this.systemDesignator != rhs.systemDesignator) {
      ivarsEqual = false;
    }

    if (this.systemSpecificData != rhs.systemSpecificData) {
      ivarsEqual = false;
    }

    if (!this.fundamentalParameters.equals(rhs.fundamentalParameters)) {
      ivarsEqual = false;
    }

    return ivarsEqual && super.equalsImpl(rhs);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getClass().getSimpleName()).append(":\n");
    sb.append(" emittingEntityId: ").append(this.emittingEntityID).append("\n");
    sb.append(" eventID: ").append(this.eventID).append("\n");
    sb.append(" location: ").append(this.location).append("\n");
    sb.append(" systemID: ").append(this.systemID).append("\n");
    sb.append(" systemDesignator: ").append(this.systemDesignator).append("\n");
    sb.append(" systemSpecificData: ").append(this.systemSpecificData).append("\n");
    sb.append(" fundamentalParameters: ").append(this.fundamentalParameters).append("\n");
    return sb.toString();
  }
}