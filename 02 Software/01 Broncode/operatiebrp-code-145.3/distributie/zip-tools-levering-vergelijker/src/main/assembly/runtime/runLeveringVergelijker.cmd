@echo off
cd /d %~dp0

TITLE LEVERING VERGELIJKER

set LOCALCLASSPATH=
set JAVA_OPTIONS=

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

@echo on
java  -cp %LOCALCLASSPATH% nl.bzk.migratiebrp.tools.levering.vergelijker.LeveringVergelijkerRuntimeMain %*