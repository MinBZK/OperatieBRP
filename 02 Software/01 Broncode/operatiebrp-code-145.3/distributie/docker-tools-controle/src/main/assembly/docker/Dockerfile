FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=migr-controle \
      nl.bzk.brp.basis.naam=${docker.image.name}

COPY app /opt/appserver/

CMD [ "tail", "-f", "/dev/null" ]
