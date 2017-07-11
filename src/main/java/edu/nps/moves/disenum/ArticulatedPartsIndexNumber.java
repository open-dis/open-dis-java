package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for ArticulatedPartsIndexNumber
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

public enum ArticulatedPartsIndexNumber 
{

    RUDDER(1024, "rudder"),
    LEFT_FLAP(1056, "left flap"),
    RIGHT_FLAP(1088, "right flap"),
    LEFT_AILERON(1120, "left aileron"),
    RIGHT_AILERON(1152, "right aileron"),
    HELICOPTER_MAIN_ROTOR(1184, "helicopter - main rotor"),
    HELICOPTER_TAIL_ROTOR(1216, "helicopter - tail rotor"),
    OTHER_AIRCRAFT_CONTROL_SURFACES_DEFINED_AS_NEEDED(1248, "other Aircraft Control Surfaces defined as needed"),
    PERISCOPE(2048, "periscope"),
    GENERIC_ANTENNA(2080, "generic antenna"),
    SNORKEL(2112, "snorkel"),
    OTHER_EXTENDIBLE_PARTS_DEFINED_AS_NEEDED(2144, "other extendible parts defined as needed"),
    LANDING_GEAR(3072, "landing gear"),
    TAIL_HOOK(3104, "tail hook"),
    SPEED_BRAKE(3136, "speed brake"),
    LEFT_WEAPON_BAY_DOOR(3168, "left weapon bay door"),
    RIGHT_WEAPON_BAY_DOORS(3200, "right weapon bay doors"),
    TANK_OR_APC_HATCH(3232, "tank or APC hatch"),
    WINGSWEEP(3264, "wingsweep"),
    BRIDGE_LAUNCHER(3296, "Bridge launcher"),
    BRIDGE_SECTION_1(3328, "Bridge section 1"),
    BRIDGE_SECTION_2(3360, "Bridge section 2"),
    BRIDGE_SECTION_3(3392, "Bridge section 3"),
    PRIMARY_BLADE_1(3424, "Primary blade 1"),
    PRIMARY_BLADE_2(3456, "Primary blade 2"),
    PRIMARY_BOOM(3488, "Primary boom"),
    PRIMARY_LAUNCHER_ARM(3520, "Primary launcher arm"),
    OTHER_FIXED_POSITION_PARTS_DEFINED_AS_NEEDED(3552, "other fixed position parts defined as needed"),
    PRIMARY_TURRET_NUMBER_1(4096, "Primary turret number 1"),
    PRIMARY_TURRET_NUMBER_2(4128, "Primary turret number 2"),
    PRIMARY_TURRET_NUMBER_3(4160, "Primary turret number 3"),
    PRIMARY_TURRET_NUMBER_4(4192, "Primary turret number 4"),
    PRIMARY_TURRET_NUMBER_5(4224, "Primary turret number 5"),
    PRIMARY_TURRET_NUMBER_6(4256, "Primary turret number 6"),
    PRIMARY_TURRET_NUMBER_7(4288, "Primary turret number 7"),
    PRIMARY_TURRET_NUMBER_8(4320, "Primary turret number 8"),
    PRIMARY_TURRET_NUMBER_9(4352, "Primary turret number 9"),
    PRIMARY_TURRET_NUMBER_10(4384, "Primary turret number 10"),
    PRIMARY_GUN_NUMBER_1(4416, "Primary gun number 1"),
    PRIMARY_GUN_NUMBER_2(4448, "Primary gun number 2"),
    PRIMARY_GUN_NUMBER_3(4480, "Primary gun number 3"),
    PRIMARY_GUN_NUMBER_4(4512, "Primary gun number 4"),
    PRIMARY_GUN_NUMBER_5(4544, "Primary gun number 5"),
    PRIMARY_GUN_NUMBER_6(4576, "Primary gun number 6"),
    PRIMARY_GUN_NUMBER_7(4608, "Primary gun number 7"),
    PRIMARY_GUN_NUMBER_8(4640, "Primary gun number 8"),
    PRIMARY_GUN_NUMBER_9(4672, "Primary gun number 9"),
    PRIMARY_GUN_NUMBER_10(4704, "Primary gun number 10"),
    PRIMARY_LAUNCHER_1(4736, "Primary launcher 1"),
    PRIMARY_LAUNCHER_2(4768, "Primary launcher 2"),
    PRIMARY_LAUNCHER_3(4800, "Primary launcher 3"),
    PRIMARY_LAUNCHER_4(4832, "Primary launcher 4"),
    PRIMARY_LAUNCHER_5(4864, "Primary launcher 5"),
    PRIMARY_LAUNCHER_6(4896, "Primary launcher 6"),
    PRIMARY_LAUNCHER_7(4928, "Primary launcher 7"),
    PRIMARY_LAUNCHER_8(4960, "Primary launcher 8"),
    PRIMARY_LAUNCHER_9(4992, "Primary launcher 9"),
    PRIMARY_LAUNCHER_10(5024, "Primary launcher 10"),
    PRIMARY_DEFENSE_SYSTEMS_1(5056, "Primary defense systems 1"),
    PRIMARY_DEFENSE_SYSTEMS_2(5088, "Primary defense systems 2"),
    PRIMARY_DEFENSE_SYSTEMS_3(5120, "Primary defense systems 3"),
    PRIMARY_DEFENSE_SYSTEMS_4(5152, "Primary defense systems 4"),
    PRIMARY_DEFENSE_SYSTEMS_5(5184, "Primary defense systems 5"),
    PRIMARY_DEFENSE_SYSTEMS_6(5216, "Primary defense systems 6"),
    PRIMARY_DEFENSE_SYSTEMS_7(5248, "Primary defense systems 7"),
    PRIMARY_DEFENSE_SYSTEMS_8(5280, "Primary defense systems 8"),
    PRIMARY_DEFENSE_SYSTEMS_9(5312, "Primary defense systems 9"),
    PRIMARY_DEFENSE_SYSTEMS_10(5344, "Primary defense systems 10"),
    PRIMARY_RADAR_1(5376, "Primary radar 1"),
    PRIMARY_RADAR_2(5408, "Primary radar 2"),
    PRIMARY_RADAR_3(5440, "Primary radar 3"),
    PRIMARY_RADAR_4(5472, "Primary radar 4"),
    PRIMARY_RADAR_5(5504, "Primary radar 5"),
    PRIMARY_RADAR_6(5536, "Primary radar 6"),
    PRIMARY_RADAR_7(5568, "Primary radar 7"),
    PRIMARY_RADAR_8(5600, "Primary radar 8"),
    PRIMARY_RADAR_9(5632, "Primary radar 9"),
    PRIMARY_RADAR_10(5664, "Primary radar 10"),
    SECONDARY_TURRET_NUMBER_1(5696, "Secondary turret number 1"),
    SECONDARY_TURRET_NUMBER_2(5728, "Secondary turret number 2"),
    SECONDARY_TURRET_NUMBER_3(5760, "Secondary turret number 3"),
    SECONDARY_TURRET_NUMBER_4(5792, "Secondary turret number 4"),
    SECONDARY_TURRET_NUMBER_5(5824, "Secondary turret number 5"),
    SECONDARY_TURRET_NUMBER_6(5856, "Secondary turret number 6"),
    SECONDARY_TURRET_NUMBER_7(5888, "Secondary turret number 7"),
    SECONDARY_TURRET_NUMBER_8(5920, "Secondary turret number 8"),
    SECONDARY_TURRET_NUMBER_9(5952, "Secondary turret number 9"),
    SECONDARY_TURRET_NUMBER_10(5984, "Secondary turret number 10"),
    SECONDARY_GUN_NUMBER_1(6016, "Secondary gun number 1"),
    SECONDARY_GUN_NUMBER_2(6048, "Secondary gun number 2"),
    SECONDARY_GUN_NUMBER_3(6080, "Secondary gun number 3"),
    SECONDARY_GUN_NUMBER_4(6112, "Secondary gun number 4"),
    SECONDARY_GUN_NUMBER_5(6144, "Secondary gun number 5"),
    SECONDARY_GUN_NUMBER_6(6176, "Secondary gun number 6"),
    SECONDARY_GUN_NUMBER_7(6208, "Secondary gun number 7"),
    SECONDARY_GUN_NUMBER_8(6240, "Secondary gun number 8"),
    SECONDARY_GUN_NUMBER_9(6272, "Secondary gun number 9"),
    SECONDARY_GUN_NUMBER_10(6304, "Secondary gun number 10"),
    SECONDARY_LAUNCHER_1(6336, "Secondary launcher 1"),
    SECONDARY_LAUNCHER_2(6368, "Secondary launcher 2"),
    SECONDARY_LAUNCHER_3(6400, "Secondary launcher 3"),
    SECONDARY_LAUNCHER_4(6432, "Secondary launcher 4"),
    SECONDARY_LAUNCHER_5(6464, "Secondary launcher 5"),
    SECONDARY_LAUNCHER_6(6496, "Secondary launcher 6"),
    SECONDARY_LAUNCHER_7(6528, "Secondary launcher 7"),
    SECONDARY_LAUNCHER_8(6560, "Secondary launcher 8"),
    SECONDARY_LAUNCHER_9(6592, "Secondary launcher 9"),
    SECONDARY_LAUNCHER_10(6624, "Secondary launcher 10"),
    SECONDARY_DEFENSE_SYSTEMS_1(6656, "Secondary defense systems 1"),
    SECONDARY_DEFENSE_SYSTEMS_2(6688, "Secondary defense systems 2"),
    SECONDARY_DEFENSE_SYSTEMS_3(6720, "Secondary defense systems 3"),
    SECONDARY_DEFENSE_SYSTEMS_4(6752, "Secondary defense systems 4"),
    SECONDARY_DEFENSE_SYSTEMS_5(6784, "Secondary defense systems 5"),
    SECONDARY_DEFENSE_SYSTEMS_6(6816, "Secondary defense systems 6"),
    SECONDARY_DEFENSE_SYSTEMS_7(6848, "Secondary defense systems 7"),
    SECONDARY_DEFENSE_SYSTEMS_8(6880, "Secondary defense systems 8"),
    SECONDARY_DEFENSE_SYSTEMS_9(6912, "Secondary defense systems 9"),
    SECONDARY_DEFENSE_SYSTEMS_10(6944, "Secondary defense systems 10"),
    SECONDARY_RADAR_1(6976, "Secondary radar 1"),
    SECONDARY_RADAR_2(7008, "Secondary radar 2"),
    SECONDARY_RADAR_3(7040, "Secondary radar 3"),
    SECONDARY_RADAR_4(7072, "Secondary radar 4"),
    SECONDARY_RADAR_5(7104, "Secondary radar 5"),
    SECONDARY_RADAR_6(7136, "Secondary radar 6"),
    SECONDARY_RADAR_7(7168, "Secondary radar 7"),
    SECONDARY_RADAR_8(7200, "Secondary radar 8"),
    SECONDARY_RADAR_9(7232, "Secondary radar 9"),
    SECONDARY_RADAR_10(7264, "Secondary radar 10"),
    DECK_ELEVATOR_1(7296, "Deck Elevator #1"),
    DECK_ELEVATOR_2(7328, "Deck Elevator #2"),
    CATAPULT_1(7360, "Catapult #1"),
    CATAPULT_2(7392, "Catapult #2"),
    JET_BLAST_DEFLECTOR_1(7424, "Jet Blast Deflector #1"),
    JET_BLAST_DEFLECTOR_2(7456, "Jet Blast Deflector #2"),
    ARRESTOR_WIRES_1(7488, "Arrestor Wires #1"),
    ARRESTOR_WIRES_2(7520, "Arrestor Wires #2"),
    ARRESTOR_WIRES_3(7552, "Arrestor Wires #3"),
    WING_OR_ROTOR_FOLD(7584, "Wing (or rotor) fold"),
    FUSELAGE_FOLD(7616, "Fuselage fold");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as ArticulatedPartsIndexNumber.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public ArticulatedPartsIndexNumber lookup[] = new ArticulatedPartsIndexNumber[7617];

static private HashMap<Integer, ArticulatedPartsIndexNumber>enumerations = new HashMap<Integer, ArticulatedPartsIndexNumber>();

/* initialize the array and hash table at class load time */
static 
{
    for(ArticulatedPartsIndexNumber anEnum:ArticulatedPartsIndexNumber.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
ArticulatedPartsIndexNumber(int value, String description)
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

    ArticulatedPartsIndexNumber val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public ArticulatedPartsIndexNumber getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    ArticulatedPartsIndexNumber val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration ArticulatedPartsIndexNumber");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    ArticulatedPartsIndexNumber val;
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
