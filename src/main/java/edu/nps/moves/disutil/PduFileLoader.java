package edu.nps.moves.disutil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// This is a utility class used by unit tests but may be useful for other purposes too.
public class PduFileLoader {

    // Load Pdu captured to file with Wireshark
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
