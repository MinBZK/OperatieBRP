for /f %%i in ('docker images -q %*') do docker rmi -f %%i
