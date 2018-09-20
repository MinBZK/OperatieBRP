FROM fac-reg.modernodam.nl:5000/busyboxtomcat:0.1.0
MAINTAINER Operatie BRP
USER root
ADD *.war /opt/appserver/webapps/
ADD *.properties /opt/appserver/env/
ADD *.xml /opt/appserver/env/
ADD conf/* /opt/appserver/conf/
ADD setenv.sh /opt/appserver/bin/
RUN chown -R appserver:appserver /opt/appserver/webapps &&\
 chown -R appserver:appserver /opt/appserver/env &&\
 chown -R appserver:appserver /opt/appserver/bin/setenv.sh &&\
 mkdir -p /data/activemq-data/scheduler &&\
 chown -R appserver:appserver /data/activemq-data
USER appserver
EXPOSE 8080
CMD ["/opt/appserver/bin/startup.sh"]
