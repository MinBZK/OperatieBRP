for /f %%i in ('docker images "--filter=dangling=true" -q') do docker rmi -f %%i
