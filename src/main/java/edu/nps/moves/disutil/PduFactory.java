package edu.nps.moves.disutil;

import java.nio.ByteBuffer;
import java.util.logging.*;
import java.util.*;

import edu.nps.moves.dis.*;
import edu.nps.moves.disenum.PduType;

/**
 * Simple factory for PDUs. When bytes are received on the wire, they're passed off to us
 * and the correct constructor called to return the correct PDU type.<p>
 *
 * This should be reworked to use the separate enumerations package, which is generated
 * from the XML EBV file. That's included with the open-dis distribution, but it's still
 * a little new.
 *
 * @author DMcG
 */
public class PduFactory {

    /** whether we should use return flattened, "fast" espdus with fewer objects */
    private boolean useFastPdu = false;

    private Logger logger;


    /** Creates a new instance of PduFactory */
    public PduFactory() {
        this(false);
    }

    /** 
     * Create a new PDU factory; if true is passed in, we use "fast PDUs",
     * which minimize the memory garbage generated at the cost of being
     * somewhat less pleasant to work with.
     * @param useFastPdu
     */
    public PduFactory(boolean useFastPdu) {
        this.useFastPdu = useFastPdu;

        // By default don't log anything
        logger = Logger.getLogger(PduFactory.class.getName());
        logger.setLevel(Level.OFF);

    }

    public void setUseFastPdu(boolean use) {
        this.useFastPdu = use;
    }

    public boolean getUseFastPdu() {
        return this.useFastPdu;
    }

    /**
     * Set the logging level that will be printed, typically to Level.INFO
     * @param loggingLevel 
     */
    public void setLoggingLevel(Level loggingLevel)
    {
        logger.setLevel(loggingLevel);
    }

    /** 
     * PDU factory. Pass in an array of bytes, get the correct type of pdu back,
     * based on the PDU type field contained in the byte array.
     * @param data
     * @return A PDU of the appropriate concrete subclass of PDU
     */
    public Pdu createPdu(byte data[]) {
        return createPdu(ByteBuffer.wrap(data));
    }

