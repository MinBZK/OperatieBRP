START TRANSACTION;

DROP SCHEMA IF EXISTS initvul CASCADE;
CREATE SCHEMA initvul;

--
--
-- PERSONEN
--
--

CREATE TABLE initvul.initvullingresult (
  gbav_pl_id                 BIGINT,
  datum_opschorting          INTEGER,
  reden_opschorting          CHARACTER(1),
  anummer                    CHARACTER(10),
  bsn                        CHARACTER(9),
  datumtijd_opname_in_gbav   TIMESTAMP WITHOUT TIME ZONE,
  berichtidentificatie       BIGINT,
  berichttype                VARCHAR(4),
  gemeente_van_inschrijving  VARCHAR(4),
  bericht_inhoud             TEXT,
  inhoud_na_terugconversie   TEXT,
  conversie_resultaat        VARCHAR(200),
  foutmelding_terugconversie TEXT,
  PRIMARY KEY (gbav_pl_id)
) WITH (autovacuum_vacuum_scale_factor=0.05);

CREATE INDEX initvullingresult_gem_inschrijving
  ON initvul.initvullingresult (gemeente_van_inschrijving);
CREATE UNIQUE INDEX initvullingresult_anummer
  ON initvul.initvullingresult (anummer);
CREATE UNIQUE INDEX initvullingresult_resultaat_plid
  ON initvul.initvullingresult (conversie_resultaat, gbav_pl_id);

