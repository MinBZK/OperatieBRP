@echo off
cd /d %~dp0

TITLE EXCELVULLING

set LOCALCLASSPATH=conf
set JAVA_OPTIONS=

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

rem To enable remote debugging uncomment the following option
rem set "JAVA_OPTIONS=%JAVA_OPTIONS% -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n"

java %JAVA_OPTIONS% -cp %LOCALCLASSPATH% nl.bzk.migratiebrp.init.naarbrp.Main -excelvulling ./excel-regressie
