#!/bin/bash
# Custom shell script voor deployment check

tomcat_status=$(/usr/bin/java -jar /opt/auto-deploy/share/cmdline-jmxclient-0.10.3.jar - localhost:1099 Catalina:type=Service stateName 2>&1)

if [[ $tomcat_status == *STARTING* ]]
then
    echo "Tomcat is aan het opstarten....."
elif [[ $tomcat_status == *STARTED* ]]
then
    tomcat_opgestart=true
    echo "Tomcat volledig opgestart!"
elif [[ $tomcat_status == *STOPPED* ]]
then
    tomcat_opgestart=false
    echo "Tomcat gestopt, start nu opnieuw!"
    /usr/bin/sudo /etc/init.d/tomcat7 start &
else
    echo "Onverwacht gedrag gevonden."
    echo "Status: $tomcat_status"
fi

