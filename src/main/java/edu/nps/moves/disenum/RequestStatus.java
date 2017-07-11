package edu.nps.moves.disenum;

import java.util.HashMap;
import edu.nps.moves.siso.EnumNotFoundException;

/** Enumeration values for RequestStatus
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

public enum RequestStatus 
{

    OTHER(0, "Other"),
    PENDING(1, "Pending"),
    EXECUTING(2, "Executing"),
    PARTIALLY_COMPLETE(3, "Partially Complete"),
    COMPLETE(4, "Complete"),
    REQUEST_REJECTED(5, "Request rejected"),
    RETRANSMIT_REQUEST_NOW(6, "Retransmit request now"),
    RETRANSMIT_REQUEST_LATER(7, "Retransmit request later"),
    INVALID_TIME_PARAMETERS(8, "Invalid time parameters"),
    SIMULATION_TIME_EXCEEDED(9, "Simulation time exceeded"),
    REQUEST_DONE(10, "Request done"),
    TACCSF_LOS_REPLY_TYPE_1(100, "TACCSF LOS Reply-Type 1"),
    TACCSF_LOS_REPLY_TYPE_2(101, "TACCSF LOS Reply-Type 2"),
    JOIN_EXERCISE_REQUEST_REJECTED(201, "Join Exercise Request Rejected");

    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;

/** This is an array, with each slot corresponding to an enumerated value. This is a fast but brittle way to look up
 * enumerated values. If there is no enumeration corresponding to the value it will fail, and it will also fail if the
 * index it out of range of the array. But it is fast, and generates less garbage than the alternative of using 
 * getEnumerationForValue(). It should be used only in real-time environments, and be careful even then.<p>
 * Use as RequestStatus.lookup[aVal] to get the enumeration that corresponds to a value.<p>
 * In non-realtime environments, the prefered method is getEnumerationForValue().
 */
static public RequestStatus lookup[] = new RequestStatus[202];

static private HashMap<Integer, RequestStatus>enumerations = new HashMap<Integer, RequestStatus>();

/* initialize the array and hash table at class load time */
static 
{
    for(RequestStatus anEnum:RequestStatus.values())
    {
        lookup[anEnum.value] = anEnum;
        enumerations.put(new Integer(anEnum.getValue()), anEnum);
    }
}

/** Constructor */
RequestStatus(int value, String description)
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

    RequestStatus val = enumerations.get(new Integer(aVal));
      if(val == null)
        desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
      else
         desc = val.getDescription();

      return desc;
}

/** Returns the enumerated instance with this value. 
 * If there is no enumerated instance for this value, the exception is thrown.
*/
static public RequestStatus getEnumerationForValue(int aVal) throws EnumNotFoundException
{
    RequestStatus val;
      val = enumerations.get(new Integer(aVal));
      if(val == null)
         throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration RequestStatus");
      return val;
}

/** Returns true if there is an enumerated instance for this value, false otherwise. 
*/
static public boolean enumerationForValueExists(int aVal)
{
    RequestStatus val;
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
