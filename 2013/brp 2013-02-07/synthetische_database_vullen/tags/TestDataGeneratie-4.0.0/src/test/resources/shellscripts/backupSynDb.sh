#!/bin/sh

### Shell script om de synthetische database te backuppen, met de standaardcompressie methode van de postgres dump. ###
### Bij opstarten wordt om wachtwoord gevraagd voor user delta, dit is "nuevennniet". ###
### Dit script dient geplaatst te worden op de db-server waar de backup dient plaats te vinden. ### 

HOST=bdev-db02.modernodam.nl
PORT=5432
USER=delta
DATABASE=synthetisch
FILE=/backup/synthetisch.tar

pg_dump --host $HOST --port $PORT --username "$USER" -W --format custom --blobs --verbose --file "$FILE" "$DATABASE"
