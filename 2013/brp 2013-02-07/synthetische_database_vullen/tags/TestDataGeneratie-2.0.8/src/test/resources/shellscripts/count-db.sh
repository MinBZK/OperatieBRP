#!/bin/sh

### Shell script om het aantal rijen van de aanwezige tabellen in een database te tellen. ###

HOST=bdev-db02.modernodam.nl
USER=delta
DATABASE=synthetisch
SCHEMA=kern
LOGFILE=count-db.out

echo "Removing logfile..."
rm -f $LOGFILE

echo "Getting table names for $SCHEMA $DATABASE..."
COMMAND="psql -h '$HOST' -U '$USER' -d '$DATABASE' -w -At -c \"SELECT table_name FROM information_schema.tables WHERE table_catalog='$DATABASE' AND table_type='BASE TABLE' AND table_schema='$SCHEMA'\""			
TABLENAMES=$(eval "$COMMAND")

echo "Counting tables for $SCHEMA $DATABASE..."
for TABLENAME in $TABLENAMES; do
	PGCOMMAND="psql -h '$HOST' -U '$USER' -d '$DATABASE' -w -At -c \"SELECT '$TABLENAME', count(*) FROM $SCHEMA.$TABLENAME\" >> $LOGFILE"
    eval "$PGCOMMAND"
done

echo "Done! open the output with next command:"
echo " cat $LOGFILE"
