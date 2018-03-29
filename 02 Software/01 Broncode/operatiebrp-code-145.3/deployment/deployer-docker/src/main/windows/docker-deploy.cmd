call %~dp0remove-containers
call %~dp0docker-build
call %~dp0docker-start %1
