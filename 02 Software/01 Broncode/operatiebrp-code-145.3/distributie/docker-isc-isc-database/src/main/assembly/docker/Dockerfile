FROM ${docker.registry}alg/postgres:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=isc-isc-database \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV POSTGRES_PORT ${isc.database.port}
ENV POSTGRES_DATABASE ${isc.database.name}
ENV POSTGRES_USER ${isc.database.username}
ENV POSTGRES_PASSWORD ${isc.database.password}

# Initialisatie scripts
ADD sql/jbpm-create.sql /docker-entrypoint-initdb.d/0100-jbpm-create.sql
ADD sql/qrtz-create.sql /docker-entrypoint-initdb.d/0200-qrtz-create.sql
ADD sql/mig-create.sql /docker-entrypoint-initdb.d/0300-mig-create.sql
ADD sql/mig-data_docker.sql /docker-entrypoint-initdb.d/0400-mig-data.sql
