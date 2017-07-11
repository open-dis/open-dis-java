package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for USWeaponsForLifeForms
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

public enum USWeaponsForLifeForms 
{

    ASSAULT_MACHINE_PISTOL_KF_AMP(1, "Assault machine pistol, KF-AMP"),
    AUTOMATIC_MODEL_1911A1_45(2, "Automatic model 1911A1 .45"),
    COMBAT_MASTER_MARK_VI_45_DETRONICS(3, "Combat Master Mark VI .45, Detronics"),
    DE_COCKER_KP90DC_45(4, "De-cocker KP90DC .45"),
    DE_COCKER_KP91DC_40(5, "De-cocker KP91DC .40"),
    GENERAL_OFFICERS_MODEL_15_45(6, "General officer's Model 15 .45"),
    NOVA_9_MM_LAFRANCE(7, "Nova 9-mm, LaFrance"),
    PERSONAL_DEFENSE_WEAPON_MP5K_PDW_9_MM(8, "Personal Defense Weapon MP5K-PDW 9-mm"),
    SILENCED_COLT_45_LAFRANCE(9, "Silenced Colt .45, LaFrance"),
    X_5900_SERIES_9_MM_SMITH_WESSON_SW(10, "5900-series 9-mm, Smith & Wesson (S&W)"),
    M9(11, "M9"),
    MODEL_1911A1_SPRINGFIELD_ARMORY(12, "Model 1911A1, Springfield Armory"),
    MODEL_2000_9_MM(13, "Model 2000 9-mm"),
    P_9_9_MM_SPRINGFIELD_ARMORY(14, "P-9 9-mm, Springfield Armory"),
    P_12_9_MM(15, "P-12 9-mm"),
    P_85_MARK_II_9_MM_RUGER(16, "P-85 Mark II 9-mm, Ruger"),
    ADVANCED_COMBAT_RIFLE_556_MM_AAI(17, "Advanced Combat Rifle 5.56-mm, AAI"),
    COMMANDO_ASSAULT_RIFLE_MODEL_733_556_MM_COLT(18, "Commando assault rifle, Model 733 5.56-mm, Colt"),
    INFANTRY_RIFLE_MINI_14_20_GB_556_MM_RUGER(19, "Infantry rifle, Mini-14/20 GB 5.56-mm, Ruger"),
    MINI_14_556_MM_RUGER(20, "Mini-14 5.56-mm, Ruger"),
    MINI_THIRTY_762_MM_RUGER(21, "Mini Thirty 7.62-mm, Ruger"),
    SEMI_AUTOMATIC_MODEL_82A2_50_BARRETT(22, "Semi-automatic model 82A2 .50, Barrett"),
    SNIPER_WEAPON_SYSTEM_M24_762_MM(23, "Sniper Weapon System M24 7.62-mm"),
    SNIPING_RIFLE_M21_SPRINGFIELD_ARMORY(24, "Sniping rifle M21, Springfield Armory"),
    SNIPING_RIFLE_M40A1_762_MM(25, "Sniping rifle M40A1 7.62-mm"),
    SNIPING_RIFLE_M600_762_MM(26, "Sniping rifle M600 7.62-mm"),
    AR_15_M16_556_MM(27, "AR-15 (M16) 5.56-mm"),
    M1_30(28, "M1 .30"),
    M14_762_MM_NATO(29, "M14 7.62-mm, NATO"),
    M14_M1A_M1A1_A1_SPRINGFIELD_ARMORY(30, "M14 (M1A, M1A1-A1), Springfield Armory"),
    M14K_ASSAULT_RIFLE_LAFRANCE(31, "M14K assault rifle, LaFrance"),
    M16A2_ASSAULT_RIFLE_556_MM_COLT(32, "M16A2 assault rifle 5.56-mm, Colt"),
    M21_762_MM_US(33, "M21 7.62-mm, U.S."),
    M77_MARK_II_556_MM_RUGER(34, "M77 Mark II 5.56-mm, Ruger"),
    M77V_762_MM_RUGER(35, "M77V 7.62-mm, Ruger"),
    S_16_762_X_36_MM_GRENDEL(36, "S-16 7.62 x 36-mm, Grendel"),
    SAR_8_762_MM(37, "SAR-8 7.62-mm"),
    SAR_4800_762_MM(38, "SAR-4800 7.62-mm"),
    ASSAULT_CARBINE_M16K_LAFRANCE(39, "Assault carbine M16K, LaFrance"),
    M1_30_1(40, "M1 .30"),
    M4_MODEL_720_556_MM_COLT(41, "M4 (Model 720) 5.56-mm, Colt"),
    M_900_9_MM_CALICO(42, "M-900 9-mm, Calico"),
    AC_556F_556_MM_RUGER(43, "AC-556F 5.56-mm, Ruger"),
    M3_45(44, "M3 .45"),
    M11_COBRAY(45, "M11, Cobray"),
    M951_9_MM_CALICO(46, "M951 9-mm, Calico"),
    MP5_10_10_MM(47, "MP5/10 10-mm"),
    X_9_MM_COLT(48, "9-mm, Colt"),
    INGRAM(49, "Ingram"),
    EXTERNALLY_POWERED_EPG_762_MM_ARES(50, "Externally powered (EPG) 7.62-mm, Ares"),
    GECAL_50(51, "GECAL 50"),
    GENERAL_PURPOSE_M60_762_MM(52, "General purpose M60 7.62-mm"),
    HEAVY_M2HB_QCB_50_RAMO(53, "Heavy M2HB-QCB .50, RAMO"),
    LIGHT_ASSAULT_M60E3_ENHANCED_762_MM(54, "Light assault M60E3 (Enhanced) 7.62-mm"),
    LIGHT_M16A2_556_MM_COLT(55, "Light M16A2 5.56-mm, Colt"),
    LIGHT_556_MM_ARES(56, "Light 5.56-mm, Ares"),
    LIGHTWEIGHT_M2_50_RAMO(57, "Lightweight M2 .50, RAMO"),
    LIGHTWEIGHT_ASSAULT_M60E3_762_MM(58, "Lightweight assault M60E3 7.62-mm"),
    MINIGUN_M134_762_MM_GENERAL_ELECTRIC(59, "Minigun M134 7.62-mm, General Electric"),
    MG_SYSTEM_MK19_MOD_3_40_MM(60, "MG system MK19 Mod 3, 40-mm"),
    MG_SYSTEM_OR_KIT_M2HB_QCB_50_SACO_DEFENSE(61, "MG system (or kit) M2HB QCB .50, Saco Defense"),
    M1919A4_30_CAL_BROWNING(62, "M1919A4 .30-cal, Browning"),
    X_50_CAL_BROWNING(63, ".50-cal, Browning"),
    COLORED_SMOKE_HAND_GRENADE_M18(64, "Colored-smoke hand grenade M18"),
    COLORED_SMOKE_GRENADES_FEDERAL_LABORATORIES(65, "Colored-smoke grenades, Federal Laboratories"),
    INFRARED_SMOKE_GRENADE_M76(66, "Infrared smoke grenade M76"),
    SMOKE_HAND_GRENADE_AN_M8_HC(67, "Smoke hand grenade AN-M8 HC"),
    DELAY_FRAGMENTATION_HAND_GRENADE_M61(68, "Delay fragmentation hand grenade M61"),
    DELAY_FRAGMENTATION_HAND_GRENADE_M67(69, "Delay fragmentation hand grenade M67"),
    IMPACT_FRAGMENTATION_HAND_GRENADE_M57(70, "Impact fragmentation hand grenade M57"),
    IMPACT_FRAGMENTATION_HAND_GRENADE_M68(71, "Impact fragmentation hand grenade M68"),
    INCENDIARY_HAND_GRENADE_AN_M14_TH3(72, "Incendiary hand grenade AN-M14 TH3"),
    LAUNCHER_I_M203_40_MM(73, "Launcher I-M203 40-mm"),
    LAUNCHER_M79_40_MM(74, "Launcher M79 40-mm"),
    MULTIPLE_GRENADE_LAUNCHER_MM_1_40_MM(75, "Multiple grenade launcher MM-1 40-mm"),
    MULTI_SHOT_PORTABLE_FLAME_WEAPON_M202A2_66_MM(76, "Multi-shot portable flame weapon M202A2 66-mm"),
    PORTABLE_ABC_M9_7(77, "Portable ABC-M9-7"),
    PORTABLE_M2A1_7(78, "Portable M2A1-7"),
    PORTABLE_M9E1_7(79, "Portable M9E1-7"),
    DRAGON_MEDIUM_ANTI_ARMOR_MISSILE_M47_FGM_77A(80, "Dragon medium anti-armor missile, M47, FGM-77A"),
    JAVELIN_AAWS_M(81, "Javelin AAWS-M"),
    LIGHT_ANTITANK_WEAPON_M72_LAW_II(82, "Light Antitank Weapon M72 (LAW II)"),
    REDEYE_FIM_43_GENERAL_DYNAMICS(83, "Redeye, FIM-43, General Dynamics"),
    SABER_DUAL_PURPOSE_MISSILE_SYSTEM(84, "Saber dual-purpose missile system"),
    STINGER_FIM_92_GENERAL_DYNAMICS(85, "Stinger, FIM-92, General Dynamics"),
    TOW_HEAVY_ANTITANK_WEAPON(86, "TOW heavy antitank weapon"),
    BEAR_TRAP_AP_DEVICE_PANCOR(87, "Bear Trap AP device, Pancor"),
    CHAIN_GUN_AUTOMATIC_WEAPON_EX_34_762_MM(88, "Chain Gun automatic weapon EX-34 7.62-mm"),
    CLOSE_ASSAULT_WEAPON_SYSTEM_CAWS_AAI(89, "Close Assault Weapon System (CAWS), AAI"),
    CAWS_OLIN_HECKLER_AND_KOCH(90, "CAWS, Olin/Heckler and Koch"),
    CROSSFIRE_SAM_MODEL_88(91, "Crossfire SAM Model 88"),
    DRAGON_AND_M16(92, "Dragon and M16"),
    FIRING_PORT_WEAPON_M231_556_MM_COLT(93, "Firing port weapon M231, 5.56-mm, Colt"),
    FOXHOLE_DIGGER_EXPLOSIVE_KIT_EXFODA(94, "Foxhole Digger Explosive Kit (EXFODA)"),
    INFANTRY_SUPPORT_WEAPON_ASP_30_RM_30_MM(95, "Infantry Support Weapon ASP-30 {RM} 30-mm"),
    JACKHAMMER_MK_3_A2_PANCOR(96, "Jackhammer Mk 3-A2, Pancor"),
    LIGHT_ANTI_ARMOR_WEAPON_M136_AT4(97, "Light anti-armor weapon M136 (AT4)"),
    M26A2(98, "M26A2"),
    MASTER_KEY_S(99, "Master Key S"),
    MINIGUN_556_MM(100, "Minigun 5.56-mm"),
    MULTIPURPOSE_INDIVIDUAL_MUNITION_MPIM_MARQUARDT(101, "Multipurpose Individual Munition (MPIM), Marquardt"),
    MULTIPURPOSE_WEAPON_AT8(102, "Multipurpose weapon AT8"),
    RECOILLESS_RIFLE_M40_M40A2_AND_M40A4_106_MM(103, "Recoilless rifle M40, M40A2, and M40A4; 106-mm"),
    RECOILLESS_RIFLE_M67_90_MM(104, "Recoilless rifle M67, 90-mm"),
    REVOLVER_SP_101(105, "Revolver, SP 101"),
    REVOLVER_SUPER_REDHAWK_44_MAGNUM_RUGER(106, "Revolver, Super Redhawk .44 magnum, Ruger"),
    RAW_ROCKET_140_MM_BRUNSWICK(107, "RAW rocket, 140-mm, Brunswick"),
    RIFLE_LAUNCHER_ANTI_ARMOR_MUNITION_RAAM_OLIN(108, "Rifle-launcher Anti-Armor Munition (RAAM), Olin"),
    ROCKET_LAUNCHER_M_20_35_IN(109, "Rocket launcher M-20 3.5-in"),
    ROCKET_LAUNCHER_ENHANCED_M72_E_SERIES_HEAT_66_MM(110, "Rocket launcher, Enhanced M72 E series HEAT, 66-mm"),
    SELECTIVE_FIRE_WEAPON_AC_556_556_MM_RUGER(111, "Selective fire weapon AC-556 5.56-mm, Ruger"),
    SELECTIVE_FIRE_WEAPON_AC_556F_556_MM_RUGER(112, "Selective fire weapon AC-556F 5.56-mm, Ruger"),
    SHOTGUN_M870_MK_1_US_MARINE_CORPS_REMINGTON(113, "Shotgun M870 Mk 1 (U.S. Marine Corps), Remington"),
    SMAW_MK_193_83_MM_MCDONNELL_DOUGLAS(114, "SMAW Mk 193, 83-mm, McDonnell-Douglas"),
    SMAW_D_DISPOSABLE_SMAW(115, "SMAW-D: Disposable SMAW"),
    SQUAD_AUTOMATIC_WEAPON_SAW_M249_556_MM(116, "Squad Automatic Weapon (SAW) M249 5.56-mm"),
    TACTICAL_SUPPORT_WEAPON_50_12_50_CAL_PEREGRINE(117, "Tactical Support Weapon 50/12, .50-cal, Peregrine"),
    TELESCOPED_AMMUNITION_REVOLVER_GUN_TARG_50_CAL_ARES(118, "Telescoped Ammunition Revolver Gun (TARG) .50-cal, Ares"),
    ULTIMATE_OVER_UNDER_COMBINATION_CIENER(119, "Ultimate over-under combination, Ciener"),
    M18A1_CLAYMORE_MINE(120, "M18A1 Claymore mine"),
    MORTAR_81_MM(121, "Mortar 81-mm"),
    MACHINEGUN_M240_762MM(134, "Machinegun M240 7.62mm");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as USWeaponsForLifeForms.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public USWeaponsForLifeForms lookup[] = new USWeaponsForLifeForms[135];

static private HashMap<Integer, USWeaponsForLifeForms>enumerations = new HashMap<Integer, USWeaponsForLifeForms>();

/* initialize the array and hash table at class load time */
static 
{
    for(USWeaponsForLifeForms anEnum:USWeaponsForLifeForms.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
USWeaponsForLifeForms(int value, String description)
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

    USWeaponsForLifeForms val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public USWeaponsForLifeForms getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    USWeaponsForLifeForms val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration USWeaponsForLifeForms");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    USWeaponsForLifeForms val;
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
