FROM ${docker.registry}/busyboxappserver:0.1.1
MAINTAINER Operatie BRP

# Environment
ENV HORNETQ_HOME hornetq-2.2.14.Final
ENV HORNETQ_ZIP ${HORNETQ_HOME}.zip
ENV PORT 5445
ENV JMX_HOST localhost
ENV JMX_PORT 5465

# Setup
USER root
COPY $HORNETQ_ZIP /opt/appserver/$HORNETQ_ZIP
RUN cd /opt/appserver && unzip -o $HORNETQ_ZIP
COPY conf/run.sh /opt/appserver/$HORNETQ_HOME/bin/
COPY conf/hornetq-jms.xml /opt/appserver/$HORNETQ_HOME/config/stand-alone/non-clustered/
COPY conf/hornetq-configuration.xml /opt/appserver/$HORNETQ_HOME/config/stand-alone/non-clustered/
RUN mkdir /opt/appserver/$HORNETQ_HOME/data
RUN mkdir VOLUME /opt/appserver/$HORNETQ_HOME/logs
RUN chmod 755 /opt/appserver/$HORNETQ_HOME/bin/run.sh
RUN chown -R appserver:appserver /opt/appserver

# Persistent data volume
VOLUME /opt/appserver/$HORNETQ_HOME/data
VOLUME /opt/appserver/$HORNETQ_HOME/logs

# Run
USER appserver
WORKDIR /opt/appserver/$HORNETQ_HOME/bin
EXPOSE $PORT $JMX_PORT

CMD ./run.sh
