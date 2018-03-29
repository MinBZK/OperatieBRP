FROM ${docker.registry}alg/java:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=java-runtime \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Environment
ENV JAVA_OPTS=-Xmx512M

COPY docker-entrypoint.sh /

RUN mkdir -p /opt/appserver \
    && cd /opt/appserver \
    && mkdir bin conf data env lib logs work temp \
    && adduser -h /opt/appserver -g appserver -D appserver \
    && chown --recursive appserver:appserver /opt/appserver \
    && chown appserver:appserver /docker-entrypoint.sh \
    && chmod +x /docker-entrypoint.sh

USER appserver
WORKDIR /opt/appserver

#VOLUME ["/opt/appserver/conf"]
#VOLUME ["/opt/appserver/data"]
#VOLUME ["/opt/appserver/logs"]
#VOLUME ["/opt/appserver/work"]

ENTRYPOINT [ "/docker-entrypoint.sh" ]
CMD [ "java", "-version" ]
