FROM kibana:4.3.0
MAINTAINER Operatie BRP

COPY ./custom-entrypoint.sh /
RUN chmod +x ./custom-entrypoint.sh
COPY setup /setup/

ENTRYPOINT ["/custom-entrypoint.sh"]
CMD ["kibana"]
