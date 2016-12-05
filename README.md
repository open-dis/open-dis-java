# open-dis-java
Open-DIS git repository for the Java implementation. 

Distrubted Interactive Simulation (DIS) is a IEEE and SISO standard used
in military simulations. This is a Java implementation of that standard. 
There are similar implementations for other languages.

## Using


## Git Submodule

The open-dis-objectivec repo uses a submodule git repository that has the XML file description of the protocol
messages, DISDescription. This lets the XML file be shared across multiple language
implementations. If you want to generate source code, as opposed to just
using it, you should pull in the git submodules. Generating source code should be a unusual event
for most users, and is of interest in a practical sense only if you're also changing
around XMLPG, which generates Objective-C source code from the XML description
file.

Pull in the git submodule with

~~~~
git submodule init
git submodule update
~~~~

This clones the repository at https://github.com/open-dis/DISDescription.git and places
it in the DISDescription directory. Once this is present you can re-generate the
source code.


[![Build Status](https://travis-ci.org/open-dis/open-dis-java.svg?branch=master)](https://travis-ci.org/open-dis/open-dis-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/edu.nps.moves/open-dis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/edu.nps.moves/open-dis)
