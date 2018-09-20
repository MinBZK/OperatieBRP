#!/usr/bin/env bash

psql -U $USERNAME -d $NAME -f /tmp/dbupdate/init-database.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/kern/Kern_BRP_structuur.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/kern/Kern_BRP_statische_stamgegevens.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/kern/Kern_BRP_dynamische_stamgegevens.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/kern/Kern_BRP_structuur_aanvullend.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/kern/Kern_BRP_custom_changes.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/kern/Kern_BRP_indexen.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/kern/Kern_BRP_rechten.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/lev/Protocol_BRP_structuur.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/lev/Protocol_BRP_structuur_aanvullend.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/lev/Protocol_BRP_statische_stamgegevens.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/lev/Protocol_BRP_indexen.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/lev/Protocol_BRP_rechten.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/ber/Bericht_BRP_structuur.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/ber/Bericht_BRP_structuur_aanvullend.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/ber/Bericht_BRP_statische_stamgegevens.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/ber/Bericht_custom-changes.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/ber/Bericht_BRP_indexen.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/ber/Bericht_BRP_rechten.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/kern/99-brp-version.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/kern/93-brp-analyze.sql
