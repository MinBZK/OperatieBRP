docker-machine start migratie

@ECHO OFF
FOR /f "tokens=*" %%i IN ('docker-machine env migratie --shell cmd') DO %%i
SET DOCKER_HOST

FOR /f "tokens=*" %%i IN ('docker-machine ip migratie') DO SET DOCKER_IP=%%i
SET DOCKER_IP

