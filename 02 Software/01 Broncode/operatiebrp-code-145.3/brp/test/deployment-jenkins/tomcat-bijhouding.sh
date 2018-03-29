#!/bin/bash
if [ $1 == "offline" ]; then
	mvn clean install -P LOCALHOST,nieuwste,bijhouding,bevraging,protocollering,tomcat -Dnieuwste

else
	mvn clean install -P LOCALHOST,nieuwste,bijhouding,bevraging,protocollering,tomcat -Dnieuwste
fi
