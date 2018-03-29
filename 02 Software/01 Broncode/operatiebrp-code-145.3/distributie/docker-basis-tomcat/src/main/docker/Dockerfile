FROM ${docker.registry}alg/java:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tomcat \
      nl.bzk.brp.basis.naam=${docker.image.name}

ENV TOMCAT_MAJOR=7 \
    TOMCAT_VERSION=7.0.77 \
    CATALINA_BASE=/opt/apache-tomcat

RUN set +x \
    && cd /tmp \
    && echo Installing dependencies \
    && apk add --update --allow-untrusted wget \
    \
    && echo Download Tomcat ${TOMCAT_VERSION} \
    && wget --no-check-certificate https://192.168.207.40/nexus3/repository/alpine-addon/apache-tomcat-$TOMCAT_VERSION.tar.gz \
    \
    && echo Verifying download \
    && checksum="fa66329388f85c08e8d6c12ceb8b2ca3" \
    && md5=$(md5sum /tmp/apache-tomcat-$TOMCAT_VERSION.tar.gz | cut -f1 -d' ') \
    && if [ ${checksum} != ${md5} ]; then \
        echo "[FATAL] File md5 ${md5} doesn't match published checksum ${checksum}. Exiting." >&2 \
        exit 1; \
       fi \
    \
    && echo Unpacking \
    && mkdir -p /opt \
    && tar -C /opt -zxf /tmp/apache-tomcat-$TOMCAT_VERSION.tar.gz \
    && ln -s /opt/apache-tomcat-${TOMCAT_VERSION} ${CATALINA_BASE} \
    && rm -rf ${CATALINA_BASE}/webapps/* \
    && apk del wget \
    && rm -rf /tmp/* /var/cache/apk/* \
    \
    && echo Installation complete
