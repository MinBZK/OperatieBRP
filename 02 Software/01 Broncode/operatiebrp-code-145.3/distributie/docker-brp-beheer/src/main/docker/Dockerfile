FROM ${docker.registry}alg/tomcat-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=brp-beheer \
      nl.bzk.brp.basis.naam=${docker.image.name}

USER root
ADD *.war /opt/appserver/webapps/
ADD beheer-docker.properties /opt/appserver/env/beheer.properties
ADD *.xml /opt/appserver/env/
ADD setenv.sh /opt/appserver/bin/
RUN chown -R appserver:appserver /opt/appserver/env; chown -R appserver:appserver /opt/appserver/bin/setenv.sh

USER appserver
ENV BRPDB_ENV_HOSTNAME=brpdb ARCHIVERINGDB_ENV_HOSTNAME=archiveringdb KERNDB_ENV_HOSTNAME=kerndb PROTOCOLLERINGDB_ENV_HOSTNAME=protocolleringdb

CMD [ "/opt/appserver/bin/startup.sh" ]
