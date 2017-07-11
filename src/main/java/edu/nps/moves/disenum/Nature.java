package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for Nature
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

public enum Nature 
{

    OTHER(0, "Other"),
    HOST_FIREABLE_MUNITION(1, "Host-fireable munition"),
    MUNITION_CARRIED_AS_CARGO(2, "Munition carried as cargo"),
    FUEL_CARRIED_AS_CARGO(3, "Fuel carried as cargo"),
    GUNMOUNT_ATTACHED_TO_HOST(4, "Gunmount attached to host"),
    COMPUTER_GENERATED_FORCES_CARRIED_AS_CARGO(5, "Computer generated forces carried as cargo"),
    VEHICLE_CARRIED_AS_CARGO(6, "Vehicle carried as cargo"),
    EMITTER_MOUNTED_ON_HOST(7, "Emitter mounted on host"),
    MOBILE_COMMAND_AND_CONTROL_ENTITY_CARRIED_ABOARD_HOST(8, "Mobile command and control entity carried aboard host"),
    ENTITY_STATIONED_AT_POSITION_WITH_RESPECT_TO_HOST(9, "Entity stationed at position with respect to host"),
    TEAM_MEMBER_IN_FORMATION_WITH(10, "Team member in formation with");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as Nature.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public Nature lookup[] = new Nature[11];

static private HashMap<Integer, Nature>enumerations = new HashMap<Integer, Nature>();

/* initialize the array and hash table at class load time */
static 
{
    for(Nature anEnum:Nature.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
Nature(int value, String description)
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

    Nature val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public Nature getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    Nature val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration Nature");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    Nature val;
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
