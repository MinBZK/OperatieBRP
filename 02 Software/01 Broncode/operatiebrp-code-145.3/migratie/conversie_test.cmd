@echo off

pushd .
call %~d0\paths.cmd

cd %~dp0\migratie
call mvn clean install

cd %~dp0\utils
call mvn clean install -Dtest=skip -DfailIfNoTests=false

cd %~dp0\conversie
call mvn clean install -Dtest=skip -DfailIfNoTests=false

cd %~dp0\synchronisatie
call mvn clean install -Dtest=skip -DfailIfNoTests=false

cd %~dp0\test
call mvn clean verify -P conversie

popd