CREATE TABLE initvul.fingerprint (
  id                 BIGINT        NOT NULL,
  voorkomen_verschil VARCHAR(1000) NOT NULL,
  gbav_pl_id         BIGINT        NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE initvul.fingerprint
  ADD CONSTRAINT fingerprint_fk_idx
FOREIGN KEY (gbav_pl_id)
REFERENCES initvul.initvullingresult (gbav_pl_id);

CREATE TABLE initvul.verschil_analyse (
  id            BIGINT  NOT NULL,
  gbav_pl_id    BIGINT  NOT NULL,
  categorie     INTEGER NOT NULL,
  stapel        INTEGER NOT NULL,
  voorkomen     INTEGER,
  verschil_type VARCHAR(200),
  element       CHAR(4),
  PRIMARY KEY (id)
);

ALTER TABLE initvul.verschil_analyse
  ADD CONSTRAINT verschil_analyse_fk_idx
FOREIGN KEY (gbav_pl_id)
REFERENCES initvul.initvullingresult (gbav_pl_id);

CREATE SEQUENCE initvul.seq_fingerprint;
CREATE SEQUENCE initvul.seq_verschil_analyse;

CREATE INDEX fingerprint_plid
  ON initvul.fingerprint (gbav_pl_id);
CREATE INDEX verschil_analyse_plid
  ON initvul.verschil_analyse (gbav_pl_id);

--
--
-- AUTORISATIES
--
--

CREATE TABLE initvul.initvullingresult_aut (
  autorisatie_id            BIGINT NOT NULL,
  afnemer_code              CHAR(6),
  afnemer_naam              VARCHAR(80),
  indicatie_geheimhouding   SMALLINT,
  verstrekkings_beperking   SMALLINT,
  conditionele_verstrekking SMALLINT,
  spontaan_medium           CHAR(1),
  selectie_soort            SMALLINT,
  bericht_aand              SMALLINT,
  eerste_selectie_datum     INTEGER,
  selectie_periode          INTEGER,
  selectie_medium           CHAR(1),
  pl_plaatsings_bevoegdheid SMALLINT,
  adres_vraag_bevoegdheid   SMALLINT,
  adhoc_medium              CHAR(1),
  adres_medium              CHAR(1),
  tabel_regel_start_datum   INTEGER,
  tabel_regel_eind_datum    INTEGER,
  sleutel_rubrieken         TEXT,
  spontaan_rubrieken        TEXT,
  voorwaarde_regel_spontaan VARCHAR(4096),
  selectie_rubrieken        TEXT,
  voorwaarde_regel_selectie VARCHAR(4096),
  adhoc_rubrieken           TEXT,
  voorwaarde_regel_adhoc    VARCHAR(4096),
  adres_rubrieken           TEXT,
  voorwaarde_regel_adres    VARCHAR(4096),
  afnemers_verstrekkingen   TEXT,
  conversie_resultaat       VARCHAR(20),
  conversie_melding         TEXT,
  PRIMARY KEY (autorisatie_id)
);

CREATE INDEX initvullingresult_afnemer_code
  ON initvul.initvullingresult_aut (afnemer_code);

--
--
-- AFNEMERINDICATIES
--
--

CREATE TABLE initvul.initvullingresult_afnind (
  pl_id               BIGINT NOT NULL,
  a_nr                CHAR(10),
  bericht_resultaat   VARCHAR(20), -- TE_VERZENDEN, VERZONDEN OF VERWERKT
  PRIMARY KEY (pl_id)
) WITH (autovacuum_vacuum_scale_factor=0.05);

CREATE UNIQUE INDEX initvullingresult_afnind_anr
  ON initvul.initvullingresult_afnind (a_nr);
CREATE UNIQUE INDEX initvullingresult_afnind_resultaat_plid
  ON initvul.initvullingresult_afnind (bericht_resultaat, pl_id);

CREATE TABLE initvul.initvullingresult_afnind_stapel (
  pl_id               BIGINT   NOT NULL,
  stapel_nr           SMALLINT NOT NULL,
  conversie_resultaat VARCHAR(20), -- TE_VERWERKEN, OK OF FOUT
  conversie_melding   TEXT,
  PRIMARY KEY (pl_id, stapel_nr)
);

ALTER TABLE initvul.initvullingresult_afnind_stapel
  ADD CONSTRAINT FK_AFNIND_STAPEL FOREIGN KEY (pl_id) REFERENCES initvul.initvullingresult_afnind;

CREATE TABLE initvul.initvullingresult_afnind_regel (
  pl_id                  BIGINT   NOT NULL,
  stapel_nr              SMALLINT NOT NULL,
  volg_nr                SMALLINT NOT NULL,
  afnemer_code           CHAR(6),
  geldigheid_start_datum INTEGER,
  PRIMARY KEY (pl_id, stapel_nr, volg_nr)
);

ALTER TABLE initvul.initvullingresult_afnind_regel
  ADD CONSTRAINT FK_AFNIND FOREIGN KEY (pl_id, stapel_nr) REFERENCES initvul.initvullingresult_afnind_stapel;

--
--
-- PROTOCOLLERING
--
--

CREATE UNLOGGED TABLE initvul.initvullingresult_protocollering_activiteit (
    activiteit_id BIGINT,
    communicatie_partner CHAR(6),
    laatste_actie_dt INTEGER,
    start_dt TIMESTAMP WITH TIME ZONE,
    laatste_actie_tijdstip TIMESTAMP WITH TIME ZONE,
    dienst_selectie BOOLEAN,
    dienst_details_persoon BOOLEAN,
    dienst_mutatielevering BOOLEAN,
    dienst_attendering BOOLEAN,
    dienst_plaatsen_afnind BOOLEAN,
    a_nr CHAR(10),
    bijhouding_opschort_reden VARCHAR(1)
);

CREATE UNLOGGED TABLE initvul.initvullingresult_protocollering_brp_dienst (
    dienst_id INTEGER NOT NULL,
    toeganglevsautorisatie_id INTEGER NOT NULL,
    dienst_selectie BOOLEAN,
    dienst_details_persoon BOOLEAN,
    dienst_mutatielevering BOOLEAN,
    dienst_attendering BOOLEAN,
    dienst_plaatsen_afnind BOOLEAN,
    PRIMARY KEY(dienst_id)
);
CREATE INDEX initvullingresult_protocollering_brp_dienst_ix1 ON initvul.initvullingresult_protocollering_brp_dienst(toeganglevsautorisatie_id);


CREATE UNLOGGED TABLE initvul.initvullingresult_protocollering_brp_pers (
    id INTEGER NOT NULL,
    anr CHAR(10) NOT NULL,
    PRIMARY KEY(anr)
);


CREATE UNLOGGED TABLE initvul.initvullingresult_protocollering_brp_toeglevaut (
    id INTEGER NOT NULL,
    partij_code CHAR(6) NOT NULL,
    datingang INTEGER,
    dateinde INTEGER,
    PRIMARY KEY(id)
);
CREATE INDEX initvullingresult_protocollering_brp_toeglevaut_ix1 ON initvul.initvullingresult_protocollering_brp_toeglevaut(partij_code);

CREATE UNLOGGED TABLE initvul.initvullingresult_protocollering_dienst (
    activiteit_id BIGINT,
    toeganglevsautorisatie_id INTEGER,
    toeganglevsautorisatie_count BIGINT,
    dienst_selectie_id INTEGER,
    dienst_details_persoon_id INTEGER,
    dienst_mutatielevering_id INTEGER,
    dienst_attendering_id INTEGER,
    dienst_plaatsen_afnind_id INTEGER
);

CREATE UNLOGGED TABLE initvul.initvullingresult_protocollering_toeglevaut (
    activiteit_id BIGINT,
    dienst_selectie BOOLEAN,
    dienst_details_persoon BOOLEAN,
    dienst_mutatielevering BOOLEAN,
    dienst_attendering BOOLEAN,
    dienst_plaatsen_afnind BOOLEAN,
    toeganglevsautorisatie_id INTEGER,
    toeganglevsautorisatie_count BIGINT
);

CREATE TABLE initvul.initvullingresult_protocollering (
  activiteit_id                BIGINT NOT NULL,
  pers_id                      INTEGER,
  bijhouding_opschort_reden    VARCHAR(1),
  toeganglevsautorisatie_id    INTEGER,
  dienst_id                    INTEGER,
  start_dt                     TIMESTAMP,
  laatste_actie_dt             TIMESTAMP,
  toeganglevsautorisatie_count SMALLINT,
  conversie_resultaat          VARCHAR(200),
  foutmelding                  TEXT,
  PRIMARY KEY (activiteit_id)
) WITH (autovacuum_vacuum_scale_factor=0.05);

create index initvullingresult_protocollering_ix1 on initvul.initvullingresult_protocollering(activiteit_id) where conversie_resultaat = 'TE_VERZENDEN';

COMMIT;
