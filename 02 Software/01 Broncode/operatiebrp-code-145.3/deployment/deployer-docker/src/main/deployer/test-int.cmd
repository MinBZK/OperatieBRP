call mvn -f docker-start.pom.xml -Dprofiel=integratie -Pdocker-start
pushd ..\..\..\..\..\migratie\migr-test-isc
call mvn -f regressie-int.pom.xml -Dregressietest.properties=docker.properties -DDOCKER_SWARM_SERVER=localhost process-test-classes
popd
