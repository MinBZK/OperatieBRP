@echo off
cd /d %~dp0

TITLE SYNCHRONISATIE

set LOCALCLASSPATH=conf

IF "%JMX_PORT%" == "" (SET JMX_PORT=1099)

for %%i in ("lib\*.jar") do call "lcp.bat" "%%i"

@echo on
java -cp %LOCALCLASSPATH% -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false -Datomikos.unique.name=sync_1 nl.bzk.migratiebrp.synchronisatie.runtime.Main -modus synchronisatie