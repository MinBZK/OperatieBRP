@echo off
cd /d %~dp0

TITLE Test run

set LOCALCLASSPATH=
set JAVA_OPTIONS=

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

@echo on
java -Dtest.directory=%1 -Dtest.thema=%2 -Dtest.casus=%3 -cp %LOCALCLASSPATH% org.junit.runner.JUnitCore  nl.bzk.migratiebrp.test.isc.ParameterizedTest