FROM ${docker.registry}alg/postgres:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tools-ggo-database \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV POSTGRES_PORT ${ggoviewer.database.port}
ENV POSTGRES_DATABASE ${ggoviewer.database.name}
ENV POSTGRES_USER ${ggoviewer.database.username}
ENV POSTGRES_PASSWORD ${ggoviewer.database.password}

COPY sql/* /docker-entrypoint-initdb.d/
