package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for ProtocolFamily
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

public enum ProtocolFamily 
{

    OTHER(0, "Other"),
    ENTITY_INFORMATION_INTERACTION(1, "Entity Information/Interaction"),
    WARFARE(2, "Warfare"),
    LOGISTICS(3, "Logistics"),
    RADIO_COMMUNICATION(4, "Radio Communication"),
    SIMULATION_MANAGEMENT(5, "Simulation Management"),
    DISTRIBUTED_EMISSION_REGENERATION(6, "Distributed Emission Regeneration"),
    ENTITY_MANAGEMENT(7, "Entity Management"),
    MINEFIELD(8, "Minefield"),
    SYNTHETIC_ENVIRONMENT(9, "Synthetic Environment"),
    SIMULATION_MANAGEMENT_WITH_RELIABILITY(10, "Simulation Management with Reliability"),
    LIVE_ENTITY(11, "Live Entity"),
    NON_REAL_TIME(12, "Non-Real Time"),
    EXPERIMENTAL_COMPUTER_GENERATED_FORCES(129, "Experimental - Computer Generated Forces");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as ProtocolFamily.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public ProtocolFamily lookup[] = new ProtocolFamily[130];

static private HashMap<Integer, ProtocolFamily>enumerations = new HashMap<Integer, ProtocolFamily>();

/* initialize the array and hash table at class load time */
static 
{
    for(ProtocolFamily anEnum:ProtocolFamily.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
ProtocolFamily(int value, String description)
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

    ProtocolFamily val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public ProtocolFamily getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    ProtocolFamily val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration ProtocolFamily");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    ProtocolFamily val;
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
