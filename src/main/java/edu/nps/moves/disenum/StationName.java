package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for StationName
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

public enum StationName 
{

    OTHER(0, "Other"),
    AIRCRAFT_WINGSTATION(1, "Aircraft wingstation"),
    SHIPS_FORWARD_GUNMOUNT_STARBOARD(2, "Ship's forward gunmount (starboard)"),
    SHIPS_FORWARD_GUNMOUNT_PORT(3, "Ship's forward gunmount (port)"),
    SHIPS_FORWARD_GUNMOUNT_CENTERLINE(4, "Ship's forward gunmount (centerline)"),
    SHIPS_AFT_GUNMOUNT_STARBOARD(5, "Ship's aft gunmount (starboard)"),
    SHIPS_AFT_GUNMOUNT_PORT(6, "Ship's aft gunmount (port)"),
    SHIPS_AFT_GUNMOUNT_CENTERLINE(7, "Ship's aft gunmount (centerline)"),
    FORWARD_TORPEDO_TUBE(8, "Forward torpedo tube"),
    AFT_TORPEDO_TUBE(9, "Aft torpedo tube"),
    BOMB_BAY(10, "Bomb bay"),
    CARGO_BAY(11, "Cargo bay"),
    TRUCK_BED(12, "Truck bed"),
    TRAILER_BED(13, "Trailer bed"),
    WELL_DECK(14, "Well deck"),
    ON_STATION_RNG_BRG(15, "On station - (RNG/BRG)"),
    ON_STATION_XYZ(16, "On station - (x,y,z)");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as StationName.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public StationName lookup[] = new StationName[17];

static private HashMap<Integer, StationName>enumerations = new HashMap<Integer, StationName>();

/* initialize the array and hash table at class load time */
static 
{
    for(StationName anEnum:StationName.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
StationName(int value, String description)
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

    StationName val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public StationName getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    StationName val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration StationName");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    StationName val;
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
