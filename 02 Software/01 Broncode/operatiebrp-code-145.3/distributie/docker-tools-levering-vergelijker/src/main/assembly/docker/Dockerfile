FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tools-levering-vergelijker

# Applicatie
ADD app /opt/appserver/
USER root
RUN chmod +x /opt/appserver/*.sh
USER appserver
