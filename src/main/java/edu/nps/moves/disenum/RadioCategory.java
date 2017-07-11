package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for RadioCategory
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

public enum RadioCategory 
{

    OTHER(0, "Other"),
    VOICE_TRANSMISSION_RECEPTION(1, "Voice Transmission/Reception"),
    DATA_LINK_TRANSMISSION_RECEPTION(2, "Data Link Transmission/Reception"),
    VOICE_AND_DATA_LINK_TRANSMISSION_RECEPTION(3, "Voice and Data Link Transmission/Reception"),
    INSTRUMENTED_LANDING_SYSTEM_ILS_GLIDESLOPE_TRANSMITTER(4, "Instrumented Landing System (ILS) Glideslope Transmitter"),
    INSTRUMENTED_LANDING_SYSTEM_ILS_LOCALIZER_TRANSMITTER(5, "Instrumented Landing System (ILS) Localizer Transmitter"),
    INSTRUMENTED_LANDING_SYSTEM_ILS_OUTER_MARKER_BEACON(6, "Instrumented Landing System (ILS) Outer Marker Beacon"),
    INSTRUMENTED_LANDING_SYSTEM_ILS_MIDDLE_MARKER_BEACON(7, "Instrumented Landing System (ILS) Middle Marker Beacon"),
    INSTRUMENTED_LANDING_SYSTEM_ILS_INNER_MARKER_BEACON(8, "Instrumented Landing System (ILS) Inner Marker Beacon"),
    INSTRUMENTED_LANDING_SYSTEM_ILS_RECEIVER_PLATFORM_RADIO(9, "Instrumented Landing System (ILS) Receiver (Platform Radio)"),
    TACTICAL_AIR_NAVIGATION_TACAN_TRANSMITTER_GROUND_FIXED_EQUIPMENT(10, "Tactical Air Navigation (TACAN) Transmitter (Ground Fixed Equipment)"),
    TACTICAL_AIR_NAVIGATION_TACAN_RECEIVER_MOVING_PLATFORM_EQUIPMENT(11, "Tactical Air Navigation (TACAN) Receiver (Moving Platform Equipment)"),
    TACTICAL_AIR_NAVIGATION_TACAN_TRANSMITTER_RECEIVER_MOVING_PLATFORM_EQUIPMENT(12, "Tactical Air Navigation (TACAN) Transmitter/Receiver (Moving Platform Equipment)"),
    VARIABLE_OMNI_RANGING_VOR_TRANSMITTER_GROUND_FIXED_EQUIPMENT(13, "Variable Omni-Ranging (VOR) Transmitter (Ground Fixed Equipment)"),
    VARIABLE_OMNI_RANGING_VOR_WITH_DISTANCE_MEASURING_EQUIPMENT_DME_TRANSMITTER_GROUND_FIXED_EQUIPMENT(14, "Variable Omni-Ranging (VOR) with Distance Measuring Equipment (DME) Transmitter (Ground Fixed Equipment)"),
    COMBINED_VOR_ILS_RECEIVER_MOVING_PLATFORM_EQUIPMENT(15, "Combined VOR/ILS Receiver (Moving Platform Equipment)"),
    COMBINED_VOR_TACAN_VORTAC_TRANSMITTER(16, "Combined VOR & TACAN (VORTAC) Transmitter"),
    NON_DIRECTIONAL_BEACON_NDB_TRANSMITTER(17, "Non-Directional Beacon (NDB) Transmitter"),
    NON_DIRECTIONAL_BEACON_NDB_RECEIVER(18, "Non-Directional Beacon (NDB) Receiver"),
    NON_DIRECTIONAL_BEACON_NDB_WITH_DISTANCE_MEASURING_EQUIPMENT_DME_TRANSMITTER(19, "Non-Directional Beacon (NDB) with Distance Measuring Equipment (DME) Transmitter"),
    DISTANCE_MEASURING_EQUIPMENT_DME(20, "Distance Measuring Equipment (DME)");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as RadioCategory.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public RadioCategory lookup[] = new RadioCategory[21];

static private HashMap<Integer, RadioCategory>enumerations = new HashMap<Integer, RadioCategory>();

/* initialize the array and hash table at class load time */
static 
{
    for(RadioCategory anEnum:RadioCategory.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
RadioCategory(int value, String description)
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

    RadioCategory val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public RadioCategory getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    RadioCategory val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration RadioCategory");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    RadioCategory val;
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
