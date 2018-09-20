#!/usr/bin/env bash

psql -U $USERNAME -d $NAME -f sql/createProtocollering.sql
