package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for GeometryRecordTypeField
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

public enum GeometryRecordTypeField 
{

    POINT_RECORD_1(655360, "Point Record 1"),
    POINT_RECORD_2(167772160, "Point Record 2"),
    LINE_RECORD_1(786432, "Line Record 1"),
    LINE_RECORD_2(201326592, "Line Record 2"),
    BOUNDING_SPHERE_RECORD(65536, "Bounding Sphere Record"),
    SPHERE_RECORD_1(851968, "Sphere Record 1"),
    SPHERE_RECORD_2(218103808, "Sphere Record 2"),
    ELLIPSOID_RECORD_1(1048576, "Ellipsoid Record 1"),
    ELLIPSOID_RECORD_2(268435456, "Ellipsoid Record 2"),
    CONE_RECORD_1(3145728, "Cone Record 1"),
    CONE_RECORD_2(805306368, "Cone Record 2"),
    UNIFORM_GEOMETRY_RECORD(327680, "Uniform Geometry Record"),
    RECTANGULAR_VOLUME_RECORD_1(5242880, "Rectangular Volume Record 1"),
    RECTANGULAR_VOLUME_RECORD_2(1342177280, "Rectangular Volume Record 2"),
    GAUSSIAN_PLUME_RECORD(1610612736, "Gaussian Plume Record"),
    GAUSSIAN_PUFF_RECORD(1879048192, "Gaussian Puff Record"),
    RECTANGULAR_VOLUME_RECORD_3(83886080, "Rectangular Volume Record 3");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as GeometryRecordTypeField.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public GeometryRecordTypeField lookup[] = new GeometryRecordTypeField[1879048193];

static private HashMap<Integer, GeometryRecordTypeField>enumerations = new HashMap<Integer, GeometryRecordTypeField>();

/* initialize the array and hash table at class load time */
static 
{
    for(GeometryRecordTypeField anEnum:GeometryRecordTypeField.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
GeometryRecordTypeField(int value, String description)
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

    GeometryRecordTypeField val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public GeometryRecordTypeField getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    GeometryRecordTypeField val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration GeometryRecordTypeField");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    GeometryRecordTypeField val;
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
