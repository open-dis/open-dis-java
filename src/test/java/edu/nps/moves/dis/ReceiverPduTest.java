package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFileLoader;
import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReceiverPduTest {

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("ReceiverPdu.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        assertEquals(6, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(27, pdu.getPduType());
        assertEquals(4, pdu.getProtocolFamily());
        //FIXME is timestamp wrong? assertEquals((long) 0, aPdu.getTimestamp());
        assertEquals(36, pdu.getLength());
        assertEquals(0, pdu.getPadding());

        ReceiverPdu rpdu = (ReceiverPdu) pdu;

        assertEquals(1, rpdu.getEntityId().getSite());
        assertEquals(1, rpdu.getEntityId().getApplication());
        assertEquals(0, rpdu.getEntityId().getEntity());

        assertEquals(2, rpdu.getRadioId());

        assertEquals(2, rpdu.getReceiverState());
        assertEquals(0, rpdu.getReceivedPower(), 0);

        assertEquals(0, rpdu.getPadding());

        assertEquals(99, rpdu.getTransmitterRadioId());
        assertEquals(999, rpdu.getTransmitterEntityId().getEntity());
        assertEquals(1, rpdu.getTransmitterEntityId().getSite());
        assertEquals(1, rpdu.getTransmitterEntityId().getApplication());
    }

    @Test
    public void marshal() {
        ReceiverPdu rpdu = new ReceiverPdu();

        byte[] buffer = rpdu.marshal();

        assertEquals(buffer.length, rpdu.getLength());
    }
}
