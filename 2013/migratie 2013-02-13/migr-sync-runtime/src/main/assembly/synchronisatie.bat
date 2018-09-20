@echo off
cd /d %~dp0

TITLE Synchronisatie Service

set LOCALCLASSPATH="migr-sync-runtime-1.0-SNAPSHOT.jar"
for %%i in ("lib\*.jar") do call "lcp.bat" "%%i"

@echo on
java -cp %LOCALCLASSPATH% nl.moderniseringgba.migratie.runtime.Main %1 %2 %3 %4 %5 %6 %7 %8 %9