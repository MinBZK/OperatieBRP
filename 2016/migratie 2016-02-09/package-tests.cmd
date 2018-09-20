@echo off

cd /d %~dp0\migr
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-utils
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-conversie
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-conversie-model
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-conversie-regels
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-isc
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-isc-bericht
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-isc-excel
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-sync
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-sync-dal
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error


cd /d %~dp0\migr-test-brpnaarlo3
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-test-isc
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-test-lg01
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-test-lo3naarbrp
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error

cd /d %~dp0\migr-test-preconditie
call mvn clean install -Dtest=no -DfailIfNoTests=false
if errorlevel 1 goto error


goto end

:error
ECHO ERROR OCCURED!

:end
cd /d %~dp0
