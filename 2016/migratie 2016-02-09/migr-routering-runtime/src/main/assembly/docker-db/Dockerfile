FROM ${docker.registry}/postgresql93:0.1.1
MAINTAINER Operatie BRP

# Configuratie
ENV PGDATA /var/lib/pgsql/data
ENV PORT ${routering.database.port}
ENV NAME ${routering.database.name}
ENV USERNAME ${routering.database.username}
ENV PASSWORD ${routering.database.password}

# Configureren database
USER root
ADD postgresql.conf $PGDATA/postgresql.conf
RUN chown postgres:postgres $PGDATA/postgresql.conf ; \
    chmod 644 $PGDATA/postgresql.conf ; \
    echo "local all $USERNAME    md5" >> $PGDATA/pg_hba.conf ; \
    echo "host  all $USERNAME 0.0.0.0/0   md5" >> $PGDATA/pg_hba.conf ; \
    chown postgres:postgres $PGDATA/pg_hba.conf

# Aanmaken database
USER postgres
RUN /usr/pgsql-9.3/bin/pg_ctl -w start ;\
    /usr/pgsql-9.3/bin/psql -U postgres -c "CREATE ROLE $USERNAME LOGIN ENCRYPTED PASSWORD '$PASSWORD' SUPERUSER INHERIT NOCREATEDB NOCREATEROLE;" ; \
    /usr/pgsql-9.3/bin/psql -U postgres -c "CREATE DATABASE $NAME WITH OWNER = $USERNAME TEMPLATE = template0 ENCODING = 'UTF8';" ; \
    /usr/pgsql-9.3/bin/pg_ctl -w stop

# Aanmaken database objecten
USER root
ADD sql/* /tmp/routering/
RUN chown -R postgres:postgres /tmp/routering

USER postgres
RUN /usr/pgsql-9.3/bin/pg_ctl -w start ;\
	/usr/pgsql-9.3/bin/psql -d $NAME -U $USERNAME -f /tmp/routering/activemq-create.sql ;\
    /usr/pgsql-9.3/bin/pg_ctl -w stop

# Expose the PostgreSQL port
EXPOSE $PORT

# Add VOLUMEs to allow backup of config, logs and databases
VOLUME ["$PGDATA"]

CMD ["/usr/pgsql-9.3/bin/postgres", "-i", "-D", "/var/lib/pgsql/data", "-c", "config_file=/var/lib/pgsql/data/postgresql.conf"]
