CREATE TABLE mig_bericht(
   id                        SERIAL,
   tijdstip                  TIMESTAMP,
   kanaal                    VARCHAR(20),
   richting                  CHAR(1),
   message_id                VARCHAR(36),
   correlation_id            VARCHAR(36),
   bericht                   TEXT          NOT NULL,
   naam                      VARCHAR(40),
   process_instance_id       INTEGER,
   virtueel_proces_id        BIGINT,
   verzendende_partij        VARCHAR(7),
   ontvangende_partij        VARCHAR(7),
   ms_sequence_number        BIGINT,
   actie                     VARCHAR(30),
   indicatie_geteld          BOOLEAN,

   CONSTRAINT ber_pk PRIMARY KEY(id)
);

CREATE TABLE mig_configuratie(
   configuratie              VARCHAR(20)   NOT NULL,
   waarde                    VARCHAR(20),

   CONSTRAINT cfg_pk PRIMARY KEY(configuratie)
);

CREATE TABLE mig_correlatie(
   message_id                VARCHAR(100)  NOT NULL,
   kanaal                    VARCHAR(8)    NOT NULL,
   verzendende_partij        VARCHAR(7),
   ontvangende_partij        VARCHAR(7),
   process_instance_id       BIGINT        NOT NULL,
   token_id                  BIGINT        NOT NULL,
   node_id                   BIGINT        NOT NULL,
   CONSTRAINT cor_pk PRIMARY KEY(message_id)
);

CREATE TABLE mig_extractie_proces(
    process_instance_id                BIGINT        NOT NULL,
    proces_naam                        VARCHAR(30)   NOT NULL,
    bericht_type                       VARCHAR(40)   NOT NULL,
    kanaal                             VARCHAR(20)   NOT NULL,
    foutreden                          VARCHAR(255),
    anummer                            VARCHAR(10),
    startdatum                         TIMESTAMP     NOT NULL,
    einddatum                          TIMESTAMP,
    wacht_startdatum                   TIMESTAMP,
    wacht_eindDatum                    TIMESTAMP,
    indicatie_gestart_geteld           BOOLEAN,
    indicatie_beeindigd_geteld         BOOLEAN,
    verwachte_verwijder_datum          TIMESTAMP,
    CONSTRAINT eps_pk PRIMARY KEY(process_instance_id)
);

CREATE TABLE mig_fout(
   id                        SERIAL,
   tijdstip                  TIMESTAMP     NOT NULL,
   proces                    VARCHAR(30)   NOT NULL,
   process_instance_id       BIGINT        NOT NULL,
   proces_init_gemeente      VARCHAR(7),
   proces_doel_gemeente      VARCHAR(7),
   code                      VARCHAR(60)   NOT NULL,
   melding                   TEXT,
   resolutie                 VARCHAR(40),

   CONSTRAINT fot_pk PRIMARY KEY(id)
);

CREATE TABLE mig_lock(
   id                        SERIAL,
   tijdstip                  TIMESTAMP     NOT NULL,
   process_instance_id       BIGINT        NOT NULL,

   CONSTRAINT lok_pk PRIMARY KEY(id)
);

CREATE TABLE mig_lock_anummer(
   id                        SERIAL,
   lock_id                   INTEGER       NOT NULL,
   anummer                   BIGINT        NOT NULL,
   tijdstip                  TIMESTAMP     NOT NULL,

   CONSTRAINT lar_pk PRIMARY KEY(id)
);

CREATE TABLE mig_proces_relatie(
   process_instance_id_een    BIGINT        NOT NULL,
   process_instance_id_twee   BIGINT        NOT NULL,

   CONSTRAINT pre_pk PRIMARY KEY(process_instance_id_een, process_instance_id_twee)
);

CREATE TABLE mig_proces_gerelateerd(
   id                        SERIAL,
   process_instance_id       BIGINT        NOT NULL,
   soort_gegeven             CHAR(3)       NOT NULL,
   gegeven                   VARCHAR(30)   NOT NULL,

   CONSTRAINT pgd_pk PRIMARY KEY(id)
);

CREATE TABLE mig_runtime (
    runtime_naam             VARCHAR(30)   NOT NULL,
    startdatum               TIMESTAMP     NOT NULL,
    client_naam              VARCHAR(60)   NOT NULL,

    CONSTRAINT rte_pk PRIMARY KEY (runtime_naam)
);

CREATE TABLE mig_telling_bericht(
    datum                    DATE          NOT NULL,
    bericht_type             VARCHAR(40)   NOT NULL,
    kanaal                   VARCHAR(20),
    aantal_ingaand           INTEGER       NOT NULL,
    aantal_uitgaand          INTEGER       NOT NULL,

    CONSTRAINT tbt_pk PRIMARY KEY(datum, bericht_type, kanaal)
);

CREATE TABLE mig_telling_proces(
    datum                              DATE          NOT NULL,
    proces_naam                        VARCHAR(30)   NOT NULL,
    bericht_type                       VARCHAR(40)   NOT NULL,
    kanaal                             VARCHAR(20)   NOT NULL,
    aantal_gestarte_processen          INTEGER       NOT NULL,
    aantal_beeindigde_processen        INTEGER       NOT NULL,

    CONSTRAINT tps_pk PRIMARY KEY(datum, proces_naam, bericht_type, kanaal)
);

