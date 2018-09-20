FROM ${docker.registry}/busyboxtomcat:0.1.0
MAINTAINER Operatie BRP
#ENV JMX_PORT 1099
USER root
COPY *.war /opt/appserver/webapps/ggo-viewer.war
COPY conf/* /opt/appserver/env/
#ADD setenv.sh /opt/appserver/bin/
RUN chown -R appserver:appserver /opt/appserver/webapps; chown -R appserver:appserver /opt/appserver/env
USER appserver
#EXPOSE 8080 $JMX_PORT
EXPOSE 8080
CMD ["/opt/appserver/bin/startup.sh"]
