@ECHO OFF

ECHO Creating swarm on CIT-DOK-01 ...
FOR /f "tokens=*" %%i IN ('docker-machine env cit-dok-01') DO %%i
FOR /f "tokens=*" %%i IN ('docker-machine ip cit-dok-01') DO set DOCKER_SWARM_MANAGER=%%i
docker swarm init

FOR /f "tokens=*" %%i IN ('docker swarm join-token --quiet worker') DO set DOCKER_SWARM_TOKEN=%%i

ECHO Joining swarm from CIT-DOK-02 ...
FOR /f "tokens=*" %%i IN ('docker-machine env cit-dok-02') DO %%i
docker swarm join %DOCKER_SWARM_MANAGER% --token %DOCKER_SWARM_TOKEN%

ECHO Joining swarm from CIT-DOK-03 ...
FOR /f "tokens=*" %%i IN ('docker-machine env cit-dok-03') DO %%i
docker swarm join %DOCKER_SWARM_MANAGER% --token %DOCKER_SWARM_TOKEN%

set DOCKER_SWARM_MANAGER=
set DOCKER_SWARM_TOKEN=

ECHO Swarm node list
FOR /f "tokens=*" %%i IN ('docker-machine env cit-dok-01') DO %%i
docker node ls

FOR /f "tokens=*" %%i IN ('docker-machine env -u') DO %%i
