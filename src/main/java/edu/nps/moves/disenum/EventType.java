package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for EventType
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

public enum EventType 
{

    OTHER(0, "Other"),
    MISSING_DESCRIPTION(1, "Missing Description"),
    RAN_OUT_OF_AMMUNITION(2, "Ran out of ammunition"),
    KILLED_IN_ACTION(3, "Killed in action"),
    DAMAGE(4, "Damage"),
    MOBILITY_DISABLED(5, "Mobility disabled"),
    FIRE_DISABLED(6, "Fire disabled"),
    RAN_OUT_OF_FUEL(7, "Ran out of fuel"),
    ENTITY_INITIALIZATION(8, "Entity initialization"),
    REQUEST_FOR_INDIRECT_FIRE_OR_CAS_MISSION(9, "Request for indirect fire or CAS mission"),
    INDIRECT_FIRE_OR_CAS_FIRE(10, "Indirect fire or CAS fire"),
    MINEFIELD_ENTRY(11, "Minefield entry"),
    MINEFIELD_DETONATION(12, "Minefield detonation"),
    VEHICLE_MASTER_POWER_ON(13, "Vehicle master power on"),
    VEHICLE_MASTER_POWER_OFF(14, "Vehicle master power off"),
    AGGREGATE_STATE_CHANGE_REQUESTED(15, "Aggregate state change requested"),
    PREVENT_COLLISION_DETONATION(16, "Prevent Collision / Detonation");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as EventType.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public EventType lookup[] = new EventType[17];

static private HashMap<Integer, EventType>enumerations = new HashMap<Integer, EventType>();

/* initialize the array and hash table at class load time */
static 
{
    for(EventType anEnum:EventType.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
EventType(int value, String description)
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

    EventType val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public EventType getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    EventType val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration EventType");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    EventType val;
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
