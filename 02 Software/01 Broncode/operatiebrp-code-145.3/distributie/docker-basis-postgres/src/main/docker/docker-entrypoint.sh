#!/bin/sh
# User postgres and permission 700 required for PostgreSQL
chown -R postgres "$POSTGRES_DATA"
chmod 700 "$POSTGRES_DATA"

export PGDATA=$POSTGRES_DATA

if [ -z "$(ls -A "$POSTGRES_DATA")" ]; then
    gosu postgres initdb

    : ${POSTGRES_USER:="postgres"}
    : ${POSTGRES_DATABASE:=$POSTGRES_USER}

    if [ "$POSTGRES_PASSWORD" ]; then
      pass="PASSWORD '$POSTGRES_PASSWORD'"
      authMethod=md5
    else
      echo "==============================="
      echo "!!! Use \$POSTGRES_PASSWORD env var to secure your database !!!"
      echo "==============================="
      pass=
      authMethod=trust
    fi
    echo

    if [ "$POSTGRES_USER" != 'postgres' ]; then
      op=CREATE
    else
      op=ALTER
    fi

    userSql="$op USER $POSTGRES_USER WITH SUPERUSER $pass;"
    echo $userSql | gosu postgres postgres --single -jE
    echo

    if [ "$POSTGRES_DATABASE" != 'postgres' ]; then
      createSql="CREATE DATABASE \"$POSTGRES_DATABASE\" OWNER $POSTGRES_USER $POSTGRES_DATABASE_OPTIONS;"
      echo $createSql | gosu postgres postgres --single -jE
      echo
    fi

    # internal start of server in order to allow set-up using psql-client
    # does not listen on TCP/IP and waits until start finishes
    gosu postgres pg_ctl -D "$POSTGRES_DATA" \
        -o "-c listen_addresses=''" \
        -w start

    echo
    for f in /docker-entrypoint-initdb.d/*; do
        case "$f" in
            *.sh)  echo "$0: running $f"; . "$f" ;;
            *.sql) echo "$0: running $f"; psql --username "$POSTGRES_USER" --dbname "$POSTGRES_DATABASE" < "$f" && echo ;;
            *)     echo "$0: ignoring $f" ;;
        esac
        echo
    done

    gosu postgres pg_ctl -m fast -w stop

    { echo; echo "host all all 0.0.0.0/0 $authMethod"; } >> "$POSTGRES_DATA"/pg_hba.conf

fi

# Setup runtime configured variabeles
sed -ri "s/^#?(listen_addresses\s*=\s*)\S+/\1'*'/" "$POSTGRES_DATA"/postgresql.conf
sed -ri "s/^#?(max_connections\s*=\s*)\S+/\1 $POSTGRES_CONNECTIONS/" "$POSTGRES_DATA"/postgresql.conf
sed -ri "s/^#?(max_prepared_transactions\s*=\s*)\S+/\1 $POSTGRES_CONNECTIONS/" "$POSTGRES_DATA"/postgresql.conf
sed -ri "s/^#?(port\s*=\s*)\S+/\1 $POSTGRES_PORT /" "$POSTGRES_DATA"/postgresql.conf

# Run
exec gosu postgres "$@"
