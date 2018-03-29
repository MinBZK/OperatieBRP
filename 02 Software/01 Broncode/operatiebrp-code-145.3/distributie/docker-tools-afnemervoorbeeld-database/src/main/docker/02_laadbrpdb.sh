#!/usr/bin/env bash

psql -U $USERNAME -d $NAME -f /tmp/dbupdate/init_database.sql