#!/bin/bash

# This script allows remote debugging of the Jetty instance while keeping the hot code reload
# advantages of mvn jetty:run (as opposed to mvn jetty:run-forked).

export MAVEN_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
mvn jetty:run
