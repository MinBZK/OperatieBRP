#!/bin/bash

# store process ids in the "pids" file
pidfile=initruntime_aut.pid

nohup java -Xms256m -Xmx512m -XX:MaxPermSize=256m -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -sync_aut >output-runaut.txt 2>&1 &
initautpid=$!
echo $initautpid >"$pidfile"
