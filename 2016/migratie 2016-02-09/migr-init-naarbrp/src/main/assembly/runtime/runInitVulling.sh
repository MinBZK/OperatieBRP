#!/bin/bash

# store process ids in the "pids" file
pidfile=initruntime.pid

nohup java -Xmx256M -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarbrp.Main -sync_pers >output-run.txt 2>&1 &
initpid=$!
echo $initpid >"$pidfile"
