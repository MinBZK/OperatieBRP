#!/bin/bash

java -Dtest.directory=$1 -cp "./lib/*" org.junit.runner.JUnitCore nl.moderniseringgba.migratie.test.lg01.ParameterizedTest