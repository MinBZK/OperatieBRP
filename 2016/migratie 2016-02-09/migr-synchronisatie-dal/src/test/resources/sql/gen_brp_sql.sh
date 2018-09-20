#!/bin/sh
# Dit script voert alle sql-scripts uit voor het aanmaken van de BRP database en de vulling van de stamgegevens.
#
# Het sed commando 's/^\xEF\xBB\xBF\|\r//g' verwijdert Byte Order Marks en \r

OUTPUT_FILE_CREATE="1_brp-struktuur.sql"
OUTPUT_FILE_FILL_STAT="2_brp-vulling-statisch.sql"
OUTPUT_FILE_FILL_DYN="3_brp-vulling-dynamisch.sql"
NOW=$(date)
ROOT_DIR="./"

INPUT_FILES_CREATE="bmr/Kern/Kern_BRP_structuur.sql bmr/Kern/Kern_BRP_structuur_aanvullend.sql bmr/Kern/Kern_BRP_custom_changes.sql bmr/Ber/Bericht_BRP_structuur.sql bmr/Ber/Bericht_BRP_structuur_aanvullend.sql bmr/Ber/Bericht_BRP_custom_changes.sql bmr/Prot/Protocol_BRP_structuur.sql bmr/Prot/Protocol_BRP_structuur_aanvullend.sql bmr/Prot/Protocol_BRP_custom_changes.sql bmr/Alg/Algemeen_BRP_structuur_aanvullend.sql bmr/Alg/Algemeen_BRP_custom_changes.sql bmr/Kern/Kern_BRP_indexen.sql bmr/Ber/Bericht_BRP_indexen.sql bmr/Prot/Protocol_BRP_indexen.sql"
INPUT_FILES_STAT="bmr/Kern/Kern_BRP_statische_stamgegevens.sql bmr/Ber/Bericht_BRP_statische_stamgegevens.sql bmr/Prot/Protocol_BRP_statische_stamgegevens.sql"
INPUT_FILES_DYN="bmr/Kern/Kern_BRP_dynamische_stamgegevens.sql"

# GENERATE CREATE DB SQL

echo "\n-- SQL script voor het aanmaken van de BRP database." > ${OUTPUT_FILE_CREATE}
echo "-- Dit script is gegenereerd op ${NOW}\n--\n" >> ${OUTPUT_FILE_CREATE}

for SQL_FILE in $INPUT_FILES_CREATE
do
    echo "\n-- SQL inhoud van bestand: ${SQL_FILE}" >> ${OUTPUT_FILE_CREATE}
    cat "${SQL_FILE}" | sed 's/^\xEF\xBB\xBF\|\r//g' >> ${OUTPUT_FILE_CREATE}
done

echo "\n-- Einde" >> ${OUTPUT_FILE_CREATE}


# GENERATE FILL BMR STAM SQL

echo "-- Dit script is gegenereerd op ${NOW}\n--\n" >> ${OUTPUT_FILE_FILL_STAT}
echo "\n-- SQL script voor het vullen van de BRP database met statische stamgegevens" > ${OUTPUT_FILE_FILL_STAT}

for SQL_FILE in $INPUT_FILES_STAT
do
    echo "\n-- SQL inhoud van bestand: ${SQL_FILE}" >> ${OUTPUT_FILE_FILL_STAT}
    cat "${SQL_FILE}" | sed 's/^\xEF\xBB\xBF\|\r//g' >> ${OUTPUT_FILE_FILL_STAT}
done

echo "\n-- Einde" >> ${OUTPUT_FILE_FILL_STAT}


# GENERATE FILL NIET_BMR STAM SQL

echo "\n-- SQL script voor het vullen van de BRP database met dynamische stamgegevens" > ${OUTPUT_FILE_FILL_DYN}
echo "-- Dit script is gegenereerd op ${NOW}\n--\n" >> ${OUTPUT_FILE_FILL_DYN}

for SQL_FILE in $INPUT_FILES_DYN
do
    echo "\n-- SQL inhoud van bestand: ${SQL_FILE}" >> ${OUTPUT_FILE_FILL_DYN}
    cat "${SQL_FILE}" | sed 's/^\xEF\xBB\xBF\|\r//g' >> ${OUTPUT_FILE_FILL_DYN}
done

echo "\n-- Einde" >> ${OUTPUT_FILE_FILL_DYN}
