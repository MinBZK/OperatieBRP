#!/bin/bash
mvn clean install -P LOCALHOST,nieuwste,tomcat,levering,protocollering,bijhouding,bevraging,levering-synchronisatie,levering-afnemerindicaties -Dnieuwste
