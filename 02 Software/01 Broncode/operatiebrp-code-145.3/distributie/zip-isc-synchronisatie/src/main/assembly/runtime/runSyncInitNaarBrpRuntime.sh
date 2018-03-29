#!/bin/bash

# store process ids in the "pids" file
pidfile=pids-initvulling

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

if [ -z ${JMX_PORT+x} ]; then JMX_PORT=4485; fi

while [ $counter -le $processcount ]
do
  JMX_INC_PORT=$(( $JMX_PORT + ($counter - 1) * 23))

  nohup java -cp "./lib/*:conf/" -Xmx512M -Datomikos.unique.name=iv_${counter} -Dsynchronisatie.jmx.serialize.port=$JMX_INC_PORT nl.bzk.migratiebrp.synchronisatie.runtime.Main -modus INITIELEVULLING >output-initv-${counter}.txt 2>&1 &
  syncpid=$!
  echo $syncpid >>"$pidfile"
  counter=$(( $counter + 1 ))
done
