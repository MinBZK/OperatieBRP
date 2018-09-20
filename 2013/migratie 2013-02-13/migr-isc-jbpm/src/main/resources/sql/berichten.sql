DROP TABLE mig_berichten;

CREATE TABLE mig_berichten(
   id                    serial,
   tijdstip              timestamp     not null,
   kanaal                varchar(20)   not null,
   richting              char(1)       not null,
   message_id            varchar(36),
   correlation_id        varchar(36),
   bericht               text          not null,
   naam                  varchar(40),
   process_instance_id   bigint,
   bron_gemeente         varchar(4),
   doel_gemeente         varchar(4),
   herhaling             smallint,

   constraint ber_pk primary key(id)
);

CREATE INDEX on MIG_BERICHTEN(process_instance_id);
