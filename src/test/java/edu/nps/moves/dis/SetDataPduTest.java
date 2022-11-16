package edu.nps.moves.dis;

import edu.nps.moves.disutil.PduFileLoader;
import edu.nps.moves.disutil.PduFactory;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class SetDataPduTest {

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("SetDataPdu-vbs-script-cmd.raw"));

        // Expected field values were determined from Wireshark: Decode As -> DIS.
        assertEquals(6, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(19, pdu.getPduType());
        assertEquals(5, pdu.getProtocolFamily());
        assertEquals(0, pdu.getTimestamp());
        assertEquals(56, pdu.getLength());
        assertEquals(0, pdu.getPadding());

        SetDataPdu sdpdu = (SetDataPdu) pdu;

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
        assertEquals(64, vd.getVariableDatumLength());

        byte[] datumValue = vd.getVariableData();
        assertEquals("allunits", new String(datumValue));
    }
    
    @Test
    public void unmarshal_multi_variable_datums() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(PduFileLoader.load("SetDataPdu-multi-variable-datums.raw"));
        
        SetDataPdu sdpdu = (SetDataPdu) pdu;
        assertEquals(3, sdpdu.getVariableDatums().size());
    }

    @Test
    public void marshal() {
        SetDataPdu sdpdu = new SetDataPdu();

        byte[] buffer = sdpdu.marshal();

        assertEquals(buffer.length, sdpdu.getLength());
    }
}
