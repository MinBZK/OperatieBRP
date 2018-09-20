#!/bin/sh

HOST=bdev-db02.modernodam.nl
DATABASE=synthetisch
USER=delta

#
# Let op -w vereist .pgpass bestand
# Plaats deze in homedir van user met voorbeeldinhoud: bdev-db02.modernodam.nl:5432:*:delta:delta
#

date
echo ++++++++++++++++++++++
psql -h $HOST -d $DATABASE -a -U $USER -w -f brpConstraintAdd.sql
date
echo xxxxxxxxxxxxxxxxxxxxxx
psql -h $HOST -d $DATABASE -a -U $USER -w -f brpIndices.sql
date
echo xxxxxxxxxxxxxxxxxxxxxx
psql -h $HOST -d $DATABASE -a -U $USER -w -f brp-indexen.sql
date
echo ----------------------
