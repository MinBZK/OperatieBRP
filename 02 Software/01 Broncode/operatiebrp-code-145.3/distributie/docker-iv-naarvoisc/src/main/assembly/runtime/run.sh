#!/bin/sh
export PGPASSWORD=$VOISC_DB_ENV_PASSWORD
psql -h $VOISC_DB_ENV_HOSTNAME -p $VOISC_DB_ENV_PORT -d $VOISC_DB_ENV_NAME -U $VOISC_DB_ENV_USERNAME -w -f /app/data/voisc-mailbox.sql
