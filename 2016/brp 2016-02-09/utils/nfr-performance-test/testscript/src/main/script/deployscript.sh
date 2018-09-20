#!/bin/bash

source omgeving.sh
source stopjava.sh

#pdb97 database

echo 'handelingtabellen schonen'
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c 'update kern.admhnd set tslev = null where id in (select admhnd from kern.pers)'"

echo 'activemq tabellen schonen'
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c 'delete from public.activemq_msgs cascade'"

echo 'ber.ber schonen'
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c 'delete from ber.ber'"

echo 'lev.lev schonen'
ssh $user@$database "export PGPASSWORD=brp && psql -Ubrp --no-password -hlocalhost -dnfr_profiel1 -q -c 'delete from lev.lev'"

#routeringcentrale

scp deployables/tomcat.tar.gz $user@$routering:~
ssh $user@$routering 'tar xzvf tomcat.tar.gz'
scp deployables/routering-centrale.war $user@$routering:~/tomcat/webapps
scp deployables/brp-alles.properties $user@$routering:~/tomcat/lib
ssh $user@$routering './tomcat/bin/startup.sh'

#mutatielevering 1

scp deployables/tomcat.tar.gz $user@$mutatielevering1:~
ssh $user@$mutatielevering1 'tar xzvf tomcat.tar.gz'
scp deployables/mutatielevering.war $user@$mutatielevering1:~/tomcat/webapps
scp deployables/brp-alles.properties $user@$mutatielevering1:~/tomcat/lib
ssh $user@$mutatielevering1 './tomcat/bin/startup.sh'

#mutatielevering 2

scp deployables/tomcat.tar.gz $user@$mutatielevering2:~
ssh $user@$mutatielevering2 'tar xzvf tomcat.tar.gz'
scp deployables/mutatielevering.war $user@$mutatielevering2:~/tomcat/webapps
scp deployables/brp-alles.properties $user@$mutatielevering2:~/tomcat/lib
ssh $user@$mutatielevering2 './tomcat/bin/startup.sh'

#verzending + afnemer

scp deployables/tomcat.tar.gz $user@$verzending:~
ssh $user@$verzending 'tar xzvf tomcat.tar.gz'
scp deployables/verzending.war $user@$verzending:~/tomcat/webapps
scp deployables/brp-kennisgeving-ontvanger.war $user@$verzending:~/tomcat/webapps
scp deployables/brp-alles.properties $user@$verzending:~/tomcat/lib
ssh $user@$verzending './tomcat/bin/startup.sh'

#protocollering

scp deployables/tomcat.tar.gz $user@$protocollering:~
ssh $user@$protocollering 'tar xzvf tomcat.tar.gz'
scp deployables/protocollering.war $user@$protocollering:~/tomcat/webapps
scp deployables/brp-alles.properties $user@$protocollering:~/tomcat/lib
ssh $user@$protocollering './tomcat/bin/startup.sh'


#loadgenerator

scp deployables/NFR-load-generator.jar $user@$loadgenerator:~




