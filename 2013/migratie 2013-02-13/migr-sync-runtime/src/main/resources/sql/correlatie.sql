-- Tabel: correlatie.mig_sync_correlatie
DROP TABLE IF EXISTS correlatie.mig_sync_correlatie;

DROP SCHEMA IF EXISTS correlatie CASCADE;
CREATE SCHEMA correlatie;

CREATE TABLE correlatie.mig_sync_correlatie (
   id                    serial,
   message_id            varchar(36) not null,
   aNummer               bigint not null,
   gemeentecode_naar     varchar(4),
   aktenummer            varchar(7),
   tijdstip              timestamp not null,

   constraint sync_correlatie_pk primary key(id),
   constraint sync_correlatie_unique unique(message_id)
);
