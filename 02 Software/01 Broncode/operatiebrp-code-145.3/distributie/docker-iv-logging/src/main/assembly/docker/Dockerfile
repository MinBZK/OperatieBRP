FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=iv-logging \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV JAVA_OPTS="-Djdk.xml.entityExpansionLimit=0 -Xmx128M"

# Applicatie
ADD app /opt/appserver/
USER root
RUN chown --recursive appserver:appserver /opt/appserver

USER appserver
WORKDIR /opt/appserver

EXPOSE 3841

# Command
CMD [ "java", "nl.bzk.migratiebrp.init.logging.runtime.Main" ]
