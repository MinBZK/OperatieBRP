-- Om de verschillende rapportages te kunnen testen is er synthetische data nodig
-- In dit script zijn de benodigde queries te vinden die dit mogelijk maken

-- GBAV tabellen aanmaken
BEGIN;

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

DROP TABLE IF EXISTS proefsynchronisatiebericht CASCADE;
CREATE TABLE proefsynchronisatiebericht
(
	id serial NOT NULL PRIMARY KEY,
	bericht_id bigint NOT NULL references lo3_bericht(lo3_bericht_id)
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX proefsyncbericht_idx_0 ON proefsynchronisatiebericht USING btree (bericht_id);

COMMIT;

-- BRP tabellen aanmaken

BEGIN;

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

DROP TABLE IF EXISTS mig_virtueel_proces_gerelateerd CASCADE;
CREATE TABLE mig_virtueel_proces_gerelateerd
(
	virtueel_proces_id serial NOT NULL PRIMARY KEY,
	gegeven bigint NOT NULL
)
WITH (OIDS=FALSE, autovacuum_enabled=true, autovacuum_vacuum_scale_factor=0.03);
CREATE INDEX mig_virtueel_proces_gerelateerd_idx_0 ON mig_virtueel_proces_gerelateerd USING btree (gegeven);

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

COMMIT;

-- GBAV tabellen vullen

-- onderstaande functie legt de berichtcorrelatie aan van de gbav berichten
DROP FUNCTION IF EXISTS create_berichtcorrelatie_gbav();
CREATE FUNCTION create_berichtcorrelatie_gbav() RETURNS void AS $$
DECLARE
BEGIN
	RAISE NOTICE 'Bezig met aanleggen berichtcorrelatie GBAV.';
	DELETE FROM mig_leveringsvergelijking_berichtcorrelatie_gbav;
	INSERT INTO mig_leveringsvergelijking_berichtcorrelatie_gbav (bijhouding_ber_id, afnemer_code, levering_ber_id, berichtnummer)
	SELECT ber1.lo3_bericht_id as bijhouding_ber_id, ber2.originator_or_recipient as afnemer_code, ber2.lo3_bericht_id as levering_ber_id, ber2.kop_berichtsoort_nummer as berichtnummer
		FROM proefsynchronisatiebericht AS proef
		INNER JOIN lo3_bericht AS ber1 ON
			proef.bericht_id = ber1.lo3_bericht_id
		INNER JOIN activiteit AS act1 ON -- Lg01/La01 bericht activiteit (100)
			ber1.bericht_activiteit_id = act1.activiteit_id AND
			act1.activiteit_type = 100 AND act1.activiteit_subtype IN (1111, 1112) AND act1.toestand >= 8000 and act1.toestand < 9000
		LEFT JOIN activiteit AS act2 ON -- LO3 cyclus
			act1.moeder_id = act2.moeder_id AND
			act2.activiteit_type = 107 AND act2.activiteit_subtype = 1220 AND act2.toestand >= 8000 and act2.toestand < 9000
		LEFT JOIN activiteit AS act3 ON -- Verstrekkingsbericht activiteit (101)
			act3.moeder_id = act2.activiteit_id AND
			act3.activiteit_type = 101 AND act3.toestand >= 8000 and act3.toestand < 9000
		LEFT JOIN lo3_bericht AS ber2 ON -- alle uitgaande berichten die bij de gevonden activiteiten horen
			act3.activiteit_id = ber2.bericht_activiteit_id AND
			ber2.kop_berichtsoort_nummer IN ('Ag11', 'Ag21', 'Ag31', 'Ng01', 'Wa11', 'Gv01', 'Gv02')
		; -- LIMIT 1000; -- Deze limit moet weg als de echte telling plaats vindt
	RAISE NOTICE 'Klaar met aanleggen berichtcorrelatie GBAV.';
	RETURN;
END;
$$ LANGUAGE plpgsql;

-- onderstaande functie legt de berichtcorrelatie aan van de brp berichten
DROP FUNCTION IF EXISTS create_berichtcorrelatie_brp();
CREATE FUNCTION create_berichtcorrelatie_brp() RETURNS void AS $$
DECLARE
BEGIN
	RAISE NOTICE 'Bezig met aanleggen berichtcorrelatie BRP.';
	DELETE FROM mig_leveringsvergelijking_berichtcorrelatie_brp;
	INSERT INTO mig_leveringsvergelijking_berichtcorrelatie_brp (bijhouding_ber_id, afnemer_code, levering_ber_id, berichtnummer)
	SELECT CAST(ber1.message_id AS bigint), ber2.ontvangende_partij, ber2.id, SUBSTRING(ber2.bericht, 9, 4) FROM
		mig_bericht AS ber1
		INNER JOIN mig_proces_gerelateerd AS proces1 ON
			ber1.naam IN ('Lg01', 'La01') AND ber1.richting = 'I' AND ber1.kanaal = 'VOSPG' AND
			ber1.process_instance_id = proces1.process_instance_id AND
			proces1.soort_gegeven = 'ADH'
		LEFT JOIN mig_virtueel_proces_gerelateerd AS proces2 ON
			proces1.gegeven = proces2.gegeven
		LEFT JOIN mig_bericht AS ber2 ON
			proces2.virtueel_proces_id = ber2.virtueel_proces_id AND ber2.naam = 'Levering' AND ber2.richting = 'U';
	RAISE NOTICE 'Klaar met aanleggen berichtcorrelatie BRP.';
	RETURN;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS get_random_bericht();
CREATE FUNCTION get_random_bericht() RETURNS character varying(4) AS $$
DECLARE
	berichtnummer integer := 0;
	berichtnummer_val character varying(4);
BEGIN
	berichtnummer := ROUND(RANDOM() * 4);
	CASE
		WHEN berichtnummer = 1 THEN berichtnummer_val := 'Ng01';
		WHEN berichtnummer = 2 THEN berichtnummer_val := 'Wa11';
		WHEN berichtnummer = 3 THEN berichtnummer_val := 'Gv02';
		ELSE berichtnummer_val := 'Ag31';
	END CASE;
	return berichtnummer_val;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS get_random_afnemer();
CREATE FUNCTION get_random_afnemer() RETURNS integer AS $$
DECLARE
BEGIN
	RETURN (ROUND(RANDOM() * 3) + 1) * 10000;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS get_random_bericht_id();
CREATE FUNCTION get_random_bericht_id() RETURNS character varying(36) AS $$
DECLARE
BEGIN
	RETURN MD5(RANDOM() || ' ' || RANDOM());
END;
$$ LANGUAGE plpgsql;



DROP FUNCTION IF EXISTS vul_brp(bigint);
CREATE FUNCTION vul_brp(bigint) RETURNS void AS $$
DECLARE
	bericht_id bigint := $1;
	process_instance_id_var bigint := 0;
	virtueel_proces_id_var bigint := 0;
	gegeven_var bigint := 0;
	max_lev integer := 0;
	count_lev integer := 0;
	afnemer_var integer := 0;
	max_ber integer := 0;
	count_ber integer := 0;
	berichtnummer integer := 0;
	berichtnummer_var character varying(4);
	last_berichtnummer_var character varying(4) := '';

BEGIN
	-- in sommige gevallen geen bijhoudingsbericht maken
	IF ROUND(RANDOM() * 30) = 1 THEN
		RETURN;
	END IF;

	-- proces van het bijhoudingsbericht maken
	INSERT INTO mig_proces_gerelateerd (soort_gegeven) VALUES ('ADH');
	process_instance_id_var := LASTVAL();
	gegeven_var := CURRVAL('mig_proces_gerelateerd_gegeven_seq');

	-- bijhoudingsbericht zelf maken
	INSERT INTO mig_bericht (message_id, ontvangende_partij, naam, richting, kanaal, process_instance_id, bericht) VALUES ('' || bericht_id, '06666', 'Lg01', 'I', 'VOSPG', process_instance_id_var, '00000000Lg01');

	-- in sommige gevallen geen levering maken
	IF ROUND(RANDOM() * 10) <> 4 THEN
		max_lev := 4;
		count_lev := 0;
		LOOP
			-- random gemeente
			afnemer_var := get_random_afnemer();

			IF NOT EXISTS (SELECT 1 FROM mig_virtueel_proces_gerelateerd, mig_bericht WHERE mig_virtueel_proces_gerelateerd.gegeven = gegeven_var AND mig_virtueel_proces_gerelateerd.virtueel_proces_id = mig_bericht.virtueel_proces_id AND ontvangende_partij = '' || afnemer_var) THEN
				count_ber := 0;
				max_ber := 2;

				-- stuur één of twee berichten naar de afnemer
				IF ROUND(RANDOM()) = 0 THEN
					max_ber := 1;
				END IF;

				last_berichtnummer_var = '';
				LOOP
					INSERT INTO mig_virtueel_proces_gerelateerd (gegeven) VALUES (gegeven_var);

					virtueel_proces_id_var := LASTVAL();

					LOOP
						berichtnummer_var := get_random_bericht();
						IF berichtnummer_var <> last_berichtnummer_var THEN
							last_berichtnummer_var := berichtnummer_var;
							EXIT;
						END IF;
					END LOOP;


					INSERT INTO mig_bericht (message_id, ontvangende_partij, naam, richting, kanaal, virtueel_proces_id, bericht) VALUES (get_random_bericht_id(), afnemer_var, 'Levering', 'U', 'VOSPG', virtueel_proces_id_var, '00000000' || berichtnummer_var);

					count_ber = count_ber + 1;
					IF count_ber >= max_ber THEN
						EXIT;
					END IF;
				END LOOP;
			END IF;
			count_lev = count_lev + 1;
			IF count_lev >= max_lev THEN
				EXIT;
			END IF;
		END LOOP;
	END IF;
	RETURN;
END;
$$ LANGUAGE plpgsql;


DROP FUNCTION IF EXISTS vul_gbav_en_brp(integer);
CREATE FUNCTION vul_gbav_en_brp(integer) RETURNS BOOLEAN AS $$
DECLARE
	count integer := 0;
	max integer := $1;
	max_lev integer := 0;
	count_lev integer := 0;
	max_ber integer := 0;
	afnemer_var integer := 0;
	berichtnummer integer := 0;
	berichtnummer_var character varying(4) := '';
	last_berichtnummer_var character varying(4) := '';
	count_ber integer := 0;
	moeder_id_var bigint := 0;
	bericht_id_var bigint;
	activiteit_id_var bigint := 0;
	vul_brp_result boolean := false;
BEGIN
	RAISE NOTICE 'Bezig met vullen van GBAV en BRP tabellen.';
	LOOP
		-- activiteit1 maken (type 100, sub 1111, toest 8000) moeder activiteit2
		INSERT INTO activiteit (activiteit_type, activiteit_subtype, toestand) VALUES (100, 1111, 8000); -- lg01 berichtcyclus
		activiteit_id_var := LASTVAL();

		-- Bericht maken (lo3_bericht) met activiteit1
		INSERT INTO lo3_bericht (kop_berichtsoort_nummer, bericht_activiteit_id, originator_or_recipient) VALUES ('Lg01', activiteit_id_var, '06666');  -- lg01 bericht
		bericht_id_var :=  LASTVAL();

		PERFORM vul_brp(bericht_id_var);

		-- proefsynchronisatiebericht maken (proefsynchronisatiebericht) koppeling naar bericht
		INSERT INTO proefsynchronisatiebericht (bericht_id) VALUES (bericht_id_var); -- proefsynchronisatiebericht

		-- activiteit3 maken (type 107, sub 1220, toest 8000) moeder activiteit2
		INSERT INTO activiteit (activiteit_type, activiteit_subtype, toestand, moeder_id) VALUES (107, 1220, 8000, activiteit_id_var); -- spontaan activiteit
		moeder_id_var := LASTVAL();

		-- maak geen of meerdere verstrekking activiteiten
		IF ROUND(RANDOM() * 10) <> 4 THEN
			max_lev := 4;
			count_lev := 0;
			LOOP
				-- random gemeente
				afnemer_var := get_random_afnemer();

				IF NOT EXISTS (SELECT 1 FROM lo3_bericht, activiteit WHERE activiteit.moeder_id = moeder_id_var AND activiteit.activiteit_id = lo3_bericht.bericht_activiteit_id AND lo3_bericht.originator_or_recipient = '' || afnemer_var) THEN
					-- stuur één of twee berichten naar de afnemer
					count_ber := 0;
					max_ber := 2;

					IF ROUND(RANDOM()) = 0 THEN
						max_ber := 1;
					END IF;

					last_berichtnummer_var = '';
					LOOP
						-- activiteit4 maken (type 101, toest 8000) en moeder activiteit3
						INSERT INTO activiteit (activiteit_type, activiteit_subtype, toestand, moeder_id) VALUES (101, 999, 8000, moeder_id_var); -- verstrekking activiteit
						activiteit_id_var := LASTVAL();

						LOOP
							berichtnummer_var := get_random_bericht();
							IF berichtnummer_var <> last_berichtnummer_var THEN
								last_berichtnummer_var := berichtnummer_var;
								EXIT;
							END IF;
						END LOOP;

						INSERT INTO lo3_bericht (kop_berichtsoort_nummer, bericht_activiteit_id, originator_or_recipient) VALUES (berichtnummer_var, activiteit_id_var, afnemer_var);

						count_ber = count_ber + 1;
						IF count_ber >= max_ber THEN
							EXIT;
						END IF;
					END LOOP;
				END IF;

				count_lev = count_lev + 1;
				IF count_lev >= max_lev THEN
					EXIT;
				END IF;
			END LOOP;
		END IF;
		count := count + 1;
		IF count >= max THEN
			EXIT;
		END IF;
	END LOOP;

	RAISE NOTICE 'Klaar met vullen van de GBAV en BRP tabellen.';

	RETURN true;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS create_random_kop_resultaat(bigint, character varying, bigint, bigint, character varying);
CREATE FUNCTION create_random_kop_resultaat(bigint, character varying, bigint, bigint, character varying) RETURNS void AS $$
DECLARE
	bijhouding_ber_id bigint = $1;
	afnemer_code bigint = $2;
	levering_ber_id_gbav bigint = $3;
	levering_ber_id_brp bigint = $4;
	berichtnummer character varying(4) = $5;
	max integer = 3;
	count integer = 0;
	afwijkingen character varying(20) = '';
BEGIN
	-- bepaal of in dit geval een resultaat vastgelegd dient te worden
	IF ROUND(RANDOM() * 5) > 3 THEN
		RETURN;
	END IF;

	-- genereer de afwijkingen string
	LOOP
		IF ROUND(RANDOM() * 2) = 1 THEN
			IF afwijkingen = '' THEN
				afwijkingen = afwijkingen || count;
			ELSE
				afwijkingen = afwijkingen || ',' || count;
			END IF;
		END IF;

		count := count + 1;
		IF count >= max THEN
			EXIT;
		END IF;
	END LOOP;

	-- als er afwijkingen zijn voeg ze dan toe aan de resultaat tabel
	IF afwijkingen <> '' THEN
		INSERT INTO mig_leveringsvergelijking_resultaat_kop (bijhouding_ber_id, afnemer_code, levering_ber_id_gbav, levering_ber_id_brp, afwijkingen, berichtnummer) VALUES
								    (bijhouding_ber_id, afnemer_code, levering_ber_id_gbav, levering_ber_id_brp, afwijkingen, berichtnummer);
	END IF;

	RETURN;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS create_random_inhoud_resultaat(bigint, character varying, bigint, bigint, character varying);
CREATE FUNCTION create_random_inhoud_resultaat(bigint, character varying, bigint, bigint, character varying) RETURNS void AS $$
DECLARE
	bijhouding_ber_id bigint = $1;
	afnemer_code bigint = $2;
	levering_ber_id_gbav bigint = $3;
	levering_ber_id_brp bigint = $4;
	berichtnummer character varying(4) = $5;
	categorie character varying(2) = 0;
	max integer = 8;
	count integer = 0;
	rubriek character varying(8) = '';
BEGIN
	-- bepaal of er verschillen geconstateerd dienen te worden
	IF ROUND(RANDOM() * 5) > 3 THEN
		RETURN;
	END IF;

	-- bepaal hoeveel afwijkingen
	max = ROUND(RANDOM() * 2);
	LOOP
		categorie = LPAD(CAST(ROUND(RANDOM() * 6 + 1) as character varying(2)), 2, '0');
		rubriek = categorie || '.' || (10 * ROUND(RANDOM() * 4) + 10) || '.' || (10 * ROUND(RANDOM() * 4) + 10);

		INSERT INTO mig_leveringsvergelijking_resultaat_inhoud (bijhouding_ber_id, afnemer_code, levering_ber_id_gbav, levering_ber_id_brp, categorie, stapel, voorkomen, rubriek, berichtnummer) VALUES
			(bijhouding_ber_id, afnemer_code, levering_ber_id_gbav, levering_ber_id_brp, CAST(categorie as integer), ROUND(RANDOM() * 4), ROUND(RANDOM() * 4), rubriek, berichtnummer);

		count := count + 1;
		IF count >= max THEN
			EXIT;
		END IF;
	END LOOP;
	RETURN;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS create_resultaten_vergelijking();
CREATE FUNCTION create_resultaten_vergelijking() RETURNS void AS $$
DECLARE
	correlatie RECORD;
BEGIN
	RAISE NOTICE 'Bezig met vullen van de resultaat tabellen.';

	FOR correlatie IN SELECT gbav.bijhouding_ber_id as bijhouding_ber_id,
				 gbav.afnemer_code as afnemer_code,
				 gbav.berichtnummer as berichtnummer,
				 gbav.levering_ber_id as levering_ber_id_gbav,
				 brp.levering_ber_id as levering_ber_id_brp
				 FROM mig_leveringsvergelijking_berichtcorrelatie_gbav AS gbav
				 INNER JOIN mig_leveringsvergelijking_berichtcorrelatie_brp AS brp ON
				 gbav.bijhouding_ber_id = brp.bijhouding_ber_id AND gbav.afnemer_code = brp.afnemer_code AND gbav.berichtnummer = brp.berichtnummer
				 LOOP
		-- bepaal of er een melding gemaakt moet worden over de kop van het bericht
		PERFORM create_random_kop_resultaat(correlatie.bijhouding_ber_id, correlatie.afnemer_code, correlatie.levering_ber_id_gbav, correlatie.levering_ber_id_brp, correlatie.berichtnummer);

		-- bepaal of en hoeveel meldingen er gemaakt dienen te worden
		PERFORM create_random_inhoud_resultaat(correlatie.bijhouding_ber_id, correlatie.afnemer_code, correlatie.levering_ber_id_gbav, correlatie.levering_ber_id_brp, correlatie.berichtnummer);

	END LOOP;

	RAISE NOTICE 'Klaar met vullen van de resultaat tabellen.';

	RETURN;
END;
$$ LANGUAGE plpgsql;

-- query om een hele nieuwe set van gegevens te genereren
BEGIN;
DELETE FROM proefsynchronisatiebericht;
DELETE FROM lo3_bericht;
DELETE FROM activiteit;
DELETE FROM mig_bericht;
DELETE FROM mig_proces_gerelateerd;
DELETE FROM mig_virtueel_proces_gerelateerd;
DELETE FROM mig_leveringsvergelijking_resultaat_inhoud;
DELETE FROM mig_leveringsvergelijking_resultaat_kop;
SELECT vul_gbav_en_brp(1000); -- vul de gbav en brp databases met synthetische data (aantal bijhoudingsberichten)
SELECT create_berichtcorrelatie_brp(); -- leg de berichtcorrelatie aan van de brp berichten
SELECT create_berichtcorrelatie_gbav(); -- leg de berichtcorrelatie aan van de gbav berichten
SELECT create_resultaten_vergelijking(); -- leg de vergelijkingsresultaten met synthetische data
COMMIT;

-- queries om te controleren of de inhoud juist is van hetgeen is gemaakt
-- SELECT * FROM activiteit;
-- SELECT * FROM lo3_bericht;
-- SELECT * FROM proefsynchronisatiebericht;
-- SELECT * FROM mig_bericht;
-- SELECT * FROM mig_proces_gerelateerd;
-- SELECT * FROM mig_virtueel_proces_gerelateerd;
-- SELECT * FROM mig_leveringsvergelijking_berichtcorrelatie_brp;
-- SELECT * FROM mig_leveringsvergelijking_berichtcorrelatie_gbav;
-- SELECT * FROM mig_leveringsvergelijking_resultaat_kop;
-- SELECT * FROM mig_leveringsvergelijking_resultaat_inhoud;
