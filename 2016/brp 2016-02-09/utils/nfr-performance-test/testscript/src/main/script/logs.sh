#!/bin/bash

source omgeving.sh

#pdb97 database



#routeringcentrale

ssh $user@$routering 'less tomcat/logs/catalina.out'

#mutatielevering 1

echo ''
ssh $user@$mutatielevering1 'less tomcat/logs/brp-systeem.log'

#mutatielevering 2

echo ''
ssh $user@$mutatielevering2 'less tomcat/logs/brp-systeem.log'

#verzending + afnemer

echo ''
ssh $user@$verzending 'less tomcat/logs/brp-systeem.log'

#protocollering

echo ''
ssh $user@$protocollering 'less tomcat/logs/brp-systeem.log'

#loadgenerator

echo ''
#ssh $user@$loadgenerator 'killall java'




