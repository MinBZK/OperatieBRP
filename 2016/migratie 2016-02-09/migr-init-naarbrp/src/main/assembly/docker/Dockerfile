FROM ${docker.registry}/busyboxappserver:0.1.1
MAINTAINER Operatie BRP

# Setup
USER root
COPY migr-init-naarbrp.zip /opt/appserver/migr-init-naarbrp.zip
RUN cd /opt/appserver && unzip -o migr-init-naarbrp.zip
COPY conf/* /opt/appserver/conf/
RUN cd /opt/appserver && rm *.cmd && rm *.sh
COPY createInitVulling.sh /opt/appserver/
COPY *.sh /opt/appserver/
RUN chown -R appserver:appserver /opt/appserver

# Run
USER appserver
WORKDIR /opt/appserver
