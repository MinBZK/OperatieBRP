@echo off

:stop-jboss
call d:\mGBA\jboss-soa-p-5\jboss_stop.cmd

if "%1" == "console" (
	ECHO Only building console
	goto migr-isc-console
)

if "%1" == "deploy" (
	ECHO Only deploying
	goto deploy-console
)

:migr-isc-mig4jsf
cd /d %~dp0\migr-isc-console-mig4jsf
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:migr-isc-console
cd /d %~dp0\migr-isc-console-webapp
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:deploy-console
cd /d %~dp0\migr-isc-console-webapp
copy target\jbpm-console.war d:\mGBA\jboss-soa-p-5\jboss-as\server\isc\deploy\jbpm.esb
if errorlevel 1 goto error

:start-jboss
start d:\mGBA\jboss-soa-p-5\jboss_start.cmd

goto end

:error
ECHO ERROR OCCURED!

:end
cd /d %~dp0
