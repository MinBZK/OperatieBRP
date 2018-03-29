FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=isc-isc \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV ROUTERING_ENV_HOSTNAME=isc-routering
ENV ROUTERING_ENV_PORT=61616
ENV ISC_DB_ENV_HOSTNAME=isc-iscdatabase
ENV ISC_DB_ENV_NAME=isc
ENV ISC_DB_ENV_PASSWORD=migratie
ENV ISC_DB_ENV_PORT=5432
ENV ISC_DB_ENV_USERNAME=M1gratie

# Applicatie
ADD app /opt/appserver/

# Export
EXPOSE 3481

# Command
CMD [ "java", "-Datomikos.unique.name=isc-${HOSTNAME}", "nl.bzk.migratiebrp.isc.runtime.Main" ]
