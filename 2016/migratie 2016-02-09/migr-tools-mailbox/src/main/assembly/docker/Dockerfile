FROM ${docker.registry}/busyboxappserver:0.1.1
MAINTAINER Operatie BRP

# Environment
ENV PORT 1212
ENV JMX_HOST localhost
ENV JMX_PORT 1099

# Setup
USER root
ADD migr-tools-mailbox.zip /opt/appserver/migr-tools-mailbox.zip
RUN cd /opt/appserver && unzip -o migr-tools-mailbox.zip
ADD conf/* /opt/appserver/conf/
ADD keystores/* /opt/appserver/keystores/
RUN mkdir /opt/appserver/data
RUN chown -R appserver:appserver /opt/appserver

# Data
VOLUME /opt/appserver/conf
VOLUME /opt/appserver/data
VOLUME /opt/appserver/logs

# Run
USER appserver
WORKDIR /opt/appserver
EXPOSE $PORT $JMX_PORT

CMD java -cp conf:lib/* -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=$JMX_HOST -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false nl.bzk.migratiebrp.tools.mailbox.MailboxMain
