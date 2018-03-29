-- Dit scripts vult de relevante tabellen van GBAV met synthetische data
-- Dit script dient uitgevoerd te worden op de database waar relevante tabellen van GBAV te vinden zijn

-- Geef een random bericht terug
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

-- Geef een random afnemer_code terug
DROP FUNCTION IF EXISTS get_random_afnemer();
CREATE FUNCTION get_random_afnemer() RETURNS integer AS $$
DECLARE
BEGIN
	RETURN (ROUND(RANDOM() * 3) + 1) * 10000;
END;
$$ LANGUAGE plpgsql;

-- Geeft een random bericht id terug
DROP FUNCTION IF EXISTS get_random_bericht_id();
CREATE FUNCTION get_random_bericht_id() RETURNS character varying(36) AS $$
DECLARE
BEGIN
	RETURN MD5(RANDOM() || ' ' || RANDOM());
END;
$$ LANGUAGE plpgsql;

-- Vul de GBAV tabellen met een X aantal random berichten en activiteiten
DROP FUNCTION IF EXISTS vul_gbav(integer);
CREATE FUNCTION vul_gbav(integer) RETURNS BOOLEAN AS $$
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

-- Schoon op en start met vullen
BEGIN;
	DELETE FROM proefsynchronisatiebericht;
	DELETE FROM lo3_bericht;
	DELETE FROM activiteit;
	SELECT vul_gbav(1000); -- vul de gbav en brp databases met synthetische data (aantal bijhoudingsberichten)
COMMIT;