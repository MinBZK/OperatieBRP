#!/bin/bash

echo -n "svn user : "
read -e SVN_UN

echo -n "svn password : "
read -e SVN_PW

echo -n "SynDbGen versie: "
read -e VERSIE

wget --user=$SVN_UN --password=$SVN_PW --no-check-certificate -r --no-parent --no-directories https://192.168.207.40/artifactory/libs-release-brp/nl/bzk/brp/TestDataGeneratie/$VERSIE/TestDataGeneratie-$VERSIE-jar-with-dependencies.jar
