@echo off

:stop-jboss
call P:\jboss-5.1.0-as\jboss_stop.cmd

if "%1" == "console" (
	ECHO Only building console
	goto migr-isc-console
)

if "%1" == "deploy" (
	ECHO Only deploying
	goto deploy-console
)

:migr-isc-jbpm4jsf
cd /d %~dp0\migr-isc-jbpm4jsf
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:migr-isc-mig4jsf
cd /d %~dp0\migr-isc-mig4jsf
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:migr-isc-console
cd /d %~dp0\migr-isc-console
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:deploy-console
cd /d %~dp0\migr-isc-console
copy target\jsf-console.war P:\jboss-5.1.0-as\server\default\deploy\jbpm.esb
if errorlevel 1 goto error

:start-jboss
start P:\jboss-5.1.0-as\jboss_start.cmd

goto end

:error
ECHO ERROR OCCURED!

:end
cd /d %~dp0
