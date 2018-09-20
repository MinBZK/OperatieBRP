DROP SCHEMA IF EXISTS initvul CASCADE;
CREATE SCHEMA initvul;

SET DATABASE TRANSACTION CONTROL MVCC;
SET DATABASE SQL SYNTAX PGS TRUE;
SET DATABASE REFERENTIAL INTEGRITY TRUE;

CREATE TABLE initvul.initvullingresult (
  gbav_pl_id BIGINT,
  datum_opschorting INTEGER,
  reden_opschorting CHARACTER(1),
  anummer BIGINT,
  datumtijd_opname_in_gbav TIMESTAMP WITHOUT TIME ZONE,
  berichtidentificatie BIGINT,
  berichttype INTEGER,
  gemeente_van_inschrijving VARCHAR(4),
  bericht_inhoud TEXT,
  inhoud_na_terugconversie TEXT,
  conversie_resultaat VARCHAR(200),
  foutmelding VARCHAR(1000),
  foutcategorie INTEGER,
  preconditie VARCHAR(6),
  PRIMARY KEY (gbav_pl_id)
);

CREATE INDEX initvullingresult_gem_inschrijving ON initvul.initvullingresult (gemeente_van_inschrijving);
CREATE UNIQUE INDEX initvullingresult_anummer ON initvul.initvullingresult (anummer);
CREATE UNIQUE INDEX initvullingresult_resultaat_plid ON initvul.initvullingresult (conversie_resultaat, gbav_pl_id);

CREATE TABLE initvul.fingerprint (
  id                 BIGINT NOT NULL,
  voorkomen_verschil VARCHAR(1000) NOT NULL,
  gbav_pl_id         BIGINT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE initvul.fingerprint
        ADD CONSTRAINT fingerprint_fk_idx
        FOREIGN KEY (gbav_pl_id)
        REFERENCES initvul.initvullingresult(gbav_pl_id);

CREATE TABLE initvul.verschil_analyse (
    id BIGINT NOT NULL,
    gbav_pl_id BIGINT NOT NULL,
    categorie INTEGER NOT NULL,
    stapel INTEGER NOT NULL,
    voorkomen INTEGER,
    verschil_type VARCHAR(200),
    element CHAR(4),
    PRIMARY KEY (id)
);

ALTER TABLE initvul.verschil_analyse
        ADD CONSTRAINT verschil_analyse_fk_idx
        FOREIGN KEY (gbav_pl_id)
        REFERENCES initvul.initvullingresult(gbav_pl_id);

CREATE SEQUENCE initvul.seq_fingerprint;
CREATE SEQUENCE initvul.seq_verschil_analyse;

CREATE INDEX fingerprint_plid ON initvul.fingerprint (gbav_pl_id);
CREATE INDEX verschil_analyse_plid ON initvul.verschil_analyse (gbav_pl_id);


CREATE TABLE initvul.initvullingresult_aut (
        afnemer_code BIGINT,
        conversie_resultaat VARCHAR(200),
        foutmelding TEXT,
        PRIMARY KEY (afnemer_code)
);

CREATE UNIQUE INDEX initvullingresult_aut_resultaat_id ON initvul.initvullingresult_aut (conversie_resultaat, afnemer_code);

CREATE TABLE initvul.initvullingresult_aut_regel (
		autorisatie_id BIGINT NOT NULL,
		afnemer_code BIGINT,
		afnemer_naam VARCHAR(80),
		indicatie_geheimhouding SMALLINT,
		verstrekkings_beperking SMALLINT,
		conditionele_verstrekking SMALLINT,
		spontaan_medium CHAR(1),
		selectie_soort SMALLINT,
		bericht_aand SMALLINT,
		eerste_selectie_datum INTEGER,
		selectie_periode INTEGER,
		selectie_medium CHAR(1),
		pl_plaatsings_bevoegdheid SMALLINT,
		adres_vraag_bevoegdheid SMALLINT,
		adhoc_medium CHAR(1),
		adres_medium CHAR(1),
		tabel_regel_start_datum INTEGER,
		tabel_regel_eind_datum INTEGER,
		sleutel_rubrieken TEXT,
		spontaan_rubrieken TEXT,
		voorwaarde_regel_spontaan VARCHAR(4096),
		selectie_rubrieken TEXT,
		voorwaarde_regel_selectie VARCHAR(4096),
		adhoc_rubrieken TEXT,
		voorwaarde_regel_adhoc VARCHAR(4096),
		adres_rubrieken TEXT,
		voorwaarde_regel_adres VARCHAR(4096),
		afnemers_verstrekkingen TEXT,
		PRIMARY KEY (autorisatie_id)
);

alter table initvul.initvullingresult_aut_regel add constraint FK_AUT_REGEL foreign key (afnemer_code) references initvul.initvullingresult_aut;

CREATE TABLE initvul.initvullingresult_afnind (
        pl_id BIGINT NOT NULL,
        a_nr BIGINT,
        conversie_resultaat VARCHAR(200),
        foutmelding TEXT,
    PRIMARY KEY (pl_id)
);

CREATE UNIQUE INDEX initvullingresult_afnind_anr ON initvul.initvullingresult_afnind (a_nr);
CREATE UNIQUE INDEX initvullingresult_afnind_resultaat_plid ON initvul.initvullingresult_afnind (conversie_resultaat, pl_id);

CREATE TABLE initvul.initvullingresult_afnind_regel (
    pl_id BIGINT NOT NULL,
    stapel_nr SMALLINT NOT NULL,
    volg_nr SMALLINT NOT NULL,
    afnemer_code BIGINT,
    geldigheid_start_datum INTEGER,
    PRIMARY KEY (pl_id, stapel_nr, volg_nr)
);

alter table initvul.initvullingresult_afnind_regel add constraint FK_AFNIND foreign key (pl_id) references initvul.initvullingresult_afnind;

CREATE TABLE initvul.initvullingresult_afnind_stapel (
    pl_id BIGINT NOT NULL,
    stapel_nr SMALLINT NOT NULL,
    conversie_resultaat VARCHAR(200),
    PRIMARY KEY (pl_id, stapel_nr)
);

alter table initvul.initvullingresult_afnind_stapel add constraint FK_AFNIND_STAPEL foreign key (pl_id) references initvul.initvullingresult_afnind;

