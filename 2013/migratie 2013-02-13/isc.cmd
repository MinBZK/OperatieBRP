@echo off

:stop-jboss
call P:\jboss-5.1.0-as\jboss_stop.cmd


if "%1" == "esb" (
	ECHO Only building ESB
	goto isc-esb
)

if "%1" == "jbpm" (
	ECHO Only building JBPM Processes and ESB
	goto isc-jbpm
)

if "%1" == "uc201" (
	ECHO Only building uc201 and ESB
	goto isc-uc201
)

if "%1" == "uc202" (
	ECHO Only building uc202 and ESB
	goto isc-uc202
)

if "%1" == "uc301" (
	ECHO Only building uc301 and ESB
	goto isc-uc301
)

if "%1" == "uc302" (
	ECHO Only building uc302 and ESB
	goto isc-uc302
)

if "%1" == "uc306" (
	ECHO Only building uc306 and ESB
	goto isc-uc306
)

if "%1" == "uc307" (
	ECHO Only building uc307 and ESB
	goto isc-uc307
)

if "%1" == "uc308" (
	ECHO Only building uc308 and ESB
	goto isc-uc308
)

if "%1" == "uc311" (
	ECHO Only building uc311 and ESB
	goto isc-uc311
)

if "%1" == "deploy" (
	ECHO Only deploying
	goto deploy-esb
)

:isc-root
cd /d %~dp0\migr-isc
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:isc-db
cd /d %~dp0\migr-isc-db
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:isc-bericht
cd /d %~dp0\migr-isc-bericht
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:isc-jbpm
cd /d %~dp0\migr-isc-jbpm
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto erro

:isc-uc201
cd /d %~dp0\migr-isc-uc201
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error
if "%1" == "uc201" (
	goto isc-esb
)

:isc-uc202
cd /d %~dp0\migr-isc-uc202
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error
if "%1" == "uc202" (
	goto isc-esb
)

:isc-uc301
cd /d %~dp0\migr-isc-uc301
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error
if "%1" == "uc301" (
	goto isc-esb
)

:isc-uc302
cd /d %~dp0\migr-isc-uc302
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error
if "%1" == "uc302" (
	goto isc-esb
)

:isc-uc306
cd /d %~dp0\migr-isc-uc306
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error
if "%1" == "uc306" (
	goto isc-esb
)

:isc-uc307
cd /d %~dp0\migr-isc-uc307
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error
if "%1" == "uc307" (
	goto isc-esb
)

:isc-uc308
cd /d %~dp0\migr-isc-uc308
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error
if "%1" == "uc308" (
	goto isc-esb
)

:isc-uc311
cd /d %~dp0\migr-isc-uc311
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error
if "%1" == "uc311" (
	goto isc-esb
)

:isc-esb
cd /d %~dp0\migr-isc-esb
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

:deploy-esb
cd /d %~dp0\migr-isc-esb
del /q P:\jboss-5.1.0-as\server\default\deploy\migr-isc-*.esb
copy target\migr-isc-*.esb P:\jboss-5.1.0-as\server\default\deploy
if errorlevel 1 goto error



:start-jboss
start P:\jboss-5.1.0-as\jboss_start.cmd

goto end



:error
ECHO ERROR OCCURED!



:end
cd /d %~dp0
