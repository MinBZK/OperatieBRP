#!/bin/sh

HOST=fac-db09.modernodam.nl
USER=migratie
DATABASE_LIST="migr_brp migr_brp_delta migr_brp_preconditie migr_brp_regressie migr_brp_relateren migr_brp_terug migr_brp_terug_regressie migr_brp_validatie"

for DATABASE in ${DATABASE_LIST}
do
	psql -h ${HOST} -d ${DATABASE} -U ${USER} -f 1_brp-struktuur.sql && psql -h ${HOST} -d ${DATABASE} -U ${USER} -f 2_brp-vulling-statisch.sql && psql -h ${HOST} -d ${DATABASE} -U ${USER} -f 3_brp-vulling-dynamisch.sql && psql -h ${HOST} -d ${DATABASE} -U ${USER} -f 4_brp-migratie-tijdelijk.sql
done

echo "Finished updating Modernodam databases: "${DATABASE_LIST}

