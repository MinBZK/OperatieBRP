#!/bin/bash
for i in `ps ax | grep nl.moderniseringgba.migratie.runtime.Main | cut -d ' ' -f 1`
do
  kill -9 $i
done
