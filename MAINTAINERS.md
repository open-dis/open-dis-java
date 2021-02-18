This document is useful for maintainers of the library only.

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
