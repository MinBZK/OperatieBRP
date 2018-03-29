FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=iv-naarbrp \
      nl.bzk.brp.basis.naam=${docker.image.name}

ENV IV_BATCH_PERSOON=100 \
    IV_BATCH_AUTORISATIE=100 \
    IV_BATCH_AFNEMERSINDICATIE=100 \
    IV_BATCH_PROTOCOLLERING=100 \
    IV_AANTAL_PROTOCOLLERING=100

# Applicatie
ADD app /opt/appserver/
USER root
RUN chown --recursive appserver:appserver /opt/appserver && chmod +x /opt/appserver/*.sh

USER appserver
