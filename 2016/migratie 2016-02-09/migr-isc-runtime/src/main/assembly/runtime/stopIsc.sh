#!/bin/bash

pidfile=pids

while read pid
do
  ps -f $pid | grep nl.bzk.migratiebrp.isc.runtime.Main >/dev/null
  isrunning=$?
  if [ $isrunning -eq 0 ]
  then
    echo "stopping $pid"
    kill $pid
  else
    echo "skipping $pid [is not running]"
  fi
done < "$pidfile"

echo -n >"$pidfile"
