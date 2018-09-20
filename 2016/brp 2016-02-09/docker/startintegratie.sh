#!/bin/bash

echo -e "\033[0;31mstoppen oude omgeving\033[0m"
docker-compose -p int -f integratie.yml kill

echo -e "\033[0;31mopruimen oude omgeving\033[0m"
docker-compose -p int -f integratie.yml rm -vf

echo -e "\033[0;31mstarten logging\033[0m"
docker-compose -p int -f integratie.yml up -d elasticsearch logstash
sleep 5

echo -e "\033[0;31mstarten databases en mailbox\033[0m"
docker-compose -p int -f integratie.yml up activemqdatabase lockingdatabase brpdatabase kerndatabase archiveringdatabase
docker-compose -p int -f integratie.yml start activemqdatabase lockingdatabase brpdatabase kerndatabase archiveringdatabase
docker-compose -p int -f integratie.yml up -d iscrouteringdatabase voiscdatabase iscdatabase sleutelbos mailbox
sleep 30

echo -e "\033[0;31mstarten message brokers\033[0m"
docker-compose -p int -f integratie.yml up -d brpmessagebroker iscroutering
sleep 15

echo -e "\033[0;31mstarten synchronisatie en gbacentrale\033[0m"
docker-compose -p int -f integratie.yml up -d synchronisatie brpgbacentrale iscsynchronisatie
sleep 65

echo -e "\033[0;31mbasis opgestart, overige componenten nu opstarten\033[0m"
#docker-compose -p int -f integratie.yml up -d
docker-compose -p int -f integratie.yml up -d iscconsole voisc isc kibana mutatielevering verzending

echo -e "\033[0;32momgeving klaar voor test\033[0m"

