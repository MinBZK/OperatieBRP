-- tabel om een mapping aan de leggen van de verschillende berichtcombinatie aan afnemers op gbav en brp
DROP TABLE IF EXISTS mig_leveringsvergelijking_berichtcombinaties_gbav_brp;
CREATE TABLE mig_leveringsvergelijking_berichtcombinaties_gbav_brp
(
    id serial NOT NULL PRIMARY KEY,
    gbav_berichtnummers character varying(64) NOT NULL, -- string met daarin gesorteerd de berichtnummers
    brp_berichtnummers character varying(64) NOT NULL, -- string met daarin gesorteerd de berichtnummers
    aantal bigint NOT NULL -- aantal keren dat deze combinatie voorkomt
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX berichtcombinaties_gbav_brp_idx_0 ON mig_leveringsvergelijking_berichtcombinaties_gbav_brp USING btree (gbav_berichtnummers COLLATE pg_catalog."default", brp_berichtnummers COLLATE pg_catalog."default");

DROP TABLE IF EXISTS mig_leveringsvergelijking_berichtcombinaties_gbav_brp_afnemer;
CREATE TABLE mig_leveringsvergelijking_berichtcombinaties_gbav_brp_afnemer
(
    id serial NOT NULL PRIMARY KEY,
    gbav_berichtnummers character varying(64) NOT NULL, -- string met daarin gesorteerd de berichtnummers
    brp_berichtnummers character varying(64) NOT NULL, -- string met daarin gesorteerd de berichtnummers
    afnemer_code character(7),
    bijhouding_ber_id bigint
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX berichtcombinaties_gbav_brp_afnemer_idx_0 ON mig_leveringsvergelijking_berichtcombinaties_gbav_brp_afnemer USING btree (gbav_berichtnummers COLLATE pg_catalog."default", brp_berichtnummers COLLATE pg_catalog."default");


-- vul mig_leveringsvergelijking_correlatie_gbav_brp met de verschillende berichtcombinatie waarbij de afnemer hetzelfde is
DROP FUNCTION IF EXISTS create_berichtcombinaties_gbav_brp();
CREATE FUNCTION create_berichtcombinaties_gbav_brp() RETURNS void AS $$
DECLARE
    correlatie RECORD;
    correlatie_afnemer RECORD;
    gbav_berichtnummers_val character varying(64);
    brp_berichtnummers_val character varying(64);
    update_query varchar;
    rowcount bigint;
    x bigint;
BEGIN
    DELETE FROM mig_leveringsvergelijking_berichtcombinaties_gbav_brp;
   
    x = 0;

    RAISE NOTICE 'Bezig met aanleggen berichtcombinaties GBAV en BRP.';

    FOR correlatie IN
        SELECT bijhouding_ber_id, afnemer_code, bijhouding_ber_id
            FROM mig_leveringsvergelijking_berichtcorrelatie_gbav GROUP BY bijhouding_ber_id, afnemer_code
    LOOP
        gbav_berichtnummers_val := '';
        brp_berichtnummers_val := '';

        SELECT ARRAY_TO_STRING(ARRAY(SELECT berichtnummer FROM mig_leveringsvergelijking_berichtcorrelatie_gbav WHERE bijhouding_ber_id = correlatie.bijhouding_ber_id AND afnemer_code = correlatie.afnemer_code ORDER BY berichtnummer), ',') INTO gbav_berichtnummers_val;

        SELECT ARRAY_TO_STRING(ARRAY(SELECT berichtnummer FROM mig_leveringsvergelijking_berichtcorrelatie_brp WHERE bijhouding_ber_eref = correlatie.bijhouding_ber_id AND afnemer_code = correlatie.afnemer_code ORDER BY berichtnummer), ',') INTO brp_berichtnummers_val;

        INSERT INTO mig_leveringsvergelijking_berichtcombinaties_gbav_brp_afnemer (gbav_berichtnummers, brp_berichtnummers, afnemer_code, bijhouding_ber_id) VALUES (gbav_berichtnummers_val, brp_berichtnummers_val, correlatie.afnemer_code, correlatie.bijhouding_ber_id);

        IF (x % 1000) = 0 THEN
            RAISE NOTICE '% : Berichten: %', timeofday(), x;
        END IF;
        x = x + 1;
    END LOOP;
   
    RAISE NOTICE 'Bezig met aanleggen berichtcombinaties BRP en GBAV.';
    x = 0;

    FOR correlatie IN
        SELECT bijhouding_ber_eref, afnemer_code, bijhouding_ber_eref AS bijhouding_ber_id
            FROM mig_leveringsvergelijking_berichtcorrelatie_brp AS brp
            WHERE NOT EXISTS (SELECT 1 FROM mig_leveringsvergelijking_berichtcorrelatie_gbav AS gbav WHERE brp.bijhouding_ber_eref = gbav.bijhouding_ber_id AND brp.afnemer_code = gbav.afnemer_code)
            GROUP BY bijhouding_ber_eref, afnemer_code
    LOOP
        gbav_berichtnummers_val := '';
        brp_berichtnummers_val := '';

        SELECT ARRAY_TO_STRING(ARRAY(SELECT berichtnummer FROM mig_leveringsvergelijking_berichtcorrelatie_brp WHERE bijhouding_ber_eref = correlatie.bijhouding_ber_id AND afnemer_code = correlatie.afnemer_code ORDER BY berichtnummer), ',') INTO brp_berichtnummers_val;

        INSERT INTO mig_leveringsvergelijking_berichtcombinaties_gbav_brp_afnemer (gbav_berichtnummers, brp_berichtnummers, afnemer_code, bijhouding_ber_id) VALUES (gbav_berichtnummers_val, brp_berichtnummers_val, correlatie.afnemer_code, correlatie.bijhouding_ber_id);

        IF (x % 1000) = 0 THEN
            RAISE NOTICE '% : Berichten: %', timeofday(), x;
        END IF;
        x = x + 1;
    END LOOP;

    RAISE NOTICE 'Bezig met consolideren van berichtcombinaties.';
    DELETE FROM mig_leveringsvergelijking_berichtcombinaties_gbav_brp;
    FOR correlatie IN
        SELECT gbav_berichtnummers, brp_berichtnummers, count(1) AS aantal FROM mig_leveringsvergelijking_berichtcombinaties_gbav_brp_afnemer GROUP BY 1, 2 ORDER BY 3 DESC
    LOOP
        INSERT INTO mig_leveringsvergelijking_berichtcombinaties_gbav_brp (gbav_berichtnummers, brp_berichtnummers, aantal) VALUES (correlatie.gbav_berichtnummers, correlatie.brp_berichtnummers, correlatie.aantal);
    END LOOP;

    RAISE NOTICE 'Klaar met aanleggen berichtcombinaties.';
    RETURN;
END;
$$ LANGUAGE plpgsql;

SELECT  create_berichtcombinaties_gbav_brp();
-- overzicht maken van de verschillende soorten berichtcombinaties