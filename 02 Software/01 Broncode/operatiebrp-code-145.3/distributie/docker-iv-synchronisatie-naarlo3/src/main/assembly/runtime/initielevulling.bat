@echo off
cd /d %~dp0

TITLE Synchronisatie Service

set LOCALCLASSPATH=conf

for %%i in ("lib\*.jar") do call "lcp.bat" "%%i"

@echo on
java -cp %LOCALCLASSPATH% -Datomikos.unique.name=iv_1 nl.bzk.migratiebrp.synchronisatie.runtime.Main -modus initielevulling