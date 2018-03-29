@echo off
pushd %~dp0..\deployer
IF /i "%~2" == "" (
	echo. 
	echo [[93mWARNING[0m] Geen Docker versie meegegeven, lokale build wordt gebruikt.
	echo. 
	call mvn -f docker-start.pom.xml -Pdocker-start -Dprofiel=%1
) ELSE (
    echo. 
	echo [[94mINFO[0m] Docker versie [92m%~2[0m meegegeven, remote versie [92m%~2[0m wordt gebruikt.
	echo.
	call mvn -f docker-start.pom.xml -Pdocker-start -Dprofiel=%1 -Dimage.registry=fac-nexus3.modernodam.nl:5000/ -Dimage.version=%2
)
popd