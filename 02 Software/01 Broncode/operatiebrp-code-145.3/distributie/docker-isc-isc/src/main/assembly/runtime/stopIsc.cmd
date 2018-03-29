@echo off
cd /d %~dp0

TITLE ISC

set LOCALCLASSPATH=conf
set JAVA_OPTIONS=

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

rem To enable remote debugging uncomment the following option
rem set "JAVA_OPTIONS=%JAVA_OPTIONS% -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n"

@echo on
java %JAVA_OPTIONS% -cp %LOCALCLASSPATH% nl.bzk.migratiebrp.isc.runtime.Exit