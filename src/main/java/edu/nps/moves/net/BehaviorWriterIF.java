/*
 Copyright (c) 1995-2009 held by the author(s).  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer
      in the documentation and/or other materials provided with the
      distribution.
    * Neither the names of the Naval Postgraduate School (NPS)
      Modeling Virtual Environments and Simulation (MOVES) Institute
      (http://www.nps.edu and http://www.movesinstitute.org)
      nor the names of its contributors may be used to endorse or
      promote products derived from this software without specific
      prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/
package edu.nps.moves.net;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * This interface lets you set up some very general defaults, which
 * will be handled by the concrete implementations.<p>
 *
 * In general, you write to a destination. That destination is represented
 * by an IP address and a port.  You can also set a "default destination", where
 * things will go unless you specify otherwise.<p>
 *
 * @author DMcG
 * @version $Id:$
 */
public interface BehaviorWriterIF {

    /** The (rough) size of an ethernet frame */
    static final int MTU_SIZE = 1500;

    /**
     * Set the default destination that the plain write(pdu) method
     * will send data to.
     *
     * @param addr first object that describes destination (eg, IP)
     * @param port second object that describes destination (eg, port number)
     */
    void setDefaultDestination(InetAddress addr, int port);

    /**
     * Write PDU information to the default destination.  The user must clear
     * the buffer if it is desired to be reused.</p>
     *
     * @param buffer the DIS PDU infomation to be written
     */
    void write(ByteBuffer buffer);
} 
