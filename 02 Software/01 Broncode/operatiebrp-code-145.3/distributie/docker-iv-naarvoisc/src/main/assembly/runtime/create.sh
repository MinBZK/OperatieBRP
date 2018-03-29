#!/bin/sh
export PGPASSWORD=$GBAV_DB_ENV_PASSWORD
psql -q -h $GBAV_DB_ENV_HOSTNAME -p $GBAV_DB_ENV_PORT -d $GBAV_DB_ENV_NAME -U $GBAV_DB_ENV_USERNAME -w -f /app/sql/extract-voisc-mailbox.sql > /app/data/voisc-mailbox.sql
