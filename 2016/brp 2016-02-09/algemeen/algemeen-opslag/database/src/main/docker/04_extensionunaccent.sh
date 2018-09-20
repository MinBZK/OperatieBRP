#!/usr/bin/env bash

psql -U $POSTGRES_USER -d $NAME -c "CREATE EXTENSION unaccent;"
