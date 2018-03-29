#!/bin/bash

mvn -f brp/brp-code/test/deployment-jenkins/pom.xml clean install -o -U -P LOCALHOST,nieuwste,tomcat,levering,admhnd-publicatie,levering-synchronisatie,levering-afnemerindicaties,vrijbericht,archivering -Dnieuwste
