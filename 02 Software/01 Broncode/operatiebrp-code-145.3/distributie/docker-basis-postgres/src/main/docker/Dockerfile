FROM ${docker.registry}alg/alpine:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=postgres \
      nl.bzk.brp.basis.naam=${docker.image.name}

ENV POSTGRES_PORT=5432 \
    POSTGRES_DATA=/var/lib/postgresql/data \
    PGDATA=/var/lib/postgresql/data \
    POSTGRES_USER=postgres \
    POSTGRES_PASSWORD=postgres \
    POSTGRES_DATABASE=postgres \
    POSTGRES_DATABASE_OPTIONS="TEMPLATE=template0 LC_COLLATE='nl_NL.UTF-8' LC_CTYPE='nl_NL.UTF-8'" \
    POSTGRES_CONNECTIONS=100

COPY docker-entrypoint.sh /
COPY brp_unaccent.rules /usr/share/postgresql/tsearch_data/

RUN set +x \
    && apk add --update --allow-untrusted wget postgresql postgresql-contrib \
    && mkdir /docker-entrypoint-initdb.d \
    && wget --no-check-certificate -O /usr/local/bin/gosu "https://192.168.207.40/nexus3/repository/alpine-addon/gosu-amd64-1.sh" \
    && chmod +x /usr/local/bin/gosu \
    && apk del wget \
    && rm -rf /var/cache/apk/* \
    && chown postgres:postgres /docker-entrypoint.sh \
    && chmod +x /docker-entrypoint.sh

#VOLUME $POSTGRES_DATA
EXPOSE $POSTGRES_PORT

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["postgres"]
