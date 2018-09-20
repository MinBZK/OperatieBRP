#!/bin/bash
mvn clean install -U -P LOCALHOST,nieuwste,bijhouding,bevraging,protocollering,tomcat -Dnieuwste
