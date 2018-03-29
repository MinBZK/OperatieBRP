FROM ${docker.registry}alg/alpine:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=logging-kibana \
      nl.bzk.brp.basis.naam=${docker.image.name}

ENV KIBANA_VERSION=5.3.2 \
    ELASTICSEARCH_HOST=elasticsearch \
    ELASTICSEARCH_PORT=9200

COPY docker-entrypoint.sh /
COPY setup /opt/kibana/setup

RUN set +x \
    && cd /tmp \
    \
    && echo Installing dependencies \
    && apk add --update --allow-untrusted wget openssl nodejs bash curl \
    && mkdir -p /opt \
    \
    && echo Download Kibana $KIBANA_VERSION \
    && wget -nv --no-check-certificate -O /tmp/kibana-$KIBANA_VERSION.tar.gz https://192.168.207.40/nexus3/repository/elastic-proxy/downloads/kibana/kibana-$KIBANA_VERSION-linux-x86_64.tar.gz \
    \
    && echo Verifying download \
    && checksum="003cec9da459cec0bb0feffd6859aa47fbe72795" \
    && sha1=$(sha1sum *.tar.gz | cut -f1 -d' ') \
    && if [ ${checksum} != ${sha1} ]; then \
        echo "[FATAL] File sha1 ${sha1} doesn't match published checksum ${checksum}. Exiting." >&2 \
        exit 1; \
       fi \
    \
    && echo Installing \
    && tar xzf /tmp/kibana-$KIBANA_VERSION.tar.gz -C /opt/ \
    && mv -f /opt/kibana-$KIBANA_VERSION-linux-x86_64/* /opt/kibana \
    && rm -rf /opt/kibana-$KIBANA_VERSION-linux-x86_64 \
    \
    && echo Replacing NodeJS \
    && rm -rf /opt/kibana/node/ \
    && mkdir -p /opt/kibana/node/bin/ \
    && ln -s $(which node) /opt/kibana/node/bin/node \
    \
    && echo User \
    && adduser -h /opt/kibana -g kibana -D kibana \
    && chown --recursive kibana:kibana /opt/kibana \
    && chown kibana:kibana /docker-entrypoint.sh \
    && chmod +x /docker-entrypoint.sh \
    \
    && echo Cleaning \
    && apk del wget openssl \
    && rm -rf /tmp/kibana-$KIBANA_VERSION.tar.gz /var/cache/apk/* \
    \
    && echo Done

USER kibana
WORKDIR /opt/kibana

EXPOSE 5601
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["./bin/kibana"]
