#!/bin/bash

# store process ids in the "pids" file
pidfile=pids

processcount=$1

# default to 1 process if no parameter given
if [ -z "$1" ]; then
  processcount=1
fi

touch "$pidfile"

counter=1

# increment counter to account for running processes
while read pid
do
  counter=$(( $counter + 1 ))
  processcount=$(( processcount + 1 ))
done < "$pidfile"

while [ $counter -le $processcount ]
do
  nohup java -cp "conf:lib/*" -Datomikos.unique.name=iv_${counter} -Djdk.xml.entityExpansionLimit=0 -Xmx128M nl.bzk.migratiebrp.init.logging.runtime.Main >output-${counter}.txt 2>&1 &
  logpid=$!
  echo $logpid >>"$pidfile"
  counter=$(( $counter + 1 ))
done
