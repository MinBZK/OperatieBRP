@echo off
docker network create brp

echo Stoppen oude omgeving ...
docker-compose -p iv -f integratie.yml kill

echo Opruimen oude omgeving ...
docker-compose -p iv -f integratie.yml rm -vf

echo Starten data stores ...
docker-compose -p iv -f integratie.yml up -d brpdatabase
docker-compose -p iv -f integratie.yml up -d gbavdatabase
docker-compose -p iv -f integratie.yml up -d ggodatabase
timeout 10

echo Starten message brokers ...
docker-compose -p iv -f integratie.yml up -d hornetq
timeout 10

echo Starten overige componenten ...
docker-compose -p iv -f integratie.yml up -d iscsynchronisatienaarbrp 
docker-compose -p iv -f integratie.yml up -d iscsynchronisatienaarlo3 
docker-compose -p iv -f integratie.yml up -d isclogging
timeout 10

echo Starten UI componenten ...
docker-compose -p iv -f integratie.yml up -d ggoviewer
timeout 10

echo Omgeving gereed
