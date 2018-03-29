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


-- leg de berichtcorrelatie aan van alle berichten van gbav
-- LET OP: Deze query gaat er van uit dat de EREF waarmee het bericht wordt verstuurd door de proefsynchronisatie gelijk is
-- aan het BERICHT_ID.
DELETE FROM mig_leveringsvergelijking_berichtcorrelatie_gbav;
INSERT INTO mig_leveringsvergelijking_berichtcorrelatie_gbav (bijhouding_ber_id, afnemer_code, levering_ber_id, berichtnummer)
SELECT ber1.lo3_bericht_id as bijhouding_ber_id, ber2.originator_or_recipient as afnemer_code, ber2.lo3_bericht_id as levering_ber_id, ber2.kop_berichtsoort_nummer as berichtnummer
	FROM proefsynchronisatiebericht AS proef
	INNER JOIN lo3_bericht AS ber1 ON
		proef.bericht_id = ber1.lo3_bericht_id
	INNER JOIN activiteit AS act1 ON -- Lg01/La01 bericht activiteit (100)
		ber1.bericht_activiteit_id = act1.activiteit_id AND
		act1.activiteit_type = 100 AND act1.activiteit_subtype IN (1111, 1112) AND act1.toestand >= 8000 and act1.toestand < 9000
	LEFT JOIN activiteit AS act2 ON -- Spontaan activiteit, heeft zelfde moeder activiteit als Lg/La berichtactiviteit
		act1.moeder_id = act2.moeder_id AND
		act2.activiteit_type = 107 AND act2.activiteit_subtype = 1221 AND act2.toestand >= 8000 and act2.toestand < 9000
	LEFT JOIN activiteit AS act3 ON -- Verstrekkingsbericht activiteit (101)
		act3.moeder_id = act2.activiteit_id AND
		act3.activiteit_type = 101 AND act3.toestand >= 8000 and act3.toestand < 9000
	LEFT JOIN lo3_bericht AS ber2 ON -- alle uitgaande berichten die bij de gevonden activiteiten horen
		act3.activiteit_id = ber2.bericht_activiteit_id AND
		ber2.kop_berichtsoort_nummer IN ('Ag11', 'Ag21', 'Ag31', 'Ng01', 'Wa11', 'Gv01', 'Gv02')
	;
COMMIT; 
