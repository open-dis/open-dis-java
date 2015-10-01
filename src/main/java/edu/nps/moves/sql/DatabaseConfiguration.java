package edu.nps.moves.sql;

import java.util.*;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import edu.nps.moves.dis.*;
import edu.nps.moves.disutil.*;

import java.net.*;


/**
 * Example, used to set up connection to database and such
 *
 * @author DMcG
 */
public class DatabaseConfiguration
{
    private static SessionFactory sessionFactory = null;

    public enum NetworkMode {UNICAST, MULTICAST, BROADCAST};

    /** Three popular database types. MySql requires a bit more setup, while hsqldb
     * and derby can be used as embedded databases, though possibly with lower performance
     * under load. */
    public enum DatabaseType{MYSQL, HSQLDB, DERBY};

    public static DatabaseType databaseType = DatabaseType.MYSQL;

    String MULTICAST_ADDRESS = "239.1.2.3";
    String PORT = "3000";
    
    /** 
     * 8K was for at least a time the max PDU size; this should be good enough
     */
    public static final int MAX_PDU_SIZE = 1024 * 8;

    public static SessionFactory getSessionFactory(DatabaseType db)
    {
        // Already been created? Then return that.
        if(sessionFactory != null)
            return sessionFactory;


        AnnotationConfiguration config = new AnnotationConfiguration();

        try
        {
            switch(db)
            {
                case MYSQL:
                       config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                       config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/opendis");
                      
                       config.setProperty("hibernate.connection.username", "root");
                       config.setProperty("hibernate.connection.password", "");
                       config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                       config.setProperty("hibernate.hbm2ddl.auto", "create"); // create tables when not exist

                       config.setProperty("hibernate.connection.pool_size", "10");
                       config.setProperty("hibernate.connection.autocommit", "true");
                       config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");

                       // When using this, the OLD TABLES WILL BE DROPPED each run
                       config.setProperty("hibernate.hbm2ddl.auto", "create-drop");

                       // When using this, the OLD TABLES WILL BE BE RETAINED each run
                       //config.setProperty("hibernate.hbm2ddl.auto", "validate");

                       //config.setProperty("hibernate.show_sql", "true");
                       config.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
                       config.setProperty("hibernate.current_session_context_class", "thread");

                    break;

                case HSQLDB:
                        config.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
                        config.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
                        //config.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:demodb");
                        config.setProperty("hibernate.connection.url", "jdbc:hsqldb:file:/tmp/hsqldb");

                        config.setProperty("hibernate.connection.username", "sa");
                        config.setProperty("hibernate.connection.password", "");
                        config.setProperty("hibernate.connection.pool_size", "1");
                        config.setProperty("hibernate.connection.autocommit", "true");
                        config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
                        config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
                        //config.setProperty("hibernate.hbm2ddl.auto", "validate");
                        //config.setProperty("hibernate.show_sql", "true");
                        config.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
                        config.setProperty("hibernate.current_session_context_class", "thread");
                    break;

                case DERBY:
                       config.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
                       //config.setProperty("hibernate.connection.driver.class", "org.apache.derby.jdbc.CLientDriver");
                       config.setProperty("hibernate.connection.driver.class", "org.apache.derby.jdbc.EmbeddedDriver");
                       //config.setProperty("hibernate.connection.url", "jdbc:derby://localhost:1527/sample");
                       config.setProperty("hibernate.connection.url", "jdbc:derby:/tmp/opendis;create=true");
                       config.setProperty("hibernate.connection.username", "app");
                       config.setProperty("hibernate.connection.password", "app");

                       config.setProperty("hibernate.hbm2ddl.auto", "create"); // create tables when not exist

                       config.setProperty("hibernate.connection.pool_size", "10");
                       config.setProperty("hibernate.connection.autocommit", "true");
                       config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
                       // When using this, the OLD TABLES WILL BE DROPPED each run
                       config.setProperty("hibernate.hbm2ddl.auto", "create-drop");

                       // When using this, the OLD TABLES WILL BE BE RETAINED each run
                       //config.setProperty("hibernate.hbm2ddl.auto", "validate");

                       //config.setProperty("hibernate.show_sql", "true");
                       config.setProperty("hibernate.show_sql", "false");
                       config.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
                       config.setProperty("hibernate.current_session_context_class", "thread");
                    break;

                default:
                    return null;
            }

            // Add  mapped classes here. Ideally this should be done via introspection, but
            // not all classes may be loaded by the classloader at runtime, which may result
            // in some classes being missed. So copy & paste from ls and some text manipulation it
            // is. The array is just a handy way to deal with the copy & paste.
            Class classArray[] = {
                 AcknowledgePdu.class,
                 AcknowledgeReliablePdu.class,
                 AcousticBeamData.class,
                 AcousticBeamFundamentalParameter.class,
                 AcousticEmitter.class,
                 AcousticEmitterSystem.class,
                 AcousticEmitterSystemData.class,
                 ActionRequestPdu.class,
                 ActionRequestReliablePdu.class,
                 ActionResponsePdu.class,
                 ActionResponseReliablePdu.class,
                 AggregateID.class,
                 AggregateMarking.class,
                 AggregateStatePdu.class,
                 AggregateType.class,
                 AngularVelocityVector.class,
                 AntennaLocation.class,
                 ApaData.class,
                 ArealObjectStatePdu.class,
                 ArticulationParameter.class,
                 BeamAntennaPattern.class,
                 BeamData.class,
                 BurstDescriptor.class,
                 ClockTime.class,
                 CollisionElasticPdu.class,
                 CollisionPdu.class,
                 CommentPdu.class,
                 CommentReliablePdu.class,
                 CreateEntityPdu.class,
                 CreateEntityReliablePdu.class,
                 DataPdu.class,
                 DataQueryPdu.class,
                 DataQueryReliablePdu.class,
                DataReliablePdu.class,
                DeadReckoningParameter.class,
                DesignatorPdu.class,
                DetonationPdu.class,
                DistributedEmissionsFamilyPdu.class,
                EightByteChunk.class,
                ElectronicEmissionBeamData.class,
                ElectronicEmissionSystemData.class,
                ElectronicEmissionsPdu.class,
                EmitterSystem.class,
                EntityID.class,
               EntityInformationFamilyPdu.class,
                EntityManagementFamilyPdu.class,
                EntityStatePdu.class,
                EntityStateUpdatePdu.class,
                edu.nps.moves.dis.EntityType.class,
                Environment.class,
                EnvironmentalProcessPdu.class,
                EventID.class,
                EventReportPdu.class,
                EventReportReliablePdu.class,
                FastEntityStatePdu.class,
                FirePdu.class,
                FixedDatum.class,
                FourByteChunk.class,
                FundamentalParameterData.class,
                FundamentalParameterDataIff.class,
                GridAxisRecord.class,
                GridAxisRecordRepresentation0.class,
                GridAxisRecordRepresentation1.class,
                GridAxisRecordRepresentation2.class,
                GriddedDataPdu.class,
                IffAtcNavAidsLayer1Pdu.class,
                IffAtcNavAidsLayer2Pdu.class,
                IffFundamentalData.class,
                IntercomCommunicationsParameters.class,
                IntercomControlPdu.class,
                IntercomSignalPdu.class,
                IsGroupOfPdu.class,
                IsPartOfPdu.class,
                LayerHeader.class,
                LinearObjectStatePdu.class,
                LinearSegmentParameter.class,
                LogisticsFamilyPdu.class,
                Marking.class,
                MinefieldDataPdu.class,
                MinefieldFamilyPdu.class,
                MinefieldQueryPdu.class,
                MinefieldResponseNackPdu.class,
                MinefieldStatePdu.class,
                ModulationType.class,
                NamedLocation.class,
                ObjectType.class,
                OneByteChunk.class,
                Orientation.class,
                Pdu.class,
                PduContainer.class,
                Point.class,
                PointObjectStatePdu.class,
                PropulsionSystemData.class,
                RadioCommunicationsFamilyPdu.class,
                RadioEntityType.class,
                ReceiverPdu.class,
                RecordQueryReliablePdu.class,
                RecordSet.class,
                Relationship.class,
                RemoveEntityPdu.class,
                RemoveEntityReliablePdu.class,
                RepairCompletePdu.class,
                RepairResponsePdu.class,
                ResupplyCancelPdu.class,
                ResupplyOfferPdu.class,
                ResupplyReceivedPdu.class,
                SeesPdu.class,
                ServiceRequestPdu.class,
                SetDataPdu.class,
                SetDataReliablePdu.class,
                SetRecordReliablePdu.class,
                ShaftRPMs.class,
                SignalPdu.class,
                SimulationAddress.class,
                SimulationManagementFamilyPdu.class,
                SimulationManagementWithReliabilityFamilyPdu.class,
                SixByteChunk.class,
                SphericalHarmonicAntennaPattern.class,
                StartResumePdu.class,
                StartResumeReliablePdu.class,
                StopFreezePdu.class,
                StopFreezeReliablePdu.class,
                SupplyQuantity.class,
                SyntheticEnvironmentFamilyPdu.class,
                SystemID.class,
                TrackJamTarget.class,
                TransferControlRequestPdu.class,
                TransmitterPdu.class,
                TwoByteChunk.class,
                UaPdu.class,
                VariableDatum.class,
                Vector3Double.class,
                Vector3Float.class,
                VectoringNozzleSystemData.class,
                WarfareFamilyPdu.class,
                edu.nps.moves.sql.PduStream.class
                 };

            for(int idx = 0; idx < classArray.length; idx++)
            {
                config.addAnnotatedClass(classArray[idx]);
            }


            return config.buildSessionFactory();
        }
        catch(Exception e)
        {
          System.err.println("++++++++++++ Exception "+e.getClass().getSimpleName()+" in DatabaseSetup.getSessionFactory(db,cntx): "+e.getLocalizedMessage());
          return null;
        }
    }

