#!/bin/bash

echo -n "svn user : "
read -e SVN_UN

echo -n "svn password : "
read -e SVN_PW

wget --user=$SVN_UN --password=$SVN_PW --no-check-certificate -r --no-parent --no-directories https://192.168.207.40/svn/brp-code/BRP/trunk/dataaccess/src/main/resources/db/

echo -n "database : "
read -e DB

psql -h bdev-db02.modernodam.nl -U delta -f brp.sql $DB
psql -h bdev-db02.modernodam.nl -U delta -f brp-0.0.3.sql $DB
psql -h bdev-db02.modernodam.nl -U delta -f stamgegevensStatisch.sql $DB
psql -h bdev-db02.modernodam.nl -U delta -f stamgegevensLand.sql $DB
psql -h bdev-db02.modernodam.nl -U delta -f stamgegevensNationaliteit.sql $DB
psql -h bdev-db02.modernodam.nl -U delta -f stamgegevensPartijGemeente.sql $DB
psql -h bdev-db02.modernodam.nl -U delta -f stamgegevensPlaats.sql $DB
psql -h bdev-db02.modernodam.nl -U delta -f autaut.sql $DB
psql -h bdev-db02.modernodam.nl -U delta -f whiteboxfiller.sql $DB
