#!/bin/bash
JAVA_OPTIONS=

if  [ "$1" = "memory" ] 
then
	echo Using in-memory database
	JAVA_OPTIONS="$JAVA_OPTIONS -Dspring.profiles.active=memoryDS"
	shift
fi

java -Dtest.directory=$1 -Dtest.thema=$2 -Dtest.casus=$3 $JAVA_OPTIONS -cp "./lib/*" org.junit.runner.JUnitCore  nl.bzk.migratiebrp.test.expressie.ParameterizedTest