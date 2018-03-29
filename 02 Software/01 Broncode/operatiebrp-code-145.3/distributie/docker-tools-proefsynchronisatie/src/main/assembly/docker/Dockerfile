FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tools-proefsynchronisatie

ENV MAX_BATCH_SIZE=1000

# Applicatie
ADD app /opt/appserver/
USER root
RUN chmod +x /opt/appserver/*.sh
USER appserver
