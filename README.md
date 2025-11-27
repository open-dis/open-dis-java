# Open DIS for Java

[![Release](https://jitpack.io/v/open-dis/open-dis-java.svg)](https://jitpack.io/#open-dis/open-dis-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/edu.nps.moves/open-dis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/edu.nps.moves/open-dis)
[![Javadocs](http://www.javadoc.io/badge/edu.nps.moves/open-dis.svg)](http://www.javadoc.io/doc/edu.nps.moves/open-dis)

## Introduction

This repository contains a Java implementation of the Distributed Interactive Simulation (DIS) IEEE-1278 standard, which is often used in military simulations.

The library consists of classes that represent Protocol Data Units (PDUs).
These classes have fields, getters, and setters, and are able to marshal and unmarshal themselves to and from the DIS binary format.
Many of the classes were initially generated with [XMLPG](http://github.com/open-dis/xmlpg).

The library also provides supporting classes that read and write PDUs from the network, log PDUs to a file, and more.

## Documentation

Javadocs for the current and previous artifact versions can be found on [javadoc.io](https://www.javadoc.io/doc/edu.nps.moves/open-dis/).
Or to generate javadocs yourself run `mvn javadoc:javadoc`.

Example code can be found in the [repository](src/main/java/edu/nps/moves/examples/).

## Using the Java Library

### Maven

There are two repositories where Open DIS can be found; JitPack and Maven Central. Read below before making a choice.

#### JitPack

[JitPack](https://jitpack.io/) is a terrific service that enables you to include any development snapshot of the Open DIS java library as a Maven dependency in your project. 
A common use case for this is wanting to try the very latest code, prior to a formal release to Maven Central being made.
To do so, do the following: 

1. Add the jitpack repository to your project `pom.xml`.

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
```

2. And this to your `<dependencies>` section in your `pom.xml`. And replace the value in `<version>` with any commit SHA1 from this projects history.

```xml
<dependency>
    <groupId>com.github.open-dis</groupId>
    <artifactId>open-dis-java</artifactId>
    <version>cc7ea44c1da0611ac67141ede060c4d2c6b0cfc0</version> <!-- replace with any git SHA1 -->
</dependency>
```

When the JitPack service is used for the first time it is common your Maven project build will fail while awaiting the Open DIS dependency to be downloaded. 
This happens if you are the first to request a particular build of the Open DIS dependency -- JitPack is busy compiling the jar and the Maven build will time out.
Wait 5-10 minutes, then retry your Maven project build.
Future builds of your project will succeed and be much faster because the jar has been cached.

#### Maven Central

Official releases published by the Open DIS maintainers can be found on Maven Central. These are less frequent, but can be more stable because they are conciously released.

Add the following to your `pom.xml`.

```xml
<dependency>
    <groupId>edu.nps.moves</groupId>
    <artifactId>open-dis</artifactId>
    <version>5.9</version>
</dependency>
```

### Ant

Include the `open-dis` `jar` in your project and all it's dependencies (i.e. `dis-enums`, `srm`, `commons-math3`). The exact versions of these jars can easily be identified by viewing the open-dis relase `pom.xml` on [Maven Central](https://maven-badges.herokuapp.com/maven-central/edu.nps.moves/open-dis) and then downloading these jars manually from Maven Central.

## License

All code is BSD license. See [LICENSE.md](LICENSE.md).

## For Maintainers

See [MAINTAINERS.md](MAINTAINERS.md).
