-- Dit script vult de BRP tabellen met synthetische data

-- Verkrijg een random berichtnummer
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

-- Verkrijg een random afnemer
DROP FUNCTION IF EXISTS get_random_afnemer();
CREATE FUNCTION get_random_afnemer() RETURNS integer AS $$
DECLARE
BEGIN
	RETURN (ROUND(RANDOM() * 3) + 1) * 10000;
END;
$$ LANGUAGE plpgsql;

-- Verkrijg een random bericht id
DROP FUNCTION IF EXISTS get_random_bericht_id();
CREATE FUNCTION get_random_bericht_id() RETURNS character varying(36) AS $$
DECLARE
BEGIN
	RETURN MD5(RANDOM() || ' ' || RANDOM());
END;
$$ LANGUAGE plpgsql;

-- Vul de BRP tabellen met synthetische data
-- Maakt hiervoor gebruik van de berichten die in GBAV aanwezig zijn
DROP FUNCTION IF EXISTS vul_brp();
CREATE FUNCTION vul_brp() RETURNS void AS $$
DECLARE
	bericht_id bigint := 0;
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
	lo3_bericht_var RECORD;

BEGIN
	FOR lo3_bericht_var IN SELECT * FROM dblink('dbname=GBAV user=postgres password=zielig', 'SELECT lo3_bericht_id FROM lo3_bericht WHERE kop_berichtsoort_nummer = ''Lg01'' AND originator_or_recipient = ''06666''') AS t(lo3_bericht_id bigint)
	LOOP
		bericht_id = lo3_bericht_var.lo3_bericht_id;

		-- in sommige gevallen geen bijhoudingsbericht maken
		IF ROUND(RANDOM() * 30) <> 1 THEN

			-- proces van het bijhoudingsbericht maken
			INSERT INTO mig_proces_gerelateerd (soort_gegeven) VALUES ('ADH');
			process_instance_id_var := LASTVAL();
			gegeven_var := CURRVAL('mig_proces_gerelateerd_gegeven_seq');

			-- bijhoudingsbericht zelf maken
			INSERT INTO mig_bericht (message_id, ontvangende_partij, naam, richting, kanaal, process_instance_id, bericht) VALUES ('' || bericht_id, '06666', 'Lg01', 'I', 'VOISC', process_instance_id_var, '00000000Lg01');

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


							INSERT INTO mig_bericht (message_id, ontvangende_partij, naam, richting, kanaal, virtueel_proces_id, bericht) VALUES (get_random_bericht_id(), afnemer_var, 'Levering', 'U', 'VOISC', virtueel_proces_id_var, '00000000' || berichtnummer_var);

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
		END IF;
	END LOOP;
	RETURN;
END;
$$ LANGUAGE plpgsql;

-- Opschonen en uitvoeren van het vullen
BEGIN;
	DELETE FROM mig_bericht;
	DELETE FROM mig_proces_gerelateerd;
	DELETE FROM mig_virtueel_proces_gerelateerd;
	SELECT vul_brp();
COMMIT;
