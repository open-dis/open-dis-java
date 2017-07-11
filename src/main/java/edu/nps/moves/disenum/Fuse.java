package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for Fuse
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

public enum Fuse 
{

    OTHER(0, "Other"),
    INTELLIGENT_INFLUENCE(10, "Intelligent Influence"),
    SENSOR(20, "Sensor"),
    SELF_DESTRUCT(30, "Self-destruct"),
    ULTRA_QUICK(40, "Ultra Quick"),
    BODY(50, "Body"),
    DEEP_INTRUSION(60, "Deep Intrusion"),
    MULTIFUNCTION(100, "Multifunction"),
    POINT_DETONATION_PD(200, "Point Detonation (PD)"),
    BASE_DETONATION_BD(300, "Base Detonation (BD)"),
    CONTACT(1000, "Contact"),
    CONTACT_INSTANT_IMPACT(1100, "Contact, Instant (Impact)"),
    CONTACT_DELAYED(1200, "Contact, Delayed"),
    X_10_MS_DELAY(1201, "10 ms delay"),
    X_20_MS_DELAY(1202, "20 ms delay"),
    X_50_MS_DELAY(1205, "50 ms delay"),
    X_60_MS_DELAY(1206, "60 ms delay"),
    X_100_MS_DELAY(1210, "100 ms delay"),
    X_125_MS_DELAY(1212, "125 ms delay"),
    X_250_MS_DELAY(1225, "250 ms delay"),
    CONTACT_ELECTRONIC_OBLIQUE_CONTACT(1300, "Contact, Electronic (Oblique Contact)"),
    CONTACT_GRAZE(1400, "Contact, Graze"),
    CONTACT_CRUSH(1500, "Contact, Crush"),
    CONTACT_HYDROSTATIC(1600, "Contact, Hydrostatic"),
    CONTACT_MECHANICAL(1700, "Contact, Mechanical"),
    CONTACT_CHEMICAL(1800, "Contact, Chemical"),
    CONTACT_PIEZOELECTRIC(1900, "Contact, Piezoelectric"),
    CONTACT_POINT_INITIATING(1910, "Contact, Point Initiating"),
    CONTACT_POINT_INITIATING_BASE_DETONATING(1920, "Contact, Point Initiating, Base Detonating"),
    CONTACT_BASE_DETONATING(1930, "Contact, Base Detonating"),
    CONTACT_BALLISTIC_CAP_AND_BASE(1940, "Contact, Ballistic Cap and Base"),
    CONTACT_BASE(1950, "Contact, Base"),
    CONTACT_NOSE(1960, "Contact, Nose"),
    CONTACT_FITTED_IN_STANDOFF_PROBE(1970, "Contact, Fitted in Standoff Probe"),
    CONTACT_NON_ALIGNED(1980, "Contact, Non-aligned"),
    TIMED(2000, "Timed"),
    TIMED_PROGRAMMABLE(2100, "Timed, Programmable"),
    TIMED_BURNOUT(2200, "Timed, Burnout"),
    TIMED_PYROTECHNIC(2300, "Timed, Pyrotechnic"),
    TIMED_ELECTRONIC(2400, "Timed, Electronic"),
    TIMED_BASE_DELAY(2500, "Timed, Base Delay"),
    TIMED_REINFORCED_NOSE_IMPACT_DELAY(2600, "Timed, Reinforced Nose Impact Delay"),
    TIMED_SHORT_DELAY_IMPACT(2700, "Timed, Short Delay Impact"),
    X_10_MS_DELAY_1(2701, "10 ms delay"),
    X_20_MS_DELAY_2(2702, "20 ms delay"),
    X_50_MS_DELAY_3(2705, "50 ms delay"),
    X_60_MS_DELAY_4(2706, "60 ms delay"),
    X_100_MS_DELAY_5(2710, "100 ms delay"),
    X_125_MS_DELAY_6(2712, "125 ms delay"),
    X_250_MS_DELAY_7(2725, "250 ms delay"),
    TIMED_NOSE_MOUNTED_VARIABLE_DELAY(2800, "Timed, Nose Mounted Variable Delay"),
    TIMED_LONG_DELAY_SIDE(2900, "Timed, Long Delay Side"),
    TIMED_SELECTABLE_DELAY(2910, "Timed, Selectable Delay"),
    TIMED_IMPACT(2920, "Timed, Impact"),
    TIMED_SEQUENCE(2930, "Timed, Sequence"),
    PROXIMITY(3000, "Proximity"),
    PROXIMITY_ACTIVE_LASER(3100, "Proximity, Active Laser"),
    PROXIMITY_MAGNETIC_MAGPOLARITY(3200, "Proximity, Magnetic (Magpolarity)"),
    PROXIMITY_ACTIVE_RADAR_DOPPLER_RADAR(3300, "Proximity, Active Radar (Doppler Radar)"),
    PROXIMITY_RADIO_FREQUENCY_RF(3400, "Proximity, Radio Frequency (RF)"),
    PROXIMITY_PROGRAMMABLE(3500, "Proximity, Programmable"),
    PROXIMITY_PROGRAMMABLE_PREFRAGMENTED(3600, "Proximity, Programmable, Prefragmented"),
    PROXIMITY_INFRARED(3700, "Proximity, Infrared"),
    COMMAND(4000, "Command"),
    COMMAND_ELECTRONIC_REMOTELY_SET(4100, "Command, Electronic, Remotely Set"),
    ALTITUDE(5000, "Altitude"),
    ALTITUDE_RADIO_ALTIMETER(5100, "Altitude, Radio Altimeter"),
    ALTITUDE_AIR_BURST(5200, "Altitude, Air Burst"),
    DEPTH(6000, "Depth"),
    ACOUSTIC(7000, "Acoustic"),
    PRESSURE(8000, "Pressure"),
    PRESSURE_DELAY(8010, "Pressure, Delay"),
    INERT(8100, "Inert"),
    DUMMY(8110, "Dummy"),
    PRACTICE(8120, "Practice"),
    PLUG_REPRESENTING(8130, "Plug Representing"),
    TRAINING(8150, "Training"),
    PYROTECHNIC(9000, "Pyrotechnic"),
    PYROTECHNIC_DELAY(9010, "Pyrotechnic, Delay"),
    ELECTRO_OPTICAL(9100, "Electro-optical"),
    ELECTROMECHANICAL(9110, "Electromechanical"),
    ELECTROMECHANICAL_NOSE(9120, "Electromechanical, Nose"),
    STRIKERLESS(9200, "Strikerless"),
    STRIKERLESS_NOSE_IMPACT(9210, "Strikerless, Nose Impact"),
    STRIKERLESS_COMPRESSION_IGNITION(9220, "Strikerless, Compression-Ignition"),
    COMPRESSION_IGNITION(9300, "Compression-Ignition"),
    COMPRESSION_IGNITION_STRIKERLESS_NOSE_IMPACT(9310, "Compression-Ignition, Strikerless, Nose Impact"),
    PERCUSSION(9400, "Percussion"),
    PERCUSSION_INSTANTANEOUS(9410, "Percussion, Instantaneous"),
    ELECTRONIC(9500, "Electronic"),
    ELECTRONIC_INTERNALLY_MOUNTED(9510, "Electronic, Internally Mounted"),
    ELECTRONIC_RANGE_SETTING(9520, "Electronic, Range Setting"),
    ELECTRONIC_PROGRAMMED(9530, "Electronic, Programmed"),
    MECHANICAL(9600, "Mechanical"),
    MECHANICAL_NOSE(9610, "Mechanical, Nose"),
    MECHANICAL_TAIL(9620, "Mechanical, Tail");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as Fuse.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public Fuse lookup[] = new Fuse[9621];

static private HashMap<Integer, Fuse>enumerations = new HashMap<Integer, Fuse>();

/* initialize the array and hash table at class load time */
static 
{
    for(Fuse anEnum:Fuse.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
Fuse(int value, String description)
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

    Fuse val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public Fuse getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    Fuse val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration Fuse");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    Fuse val;
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
