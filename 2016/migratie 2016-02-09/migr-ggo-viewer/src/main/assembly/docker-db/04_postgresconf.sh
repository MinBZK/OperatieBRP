#!/usr/bin/env bash

cp /docker-entrypoint-initdb.d/postgresql.conf $PGDATA/postgres.conf
chown postgres:postgres $PGDATA/postgres.conf

