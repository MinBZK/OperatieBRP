FROM logstash:2.1.0
MAINTAINER Operatie BRP

# JMX Plugin
COPY plugins-*.tar.gz /opt/logstash/plugins.tar.gz
RUN cd /opt/logstash/bin && ./plugin unpack /opt/logstash/plugins.tar.gz && ./plugin install --local logstash-input-jmx

# Configuration
COPY conf /conf/

CMD ["logstash", "agent", "-f", "/conf/logstash.conf"]
