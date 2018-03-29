#!/bin/bash

# store process ids in the "pids" file
pidfile=initruntime_prot.pid

nohup java -Xms256m -Xmx512m -XX:MaxPermSize=256m -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -sync_prot >output-runprot.txt 2>&1 &
initprotpid=$!
echo $initprotpid >"$pidfile"
