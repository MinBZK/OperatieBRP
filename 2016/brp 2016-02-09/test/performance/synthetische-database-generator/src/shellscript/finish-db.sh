#!/bin/sh

#
# Dit shell script is om de database vulling af te kunnen ronden in een linux omgeving.
# Tip voor grote databases: roep deze aan via het commando: 'nohup PAD_NAAR_SHELLSCRIPT/finish-db.sh &'
# Dan wordt het proces door de server afgerond en kun je veilig je terminal sluiten zonder dat het proces afgesloten
# wordt (handig bij grote databases).
#

HOST=hostname.modernodam.nl
DATABASE=databasename
USER=brp

#
# Let op -w vereist .pgpass bestand
# Plaats deze in homedir van user met voorbeeldinhoud: bdev-db99.modernodam.nl:5432:*:brp:brp
#

echo ---------- Start ----------

date
echo ** Toevoegen indexen, constraints en sequences updaten **
psql -h $HOST -d $DATABASE -a -U $USER -w -f ../db/kern/helper/01-constraintsAdd.sql
psql -h $HOST -d $DATABASE -a -U $USER -w -f ../db/kern/helper/01-indexesAdd.sql
psql -h $HOST -d $DATABASE -a -U $USER -w -f ../db/kern/helper/92-sequencesUpdate.sql

date
echo ** Vacuum / analyze database **
psql -h $HOST -d $DATABASE -a -U $USER -w -f ../conf/changeset/sql/helper/vacuumAnalyzeDb.sql

date
echo ** Vullen afgeleid administratief **
psql -h $HOST -d $DATABASE -a -U $USER -w -f ../conf/changeset/sql/helper/vulAfgeleidAdministratief.sql

date
echo ---------- Einde ----------
