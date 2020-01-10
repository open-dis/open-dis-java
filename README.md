# Open DIS for Java

[![Build Status](https://travis-ci.org/open-dis/open-dis-java.svg?branch=master)](https://travis-ci.org/open-dis/open-dis-java)
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

[JitPack](https://jitpack.io/) is a terrific service that enables you to include any development snapshot of the Open DIS java library as a Maven dependency in your project. A common use case is someone who wants to try the latest code because it contains a fix that was recently merged in. To use this service do the following: 

1. Add the jitpack repository to your project `pom.xml`.

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
```

2. And this to your `<dependencies>` section, replacing the `<version>` value with any SHA1 from this library you wish.

```xml
<dependency>
    <groupId>com.github.open-dis</groupId>
    <artifactId>open-dis-java</artifactId>
    <version>d3c2b19aed8e80c5e7ef938dc0982f5ad0282ae6</version> <!-- replace with any git SHA1 -->
</dependency>
```

Note: When you use the JitPack service for the first time it is common your project build will time out and then fail. This is because your request likely has triggered a new build that no one else has and the JitPack servers are busy doing that for you. If this happens, retry you project build 5-10 minutes later and it should succeed this time. Future builds of your project will be much faster because the library artifact will have been cached on jitpack servers and be stored locally on your disk.

#### Maven Central

Official releases published by the Open DIS maintainers can be found on Maven Central. These are less frequent, but can be more stable because they are conciously released.

Add the following to your `pom.xml`.

```xml
<dependency>
    <groupId>edu.nps.moves</groupId>
    <artifactId>open-dis</artifactId>
    <version>5.0</version>
</dependency>
```

### Ant

Include the `open-dis-<version-number>.jar` file in your project, found on the [releases](https://github.com/open-dis/open-dis-java/releases) page of this GitHub project.

## License

All code is BSD license. See `LICENSE.md`.

## For Contributors

This section is useful for developers who may be contributing to the library.
This contains info about "how the hotdog is made".

### Software release process

Once enough changes have been made we cut a new release and deploy it to Maven Central.

For more info view this [guide](https://central.sonatype.org/pages/apache-maven.html).

In a nutshell the person performing the release will need:
 * A Sonatype JIRA account associated with the `edu.nps.moves` groupId.
 * Your JIRA credentials placed in your `~/.m2/settings.xml`
 * Your GPG key published

Once that's done, do the following commands to release it to a OSSRH staging area:

    $ mvn release:clean release:prepare
    $ mvn release:perform

Then log into the [OSSRH user interface](https://oss.sonatype.org/) to release the staged artifacts to Central. For more info view this [guide](https://central.sonatype.org/pages/releasing-the-deployment.html)
