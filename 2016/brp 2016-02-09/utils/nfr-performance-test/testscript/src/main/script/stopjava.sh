#!/bin/bash

source omgeving.sh

#pdb97 database


#routeringcentrale
echo 'Schoon routeringcentrale'
ssh $user@$routering 'killall -9 java'
ssh $user@$routering 'rm -rf tomcat'


#mutatielevering 1
echo 'Schoon mutatielevering 1'
ssh $user@$mutatielevering1 'killall -9 java'
ssh $user@$mutatielevering1 'rm -rf tomcat'

#mutatielevering 2
echo 'Schoon mutatielevering 2'
ssh $user@$mutatielevering2 'killall -9 java'
ssh $user@$mutatielevering2 'rm -rf tomcat'

#verzending + afnemer
echo 'Schoon verzending + afnemer'
ssh $user@$verzending 'killall -9 java'
ssh $user@$verzending 'rm -rf tomcat'

#protocollering
echo 'Schoon protocollering'
ssh $user@$protocollering 'killall -9 java'
ssh $user@$protocollering 'rm -rf tomcat'

#loadgenerator
echo 'Schoon loadgenerator'
ssh $user@$loadgenerator 'killall -9 java'
ssh $user@$loadgenerator 'rm -rf *.jar'




