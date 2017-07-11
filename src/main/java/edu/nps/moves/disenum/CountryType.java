package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for CountryType
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

public enum CountryType 
{

    OTHER(0, "Other", "Unknown"),
    AFGHANISTAN(1, "Afghanistan", "AF"),
    ALBANIA(2, "Albania", "AL"),
    ALGERIA(3, "Algeria", "DZ"),
    AMERICAN_SAMOA_UNITED_STATES(4, "American Samoa (United States)", "Unknown"),
    ANDORRA(5, "Andorra", "AD"),
    ANGOLA(6, "Angola", "AO"),
    ANGUILLA(7, "Anguilla", "AI"),
    ANTARCTICA_INTERNATIONAL(8, "Antarctica (International)", "Unknown"),
    ANTIGUA_AND_BARBUDA(9, "Antigua and Barbuda", "AG"),
    ARGENTINA(10, "Argentina", "AR"),
    ARMENIA(244, "Armenia", "AM"),
    ARUBA(11, "Aruba", "AW"),
    ASHMORE_AND_CARTIER_ISLANDS_AUSTRALIA(12, "Ashmore and Cartier Islands (Australia)", "Unknown"),
    AUSTRALIA(13, "Australia", "AU"),
    AUSTRIA(14, "Austria", "AT"),
    AZERBAIJAN(245, "Azerbaijan", "AZ"),
    BAHAMAS(15, "Bahamas", "BS"),
    BAHRAIN(16, "Bahrain", "BH"),
    BAKER_ISLAND_UNITED_STATES(17, "Baker Island (United States)", "Unknown"),
    BANGLADESH(18, "Bangladesh", "BD"),
    BARBADOS(19, "Barbados", "BB"),
    BASSAS_DA_INDIA_FRANCE(20, "Bassas da India (France)", "Unknown"),
    BELARUS(246, "Belarus", "BY"),
    BELGIUM(21, "Belgium", "BE"),
    BELIZE(22, "Belize", "BZ"),
    BENIN_AKA_DAHOMEY(23, "Benin (aka Dahomey)", "Unknown"),
    BERMUDA_UNITED_KINGDOM(24, "Bermuda (United Kingdom)", "Unknown"),
    BHUTAN(25, "Bhutan", "BT"),
    BOLIVIA(26, "Bolivia", "BO"),
    BOSNIA_AND_HERCEGOVINA(247, "Bosnia and Hercegovina", "Unknown"),
    BOTSWANA(27, "Botswana", "BW"),
    BOUVET_ISLAND_NORWAY(28, "Bouvet Island (Norway)", "Unknown"),
    BRAZIL(29, "Brazil", "BR"),
    BRITISH_INDIAN_OCEAN_TERRITORY_UNITED_KINGDOM(30, "British Indian Ocean Territory (United Kingdom)", "Unknown"),
    BRITISH_VIRGIN_ISLANDS_UNITED_KINGDOM(31, "British Virgin Islands (United Kingdom)", "Unknown"),
    BRUNEI(32, "Brunei", "Unknown"),
    BULGARIA(33, "Bulgaria", "BG"),
    BURKINA_AKA_BURKINA_FASO_OR_UPPER_VOLTA(34, "Burkina (aka Burkina Faso or Upper Volta)", "Unknown"),
    BURMA_MYANMAR(35, "Burma (Myanmar)", "Unknown"),
    BURUNDI(36, "Burundi", "BI"),
    CAMBODIA_AKA_KAMPUCHEA(37, "Cambodia (aka Kampuchea)", "Unknown"),
    CAMEROON(38, "Cameroon", "CM"),
    CANADA(39, "Canada", "CA"),
    CAPE_VERDE_REPUBLIC_OF(40, "Cape Verde, Republic of", "Unknown"),
    CAYMAN_ISLANDS_UNITED_KINGDOM(41, "Cayman Islands (United Kingdom)", "Unknown"),
    CENTRAL_AFRICAN_REPUBLIC(42, "Central African Republic", "CF"),
    CHAD(43, "Chad", "TD"),
    CHILE(44, "Chile", "CL"),
    CHINA_PEOPLES_REPUBLIC_OF(45, "China, People's Republic of", "Unknown"),
    CHRISTMAS_ISLAND_AUSTRALIA(46, "Christmas Island (Australia)", "Unknown"),
    CLIPPERTON_ISLAND_FRANCE(248, "Clipperton Island (France)", "Unknown"),
    COCOS_KEELING_ISLANDS_AUSTRALIA(47, "Cocos (Keeling) Islands (Australia)", "Unknown"),
    COLOMBIA(48, "Colombia", "CO"),
    COMMONWEALTH_OF_INDEPENDENT_STATES(222, "Commonwealth of Independent States", "Unknown"),
    COMOROS(49, "Comoros", "KM"),
    CONGO_REPUBLIC_OF(50, "Congo, Republic of", "Unknown"),
    COOK_ISLANDS_NEW_ZEALAND(51, "Cook Islands (New Zealand)", "Unknown"),
    CORAL_SEA_ISLANDS_AUSTRALIA(52, "Coral Sea Islands (Australia)", "Unknown"),
    COSTA_RICA(53, "Costa Rica", "CR"),
    COTE_DIVOIRE_AKA_IVORY_COAST(107, "(Cote D'Ivoire (aka Ivory Coast)", "Unknown"),
    CROATIA(249, "Croatia", "Unknown"),
    CUBA(54, "Cuba", "CU"),
    CYPRUS(55, "Cyprus", "CY"),
    CZECHOSLOVAKIA_SEPARATING_INTO_CZECH_REPUBLIC_AND_SLOVAK_REPUBLIC(56, "Czechoslovakia (separating into Czech Republic and Slovak Republic)", "Unknown"),
    DAHOMEY_AKA_BENIN(23, "Dahomey (aka Benin)", "Unknown"),
    DENMARK(57, "Denmark", "DK"),
    DJIBOUTI(58, "Djibouti", "DJ"),
    DOMINICA(59, "Dominica", "DM"),
    DOMINICAN_REPUBLIC(60, "Dominican Republic", "DO"),
    ECUADOR(61, "Ecuador", "EC"),
    EGYPT(62, "Egypt", "EG"),
    EL_SALVADOR(63, "El Salvador", "SV"),
    EQUATORIAL_GUINEA(64, "Equatorial Guinea", "GQ"),
    ESTONIA(250, "Estonia", "EE"),
    ETHIOPIA(65, "Ethiopia", "ET"),
    EUROPA_ISLAND_FRANCE(66, "Europa Island (France)", "Unknown"),
    FALKLAND_ISLANDS_AKA_ISLAS_MALVINAS_UNITED_KINGDOM(67, "Falkland Islands (aka Islas Malvinas) (United Kingdom)", "Unknown"),
    FAROE_ISLANDS_DENMARK(68, "Faroe Islands (Denmark)", "Unknown"),
    FIJI(69, "Fiji", "FJ"),
    FINLAND(70, "Finland", "FI"),
    FRANCE(71, "France", "FR"),
    FRENCH_GUIANA_FRANCE(72, "French Guiana (France)", "Unknown"),
    FRENCH_POLYNESIA_FRANCE(73, "French Polynesia (France)", "Unknown"),
    FRENCH_SOUTHERN_AND_ANTARCTIC_ISLANDS_FRANCE(74, "French Southern and Antarctic Islands (France)", "Unknown"),
    GABON(75, "Gabon", "GA"),
    GAMBIA_THE(76, "Gambia, The", "Unknown"),
    GAZA_STRIP_ISRAEL(77, "Gaza Strip (Israel)", "Unknown"),
    GEORGIA(251, "Georgia", "GE"),
    GERMANY(78, "Germany", "DE"),
    GHANA(79, "Ghana", "GH"),
    GIBRALTAR_UNITED_KINGDOM(80, "Gibraltar (United Kingdom)", "Unknown"),
    GLORIOSO_ISLANDS_FRANCE(81, "Glorioso Islands (France)", "Unknown"),
    GREECE(82, "Greece", "GR"),
    GREENLAND_DENMARK(83, "Greenland (Denmark)", "Unknown"),
    GRENADA(84, "Grenada", "GD"),
    GUADALOUPE_FRANCE(85, "Guadaloupe (France)", "Unknown"),
    GUAM_UNITED_STATES(86, "Guam (United States)", "Unknown"),
    GUATEMALA(87, "Guatemala", "GT"),
    GUERNSEY_UNITED_KINGDOM(88, "Guernsey (United Kingdom)", "Unknown"),
    GUINEA(89, "Guinea", "GN"),
    GUINEA_BISSAU(90, "Guinea- Bissau", "Unknown"),
    GUYANA(91, "Guyana", "GY"),
    HAITI(92, "Haiti", "HT"),
    HEARD_ISLAND_AND_MCDONALD_ISLANDS_AUSTRALIA(93, "Heard Island and McDonald Islands (Australia)", "Unknown"),
    HONDURAS(94, "Honduras", "HN"),
    HONG_KONG_UNITED_KINGDOM(95, "Hong Kong (United Kingdom)", "Unknown"),
    HOWLAND_ISLAND_UNITED_STATES(96, "Howland Island (United States)", "Unknown"),
    HUNGARY(97, "Hungary", "HU"),
    ICELAND(98, "Iceland", "IS"),
    INDIA(99, "India", "IN"),
    INDONESIA(100, "Indonesia", "ID"),
    IRAN(101, "Iran", "IR"),
    IRAQ(102, "Iraq", "IQ"),
    IRELAND(104, "Ireland", "IE"),
    ISRAEL(105, "Israel", "IL"),
    ITALY(106, "Italy", "IT"),
    IVORY_COAST_AKA_COTE_DIVOIRE(107, "Ivory Coast (aka Cote D'Ivoire)", "Unknown"),
    JAMAICA(108, "Jamaica", "JM"),
    JAN_MAYEN_NORWAY(109, "Jan Mayen (Norway)", "Unknown"),
    JAPAN(110, "Japan", "JP"),
    JARVIS_ISLAND_UNITED_STATES(111, "Jarvis Island (United States)", "Unknown"),
    JERSEY_UNITED_KINGDOM(112, "Jersey (United Kingdom)", "Unknown"),
    JOHNSTON_ATOLL_UNITED_STATES(113, "Johnston Atoll (United States)", "Unknown"),
    JORDAN(114, "Jordan", "JO"),
    JUAN_DE_NOVA_ISLAND(115, "Juan de Nova Island", "Unknown"),
    KAZAKHSTAN(252, "Kazakhstan", "KZ"),
    KENYA(116, "Kenya", "KE"),
    KINGMAN_REEF_UNITED_STATES(117, "Kingman Reef (United States)", "Unknown"),
    KIRIBATI(118, "Kiribati", "KI"),
    KOREA_DEMOCRATIC_PEOPLES_REPUBLIC_OF_NORTH(119, "Korea, Democratic People's Republic of (North)", "Unknown"),
    KOREA_REPUBLIC_OF_SOUTH(120, "Korea, Republic of (South)", "Unknown"),
    KUWAIT(121, "Kuwait", "KW"),
    KYRGYZSTAN(253, "Kyrgyzstan", "KG"),
    LAOS(122, "Laos", "LA"),
    LATVIA(254, "Latvia", "LV"),
    LEBANON(123, "Lebanon", "LB"),
    LESOTHO(124, "Lesotho", "LS"),
    LIBERIA(125, "Liberia", "LR"),
    LIBYA(126, "Libya", "LY"),
    LIECHTENSTEIN(127, "Liechtenstein", "LI"),
    LITHUANIA(255, "Lithuania", "LT"),
    LUXEMBOURG(128, "Luxembourg", "LU"),
    MACAU_PORTUGAL(130, "Macau (Portugal)", "Unknown"),
    MACEDONIA(256, "Macedonia", "MK"),
    MADAGASCAR(129, "Madagascar", "MG"),
    MALAWI(131, "Malawi", "MW"),
    MALAYSIA(132, "Malaysia", "MY"),
    MALDIVES(133, "Maldives", "MV"),
    MALI(134, "Mali", "ML"),
    MALTA(135, "Malta", "MT"),
    MAN_ISLE_OF_UNITED_KINGDOM(136, "Man, Isle of (United Kingdom)", "Unknown"),
    MARSHALL_ISLANDS(137, "Marshall Islands", "MH"),
    MARTINIQUE_FRANCE(138, "Martinique (France)", "Unknown"),
    MAURITANIA(139, "Mauritania", "MR"),
    MAURITIUS(140, "Mauritius", "MU"),
    MAYOTTE_FRANCE(141, "Mayotte (France)", "Unknown"),
    MEXICO(142, "Mexico", "MX"),
    MICRONESIA_FEDERATIVE_STATES_OF(143, "Micronesia, Federative States of", "Unknown"),
    MIDWAY_ISLANDS_UNITED_STATES(257, "Midway Islands (United States)", "Unknown"),
    MOLDOVA(258, "Moldova", "MD"),
    MONACO(144, "Monaco", "MC"),
    MONGOLIA(145, "Mongolia", "MN"),
    MONTENEGRO(259, "Montenegro", "Unknown"),
    MONTSERRAT_UNITED_KINGDOM(146, "Montserrat (United Kingdom)", "Unknown"),
    MOROCCO(147, "Morocco", "MA"),
    MOZAMBIQUE(148, "Mozambique", "MZ"),
    MYANMAR_AKA_BURMA(35, "Myanmar (aka Burma)", "Unknown"),
    NAMIBIA_SOUTH_WEST_AFRICA(149, "Namibia (South West Africa)", "Unknown"),
    NAURU(150, "Nauru", "NR"),
    NAVASSA_ISLAND_UNITED_STATES(151, "Navassa Island (United States)", "Unknown"),
    NEPAL(152, "Nepal", "NP"),
    NETHERLANDS(153, "Netherlands", "NL"),
    NETHERLANDS_ANTILLES_CURACAO_BONAIRE_SABA_SINT_MAARTEN_SINT_EUSTATIUS(154, "Netherlands Antilles (Curacao, Bonaire, Saba, Sint Maarten Sint Eustatius)", "Unknown"),
    NEW_CALEDONIA_FRANCE(155, "New Caledonia (France)", "Unknown"),
    NEW_ZEALAND(156, "New Zealand", "Unknown"),
    NICARAGUA(157, "Nicaragua", "NI"),
    NIGER(158, "Niger", "NE"),
    NIGERIA(159, "Nigeria", "NG"),
    NIUE_NEW_ZEALAND(160, "Niue (New Zealand)", "Unknown"),
    NORFOLK_ISLAND_AUSTRALIA(161, "Norfolk Island (Australia)", "Unknown"),
    NORTHERN_MARIANA_ISLANDS_UNITED_STATES(162, "Northern Mariana Islands (United States)", "Unknown"),
    NORWAY(163, "Norway", "NO"),
    OMAN(164, "Oman", "OM"),
    PACIFIC_ISLANDS_TRUST_TERRITORY_OF_THE_PALAU(216, "Pacific Islands, Trust Territory of the (Palau)", "Unknown"),
    PAKISTAN(165, "Pakistan", "PK"),
    PALMYRA_ATOLL_UNITED_STATES(166, "Palmyra Atoll (United States)", "Unknown"),
    PANAMA(168, "Panama", "PA"),
    PAPUA_NEW_GUINEA(169, "Papua New Guinea", "PG"),
    PARACEL_ISLANDS_INTERNATIONAL_OCCUPIED_BY_CHINA_ALSO_CLAIMED_BY_TAIWAN_AND_VIETNAM(170, "Paracel Islands (International - Occupied by China, also claimed by Taiwan and Vietnam)", "Unknown"),
    PARAGUAY(171, "Paraguay", "PY"),
    PERU(172, "Peru", "PE"),
    PHILIPPINES(173, "Philippines", "PH"),
    PITCAIRN_ISLANDS_UNITED_KINGDOM(174, "Pitcairn Islands (United Kingdom)", "Unknown"),
    POLAND(175, "Poland", "PL"),
    PORTUGAL(176, "Portugal", "PT"),
    PUERTO_RICO_UNITED_STATES(177, "Puerto Rico (United States)", "Unknown"),
    QATAR(178, "Qatar", "QA"),
    REUNION_FRANCE(179, "Reunion (France)", "Unknown"),
    ROMANIA(180, "Romania", "RO"),
    RUSSIA(260, "Russia", "Unknown"),
    RWANDA(181, "Rwanda", "RW"),
    ST_HELENA_UNITED_KINGDOM(183, "St. Helena (United Kingdom)", "Unknown"),
    ST_LUCIA(184, "St. Lucia", "Unknown"),
    ST_VINCENT_AND_THE_GRENADINES(186, "St. Vincent and the Grenadines", "Unknown"),
    ST_KITTS_AND_NEVIS(182, "St. Kitts and Nevis", "Unknown"),
    ST_PIERRE_AND_MIQUELON_FRANCE(185, "St. Pierre and Miquelon (France)", "Unknown"),
    SAN_MARINO(187, "San Marino", "SM"),
    SAO_TOME_AND_PRINCIPE(188, "Sao Tome and Principe", "ST"),
    SAUDI_ARABIA(189, "Saudi Arabia", "SA"),
    SENEGAL(190, "Senegal", "SN"),
    SERBIA_AND_MONTENEGRO_MONTENEGRO_TO_SEPARATE(261, "Serbia and Montenegro (Montenegro to separate)", "Unknown"),
    SEYCHELLES(191, "Seychelles", "SC"),
    SIERRA_LEONE(192, "Sierra Leone", "SL"),
    SINGAPORE(193, "Singapore", "SG"),
    SLOVENIA(262, "Slovenia", "SI"),
    SOLOMON_ISLANDS(194, "Solomon Islands", "SB"),
    SOMALIA(195, "Somalia", "SO"),
    SOUTH_AFRICA(197, "South Africa", "ZA"),
    SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS_UNITED_KINGDOM(196, "South Georgia and the South Sandwich Islands (United Kingdom)", "Unknown"),
    SPAIN(198, "Spain", "ES"),
    SPRATLY_ISLANDS_INTERNATIONAL_PARTS_OCCUPIED_AND_CLAIMED_BY_CHINAMALAYSIA_PHILIPPINES_TAIWAN_VIETNAM(199, "Spratly Islands (International - parts occupied and claimed by China,Malaysia, Philippines, Taiwan, Vietnam)", "Unknown"),
    SRI_LANKA(200, "Sri Lanka", "LK"),
    SUDAN(201, "Sudan", "SD"),
    SURINAME(202, "Suriname", "SR"),
    SVALBARD_NORWAY(203, "Svalbard (Norway)", "Unknown"),
    SWAZILAND(204, "Swaziland", "SZ"),
    SWEDEN(205, "Sweden", "SE"),
    SWITZERLAND(206, "Switzerland", "CH"),
    SYRIA(207, "Syria", "SY"),
    TAIWAN(208, "Taiwan", "TW"),
    TAJIKISTAN(263, "Tajikistan", "TJ"),
    TANZANIA(209, "Tanzania", "TZ"),
    THAILAND(210, "Thailand", "TH"),
    TOGO(211, "Togo", "TG"),
    TOKELAU_NEW_ZEALAND(212, "Tokelau (New Zealand)", "Unknown"),
    TONGA(213, "Tonga", "TO"),
    TRINIDAD_AND_TOBAGO(214, "Trinidad and Tobago", "TT"),
    TROMELIN_ISLAND_FRANCE(215, "Tromelin Island (France)", "Unknown"),
    TUNISIA(217, "Tunisia", "TN"),
    TURKEY(218, "Turkey", "TR"),
    TURKMENISTAN(264, "Turkmenistan", "TM"),
    TURKS_AND_CAICOS_ISLANDS_UNITED_KINGDOM(219, "Turks and Caicos Islands (United Kingdom)", "Unknown"),
    TUVALU(220, "Tuvalu", "TV"),
    UGANDA(221, "Uganda", "UG"),
    UKRAINE(265, "Ukraine", "UA"),
    UNITED_ARAB_EMIRATES(223, "United Arab Emirates", "AE"),
    UNITED_KINGDOM(224, "United Kingdom", "UK"),
    UNITED_STATES(225, "United States", "US"),
    UPPER_VOLTA_AKA_BURKINA_OR_BURKINA_FASO(34, "Upper Volta (aka Burkina or Burkina Faso)", "Unknown"),
    URUGUAY(226, "Uruguay", "UY"),
    UZBEKISTAN(266, "Uzbekistan", "UZ"),
    VANUATU(227, "Vanuatu", "VU"),
    VATICAN_CITY_HOLY_SEE(228, "Vatican City (Holy See)", "Unknown"),
    VENEZUELA(229, "Venezuela", "VE"),
    VIETNAM(230, "Vietnam", "Unknown"),
    VIRGIN_ISLANDS_UNITED_STATES(231, "Virgin Islands (United States)", "Unknown"),
    WAKE_ISLAND_UNITED_STATES(232, "Wake Island (United States)", "Unknown"),
    WALLIS_AND_FUTUNA_FRANCE(233, "Wallis and Futuna (France)", "Unknown"),
    WEST_BANK_ISRAEL(235, "West Bank (Israel)", "Unknown"),
    WESTERN_SAHARA(234, "Western Sahara", "EH"),
    WESTERN_SAMOA(236, "Western Samoa", "Unknown"),
    YEMEN(237, "Yemen", "YE"),
    SERBIA_AND_MONTENEGRO(240, "Serbia and Montenegro", "CS"),
    ZAIRE(241, "Zaire", "Unknown"),
    ZAMBIA(242, "Zambia", "ZM"),
    ZIMBABWE(243, "Zimbabwe", "ZW");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

    /** Internet domain code (US, FR, UK, CA, etc). This is a guess for many countries */
    public final String internetDomainCode;

/** This is an array, with each slot corresponding to an enumerated value
 * and that slot containing the enumerated object. Use to look up enumerated object when you have the value
 */
static public CountryType lookup[] = new CountryType[267];
static private HashMap<Integer, CountryType>enumerations = new HashMap<Integer, CountryType>();

/* initialize the array and hash table at class load time */
static 
{
    for(CountryType anEnum:CountryType.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
CountryType(int value, String description, String internetDomainCode)
{
    this.value = value;
    this.description = description;
    this.internetDomainCode = internetDomainCode;
}

/** Returns the string description associated with the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the string Invalid enumeration: <val> is returned.
*/
static public String getDescriptionForValue(int aVal)
{
  String desc;

    CountryType val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public CountryType getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    CountryType val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration CountryType");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    CountryType val;
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

/** Returns the string description for this enumeration. */
public String getDescription()
{
  return description;
}

/** As an alternative, returns the internet domain code for this country, if it exists. */
public String getInternetDomainCode()
{
  return internetDomainCode;
}

}
