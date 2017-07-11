package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for ForceID
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

public enum ForceID 
{

    OTHER(0, "Other"),
    FRIENDLY(1, "Friendly"),
    OPPOSING(2, "Opposing"),
    NEUTRAL(3, "Neutral"),
    FRIENDLY_2(4, "Friendly 2"),
    OPPOSING_2(5, "Opposing 2"),
    NEUTRAL_2(6, "Neutral 2"),
    FRIENDLY_3(7, "Friendly 3"),
    OPPOSING_3(8, "Opposing 3"),
    NEUTRAL_3(9, "Neutral 3"),
    FRIENDLY_4(10, "Friendly 4"),
    OPPOSING_4(11, "Opposing 4"),
    NEUTRAL_4(12, "Neutral 4"),
    FRIENDLY_5(13, "Friendly 5"),
    OPPOSING_5(14, "Opposing 5"),
    NEUTRAL_5(15, "Neutral 5"),
    FRIENDLY_6(16, "Friendly 6"),
    OPPOSING_6(17, "Opposing 6"),
    NEUTRAL_6(18, "Neutral 6"),
    FRIENDLY_7(19, "Friendly 7"),
    OPPOSING_7(20, "Opposing 7"),
    NEUTRAL_7(21, "Neutral 7"),
    FRIENDLY_8(22, "Friendly 8"),
    OPPOSING_8(23, "Opposing 8"),
    NEUTRAL_8(24, "Neutral 8"),
    FRIENDLY_9(25, "Friendly 9"),
    OPPOSING_9(26, "Opposing 9"),
    NEUTRAL_9(27, "Neutral 9"),
    FRIENDLY_10(28, "Friendly 10"),
    OPPOSING_10(29, "Opposing 10"),
    NEUTRAL_10(30, "Neutral 10");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as ForceID.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public ForceID lookup[] = new ForceID[31];

static private HashMap<Integer, ForceID>enumerations = new HashMap<Integer, ForceID>();

/* initialize the array and hash table at class load time */
static 
{
    for(ForceID anEnum:ForceID.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
ForceID(int value, String description)
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

    ForceID val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public ForceID getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    ForceID val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration ForceID");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    ForceID val;
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
