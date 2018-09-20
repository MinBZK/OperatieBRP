#!/bin/bash

# store process ids in the "pids" file
pidfile=pids

touch "$pidfile"

if [ -z ${JMX_PORT+x} ]; then JMX_PORT=1099; fi

nohup java -cp "conf:lib/*" -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false nl.bzk.migratiebrp.tools.mailbox.MailboxMain >output.txt 2>&1 &

syncpid=$!
echo $syncpid >>"$pidfile"
