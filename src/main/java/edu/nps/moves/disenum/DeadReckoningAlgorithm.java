package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for DeadReckoningAlgorithm
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

public enum DeadReckoningAlgorithm 
{

    OTHER(0, "Other"),
    STATIC_ENTITY_DOES_NOT_MOVE(1, "Static (Entity does not move.)"),
    DRMF_P_W(2, "DRM(F, P, W)"),
    DRMR_P_W(3, "DRM(R, P, W)"),
    DRMR_V_W(4, "DRM(R, V, W)"),
    DRMF_V_W(5, "DRM(F, V, W)"),
    DRMF_P_B(6, "DRM(F, P, B)"),
    DRMR_P_B(7, "DRM(R, P, B)"),
    DRMR_V_B(8, "DRM(R, V, B)"),
    DRMF_V_B(9, "DRM(F, V, B)");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as DeadReckoningAlgorithm.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public DeadReckoningAlgorithm lookup[] = new DeadReckoningAlgorithm[10];

static private HashMap<Integer, DeadReckoningAlgorithm>enumerations = new HashMap<Integer, DeadReckoningAlgorithm>();

/* initialize the array and hash table at class load time */
static 
{
    for(DeadReckoningAlgorithm anEnum:DeadReckoningAlgorithm.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
DeadReckoningAlgorithm(int value, String description)
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

    DeadReckoningAlgorithm val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public DeadReckoningAlgorithm getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    DeadReckoningAlgorithm val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration DeadReckoningAlgorithm");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    DeadReckoningAlgorithm val;
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
