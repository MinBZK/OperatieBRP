#!/bin/bash
echo -e "\033[0;31mstoppen oude omgeving\033[0m"
docker-compose -p tb02 -f toevalligegebeurtenis.yml kill

echo -e "\033[0;31mopruimen oude omgeving\033[0m"
docker-compose -p tb02 -f toevalligegebeurtenis.yml rm -vf

echo -e "\033[0;31mstarten logging\033[0m"
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d elasticsearch logstash
sleep 5

echo -e "\033[0;31mstarten databases en mailbox\033[0m"
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d iscrouteringdatabase activemqdatabase lockingdatabase voiscdatabase brpdatabase archiveringdatabase iscdatabase sleutelbos mailbox
sleep 60 

echo -e "\033[0;31mstarten message brokers\033[0m"
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d brpmessagebroker iscroutering
sleep 35

echo -e "\033[0;31mstarten synchronisatie en gbacentrale\033[0m"
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d synchronisatie brpgbacentrale
sleep 35

echo -e "\033[0;31mbasis opgestart, overige componenten nu opstarten\033[0m"
docker-compose -p tb02 -f toevalligegebeurtenis.yml up -d

echo -e "\033[0;32momgeving klaar voor test\033[0m"
