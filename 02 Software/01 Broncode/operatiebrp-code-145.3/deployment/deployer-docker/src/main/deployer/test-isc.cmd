call mvn -f docker-start.pom.xml -Dprofiel=isc -Pdocker-start
pushd ..\..\..\..\..\migratie\migr-test-isc
call mvn -f regressie-isc.pom.xml -Dregressietest.properties=docker.properties -DDOCKER_SWARM_SERVER=localhost process-test-classes
popd
