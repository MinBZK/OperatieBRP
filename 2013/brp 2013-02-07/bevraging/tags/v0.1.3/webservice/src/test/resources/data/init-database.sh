#!/bin/bash
#
# File: init-database.sh
# Doel: Het initialiseren van een database met een schema en stamgegevens met behulp
#       van een aantal scripts die in dezelfde directory als dit script staan.
#       Alle in de database aanwezige gegevens zullen worden gewist.
# Parameter: een database. Ofwel de naam van een lokale database, of een
#            PostgreSQL connect string
#            (zie PostgreSQL documentatie paragraaf 31.1. "Database Connection Control Functions").
#
if [ $# -lt 1 ]
then
    echo "Database connect string verplicht"
    exit 1
fi
_CURRENT_DIRECTORY=$(dirname $0)

psql "$@" -f - <<delim
\i ${_CURRENT_DIRECTORY}/testdata.sql
delim
