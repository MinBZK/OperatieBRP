#!/bin/sh
export JAVA_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.awt.headless=true -Xmx3G -server -Xms1G -XX:NewSize=256m -XX:MaxNewSize=356m -XX:PermSize=256m -XX:ReservedCodeCacheSize=128m -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
