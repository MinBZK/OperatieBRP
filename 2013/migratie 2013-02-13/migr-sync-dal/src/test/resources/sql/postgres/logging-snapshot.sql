
-- Schemas ---------------------------------------------------------------------
drop schema IF EXISTS logging CASCADE;
CREATE SCHEMA logging;


-- Hieronder worden alle tabellen aangemaakt:
CREATE SEQUENCE logging.seq_berichtlog;
CREATE TABLE logging.berichtlog
(
  id bigint NOT NULL DEFAULT nextval('logging.seq_berichtlog'),
  referentie character varying(36) NOT NULL,
  bron character varying(36) NOT NULL,
  tijdstip_verwerking timestamp NOT NULL,
  a_nummer BigInt,
  pers_id integer,
  bericht_data text NOT NULL,
  bericht_hash character varying(64),
  foutcode character varying(200),
  foutmelding text,
  gemeente_van_inschrijving smallint,
  CONSTRAINT berichtlog_pkey PRIMARY KEY (id)
);
ALTER SEQUENCE logging.seq_berichtlog OWNED BY logging.berichtlog.id;

ALTER TABLE logging.berichtlog ADD CONSTRAINT logbericht_pers_fkey FOREIGN KEY (pers_id) REFERENCES Kern.Pers (id);


CREATE SEQUENCE logging.seq_logregel;
CREATE TABLE logging.logregel
(
  id bigint NOT NULL DEFAULT nextval('logging.seq_logregel'),
  berichtlog_id integer NOT NULL,
  lo3_categorie smallint NOT NULL,
  lo3_stapel smallint NOT NULL,
  lo3_voorkomen smallint NOT NULL,
  log_severity smallint NOT NULL,
  log_type character varying(20) NOT NULL,
  log_code character varying(20),
  log_omschrijving character varying(400),
  CONSTRAINT logregel_pkey PRIMARY KEY (id)
);
ALTER SEQUENCE logging.seq_logregel OWNED BY logging.logregel.id;

ALTER TABLE logging.logregel ADD CONSTRAINT logregel_berichtlog_fkey FOREIGN KEY (berichtlog_id) REFERENCES logging.berichtlog (id);

CREATE SEQUENCE logging.seq_lo3herkomst;
CREATE TABLE logging.lo3herkomst
(
  id bigint NOT NULL DEFAULT nextval('logging.seq_lo3herkomst'),
  brp_actie_id bigint NOT NULL,
  lo3_categorie smallint NOT NULL,
  lo3_stapel smallint NOT NULL,
  lo3_voorkomen smallint NOT NULL,
  CONSTRAINT lo3herkomst_pk PRIMARY KEY (brp_actie_id )
);
ALTER SEQUENCE logging.seq_lo3herkomst OWNED BY logging.lo3herkomst.id;

ALTER TABLE logging.lo3herkomst ADD CONSTRAINT lo3herkomst_brp_actie_fkey FOREIGN KEY (brp_actie_id) REFERENCES kern.actie (id);
ALTER TABLE logging.lo3herkomst ADD CONSTRAINT unique_brp_actie_id UNIQUE (brp_actie_id);
