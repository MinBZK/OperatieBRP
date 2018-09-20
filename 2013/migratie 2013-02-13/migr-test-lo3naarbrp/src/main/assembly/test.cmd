@echo off
cd /d %~dp0

TITLE Test run

set LOCALCLASSPATH=
set JAVA_OPTIONS=

if "%1" == "memory" (
	ECHO Using in-memory database
	SET JAVA_OPTIONS=%JAVA_OPTIONS% -Dspring.profiles.active=memoryDS
	SHIFT
)

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

@echo on
java -Dtest.directory=%1 -Dtest.thema=%2 -Dtest.casus=%3 %JAVA_OPTIONS% -cp %LOCALCLASSPATH% org.junit.runner.JUnitCore  nl.moderniseringgba.migratie.test.lo3naarbrp.ParameterizedTest