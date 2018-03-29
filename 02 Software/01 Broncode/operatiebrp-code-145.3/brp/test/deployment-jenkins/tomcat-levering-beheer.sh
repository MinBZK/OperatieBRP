#!/bin/bash
mvn clean install -P LOCALHOST,nieuwste,tomcat,beheer,levering,admhnd-publicatie,protocollering,bijhouding,bevraging,levering-synchronisatie,levering-afnemerindicaties,vrijbericht,archivering -Dnieuwste
