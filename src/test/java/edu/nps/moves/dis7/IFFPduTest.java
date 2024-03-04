package edu.nps.moves.dis7;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fo
 */
public class IFFPduTest {
    
    public IFFPduTest() {
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
    public void marshal() {
        IFFPdu ipdu = new IFFPdu();

        byte[] buffer = ipdu.marshal();

        assertEquals(buffer.length, ipdu.getLength());
    }

    @Test
    public void unmarshal() throws IOException {
        PduFactory factory = new PduFactory();
        Pdu pdu = factory.createPdu(edu.nps.moves.dis7.PduFileLoader.load("IFFPdu.raw"));

        // Header
        assertEquals(7, pdu.getProtocolVersion());
        assertEquals(1, pdu.getExerciseID());
        assertEquals(28, pdu.getPduType());
        assertEquals(6, pdu.getProtocolFamily());
        assertEquals(136, pdu.getLength());
        assertEquals(13, pdu.getPduStatus());
        assertEquals(0, pdu.getPadding());
        
        IFFPdu ipdu =(IFFPdu) pdu;
        


        //Layer1
        final IFFLayer1 layer1 = ipdu.getLayer1();
       
        //EmittingEntityId
        assertEquals(1, layer1.getEmittingEntityId().getSiteID());
        assertEquals(206, layer1.getEmittingEntityId().getApplicationID());
        assertEquals(55, layer1.getEmittingEntityId().getEntityID());
        
        //EventId
        assertEquals(1, layer1.getEventId().getSimulationAddress().getSite());
        assertEquals(206, layer1.getEventId().getSimulationAddress().getApplication());
        assertEquals(1, layer1.getEventId().getEventNumber());
        
        //RelativeAntennaLocation
        assertEquals(1, layer1.getRelativeAntennaLocation().getX(),0.001);
        assertEquals(1, layer1.getRelativeAntennaLocation().getY(),0.001);
        assertEquals(1, layer1.getRelativeAntennaLocation().getZ(),0.001);

        //SystemId
        assertEquals(1, layer1.getSystemId().getSystemType());
        assertEquals(1, layer1.getSystemId().getSystemName());
        assertEquals(3, layer1.getSystemId().getSystemMode());
        assertEquals(2, layer1.getSystemId().getChangeOptions().getValue());

        //SystemDesignator
        assertEquals(55, layer1.getSystemDesignator());

        //SystemSpecificData
        assertEquals(55, layer1.getSystemSpecificData());

        //FundamentalOperationalData

        //SystemStatus
        assertEquals(1, layer1.getFundamentalOperationalData().getSystemStatus());
        
        //DataField1
        assertEquals(0, layer1.getFundamentalOperationalData().getDataField1());
        
        //InformationLayers
        assertEquals(3, layer1.getFundamentalOperationalData().getInformationLayers());

        //DataField2
        assertEquals(14, layer1.getFundamentalOperationalData().getDataField2());
        
        //Parameter1
        assertEquals(57403, layer1.getFundamentalOperationalData().getParameter1());

        //Parameter1
        assertEquals(61439, layer1.getFundamentalOperationalData().getParameter2());

        //Parameter1
        assertEquals(61439, layer1.getFundamentalOperationalData().getParameter3());

        //Parameter1
        assertEquals(57389, layer1.getFundamentalOperationalData().getParameter4());

        //Parameter1
        assertEquals(57364, layer1.getFundamentalOperationalData().getParameter5());

        //Parameter1
        assertEquals(57344, layer1.getFundamentalOperationalData().getParameter6());



        //Layer2
        final IFFLayer2 layer2 = ipdu.getLayer2();
        
        //LayerHeader
        assertEquals(2, layer2.getLayerHeader().getLayerNumber());        
        assertEquals(76, layer2.getLayerHeader().getLength());        
        assertEquals(0, layer2.getLayerHeader().getLayerSpecificInformation());        
        
        //BeamData
        assertEquals(56, layer2.getBeamData().getBeamAzimuthCenter(), 0.001);
        assertEquals(57, layer2.getBeamData().getBeamAzimuthSweep(), 0.001);
        assertEquals(58, layer2.getBeamData().getBeamElevationCenter(), 0.001);
        assertEquals(60, layer2.getBeamData().getBeamElevationSweep(), 0.001);
        assertEquals(59, layer2.getBeamData().getBeamSweepSync(), 0.001);
        
        //IffFundamentalParameterData
        assertEquals(2, layer2.getIffFundamentalParameterData().size());
        
        assertEquals(0, layer2.getIffFundamentalParameterData().get(0).getApplicableModes());
        assertEquals(5, layer2.getIffFundamentalParameterData().get(0).getBurstLength());
        assertEquals(5, layer2.getIffFundamentalParameterData().get(0).getErp(), 0.001);
        assertEquals(5, layer2.getIffFundamentalParameterData().get(0).getFrequency(), 0.001);
        assertEquals(5, layer2.getIffFundamentalParameterData().get(0).getPgrf(), 0.001);
        assertEquals(5, layer2.getIffFundamentalParameterData().get(0).getPulseWidth(), 0.001);
        assertEquals(3, layer2.getIffFundamentalParameterData().get(0).getSystemSpecificData().length);
        assertEquals(0, layer2.getIffFundamentalParameterData().get(0).getSystemSpecificData()[0]);
        assertEquals(0, layer2.getIffFundamentalParameterData().get(0).getSystemSpecificData()[1]);
        assertEquals(0, layer2.getIffFundamentalParameterData().get(0).getSystemSpecificData()[2]);

        assertEquals(0, layer2.getIffFundamentalParameterData().get(1).getApplicableModes());
        assertEquals(6, layer2.getIffFundamentalParameterData().get(1).getBurstLength());
        assertEquals(6, layer2.getIffFundamentalParameterData().get(1).getErp(), 0.001);
        assertEquals(6, layer2.getIffFundamentalParameterData().get(1).getFrequency(), 0.001);
        assertEquals(6, layer2.getIffFundamentalParameterData().get(1).getPgrf(), 0.001);
        assertEquals(6, layer2.getIffFundamentalParameterData().get(1).getPulseWidth(), 0.001);
        assertEquals(3, layer2.getIffFundamentalParameterData().get(1).getSystemSpecificData().length);
        assertEquals(0, layer2.getIffFundamentalParameterData().get(1).getSystemSpecificData()[0]);
        assertEquals(0, layer2.getIffFundamentalParameterData().get(1).getSystemSpecificData()[1]);
        assertEquals(0, layer2.getIffFundamentalParameterData().get(1).getSystemSpecificData()[2]);
        
        //SecondaryOperationalData
        assertEquals(2, layer2.getSecondaryOperationalData().getNumberOfIFFFundamentalParameterRecords());
        assertEquals(0, layer2.getSecondaryOperationalData().getOperationalData1());
        assertEquals(0, layer2.getSecondaryOperationalData().getOperationalData2());
    }
}
