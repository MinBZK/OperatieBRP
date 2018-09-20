@echo off

:stop-jboss
call d:\mGBA\jboss-soa-p-5\jboss_stop.cmd

SET BUILD_OPTS=

if "%1" == "esb" (
	ECHO Only building ESB
	SET BUILD_OPTS=-pl migr-isc-esb
	goto setnotest
)

if "%1" == "deploy" (
	ECHO Only deploying
	goto deploy
)

SET BUILD_OPTS=-pl migr-isc-esb,migr-isc-queue,migr-isc-jbpm-logging -am

:setnotest
SET BUILD_OPTS=%BUILD_OPTS% -Dtest=no -DfailIfNoTests=false

:build
cd /d %~dp0
call mvn clean install %BUILD_OPTS%
if errorlevel 1 goto error

:deploy
cd /d %~dp0
del /q d:\mGBA\jboss-soa-p-5\jboss-as\server\isc\deploy\migr-isc-esb-*.esb
del /q d:\mGBA\jboss-soa-p-5\jboss-as\server\isc\deploy\migr-isc-queue-*.sar
del /q d:\mGBA\jboss-soa-p-5\jboss-as\server\isc\deploy\jbpm.esb\migr-isc-jbpm-logging-*.jar

copy migr-isc-esb\target\migr-isc-esb-*.esb d:\mGBA\jboss-soa-p-5\jboss-as\server\isc\deploy
if errorlevel 1 goto error
copy migr-isc-queue\target\migr-isc-queue-*.sar d:\mGBA\jboss-soa-p-5\jboss-as\server\isc\deploy
if errorlevel 1 goto error
copy migr-isc-jbpm-logging\target\migr-isc-jbpm-logging-*.jar d:\mGBA\jboss-soa-p-5\jboss-as\server\isc\deploy\jbpm.esb\
if errorlevel 1 goto error
del /q d:\mGBA\jboss-soa-p-5\jboss-as\server\isc\deploy\jbpm.esb\migr-isc-jbpm-logging-*-sources.jar
del /q d:\mGBA\jboss-soa-p-5\jboss-as\server\isc\deploy\jbpm.esb\migr-isc-jbpm-logging-*-tests.jar


:start-jboss
start d:\mGBA\jboss-soa-p-5\jboss_start.cmd

goto end



:error
ECHO ERROR OCCURED!



:end
cd /d %~dp0
