FROM ${docker.registry}alg/postgres:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=isc-voisc-database \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV POSTGRES_PORT ${voisc.database.port}
ENV POSTGRES_DATABASE ${voisc.database.name}
ENV POSTGRES_USER ${voisc.database.username}
ENV POSTGRES_PASSWORD ${voisc.database.password}

# Initialisatie scripts
ADD sql/voisc-create.sql /docker-entrypoint-initdb.d/0100-voisc-create.sql
ADD sql/voisc-data.sql /docker-entrypoint-initdb.d/0200-voisc-data.sql
