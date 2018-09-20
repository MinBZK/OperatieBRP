#!/bin/bash
echo -e "\033[0;31mAanmaken integratie machine\033[0m"
docker-machine create -d virtualbox --virtualbox-memory 4096 integratie
eval `docker-machine env integratie`
docker network create brp
export DOCKER_IP=`docker-machine ip integratie`
echo -e "\033[0;32mIntegratie omgeving opgezet. Tegen deze docker omgeving de code bouwen. Hierna kan het script startintegratie.sh worden gedraaid voor de test\033[0m"
