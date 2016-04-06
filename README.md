# Open DIS for Java

[![Build Status](https://travis-ci.org/open-dis/open-dis-java.svg?branch=master)](https://travis-ci.org/open-dis/open-dis-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/edu.nps.moves/open-dis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/edu.nps.moves/open-dis)
[![Javadocs](http://www.javadoc.io/badge/edu.nps.moves/open-dis.svg)](http://www.javadoc.io/doc/edu.nps.moves/open-dis)

## Introduction

This repository contains a Java implementation of the Distributed Interactive Simulation (DIS) IEEE-1278 standard, which is often used in military simulations.

The library consists of classes that represent Protocol Data Units (PDUs).
These classes have fields, getters, and setters, and are able to marshal and unmarshal themselves to and from the DIS binary format.
Many of the classes were automatically generated via [XMLPG](http://github.com/open-dis/xmlpg). More on that below.

The library also provides supporting classes that read and write PDUs from the network, log PDUs to a file, and more.

## Using the Java Library

### Maven

Add the following to your `pom.xml`.

    <dependency>
        <groupId>edu.nps.moves</groupId>
        <artifactId>open-dis</artifactId>
        <version>4.08</version>
    </dependency>

### Ant

Include the `open-dis-<version-number>.jar` file in your project, along with the supporting jar files in the `lib` directory.
(The `xmlpg.jar` file is not needed.)

## Release Notes

### 4.x release highlights

* SQL support.
* Modified & rationalized repository layout.
* Added mobile support in addition to desktop support.

### 3.x release highlights

* CSharp implementation from Peter Smith. He wrote a new xmlpg class to generate CSharp code.

* Automated patch application. The Unix patch utility is used to modify the auotmatically generated code. See the patch directory in the open-dis directory. Patches are applied via the ant task "patch". Windows users; need to install cygwin utilities to be able to use patch, and make some minor changes to the ant build.xml file.

* Robert Harder has reworked the Java marshal and unmarshal code to use much more memory efficient NIO classes, which dramatically reduce the amount of temporary object memory generated.

* Sheldon Snyder has contributed dead reckoning algorithms in Java, available in the `edu.nps.moves.deadreckoning` package.

* Tariq Rashid has contributed an xplane gateway, which reads xplane flight simulator UDP packets and converts them to DIS. He's also implemented a KML gateway, which allows low-resolution updates to markers on Google Earth. This code is a bit rough right now--it needs to be cleaned up. There's no guarantee it works. This is available in the xplane directory of open-dis.

* xmlpg, which generates the source code, is now included in the open-dis lib directory. This allows code to be generated completly withing the open-dis directory, rather than trying to do cross-directory builds. See the `generateDisSourceCode` ant target.

* Enumerations. The SISO EBV XML document was used to generate Java enumeration classes. This can be extended as the EBV XML document is completed. A C++ enumerations project would be a useful project for some developer. The enumerations are used in several places in the open-dis code, notably the PduFactory.

* Unit tests. The Java code has added some JUnit 4.4 unit tests, primarily to test that post-processing source code patches have been applied correctly. These can be extended to provide more complete test coverage.

## License

All code is BSD license. See `License.txt`.

## For Contributors

This section is useful for developers who may be contributing to the library.
This contains info about "how the hotdog is made".

### XML Description File

The `DISXXXX.xml` files are an abstract description of the protocol.
These are processed by XMLPG to generate the Java protocol code in `edu.nps.moves.dis`.

To generate the Java code from scratch, type `ant generateJavaDisSourceCode`.

The idea is to use the XMLPG tool to get the final product "close", and then manually modify the source code to tweak the last bit.
To this end there is a "patches" directory, which uses the Unix `patch` utility to modify the generated code via patch files.

### Software release process

Once enough changes have been made we cut a new release and deploy it to Maven Central.

In a nutshell the person performing the release will need:
 * A Sonatype JIRA account
 * Your JIRA credentials placed in your `~/.m2/settings.xml`
 * Your GPG key published

For more info view this [guide](https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide).

Once that's done, for each release do the following commands:

    $ mvn release:clean
    $ mvn release:prepare
    $ mvn release:perform
