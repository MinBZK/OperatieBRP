FROM ${docker.registry}alg/tomcat-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=brp-vrijbericht-gba \
      nl.bzk.brp.basis.naam=${docker.image.name}

USER root
ADD *.war /opt/appserver/webapps/
ADD *.properties /opt/appserver/env/
ADD *.xml /opt/appserver/env/
ADD setenv.sh /opt/appserver/bin/
RUN chown -R appserver:appserver /opt/appserver/env; chown -R appserver:appserver /opt/appserver/bin/setenv.sh

USER appserver
WORKDIR /opt/appserver

CMD [ "/opt/appserver/bin/startup.sh" ]
