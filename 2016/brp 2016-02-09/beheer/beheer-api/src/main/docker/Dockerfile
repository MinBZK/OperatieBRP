FROM ${docker.registry}/busyboxtomcat:0.1.0
MAINTAINER Operatie BRP
USER root
ADD *.war /opt/appserver/webapps/
ADD *.properties /opt/appserver/env/
ADD *.xml /opt/appserver/env/
ADD setenv.sh /opt/appserver/bin/
RUN chown -R appserver:appserver /opt/appserver/webapps; chown -R appserver:appserver /opt/appserver/env; chown -R appserver:appserver /opt/appserver/bin/setenv.sh
USER appserver

ENV BRPDB_ENV_HOSTNAME=brpdb ARCHIVERINGDB_ENV_HOSTNAME=archiveringdb KERNDB_ENV_HOSTNAME=kerndb PROTOCOLLERINGDB_ENV_HOSTNAME=protocolleringdb
EXPOSE 8080
CMD ["/opt/appserver/bin/startup.sh"]
