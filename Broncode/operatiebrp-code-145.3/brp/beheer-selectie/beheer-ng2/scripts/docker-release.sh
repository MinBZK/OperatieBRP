#!/usr/bin/env bash
#echo "Login Docker registry..."
#docker login -u ${jenkins.user} -p ${jenkins.password} fac-nexus3.modernodam.nl:5000

echo "Tag image..."
docker tag brp/beheer-ng2 fac-nexus3.modernodam.nl:${DOCKER_REGISTRY_PORT-5001}/brp/beheer-ng2

echo "Push..."
docker push fac-nexus3.modernodam.nl:${DOCKER_REGISTRY_PORT-5001}/brp/beheer-ng2
