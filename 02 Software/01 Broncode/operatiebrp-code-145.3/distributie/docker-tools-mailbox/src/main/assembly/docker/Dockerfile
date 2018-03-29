FROM ${docker.registry}alg/java-runtime:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=tools-mailbox \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Environment
ENV PORT=1212

COPY app /opt/appserver/

USER root

# Export
EXPOSE $PORT
EXPOSE 3481

CMD [ "java", "nl.bzk.migratiebrp.tools.mailbox.MailboxMain" ]
