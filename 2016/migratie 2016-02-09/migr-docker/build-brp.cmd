pushd %~dp0\..\..\brp-code

call mvn clean install -Dmaven.test.skip=true -Pdocker,inmemory -pl :brp-algemeen-opslag-database,:brp-message-broker,:brp-gba-centrale,:brp-beheer-api,:brp-levering-services-synchronisatie,:brp-levering-services-mutatielevering,:brp-levering-protocollering,:brp-levering-services-onderhoud-afnemerindicaties,:brp-levering-services-verzending %* 

popd