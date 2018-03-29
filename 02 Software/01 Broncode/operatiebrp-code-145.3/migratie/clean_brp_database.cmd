@ECHO OFF

REM Postgres password
SET PGPASSWORD=postgres

REM Clean BRP tables
psql -U postgres -d BRP -f migr-perf-isc\src\test\resources\sql\truncate_brp.sql
