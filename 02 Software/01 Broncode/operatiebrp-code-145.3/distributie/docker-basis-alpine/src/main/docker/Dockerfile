FROM alpine:3.5
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=alpine \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Default to UTF-8 file.encoding
ENV LANG=C.UTF-8

COPY dockerize /
RUN chmod a+x /dockerize

# Upgrade alpine
RUN set -x \
    && echo "https://192.168.207.40/nexus3/repository/alpine-proxy/v3.5/main" > /etc/apk/repositories \
    && echo "https://192.168.207.40/nexus3/repository/alpine-proxy/v3.5/community" >> /etc/apk/repositories \
    && echo "https://192.168.207.40/nexus3/repository/alpine-addon" >> /etc/apk/repositories \
    && apk upgrade --update --allow-untrusted \
    && rm -rf /var/cache/apk/*
