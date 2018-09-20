#!/bin/bash

pidfile=pids

while read pid
do
  ps -f $pid | grep nl.bzk.migratiebrp.voisc.runtime.VoiscMain >/dev/null
  isrunning=$?
  if [ $isrunning -eq 0 ]
  then
    echo "Proces $pid is running"
  else
    echo "Proces $pid is not running"
  fi
done < "$pidfile"
