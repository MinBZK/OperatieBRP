@echo off
pushd %~dp0..\deployer
call mvn -f docker-install.pom.xml -Pdocker-install
popd