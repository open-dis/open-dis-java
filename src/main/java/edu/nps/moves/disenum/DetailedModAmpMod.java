package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for DetailedModAmpMod
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

public enum DetailedModAmpMod 
{

    OTHER(0, "Other"),
    AFSK_AUDIO_FREQUENCY_SHIFT_KEYING(1, "AFSK (Audio Frequency Shift Keying)"),
    AM_AMPLITUDE_MODULATION(2, "AM (Amplitude Modulation)"),
    CW_CONTINUOUS_WAVE_MODULATION(3, "CW (Continuous Wave Modulation)"),
    DSB_DOUBLE_SIDEBAND(4, "DSB (Double Sideband)"),
    ISB_INDEPENDENT_SIDEBAND(5, "ISB (Independent Sideband)"),
    LSB_SINGLE_BAND_SUPPRESSED_CARRIER_LOWER_SIDEBAND_MODE(6, "LSB (Single Band Suppressed Carrier, Lower Sideband Mode)"),
    SSB_FULL_SINGLE_SIDEBAND_FULL_CARRIER(7, "SSB-Full (Single Sideband Full Carrier)"),
    SSB_REDUC_SINGLE_BAND_REDUCED_CARRIER(8, "SSB-Reduc (Single Band Reduced Carrier)"),
    USB_SINGLE_BAND_SUPPRESSED_CARRIER_UPPER_SIDEBAND_MODE(9, "USB (Single Band Suppressed Carrier, Upper Sideband Mode)"),
    VSB_VESTIGIAL_SIDEBAND(10, "VSB (Vestigial Sideband)");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as DetailedModAmpMod.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public DetailedModAmpMod lookup[] = new DetailedModAmpMod[11];

static private HashMap<Integer, DetailedModAmpMod>enumerations = new HashMap<Integer, DetailedModAmpMod>();

/* initialize the array and hash table at class load time */
static 
{
    for(DetailedModAmpMod anEnum:DetailedModAmpMod.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
DetailedModAmpMod(int value, String description)
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

    DetailedModAmpMod val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public DetailedModAmpMod getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    DetailedModAmpMod val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration DetailedModAmpMod");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    DetailedModAmpMod val;
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
