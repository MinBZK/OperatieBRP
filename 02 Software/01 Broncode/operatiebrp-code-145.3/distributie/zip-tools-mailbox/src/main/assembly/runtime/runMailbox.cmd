@echo off
cd /d %~dp0 

TITLE MAILBOX

set LOCALCLASSPATH=conf
set JAVA_OPTIONS=

IF "%JMX_PORT%" == "" (SET JMX_PORT=1099)

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

@echo on
java -cp %LOCALCLASSPATH% -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=%JMX_PORT% -Dcom.sun.management.jmxremote.rmi.port=%JMX_PORT% -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false nl.bzk.migratiebrp.tools.mailbox.MailboxMain
