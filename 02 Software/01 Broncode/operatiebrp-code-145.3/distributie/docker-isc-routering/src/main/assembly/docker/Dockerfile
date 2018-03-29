FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=isc-routering \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV PORT=61616
ENV ROUTERING_DB_ENV_HOSTNAME=isc-routeringdatabase
ENV ROUTERING_DB_ENV_PORT=5432
ENV ROUTERING_DB_ENV_NAME=routering
ENV ROUTERING_DB_ENV_USERNAME=migratie
ENV ROUTERING_DB_ENV_PASSWORD=M1gratie

# Applicatie
ADD app /opt/appserver/

# Export
EXPOSE $PORT
EXPOSE 3481

# Command
CMD [ "java", "nl.bzk.migratiebrp.routering.runtime.Main" ]
