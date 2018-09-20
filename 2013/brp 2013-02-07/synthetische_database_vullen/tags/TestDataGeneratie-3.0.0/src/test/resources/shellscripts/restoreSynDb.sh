#!/bin/sh

### Shell script om een dump te restoren op database server. ###
### Aangezien dit lang kan duren, is het aan te raden dit via "nohup ./restoreSynDb.sh &" uit te voeren, ###
### zodat het op achtergrond gebeurt en output in nohup.out komt ###

DBNAME=synthetisch
BACKUPFILE=/backup/synthetisch.tar
OWNER=delta

echo "Start!: $(date)"

createdb -T 'template0' -e -E 'UTF8' -O $OWNER $DBNAME
pg_restore --dbname $DBNAME --no-password $BACKUPFILE

echo "Einde!: $(date)"
