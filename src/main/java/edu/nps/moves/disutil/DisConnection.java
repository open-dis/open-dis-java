package edu.nps.moves.disutil;

import edu.nps.moves.dis.Pdu;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DisConnection implements Runnable {

    private final DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress addr;
    private final PduFactory pduFactory = new PduFactory();
    private final BlockingQueue<Pdu> msgQ = new LinkedBlockingQueue<>();
    private static final int MAX_PDU_SIZE = 16384;
    private static final int MAX_PDU_QUEUE_SIZE = 500;

    public DisConnection(String multicastAddress, int port) throws IOException {
        init();
        socket = new MulticastSocket(port);
        addr = InetAddress.getByName(multicastAddress);
        ((MulticastSocket) socket).joinGroup(addr);
        //((MulticastSocket) socket).setLoopbackMode(true); // disable loopback
    }

    public DisConnection(int port) throws IOException {
        init();
        socket = new DatagramSocket(port);
    }

    private void init() {
        byte buffer[] = new byte[MAX_PDU_SIZE];
        packet = new DatagramPacket(buffer, buffer.length);
    }

    public void terminate() {
        socket.disconnect();
        try {
            if (socket instanceof MulticastSocket) {
                ((MulticastSocket) socket).leaveGroup(socket.getInetAddress());
            }
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void handleMessage(Pdu pdu) {
        synchronized (msgQ) {
            msgQ.add(pdu);
            if (msgQ.size() > MAX_PDU_QUEUE_SIZE) {
                Logger.getLogger(getClass().getName()).warning("Pdu buffer overflow, clearing.");
                msgQ.clear();
            }
        }
    }

    public synchronized Pdu getNext() throws InterruptedException {
        return msgQ.take();
    }

    public void send(Pdu pdu) throws IOException {
        socket.send(new DatagramPacket(pdu.marshal(), pdu.getLength(), addr, socket.getLocalPort()));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                socket.receive(packet);
                Pdu pdu = pduFactory.createPdu(packet.getData());
                if (pdu != null) {
                    handleMessage(pdu);
                }
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                break;
            }
        }
        terminate();
    }
}

