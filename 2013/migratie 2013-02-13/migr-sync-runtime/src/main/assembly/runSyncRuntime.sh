#!/bin/bash
cp -lf migr-sync-runtime-*.jar migr-sync-runtime.jar
processcount=$1
if [ -z "$1" ]; then
processcount=1
fi

counter=1
while [ $counter -le $processcount ]
do
nohup java -cp "migr-sync-runtime.jar:./lib/*" -Xmx256M nl.moderniseringgba.migratie.runtime.Main -config "<xml configuratie bestand>" >nohup${counter}.out &
counter=$(( $counter + 1 ))
done
