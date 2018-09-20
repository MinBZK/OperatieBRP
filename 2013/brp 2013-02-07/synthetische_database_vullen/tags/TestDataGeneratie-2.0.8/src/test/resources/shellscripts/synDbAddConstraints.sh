#!/bin/sh

HOST=bdev-db02.modernodam.nl
DATABASE=synthetisch
USER=delta
SQLFILE=brpIndexAdd.sql

#
# Let op -w vereist .pgpass bestand
# Plaats deze in homedir van user met voorbeeldinhoud: bdev-db02.modernodam.nl:5432:*:delta:delta
#
# NB: Plaats de SQLFILE in dezelfde map als dit script
#

psql -h $HOST -d $DATABASE -a -U $USER -w -f $SQLFILE
