package edu.nps.moves.disutil;

import org.junit.Assert;
import org.junit.Test;

public class CoordinateConversionsTest {

    final double DELTA = 0.1; // acceptable amount of error

    double[] xyz = {1071401.9738714, -4450059.11156203, 4427050.83344532};
    double[] latlonheightDegrees = {44.2368804, -76.4630307, 91};
    double[] latlonheightRadians;

    public CoordinateConversionsTest() {
        latlonheightRadians = latlonheightDegrees.clone();
        latlonheightRadians[0] *= CoordinateConversions.DEGREES_TO_RADIANS;
        latlonheightRadians[1] *= CoordinateConversions.DEGREES_TO_RADIANS;
    }

    @Test
    public void xyzToLatLonRadiansTest() {
        Assert.assertArrayEquals(latlonheightRadians, CoordinateConversions.xyzToLatLonRadians(xyz), DELTA);
    }

    @Test
    public void xyzToLatLonDegreesTest() {
        Assert.assertArrayEquals(latlonheightDegrees, CoordinateConversions.xyzToLatLonDegrees(xyz), DELTA);
    }

    @Test
    public void getXYZfromLatLonDegreesTest() {
        Assert.assertArrayEquals(xyz, CoordinateConversions.getXYZfromLatLonDegrees(latlonheightDegrees[0], latlonheightDegrees[1], latlonheightDegrees[2]), DELTA);
    }

    @Test
    public void getXYZfromLatLonRadiansTest() {
        Assert.assertArrayEquals(xyz, CoordinateConversions.getXYZfromLatLonRadians(latlonheightRadians[0], latlonheightRadians[1], latlonheightRadians[2]), DELTA);
    }
}
