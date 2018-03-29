@echo off
cd /d %~dp0 

TITLE TELLINGEN OPSCHONEN

set LOCALCLASSPATH=
set JAVA_OPTIONS=

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

@echo on
java  -cp %LOCALCLASSPATH% nl.bzk.migratiebrp.tools.proefsynchronisatie.Main -config proef-sync.properties -create -datumVanaf %1 -datumTot %2