    /**
     * PDU factory. Pass in an array of bytes, get the correct type of pdu back,
     * based on the PDU type field contained in the byte buffer.
     * @param buff
     * @return null if there was an error creating the Pdu
     */
    public Pdu createPdu(java.nio.ByteBuffer buff) {
        
        int pos = buff.position();          // Save buffer's position
        if (pos + 2 > buff.limit()) {       // Make sure there's enough space in buffer
            return null;                    // Else return null
        }   // end if: buffer too short
        buff.position(pos + 2);             // Advance to third byte
        int pduType = buff.get() & 0xFF;    // Read Pdu type
        buff.position(pos);                 // Reset buffer

        // Do a lookup to get the enumeration instance that corresponds to this value.
        PduType pduTypeEnum = PduType.lookup[pduType];
        Pdu aPdu = null;

        switch (pduTypeEnum) {
        // NOTE: OTHER is a valid pduTypeEnum, but has no corresponding object

        case ENTITY_STATE:
            // if the user has created the factory requesting that he get fast espdus back, give him those.
            if (useFastPdu) {
                aPdu = new FastEntityStatePdu();
            } else {
                aPdu = new EntityStatePdu();
            }
            break;

        case FIRE:
            aPdu = new FirePdu();
            break;

        case DETONATION:
            aPdu = new DetonationPdu();
            break;

        case COLLISION:
            aPdu = new CollisionPdu();
            break;

        case SERVICE_REQUEST:
            aPdu = new ServiceRequestPdu();
            break;

        case RESUPPLY_OFFER:
            aPdu = new ResupplyOfferPdu();
            break;

        case RESUPPLY_RECEIVED:
            aPdu = new ResupplyReceivedPdu();
            break;

        case RESUPPLY_CANCEL:
            aPdu = new ResupplyCancelPdu();
            break;

        case REPAIR_COMPLETE:
            aPdu = new RepairCompletePdu();
            break;

        case REPAIR_RESPONSE:
            aPdu = new RepairResponsePdu();
            break;

        case CREATE_ENTITY:
            aPdu = new CreateEntityPdu();
            break;

        case REMOVE_ENTITY:
            aPdu = new RemoveEntityPdu();
            break;

        case START_RESUME:
            aPdu = new StartResumePdu();
            break;

        case STOP_FREEZE:
            aPdu = new StopFreezePdu();
            break;

        case ACKNOWLEDGE:
            aPdu = new AcknowledgePdu();
            break;

        case ACTION_REQUEST:
            aPdu = new ActionRequestPdu();
            break;

        case ACTION_RESPONSE:
            aPdu = new ActionResponsePdu();
            break;

        case DATA_QUERY:
            aPdu = new DataQueryPdu();
            break;

        case SET_DATA:
            aPdu = new SetDataPdu();
            break;

        case DATA:
            aPdu = new DataPdu();
            break;

        case EVENT_REPORT:
            aPdu = new EventReportPdu();
            break;

        case COMMENT:
            aPdu = new CommentPdu();
            break;

        case ELECTROMAGNETIC_EMISSION:
            aPdu = new ElectronicEmissionsPdu();
            break;

        case DESIGNATOR:
            aPdu = new DesignatorPdu();
            break;

        case TRANSMITTER:
            aPdu = new TransmitterPdu();
            break;

        case SIGNAL:
            aPdu = new SignalPdu();
            break;

        case RECEIVER:
            aPdu = new ReceiverPdu();
            break;

        // FIXME: IFF_ATC_NAVAIDS (28)
            
        case UNDERWATER_ACOUSTIC:
            aPdu = new UaPdu();
            break;
            
        case SUPPLEMENTAL_EMISSION_ENTITY_STATE:
            aPdu = new SeesPdu();
            break;

        case INTERCOM_SIGNAL:
            aPdu = new IntercomSignalPdu();
            break;

        case INTERCOM_CONTROL:
            aPdu = new IntercomControlPdu();
            break;

        case AGGREGATE_STATE:
            aPdu = new AggregateStatePdu();
            break;

        case ISGROUPOF:
            aPdu = new IsGroupOfPdu();
            break;

        case TRANSFER_CONTROL:
            aPdu = new TransferControlRequestPdu();
            break;

        case ISPARTOF:
            aPdu = new IsPartOfPdu();
            break;

        case MINEFIELD_STATE:
            aPdu = new MinefieldStatePdu();
            break;

        case MINEFIELD_QUERY:
            aPdu = new MinefieldQueryPdu();
            break;

        case MINEFIELD_DATA:
            aPdu = new MinefieldDataPdu();
            break;

        case MINEFIELD_RESPONSE_NAK:
            aPdu = new MinefieldResponseNackPdu();
            break;

        case ENVIRONMENTAL_PROCESS:
            aPdu = new EnvironmentalProcessPdu();
            break;

        case GRIDDED_DATA:
            aPdu = new GriddedDataPdu();
            break;

        case POINT_OBJECT_STATE:
            aPdu = new PointObjectStatePdu();
            break;

        case LINEAR_OBJECT_STATE:
            aPdu = new LinearObjectStatePdu();
            break;

        case AREAL_OBJECT_STATE:
            aPdu = new ArealObjectStatePdu();
            break;

            // FIXME: case TSPI: (46)
            // FIXME: case APPEARANCE: (47)
            // FIXME: case ARTICULATED_PARTS: (48)
            // FIXME: case LE_FIRE: (49)
            // FIXME: case LE_DETONATION: (50)

        case CREATE_ENTITY_R:
            aPdu = new CreateEntityReliablePdu();
            break;

        case REMOVE_ENTITY_R:
            aPdu = new RemoveEntityReliablePdu();
            break;

        case START_RESUME_R:
            aPdu = new StartResumeReliablePdu();
            break;

        case STOP_FREEZE_R:
            aPdu = new StopFreezeReliablePdu();
            break;

        case ACKNOWLEDGE_R:
            aPdu = new AcknowledgeReliablePdu();
            break;

        case ACTION_REQUEST_R:
            aPdu = new ActionRequestReliablePdu();
            break;

        case ACTION_RESPONSE_R:
            aPdu = new ActionResponseReliablePdu();
            break;

        case DATA_QUERY_R:
            aPdu = new DataQueryReliablePdu();
            break;

        case SET_DATA_R:
            aPdu = new SetDataReliablePdu();
            break;

        case DATA_R:
            aPdu = new DataReliablePdu();
            break;

        case EVENT_REPORT_R:
            aPdu = new EventReportReliablePdu();
            break;

        case COMMENT_R:
            aPdu = new CommentReliablePdu();
            break;

            // FIXME: case RECORD_R: (63)

        case SET_RECORD_R:
            aPdu = new SetRecordReliablePdu();
            break;

        case RECORD_QUERY_R:
            aPdu = new RecordQueryReliablePdu();
            break;

        case COLLISION_ELASTIC:
            aPdu = new CollisionElasticPdu();
            break;

        case ENTITY_STATE_UPDATE:
            aPdu = new EntityStateUpdatePdu();
            break;

        default:
            logger.log(Level.INFO, "PDU not implemented. Type = " + pduType + "\n");
            if (pduTypeEnum != null) {
                logger.log(Level.INFO, "  PDU  name is: " + pduTypeEnum.getDescription());
            }

        }   // end switch

        if (aPdu != null) {
            aPdu.unmarshal(buff);
        }
        return aPdu;
    }

