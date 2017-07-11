package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for Electronics
 * The enumeration values are generated from the SISO DIS XML EBV document (R35), which was
 * obtained from http://discussions.sisostds.org/default.asp?action=10&fd=31<p>
 *
 * Note that this has two ways to look up an enumerated instance from a value: a fast
 * but brittle array lookup, and a slower and more garbage-intensive, but safer, method.
 * if you want to minimize memory use, get rid of one or the other.<p>
 *
 * Copyright 2008-2009. This work is licensed under the BSD license, available at
 * http://www.movesinstitute.org/licenses<p>
 *
 * @author DMcG, Jason Nelson
 */

public enum Electronics 
{

    ELECTRONIC_WARFARE_SYSTEMS(4500, "electronic warfare systems"),
    DETECTION_SYSTEMS(4600, "detection systems"),
    RADIO_FREQUENCY(4610, "radio frequency"),
    MICROWAVE(4620, "microwave"),
    INFRARED(4630, "infrared"),
    LASER(4640, "laser"),
    RANGE_FINDERS(4700, "range finders"),
    RANGE_ONLY_RADAR(4710, "range-only radar"),
    LASER_RANGE_FINDER(4720, "laser range finder"),
    ELECTRONIC_SYSTEMS(4800, "electronic systems"),
    RADIO_FREQUENCY_1(4810, "radio frequency"),
    MICROWAVE_2(4820, "microwave"),
    INFRARED_3(4830, "infrared"),
    LASER_4(4840, "laser"),
    RADIOS(5000, "radios"),
    COMMUNICATION_SYSTEMS(5010, "communication systems"),
    INTERCOMS(5100, "intercoms"),
    ENCODERS(5200, "encoders"),
    ENCRYPTION_DEVICES(5250, "encryption devices"),
    DECODERS(5300, "decoders"),
    DECRYPTION_DEVICES(5350, "decryption devices"),
    COMPUTERS(5500, "computers"),
    NAVIGATION_AND_CONTROL_SYSTEMS(6000, "navigation and control systems"),
    FIRE_CONTROL_SYSTEMS(6500, "fire control systems"),
    MISSING_DESCRIPTION(6501, "Missing Description");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as Electronics.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public Electronics lookup[] = new Electronics[6502];

static private HashMap<Integer, Electronics>enumerations = new HashMap<Integer, Electronics>();

/* initialize the array and hash table at class load time */
static 
{
    for(Electronics anEnum:Electronics.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
Electronics(int value, String description)
{
    this.value = value;
    this.description = description;
}

/** Returns the string description associated with the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the string Invalid enumeration: <val> is returned.
*/
static public String getDescriptionForValue(int aVal)
{
  String desc;

    Electronics val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public Electronics getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    Electronics val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration Electronics");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    Electronics val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         return false;
      return true;
}

/** Returns the enumerated value for this enumeration */
public int getValue()
{
  return value;
}


/** Returns a text descriptioni for this enumerated value. This is usually used as the basis for the enumeration name. */
public String getDescription()
{
  return description;
}

}
