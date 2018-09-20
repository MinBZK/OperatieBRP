FROM ${docker.registry}/busyboxappserver:0.1.1
MAINTAINER Operatie BRP

# Environment
ENV PORT 61616
ENV JMX_HOST localhost
ENV JMX_PORT 1099

# Setup
USER root
ADD migr-tools-brp-routering.zip /opt/appserver/migr-tools-brp-routering.zip
RUN cd /opt/appserver && unzip -o migr-tools-brp-routering.zip
ADD conf/* /opt/appserver/conf/
RUN chown -R appserver:appserver /opt/appserver

# Run
USER appserver
WORKDIR /opt/appserver
EXPOSE $PORT $JMX_PORT

CMD java -cp conf:lib/* -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=$JMX_HOST -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false nl.bzk.migratiebrp.tools.brp.routering.Main
