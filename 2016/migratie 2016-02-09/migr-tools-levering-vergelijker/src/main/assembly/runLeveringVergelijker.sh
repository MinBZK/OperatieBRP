#!/bin/bash

# store process ids in the "pids" file
pidfile=pids

touch "$pidfile"

java -cp "./lib/*:." -Xms1024M -Xmx2048M nl.bzk.migratiebrp.tools.levering.vergelijker.LeveringVergelijkerRuntimeMain "$@" >>output-run.txt 2>&1 &

tellingpid=$!
echo $tellingpid >>"$pidfile"
