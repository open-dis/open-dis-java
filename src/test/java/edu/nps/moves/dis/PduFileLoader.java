package edu.nps.moves.dis;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PduFileLoader {

    // Load a Signal Pdu captured with Wireshark
    public static byte[] load(String pduResourceName) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(PduFileLoader.class.getResourceAsStream(pduResourceName));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        while ((nRead = bis.read()) != -1) {
            buffer.write(nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
}
