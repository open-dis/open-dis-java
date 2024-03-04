package edu.nps.moves.dis7;

import edu.nps.moves.dis7.AcknowledgePdu;
import edu.nps.moves.dis7.AcknowledgeReliablePdu;
import edu.nps.moves.dis7.ActionRequestPdu;
import edu.nps.moves.dis7.ActionRequestReliablePdu;
import edu.nps.moves.dis7.ActionResponsePdu;
import edu.nps.moves.dis7.ActionResponseReliablePdu;
import edu.nps.moves.dis7.ArealObjectStatePdu;
import edu.nps.moves.dis7.CollisionElasticPdu;
import edu.nps.moves.dis7.CollisionPdu;
import edu.nps.moves.dis7.CommentPdu;
import edu.nps.moves.dis7.CommentReliablePdu;
import edu.nps.moves.dis7.CreateEntityPdu;
import edu.nps.moves.dis7.CreateEntityReliablePdu;
import edu.nps.moves.dis7.DataPdu;
import edu.nps.moves.dis7.DataQueryPdu;
import edu.nps.moves.dis7.DataQueryReliablePdu;
import edu.nps.moves.dis7.DataReliablePdu;
import edu.nps.moves.dis7.DesignatorPdu;
import edu.nps.moves.dis7.DetonationPdu;
import edu.nps.moves.dis7.ElectronicEmissionsPdu;
import edu.nps.moves.dis7.EntityStatePdu;
import edu.nps.moves.dis7.EntityStateUpdatePdu;
import edu.nps.moves.dis7.EventReportPdu;
import edu.nps.moves.dis7.EventReportReliablePdu;
import edu.nps.moves.dis7.FirePdu;
import edu.nps.moves.dis7.IntercomControlPdu;
import edu.nps.moves.dis7.IntercomSignalPdu;
import edu.nps.moves.dis7.IsPartOfPdu;
import edu.nps.moves.dis7.LinearObjectStatePdu;
import edu.nps.moves.dis7.MinefieldResponseNackPdu;
import edu.nps.moves.dis7.MinefieldStatePdu;
import edu.nps.moves.dis7.Pdu;
import edu.nps.moves.dis7.PointObjectStatePdu;
import edu.nps.moves.dis7.ReceiverPdu;
import edu.nps.moves.dis7.RecordQueryReliablePdu;
import edu.nps.moves.dis7.RemoveEntityPdu;
import edu.nps.moves.dis7.RemoveEntityReliablePdu;
import edu.nps.moves.dis7.RepairCompletePdu;
import edu.nps.moves.dis7.RepairResponsePdu;
import edu.nps.moves.dis7.ResupplyOfferPdu;
import edu.nps.moves.dis7.ResupplyReceivedPdu;
import edu.nps.moves.dis7.SeesPdu;
import edu.nps.moves.dis7.ServiceRequestPdu;
import edu.nps.moves.dis7.SetDataPdu;
import edu.nps.moves.dis7.SetDataReliablePdu;
import edu.nps.moves.dis7.SignalPdu;
import edu.nps.moves.dis7.StartResumePdu;
import edu.nps.moves.dis7.StartResumeReliablePdu;
import edu.nps.moves.dis7.StopFreezePdu;
import edu.nps.moves.dis7.StopFreezeReliablePdu;
import edu.nps.moves.dis7.TransmitterPdu;
import edu.nps.moves.dis7.UaPdu;
import edu.nps.moves.disenum.PduType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PduFactory {

    private Logger logger;

    public PduFactory() {
        this.logger = Logger.getLogger(edu.nps.moves.disutil.PduFactory.class.getName());
        this.logger.setLevel(Level.OFF);
    }

    public void setLoggingLevel(Level loggingLevel) {
        this.logger.setLevel(loggingLevel);
    }

    public Pdu createPdu(byte[] data) {
        return this.createPdu(ByteBuffer.wrap(data));
    }

    public Pdu createPdu(ByteBuffer buff) {
        int pos = buff.position();
        if (pos + 2 > buff.limit()) {
            return null;
        } else {
            buff.position(pos + 2);
            int pduType = buff.get() & 255;
            buff.position(pos);
            PduType pduTypeEnum = PduType.lookup[pduType];
            Pdu aPdu = null;
            switch (pduTypeEnum) {
                case ENTITY_STATE:
                    aPdu = new EntityStatePdu();
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
                case ISPARTOF:
                    aPdu = new IsPartOfPdu();
                    break;
                case MINEFIELD_STATE:
                    aPdu = new MinefieldStatePdu();
                    break;
                case MINEFIELD_RESPONSE_NAK:
                    aPdu = new MinefieldResponseNackPdu();
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
                case RECORD_QUERY_R:
                    aPdu = new RecordQueryReliablePdu();
                    break;
                case COLLISION_ELASTIC:
                    aPdu = new CollisionElasticPdu();
                    break;
                case ENTITY_STATE_UPDATE:
                    aPdu = new EntityStateUpdatePdu();
                    break;
                case IFF_ATC_NAVAIDS:
                    aPdu = new IFFPdu();
                    break;
                default:
                    this.logger.log(Level.INFO, "PDU not implemented. Type = " + pduType + "\n");
                    if (pduTypeEnum != null) {
                        this.logger.log(Level.INFO, "  PDU  name is: " + pduTypeEnum.getDescription());
                    }
            }

            if (aPdu != null) {
                ((Pdu) aPdu).unmarshal(buff);
            }

            return (Pdu) aPdu;
        }
    }

    public List<Pdu> getPdusFromBundle(byte[] data) {
        ArrayList<Pdu> pdus = new ArrayList();
        int pduStartPointInData = 0;

        while (true) {
            byte[] remaining = Arrays.copyOfRange(data, pduStartPointInData, data.length);

            try {
                Pdu pdu = this.createPdu(remaining);
                if (pdu == null) {
                    break;
                }

                pdus.add(pdu);
                int pduLength = pdu.getLength();
                pduStartPointInData += pduLength;
                if (pduStartPointInData >= data.length) {
                    break;
                }
            } catch (Exception var7) {
                System.out.println("Problems decoding multiple PDUs in datagram; decoded as may as possible");
                break;
            }
        }

        return pdus;
    }
}
