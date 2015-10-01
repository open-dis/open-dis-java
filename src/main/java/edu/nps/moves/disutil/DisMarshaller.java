
package edu.nps.moves.disutil;

import edu.nps.moves.dis.*;

/**
 * Marhsalls a Java object PDU to IEEE DIS, optionally changing coordinate systems
 * of some PDUs to conform to the desired coordinate system.<p>
 *
 * DIS has a standard coordiante system; its origin is a the center of the
 * earth and it is cartesian. It's very difficult for casual programmers to work
 * with, though. Often simulators want to specify object locations in (lat, lon, altitude)
 * or (x,y) offset from some position on the surface of the earth. Also, the timestamp
 * updates are not always easy to work with. In short, there are some fields in the
 * PDUs whose values are not easily set, but that nonetheless need to be correctly
 * specified. This class accoplishes that.<p>
 *
 * The class can rewrite the coordinates used for position in PDUs to conform to
 * the DIS standard, or someother agreed-upon standard. Fields such as the
 * timestamp or exercise ID can also be set--in short, it can look over the
 * shoulder of the programmer and set the fields correctly even if the programmer
 * doesn't. As with any effort of this type, the line betwen being helpful and
 * being annoying is a fine one.<p>
 * 
 * @author DMcG
 */
public class DisMarshaller
{
    /** The various formats we can use for the timestamp format in the PDU header when marshalled */
    public enum TimestampStyle { IEEE_ABSOLUTE, IEEE_RELATIVE, NPS};


    /** Class that performs coordinate transformations */
    private CoordinateTransformer coordinateTransformer;
    private boolean doCoordinateTransform = false;

    /** The exercise ID, which is in the header */
    private short exerciseId = 0;
    private boolean doExerciseId = false;

    /** We can marshal the PDU with a timestamp set to any of several styles. Remember, you
     * pretty much MUST set a timestamp; DIS will regard multiple packets sent with the
     * same timestamp as duplicates and may discard them.
     */
    private TimestampStyle timestampStyle;
    private boolean doTimestamp = false;


   public byte[] marshalPdu(Pdu aPdu)
   {
       // Should we rewrite the timestamp field?
      if(doTimestamp)
      {
         this.rewriteTimestamp(aPdu);
      }

      // Should we set the exercise ID when we send?
      if(doExerciseId)
      {
          aPdu.setExerciseID(exerciseId);
      }

      // 

      return null;
   }

   private void rewriteTimestamp(Pdu aPdu)
   {
        switch(timestampStyle)
          {
              case IEEE_ABSOLUTE:
                  break;

              case IEEE_RELATIVE:
                  break;

              case NPS:
                  break;

              default:
                  break;
          }  
   }
}
