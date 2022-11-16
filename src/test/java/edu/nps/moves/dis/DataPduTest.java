package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFileLoader;
import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataPduTest {

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("DataPdu-vbs-script-cmd.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        assertEquals(6, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(20, pdu.getPduType());
        assertEquals(5, pdu.getProtocolFamily());
        assertEquals(0, pdu.getTimestamp());
        assertEquals(296, pdu.getLength());
        assertEquals(0, pdu.getPadding());

        DataPdu sdpdu = (DataPdu) pdu;

        assertEquals(0, sdpdu.getOriginatingEntityID().getSite());
        assertEquals(0, sdpdu.getOriginatingEntityID().getApplication());
        assertEquals(0, sdpdu.getOriginatingEntityID().getEntity());

        assertEquals(0, sdpdu.getReceivingEntityID().getSite());
        assertEquals(0, sdpdu.getReceivingEntityID().getApplication());
        assertEquals(0, sdpdu.getReceivingEntityID().getEntity());

        assertEquals(9, sdpdu.getRequestID());

        assertEquals(0, sdpdu.getPadding());

        assertEquals(0, sdpdu.getNumberOfFixedDatumRecords());
        assertEquals(1, sdpdu.getNumberOfVariableDatumRecords());

        VariableDatum vd = sdpdu.getVariableDatums().remove(0);
        assertEquals(1, vd.getVariableDatumID());
        assertEquals(1976, vd.getVariableDatumLength());

        byte[] datumValue = vd.getVariableData();
        assertEquals("[UAV2_PAYLOAD,UAV2_PLANNER,unit_7,UAV1_PLANNER,UAV1_PAYLOAD,unit_3,B 1-1-C-4:1,B 1-1-C-4:2,B 1-1-C-4:3,B 1-1-C-5:1,B 1-1-C-5:2,B 1-1-C-5:3,B 1-1-C-6:1,B 1-1-C-6:2,B 1-1-C-6:3,B 1-1-A-4:1,B 1-1-A-9:1,B 1-1-A-5:1,B 1-1-B-4:1,B 1-1-A-6:1,B 1-1-A-6:2]", new String(datumValue));
    }

    @Test
    public void marshal() {
        SetDataPdu sdpdu = new SetDataPdu();

        byte[] buffer = sdpdu.marshal();

        assertEquals(buffer.length, sdpdu.getLength());
    }
}
