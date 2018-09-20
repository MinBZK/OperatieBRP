@echo off
docker network create tb02

echo Stoppen oude omgeving ...
docker-compose -p tb02 -f toevalligegebeurtenis.yml kill

echo Opruimen oude omgeving ...
docker-compose -p tb02 -f toevalligegebeurtenis.yml rm -vf

echo Starten logging ...
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d elasticsearch logstash 
timeout 5

echo Starten data stores ...
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d iscrouteringdatabase activemqdatabase lockingdatabase voiscdatabase brpdatabase archiveringdatabase iscdatabase
timeout 5

echo Starten overige stores ...
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d sleutelbos mailbox
timeout 5

echo Starten message brokers ...
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d brpmessagebroker iscroutering
timeout 5

echo Starten overige componenten ...
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d

echo Omgeving gereed
