@echo off
cd /d %~dp0

TITLE ROUTERING

set LOCALCLASSPATH=conf
set JAVA_OPTIONS=

IF "%JMX_PORT%" == "" (SET JMX_PORT=1099)

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

rem To enable remote debugging uncomment the following option
rem set "JAVA_OPTIONS=%JAVA_OPTIONS% -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n"

java %JAVA_OPTIONS% -cp %LOCALCLASSPATH% -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=%JMX_PORT% -Dcom.sun.management.jmxremote.rmi.port=%JMX_PORT% -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false nl.bzk.migratiebrp.tools.brp.routering.Main
