/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
  nifty piece of code, but doesn't compile under JDK 1.5 due ot use
  of some swing features in javax that are not present. To compile
  this under JDK 1.6, simply change the file name to end in .java
*/

/*
 * PduByteBufferTester.java
 *
 * Created on Nov 4, 2008, 4:10:01 PM
 */

package edu.nps.moves.examples;

import edu.nps.moves.dis.EntityStatePdu;
import edu.nps.moves.dis.Pdu;
import edu.nps.moves.disutil.PduMulticastReceiver;
import edu.nps.moves.disutil.UdpServer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author rharder
 */
public class PduByteBufferTester extends javax.swing.JFrame implements PduMulticastReceiver.Listener, UdpServer.Listener {
    //private byte[] getOverThe5MbStackHurdle = new byte[1024*1024*5];
    private final static String START_FLOOD = "Begin Packet Flood";
    private final static String STOP_FLOOD = "Stop";
    
    private SwingWorker<Object,Object>senderSW;
    private EntityStatePdu espdu = new EntityStatePdu();

    private boolean marshalWithByteBuffer;
    
    private int burstSize = 50;

    private java.util.Timer rateAveragingTimer;
    private volatile long sentCount = 0; // volatile => atomic
    private volatile long recvCount = 0;
    private float averageSendRate = 0;
    private float averageRecvRate = 0;
    private int[][] rateAveraging = new int[5][3];
    private int nextRateAveragingPos = 0;
    private final static int INTERVAL = 0;
    private final static int SENT_COUNT = 1;
    private final static int RECV_COUNT = 2;
    private List<Pdu> receivedPdus = new LinkedList<Pdu>();


    private PduMulticastReceiver pduServer;


    /** Creates new form PduByteBufferTester */
    public PduByteBufferTester() {
        initComponents();
        myInitComponents();
    }


