@echo off
setlocal

set HOST=fac-db09.modernodam.nl
set USER=migratie
set PASS=M1gratie
set DATABASE_LIST=migr_brp migr_brp_delta migr_brp_preconditie migr_brp_regressie migr_brp_relateren migr_brp_terug migr_brp_terug_regressie migr_brp_validatie
set REMAINING_LIST=%DATABASE_LIST%

:loop
for /f "tokens=1,*" %%A in ("%REMAINING_LIST%") do (
   call update_database.cmd %HOST% %%A %USER% %PASS%
   set REMAINING_LIST=%%B
)
if defined REMAINING_LIST goto :loop

echo Finished updating Modernodam databases: %DATABASE_LIST%

ENDLOCAL