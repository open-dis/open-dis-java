package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for CISWeaponsForLifeForms
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

public enum CISWeaponsForLifeForms 
{

    AUTOMATIC_APS_9_MM_STECHKIN(201, "Automatic (APS) 9-mm, Stechkin"),
    PSM_545_MM(202, "PSM 5.45-mm"),
    SELF_LOADING_PM_9_MM_MAKAROV(203, "Self-loading (PM) 9-mm, Makarov"),
    TT_33_762_MM_TOKAREV(204, "TT-33 7.62-mm, Tokarev"),
    ASSAULT_RIFLE_AK_AND_AKM_762_MM(205, "Assault rifle AK and AKM, 7.62-mm"),
    ASSAULT_RIFLE_AK_74_AND_AKS_74_545_MM(206, "Assault rifle AK-74 and AKS-74, 5.45-mm"),
    SELF_LOADING_RIFLE_SKS_762_MM_SIMONOV(207, "Self-loading rifle (SKS), 7.62-mm, Simonov"),
    SNIPER_RIFLE_SVD_762_MM_DRAGUNOV(208, "Sniper rifle SVD 7.62-mm, Dragunov"),
    AKSU_74_545_MM(209, "AKSU-74 5.45-mm"),
    PPS_43_762_MM(210, "PPS-43 7.62-mm"),
    PPSH_41_762_MM(211, "PPSh-41 7.62-mm"),
    GENERAL_PURPOSE_PK_762_MM(212, "General purpose PK 7.62-mm"),
    HEAVY_DSHK_38_AND_MODEL_38_46_127_MM_DEGTYAREV(213, "Heavy DShK-38 and Model 38/46 12.7-mm, Degtyarev"),
    HEAVY_NSV_127_MM(214, "Heavy NSV 12.7-mm"),
    LIGHT_RPD_762_MM(215, "Light RPD 7.62-mm"),
    LIGHT_RPK_762_MM(216, "Light RPK 7.62-mm"),
    LIGHT_RPK_74_545_MM(217, "Light RPK-74 5.45-mm"),
    HAND_GRENADE_M75(218, "Hand grenade M75"),
    HAND_GRENADE_RGD_5(219, "Hand grenade RGD-5"),
    AP_HAND_GRENADE_F1(220, "AP hand grenade F1"),
    AT_HAND_GRENADE_RKG_3(221, "AT hand grenade RKG-3"),
    AT_HAND_GRENADE_RKG_3M(222, "AT hand grenade RKG-3M"),
    AT_HAND_GRENADE_RKG_3T(223, "AT hand grenade RKG-3T"),
    FRAGMENTATION_HAND_GRENADE_RGN(224, "Fragmentation hand grenade RGN"),
    FRAGMENTATION_HAND_GRENADE_RGO(225, "Fragmentation hand grenade RGO"),
    SMOKE_HAND_GRENADE_RDG_1(226, "Smoke hand grenade RDG-1"),
    PLAMYA_LAUNCHER_30_MM_AGS_17(227, "Plamya launcher, 30-mm AGS-17"),
    RIFLE_MOUNTED_LAUNCHER_BG_15_40_MM(228, "Rifle-mounted launcher, BG-15 40-mm"),
    LPO_50(229, "LPO-50"),
    ROKS_3(230, "ROKS-3"),
    CART_MOUNTED_TPO_50(231, "Cart-mounted TPO-50"),
    GIMLET_SA_16(232, "Gimlet SA-16"),
    GRAIL_SA_7(233, "Grail SA-7"),
    GREMLIN_SA_14(234, "Gremlin SA-14"),
    SAGGER_AT_3_MCLOS(235, "Sagger AT-3 (MCLOS)"),
    SAXHORN_AT_7(236, "Saxhorn AT-7"),
    SPIGOT_A_B_AT_14(237, "Spigot A/B AT-14"),
    SA_18(238, "SA-18"),
    SA_19(239, "SA-19"),
    GRAD_1P_MANPORTABLE_TRIPOD_ROCKET_LAUNCHER_122_MM_FOR_SPESNATZ_AND_OTHER_SPECIALISTS_AKA_9P132(240, "Grad-1P manportable tripod rocket launcher, 122-mm (for Spesnatz and other specialists; aka 9P132)"),
    LIGHT_ANTI_ARMOR_WEAPON_RPG_18(241, "Light anti-armor weapon RPG-18"),
    LIGHT_ANTITANK_WEAPON_RPG_22(242, "Light antitank weapon RPG-22"),
    MG_RPG(243, "MG & RPG"),
    PORTABLE_ROCKET_LAUNCHER_RPG_16(244, "Portable rocket launcher RPG-16"),
    RECOILLESS_GUN_73_MM_SPG_9(245, "Recoilless gun 73-mm SPG-9"),
    VAT_ROCKET_LAUNCHER_RPG_7(246, "VAT rocket launcher RPG-7"),
    MON_50_ANTIPERSONNEL_MINE(248, "Mon-50 antipersonnel mine");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as CISWeaponsForLifeForms.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public CISWeaponsForLifeForms lookup[] = new CISWeaponsForLifeForms[249];

static private HashMap<Integer, CISWeaponsForLifeForms>enumerations = new HashMap<Integer, CISWeaponsForLifeForms>();

/* initialize the array and hash table at class load time */
static 
{
    for(CISWeaponsForLifeForms anEnum:CISWeaponsForLifeForms.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
CISWeaponsForLifeForms(int value, String description)
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

    CISWeaponsForLifeForms val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public CISWeaponsForLifeForms getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    CISWeaponsForLifeForms val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration CISWeaponsForLifeForms");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    CISWeaponsForLifeForms val;
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