    public DatabaseConfiguration(SessionFactory sessionFactory, Properties props)
    {
        String multicastAddress = props.getProperty("multicastAddress");
        InetAddress mcastAddress;
        String port = props.getProperty("port");
        int portNumber;
        String mode = props.getProperty("networkMode"); // unicast or multicast or broadcast
        NetworkMode networkMode = NetworkMode.BROADCAST;
        PduFactory pduFactory = new PduFactory();

        if(port == null)
            port = PORT;
        if(multicastAddress == null)
            multicastAddress = MULTICAST_ADDRESS;
        if(mode == null)
            mode = "multicast";

        try
        {
            portNumber = Integer.parseInt(port);
            if(mode.equalsIgnoreCase("multicast"))
                networkMode = NetworkMode.MULTICAST;
            else if(mode.equalsIgnoreCase("unicast"))
                networkMode = NetworkMode.UNICAST;
            else if(mode.equalsIgnoreCase("broadcast"))
                networkMode = NetworkMode.BROADCAST;

            DatagramSocket socket = null;

            portNumber = Integer.parseInt(port);

            switch(networkMode)
            {
                case UNICAST:
                case BROADCAST:
                    socket = new DatagramSocket(portNumber);
                    System.out.println("created unicast/bcast socket");
                    break;

                case MULTICAST:
                    MulticastSocket aSocket = new MulticastSocket(portNumber);
                    mcastAddress = InetAddress.getByName(multicastAddress);
                    aSocket.joinGroup(mcastAddress);
                    socket = aSocket;
                    System.out.println("created multicast socket");

                    break;

                default:
                    System.out.println("unhandled network type: expected to be unicast, broadcast, or multicast");
            }

            System.out.println("Network configured, port=" + port + " mode=" +networkMode);
            
            while(true)
            {
                try
                {
                    byte buffer[] = new byte[MAX_PDU_SIZE]; 
                    DatagramPacket aPacket = new DatagramPacket(buffer, buffer.length);

                    System.out.println("ready to receive a packet");
                    socket.receive(aPacket);
                    System.out.println("got a packet");

                    Pdu aPdu = pduFactory.createPdu(aPacket.getData());

                    
                    Session session = sessionFactory.openSession();
                    Transaction transaction = session.beginTransaction();
                    //FastEntityStatePdu espdu = new FastEntityStatePdu();
                    //espdu.setCapabilities(23);
                    session.save(aPdu);
                    transaction.commit();
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                
            }


        }
        catch(Exception e)
        {
            System.out.println(e);
        }



    }

    public static void main(String args[])
    {
        System.out.println("Startup....");
        SessionFactory factory = DatabaseConfiguration.getSessionFactory(DatabaseType.MYSQL);
        //Session session = factory.getCurrentSession();

        Properties systemProperties = System.getProperties();
        System.out.println(systemProperties);
        //System.out.println(args[0]);

        DatabaseConfiguration instance = new DatabaseConfiguration(factory, systemProperties);
        

        System.out.println("Starting");
        System.out.println(systemProperties.getProperty("port"));
        System.out.println(systemProperties.getProperty("networkMode"));


    }
}


