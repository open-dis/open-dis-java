This document is useful for maintainers of the library only.

### Software release process

Once enough changes have been made we cut a new release and deploy it to Maven Central so that other developers can use the Open DIS library in their project.

Pre-conditions:

The maintainer performing the release will need:

1. A Central Portal account associated with the `edu.nps.moves` groupId.
2. Your Portal Token placed in your local `~/.m2/settings.xml`
3. Your GPG key published

Release-Steps:

1. Perform the Maven Release steps to cut the release and deploy to Maven Central. For more info view this [guide](https://central.sonatype.org/register/central-portal/). Run the following commands to release the Open DIS library artifacts to a OSSRH staging area:

```
$ mvn release:clean release:prepare
$ mvn release:perform
```

2. Go to [Maven Central Repository](https://central.sonatype.org/), Login, click "Staging Repositories", select the artifact, click "Close", click "Refresh", then click "Release". For more info view this [guide](https://central.sonatype.org/pages/releasing-the-deployment.html)
3. Now go to the [Releases for the GitHub project](https://github.com/open-dis/open-dis-java/releases), click "Draft a new Release" button, select the Tag name used just now for the release, click "Generate release notes", and click the "Publish Release" button.
