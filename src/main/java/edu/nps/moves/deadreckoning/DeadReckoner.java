// This Java implementation is based on the C implementation from the paper:
// Towers, J., and Hines, J., "Equations of motion of the DIS 2.0.3 dead reckoning algorithms,"
// Tenth Workshop on Standards for the Interoperability of Defense Simulations, Orlando, FL, pp. 431-462, Mar. 1994.
// Available at http://www.sisostds.org/DigitalLibrary.aspx?EntryId=31599

// This provides an alternate to the existing classes
// (DIS_DeadReckoning, DIS_DR_Static_01, DIS_DR_FPW_02, etc.)
// in the Java Open-DIS library.

package edu.nps.moves.deadreckoning;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.RotationOrder;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import edu.nps.moves.dis.ArticulationParameter;
import edu.nps.moves.dis.EntityStatePdu;
import edu.nps.moves.disenum.DeadReckoningAlgorithm;

public class DeadReckoner {

    private static double MIN_ROTATION_RATE = 0.2 * Math.PI / 180;  // minimum significant rate = 1deg/5sec
    private static double MIN_ACCELERATION_RATE = 0.1;  // minimum significant rate = 1m/l0sec^2

    /**
     * Performs Dead Reckoning using any algorithm 1 through 9.
     * Modifies input pESPDU.
     * 
     * @param pESPDU
     * @param deltaTime
     */
    public static void perform_DR(EntityStatePdu pESPDU, double deltaTime) {

        // Check for invalid input parameters
        if (!DeadReckoningAlgorithm.enumerationForValueExists(pESPDU.getDeadReckoningParameters().getDeadReckoningAlgorithm()) ||
                deltaTime < 0.0) {
            throw new IllegalArgumentException();
        }

        // Determine the DR algorithm type to be used for this entity
        DeadReckoningAlgorithm DRalg = DeadReckoningAlgorithm.lookup[pESPDU.getDeadReckoningParameters().getDeadReckoningAlgorithm()];
        if (DRalg == DeadReckoningAlgorithm.OTHER) {
            throw new IllegalArgumentException();
        }

        if (deltaTime == 0.0) {
            // no time elapsed, nothing to do
            return;
        }

        // Update the ESPDU timestamp
        // TODO: Need to deal with timeStamp absolute / relative bit, and rollover.
        long dtimeStamp = 2 * (long) (deltaTime * 2147483648. / 3600.);
        pESPDU.setTimestamp(pESPDU.getTimestamp() + dtimeStamp);

        if (DRalg == DeadReckoningAlgorithm.STATIC_ENTITY_DOES_NOT_MOVE) {
            // no time elapsed, nothing further to do
            return;
        }

        // Change structure to vectors because they are easier to work with
        Vector3D location = new Vector3D(
                pESPDU.getEntityLocation().getX(),
                pESPDU.getEntityLocation().getY(),
                pESPDU.getEntityLocation().getZ());

        Vector3D velocity = new Vector3D(
                pESPDU.getEntityLinearVelocity().getX(),
                pESPDU.getEntityLinearVelocity().getY(),
                pESPDU.getEntityLinearVelocity().getZ());

        Vector3D acceleration = new Vector3D(
                pESPDU.getDeadReckoningParameters().getEntityLinearAcceleration().getX(),
                pESPDU.getDeadReckoningParameters().getEntityLinearAcceleration().getY(),
                pESPDU.getDeadReckoningParameters().getEntityLinearAcceleration().getZ());

        // Convert rotation rates to double
        double rotRateRoll  = pESPDU.getDeadReckoningParameters().getEntityAngularVelocity().getX();
        double rotRatePitch = pESPDU.getDeadReckoningParameters().getEntityAngularVelocity().getY();
        double rotRateYaw   = pESPDU.getDeadReckoningParameters().getEntityAngularVelocity().getZ();

        // Check for rotation
        boolean rotating = false;
        if (DRalg == DeadReckoningAlgorithm.DRMR_P_W ||
                DRalg == DeadReckoningAlgorithm.DRMR_V_W ||
                DRalg == DeadReckoningAlgorithm.DRMR_P_B ||
                DRalg == DeadReckoningAlgorithm.DRMR_V_B) {
            if(
                    Math.abs(rotRateRoll)  >= MIN_ROTATION_RATE ||
                    Math.abs(rotRatePitch) >= MIN_ROTATION_RATE ||
                    Math.abs(rotRateYaw)   >= MIN_ROTATION_RATE ) {
                rotating = true;
            }
        }

        // Check for acceleration
        boolean accelerating = false;
        if (DRalg == DeadReckoningAlgorithm.DRMR_V_W ||
                DRalg == DeadReckoningAlgorithm.DRMF_V_W ||
                DRalg == DeadReckoningAlgorithm.DRMR_V_B ||
                DRalg == DeadReckoningAlgorithm.DRMF_V_B) {
            if(
                    Math.abs(acceleration.getX()) >= MIN_ACCELERATION_RATE ||
                    Math.abs(acceleration.getY()) >= MIN_ACCELERATION_RATE ||
                    Math.abs(acceleration.getZ()) >= MIN_ACCELERATION_RATE ) {
                accelerating = true;
            }
        }

        // Check for use of body (entity relative) vel/accel coords
        boolean bodyCoords = false;
        if (DRalg == DeadReckoningAlgorithm.DRMF_P_B ||
                DRalg == DeadReckoningAlgorithm.DRMR_P_B ||
                DRalg == DeadReckoningAlgorithm.DRMR_V_B ||
                DRalg == DeadReckoningAlgorithm.DRMF_V_B) {
            bodyCoords = true;
        }

        // Set up and compute intermediate variables
        Rotation WCStoECS1Mat = Rotation.IDENTITY;

        if (rotating || bodyCoords) {
            WCStoECS1Mat = new Rotation(
                    RotationOrder.ZYX,
                    RotationConvention.FRAME_TRANSFORM,
                    pESPDU.getEntityOrientation().getPsi(),
                    pESPDU.getEntityOrientation().getTheta(),
                    pESPDU.getEntityOrientation().getPhi());
        }

        Vector3D vDistECS1 = Vector3D.ZERO;
        Vector3D aDistECS1 = Vector3D.ZERO;

        if (rotating) {

            // Compute initial ECS to final ECS rotation matrix
            RealVector wVec = MatrixUtils.createRealVector(new double[]
                    { rotRateRoll, rotRatePitch, rotRateYaw });

            RealMatrix wOut = wVec.outerProduct(wVec);
            RealMatrix wMat = MatrixUtils.createRealMatrix(new double[][]
                    { { 0.0, -rotRateYaw, rotRatePitch },
                { rotRateYaw, 0.0, -rotRateRoll },
                { -rotRatePitch, rotRateRoll, 0.0 } });

            double wMagSq = wVec.dotProduct(wVec);
            double wMag = Math.sqrt(wMagSq);
            double wMagT = wMag * deltaTime;
            double cosWMagT = Math.cos(wMagT);
            double term1 = (1.0 - cosWMagT) / wMagSq;
            double sinWMagT = Math.sin(wMagT);
            double term3 = sinWMagT / wMag;

            RealMatrix ECS1toECS2Mat = wOut.scalarMultiply(term1)
                    .add(MatrixUtils.createRealIdentityMatrix(3).scalarMultiply(cosWMagT))
                    .subtract(wMat.scalarMultiply(term3));
            if (bodyCoords) {
                // Compute the first integral of the final ECS to initial
                //   ECS rotation matrix for use with velocity
                double wMag3 = wMagSq * wMag;
                double vIntTerm1 = (wMagT - sinWMagT) / wMag3;
                // Terms 2&3 of this matrix calc are the same as terms 3&1 of
                // earlier initial to final ECS matrix calcs so we'll reuse
                RealMatrix vIntECS2toECS1Mat = wOut.scalarMultiply(vIntTerm1)
                        .add(MatrixUtils.createRealIdentityMatrix(3).scalarMultiply(term3))
                        .add(wMat.scalarMultiply(term1));

                // Compute movement in initial entity coordinates due
                // to initial velocity (ignoring acceleration)
                // vDistECS1 = vIntECS2toECS1Mat * velocity, a little hard to read due to type changes...
                vDistECS1 = new Vector3D(vIntECS2toECS1Mat.operate(
                        MatrixUtils.createRealVector(velocity.toArray())).toArray());

                System.out.println("vIntTerm1:" + vIntTerm1);
                System.out.println("term3:" + term3);
                System.out.println("term1:" + term1);

                System.out.println("vIntECS2toECS1Mat:");
                for (int i = 0; i < 3; i++) {
                    System.out.println(vIntECS2toECS1Mat.getEntry(i, 0) + " " + vIntECS2toECS1Mat.getEntry(i, 1) + " " + vIntECS2toECS1Mat.getEntry(i, 2));
                }

                if (accelerating) {
                    // Compute first integral of the final ECS to initial ECS
                    //   rotation matrix times time for use with acceleration
                    double wMag4 = wMag3 * wMag ;
                    double aIntTerm2Top = cosWMagT + (wMagT*sinWMagT) - 1.0 ;
                    double aIntTerm1 = ( (0.5*wMagT*wMagT) - aIntTerm2Top ) / wMag4 ;
                    double aIntTerm2 = aIntTerm2Top / wMagSq ;
                    double aIntTerm3 = ( sinWMagT - (wMagT*cosWMagT) ) / wMag3 ;

                    RealMatrix aIntECS2toECS1Mat = wOut.scalarMultiply(aIntTerm1)
                            .add(MatrixUtils.createRealIdentityMatrix(3).scalarMultiply(aIntTerm2))
                            .add(wMat.scalarMultiply(aIntTerm3));

                    // Compute movement in initial entity coordinates due
                    //    to acceleration (ignoring initial velocity)
                    // aDistECS1 = aIntECS2toECS1Mat * acceleration, a little hard to read due to type changes...
                    aDistECS1 = new Vector3D(aIntECS2toECS1Mat.operate(
                            MatrixUtils.createRealVector(acceleration.toArray())).toArray());

                    //                    System.out.println("aIntECS2toECS1Mat:");
                    //                    for (int i = 0; i < 3; i++) {
                    //                        System.out.println(aIntECS2toECS1Mat.getEntry(i, 0) + " " + aIntECS2toECS1Mat.getEntry(i, 1) + " " + aIntECS2toECS1Mat.getEntry(i, 2));
                    //                    }

                }
            }

            // Compute final Euler angles

            Rotation WCStoECS2Mat = new Rotation(ECS1toECS2Mat.getData(), 1e-15)
                    .applyTo(WCStoECS1Mat);

            double[] eulerAngles = WCStoECS2Mat.getAngles(RotationOrder.ZYX, RotationConvention.FRAME_TRANSFORM);
            // Update ESPDU Euler angle values
            pESPDU.getEntityOrientation().setPsi((float)   eulerAngles[0]);
            pESPDU.getEntityOrientation().setTheta((float) eulerAngles[1]);
            pESPDU.getEntityOrientation().setPhi((float)   eulerAngles[2]);

            // end of compute final Euler angles
        }  // if (rotating)

        // Compute final velocity and position
        if (bodyCoords) {  // velocity/acceleration in ECS coords
            if (!rotating) {
                // Compute movement in initial entity coordinates due
                //    to initial velocity (ignoring acceleration)
                vDistECS1 = velocity.scalarMultiply(deltaTime);
            }
            if( accelerating )
            {
                // Update ESPDU velocity values in final ECS coords
                velocity = velocity.add(deltaTime, acceleration);

                if (!rotating) {
                    // Compute movement in initial entity coordinates due
                    //    to acceleration (ignoring initial velocity)
                    aDistECS1 = acceleration.scalarMultiply(0.5 * deltaTime * deltaTime);
                }
            }  // end if accelerating block

            // Compute the total movement in initial ECS coords
            Vector3D distECS1 = new Vector3D(vDistECS1.add(aDistECS1).toArray());

            // Convert the movement in initial ECS coords to update ESPDU
            //    location in WCS coords.
            // Use transposed WCStoECSIMat rather building an ECSItoWCSMat
            location = location.add(WCStoECS1Mat.applyInverseTo(distECS1));
        } else {  // velocity/acceleration in WCS coords
            if( accelerating )
            {
                // Compute final velocity vector in WCS coords
                Vector3D finalVelWCS = velocity.add(deltaTime, acceleration);

                // Update ESPDU location in WCS coords
                location = location.add(0.5 * deltaTime, velocity.add(finalVelWCS));

                // Update ESPDU velocity values
                velocity = finalVelWCS;
            }
            else // not accelerating, use first-order linear projection
            {
                location = location.add(deltaTime, velocity);
            }
        }

        // Store location, velocity, and acceleration back in ESPDU object
        pESPDU.getEntityLocation().setX(location.getX());
        pESPDU.getEntityLocation().setY(location.getY());
        pESPDU.getEntityLocation().setZ(location.getZ());

        pESPDU.getEntityLinearVelocity().setX((float) velocity.getX());
        pESPDU.getEntityLinearVelocity().setY((float) velocity.getY());
        pESPDU.getEntityLinearVelocity().setZ((float) velocity.getZ());

        pESPDU.getDeadReckoningParameters().getEntityLinearAcceleration().setX((float) acceleration.getX());
        pESPDU.getDeadReckoningParameters().getEntityLinearAcceleration().setY((float) acceleration.getY());
        pESPDU.getDeadReckoningParameters().getEntityLinearAcceleration().setZ((float) acceleration.getZ());

        // Process articulation parameters
        // TODO: Write unit tests for the articulation parameters processing
        for (ArticulationParameter thisRate : pESPDU.getArticulationParameters()) { // update articulation parameter values
            if (thisRate.getParameterType() % 2 == 0) {  // found a rate (even type)
                // Search for the corresponding art. part value parameter
                for (ArticulationParameter thisVal : pESPDU.getArticulationParameters()) {
                    if (thisVal.getParameterType() == thisRate.getParameterType() - 1 &&
                            thisVal.getPartAttachedTo() == thisRate.getPartAttachedTo()) {  // found part's value
                        // update values based on type
                        switch (thisVal.getParameterType() % 32) {
                        case 1:   // position
                        case 3:  // extension
                            thisVal.setParameterValue(thisVal.getParameterValue() +
                                    thisRate.getParameterValue() * deltaTime);
                            if (thisVal.getParameterValue() > 1.0) {  // fully extended
                                thisVal.setParameterValue(1.0);
                            } else if (thisVal.getParameterValue() < 0.0) {  // fully retracted
                                thisVal.setParameterValue(0.0);
                            }
                            break;
                        case 5:  // x
                        case 7:  // y
                        case 9:  // z
                            thisVal.setParameterValue(thisVal.getParameterValue() +
                                    thisRate.getParameterValue() * deltaTime);
                            break;
                        case 11:  // azimuth
                        case 13:  // elevation
                        case 15:  // elevation
                            thisVal.setParameterValue(thisVal.getParameterValue() +
                                    thisRate.getParameterValue() * deltaTime);
                            if (thisVal.getParameterValue() > Math.PI) {  // wrapping over 180 degrees
                                thisVal.setParameterValue(thisVal.getParameterValue() - 2.0 * Math.PI);
                            } else if (thisVal.getParameterValue() < -Math.PI) {  // wrapping under 180 degrees
                                thisVal.setParameterValue(thisVal.getParameterValue() + 2.0 * Math.PI);
                            }
                            break;
                        default:
                            throw new IllegalArgumentException();  // Fail Fast LOL
                            // break;  // Unreachable code
                        }
                        break;  // exit the value search loop
                    }  // end of found the corresponding value
                }  // end value search
            }  // end of found a rate 
        }  // end update articulation parameter values

    } // end of perform_DR

}
