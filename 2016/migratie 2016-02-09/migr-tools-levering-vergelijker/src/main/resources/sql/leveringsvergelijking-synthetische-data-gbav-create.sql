-- Dit script dient alleen uitgevoerd te worden wanneer synthetische data gewenst is van GBAV
-- Dit script mag ook op een andere database uitgevoerd te worden, in andere scripts wordt hier rekening mee gehouden
-- Dit script maakt de benodigde tabellen van GBAV aan

-- GBAV activiteit tabel
DROP TABLE IF EXISTS activiteit CASCADE;
CREATE TABLE activiteit
(
	activiteit_id serial NOT NULL PRIMARY KEY,
	activiteit_type integer NOT NULL,
	activiteit_subtype integer NOT NULL,
	moeder_id bigint references activiteit(activiteit_id),
	toestand integer NOT NULL
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX activiteit_idx_0 ON activiteit USING btree (activiteit_type);
CREATE INDEX activiteit_idx_1 ON activiteit USING btree (activiteit_subtype);
CREATE INDEX activiteit_idx_2 ON activiteit USING btree (moeder_id);

-- GBAV lo3_bericht tabel
DROP TABLE IF EXISTS lo3_bericht CASCADE;
CREATE TABLE lo3_bericht
(
	lo3_bericht_id serial NOT NULL PRIMARY KEY,
	kop_berichtsoort_nummer character varying(4) NOT NULL,
	bericht_activiteit_id integer NOT NULL references activiteit(activiteit_id),
	originator_or_recipient character varying(8) NOT NULL
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX lo3_bericht_idx_0 ON lo3_bericht USING btree (kop_berichtsoort_nummer);
CREATE INDEX lo3_bericht_idx_1 ON lo3_bericht USING btree (bericht_activiteit_id);

-- GBAV proefsynchronisatiebericht tabel
DROP TABLE IF EXISTS proefsynchronisatiebericht CASCADE;
CREATE TABLE proefsynchronisatiebericht
(
	id serial NOT NULL PRIMARY KEY,
	bericht_id bigint NOT NULL references lo3_bericht(lo3_bericht_id)
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX proefsyncbericht_idx_0 ON proefsynchronisatiebericht USING btree (bericht_id);
