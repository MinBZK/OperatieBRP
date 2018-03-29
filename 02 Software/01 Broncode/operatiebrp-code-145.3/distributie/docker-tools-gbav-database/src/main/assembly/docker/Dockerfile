FROM ${docker.registry}alg/postgres:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tools-gbav-database \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV POSTGRES_PORT ${gbav.database.port}
ENV POSTGRES_DATABASE ${gbav.database.name}
ENV POSTGRES_USER ${gbav.database.username}
ENV POSTGRES_PASSWORD ${gbav.database.password}

# Initialisatie scripts
ADD sql/create_lo3.sql /docker-entrypoint-initdb.d/0100-create_lo3.sql
ADD sql/createInitVullingTables.sql /docker-entrypoint-initdb.d/0200-createInitVullingTables.sql
ADD sql/init_lo3_database.sql /docker-entrypoint-initdb.d/0300-init_lo3_database.sql
