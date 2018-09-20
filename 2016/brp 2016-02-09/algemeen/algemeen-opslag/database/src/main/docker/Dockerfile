FROM postgres:9.4.5
MAINTAINER Operatie BRP

RUN localedef -i nl_NL -c -f UTF-8 -A /usr/share/locale/locale.alias nl_NL.UTF-8
ENV LANG nl_NL.utf8

ENV PGDATA /var/lib/postgresql/data/pgdata
ENV PORT 5432
ENV NAME brp
ENV USERNAME brp
ENV PASSWORD brp

ADD *.sh /docker-entrypoint-initdb.d/
ADD postgresql.conf /docker-entrypoint-initdb.d/

ADD dbupdate /tmp/dbupdate
RUN mv /docker-entrypoint-initdb.d/docker-entrypoint.sh / && chown -R postgres:postgres /tmp/dbupdate;

# Expose the PostgreSQL port
EXPOSE $PORT

# Add VOLUMEs to allow backup of config, logs and databases
VOLUME ["$PGDATA"]

ENTRYPOINT ["/docker-entrypoint.sh"]

CMD ["postgres", "-i", "-D", "/var/lib/postgresql/data/pgdata", "-c", "config_file=/var/lib/postgresql/data/pgdata/postgres.conf"]
