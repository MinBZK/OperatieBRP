FROM ${docker.registry}alg/postgres:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=brp-database-afnemer \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV POSTGRES_PORT 5432
ENV POSTGRES_DATABASE brp
ENV POSTGRES_USER brp
ENV POSTGRES_PASSWORD brp

ENV PORT 5432
ENV NAME brp
ENV USERNAME brp
ENV PASSWORD brp

# Scripts voor database initialisatie
ADD *.sh /docker-entrypoint-initdb.d/
RUN chmod +x /docker-entrypoint-initdb.d/*.sh

ADD dbupdate /tmp/dbupdate
RUN chown -R postgres:postgres /tmp/dbupdate;
