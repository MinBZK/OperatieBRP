FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=brp-mutatielevering \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Applicatie
ADD app /opt/appserver/

# Command
CMD [ "java", "nl.bzk.brp.delivery.mutatielevering.Main" ]
