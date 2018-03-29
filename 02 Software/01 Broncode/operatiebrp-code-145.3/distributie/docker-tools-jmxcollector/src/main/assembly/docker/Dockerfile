FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tools-jmxcollector \
      nl.bzk.brp.basis.naam=${docker.image.name}

COPY app /opt/appserver/

USER root
RUN chown --recursive appserver:appserver /opt/appserver

USER appserver
WORKDIR /opt/appserver

CMD [ "java", "nl.bzk.migratiebrp.tools.jmxcollector.JMXCollector" ]
