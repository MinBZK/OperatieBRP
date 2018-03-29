FROM ${docker.registry}alg/java:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=logging-elasticsearch \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Set environment variables
ENV ELASTICSEARCH_VERSION=5.3.2

COPY config /opt/elasticsearch/config
COPY docker-entrypoint.sh /

RUN echo Installing dependencies \
    && set +x \
    && apk add --update --allow-untrusted wget openssl bash \
    && mkdir -p /opt \
    \
    && echo Download Elasticsearch $ELASTICSEARCH_VERSION \
    && wget -nv --no-check-certificate -O /tmp/elasticsearch-$ELASTICSEARCH_VERSION.tar.gz https://192.168.207.40/nexus3/repository/elastic-proxy/downloads/elasticsearch/elasticsearch-$ELASTICSEARCH_VERSION.tar.gz \
    \
    && echo Verifying download \
    && checksum="154e46d304c1226b52b489c9270d793c48b801ec" \
    && sha1=$(sha1sum /tmp/elasticsearch-$ELASTICSEARCH_VERSION.tar.gz | cut -f1 -d' ') \
    && if [ ${checksum} != ${sha1} ]; then \
        echo "[FATAL] File sha1 ${sha1} doesn't match published checksum ${checksum}. Exiting." >&2 \
        exit 1; \
       fi \
    \
    && echo Installing \
    && tar xzf /tmp/elasticsearch-$ELASTICSEARCH_VERSION.tar.gz -C /opt/ \
    && rm -rf /opt/elasticsearch-$ELASTICSEARCH_VERSION/config \
    && mv -f /opt/elasticsearch-$ELASTICSEARCH_VERSION/* /opt/elasticsearch \
    && rm -rf /opt/elasticsearch-$ELASTICSEARCH_VERSION \
    \
    && echo User \
    && mkdir -p /opt/elasticsearch/data \
    && mkdir -p /opt/elasticsearch/logs \
    && mkdir -p /opt/elasticsearch/config/scripts \
    && adduser -h /opt/elasticsearch -g elasticsearch -D elasticsearch \
    && chown --recursive elasticsearch:elasticsearch /opt/elasticsearch \
    && chown elasticsearch:elasticsearch /docker-entrypoint.sh \
    && chmod +x /docker-entrypoint.sh \
    \
    && echo Cleaning \
    && apk del wget openssl \
    && rm -rf /tmp/elasticsearch-$ELASTICSEARCH_VERSION.tar.gz /var/cache/apk/* \
    \
    && echo Done

USER elasticsearch
WORKDIR /opt/elasticsearch

EXPOSE 9200 9300
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["./bin/elasticsearch"]
