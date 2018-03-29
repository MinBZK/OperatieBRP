FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=iv-hornetq \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV HORNETQ_HOME hornetq-2.2.14.Final
ENV PORT 5445

# Applicatie
ADD app /opt/appserver/
USER root
ADD conf/*.sh /opt/appserver/$HORNETQ_HOME/bin/
ADD conf/*.xml /opt/appserver/$HORNETQ_HOME/config/stand-alone/non-clustered/
ADD conf/logging.properties /opt/appserver/$HORNETQ_HOME/config/stand-alone/non-clustered/
RUN mkdir /opt/appserver/$HORNETQ_HOME/data
RUN mkdir /opt/appserver/$HORNETQ_HOME/logs
RUN chmod 755 /opt/appserver/$HORNETQ_HOME/bin/run.sh
RUN chown --recursive appserver:appserver /opt/appserver

# Persistent data volume
VOLUME /opt/appserver/$HORNETQ_HOME/data
VOLUME /opt/appserver/$HORNETQ_HOME/logs

# Run
USER appserver
WORKDIR /opt/appserver/$HORNETQ_HOME/bin

# Export
EXPOSE $PORT 3481

CMD ./run.sh
