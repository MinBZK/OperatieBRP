#!/bin/bash

# store process ids in the "pids" file
pidfile=pids

touch "$pidfile"

java -cp "./lib/*:." -Xmx1024M nl.bzk.migratiebrp.tools.proefsynchronisatie.Main -config proef-sync.properties -create -datumVanaf "$1" -datumTot "$2" >output-create.txt 2>&1 &

syncpid=$!
echo $syncpid >>"$pidfile"
