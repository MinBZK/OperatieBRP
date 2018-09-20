DROP SEQUENCE IF EXISTS SEQ_FINGERPRINT;
DROP TABLE IF EXISTS fingerprint;
DROP TABLE IF EXISTS initvullingresult;

CREATE TABLE fingerprint (
    id bigint not null,
    voorkomen_verschil varchar(1000),
    gbav_pl_id bigint,
    primary key (id)
);

CREATE TABLE initvullingresult
(
  gbav_pl_id bigint,
  bericht_inhoud text,
  datum_opschorting integer,
  reden_opschorting character(1),
  anummer bigint,
  datumtijd_opname_in_gbav timestamp without time zone,
  berichtidentificatie bigint,
  berichttype integer,
  gemeente_van_inschrijving smallint,
  inhoud_na_terugconversie text,
  bericht_diff text,
  conversie_resultaat varchar(200),
  foutmelding varchar(1000),
  foutcategorie integer,
  preconditie varchar(6),
  primary key (gbav_pl_id)
);

CREATE INDEX initvullingresult_gem_inschrijving on initvullingresult (gemeente_van_inschrijving);
CREATE UNIQUE INDEX initvullingresult_anummer on initvullingresult (anummer);
CREATE UNIQUE INDEX initvullingresult_resultaat_plid on initvullingresult (conversie_resultaat, gbav_pl_id);

alter table fingerprint
        add constraint FKADFCE8E43D3973B0
        foreign key (gbav_pl_id)
        references initvullingresult(gbav_pl_id);

create sequence SEQ_FINGERPRINT;
