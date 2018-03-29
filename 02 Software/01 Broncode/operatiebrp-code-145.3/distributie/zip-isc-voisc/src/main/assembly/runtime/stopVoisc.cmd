@echo off
cd /d %~dp0 

set LOCALCLASSPATH=
for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

@echo on
java -cp %LOCALCLASSPATH% nl.bzk.migratiebrp.voisc.runtime.VoiscExit