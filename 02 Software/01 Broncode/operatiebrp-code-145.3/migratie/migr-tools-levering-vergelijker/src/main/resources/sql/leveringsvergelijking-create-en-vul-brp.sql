-- onderstaande de queries die uitgevoerd dienen te worden op de ISC database (SOA)
BEGIN;

-- tabel waarin alle berichtcorrelaties van BRP opgenomen dienen te worden
DROP TABLE IF EXISTS mig_leveringsvergelijking_berichtcorrelatie_brp CASCADE;
CREATE TABLE mig_leveringsvergelijking_berichtcorrelatie_brp
(
	id serial NOT NULL,
	bijhouding_ber_eref bigint NOT NULL,
	bijhouding_ber_id bigint NOT NULL,
	afnemer_code character varying(7),
	levering_ber_id bigint,
	berichtnummer character varying(4),
	CONSTRAINT levberbrp_pk PRIMARY KEY (id)
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX levberbrp_idx_0 ON mig_leveringsvergelijking_berichtcorrelatie_brp USING btree (bijhouding_ber_id);
CREATE INDEX levberbrp_idx_1 ON mig_leveringsvergelijking_berichtcorrelatie_brp USING btree (afnemer_code);
CREATE INDEX levberbrp_idx_2 ON mig_leveringsvergelijking_berichtcorrelatie_brp USING btree (levering_ber_id);
CREATE INDEX levberbrp_idx_3 ON mig_leveringsvergelijking_berichtcorrelatie_brp USING btree (bijhouding_ber_eref);

-- tabel waarin de resultaten van de leveringsvergelijking van de inhoud opgeslagen dient te worden
DROP TABLE IF EXISTS mig_leveringsvergelijking_resultaat_inhoud CASCADE;
CREATE TABLE mig_leveringsvergelijking_resultaat_inhoud
(
	id serial NOT NULL,
	bijhouding_eref bigint NOT NULL,
	bijhouding_ber_id_gbav bigint NOT NULL,
	bijhouding_ber_id_brp bigint NOT NULL,
	afnemer_code character varying(7) NOT NULL,
	berichtnummer character varying(4) NOT NULL,
	levering_ber_id_gbav bigint NOT NULL,
	levering_ber_id_brp bigint NOT NULL,
	categorie integer NOT NULL,
	stapel integer NOT NULL,
	voorkomen integer NOT NULL,
	rubriek character varying(8) NOT NULL,
	CONSTRAINT levberresinh_pk PRIMARY KEY (id)
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX levberresinh_idx_0 ON mig_leveringsvergelijking_resultaat_inhoud USING btree (bijhouding_eref);
CREATE INDEX levberresinh_idx_1 ON mig_leveringsvergelijking_resultaat_inhoud USING btree (bijhouding_ber_id_gbav);
CREATE INDEX levberresinh_idx_2 ON mig_leveringsvergelijking_resultaat_inhoud USING btree (bijhouding_ber_id_brp);
CREATE INDEX levberresinh_idx_3 ON mig_leveringsvergelijking_resultaat_inhoud USING btree (afnemer_code);
CREATE INDEX levberresinh_idx_4 ON mig_leveringsvergelijking_resultaat_inhoud USING btree (levering_ber_id_gbav);
CREATE INDEX levberresinh_idx_5 ON mig_leveringsvergelijking_resultaat_inhoud USING btree (levering_ber_id_brp);
CREATE INDEX levberresinh_idx_6 ON mig_leveringsvergelijking_resultaat_inhoud USING btree (rubriek);

-- tabel waarin de resultaten van de leveringsvergelijking van de kop opgeslagen dient te worden
DROP TABLE IF EXISTS mig_leveringsvergelijking_resultaat_kop CASCADE;
CREATE TABLE mig_leveringsvergelijking_resultaat_kop
(
	id serial NOT NULL,
	bijhouding_eref bigint NOT NULL,
	bijhouding_ber_id_gbav bigint NOT NULL,
	bijhouding_ber_id_brp bigint NOT NULL,
	afnemer_code character varying(7) NOT NULL,
	berichtnummer character varying(4) NOT NULL,
	levering_ber_id_gbav bigint NOT NULL,
	levering_ber_id_brp bigint NOT NULL,
	afwijkingen character varying(16) NOT NULL,
	CONSTRAINT levberreskop_pk PRIMARY KEY (id)
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX levberreskop_idx_0 ON mig_leveringsvergelijking_resultaat_kop USING btree (bijhouding_eref);
CREATE INDEX levberreskop_idx_1 ON mig_leveringsvergelijking_resultaat_kop USING btree (bijhouding_ber_id_gbav);
CREATE INDEX levberreskop_idx_2 ON mig_leveringsvergelijking_resultaat_kop USING btree (bijhouding_ber_id_brp);
CREATE INDEX levberreskop_idx_3 ON mig_leveringsvergelijking_resultaat_kop USING btree (afnemer_code);
CREATE INDEX levberreskop_idx_4 ON mig_leveringsvergelijking_resultaat_kop USING btree (levering_ber_id_gbav);
CREATE INDEX levberreskop_idx_5 ON mig_leveringsvergelijking_resultaat_kop USING btree (levering_ber_id_brp);
CREATE INDEX levberreskop_idx_6 ON mig_leveringsvergelijking_resultaat_kop USING btree (afwijkingen);

-- leg de berichtcorrelatie aan van alle berichten van BRP
DELETE FROM mig_leveringsvergelijking_berichtcorrelatie_brp;
INSERT INTO mig_leveringsvergelijking_berichtcorrelatie_brp (bijhouding_ber_eref, bijhouding_ber_id, afnemer_code, levering_ber_id, berichtnummer)
SELECT cast(ber1.message_id as bigint), ber1.id, ber2.ontvangende_partij, ber2.id, SUBSTRING(ber2.bericht, 9, 4) FROM
	mig_bericht AS ber1
	INNER JOIN mig_proces_gerelateerd AS proces1 ON
	ber1.naam IN ('Lg01', 'La01') AND ber1.richting = 'I' AND ber1.kanaal = 'VOISC' AND
	ber1.process_instance_id = proces1.process_instance_id AND
	proces1.soort_gegeven = 'ADH'
	INNER JOIN mig_virtueel_proces_gerelateerd AS proces2 ON
	proces1.gegeven = proces2.gegeven
	LEFT JOIN mig_bericht AS ber2 ON
	proces2.virtueel_proces_id = ber2.virtueel_proces_id AND ber2.kanaal = 'VOISC' AND ber2.richting = 'U'
	;

UPDATE mig_leveringsvergelijking_berichtcorrelatie_brp SET afnemer_code = afnemer_code || '0' WHERE LENGTH(afnemer_code) = 6; -- 0 toevoegen om gelijk te trekken met GBAV

COMMIT;
