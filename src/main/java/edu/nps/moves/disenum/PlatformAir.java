package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for PlatformAir
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

public enum PlatformAir 
{

    OTHER(0, "Other"),
    FIGHTER_AIR_DEFENSE(1, "Fighter/Air Defense"),
    ATTACK_STRIKE(2, "Attack/Strike"),
    BOMBER(3, "Bomber"),
    CARGO_TANKER(4, "Cargo/Tanker"),
    ASW_PATROL_OBSERVATION(5, "ASW/Patrol/Observation"),
    ELECTRONIC_WARFARE_EW(6, "Electronic Warfare (EW)"),
    RECONNAISSANCE(7, "Reconnaissance"),
    SURVEILLANCE_C2_AIRBORNE_EARLY_WARNING(8, "Surveillance/C2 (Airborne Early Warning)"),
    ATTACK_HELICOPTER(20, "Attack Helicopter"),
    UTILITY_HELICOPTER(21, "Utility Helicopter"),
    ANTISUBMARINE_WARFARE_PATROL_HELICOPTER(22, "Antisubmarine Warfare/Patrol Helicopter"),
    CARGO_HELICOPTER(23, "Cargo Helicopter"),
    OBSERVATION_HELICOPTER(24, "Observation Helicopter"),
    SPECIAL_OPERATIONS_HELICOPTER(25, "Special Operations Helicopter"),
    TRAINER(40, "Trainer"),
    UNMANNED(50, "Unmanned"),
    NON_COMBATANT_COMMERCIAL_AIRCRAFT(57, "Non-Combatant Commercial Aircraft");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as PlatformAir.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public PlatformAir lookup[] = new PlatformAir[58];

static private HashMap<Integer, PlatformAir>enumerations = new HashMap<Integer, PlatformAir>();

/* initialize the array and hash table at class load time */
static 
{
    for(PlatformAir anEnum:PlatformAir.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
PlatformAir(int value, String description)
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

    PlatformAir val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public PlatformAir getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    PlatformAir val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration PlatformAir");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    PlatformAir val;
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
