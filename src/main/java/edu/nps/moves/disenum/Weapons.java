package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for Weapons
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

public enum Weapons 
{

    GUN_ELEVATION_DRIVE(2000, "gun elevation drive"),
    GUN_STABILIZATION_SYSTEM(2010, "gun stabilization system"),
    GUNNERS_PRIMARY_SIGHT_GPS(2020, "gunner's primary sight (GPS)"),
    COMMANDERS_EXTENSION_TO_THE_GPS(2030, "commander's extension to the GPS"),
    LOADING_MECHANISM(2040, "loading mechanism"),
    GUNNERS_AUXILIARY_SIGHT(2050, "gunner's auxiliary sight"),
    GUNNERS_CONTROL_PANEL(2060, "gunner's control panel"),
    GUNNERS_CONTROL_ASSEMBLY_HANDLES(2070, "gunner's control assembly handle(s)"),
    COMMANDERS_CONTROL_HANDLES_ASSEMBLY(2090, "commander's control handles/assembly"),
    COMMANDERS_WEAPON_STATION(2100, "commander's weapon station"),
    COMMANDERS_INDEPENDENT_THERMAL_VIEWER_CITV(2110, "commander's independent thermal viewer (CITV)"),
    GENERAL_WEAPONS(2120, "general weapons"),
    MISSING_DESCRIPTION(2121, "Missing Description");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as Weapons.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public Weapons lookup[] = new Weapons[2122];

static private HashMap<Integer, Weapons>enumerations = new HashMap<Integer, Weapons>();

/* initialize the array and hash table at class load time */
static 
{
    for(Weapons anEnum:Weapons.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
Weapons(int value, String description)
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

    Weapons val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public Weapons getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    Weapons val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration Weapons");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    Weapons val;
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
