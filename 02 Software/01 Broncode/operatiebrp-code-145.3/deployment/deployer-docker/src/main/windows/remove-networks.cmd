 for /f %%i in ('docker network ls -q') do docker network rm %%i