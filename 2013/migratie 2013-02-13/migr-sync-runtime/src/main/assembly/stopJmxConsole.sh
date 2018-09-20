#!/bin/bash
[ -f jmxConsole.pid ] || exit 1
pid=`cat jmxConsole.pid`
kill $pid

rm jmxConsole.pid
