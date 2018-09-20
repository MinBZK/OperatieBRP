#!/bin/bash
nohup java -cp "migr-sync-runtime.jar:./lib/*" nl.moderniseringgba.migratie.runtime.JmxStandaloneConsole $@ >nohup-jmxconsole.out &
echo $! > jmxConsole.pid

