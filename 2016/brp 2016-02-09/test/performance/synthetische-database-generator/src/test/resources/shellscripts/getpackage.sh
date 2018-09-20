#!/bin/bash

echo -n "svn user : "
read -e SVN_UN

echo -n "svn password : "
read -e SVN_PW

echo -n "Url uit artifactory: "
read -e URL

wget --user=$SVN_UN --password=$SVN_PW --no-check-certificate -r --no-parent --no-directories $URL
