@echo off
docker network create brp

echo Stoppen oude omgeving ...
docker-compose -p int -f integratie.yml kill

echo Opruimen oude omgeving ...
docker-compose -p int -f integratie.yml rm -vf

echo Starten logging ...
docker-compose -p int -f integratie.yml up -d elasticsearch logstash 
timeout 5

echo Initieren data stores ...
docker-compose -p int -f integratie.yml up activemqdatabase archiveringdatabase brpdatabase kerndatabase lockingdatabase

echo Starten data stores ...
docker-compose -p int -f integratie.yml up -d activemqdatabase archiveringdatabase brpdatabase kerndatabase lockingdatabase iscdatabase iscrouteringdatabase voiscdatabase
timeout 10

echo Starten overige stores ...
docker-compose -p int -f integratie.yml up -d sleutelbos mailbox
timeout 10

echo Starten message brokers ...
docker-compose -p int -f integratie.yml up -d brpmessagebroker iscroutering
timeout 10

echo Starten synchronisatie en gbacentrale
docker-compose -p int -f integratie.yml up -d synchronisatie brpgbacentrale iscsynchronisatie
timeout 30

echo Starten overige componenten ...
docker-compose -p int -f integratie.yml up -d afnemerindicaties bijhouding mutatielevering protocollering verzending isc voisc
timeout 10

echo Starten UI componenten ...
docker-compose -p int -f integratie.yml up -d beheer kibana iscconsole
timeout 10


echo Omgeving gereed
