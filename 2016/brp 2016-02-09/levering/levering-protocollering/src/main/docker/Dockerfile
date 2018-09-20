FROM ${docker.registry}/busyboxtomcat:0.1.0
MAINTAINER Operatie BRP
ENV JMX_PORT 1099
USER root
ADD *.war /opt/appserver/webapps/
ADD *.properties /opt/appserver/env/
ADD *.xml /opt/appserver/env/
ADD setenv.sh /opt/appserver/bin/
RUN chown -R appserver:appserver /opt/appserver/webapps; chown -R appserver:appserver /opt/appserver/env; chown -R appserver:appserver /opt/appserver/bin/setenv.sh
USER appserver
EXPOSE 8080 $JMX_PORT
CMD ["/opt/appserver/bin/startup.sh"]
