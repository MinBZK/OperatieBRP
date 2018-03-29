FROM ${docker.registry}alg/alpine:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=postgres-client \
      nl.bzk.brp.basis.naam=${docker.image.name}

RUN set +x \
    && apk add --update --allow-untrusted postgresql-client \
    && rm -rf /var/cache/apk/* \

CMD ["/bin/sh"]
