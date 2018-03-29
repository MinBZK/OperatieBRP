FROM ${docker.registry}alg/tomcat:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tomcat-runtime \
      nl.bzk.brp.basis.naam=${docker.image.name}

ENV CATALINA_HOME=/opt/appserver \
    CATALINA_OUT=/dev/null \
    CATALINA_OPTS="-Xms512M -Xmx1024M -XX:MaxPermSize=256M"

RUN mkdir -p /opt/appserver \
    && cd /opt/appserver \
    && mkdir bin conf env lib logs work temp webapps

ADD bin /opt/appserver/bin/
ADD conf /opt/appserver/conf/
ADD lib /opt/appserver/lib/

RUN adduser -h /opt/appserver -g appserver -D appserver \
    && chown -R appserver:appserver /opt/appserver \
    && chmod 755 /opt/appserver/bin/*.sh

USER appserver
WORKDIR /opt/appserver

EXPOSE 8080

CMD ["/opt/appserver/bin/startup.sh"]
