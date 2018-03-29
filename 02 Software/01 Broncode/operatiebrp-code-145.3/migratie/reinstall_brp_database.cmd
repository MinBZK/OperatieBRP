@ECHO OFF

REM Postgres password
SET PGPASSWORD=postgres

REM Drop BRP schemas
psql -U postgres -d BRP -f migr-sync-dal\src\test\resources\sql\postgres\drop-schemas.sql

REM Create BRP schemas
psql -U postgres -d BRP -f migr-sync-dal\src\test\resources\sql\postgres\brp-snapshot.sql
psql -U postgres -d BRP -f migr-sync-dal\src\test\resources\sql\postgres\blokkering-snapshot.sql
psql -U postgres -d BRP -f migr-sync-dal\src\test\resources\sql\postgres\logging-snapshot.sql

REM Insert BRP data
psql -U postgres -d BRP -f migr-sync-dal\src\test\resources\sql\data\insertNietKernStamgegevens.sql
psql -U postgres -d BRP -f migr-sync-dal\src\test\resources\sql\data\insertStatischeStamgegevens.sql
psql -U postgres -d BRP -f migr-sync-dal\src\test\resources\sql\data\insertDynamischeStamgegevens.sql
psql -U postgres -d BRP -f migr-sync-dal\src\test\resources\sql\data\insertConversieTabel.sql

