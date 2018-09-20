-- Bij het gebruik van de in-memory DB wordt het schema gegenereerd vanuit de JPA-metadata.
-- Hierbij is het helaas (nog) niet mogelijk ook automatisch het schema te laten aanmaken, vandaar dat we dat via dit script regelen.
CREATE SCHEMA LOGGING;
-- Om de compatibiliteit met PostgreSQL te vergroten zetten we de volgende HSQLDB-configuratie-opties aan:
-- (zie http://hsqldb.org/doc/2.0/guide/guide.pdf)
SET DATABASE TRANSACTION CONTROL MVCC;
SET DATABASE SQL SYNTAX PGS TRUE;
SET DATABASE REFERENTIAL INTEGRITY TRUE;



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

ALTER TABLE logging.lo3herkomst ADD CONSTRAINT lo3herkomst_brp_actie_fkey FOREIGN KEY (brp_actie_id) REFERENCES kern.actie (id);
