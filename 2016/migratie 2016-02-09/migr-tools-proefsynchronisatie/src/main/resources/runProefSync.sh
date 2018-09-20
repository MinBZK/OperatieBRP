#!/bin/bash

# store process ids in the "pids" file
pidfile=pids

touch "$pidfile"

java -Xms512m -Xmx1024m -XX:MaxPermSize=512m -cp "./lib/*:." -Dlog4j.configuration=file:log4j.properties nl.bzk.migratiebrp.tools.proefsynchronisatie.Main -config proef-sync.properties -batchSize 10000 -timeout 1200000 >output-run.txt 2>&1 &

syncpid=$!
echo $syncpid >>"$pidfile"
