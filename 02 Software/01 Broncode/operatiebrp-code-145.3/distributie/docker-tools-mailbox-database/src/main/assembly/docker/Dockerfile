FROM ${docker.registry}alg/postgres:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tools-mailbox-database \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV POSTGRES_PORT ${mailbox.database.port}
ENV POSTGRES_DATABASE ${mailbox.database.name}
ENV POSTGRES_USER ${mailbox.database.username}
ENV POSTGRES_PASSWORD ${mailbox.database.password}

# Initialisatie scripts
ADD sql/create_mailbox.sql /docker-entrypoint-initdb.d/0100-create_mailbox.sql
ADD sql/data_mailbox.sql /docker-entrypoint-initdb.d/0200-data_mailbox.sql
