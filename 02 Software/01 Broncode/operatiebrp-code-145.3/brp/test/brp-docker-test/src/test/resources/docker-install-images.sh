#!/bin/bash

#mvn -f deployment/deployer-docker/src/main/deployer/docker-remove.pom.xml -Dtest.type=brp-alles -U help:active-profiles install -Pdocker-remove
#mvn -f deployment/deployer-docker/src/main/deployer/docker-deinstall.pom.xml -Dtest.type=brp-alles help:active-profiles install -Pdocker-deinstall
#mvn -f deployment/deployer-docker/src/main/deployer/docker-install.pom.xml -Dtest.type=brp-alles help:active-profiles clean install -Pdocker-install

# Delete all containers
docker rm -f $(docker ps -qf "status=exited")
# Delete all images
docker rmi $(docker images -q)

#delete all volumes
docker volume rm $(docker volume ls -qf dangling=true)



mvn install -Pdocker -f ../../../../../../distributie/docker-basis-alpine/pom.xml

mvn install -Pdocker -f ../../../../../../distributie/docker-basis-postgres/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-basis-postgresclient/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-database/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-database-ber/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-database-kern/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-database-leeg/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-database-prot/pom.xml

mvn install -Pdocker -f ../../../../../../distributie/docker-basis-java/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-basis-tomcat/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-basis-tomcat-runtime/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-basis-java-runtime/pom.xml

mvn install -Pdocker -f ../../../../../../distributie/docker-brp-mutatielevering/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-verzending/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-message-broker/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-synchronisatie/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-afnemerindicatie/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-vrijbericht/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-bevraging/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-admhnd-publicatie/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-bijhouding/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-sleutel/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-selectie/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-verwerker/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-schrijver/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-afnemerindicatie/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-message-broker/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-protocollering/pom.xml

mvn install -Pdocker -f ../../../../../../distributie/docker-tools-afnemervoorbeeld/pom.xml

mvn install -Pdocker -f ../../../../../../distributie/docker-brp-stuf/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-stuf/pom.xml
mvn install -Pdocker -f ../../../../../../distributie/docker-brp-beheer-selectie/pom.xml
