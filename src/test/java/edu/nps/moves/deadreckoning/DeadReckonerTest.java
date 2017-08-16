package edu.nps.moves.deadreckoning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.nps.moves.dis.EntityStatePdu;
import edu.nps.moves.disenum.DeadReckoningAlgorithm;

public class DeadReckonerTest {

    EntityStatePdu espdu4, espdu8;
    private static final int FRAMES_PER_SECOND = 2;
    private static final double DELTA_TIME = 1.0 / (double) FRAMES_PER_SECOND;
    private static final long TIMESTAMP_TOLERANCE = 2;
    private static double DELTA_DOUBLE = 2.0e-3;
    private static float DELTA_FLOAT = 2.0e-3f;

    /*
     * A helper function to make it easier to test DIS_DeadReckoning et al.
     */
    static double[] toAllDis(EntityStatePdu entityStatePdu) {

        double[] allDis = new double[15];

        allDis[0]  = entityStatePdu.getEntityLocation().getX(); 
        allDis[1]  = entityStatePdu.getEntityLocation().getY(); 
        allDis[2]  = entityStatePdu.getEntityLocation().getZ(); 

        allDis[3]  = entityStatePdu.getEntityOrientation().getPsi();
        allDis[4]  = entityStatePdu.getEntityOrientation().getTheta();
        allDis[5]  = entityStatePdu.getEntityOrientation().getPhi();

        allDis[6]  = entityStatePdu.getEntityLinearVelocity().getX();
        allDis[7]  = entityStatePdu.getEntityLinearVelocity().getY();
        allDis[8]  = entityStatePdu.getEntityLinearVelocity().getZ();

        allDis[9]  = entityStatePdu.getDeadReckoningParameters().getEntityLinearAcceleration().getX();
        allDis[10] = entityStatePdu.getDeadReckoningParameters().getEntityLinearAcceleration().getY();
        allDis[11] = entityStatePdu.getDeadReckoningParameters().getEntityLinearAcceleration().getZ();

        allDis[12] = entityStatePdu.getDeadReckoningParameters().getEntityAngularVelocity().getX();
        allDis[13] = entityStatePdu.getDeadReckoningParameters().getEntityAngularVelocity().getY();
        allDis[14] = entityStatePdu.getDeadReckoningParameters().getEntityAngularVelocity().getZ();

        return allDis;
    }

    /*
     * Another helper function to make it easier to test DIS_DeadReckoning et al.
     */
    static void fromNewLocVelTimestamp(double[] newLoc, double[] newVel, long timestamp, EntityStatePdu entityStatePdu) {
        entityStatePdu.getEntityLocation().setX(newLoc[0]); 
        entityStatePdu.getEntityLocation().setY(newLoc[1]); 
        entityStatePdu.getEntityLocation().setZ(newLoc[2]); 

        entityStatePdu.getEntityOrientation().setPsi(  (float) newLoc[3]);
        entityStatePdu.getEntityOrientation().setTheta((float) newLoc[4]);
        entityStatePdu.getEntityOrientation().setPhi(  (float) newLoc[5]);

        entityStatePdu.getEntityLinearVelocity().setX((float) newVel[0]);
        entityStatePdu.getEntityLinearVelocity().setY((float) newVel[1]);
        entityStatePdu.getEntityLinearVelocity().setZ((float) newVel[2]);

        entityStatePdu.setTimestamp(timestamp);
    }

    @Before
    public void setUp() throws Exception {
        espdu4 = new EntityStatePdu();
        espdu4.getEntityLocation().setX(-6378137.0);
        espdu4.getEntityLocation().setY(-5.0);
        espdu4.getEntityLocation().setZ(-10.0);
        espdu4.getEntityLinearVelocity().setX(30.0f);
        espdu4.getEntityLinearVelocity().setY(35.0f);
        espdu4.getEntityLinearVelocity().setZ(40.0f);
        espdu4.getDeadReckoningParameters().getEntityLinearAcceleration().setX(-80.0f);
        espdu4.getDeadReckoningParameters().getEntityLinearAcceleration().setY(-85.0f);
        espdu4.getDeadReckoningParameters().getEntityLinearAcceleration().setZ(-90.0f);
        espdu4.getEntityOrientation().setPsi((float) (15.0 * Math.PI / 180.0));
        espdu4.getEntityOrientation().setTheta((float) (20.0 * Math.PI / 180.0));
        espdu4.getEntityOrientation().setPhi((float) (25.0 * Math.PI / 180.0));
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setX((float) (60.0 * Math.PI / 180.0));
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setY((float) (65.0 * Math.PI / 180.0));
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setZ((float) (70.0 * Math.PI / 180.0));
        espdu4.setTimestamp(0x1150C836);

        espdu8 = new EntityStatePdu();
        espdu8.getEntityLocation().setX(6378137.0);
        espdu8.getEntityLocation().setY(5.0);
        espdu8.getEntityLocation().setZ(10.0);
        espdu8.getEntityLinearVelocity().setX(30.0f);
        espdu8.getEntityLinearVelocity().setY(35.0f);
        espdu8.getEntityLinearVelocity().setZ(40.0f);
        espdu8.getDeadReckoningParameters().getEntityLinearAcceleration().setX(-80.0f);
        espdu8.getDeadReckoningParameters().getEntityLinearAcceleration().setY(-85.0f);
        espdu8.getDeadReckoningParameters().getEntityLinearAcceleration().setZ(-90.0f);
        espdu8.getEntityOrientation().setPsi((float) (15.0 * Math.PI / 180.0));
        espdu8.getEntityOrientation().setTheta((float) (20.0 * Math.PI / 180.0));
        espdu8.getEntityOrientation().setPhi((float) (25.0 * Math.PI / 180.0));
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setX((float) (-60.0 * Math.PI / 180.0));
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setY((float) (-65.0 * Math.PI / 180.0));
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setZ((float) (-70.0 * Math.PI / 180.0));
        espdu8.setTimestamp(0x12740D76);
    }

