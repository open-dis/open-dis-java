package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for SensorEmitterCategory
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

public enum SensorEmitterCategory 
{

    OTHER(0, "Other"),
    MULTI_SPECTRAL(1, "Multi-spectral"),
    RF_ACTIVE(2, "RF Active"),
    RF_PASSIVE_INTERCEPT_AND_DF(3, "RF Passive (intercept and DF)"),
    OPTICAL_DIRECT_VIEWING_WITH_OR_WITHOUT_OPTICS(4, "Optical (direct viewing with or without optics)"),
    ELECTRO_OPTICAL(5, "Electro-Optical"),
    SEISMIC(6, "Seismic"),
    CHEMICAL_POINT_DETECTOR(7, "Chemical, point detector"),
    CHEMICAL_STANDOFF(8, "Chemical, standoff"),
    THERMAL_TEMPERATURE_SENSING(9, "Thermal (temperature sensing)"),
    ACOUSTIC_ACTIVE(10, "Acoustic, Active"),
    ACOUSTIC_PASSIVE(11, "Acoustic, Passive"),
    CONTACT_PRESSURE_PHYSICAL_HYDROSTATIC_BAROMETRIC(12, "Contact/Pressure (physical, hydrostatic, barometric)"),
    ELECTRO_MAGNETIC_RADIATION_GAMMA_RADIATION(13, "Electro-Magnetic Radiation (gamma radiation)"),
    PARTICLE_RADIATION_NEUTRONS_ALPHA_BETA_PARTICLES(14, "Particle Radiation (Neutrons, alpha, beta particles)"),
    MAGNETIC(15, "Magnetic"),
    GRAVITATIONAL(16, "Gravitational");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as SensorEmitterCategory.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public SensorEmitterCategory lookup[] = new SensorEmitterCategory[17];

static private HashMap<Integer, SensorEmitterCategory>enumerations = new HashMap<Integer, SensorEmitterCategory>();

/* initialize the array and hash table at class load time */
static 
{
    for(SensorEmitterCategory anEnum:SensorEmitterCategory.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
SensorEmitterCategory(int value, String description)
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

    SensorEmitterCategory val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public SensorEmitterCategory getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    SensorEmitterCategory val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration SensorEmitterCategory");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    SensorEmitterCategory val;
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
