#!/bin/bash
mvn clean install -U -P LOCALHOST,release,tomcat,levering,admhnd-publicatie,protocollering,bijhouding,bevraging,levering-synchronisatie,levering-afnemerindicaties,vrijbericht,archivering -Drelease
