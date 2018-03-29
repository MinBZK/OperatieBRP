#!/usr/bin/env bash

psql -U $USERNAME -d $NAME -f /tmp/dbupdate/bmr/Postgres/BRP_init_database.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/bmr/Postgres/Prot/Protocol_BRP_structuur.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/bmr/Postgres/Prot/Protocol_BRP_structuur_aanvullend.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/bmr/Postgres/Prot/Protocol_BRP_statische_stamgegevens.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/bmr/Postgres/Prot/Protocol_BRP_indexen.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/bmr/Postgres/Prot/Protocol_BRP_rechten.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/bmr/Postgres/Alg/Algemeen_BRP_structuur_aanvullend.sql
psql -U $USERNAME -d $NAME -f /tmp/dbupdate/bmr/Postgres/Alg/Algemeen_BRP_custom_changes.sql
