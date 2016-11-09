# Runs a generator that sends many or most of the PDU types, and saves an 
# example XML file.

mvn exec:java -Dexec.mainClass=edu.nps.moves.disutil.ConcatenatedDisPdus -Dexec.args="dispackets.disbin"
