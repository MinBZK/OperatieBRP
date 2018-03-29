FROM ${docker.registry}alg/tomcat-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=isc-isc-console \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Configuratie
ENV ISC_DB_ENV_HOSTNAME=isc-iscdatabase
ENV ISC_DB_ENV_NAME=isc
ENV ISC_DB_ENV_PASSWORD=migratie
ENV ISC_DB_ENV_PORT=5432
ENV ISC_DB_ENV_USERNAME=M1gratie
ENV ISC_ENV_JMX_HOSTNAME=isc-isc
ENV ISC_ENV_JMX_PASSWORD=admin
ENV ISC_ENV_JMX_PORT=3481
ENV ISC_ENV_JMX_USERNAME=admin

# Applicatie
ADD *.war /opt/appserver/webapps/
ADD *.properties /opt/appserver/env/
ADD *.xml /opt/appserver/env/
ADD conf/* /opt/appserver/conf/
ADD lib/* /opt/appserver/lib/
ADD setenv.sh /opt/appserver/bin/

USER root
RUN chown -R appserver:appserver /opt/appserver/env &&\
 chown -R appserver:appserver /opt/appserver/bin/setenv.sh &&\
 mkdir -p /data/activemq-data/scheduler &&\
 chown -R appserver:appserver /data/activemq-data &&\
 echo Installing fonts &&\
 set +x &&\
 cd /tmp &&\
 apk add --update --no-cache --allow-untrusted libpng ttf-freefont

USER appserver
WORKDIR /opt/appserver

EXPOSE 3481

CMD [ "/opt/appserver/bin/startup.sh" ]
