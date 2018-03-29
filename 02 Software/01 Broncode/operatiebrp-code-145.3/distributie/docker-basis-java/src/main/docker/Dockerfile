FROM ${docker.registry}alg/alpine:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=java \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Install GNU libc (aka glibc)
RUN ALPINE_GLIBC_PACKAGE_VERSION="2.23-r1" && \
    ALPINE_GLIBC_BASE_PACKAGE="glibc" && \
    ALPINE_GLIBC_BIN_PACKAGE="glibc-bin" && \
    ALPINE_GLIBC_I18N_PACKAGE="glibc-i18n" && \
    apk add --update --no-cache --allow-untrusted \
        "$ALPINE_GLIBC_BASE_PACKAGE" \
        "$ALPINE_GLIBC_BIN_PACKAGE" \
        "$ALPINE_GLIBC_I18N_PACKAGE" && \
    \
    /usr/glibc-compat/bin/localedef --force --inputfile POSIX --charmap UTF-8 C.UTF-8 || true && \
    echo "export LANG=C.UTF-8" > /etc/profile.d/locale.sh && \
    \
    apk del glibc-i18n

ENV JAVA_PACKAGE=zulu8.20.0.5
ENV JAVA_VERSION=jdk8.0.121-linux_x64
ENV PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/java/bin:/usr/local/java/jre/bin
ENV JAVA_HOME=/usr/local/java
ENV JRE=/usr/local/java/jre

# Install OpenJDK Java 8 (Zulu build) and check the JAVA_HOME is set correctly
RUN set +x \
    && cd /tmp \
    && echo Installing dependencies \
    && echo ipv6 >> /etc/modules \
    && apk add --update --no-cache --allow-untrusted wget \
    && sed -i -e 's#:/bin/[^:].*$#:/sbin/nologin#' /etc/passwd \
    \
    && echo Download Java ${JAVA_PACKAGE}-${JAVA_VERSION} \
    && zurl=https://192.168.207.40/nexus3/repository/alpine-addon/$JAVA_PACKAGE-$JAVA_VERSION.tar.gz \
    && wget -nv --no-check-certificate $zurl \
    \
    && echo Verifying download \
    && checksum="e5f4b1d997e50ffe4998c68c8ec45403" \
    && md5=$(md5sum *.tar.gz | cut -f1 -d' ') \
    && if [ ${checksum} != ${md5} ]; then \
        echo "[FATAL] File md5 ${md5} doesn't match published checksum ${checksum}. Exiting." >&2 ; \
        exit 1; \
       fi \
    \
    && echo Unpacking Java \
    && tar -xzf ${JAVA_PACKAGE}-${JAVA_VERSION}*.tar.gz \
    && rm ${JAVA_PACKAGE}-${JAVA_VERSION}.tar.gz \
    && cd ${JAVA_PACKAGE}-${JAVA_VERSION} \
    && mkdir -p /usr/local/java \
    && mv -f * /usr/local/java \
    && cd .. \
    && rmdir ${JAVA_PACKAGE}-${JAVA_VERSION} \
    \
    && echo Minimizing Java \
    && cd /usr/local/java \
    && rm -rf *.zip demo man sample \
    && for ff in /usr/local/java/bin/*; do f=$(basename $ff); if [ -e /usr/local/java/jre/bin/$f ]; then ln -snf /usr/local/java/jre/bin/$f $ff; fi; done \
    && rm -rf /tmp/* /var/cache/apk/* \
    \
    && echo Verifying configuration \
    && { \
        echo '#!/bin/sh'; \
        echo 'set -e'; \
        echo; \
        echo 'dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"'; \
       } > /usr/local/bin/check-java-home.sh \
    && chmod +x /usr/local/bin/check-java-home.sh \
    && [ "/usr/local/java" = "$(check-java-home.sh)" ] \
    && rm /usr/local/bin/check-java-home.sh \
    \
    && apk del wget \
    && echo Installation complete \
    && java -version
