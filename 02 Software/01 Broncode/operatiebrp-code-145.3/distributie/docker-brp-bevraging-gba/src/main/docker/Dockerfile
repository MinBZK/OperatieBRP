FROM ${docker.registry}alg/tomcat-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=brp-bevraging-gba \
      nl.bzk.brp.basis.naam=${docker.image.name}

ADD *.war /opt/appserver/webapps/
ADD *.properties /opt/appserver/env/
ADD *.xml /opt/appserver/env/
ADD setenv.sh /opt/appserver/bin/

USER root
RUN chown -R appserver:appserver /opt/appserver/env &&\
 chown -R appserver:appserver /opt/appserver/bin/setenv.sh &&\
 mkdir -p /data/activemq-data/scheduler &&\
 chown -R appserver:appserver /data/activemq-data

USER appserver
WORKDIR /opt/appserver

CMD [ "/opt/appserver/bin/startup.sh" ]
