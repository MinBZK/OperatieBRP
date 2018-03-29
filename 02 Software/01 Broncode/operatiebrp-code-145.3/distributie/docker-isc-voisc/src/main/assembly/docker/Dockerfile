FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=isc-voisc \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV MAILBOX_ENV_HOSTNAME=migr-mailbox
ENV MAILBOX_ENV_PORT=1212
ENV ROUTERING_ENV_HOSTNAME=isc-routering
ENV ROUTERING_ENV_PORT=61616
ENV VOISC_DB_ENV_HOSTNAME=isc-voiscdatabase
ENV VOISC_DB_ENV_NAME=voisc
ENV VOISC_DB_ENV_PASSWORD=migratie
ENV VOISC_DB_ENV_PORT=5432
ENV VOISC_DB_ENV_USERNAME=M1gratie

# Applicatie
ADD app /opt/appserver/

# Export
EXPOSE 3481

# Command
CMD [ "java", "nl.bzk.migratiebrp.voisc.runtime.VoiscMain" ]
