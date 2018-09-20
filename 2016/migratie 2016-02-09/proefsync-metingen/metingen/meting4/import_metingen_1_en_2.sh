#!/bin/bash

dropdb metingen
createdb metingen
psql -d metingen -U migratie -f maak_metingen_tabellen.sql
psql -d metingen -U migratie -f import_meting1.sql
psql -d metingen -U migratie -f import_meting2.sql
