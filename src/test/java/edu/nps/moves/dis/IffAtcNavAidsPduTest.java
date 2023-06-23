package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fo-ifad
 */
public class IffAtcNavAidsPduTest {

    public IffAtcNavAidsPduTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("IFF_ATC_NAVAIDS_PDU.raw"));

        // Header
        assertEquals(6, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(28, pdu.getPduType());
        assertEquals(6, pdu.getProtocolFamily());
        //TODO assertEquals((long) ???, aPdu.getTimestamp());
        assertEquals(60, pdu.getLength());
        assertEquals(0, pdu.getPadding());

        IffAtcNavAidsLayer1Pdu iffPdu = (IffAtcNavAidsLayer1Pdu) pdu;
        //IFF PDU
        //Emitting entityid
        assertEquals(1, iffPdu.getEmittingEntityId().getSite());
        assertEquals(3001, iffPdu.getEmittingEntityId().getApplication());
        assertEquals(17, iffPdu.getEmittingEntityId().getEntity());
        //Event id
        assertEquals(1, iffPdu.getEventID().getSite());
        assertEquals(3001, iffPdu.getEventID().getApplication());
        assertEquals(2, iffPdu.getEventID().getEventNumber());
        //Location with respect to entity
        assertEquals(0f, iffPdu.getLocation().getX(), 0.001);
        assertEquals(0f, iffPdu.getLocation().getY(), 0.001);
        assertEquals(0f, iffPdu.getLocation().getZ(), 0.001);
        //System ID
        assertEquals(1, iffPdu.getSystemID().getSystemType());
        assertEquals(7, iffPdu.getSystemID().getSystemName());
        assertEquals(0, iffPdu.getSystemID().getSystemMode());
        //Fundamental operational status
        assertEquals(0x01, iffPdu.getFundamentalData().getSystemStatus());
        assertEquals(1, iffPdu.getFundamentalData().getAlternateParameter4());
        assertEquals(2, iffPdu.getFundamentalData().getInformationLayers());
        assertEquals(14, iffPdu.getFundamentalData().getModifier());
        assertEquals(8255, iffPdu.getFundamentalData().getParameter1());
        assertEquals(12287, iffPdu.getFundamentalData().getParameter2());
        assertEquals(12287, iffPdu.getFundamentalData().getParameter3());
        assertEquals(23, iffPdu.getFundamentalData().getParameter4());
        assertEquals(12287, iffPdu.getFundamentalData().getParameter5());
        assertEquals(8192, iffPdu.getFundamentalData().getParameter6());

    }
}
