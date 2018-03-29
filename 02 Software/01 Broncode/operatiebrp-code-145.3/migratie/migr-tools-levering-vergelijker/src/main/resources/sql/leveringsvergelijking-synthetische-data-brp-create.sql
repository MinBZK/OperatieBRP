-- Dit scripts maakt de benodigde BRP tabellen aan die gevuld dienen te worden met synthetische data

-- De mig_proces_gerelateerd tabel
DROP TABLE IF EXISTS mig_proces_gerelateerd CASCADE;
CREATE TABLE mig_proces_gerelateerd
(
	process_instance_id serial NOT NULL PRIMARY KEY,
	soort_gegeven character varying(3) NOT NULL,
	gegeven serial NOT NULL
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX mig_proces_gerelateerd_idx_0 ON mig_proces_gerelateerd USING btree (soort_gegeven);
CREATE INDEX mig_proces_gerelateerd_idx_1 ON mig_proces_gerelateerd USING btree (gegeven);

-- De mig_virtueel_proces_gerelateerd tabel
DROP TABLE IF EXISTS mig_virtueel_proces_gerelateerd CASCADE;
CREATE TABLE mig_virtueel_proces_gerelateerd
(
	virtueel_proces_id serial NOT NULL PRIMARY KEY,
	gegeven bigint NOT NULL
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX mig_virtueel_proces_gerelateerd_idx_0 ON mig_virtueel_proces_gerelateerd USING btree (gegeven);

-- De mig_bericht tabel
DROP TABLE IF EXISTS mig_bericht CASCADE;
CREATE TABLE mig_bericht
(
	id serial NOT NULL PRIMARY KEY,
	message_id character varying(36) NOT NULL,
	ontvangende_partij character varying(8) NOT NULL,
	naam character varying(8) NOT NULL,
	richting character varying(1) NOT NULL,
	kanaal character varying(5) NOT NULL,
	bericht character varying(12) NOT NULL,
	process_instance_id bigint references mig_proces_gerelateerd(process_instance_id),
	virtueel_proces_id bigint references mig_virtueel_proces_gerelateerd(virtueel_proces_id)
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX mig_bericht_idx_0 ON mig_bericht USING btree (ontvangende_partij);
CREATE INDEX mig_bericht_idx_1 ON mig_bericht USING btree (naam);
CREATE INDEX mig_bericht_idx_2 ON mig_bericht USING btree (richting);
CREATE INDEX mig_bericht_idx_3 ON mig_bericht USING btree (kanaal);
CREATE INDEX mig_bericht_idx_4 ON mig_bericht USING btree (process_instance_id);
CREATE INDEX mig_bericht_idx_5 ON mig_bericht USING btree (virtueel_proces_id);

-- tabel waarin alle berichtcorrelaties van GBAV opgenomen dienen te worden
DROP TABLE IF EXISTS mig_leveringsvergelijking_berichtcorrelatie_gbav CASCADE;
CREATE TABLE mig_leveringsvergelijking_berichtcorrelatie_gbav
(
	id serial NOT NULL,
	bijhouding_ber_id bigint NOT NULL,
	afnemer_code character varying(7),
	levering_ber_id bigint,
	berichtnummer character varying(4),
	CONSTRAINT levbergbav_pk PRIMARY KEY (id)
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX levbergbav_idx_0 ON mig_leveringsvergelijking_berichtcorrelatie_gbav USING btree (bijhouding_ber_id);
CREATE INDEX levbergbav_idx_1 ON mig_leveringsvergelijking_berichtcorrelatie_gbav USING btree (afnemer_code);
CREATE INDEX levbergbav_idx_2 ON mig_leveringsvergelijking_berichtcorrelatie_gbav USING btree (levering_ber_id);
