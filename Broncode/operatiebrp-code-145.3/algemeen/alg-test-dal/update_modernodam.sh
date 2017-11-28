#!/bin/sh

HOST=fac-db09.modernodam.nl
USER=migratie
PASS=M1gratie
DATABASE_LIST="migr_brp migr_brp_delta migr_brp_preconditie migr_brp_regressie migr_brp_relateren migr_brp_terug migr_brp_terug_regressie migr_brp_validatie"

for DATABASE in ${DATABASE_LIST}
do
	./update_database.sh ${HOST} ${DATABASE} ${USER} ${PASS}
done

echo "Finished updating Modernodam databases: "${DATABASE_LIST}