    /**
     * Decodes datagram contents with bundled PDUs. As a performance hack DIS
     * may include several PDUs in one datagram. Typically the max datagram 
     * size is 8K (above that it runs into some issues with the default 
     * incoming socket buffer size) but it may be more. The PDUs may be of
     * multiple types and different lengths, so we have to step through the
     * buffer and depend on the reported PDU length in the header. There's
     * a lot that can go wrong. If something blows up, we return all the decoded
     * PDUs we can.<p>
     * 
     * @param data
     * @return List of PDUs decoded
     */
    public List<Pdu> getPdusFromBundle(byte data[])
    {
        // All the PDUs in this bundle we were able to decode
        ArrayList<Pdu> pdus = new ArrayList<Pdu>();
        // The start point of a PDU in the data. We advance this by the size
        // of each PDU as we read it.
        int pduStartPointInData = 0;

        while(true)
        {
            // This is inefficient, but screw it. Give the GC a workout. Create a new
            // data array from where the last PDU left off to the end of the original
            // data array. This lets us reuse a bunch of old code.

            byte remaining[] = Arrays.copyOfRange(data, pduStartPointInData, data.length);

            try
            {
                // Decode one PDU
                Pdu pdu = this.createPdu(remaining);

                // If the read is muffed somehow, give up on decoding the rest of
                // the data
                if(pdu == null)
                {
                    //System.out.println("Stopped reading bundled PDU due to bad PDU");
                    break;
                }
                else // otherwise add it to the list of PDUs we have decoded from this UDP packet
                {
                    pdus.add(pdu);
                }

                // Advance the index to the start of the next PDU
                int pduLength = pdu.getLength();
                pduStartPointInData = pduStartPointInData + pduLength;

                //System.out.println("PDUStartPOint:" + pduStartPointInData + " data: " + data.length);
                // Have we read all the data?
                if(pduStartPointInData >= data.length)
                {
                    //System.out.println("Out of data to read" + pduStartPointInData + " data length:" + data.length);
                    break;
                }

            }
            catch(Exception e)
            {
                System.out.println("Problems decoding multiple PDUs in datagram; decoded as may as possible");
            }
        } // end while

        return pdus;
    }
}
