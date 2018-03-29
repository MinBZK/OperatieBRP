#!/bin/bash

# store process ids in the "pids" file
pidfile=initruntime_afnind.pid

nohup java -Xms256m -Xmx512m -XX:MaxPermSize=256m -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -sync_afn >output-runafnind.txt 2>&1 &
initafnindpid=$!
echo $initafnindpid >"$pidfile"
