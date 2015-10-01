
package edu.nps.moves.disutil;

import java.nio.*;
import java.util.*;

/**
 * A pool that holds ByteBuffer objects. <p>
 *
 * We'd like to reuse buffers for sending, but this is tricky because the
 * Java NIO byte buffers can't expand. So if we try to marshal a PDU that is
 * 144 bytes long and one that is 800 bytes long, we'd always need a byte
 * buffer that is 800 bytes long.
 *
 * This has some obvious problems if we generate 2000 byte buffer objects, which
 * will have a big memory footprint that won't be GC'd. If this turns out to be
 * a problem--I suspect not, right now--we can implement some sort of clear()
 * operation to wipe out the pool once some criteria is met.<p>
 *
 * Instances of this class are defitnely not thread-safe. If you have two threads,
 * you should have a ByteBufferPool for each thread to ensure that the same byte buffer
 * from the pool isn't being used by two threads.
 * 
 * @author DMcG
 */
public class ByteBufferPool
{

    private HashMap<Integer, ByteBuffer> pool;


    public ByteBufferPool()
    {
        pool = new HashMap<Integer, ByteBuffer>();
    }

    /**
     * Returns a byte buffer from the pool that has the given length.
     * If there is not ByteBuffer with that length, it is created and
     * added to the pool.
     *
     * @param length
     * @return The byte buffer with the given length
     */
    public ByteBuffer getByteBufferOfLength(int length)
    {
        ByteBuffer bbuf = pool.get(new Integer(length));

        if(bbuf == null)
        {
            byte[] backingStore = new byte[length];
            bbuf = ByteBuffer.wrap(backingStore);
            pool.put(new Integer(length), bbuf);
        }

        return bbuf;
    }

    /**
     * Removes all the byte buffers from the pool, allowing them to
     * be GC'd. 
     */
    public void clear()
    {
        pool.clear();
    }

}
