#!/bin/sh
export CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=localhost -Dcom.sun.management.jmxremote.port=$JMX_PORT
-Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun
.management.jmxremote.local.only=false -Djava.rmi.server.hostname='$DOCKER_SERVER'"

# TODO opschonen
export ROUTERINGCENTRALE_PORT_61616_TCP_PORT=61616
