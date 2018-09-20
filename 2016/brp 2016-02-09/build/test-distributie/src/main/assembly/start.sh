#!/bin/bash

export JAVA_OPTS="-Xms512m -Xmx2g -XX:MaxPermSize=256m"

java $JAVA_OPTS -cp ":jars/*" -Dstoryfilter="testcases/intake/**/*.story" org.junit.runner.JUnitCore nl.bzk.brp.testrunner.ArtStoryRunner
