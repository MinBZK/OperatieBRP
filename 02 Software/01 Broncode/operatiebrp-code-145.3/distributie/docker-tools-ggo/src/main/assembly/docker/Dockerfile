FROM ${docker.registry}alg/tomcat-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tools-ggo \
      nl.bzk.brp.basis.naam=${docker.image.name}

COPY *.war /opt/appserver/webapps/
COPY conf/* /opt/appserver/env/

ENV BRPDB_ENV_HOSTNAME=brpdb GGODB_ENV_HOSTNAME=ggodatabase
#EXPOSE 8080
#CMD ["/opt/appserver/bin/startup.sh"]