CREATE TABLE mig_verzender(
   instantiecode                       INTEGER       NOT NULL,
   verzendende_instantiecode           INTEGER       NOT NULL,

   CONSTRAINT vzr_pk PRIMARY KEY(instantiecode)
);

CREATE TABLE mig_virtueel_proces(
   id                        SERIAL,
   tijdstip                  TIMESTAMP     NOT NULL,

   CONSTRAINT vps_pk PRIMARY KEY(id)
);

CREATE TABLE mig_virtueel_proces_gerelateerd(
   id                        SERIAL,
   virtueel_proces_id        INTEGER       NOT NULL,
   soort_gegeven             CHAR(3)       NOT NULL,
   gegeven                   VARCHAR(30)   NOT NULL,

   CONSTRAINT vpg_pk PRIMARY KEY(id)
);

-- FK Initially deferred, omdat proces instantie in aparte connectie (zelfde XA transactie) wordt aangemaakt en pas op commit te refereren is
ALTER TABLE mig_bericht ADD CONSTRAINT ber_pie_fk1 FOREIGN KEY(process_instance_id) REFERENCES jbpm_processinstance(id_) ON DELETE CASCADE;
-- INITIALLY DEFERRED;
ALTER TABLE mig_bericht ADD CONSTRAINT ber_pvl_fk1 FOREIGN KEY(virtueel_proces_id) REFERENCES mig_virtueel_proces(id) ON DELETE CASCADE;

CREATE INDEX ber_ix1 ON mig_bericht(process_instance_id);
CREATE INDEX ber_ix2 ON mig_bericht(virtueel_proces_id);
CREATE INDEX ber_ix3 ON mig_bericht(message_id, kanaal, richting);
CREATE INDEX ber_ix4 ON mig_bericht(ms_sequence_number);

ALTER TABLE mig_correlatie ADD CONSTRAINT cor_pie_fk1 FOREIGN KEY(process_instance_id) REFERENCES jbpm_processinstance(id_) ON DELETE CASCADE;

CREATE INDEX cor_ix1 ON mig_correlatie(process_instance_id);

ALTER TABLE mig_extractie_proces ADD CONSTRAINT eps_pie_fk1 FOREIGN KEY(process_instance_id) REFERENCES jbpm_processinstance(id_) ON DELETE CASCADE;

CREATE INDEX eps_ix1 ON mig_extractie_proces(process_instance_id);

ALTER TABLE mig_fout ADD CONSTRAINT fot_pie_fk1 FOREIGN KEY(process_instance_id) REFERENCES jbpm_processinstance(id_) ON DELETE CASCADE;

CREATE INDEX fot_ix1 ON mig_fout(process_instance_id);
CREATE INDEX fot_ix2 ON mig_fout(tijdstip);
CREATE INDEX fot_ix3 ON mig_fout(proces);
CREATE INDEX fot_ix4 ON mig_fout(code);

ALTER TABLE mig_lock ADD CONSTRAINT lok_uk1 UNIQUE(process_instance_id);
ALTER TABLE mig_lock ADD CONSTRAINT lok_pie_fk1 FOREIGN KEY(process_instance_id) REFERENCES jbpm_processinstance(id_) ON DELETE CASCADE;

CREATE INDEX lok_ix1 ON mig_lock(process_instance_id);

ALTER TABLE mig_lock_anummer ADD CONSTRAINT lar_uk1 UNIQUE(anummer);
ALTER TABLE mig_lock_anummer ADD CONSTRAINT lar_lok_fk1 FOREIGN KEY(lock_id) REFERENCES mig_lock(id) ON DELETE CASCADE;

CREATE INDEX lar_ix1 ON mig_lock_anummer(lock_id);

-- FK Initially deferred, omdat proces instantie in aparte connectie (zelfde XA transactie) wordt aangemaakt en pas op commit te refereren is
ALTER TABLE mig_proces_relatie ADD CONSTRAINT pre_pie_fk1 FOREIGN KEY(process_instance_id_een) REFERENCES jbpm_processinstance(id_) ON DELETE CASCADE;
-- INITIALLY DEFERRED;
ALTER TABLE mig_proces_relatie ADD CONSTRAINT pre_pie_fk2 FOREIGN KEY(process_instance_id_twee) REFERENCES jbpm_processinstance(id_) ON DELETE CASCADE;
-- INITIALLY DEFERRED;

CREATE INDEX pre_ix1 ON mig_proces_relatie(process_instance_id_een);
CREATE INDEX pre_ix2 ON mig_proces_relatie(process_instance_id_twee);

ALTER TABLE mig_proces_gerelateerd ADD CONSTRAINT pgd_pie_fk1 FOREIGN KEY(process_instance_id) REFERENCES jbpm_processinstance(id_) ON DELETE CASCADE;

CREATE INDEX pgd_ix1 ON mig_proces_gerelateerd(process_instance_id);
CREATE INDEX pdg_ix2 ON mig_proces_gerelateerd(soort_gegeven, gegeven);

ALTER TABLE mig_virtueel_proces_gerelateerd ADD CONSTRAINT vpg_pvl_fk1 FOREIGN KEY(virtueel_proces_id) REFERENCES mig_virtueel_proces(id) ON DELETE CASCADE;

CREATE INDEX vpg_ix1 ON mig_virtueel_proces_gerelateerd(virtueel_proces_id);

