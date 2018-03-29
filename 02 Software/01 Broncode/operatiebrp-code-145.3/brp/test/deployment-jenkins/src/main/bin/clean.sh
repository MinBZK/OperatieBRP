#!/bin/bash
TOMCAT="/opt/tomcat"
TOMCAT_STOP="/usr/bin/sudo /etc/init.d/tomcat7 stop"

echo "TOMCAT CLEANUP script."
tomcat_status=$(/usr/bin/java -jar /opt/auto-deploy/share/cmdline-jmxclient-0.10.3.jar - localhost:1099 Catalina:type=Service stateName 2>&1)
if [[ $tomcat_status == *STARTED* ]]
then
    echo "Stop tomcat eerst!"
    $TOMCAT_STOP
    sleep 15
fi

echo "Eerst de omgeving opruimen. Alle WAR's weghalen uit Tomcat
# Haal alle war's weg uit de tomcat directory
rm -rf /opt/tomcat/webapps/*

# Haal alles weg uit de jenkins directory
rm -rf /tmp/jenkins/*