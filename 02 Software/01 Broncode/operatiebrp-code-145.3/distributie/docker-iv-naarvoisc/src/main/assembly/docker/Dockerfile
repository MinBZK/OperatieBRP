FROM ${docker.registry}alg/postgresclient:${docker.image.version}
MAINTAINER Operatie BRP

LABEL nl.bzk.brp.applicatie=iv-naarvoisc \
      nl.bzk.brp.basis.naam=${docker.image.name}

# Config
ENV GBAV_DB_ENV_HOSTNAME=migr-gbavdatabase \
    GBAV_DB_ENV_PORT=5432 \
    GBAV_DB_ENV_NAME=gbav \
    GBAV_DB_ENV_USERNAME=migratie \
    GBAV_DB_ENV_PASSWORD=M1gratie \
    ISC_DB_ENV_HOSTNAME=isc-iscdatabase \
    ISC_DB_ENV_PORT=5432 \
    ISC_DB_ENV_NAME=isc \
    ISC_DB_ENV_USERNAME=migratie \
    ISC_DB_ENV_PASSWORD=M1gratie

# Applicatie
RUN set +x \
    && mkdir /app \
    && mkdir /app/sql \
    && mkdir /app/data

ADD sql /app/sql/
ADD app /app/

RUN set +x \
    && chmod +x /app/*.sh

# Volume
#VOLUME /app/data