    /*
     * The Towers & Hines papers only contain results for DR4 and DR8,
     * but it's pretty easy to figure out what the results should be
     * for special cases of these algorithms.
     * DR2, DR3, and DR5 are special cases of DR4;
     * DR6, DR7, and DR9 are special cases of DR8
     * DR1 is a special case of both.
     * 
     * The timestamps are a little finicky, especially when taking half setps,
     * so we allow some tolerance there.
     */

    void answers1B(EntityStatePdu espdu4) {
        assertEquals(-6378137.0, espdu4.getEntityLocation().getX(), 0.01);
        assertEquals(-5.0, espdu4.getEntityLocation().getY(), 0.01);
        assertEquals(-10.0, espdu4.getEntityLocation().getZ(), 0.01);
        assertEquals(15.0, espdu4.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(20.0, espdu4.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(25.0, espdu4.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(30.0f, espdu4.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(35.0f, espdu4.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(40.0f, espdu4.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x1159E260 - espdu4.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    void answers2(EntityStatePdu espdu4) {
        assertEquals(-6378122.0, espdu4.getEntityLocation().getX(), 0.01);
        assertEquals(12.5, espdu4.getEntityLocation().getY(), 0.01);
        assertEquals(10.0, espdu4.getEntityLocation().getZ(), 0.01);
        assertEquals(15.0, espdu4.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(20.0, espdu4.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(25.0, espdu4.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(30.0f, espdu4.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(35.0f, espdu4.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(40.0f, espdu4.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x1159E260 - espdu4.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    void answers3(EntityStatePdu espdu4) {
        assertEquals(-6378122.0, espdu4.getEntityLocation().getX(), 0.01);
        assertEquals(12.5, espdu4.getEntityLocation().getY(), 0.01);
        assertEquals(10.0, espdu4.getEntityLocation().getZ(), 0.01);
        assertEquals(64.32, espdu4.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(14.84, espdu4.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(72.5, espdu4.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(30.0f, espdu4.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(35.0f, espdu4.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(40.0f, espdu4.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x1159E260 - espdu4.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    void answers4(EntityStatePdu espdu4) {
        assertEquals(-6378132.0, espdu4.getEntityLocation().getX(), 0.01);
        assertEquals(1.88, espdu4.getEntityLocation().getY(), 0.01);
        assertEquals(-1.25, espdu4.getEntityLocation().getZ(), 0.01);
        assertEquals(64.32, espdu4.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(14.84, espdu4.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(72.5, espdu4.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(-10.0, espdu4.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(-7.5, espdu4.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(-5.0, espdu4.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x1159E260 - espdu4.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    void answers5(EntityStatePdu espdu4) {
        assertEquals(-6378132.0, espdu4.getEntityLocation().getX(), 0.01);
        assertEquals(1.88, espdu4.getEntityLocation().getY(), 0.01);
        assertEquals(-1.25, espdu4.getEntityLocation().getZ(), 0.01);
        assertEquals(15.0, espdu4.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(20.0, espdu4.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(25.0, espdu4.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(-10.0, espdu4.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(-7.5, espdu4.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(-5.0, espdu4.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x1159E260 - espdu4.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    void answers1W(EntityStatePdu espdu8) {
        assertEquals(6378137.0, espdu8.getEntityLocation().getX(), 0.01);
        assertEquals(5.0, espdu8.getEntityLocation().getY(), 0.01);
        assertEquals(10.0, espdu8.getEntityLocation().getZ(), 0.01);
        assertEquals(15.0, espdu8.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(20.0, espdu8.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(25.0, espdu8.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(30.0f, espdu8.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(35.0f, espdu8.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(40.0f, espdu8.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x127D27A0 - espdu8.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    void answers6(EntityStatePdu espdu8) {
        assertEquals(6378157.13, espdu8.getEntityLocation().getX(), 0.01);
        assertEquals(18.06, espdu8.getEntityLocation().getY(), 0.01);
        assertEquals(28.85, espdu8.getEntityLocation().getZ(), 0.01);
        assertEquals(15.0, espdu8.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(20.0, espdu8.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(25.0, espdu8.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(30.0f, espdu8.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(35.0f, espdu8.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(40.0f, espdu8.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x127D27A0 - espdu8.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    void answers7(EntityStatePdu espdu8) {
        assertEquals(6378156.77, espdu8.getEntityLocation().getX(), 0.01);
        assertEquals(18.75, espdu8.getEntityLocation().getY(), 0.01);
        assertEquals(28.73, espdu8.getEntityLocation().getZ(), 0.01);
        assertEquals(-23.07, espdu8.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(-8.68, espdu8.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(-10.86, espdu8.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(30.0f, espdu8.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(35.0f, espdu8.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(40.0f, espdu8.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x127D27A0 - espdu8.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    void answers8(EntityStatePdu espdu8) {
        assertEquals(6378144.03, espdu8.getEntityLocation().getX(), 0.01);
        assertEquals(10.46, espdu8.getEntityLocation().getY(), 0.01);
        assertEquals(18.32, espdu8.getEntityLocation().getZ(), 0.01);
        assertEquals(-23.07, espdu8.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(-8.68, espdu8.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(-10.86, espdu8.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(-10.0, espdu8.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(-7.5, espdu8.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(-5.0, espdu8.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x127D27A0 - espdu8.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    void answers9(EntityStatePdu espdu8) {
        assertEquals(6378144.46, espdu8.getEntityLocation().getX(), 0.01);
        assertEquals(9.62, espdu8.getEntityLocation().getY(), 0.01);
        assertEquals(18.47, espdu8.getEntityLocation().getZ(), 0.01);
        assertEquals(15.0, espdu8.getEntityOrientation().getPsi() * 180.0 / Math.PI, 0.01);
        assertEquals(20.0, espdu8.getEntityOrientation().getTheta() * 180.0 / Math.PI, 0.01);
        assertEquals(25.0, espdu8.getEntityOrientation().getPhi() * 180.0 / Math.PI, 0.01);
        assertEquals(-10.0f, espdu8.getEntityLinearVelocity().getX(), 0.01);
        assertEquals(-7.5f, espdu8.getEntityLinearVelocity().getY(), 0.01);
        assertEquals(-5.0f, espdu8.getEntityLinearVelocity().getZ(), 0.01);
        assertTrue(Math.abs(0x127D27A0 - espdu8.getTimestamp()) <= TIMESTAMP_TOLERANCE);
    }

    /*
     * Note that these tests only change the deadReckoningAlgorithm field,
     * e.g. if we choose an algorithm that does not handle rotation
     * we do not explicitly zero out the angularVelocity fields. 
     */
    @Test
    public void testPerform_DR1B() {
        espdu4.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.STATIC_ENTITY_DOES_NOT_MOVE.getValue());
        DeadReckoner.perform_DR(espdu4, DELTA_TIME);
        answers1B(espdu4);
    }

    @Test
    public void testPerform_DR2() {
        espdu4.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMF_P_W.getValue());
        DeadReckoner.perform_DR(espdu4, DELTA_TIME);
        answers2(espdu4);
    }

    @Test
    public void testPerform_DR3() {
        espdu4.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_P_W.getValue());
        DeadReckoner.perform_DR(espdu4, DELTA_TIME);
        answers3(espdu4);
    }

    @Test
    public void testPerform_DR4() {
        espdu4.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_V_W.getValue());
        DeadReckoner.perform_DR(espdu4, DELTA_TIME);
        answers4(espdu4);
    }

    /*
     * There was a bug in the DIS_DeadReckoning abstract classes with
     * acceleration and multiple time steps.  I added regression cases
     * for both the old DIS_DeadReckoning and new DeadReckoner classes.
     */

    @Test
    public void testPerform_DR4halfStep() {
        espdu4.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_V_W.getValue());
        DeadReckoner.perform_DR(espdu4, DELTA_TIME / 2.0);
        DeadReckoner.perform_DR(espdu4, DELTA_TIME / 2.0);
        answers4(espdu4);
    }

    @Test
    public void testPerform_DR5() {
        espdu4.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMF_V_W.getValue());
        DeadReckoner.perform_DR(espdu4, DELTA_TIME);
        answers5(espdu4);
    }

    @Test
    public void testPerform_DR5halfStep() {
        espdu4.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMF_V_W.getValue());
        DeadReckoner.perform_DR(espdu4, DELTA_TIME / 2.0);
        DeadReckoner.perform_DR(espdu4, DELTA_TIME / 2.0);
        answers5(espdu4);
    }

    @Test
    public void testPerform_DR1W() {
        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.STATIC_ENTITY_DOES_NOT_MOVE.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME);
        answers1W(espdu8);
    }

    @Test
    public void testPerform_DR6() {
        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMF_P_B.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME);
        answers6(espdu8);
    }

    @Test
    public void testPerform_DR7() {
        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_P_B.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME);
        answers7(espdu8);
    }

    @Test
    public void testPerform_DR7halfStep() {
        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_P_B.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME / 2.0);
        DeadReckoner.perform_DR(espdu8, DELTA_TIME / 2.0);
        answers7(espdu8);
    }

    @Test
    public void testPerform_DR8() {
        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_V_B.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME);
        answers8(espdu8);
    }

    @Test
    public void testPerform_DR8halfStep() {
        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_V_B.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME / 2.0);
        DeadReckoner.perform_DR(espdu8, DELTA_TIME / 2.0);
        answers8(espdu8);
    }

    @Test
    public void testPerform_DR9() {
        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMF_V_B.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME);
        answers9(espdu8);
    }

    @Test
    public void testPerform_DR9halfStep() {
        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMF_V_B.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME / 2.0);
        DeadReckoner.perform_DR(espdu8, DELTA_TIME / 2.0);
        answers9(espdu8);
    }

    // DIS_DeadReckoning et al

    @Test
    public void testStatic01B() throws Exception {
        DIS_DR_Static_01 disDr = new DIS_DR_Static_01();
        disDr.setNewAll(toAllDis(espdu4));
        disDr.setInitTimestamp(espdu4.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu4);
        answers1B(espdu4);
    }

    @Test
    public void testFpw02() throws Exception {
        DIS_DR_FPW_02 disDr = new DIS_DR_FPW_02();
        disDr.setNewAll(toAllDis(espdu4));
        disDr.setInitTimestamp(espdu4.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu4);
        answers2(espdu4);
    }

    @Test
    public void testRpw03() throws Exception {
        DIS_DR_RPW_03 disDr = new DIS_DR_RPW_03();
        disDr.setNewAll(toAllDis(espdu4));
        disDr.setInitTimestamp(espdu4.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu4);
        answers3(espdu4);
    }

    @Test
    public void testRvw04() throws Exception {
        DIS_DR_RVW_04 disDr = new DIS_DR_RVW_04();
        disDr.setNewAll(toAllDis(espdu4));
        disDr.setInitTimestamp(espdu4.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu4);
        answers4(espdu4);
    }

    @Test
    public void testRvw04halfStep() throws Exception {
        DIS_DR_RVW_04 disDr = new DIS_DR_RVW_04();
        disDr.setNewAll(toAllDis(espdu4));
        disDr.setInitTimestamp(espdu4.getTimestamp());
        disDr.setFPS(2 * FRAMES_PER_SECOND);
        disDr.update();
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu4);
        answers4(espdu4);
    }

    // DIS_DR_RPW_03b and DIS_DR_RVW_04b do not follow IEEE Std 1278.1
    // and should be deprecated immediately!

    @Test
    public void testFvw05() throws Exception {
        DIS_DR_FVW_05 disDr = new DIS_DR_FVW_05();
        disDr.setNewAll(toAllDis(espdu4));
        disDr.setInitTimestamp(espdu4.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu4);
        answers5(espdu4);
    }

    @Test
    public void testFvw05halfStep() throws Exception {
        DIS_DR_FVW_05 disDr = new DIS_DR_FVW_05();
        disDr.setNewAll(toAllDis(espdu4));
        disDr.setInitTimestamp(espdu4.getTimestamp());
        disDr.setFPS(2 * FRAMES_PER_SECOND);
        disDr.update();
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu4);
        answers5(espdu4);
    }

    @Test
    public void testStatic01W() throws Exception {
        DIS_DR_Static_01 disDr = new DIS_DR_Static_01();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers1W(espdu8);
    }

    @Test
    public void testFpb06() throws Exception {
        DIS_DR_FPB_06 disDr = new DIS_DR_FPB_06();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers6(espdu8);
    }

    @Test
    public void testRpb07() throws Exception {
        DIS_DR_RPB_07 disDr = new DIS_DR_RPB_07();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers7(espdu8);
    }

    @Test
    public void testRpb07HalfStep() throws Exception {
        DIS_DR_RPB_07 disDr = new DIS_DR_RPB_07();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(2 * FRAMES_PER_SECOND);
        disDr.update();
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers7(espdu8);
    }

    @Test
    public void testRvb08() throws Exception {
        DIS_DR_RVB_08 disDr = new DIS_DR_RVB_08();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers8(espdu8);
    }

    @Test
    public void testRvb08halfStep() throws Exception {
        DIS_DR_RVB_08 disDr = new DIS_DR_RVB_08();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(2 * FRAMES_PER_SECOND);
        disDr.update();
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers8(espdu8);
    }

    @Test
    public void testFvb09() throws Exception {
        DIS_DR_FVB_09 disDr = new DIS_DR_FVB_09();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers9(espdu8);
    }

    @Test
    public void testFvb09halfStep() throws Exception {
        DIS_DR_FVB_09 disDr = new DIS_DR_FVB_09();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(2 * FRAMES_PER_SECOND);
        disDr.update();
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers9(espdu8);
    }

    @Test
    public void testCwCircle() {
        double gravity = 10.0;  // Rounded from 9.8 m/s^2
        double speed = 100.0;
        double radius = 5000.0;
        double centripetal = speed * speed / radius;
        double magRoll = Math.atan(centripetal / gravity);
        double period = 2.0 * Math.PI * radius / speed;
        
        double psi = Math.PI / 2.0;
        double theta = 0.0;
        double phi = magRoll;
        
        double dPsiDt = 2.0 * Math.PI / period;
        double dThetaDt = 0.0;
        double dPhiDt = 0.0;
        double[] bodyAngularVelocities = RotationUtils.world2bodyAngularVelocities(dPhiDt, dThetaDt, dPsiDt,
                phi, theta);
        
        assertTrue(bodyAngularVelocities[1] > 0.0);
        assertTrue(bodyAngularVelocities[2] > 0.0);
        
        EntityStatePdu cwCircle;
        cwCircle = new EntityStatePdu();
        cwCircle.getEntityLocation().setX(radius);
        cwCircle.getEntityLocation().setY(0.0);
        cwCircle.getEntityLocation().setZ(0.0);
        cwCircle.getEntityLinearVelocity().setX((float) speed);
        cwCircle.getEntityLinearVelocity().setY(0.0f);
        cwCircle.getEntityLinearVelocity().setZ(0.0f);
        cwCircle.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_P_B.getValue());
        cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().setX(0.0f);
        cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().setY(0.0f);
        cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().setZ(0.0f);
        cwCircle.getEntityOrientation().setPsi((float) psi);
        cwCircle.getEntityOrientation().setTheta((float) theta);
        cwCircle.getEntityOrientation().setPhi((float) phi);
        cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().setX((float) bodyAngularVelocities[0]);
        cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().setY((float) bodyAngularVelocities[1]);
        cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().setZ((float) bodyAngularVelocities[2]);
        cwCircle.setTimestamp(0);

        double[] worldAngularVelocities;
        
        DeadReckoner.perform_DR(cwCircle, period / 4.0);
        worldAngularVelocities = RotationUtils.body2worldAngularVelocities(
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getX(),
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getY(),
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getZ(),
                cwCircle.getEntityOrientation().getPhi(),
                cwCircle.getEntityOrientation().getTheta());

        assertEquals(0.0, cwCircle.getEntityLocation().getX(), DELTA_DOUBLE);
        assertEquals(radius, cwCircle.getEntityLocation().getY(), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityLocation().getZ(), DELTA_DOUBLE);
        assertEquals((float) speed, cwCircle.getEntityLinearVelocity().getX(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getEntityLinearVelocity().getY(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getEntityLinearVelocity().getZ(), DELTA_FLOAT);
        assertEquals((short)DeadReckoningAlgorithm.DRMR_P_B.getValue(), cwCircle.getDeadReckoningParameters().getDeadReckoningAlgorithm());
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getX(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getY(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getZ(), DELTA_FLOAT);
        assertEquals(Math.PI, Math.abs(cwCircle.getEntityOrientation().getPsi()), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityOrientation().getTheta(), DELTA_DOUBLE);
        assertEquals(magRoll, cwCircle.getEntityOrientation().getPhi(), DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[0], DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[1], DELTA_DOUBLE);
        assertEquals(dPsiDt, worldAngularVelocities[2], DELTA_DOUBLE);

        DeadReckoner.perform_DR(cwCircle, period / 4.0);
        worldAngularVelocities = RotationUtils.body2worldAngularVelocities(
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getX(),
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getY(),
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getZ(),
                cwCircle.getEntityOrientation().getPhi(),
                cwCircle.getEntityOrientation().getTheta());

        assertEquals(-radius, cwCircle.getEntityLocation().getX(), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityLocation().getY(), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityLocation().getZ(), DELTA_DOUBLE);
        assertEquals((float) speed, cwCircle.getEntityLinearVelocity().getX(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getEntityLinearVelocity().getY(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getEntityLinearVelocity().getZ(), DELTA_FLOAT);
        assertEquals((short)DeadReckoningAlgorithm.DRMR_P_B.getValue(), cwCircle.getDeadReckoningParameters().getDeadReckoningAlgorithm());
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getX(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getY(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getZ(), DELTA_FLOAT);
        assertEquals(-Math.PI / 2.0, cwCircle.getEntityOrientation().getPsi(), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityOrientation().getTheta(), DELTA_DOUBLE);
        assertEquals(magRoll, cwCircle.getEntityOrientation().getPhi(), DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[0], DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[1], DELTA_DOUBLE);
        assertEquals(dPsiDt, worldAngularVelocities[2], DELTA_DOUBLE);

        DeadReckoner.perform_DR(cwCircle, period / 4.0);
        worldAngularVelocities = RotationUtils.body2worldAngularVelocities(
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getX(),
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getY(),
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getZ(),
                cwCircle.getEntityOrientation().getPhi(),
                cwCircle.getEntityOrientation().getTheta());

        assertEquals(0.0, cwCircle.getEntityLocation().getX(), DELTA_DOUBLE);
        assertEquals(-radius, cwCircle.getEntityLocation().getY(), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityLocation().getZ(), DELTA_DOUBLE);
        assertEquals((float) speed, cwCircle.getEntityLinearVelocity().getX(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getEntityLinearVelocity().getY(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getEntityLinearVelocity().getZ(), DELTA_FLOAT);
        assertEquals((short)DeadReckoningAlgorithm.DRMR_P_B.getValue(), cwCircle.getDeadReckoningParameters().getDeadReckoningAlgorithm());
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getX(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getY(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getZ(), DELTA_FLOAT);
        assertEquals(0.0, Math.abs(cwCircle.getEntityOrientation().getPsi()), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityOrientation().getTheta(), DELTA_DOUBLE);
        assertEquals(magRoll, cwCircle.getEntityOrientation().getPhi(), DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[0], DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[1], DELTA_DOUBLE);
        assertEquals(dPsiDt, worldAngularVelocities[2], DELTA_DOUBLE);

        DeadReckoner.perform_DR(cwCircle, period / 4.0);
        worldAngularVelocities = RotationUtils.body2worldAngularVelocities(
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getX(),
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getY(),
                cwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getZ(),
                cwCircle.getEntityOrientation().getPhi(),
                cwCircle.getEntityOrientation().getTheta());

        assertEquals(radius, cwCircle.getEntityLocation().getX(), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityLocation().getY(), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityLocation().getZ(), DELTA_DOUBLE);
        assertEquals((float) speed, cwCircle.getEntityLinearVelocity().getX(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getEntityLinearVelocity().getY(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getEntityLinearVelocity().getZ(), DELTA_FLOAT);
        assertEquals((short)DeadReckoningAlgorithm.DRMR_P_B.getValue(), cwCircle.getDeadReckoningParameters().getDeadReckoningAlgorithm());
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getX(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getY(), DELTA_FLOAT);
        assertEquals(0.0f, cwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getZ(), DELTA_FLOAT);
        assertEquals(Math.PI / 2.0, cwCircle.getEntityOrientation().getPsi(), DELTA_DOUBLE);
        assertEquals(0.0, cwCircle.getEntityOrientation().getTheta(), DELTA_DOUBLE);
        assertEquals(magRoll, cwCircle.getEntityOrientation().getPhi(), DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[0], DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[1], DELTA_DOUBLE);
        assertEquals(dPsiDt, worldAngularVelocities[2], DELTA_DOUBLE);
    }
    
    @Test
    public void testCcwCircle() {
        double gravity = 10.0;  // Rounded from 9.8 m/s^2
        double speed = 200.0;
        double radius = 5000.0;
        double centripetal = speed * speed / radius;
        double magRoll = Math.atan(centripetal / gravity);
        double period = 2.0 * Math.PI * radius / speed;
        
        double psi = 0.0;
        double theta = 0.0;
        double phi = -magRoll;
        
        double dPsiDt = -2.0 * Math.PI / period;
        double dThetaDt = 0.0;
        double dPhiDt = 0.0;
        double[] bodyAngularVelocities = RotationUtils.world2bodyAngularVelocities(dPhiDt, dThetaDt, dPsiDt,
                phi, theta);
        
        assertTrue(bodyAngularVelocities[1] > 0.0);
        assertTrue(bodyAngularVelocities[2] < 0.0);
        
        EntityStatePdu ccwCircle;
        ccwCircle = new EntityStatePdu();
        ccwCircle.getEntityLocation().setX(0.0);
        ccwCircle.getEntityLocation().setY(radius);
        ccwCircle.getEntityLocation().setZ(0.0);
        ccwCircle.getEntityLinearVelocity().setX((float) speed);
        ccwCircle.getEntityLinearVelocity().setY(0.0f);
        ccwCircle.getEntityLinearVelocity().setZ(0.0f);
        ccwCircle.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_P_B.getValue());
        ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().setX(0.0f);
        ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().setY(0.0f);
        ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().setZ(0.0f);
        ccwCircle.getEntityOrientation().setPsi((float) psi);
        ccwCircle.getEntityOrientation().setTheta((float) theta);
        ccwCircle.getEntityOrientation().setPhi((float) phi);
        ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().setX((float) bodyAngularVelocities[0]);
        ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().setY((float) bodyAngularVelocities[1]);
        ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().setZ((float) bodyAngularVelocities[2]);
        ccwCircle.setTimestamp(0);

        double[] worldAngularVelocities;
        
        DeadReckoner.perform_DR(ccwCircle, period / 4.0);
        worldAngularVelocities = RotationUtils.body2worldAngularVelocities(
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getX(),
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getY(),
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getZ(),
                ccwCircle.getEntityOrientation().getPhi(),
                ccwCircle.getEntityOrientation().getTheta());

        assertEquals(radius, ccwCircle.getEntityLocation().getX(), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityLocation().getY(), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityLocation().getZ(), DELTA_DOUBLE);
        assertEquals((float) speed, ccwCircle.getEntityLinearVelocity().getX(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getEntityLinearVelocity().getY(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getEntityLinearVelocity().getZ(), DELTA_FLOAT);
        assertEquals((short)DeadReckoningAlgorithm.DRMR_P_B.getValue(), ccwCircle.getDeadReckoningParameters().getDeadReckoningAlgorithm());
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getX(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getY(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getZ(), DELTA_FLOAT);
        assertEquals(Math.PI / 2.0, Math.abs(ccwCircle.getEntityOrientation().getPsi()), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityOrientation().getTheta(), DELTA_DOUBLE);
        assertEquals(-magRoll, ccwCircle.getEntityOrientation().getPhi(), DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[0], DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[1], DELTA_DOUBLE);
        assertEquals(dPsiDt, worldAngularVelocities[2], DELTA_DOUBLE);

        DeadReckoner.perform_DR(ccwCircle, period / 4.0);
        worldAngularVelocities = RotationUtils.body2worldAngularVelocities(
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getX(),
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getY(),
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getZ(),
                ccwCircle.getEntityOrientation().getPhi(),
                ccwCircle.getEntityOrientation().getTheta());

        assertEquals(0.0, ccwCircle.getEntityLocation().getX(), DELTA_DOUBLE);
        assertEquals(-radius, ccwCircle.getEntityLocation().getY(), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityLocation().getZ(), DELTA_DOUBLE);
        assertEquals((float) speed, ccwCircle.getEntityLinearVelocity().getX(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getEntityLinearVelocity().getY(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getEntityLinearVelocity().getZ(), DELTA_FLOAT);
        assertEquals((short)DeadReckoningAlgorithm.DRMR_P_B.getValue(), ccwCircle.getDeadReckoningParameters().getDeadReckoningAlgorithm());
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getX(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getY(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getZ(), DELTA_FLOAT);
        assertEquals(Math.PI, Math.abs(ccwCircle.getEntityOrientation().getPsi()), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityOrientation().getTheta(), DELTA_DOUBLE);
        assertEquals(-magRoll, ccwCircle.getEntityOrientation().getPhi(), DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[0], DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[1], DELTA_DOUBLE);
        assertEquals(dPsiDt, worldAngularVelocities[2], DELTA_DOUBLE);

        DeadReckoner.perform_DR(ccwCircle, period / 4.0);
        worldAngularVelocities = RotationUtils.body2worldAngularVelocities(
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getX(),
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getY(),
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getZ(),
                ccwCircle.getEntityOrientation().getPhi(),
                ccwCircle.getEntityOrientation().getTheta());

        assertEquals(-radius, ccwCircle.getEntityLocation().getX(), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityLocation().getY(), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityLocation().getZ(), DELTA_DOUBLE);
        assertEquals((float) speed, ccwCircle.getEntityLinearVelocity().getX(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getEntityLinearVelocity().getY(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getEntityLinearVelocity().getZ(), DELTA_FLOAT);
        assertEquals((short)DeadReckoningAlgorithm.DRMR_P_B.getValue(), ccwCircle.getDeadReckoningParameters().getDeadReckoningAlgorithm());
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getX(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getY(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getZ(), DELTA_FLOAT);
        assertEquals(Math.PI / 2.0, Math.abs(ccwCircle.getEntityOrientation().getPsi()), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityOrientation().getTheta(), DELTA_DOUBLE);
        assertEquals(-magRoll, ccwCircle.getEntityOrientation().getPhi(), DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[0], DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[1], DELTA_DOUBLE);
        assertEquals(dPsiDt, worldAngularVelocities[2], DELTA_DOUBLE);

        DeadReckoner.perform_DR(ccwCircle, period / 4.0);
        worldAngularVelocities = RotationUtils.body2worldAngularVelocities(
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getX(),
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getY(),
                ccwCircle.getDeadReckoningParameters().getEntityAngularVelocity().getZ(),
                ccwCircle.getEntityOrientation().getPhi(),
                ccwCircle.getEntityOrientation().getTheta());

        assertEquals(0.0, ccwCircle.getEntityLocation().getX(), DELTA_DOUBLE);
        assertEquals(radius, ccwCircle.getEntityLocation().getY(), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityLocation().getZ(), DELTA_DOUBLE);
        assertEquals((float) speed, ccwCircle.getEntityLinearVelocity().getX(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getEntityLinearVelocity().getY(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getEntityLinearVelocity().getZ(), DELTA_FLOAT);
        assertEquals((short)DeadReckoningAlgorithm.DRMR_P_B.getValue(), ccwCircle.getDeadReckoningParameters().getDeadReckoningAlgorithm());
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getX(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getY(), DELTA_FLOAT);
        assertEquals(0.0f, ccwCircle.getDeadReckoningParameters().getEntityLinearAcceleration().getZ(), DELTA_FLOAT);
        assertEquals(0.0, ccwCircle.getEntityOrientation().getPsi(), DELTA_DOUBLE);
        assertEquals(0.0, ccwCircle.getEntityOrientation().getTheta(), DELTA_DOUBLE);
        assertEquals(-magRoll, ccwCircle.getEntityOrientation().getPhi(), DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[0], DELTA_DOUBLE);
        assertEquals(0.0, worldAngularVelocities[1], DELTA_DOUBLE);
        assertEquals(dPsiDt, worldAngularVelocities[2], DELTA_DOUBLE);
    }
    
    // zero angular velocity tests
    
    @Test
    public void testRpw03omega0() throws Exception {
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setX(0.0f);
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setY(0.0f);
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setZ(0.0f);

        DIS_DR_RPW_03 disDr = new DIS_DR_RPW_03();
        disDr.setNewAll(toAllDis(espdu4));
        disDr.setInitTimestamp(espdu4.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu4);
        answers2(espdu4);  // NOTE: With zero angular velocity, DR3 should get same results as DR2
    }
    
    @Test
    public void testRvw04omega0() throws Exception {
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setX(0.0f);
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setY(0.0f);
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setZ(0.0f);

        DIS_DR_RVW_04 disDr = new DIS_DR_RVW_04();
        disDr.setNewAll(toAllDis(espdu4));
        disDr.setInitTimestamp(espdu4.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu4);
        answers5(espdu4);  // NOTE: With zero angular velocity, DR4 should get same results as DR5
    }

    @Test
    public void testRpb07omega0() throws Exception {
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setX(0.0f);
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setY(0.0f);
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setZ(0.0f);

        DIS_DR_RPB_07 disDr = new DIS_DR_RPB_07();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers6(espdu8);  // NOTE: With zero angular velocity, DR7 should get same results as DR6
    }

    @Test
    public void testRvb08omega0() throws Exception {
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setX(0.0f);
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setY(0.0f);
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setZ(0.0f);

        DIS_DR_RVB_08 disDr = new DIS_DR_RVB_08();
        disDr.setNewAll(toAllDis(espdu8));
        disDr.setInitTimestamp(espdu8.getTimestamp());
        disDr.setFPS(FRAMES_PER_SECOND);
        disDr.update();
        double[] newLoc = disDr.getUpdatedPositionOrientation();
        double[] newVel = disDr.getUpdatedVelocity();
        long timestamp = disDr.getUpdatedTimestamp();
        fromNewLocVelTimestamp(newLoc, newVel, timestamp, espdu8);
        answers9(espdu8);  // NOTE: With zero angular velocity, DR8 should get same results as DR9
    }

    @Test
    public void testPerform_DR3omega0() {
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setX(0.0f);
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setY(0.0f);
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setZ(0.0f);

        espdu4.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_P_W.getValue());
        DeadReckoner.perform_DR(espdu4, DELTA_TIME);
        answers2(espdu4);  // NOTE: With zero angular velocity, DR3 should get same results as DR2
    }

    @Test
    public void testPerform_DR4omega0() {
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setX(0.0f);
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setY(0.0f);
        espdu4.getDeadReckoningParameters().getEntityAngularVelocity().setZ(0.0f);

        espdu4.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_V_W.getValue());
        DeadReckoner.perform_DR(espdu4, DELTA_TIME);
        answers5(espdu4);  // NOTE: With zero angular velocity, DR4 should get same results as DR5
    }

    @Test
    public void testPerform_DR7omega0() {
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setX(0.0f);
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setY(0.0f);
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setZ(0.0f);

        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_P_B.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME);
        answers6(espdu8);  // NOTE: With zero angular velocity, DR7 should get same results as DR6
    }

    @Test
    public void testPerform_DR8omega0() {
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setX(0.0f);
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setY(0.0f);
        espdu8.getDeadReckoningParameters().getEntityAngularVelocity().setZ(0.0f);

        espdu8.getDeadReckoningParameters().setDeadReckoningAlgorithm((short)DeadReckoningAlgorithm.DRMR_V_B.getValue());
        DeadReckoner.perform_DR(espdu8, DELTA_TIME);
        answers9(espdu8);  // NOTE: With zero angular velocity, DR8 should get same results as DR9
    }

}
