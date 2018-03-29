FROM ${docker.registry}alg/java:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=brp-beheer-selectie \
      nl.bzk.brp.basis.naam=${docker.image.name}

VOLUME /tmp
RUN mkdir -p /opt/appserver \
    && cd /opt/appserver \
    && adduser -h /opt/appserver -g appserver -D appserver

ADD beheer-selectie.jar /opt/appserver/beheer-selectie.jar
ADD application.properties /opt/appserver/application.properties

USER root
RUN sh -c 'touch /opt/appserver/beheer-selectie.jar'
RUN sh -c 'touch /opt/appserver/application.properties'
RUN chown -R appserver:appserver /opt/appserver
ENV JAVA_OPTS=""

USER appserver
WORKDIR /opt/appserver
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /opt/appserver/beheer-selectie.jar $JAVA_OPTS" ]
