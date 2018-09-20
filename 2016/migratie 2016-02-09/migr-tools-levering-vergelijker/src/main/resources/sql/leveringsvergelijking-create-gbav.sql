-- onderstaand de queries die uitgevoerd dienen te worden op de GBA database (GBAV)
BEGIN;

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

COMMIT;
 