    private void myInitComponents(){

        this.pduServer = new PduMulticastReceiver();
        this.pduServer.setPort(62040);
        this.pduServer.setGroups("239.1.2.3");

        this.pduServer.setUseByteBuffer( this.unmarshalWithByteBufferCheckbox.isSelected() );
        
        UdpServer.setLoggingLevel(Level.OFF);
        this.pduServer.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {

                if( UdpServer.PORT_PROP.equals( evt.getPropertyName() ) ){
                    final Object newVal = evt.getNewValue();
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            recvPortField.setValue( newVal );
                        }   // end run
                    }); // end swing utilities

                } else if( UdpServer.GROUPS_PROP.equals( evt.getPropertyName() ) ){
                    final Object newVal = evt.getNewValue();
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            recvGroupField.setText( newVal == null ? "" : newVal.toString() );
                        }   // end run
                    }); // end swing utilities

                }
            }
        });
        this.pduServer.fireProperties();
        this.pduServer.addUdpServerListener(this);
        this.pduServer.addPduMulticastReceiverListener(this);
        this.pduServer.fireState();


        this.burstSizeSlider.setValue(burstSize);
        this.burstSizeField.setValue(burstSize);

        MemoryView mv = new MemoryView();
        mv.setRefresh(100);
        this.memoryViewPanel.add( mv, BorderLayout.CENTER);

        ActionListener taskPerformer = new ActionListener() {
            long lastTime;
            long lastSentCount;
            long lastRecvCount;
            public void actionPerformed(ActionEvent evt) {

                // Collect snapshot
                long nowTime = System.currentTimeMillis();
                long nowSentCount = sentCount;
                long nowRecvCount = recvCount;

                int intervalTime = (int)(nowTime - lastTime);
                int intervalSentCount = (int)(nowSentCount - lastSentCount);
                int intervalRecvCount = (int)(nowRecvCount - lastRecvCount);

                int len = rateAveraging.length;
                rateAveraging[nextRateAveragingPos][INTERVAL] = intervalTime;
                rateAveraging[nextRateAveragingPos][SENT_COUNT] = intervalSentCount;
                rateAveraging[nextRateAveragingPos][RECV_COUNT] = intervalRecvCount;
                nextRateAveragingPos = (nextRateAveragingPos+1) % len;
                lastTime = nowTime;
                lastSentCount = nowSentCount;
                lastRecvCount = nowRecvCount;

                // Calculate average
                float sentSum = 0;
                float recvSum = 0;
                for( int i = 0; i < len; i++ ){
                    sentSum += 1000f * rateAveraging[i][SENT_COUNT] / rateAveraging[i][INTERVAL];
                    recvSum += 1000f * rateAveraging[i][RECV_COUNT] / rateAveraging[i][INTERVAL];
                }
                averageSendRate = sentSum / len;
                averageRecvRate = recvSum / len;

                // Update field
                actualSendRateField.setValue((int)averageSendRate);
                recvRateField.setValue((int)averageRecvRate);
            }
        };
        new javax.swing.Timer(250, taskPerformer).start();

    }   // end myInitComponents



    private void startFlood(){
        this.startFloodButton.setText( STOP_FLOOD );
        this.sendProgress.setIndeterminate(true);
        this.destIp.setEnabled(false);
        this.destPortField.setEnabled(false);
        final String dest = this.destIp.getText();
        final int port = (Integer)this.destPortField.getValue();
        
        this.senderSW = new SwingWorker<Object,Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                byte[] data = new byte[64*1024];            // Used with ByteBuffer version
                ByteBuffer buff = ByteBuffer.wrap(data);    // Used with ByteBuffer version
                
                DatagramSocket socket = new DatagramSocket();
                DatagramPacket packet = new DatagramPacket(new byte[0],0);
                packet.setAddress(InetAddress.getByName(dest));
                packet.setPort(port);

                int burst = burstSize;
                int i = 0;
                while( !isCancelled() ){

                    burst = burstSize;
                    for( i = 0; i < burst; i++ ){

                        // With Byte Buffers
                        if( marshalWithByteBuffer ){
                            buff.rewind();
                            espdu.marshal(buff);
                            packet.setData(data, 0, buff.position());
                            socket.send(packet);
                            sentCount++;
                        }   // end if: buffers

                        // With Streams
                        else {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            DataOutputStream dos = new DataOutputStream(baos);
                            espdu.marshal(dos);
                            dos.close();
                            byte[] marshData = baos.toByteArray();
                            packet.setData(marshData);
                            socket.send(packet);
                            sentCount++;
                        }   // end else: streams
                    }   // end for: burst

                    //long a = System.currentTimeMillis();
                    Thread.sleep( 1 );
                    //long b = System.currentTimeMillis();
                    //System.out.println((b-a));
                    
                }   // end while: not cancelled
                return null;
            }
        };
        this.senderSW.execute();
    }


    private void stopFlood(){
        this.startFloodButton.setText( START_FLOOD );
        this.sendProgress.setIndeterminate(false);
        this.destIp.setEnabled(true);
        this.destPortField.setEnabled(true);
        this.senderSW.cancel(true);
        this.senderSW = null;
    }

    
    public float getAverageSendRate(){
        return this.averageSendRate;
    }




    public void udpServerStateChanged(UdpServer.Event evt) {
        final UdpServer.State state = evt.getState();
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                switch( state ){
                    case STARTING:
                        stateLabel.setText( "Starting" );
                        startStopButton.setEnabled( false );
                        break;
                    case STARTED:
                        stateLabel.setText( "Started" );
                        startStopButton.setText( "Stop" );
                        startStopButton.setEnabled( true );
                        break;
                    case STOPPING:
                        stateLabel.setText( "Stopping" );
                        startStopButton.setEnabled( false );
                        break;
                    case STOPPED:
                        stateLabel.setText( "Stopped" );
                        startStopButton.setText( "Start" );
                        startStopButton.setEnabled( true );
                        break;
                    default:
                        assert false : state;
                        break;
                }   // end switch
            }   // end run
        }); // end swing utilities
    }


    public void udpServerPacketReceived(UdpServer.Event evt) {
    }


    
    public void pduReceived( PduMulticastReceiver.Event evt ){
        // Woo Hoo!
        this.recvCount++;
        this.receivedPdus.add( evt.getPdu() );
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        senderPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        destIp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        destPortField = new javax.swing.JFormattedTextField(62040);
        jLabel5 = new javax.swing.JLabel();
        burstSizeField = new javax.swing.JFormattedTextField(1);
        burstSizeSlider = new javax.swing.JSlider();
        startFloodButton = new javax.swing.JButton();
        sendProgress = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();
        actualSendRateField = new javax.swing.JFormattedTextField(0);
        marshalWithByteBufferCheckbox = new javax.swing.JCheckBox();
        receiverPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        recvPortField = new javax.swing.JFormattedTextField(0);
        recvGroupField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        stateLabel = new javax.swing.JLabel();
        startStopButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        recvRateField = new javax.swing.JFormattedTextField(0);
        unmarshalWithByteBufferCheckbox = new javax.swing.JCheckBox();
        unmarshalToFastEspdu = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        memoryViewPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PDU Byte Buffer Efficiency Test");

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() & ~java.awt.Font.BOLD));
        jLabel1.setText("<html><b>Purpose:</b> To test the efficiency of marshaling and unmarshaling PDUs with DataInputStreams/DataOuputStreams vs reusable ByteBuffers. <strong>Recommend running with -Xincgc argument for better visualization with garbage collection</strong>.</html>");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() & ~java.awt.Font.BOLD));
        jLabel2.setText("<html>Sends an Entity State PDU (same one) repeatedly over the network at high speeds. It sends a burst of packets, pauses 1 ms, and repeats. Marshalling PDUs with ByteBuffers requires <strong>no object instantiation</strong>, so the only memory growth you see with that enabled is from other parts of the Java subsystems.</html>");

        jLabel3.setText("Destination IP:");

        destIp.setColumns(12);
        destIp.setText("239.1.2.3");

        jLabel4.setText("Port:");

        destPortField.setColumns(8);

        jLabel5.setText("Burst Size:");

        burstSizeField.setColumns(8);
        burstSizeField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                burstSizeFieldFocusLost(evt);
            }
        });

        burstSizeSlider.setMajorTickSpacing(100);
        burstSizeSlider.setMaximum(500);
        burstSizeSlider.setPaintLabels(true);
        burstSizeSlider.setPaintTicks(true);
        burstSizeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                burstSizeSliderStateChanged(evt);
            }
        });

        startFloodButton.setText("Begin Packet Flood");
        startFloodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startFloodButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Actual Send Rate (packets per second):");

        actualSendRateField.setColumns(8);
        actualSendRateField.setEditable(false);

        marshalWithByteBufferCheckbox.setText("Marshal PDUs with ByteBuffer (more efficient)");
        marshalWithByteBufferCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                marshalWithByteBufferCheckboxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout senderPanelLayout = new javax.swing.GroupLayout(senderPanel);
        senderPanel.setLayout(senderPanelLayout);
        senderPanelLayout.setHorizontalGroup(
            senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(senderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(marshalWithByteBufferCheckbox)
                    .addGroup(senderPanelLayout.createSequentialGroup()
                        .addGroup(senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(destPortField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(destIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(senderPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(burstSizeSlider, 0, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(burstSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(senderPanelLayout.createSequentialGroup()
                        .addComponent(startFloodButton)
                        .addGap(18, 18, 18)
                        .addComponent(sendProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(senderPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(actualSendRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addContainerGap())
        );
        senderPanelLayout.setVerticalGroup(
            senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(senderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(destIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(destPortField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(burstSizeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(burstSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(marshalWithByteBufferCheckbox)
                .addGap(26, 26, 26)
                .addGroup(senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startFloodButton)
                    .addComponent(sendProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(senderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(actualSendRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("PDU Sender", senderPanel);

        jLabel7.setText("Multicast Group:");

        jLabel8.setText("Port:");

        recvPortField.setColumns(12);
        recvPortField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                recvPortFieldFocusLost(evt);
            }
        });

        recvGroupField.setColumns(12);
        recvGroupField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recvGroupFieldActionPerformed(evt);
            }
        });
        recvGroupField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                recvGroupFieldFocusLost(evt);
            }
        });

        jLabel9.setText("State:");

        stateLabel.setText("Unknown");

        startStopButton.setText("Start/Stop");
        startStopButton.setEnabled(false);
        startStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startStopButtonActionPerformed(evt);
            }
        });

        jLabel10.setText("Receive Rate (PDU packets per second):");

        recvRateField.setColumns(8);
        recvRateField.setEditable(false);

        unmarshalWithByteBufferCheckbox.setText("Unmarshal PDUs with ByteBuffer (leaves no garbage to collect)");
        unmarshalWithByteBufferCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                unmarshalWithByteBufferCheckboxItemStateChanged(evt);
            }
        });

        unmarshalToFastEspdu.setText("Generate FastEntityStatePdus (fewer objects to instantiate)");
        unmarshalToFastEspdu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                unmarshalToFastEspduItemStateChanged(evt);
            }
        });

        jLabel11.setFont(jLabel11.getFont().deriveFont(jLabel11.getFont().getStyle() & ~java.awt.Font.BOLD));
        jLabel11.setText("<html>Listen for traffic and try to unmarshal the packets. All packets are saved in a list so that they will not be garbage collected. What you are seeing in the memory view when the garbage collector runs is the removal of any junk objects required in the <strong>creation</strong> of the packets. <strong>Notice:</strong> Although memory usage grows steadily (packets <em>are</em> being created after all) it grows more slowly with ByteBuffers and Fast Entity State PDUs turned on.</html>");

        javax.swing.GroupLayout receiverPanelLayout = new javax.swing.GroupLayout(receiverPanel);
        receiverPanel.setLayout(receiverPanelLayout);
        receiverPanelLayout.setHorizontalGroup(
            receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(receiverPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(unmarshalToFastEspdu)
                    .addGroup(receiverPanelLayout.createSequentialGroup()
                        .addGroup(receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(recvPortField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(recvGroupField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(119, 119, 119))
                    .addComponent(unmarshalWithByteBufferCheckbox)
                    .addGroup(receiverPanelLayout.createSequentialGroup()
                        .addComponent(startStopButton)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stateLabel))
                    .addGroup(receiverPanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(recvRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addContainerGap())
        );
        receiverPanelLayout.setVerticalGroup(
            receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(receiverPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(recvPortField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(recvGroupField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(unmarshalWithByteBufferCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(unmarshalToFastEspdu)
                .addGap(18, 18, 18)
                .addGroup(receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startStopButton)
                    .addGroup(receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(stateLabel)))
                .addGap(18, 18, 18)
                .addGroup(receiverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(recvRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("PDU Receiver", receiverPanel);

        memoryViewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Memory View"));
        memoryViewPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                    .addComponent(memoryViewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memoryViewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startFloodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startFloodButtonActionPerformed
        if( senderSW == null ){
            startFlood();
        } else {
            stopFlood();
        }
}//GEN-LAST:event_startFloodButtonActionPerformed

    private void marshalWithByteBufferCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_marshalWithByteBufferCheckboxItemStateChanged
        JCheckBox box = (JCheckBox)evt.getSource();
        if( evt.getStateChange() == ItemEvent.SELECTED ){
            this.marshalWithByteBuffer = true;
        } else if( evt.getStateChange() == ItemEvent.DESELECTED ){
            this.marshalWithByteBuffer = false;
        }
}//GEN-LAST:event_marshalWithByteBufferCheckboxItemStateChanged

    private void burstSizeFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_burstSizeFieldFocusLost
        JFormattedTextField field = (JFormattedTextField)evt.getSource();
        try {
            field.commitEdit();
            this.burstSize = (Integer)field.getValue();
            int sliderMax = this.burstSizeSlider.getMaximum();
            int sliderMin = this.burstSizeSlider.getMinimum();
            this.burstSizeSlider.setValue( Math.max(sliderMin,Math.min(sliderMax,this.burstSize) ) );

        } catch (ParseException ex) {
            Logger.getLogger(PduByteBufferTester.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_burstSizeFieldFocusLost

    private void burstSizeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_burstSizeSliderStateChanged
        JSlider slider = (JSlider)evt.getSource();
        if( slider.getValueIsAdjusting() ){
            this.burstSize = slider.getValue();
            this.burstSizeField.setValue(this.burstSize);
        }
}//GEN-LAST:event_burstSizeSliderStateChanged

    private void recvPortFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_recvPortFieldFocusLost
            try {
                JFormattedTextField field = (JFormattedTextField) evt.getSource();
                field.commitEdit();
                this.pduServer.setPort((Integer)field.getValue());
            } catch (ParseException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
}//GEN-LAST:event_recvPortFieldFocusLost

    private void recvGroupFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recvGroupFieldActionPerformed
            JTextField field = (JTextField)evt.getSource();
            String val = field.getText();
            this.pduServer.setGroups(val);
}//GEN-LAST:event_recvGroupFieldActionPerformed

    private void recvGroupFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_recvGroupFieldFocusLost
            JTextField field = (JTextField)evt.getSource();
            String val = field.getText();
            this.pduServer.setGroups(val);
}//GEN-LAST:event_recvGroupFieldFocusLost

        private void startStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startStopButtonActionPerformed
            UdpServer.State state = this.pduServer.getState();
            switch( state ){
                case STOPPED:
                    this.pduServer.start();
                    break;
                case STARTED:
                    this.pduServer.stop();
                    this.receivedPdus.clear();
                    break;
                default:
                    System.err.println("Shouldn't see this. State: " + state );
                    break;
            }
}//GEN-LAST:event_startStopButtonActionPerformed

        private void unmarshalWithByteBufferCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_unmarshalWithByteBufferCheckboxItemStateChanged
        JCheckBox box = (JCheckBox)evt.getSource();
        if( evt.getStateChange() == ItemEvent.SELECTED ){
            this.pduServer.setUseByteBuffer(true);
        } else if( evt.getStateChange() == ItemEvent.DESELECTED ){
            this.pduServer.setUseByteBuffer(false);
        }
}//GEN-LAST:event_unmarshalWithByteBufferCheckboxItemStateChanged

        private void unmarshalToFastEspduItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_unmarshalToFastEspduItemStateChanged
        JCheckBox box = (JCheckBox)evt.getSource();
        if( evt.getStateChange() == ItemEvent.SELECTED ){
            this.pduServer.setUseFastEspdu(true);
        } else if( evt.getStateChange() == ItemEvent.DESELECTED ){
            this.pduServer.setUseFastEspdu(false);
        }
}//GEN-LAST:event_unmarshalToFastEspduItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PduByteBufferTester().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField actualSendRateField;
    private javax.swing.JFormattedTextField burstSizeField;
    private javax.swing.JSlider burstSizeSlider;
    private javax.swing.JTextField destIp;
    private javax.swing.JFormattedTextField destPortField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JCheckBox marshalWithByteBufferCheckbox;
    private javax.swing.JPanel memoryViewPanel;
    private javax.swing.JPanel receiverPanel;
    private javax.swing.JTextField recvGroupField;
    private javax.swing.JFormattedTextField recvPortField;
    private javax.swing.JFormattedTextField recvRateField;
    private javax.swing.JProgressBar sendProgress;
    private javax.swing.JPanel senderPanel;
    private javax.swing.JButton startFloodButton;
    private javax.swing.JButton startStopButton;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JCheckBox unmarshalToFastEspdu;
    private javax.swing.JCheckBox unmarshalWithByteBufferCheckbox;
    // End of variables declaration//GEN-END:variables

}
