#!/usr/bin/env bash
echo -e "Tomcat stoppen"
TOMCAT_PIDS=`ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
echo -e "Gevonden TOMCAT processen: $TOMCAT_PIDS"
if [ "$TOMCAT_PIDS" != "" ]
then
   echo -e "Hard stoppen van Tomcat processen: $TOMCAT_PIDS"
   kill -9 $TOMCAT_PIDS
   echo -e "KILLED"
else
   echo -e "Geen Tomcat gevonden die draait"
fi

exit 0;
