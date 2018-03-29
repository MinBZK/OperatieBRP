@echo off
cd /d %~dp0

TITLE Execute SQL

set LOCALCLASSPATH=

for %%i in ("lib\*.jar") do call "lcp.cmd" "%%i"

java -cp %LOCALCLASSPATH% nl.bzk.migratiebrp.test.common.sql.ExecuteSql %*
