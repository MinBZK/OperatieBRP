FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=iv-naarlo3 \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Applicatie
ADD app /opt/appserver/
USER root
RUN chown --recursive appserver:appserver /opt/appserver

USER appserver
WORKDIR /opt/appserver

# Command
CMD [ "java", "nl.bzk.migratiebrp.init.naarlo3.Main" ]
