FROM ${docker.registry}alg/java:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=brp-sleutel \
      nl.bzk.brp.basis.naam=${docker.image.name}

USER root
COPY *.jks /keystores/
RUN chmod 644 /keystores/*

ENV PUBLICFILE=/keystores/publicstore.jks \
PUBLICPASSWORD=publicstore \
PRIVATEFILE=/keystores/privatestore.jks \
PRIVATEPASSWORD=privatestore \
PRIVATEKEY=serverkey \
PRIVATEKEYPASSWORD=serverkeypassword

VOLUME /keystores

CMD ["tail", "-f", "-n0", "/etc/hosts"]
