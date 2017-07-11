package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for PlatformLand
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

public enum PlatformLand 
{

    OTHER(0, "Other"),
    TANK(1, "Tank"),
    ARMORED_FIGHTING_VEHICLE_IFV_APC_SP_MORTARS_ARMORED_CARS_CHEMICAL_RECONNAISSANCE_ANTI_TANK_GUIDED_MISSILE_LAUNCHERS_ETC(2, "Armored Fighting Vehicle - (IFV, APC, SP mortars, armored cars, chemical reconnaissance, anti-tank guided missile launchers, etc.)"),
    ARMORED_UTILITY_VEHICLE_ENGINEERING_VEHICLE_TRACKED_LOAD_CARRIERS_TOWING_VEHICLES_RECOVERY_VEHICLES_AVLB_ETC(3, "Armored Utility Vehicle - (Engineering vehicle, tracked load carriers, towing vehicles, recovery vehicles, AVLB, etc.)"),
    SELF_PROPELLED_ARTILLERY_GUNS_AND_HOWITZERS(4, "Self-propelled Artillery - (guns and howitzers)"),
    TOWED_ARTILLERY_ANTI_TANK_GUNS_GUNS_AND_HOWITZERS(5, "Towed Artillery - (anti-tank guns, guns and howitzers)"),
    SMALL_WHEELED_UTILITY_VEHICLE_0_125_TONS(6, "Small Wheeled Utility Vehicle - (0-1.25 tons)"),
    LARGE_WHEELED_UTILITY_VEHICLE_GREATER_THAN_125_TONS(7, "Large Wheeled Utility Vehicle - (greater than 1.25 tons)"),
    SMALL_TRACKED_UTILITY_VEHICLE_0_4999_KG_WEIGHT_LOAD(8, "Small Tracked Utility Vehicle - (0-4999 kg weight load)"),
    LARGE_TRACKED_UTILITY_VEHICLE_GREATER_THAN_4999_KG_WEIGHT_LOAD(9, "Large Tracked Utility Vehicle - (greater than 4999 kg weight load)"),
    MORTAR(10, "Mortar"),
    MINE_PLOW(11, "Mine plow"),
    MINE_RAKE(12, "Mine rake"),
    MINE_ROLLER(13, "Mine roller"),
    CARGO_TRAILER(14, "Cargo trailer"),
    FUEL_TRAILER(15, "Fuel trailer"),
    GENERATOR_TRAILER(16, "Generator trailer"),
    WATER_TRAILER(17, "Water trailer"),
    ENGINEER_EQUIPMENT(18, "Engineer equipment"),
    HEAVY_EQUIPMENT_TRANSPORT_TRAILER(19, "Heavy equipment transport trailer"),
    MAINTENANCE_EQUIPMENT_TRAILER(20, "Maintenance equipment trailer"),
    LIMBER(21, "Limber"),
    CHEMICAL_DECONTAMINATION_TRAILER(22, "Chemical decontamination trailer"),
    WARNING_SYSTEM(23, "Warning System"),
    TRAIN_ENGINE(24, "Train - Engine"),
    TRAIN_CAR(25, "Train - Car"),
    TRAIN_CABOOSE(26, "Train - Caboose"),
    CIVILIAN_VEHICLE(27, "Civilian Vehicle"),
    AIR_DEFENSE_MISSILE_DEFENSE_UNIT_EQUIPMENT(28, "Air Defense / Missile Defense Unit Equipment"),
    COMMAND_CONTROL_COMMUNICATIONS_AND_INTELLIGENCE_C3I_SYSTEM(29, "Command, Control, Communications, and Intelligence (C3I) System"),
    OPERATIONS_FACILITY(30, "Operations Facility"),
    INTELLIGENCE_FACILITY(31, "Intelligence Facility"),
    SURVEILLANCE_FACILITY(32, "Surveillance Facility"),
    COMMUNICATIONS_FACILITY(33, "Communications Facility"),
    COMMAND_FACILITY(34, "Command Facility"),
    C4I_FACILITY(35, "C4I Facility"),
    CONTROL_FACILITY(36, "Control Facility"),
    FIRE_CONTROL_FACILITY(37, "Fire Control Facility"),
    MISSILE_DEFENSE_FACILITY(38, "Missile Defense Facility"),
    FIELD_COMMAND_POST(39, "Field Command Post"),
    OBSERVATION_POST(40, "Observation Post");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as PlatformLand.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public PlatformLand lookup[] = new PlatformLand[41];

static private HashMap<Integer, PlatformLand>enumerations = new HashMap<Integer, PlatformLand>();

/* initialize the array and hash table at class load time */
static 
{
    for(PlatformLand anEnum:PlatformLand.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
PlatformLand(int value, String description)
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

    PlatformLand val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public PlatformLand getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    PlatformLand val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration PlatformLand");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    PlatformLand val;
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
