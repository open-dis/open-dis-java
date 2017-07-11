package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for TDLType
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

public enum TDLType 
{

    OTHER(0, "Other"),
    PADIL(1, "PADIL"),
    NATO_LINK_1(2, "NATO Link-1"),
    ATDL_1(3, "ATDL-1"),
    LINK_11B_TADIL_B(4, "Link 11B (TADIL B)"),
    SITUATIONAL_AWARENESS_DATA_LINK_SADL(5, "Situational Awareness Data Link (SADL)"),
    LINK_16_LEGACY_FORMAT_JTIDS_TADIL_J(6, "Link 16 Legacy Format (JTIDS/TADIL-J)"),
    LINK_16_LEGACY_FORMAT_JTIDS_FDL_TADIL_J(7, "Link 16 Legacy Format (JTIDS/FDL/TADIL-J)"),
    LINK_11A_TADIL_A(8, "Link 11A (TADIL A)"),
    IJMS(9, "IJMS"),
    LINK_4A_TADIL_C(10, "Link 4A (TADIL C)"),
    LINK_4C(11, "Link 4C"),
    TIBS(12, "TIBS"),
    ATL(13, "ATL"),
    CONSTANT_SOURCE(14, "Constant Source"),
    ABBREVIATED_COMMAND_AND_CONTROL(15, "Abbreviated Command and Control"),
    MILSTAR(16, "MILSTAR"),
    ATHS(17, "ATHS"),
    OTHGOLD(18, "OTHGOLD"),
    TACELINT(19, "TACELINT"),
    WEAPONS_DATA_LINK_AWW_13(20, "Weapons Data Link (AWW-13)"),
    ABBREVIATED_COMMAND_AND_CONTROL_1(21, "Abbreviated Command and Control"),
    ENHANCED_POSITION_LOCATION_REPORTING_SYSTEM_EPLRS(22, "Enhanced Position Location Reporting System (EPLRS)"),
    POSITION_LOCATION_REPORTING_SYSTEM_PLRS(23, "Position Location Reporting System (PLRS)"),
    SINCGARS(24, "SINCGARS"),
    HAVE_QUICK_I(25, "Have Quick I"),
    HAVE_QUICK_II(26, "Have Quick II"),
    HAVE_QUICK_IIA_SATURN(27, "Have Quick IIA (Saturn)"),
    INTRA_FLIGHT_DATA_LINK_1(28, "Intra-Flight Data Link 1"),
    INTRA_FLIGHT_DATA_LINK_2(29, "Intra-Flight Data Link 2"),
    IMPROVED_DATA_MODEM_IDM(30, "Improved Data Modem (IDM)"),
    AIR_FORCE_APPLICATION_PROGRAM_DEVELOPMENT_AFAPD(31, "Air Force Application Program Development (AFAPD)"),
    COOPERATIVE_ENGAGEMENT_CAPABILITY_CEC(32, "Cooperative Engagement Capability (CEC)"),
    FORWARD_AREA_AIR_DEFENSE_FAAD_DATA_LINK_FDL(33, "Forward Area Air Defense (FAAD) Data Link (FDL)"),
    GROUND_BASED_DATA_LINK_GBDL(34, "Ground Based Data Link (GBDL)"),
    INTRA_VEHICULAR_INFO_SYSTEM_IVIS(35, "Intra Vehicular Info System (IVIS)"),
    MARINE_TACTICAL_SYSTEM_MTS(36, "Marine Tactical System (MTS)"),
    TACTICAL_FIRE_DIRECTION_SYSTEM_TACFIRE(37, "Tactical Fire Direction System (TACFIRE)"),
    INTEGRATED_BROADCAST_SERVICE_IBS(38, "Integrated Broadcast Service (IBS)"),
    AIRBORNE_INFORMATION_TRANSFER_ABIT(39, "Airborne Information Transfer (ABIT)"),
    ADVANCED_TACTICAL_AIRBORNE_RECONNAISSANCE_SYSTEM_ATARS_DATA_LINK(40, "Advanced Tactical Airborne Reconnaissance System (ATARS) Data Link"),
    BATTLE_GROUP_PASSIVE_HORIZON_EXTENSION_SYSTEM_BGPHES_DATA_LINK(41, "Battle Group Passive Horizon Extension System (BGPHES) Data Link"),
    COMMON_HIGH_BANDWIDTH_DATA_LINK_CHBDL(42, "Common High Bandwidth Data Link (CHBDL)"),
    GUARDRAIL_INTEROPERABLE_DATA_LINK_IDL(43, "Guardrail Interoperable Data Link (IDL)"),
    GUARDRAIL_COMMON_SENSOR_SYSTEM_ONE_CSS1_DATA_LINK(44, "Guardrail Common Sensor System One (CSS1) Data Link"),
    GUARDRAIL_COMMON_SENSOR_SYSTEM_TWO_CSS2_DATA_LINK(45, "Guardrail Common Sensor System Two (CSS2) Data Link"),
    GUARDRAIL_CSS2_MULTI_ROLE_DATA_LINK_MRDL(46, "Guardrail CSS2 Multi-Role Data Link (MRDL)"),
    GUARDRAIL_CSS2_DIRECT_AIR_TO_SATELLITE_RELAY_DASR_DATA_LINK(47, "Guardrail CSS2 Direct Air to Satellite Relay (DASR) Data Link"),
    LINE_OF_SIGHT_LOS_DATA_LINK_IMPLEMENTATION_LOS_TETHER(48, "Line of Sight (LOS) Data Link Implementation (LOS tether)"),
    LIGHTWEIGHT_CDL_LWCDL(49, "Lightweight CDL (LWCDL)"),
    L_52M_SR_71(50, "L-52M (SR-71)"),
    RIVET_REACH_RIVET_OWL_DATA_LINK(51, "Rivet Reach/Rivet Owl Data Link"),
    SENIOR_SPAN(52, "Senior Span"),
    SENIOR_SPUR(53, "Senior Spur"),
    SENIOR_STRETCH(54, "Senior Stretch."),
    SENIOR_YEAR_INTEROPERABLE_DATA_LINK_IDL(55, "Senior Year Interoperable Data Link (IDL)"),
    SPACE_CDL(56, "Space CDL"),
    TR_1_MODE_MIST_AIRBORNE_DATA_LINK(57, "TR-1 mode MIST Airborne Data Link"),
    KU_BAND_SATCOM_DATA_LINK_IMPLEMENTATION_UAV(58, "Ku-band SATCOM Data Link Implementation (UAV)"),
    MISSION_EQUIPMENT_CONTROL_DATA_LINK_MECDL(59, "Mission Equipment Control Data link (MECDL)"),
    RADAR_DATA_TRANSMITTING_SET_DATA_LINK(60, "Radar Data Transmitting Set Data Link"),
    SURVEILLANCE_AND_CONTROL_DATA_LINK_SCDL(61, "Surveillance and Control Data Link (SCDL)"),
    TACTICAL_UAV_VIDEO(62, "Tactical UAV Video"),
    UHF_SATCOM_DATA_LINK_IMPLEMENTATION_UAV(63, "UHF SATCOM Data Link Implementation (UAV)"),
    TACTICAL_COMMON_DATA_LINK_TCDL(64, "Tactical Common Data Link (TCDL)"),
    LOW_LEVEL_AIR_PICTURE_INTERFACE_LLAPI(65, "Low Level Air Picture Interface (LLAPI)"),
    WEAPONS_DATA_LINK_AGM_130(66, "Weapons Data Link (AGM-130)"),
    GC3(99, "GC3"),
    LINK_16_STANDARDIZED_FORMAT_JTIDS_MIDS_TADIL_J(100, "Link 16 Standardized Format (JTIDS/MIDS/TADIL J)"),
    LINK_16_ENHANCED_DATA_RATE_EDR_JTIDS_MIDS_TADIL_J(101, "Link 16 Enhanced Data Rate (EDR JTIDS/MIDS/TADIL-J)"),
    JTIDS_MIDS_NET_DATA_LOAD_TIMS_TOMS(102, "JTIDS/MIDS Net Data Load (TIMS/TOMS)"),
    LINK_22(103, "Link 22"),
    AFIWC_IADS_COMMUNICATIONS_LINKS(104, "AFIWC IADS Communications Links");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as TDLType.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public TDLType lookup[] = new TDLType[105];

static private HashMap<Integer, TDLType>enumerations = new HashMap<Integer, TDLType>();

/* initialize the array and hash table at class load time */
static 
{
    for(TDLType anEnum:TDLType.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
TDLType(int value, String description)
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

    TDLType val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public TDLType getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    TDLType val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration TDLType");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    TDLType val;
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
