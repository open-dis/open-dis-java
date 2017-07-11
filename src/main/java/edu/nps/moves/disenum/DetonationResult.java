package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for DetonationResult
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

public enum DetonationResult 
{

    OTHER(0, "Other"),
    ENTITY_IMPACT(1, "Entity Impact"),
    ENTITY_PROXIMATE_DETONATION(2, "Entity Proximate Detonation"),
    GROUND_IMPACT(3, "Ground Impact"),
    GROUND_PROXIMATE_DETONATION(4, "Ground Proximate Detonation"),
    DETONATION(5, "Detonation"),
    NONE_OR_NO_DETONATION_DUD(6, "None or No Detonation (Dud)"),
    HE_HIT_SMALL(7, "HE hit, small"),
    HE_HIT_MEDIUM(8, "HE hit, medium"),
    HE_HIT_LARGE(9, "HE hit, large"),
    ARMOR_PIERCING_HIT(10, "Armor-piercing hit"),
    DIRT_BLAST_SMALL(11, "Dirt blast, small"),
    DIRT_BLAST_MEDIUM(12, "Dirt blast, medium"),
    DIRT_BLAST_LARGE(13, "Dirt blast, large"),
    WATER_BLAST_SMALL(14, "Water blast, small"),
    WATER_BLAST_MEDIUM(15, "Water blast, medium"),
    WATER_BLAST_LARGE(16, "Water blast, large"),
    AIR_HIT(17, "Air hit"),
    BUILDING_HIT_SMALL(18, "Building hit, small"),
    BUILDING_HIT_MEDIUM(19, "Building hit, medium"),
    BUILDING_HIT_LARGE(20, "Building hit, large"),
    MINE_CLEARING_LINE_CHARGE(21, "Mine-clearing line charge"),
    ENVIRONMENT_OBJECT_IMPACT(22, "Environment object impact"),
    ENVIRONMENT_OBJECT_PROXIMATE_DETONATION(23, "Environment object proximate detonation"),
    WATER_IMPACT(24, "Water Impact"),
    AIR_BURST(25, "Air Burst"),
    KILL_WITH_FRAGMENT_TYPE_1(26, "Kill with fragment type 1"),
    KILL_WITH_FRAGMENT_TYPE_2(27, "Kill with fragment type 2"),
    KILL_WITH_FRAGMENT_TYPE_3(28, "Kill with fragment type 3"),
    KILL_WITH_FRAGMENT_TYPE_1_AFTER_FLY_OUT_FAILURE(29, "Kill with fragment type 1 after fly-out failure"),
    KILL_WITH_FRAGMENT_TYPE_2_AFTER_FLY_OUT_FAILURE(30, "Kill with fragment type 2 after fly-out failure"),
    MISS_DUE_TO_FLY_OUT_FAILURE(31, "Miss due to fly-out failure"),
    MISS_DUE_TO_END_GAME_FAILURE(32, "Miss due to end-game failure"),
    MISS_DUE_TO_FLY_OUT_AND_END_GAME_FAILURE(33, "Miss due to fly-out and end-game failure");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as DetonationResult.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public DetonationResult lookup[] = new DetonationResult[34];

static private HashMap<Integer, DetonationResult>enumerations = new HashMap<Integer, DetonationResult>();

/* initialize the array and hash table at class load time */
static 
{
    for(DetonationResult anEnum:DetonationResult.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
DetonationResult(int value, String description)
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

    DetonationResult val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public DetonationResult getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    DetonationResult val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration DetonationResult");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    DetonationResult val;
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
