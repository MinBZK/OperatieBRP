@echo off
set server=%1
set profile=%2
for /f "tokens=2,*" %%a in ("%*") do (set config=%%b)

echo Server: %server%
echo Profile: %profile%
echo Configuratie: %config%
if "%server%"=="" goto exit
if "%profile%"=="" goto exit

:stop
call mvn -f installer-stop.pom.xml clean install -Dtest.server=%server% %config%
if errorlevel 1 goto exit

:deinstall
call mvn -f installer-deinstall.pom.xml clean install -Dtest.server=%server% %config%
if errorlevel 1 goto exit

:install
call mvn -f installer-install.pom.xml clean install -Dtest.server=%server% -Dtest.type=%profile% %config%
if errorlevel 1 goto exit

:start
call mvn -f installer-start.pom.xml clean install -Dtest.server=%server% -Dtest.type=%profile% %config%
if errorlevel 1 goto exit

:exit
