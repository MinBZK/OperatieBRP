@echo off
for /f "tokens=*" %%a in ("%*") do (set config=%%a)

echo Configuratie: %config%

:stop
call mvn -f cit-stop.pom.xml install %config%
if errorlevel 1 goto exit

:deinstall
call mvn -f cit-deinstall.pom.xml install %config%
if errorlevel 1 goto exit

:install
call mvn -f cit-install.pom.xml install %config%
if errorlevel 1 goto exit

:start
call mvn -f cit-start.pom.xml install %config%
if errorlevel 1 goto exit

:exit
