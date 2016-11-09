# Possible properties to pass into the program: 
#
# networkMode: unicast or multicast or broadcast
# destinationIp: destination IP address. Could be mcast if in mcast mode, bcast if in bcast mode 
# port: Both source and destination port

mvn exec:java -Dexec.mainClass=edu.nps.moves.examples.EspduReceiver 
