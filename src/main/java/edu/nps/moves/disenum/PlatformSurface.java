package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for PlatformSurface
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

public enum PlatformSurface 
{

    OTHER(0, "Other"),
    CARRIER(1, "Carrier"),
    COMMAND_SHIP_CRUISER(2, "Command Ship/Cruiser"),
    GUIDED_MISSILE_CRUISER(3, "Guided Missile Cruiser"),
    GUIDED_MISSILE_DESTROYER_DDG(4, "Guided Missile Destroyer (DDG)"),
    DESTROYER_DD(5, "Destroyer (DD)"),
    GUIDED_MISSILE_FRIGATE_FFG(6, "Guided Missile Frigate (FFG)"),
    LIGHT_PATROL_CRAFT(7, "Light/Patrol Craft"),
    MINE_COUNTERMEASURE_SHIP_CRAFT(8, "Mine Countermeasure Ship/Craft"),
    DOCK_LANDING_SHIP(9, "Dock Landing Ship"),
    TANK_LANDING_SHIP(10, "Tank Landing Ship"),
    LANDING_CRAFT(11, "Landing Craft"),
    LIGHT_CARRIER(12, "Light Carrier"),
    CRUISER_HELICOPTER_CARRIER(13, "Cruiser/Helicopter Carrier"),
    HYDROFOIL(14, "Hydrofoil"),
    AIR_CUSHION_SURFACE_EFFECT(15, "Air Cushion/Surface Effect"),
    AUXILIARY(16, "Auxiliary"),
    AUXILIARY_MERCHANT_MARINE(17, "Auxiliary, Merchant Marine"),
    UTILITY(18, "Utility"),
    FRIGATE_INCLUDING_CORVETTE(50, "Frigate (including Corvette)"),
    BATTLESHIP(51, "Battleship"),
    HEAVY_CRUISER(52, "Heavy Cruiser"),
    DESTROYER_TENDER(53, "Destroyer Tender"),
    AMPHIBIOUS_ASSAULT_SHIP(54, "Amphibious Assault Ship"),
    AMPHIBIOUS_CARGO_SHIP(55, "Amphibious Cargo Ship"),
    AMPHIBIOUS_TRANSPORT_DOCK(56, "Amphibious Transport Dock"),
    AMMUNITION_SHIP(57, "Ammunition Ship"),
    COMBAT_STORES_SHIP(58, "Combat Stores Ship"),
    SURVEILLANCE_TOWED_ARRAY_SONAR_SYSTEM_SURTASS(59, "Surveillance Towed Array Sonar System (SURTASS)"),
    FAST_COMBAT_SUPPORT_SHIP(60, "Fast Combat Support Ship"),
    NON_COMBATANT_SHIP(61, "Non-Combatant Ship"),
    COAST_GUARD_CUTTERS(62, "Coast Guard Cutters"),
    COAST_GUARD_BOATS(63, "Coast Guard Boats");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as PlatformSurface.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public PlatformSurface lookup[] = new PlatformSurface[64];

static private HashMap<Integer, PlatformSurface>enumerations = new HashMap<Integer, PlatformSurface>();

/* initialize the array and hash table at class load time */
static 
{
    for(PlatformSurface anEnum:PlatformSurface.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
PlatformSurface(int value, String description)
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

    PlatformSurface val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public PlatformSurface getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    PlatformSurface val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration PlatformSurface");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    PlatformSurface val;
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
