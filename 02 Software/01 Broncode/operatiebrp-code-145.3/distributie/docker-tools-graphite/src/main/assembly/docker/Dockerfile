FROM diceone/alpine-graphite
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tools-graphite \
      nl.bzk.brp.basis.naam=${docker.image.name}

ENTRYPOINT ["/sbin/tini", "--"]
CMD ["/bin/bash", "/run.sh"]
