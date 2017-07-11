package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for FirstInfHighLevelUnit
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

public enum FirstInfHighLevelUnit 
{

    X_1_16INF(1, "1-16INF"),
    X_2_16INF(2, "2-16INF"),
    X_1_34AR(3, "1-34AR"),
    X_2_34AR(4, "2-34AR"),
    X_3_37AR(5, "3-37AR"),
    X_4_37AR(6, "4-37AR"),
    X_1_118INF(7, "1-118INF"),
    X_4_118INF(8, "4-118INF"),
    X_2_265AR(9, "2-265AR"),
    X_2_136IF(10, "2-136IF"),
    X_1_5F(20, "1-5F"),
    X_4_5F(21, "4-5F"),
    X_1_178F(22, "1-178F"),
    X_6F(23, "6F"),
    X_25F(24, "25F"),
    X_1E(30, "1E"),
    X_70E(31, "70E"),
    X_4_1AVN(32, "4-1AVN"),
    X_1_1AVN(33, "1-1AVN"),
    X_2_3ADA(34, "2-3ADA"),
    X_1_4CAV(35, "1-4CAV"),
    X_701MSB(40, "701MSB"),
    X_101FSB(41, "101FSB"),
    X_201FSB(42, "201FSB"),
    X_163FSB(43, "163FSB"),
    X_101MI(45, "101MI"),
    X_121S(46, "121S"),
    X_1MP(47, "1MP"),
    X_12CML(48, "12CML"),
    X_1INF(50, "1INF"),
    XBDE(51, "XBDE"),
    AVNBDE(55, "AVNBDE"),
    E(56, "E"),
    F(57, "F"),
    DSC(58, "DSC");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as FirstInfHighLevelUnit.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public FirstInfHighLevelUnit lookup[] = new FirstInfHighLevelUnit[59];

static private HashMap<Integer, FirstInfHighLevelUnit>enumerations = new HashMap<Integer, FirstInfHighLevelUnit>();

/* initialize the array and hash table at class load time */
static 
{
    for(FirstInfHighLevelUnit anEnum:FirstInfHighLevelUnit.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
FirstInfHighLevelUnit(int value, String description)
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

    FirstInfHighLevelUnit val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public FirstInfHighLevelUnit getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    FirstInfHighLevelUnit val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration FirstInfHighLevelUnit");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    FirstInfHighLevelUnit val;
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
