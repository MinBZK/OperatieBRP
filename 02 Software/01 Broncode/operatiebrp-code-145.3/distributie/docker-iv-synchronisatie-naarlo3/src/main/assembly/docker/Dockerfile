FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=iv-synchronisatie-naarlo3 \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV BRPROUTERING_ENV_HOSTNAME=brp-messagebroker
ENV BRPROUTERING_ENV_PORT=61616
ENV ROUTERING_ENV_HOSTNAME=isc-routering
ENV ROUTERING_ENV_PORT=61616
ENV BRP_DB_ENV_HOSTNAME=brp-database
ENV BRP_DB_ENV_NAME=brp
ENV BRP_DB_ENV_PASSWORD=brp
ENV BRP_DB_ENV_PORT=5432
ENV BRP_DB_ENV_USERNAME=brp
ENV IV_ROUTERING_ENV_HOSTNAME=iv-hornetq
ENV IV_ROUTERING_ENV_PORT=5445

# Applicatie
ADD app /opt/appserver/
USER root
RUN chown --recursive appserver:appserver /opt/appserver

USER appserver
WORKDIR /opt/appserver

# Export
EXPOSE 3481

# Command
CMD [ "java", "-Datomikos.unique.name=sync-${HOSTNAME}", "nl.bzk.migratiebrp.synchronisatie.runtime.Main", "-modus", "${sync.modus}" ]
