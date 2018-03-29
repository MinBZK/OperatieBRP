@echo off
cd /d %~dp0 

TITLE TELLINGEN OPSCHONEN

set LOCALCLASSPATH=
set JAVA_OPTIONS=

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

@echo on
java -Xms512m -Xmx1024m -XX:MaxPermSize=512m -cp %LOCALCLASSPATH% nl.bzk.migratiebrp.tools.proefsynchronisatie.Main -config proef-sync.properties  -batchSize 10000 -timeout 1200000