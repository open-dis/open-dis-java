package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for UserProtocolIDNum
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

public enum UserProtocolIDNum 
{

    CCSIL(1, "CCSIL"),
    A2ATD_SINCGARS_ERF(5, "A2ATD SINCGARS ERF"),
    A2ATD_CAC2(6, "A2ATD CAC2"),
    BATTLE_COMMAND(20, "Battle Command"),
    AFIWC_IADS_TRACK_REPORT(30, "AFIWC IADS Track Report"),
    AFIWC_IADS_COMM_C2_MESSAGE(31, "AFIWC IADS Comm C2 Message"),
    AFIWC_IADS_GROUND_CONTROL_INTERCEPTOR_GCI_COMMAND(32, "AFIWC IADS Ground Control Interceptor (GCI) Command"),
    AFIWC_VOICE_TEXT_MESSAGE(35, "AFIWC Voice Text Message"),
    MODSAF_TEXT_RADIO(177, "ModSAF Text Radio"),
    CCTT_SINCGARS_ERF_LOCKOUT(200, "CCTT SINCGARS ERF-LOCKOUT"),
    CCTT_SINCGARS_ERF_HOPSET(201, "CCTT SINCGARS ERF-HOPSET"),
    CCTT_SINCGARS_OTAR(202, "CCTT SINCGARS OTAR"),
    CCTT_SINCGARS_DATA(203, "CCTT SINCGARS DATA"),
    MODSAF_FWA_FORWARD_AIR_CONTROLLER(546, "ModSAF FWA Forward Air Controller"),
    MODSAF_THREAT_ADA_C3(832, "ModSAF Threat ADA C3"),
    F_16_MTC_AFAPD_PROTOCOL(1000, "F-16 MTC AFAPD Protocol"),
    F_16_MTC_IDL_PROTOCOL(1100, "F-16 MTC IDL Protocol"),
    MODSAF_ARTILLERY_FIRE_CONTROL(4570, "ModSAF Artillery Fire Control"),
    AGTS(5361, "AGTS"),
    GC3(6000, "GC3"),
    WNCP_DATA(6010, "WNCP data"),
    SPOKEN_TEXT_MESSAGE(6020, "Spoken text message"),
    LONGBOW_IDM_MESSAGE(6661, "Longbow IDM message"),
    COMANCHE_IDM_MESSAGE(6662, "Comanche IDM message"),
    LONGBOW_AIRBORNE_TACFIRE_MESSAGE(6663, "Longbow Airborne TACFIRE Message"),
    LONGBOW_GROUND_TACFIRE_MESSAGE(6664, "Longbow Ground TACFIRE Message"),
    LONGBOW_AFAPD_MESSAGE(6665, "Longbow AFAPD Message"),
    LONGBOW_ERF_MESSAGE(6666, "Longbow ERF message");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as UserProtocolIDNum.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public UserProtocolIDNum lookup[] = new UserProtocolIDNum[6667];

static private HashMap<Integer, UserProtocolIDNum>enumerations = new HashMap<Integer, UserProtocolIDNum>();

/* initialize the array and hash table at class load time */
static 
{
    for(UserProtocolIDNum anEnum:UserProtocolIDNum.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
UserProtocolIDNum(int value, String description)
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

    UserProtocolIDNum val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public UserProtocolIDNum getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    UserProtocolIDNum val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration UserProtocolIDNum");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    UserProtocolIDNum val;
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
