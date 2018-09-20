#!/bin/bash
docker-machine start integratie
eval `docker-machine env integratie`
export DOCKER_IP=`docker-machine ip integratie`

