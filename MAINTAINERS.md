This document is useful for maintainers of the library only.

### Software release process

Once enough changes have been made we cut a new release and deploy it to Maven Central so that other developers can use the Open DIS library in their project.

Steps:

1. Draft the description of the new release on GitHub: https://github.com/open-dis/open-dis-java/releases
2. Perform the steps to cut the release and deploy to Maven Central. For more info view this [guide](https://central.sonatype.org/pages/apache-maven.html).

In a nutshell the maintainer performing the release will need:
 * A Sonatype JIRA account associated with the `edu.nps.moves` groupId.
 * Your JIRA credentials placed in your local `~/.m2/settings.xml`
 * Your GPG key published

Once that's done, run the following commands to release the Open DIS library artifacts to a OSSRH staging area:

    $ mvn release:clean release:prepare
    $ mvn release:perform

Then log into the [OSSRH user interface](https://oss.sonatype.org/) to release the staged artifacts to Central. For more info view this [guide](https://central.sonatype.org/pages/releasing-the-deployment.html)
