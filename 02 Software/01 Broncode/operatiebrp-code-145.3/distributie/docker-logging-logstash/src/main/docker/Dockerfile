FROM ${docker.registry}alg/java:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=logging-logstash \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Set environment variables
ENV LOGSTASH_VERSION=5.3.2

COPY conf /opt/logstash/conf/

RUN set +x \
    && cd /tmp \
    \
    && echo Installing dependencies \
    && apk add --update --allow-untrusted bash wget openssl \
    && mkdir -p /opt \
    \
    && echo Download Logstash ${LOGSTASH_VERSION} \
    && wget -nv --no-check-certificate -O /tmp/logstash-$LOGSTASH_VERSION.tar.gz https://192.168.207.40/nexus3/repository/elastic-proxy/downloads/logstash/logstash-$LOGSTASH_VERSION.tar.gz \
    \
    && echo Verifying download \
    && checksum="e144cd85eddc83f72cbad6c340a52c281f3be7e4" \
    && sha1=$(sha1sum *.tar.gz | cut -f1 -d' ') \
    && if [ ${checksum} != ${sha1} ]; then \
        echo "[FATAL] File sha1 ${sha1} doesn't match published checksum ${checksum}. Exiting." >&2 \
        exit 1; \
       fi \
    \
    && echo Installing \
    && tar xzf /tmp/logstash-$LOGSTASH_VERSION.tar.gz -C /opt/ \
    && mv -f /opt/logstash-$LOGSTASH_VERSION/* /opt/logstash/ \
    && rm -rf /opt/logstash-$LOGSTASH_VERSION \
    \
    && echo Configure \
    && mkdir -p /opt/logstash/conf \
    && adduser -h /opt/logstash -g logstash -D logstash \
    && chown --recursive logstash:logstash /opt/logstash \
    \
    && echo Cleaning \
    && apk del wget openssl \
    && rm -rf /tmp/logstash-$LOGSTASH_VERSION.tar.gz /var/cache/apk/* \
    \
    && echo Done

USER logstash
WORKDIR /opt/logstash

#EXPOSE 5514
CMD ["./bin/logstash", "-f", "./conf/logstash.conf"]
