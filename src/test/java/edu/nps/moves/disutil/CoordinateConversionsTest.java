package edu.nps.moves.disutil;

import org.junit.Assert;
import org.junit.Test;

public class CoordinateConversionsTest {

    final double DELTA = 0.1; // acceptable amount of error.

    double[] xyz = {4153622.0304340417, -4153622.030434041, -2476719.3300588187};
    double[] latlonheightDegrees = {-23, -45, 0};
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
