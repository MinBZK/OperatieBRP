#!/bin/bash

source omgeving.sh

echo ''


echo 'BRP versie 89'
echo 'routering=perf-mgmt02
      mutatielevering1=pap95
      mutatielevering2=pap96
      verzending=pdb98
      loadgenerator=pdb03
      protocollering=pdb04
      database=pdb97'

echo ''
echo ''
echo ''
echo ''
echo ''
echo SYSTEEM DATABASE [$database]
ssh $user@$database "cat /proc/meminfo"
ssh $user@$database "cat /proc/cpuinfo"

echo ''
echo ''
echo ''
echo ''
echo ''
echo SYSTEEM ROUTERING [$routering]
ssh $user@$routering "cat /proc/meminfo"
ssh $user@$routering "cat /proc/cpuinfo"

echo ''
echo ''
echo ''
echo ''
echo ''
echo SYSTEEM MUTATIELEVERING 1 [$mutatielevering1]
ssh $user@$mutatielevering1 "cat /proc/meminfo"
ssh $user@$mutatielevering1 "cat /proc/cpuinfo"


echo ''
echo ''
echo ''
echo ''
echo ''
echo SYSTEEM MUTATIELEVERING 2 [$mutatielevering2]
ssh $user@$mutatielevering2 "cat /proc/meminfo"
ssh $user@$mutatielevering2 "cat /proc/cpuinfo"

echo ''
echo ''
echo ''
echo ''
echo ''
echo SYSTEEM VERZENDING  + DUMMY AFNEMER [$verzending]
ssh $user@$verzending "cat /proc/meminfo"
ssh $user@$verzending "cat /proc/cpuinfo"


echo ''
echo ''
echo ''
echo ''
echo ''
echo SYSTEEM PROTOCOLLERING [$protocollering]
ssh $user@$protocollering "cat /proc/meminfo"
ssh $user@$protocollering "cat /proc/cpuinfo"


echo ''
echo ''
echo ''
echo ''
echo ''
echo SYSTEEM LOADGENERATOR [$loadgenerator]
ssh $user@$loadgenerator "cat /proc/meminfo"
ssh $user@$loadgenerator "cat /proc/cpuinfo"

echo ''
echo ''
echo ''
echo ''
echo ''
echo 'SYSTEEM PROPERTIES'

cat deployables/brp-alles.properties