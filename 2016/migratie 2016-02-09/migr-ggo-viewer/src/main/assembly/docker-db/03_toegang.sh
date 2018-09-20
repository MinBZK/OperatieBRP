#!/usr/bin/env bash

echo "local all $USERNAME       md5" >> $PGDATA/pg_hba.conf
echo "host  all brp 0.0.0.0/0   md5" >> $PGDATA/pg_hba.conf
