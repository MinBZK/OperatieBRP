-- Tabel: blokkering.mig_blokkering
DROP TABLE IF EXISTS blokkering.mig_blokkering;

DROP SCHEMA IF EXISTS blokkering CASCADE;
CREATE SCHEMA blokkering;

CREATE TABLE blokkering.mig_blokkering(
   id                    serial,
   aNummer               bigint not null,
   persoonsAanduiding    varchar(31),
   process_id            bigint,
   gemeentecode_naar     varchar(4),
   registratieGemeente   varchar(4),
   tijdstip              timestamp not null,

   constraint blokkering_pk primary key(id),
   constraint blokkering_unique unique(aNummer)
);
