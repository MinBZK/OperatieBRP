FROM ${docker.registry}/busyboxappserver:0.1.1
MAINTAINER Operatie BRP

# Configuratie
ENV JMX_HOST localhost
ENV JMX_PORT 1099

# Setup
USER root
ADD migr-voisc-runtime.zip /opt/appserver/migr-voisc-runtime.zip
RUN cd /opt/appserver && unzip -o migr-voisc-runtime.zip
ADD conf/* /opt/appserver/conf/
RUN echo 'changeit' > .voiscPwd
RUN chown -R appserver:appserver /opt/appserver

# External
EXPOSE $JMX_PORT
VOLUME ["/opt/appserver/conf"]
VOLUME ["/opt/appserver/logs"]
VOLUME ["/opt/appserver/work"]

# Run
USER appserver
WORKDIR /opt/appserver
CMD java -cp conf:lib/* -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=$JMX_HOST -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false nl.bzk.migratiebrp.voisc.runtime.VoiscMain
