FOR /f "tokens=*" %%i IN ('docker ps -q') DO docker stop %%i

REM docker container prune
REM docker volume prune
REM docker image prune
REM docker system prune -a -f

REM containers
REM FOR /f "tokens=*" %%i IN ('docker ps -a -q') DO docker rm %i
REM images
REM FOR /f "tokens=*" %%i IN ('docker images -q -f "dangling=true"') DO docker rmi %i


call mvn -o install -Pdocker -f ../../../../../../distributie/docker-basis-alpine/pom.xml

call mvn -o install -Pdocker -f ../../../../../../distributie/docker-basis-postgres/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-basis-postgresclient/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-database/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-database-ber/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-database-kern/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-database-leeg/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-database-prot/pom.xml

call mvn -o install -Pdocker -f ../../../../../../distributie/docker-basis-java/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-basis-tomcat/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-basis-tomcat-runtime/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-basis-java-runtime/pom.xml

call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-mutatielevering/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-verzending/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-message-broker/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-synchronisatie/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-afnemerindicatie/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-vrijbericht/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-bevraging/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-admhnd-publicatie/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-bijhouding/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-sleutel/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-tools-afnemervoorbeeld/pom.xml

call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-selectie/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-verwerker/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-schrijver/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-afnemerindicatie/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-message-broker/pom.xml
call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-selectie-protocollering/pom.xml

call mvn -o install -Pdocker -f ../../../../../../distributie/docker-brp-stuf/pom.xml
