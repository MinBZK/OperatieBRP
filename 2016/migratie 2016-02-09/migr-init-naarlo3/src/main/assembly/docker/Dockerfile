FROM ${docker.registry}/busyboxappserver:0.1.1
MAINTAINER Operatie BRP

# Setup
USER root
COPY migr-init-naarlo3.zip /opt/appserver/migr-init-naarlo3.zip
RUN cd /opt/appserver && unzip -o migr-init-naarlo3.zip
COPY conf/* /opt/appserver/conf/
RUN cd /opt/appserver && rm *.cmd && rm *.sh
RUN chown -R appserver:appserver /opt/appserver

# Run
USER appserver
WORKDIR /opt/appserver
CMD java -cp "conf:lib/*" nl.bzk.migratiebrp.init.naarlo3.Main
