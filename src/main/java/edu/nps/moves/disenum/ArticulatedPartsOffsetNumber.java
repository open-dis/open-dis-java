package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for ArticulatedPartsOffsetNumber
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

public enum ArticulatedPartsOffsetNumber 
{

    POSITION(1, "Position"),
    POSITION_RATE(2, "Position Rate"),
    EXTENSION(3, "Extension"),
    EXTENSION_RATE(4, "Extension Rate"),
    X(5, "X"),
    X_RATE(6, "X Rate"),
    Y(7, "Y"),
    Y_RATE(8, "Y Rate"),
    Z(9, "Z"),
    Z_RATE(10, "Z Rate"),
    AZIMUTH(11, "Azimuth"),
    AZIMUTH_RATE(12, "Azimuth Rate"),
    ELEVATION(13, "Elevation"),
    ELEVATION_RATE(14, "Elevation Rate"),
    ROTATION(15, "Rotation"),
    ROTATION_RATE(16, "Rotation Rate");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as ArticulatedPartsOffsetNumber.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public ArticulatedPartsOffsetNumber lookup[] = new ArticulatedPartsOffsetNumber[17];

static private HashMap<Integer, ArticulatedPartsOffsetNumber>enumerations = new HashMap<Integer, ArticulatedPartsOffsetNumber>();

/* initialize the array and hash table at class load time */
static 
{
    for(ArticulatedPartsOffsetNumber anEnum:ArticulatedPartsOffsetNumber.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
ArticulatedPartsOffsetNumber(int value, String description)
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

    ArticulatedPartsOffsetNumber val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public ArticulatedPartsOffsetNumber getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    ArticulatedPartsOffsetNumber val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration ArticulatedPartsOffsetNumber");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    ArticulatedPartsOffsetNumber val;
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
