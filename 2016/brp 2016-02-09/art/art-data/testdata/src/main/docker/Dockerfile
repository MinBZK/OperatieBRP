FROM ${docker.registry}/postgresql93:0.1.1
MAINTAINER Operatie BRP

USER root

ENV PGDATA /var/lib/pgsql/data
ENV DBUSER brp
ENV DBPASS brp
ENV DBNAME brp

ADD postgresql.conf $PGDATA/postgresql.conf
RUN chown postgres:postgres $PGDATA/postgresql.conf ; \
    chmod 644 $PGDATA/postgresql.conf ; \
    echo "local all brp    md5" >> $PGDATA/pg_hba.conf ; \
    echo "host  all brp 0.0.0.0/0   md5" >> $PGDATA/pg_hba.conf ; \
    chown postgres:postgres $PGDATA/pg_hba.conf

ADD dbupdate /tmp/dbupdate
RUN chown -R postgres:postgres /tmp/dbupdate;

USER postgres
## Configuratie SOA database
RUN /usr/pgsql-9.3/bin/pg_ctl -w start ;\
    /usr/pgsql-9.3/bin/psql -U postgres -c "CREATE ROLE brp LOGIN ENCRYPTED PASSWORD 'brp' SUPERUSER INHERIT NOCREATEDB NOCREATEROLE;" ; \
    /usr/pgsql-9.3/bin/psql -U postgres -c "CREATE DATABASE \"brp\" WITH OWNER = brp ENCODING = 'UTF8' TABLESPACE = pg_default TEMPLATE = template0 LC_COLLATE = 'POSIX' LC_CTYPE = 'POSIX' CONNECTION LIMIT = -1;" ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/init-database.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/kern/Kern_BRP_structuur.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/kern/Kern_BRP_statische_stamgegevens.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/kern/Kern_BRP_dynamische_stamgegevens.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/kern/Kern_BRP_structuur_aanvullend.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/kern/Kern_BRP_custom_changes.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/kern/Kern_BRP_indexen.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/kern/Kern_BRP_rechten.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/lev/Protocol_BRP_structuur.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/lev/Protocol_BRP_structuur_aanvullend.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/lev/Protocol_BRP_statische_stamgegevens.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/lev/Protocol_BRP_indexen.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/lev/Protocol_BRP_rechten.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/ber/Bericht_BRP_structuur.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/ber/Bericht_BRP_structuur_aanvullend.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/ber/Bericht_BRP_statische_stamgegevens.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/ber/Bericht_custom-changes.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/ber/Bericht_BRP_indexen.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/ber/Bericht_BRP_rechten.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/kern/99-brp-version.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/kern/93-brp-analyze.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/93-deleteCascade.sql ; \
    /usr/pgsql-9.3/bin/psql -U brp -d brp -f /tmp/dbupdate/testdata/specials.sql ; \
    /usr/pgsql-9.3/bin/pg_ctl -w stop

# Expose the PostgreSQL port
EXPOSE 5432

# Add VOLUMEs to allow backup of config, logs and databases
VOLUME ["$PGDATA"]

CMD ["/usr/pgsql-9.3/bin/postgres", "-i", "-D", "/var/lib/pgsql/data", "-c", "config_file=/var/lib/pgsql/data/postgresql.conf"]
