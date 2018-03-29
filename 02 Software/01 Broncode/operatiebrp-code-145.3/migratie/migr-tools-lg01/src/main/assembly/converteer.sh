#!/bin/bash

java -Dtest.directory=$1 -cp "./lib/*" org.junit.runner.JUnitCore nl.bzk.migratiebrp.tools.lg01.ParameterizedTest