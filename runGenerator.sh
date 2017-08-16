# Runs a generator that sends many or most of the PDU types.
#
# Note: by default VBS2 runs on port 3000 with broadcast, so if you want to send# to VBS2, set the network mode to bcast, select the appropriate bcast address,
# and send to port 3000. You'll almost certainly have to adjust the location as
# well.

mvn exec:java -Dexec.mainClass=edu.nps.moves.examples.PduSender -Dexec.args="62040 239.1.2.3"
