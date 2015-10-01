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

/**
 * The BehaviorProducer interface describes a class that listens
 * in its own thread for PDUs, then notifies any listeners when
 * they arrive.<p>
 *
 * This implements a listener pattern. Those interested in hearing
 * from the BehaviorProducerIF register with the object. When the BehaviorProducerIF
 * gets (or produces) a PDU, it notifies all listeners. Note that there is
 * no attempt to limit which listeners/consumers receive the PDU.<p>
 *
 * @author DMcG
 * @version $Id:$
 */ 
 public interface BehaviorProducerIF {

   /**
    * Add a listener that will be notified when a PDU is
    * ready.
    *
    * @param consumer the object that will be notified of the PDU
    */
   void addListener(BehaviorConsumerIF consumer);
   
   /**
    * Remove a listener/consumer of PDUs from the notification list.
    *
    * @param consumer to be removed from the notification list
    */
   void removeListener(BehaviorConsumerIF consumer);
   
   /**
    * This is a performance option. When a PDU arrives we want to distribute
    * it to all listeners. If we use a single copy of the object distributed
    * to all listeners this may cause problems if one listener modifies the
    * object and undermines the expectations of another listener. to avoid this
    * we can create a new copy of the PDU and hand off a new, unique copy of
    * the object to each listener. But this may cause some performance problems,
    * since it takes a while to allocate a new object.<p>
    *
    * The default behavior should be to distribute a new, unqiue copy to each
    * listener. this allows the user to override this behavior for better
    * performance.
    *
    * @param shouldCreateCopy true to create a new copy for each listener, false for a shared copy for each listener
    */
    void setUseCopies(boolean shouldCreateCopy);
    
 }
