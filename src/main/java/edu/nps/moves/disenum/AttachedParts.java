package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for AttachedParts
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

public enum AttachedParts 
{

    NOTHING_EMPTY(0, "Nothing, Empty"),
    SEQUENTIAL_IDS_FOR_MODEL_SPECIFIC_STATIONS(1, "Sequential IDs for model-specific stations"),
    FUSELAGE_STATIONS(512, "Fuselage Stations"),
    LEFT_WING_STATIONS(640, "Left-wing Stations"),
    RIGHT_WING_STATIONS(768, "Right-wing Stations"),
    M16A42_RIFLE(896, "M16A42 rifle"),
    M249_SAW(897, "M249 SAW"),
    M60_MACHINE_GUN(898, "M60 Machine gun"),
    M203_GRENADE_LAUNCHER(899, "M203 Grenade Launcher"),
    M136_AT4(900, "M136 AT4"),
    M47_DRAGON(901, "M47 Dragon"),
    AAWS_M_JAVELIN(902, "AAWS-M Javelin"),
    M18A1_CLAYMORE_MINE(903, "M18A1 Claymore Mine"),
    MK19_GRENADE_LAUNCHER(904, "MK19 Grenade Launcher"),
    M2_MACHINE_GUN(905, "M2 Machine Gun"),
    OTHER_ATTACHED_PARTS(906, "Other attached parts");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as AttachedParts.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public AttachedParts lookup[] = new AttachedParts[907];

static private HashMap<Integer, AttachedParts>enumerations = new HashMap<Integer, AttachedParts>();

/* initialize the array and hash table at class load time */
static 
{
    for(AttachedParts anEnum:AttachedParts.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
AttachedParts(int value, String description)
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

    AttachedParts val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public AttachedParts getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    AttachedParts val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration AttachedParts");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    AttachedParts val;
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
