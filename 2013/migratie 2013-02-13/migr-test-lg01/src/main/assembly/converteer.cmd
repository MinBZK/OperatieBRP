@echo off
cd /d %~dp0

TITLE Test run

set LOCALCLASSPATH=
set JAVA_OPTIONS=

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

@echo on
java -Dtest.directory=%1 -cp %LOCALCLASSPATH% org.junit.runner.JUnitCore nl.moderniseringgba.migratie.test.lg01.ParameterizedTest
