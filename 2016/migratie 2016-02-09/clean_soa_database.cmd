@ECHO OFF

REM Postgres password
SET PGPASSWORD=postgres

REM Clean SOA tables
psql -U postgres -d soa -f migr-perf-isc\src\test\resources\sql\truncate_isc.sql
