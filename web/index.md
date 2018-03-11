# Open DIS

Open-DIS is a free, open source implementation of the Distributed
Interactive Simulation (DIS) protocol standardized as IEEE-1278. Both
C++ and Java implementations are provided under the BSD open source
license.

Both the Java and C++ code implementations are generated from an XML
file. This allows much of the boilerplate code to be generated from a
relatively simple XML file that can be created by a non-expert
programmer. As of this writing, the XML file that describes the DIS
protocol is about 4,000 lines long, but generates about 20,000 lines of
Java code and 30,000 lines of C++ code, for a total of about 50,000
lines of code. The C++ code has been compiled under Microsoft Visual
C++ and gcc, and the Java code under JDK 1.6.

The XML code is not able to describe exactly all the PDUs. As of this
writing, about 45 PDUs are able to be completely described by the XML
file, meaning that both C++ and Java classes are correctly generated.
About 15 PDUs are not, and require manual intervention in the generated
code to correctly describe the PDU. The degree of manual intervention
required on the remaining PDUs varies; most seem fairly benign, but a
few are more complex. My guess is that the protocol can be finished in
less than a thousand lines of code.

Some rudimentary networking support is provided.

Assistance from others in reviewing and completing the code is welcome.
Send email to mcgredo &lt;at&gt; nps.edu if you are interested.

## Future Work

The plan is for the initially generated code to be reviewed by various
DIS practioners early in the development cycle. This allows for the
possiblity of the generated code to be changed early on. Once the code
is "close", the generated code will be checked into the Subversion
source code control system at sourceforge, and the remaining manual
changes required checked in. At that point the umbilical cord with the
code generator will be cut, and the code maintained manually from that
point forward.

Also not implemented are enumerations, the arbitrary numbers that give
PDU contents semantic meaning. Finite state machines that follow the
rules of the protocol are also not provided.<br>

The generated DIS source code can be downloaded from sourceforge.net at

http://sourceforge.net/project/showfiles.php?group_id=193363

The project description page is at 

http://open-dis.sourceforge.net

and the main project page is at 

http://sourceforge.net/projects/open-dis

### Delta3D

http://www.delta3d.org

![](images/Delta3d_logo.png)

### MOVES Institute

http://www.movesinstitute.org

![](images/moves-logo.jpg)

### Banzi Institute

http://www.banzai-institute.com

![](images/BBInstHead.jpg)
