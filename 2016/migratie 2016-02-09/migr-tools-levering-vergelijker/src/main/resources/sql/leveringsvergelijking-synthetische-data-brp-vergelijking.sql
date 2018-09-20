-- Dit script vult de vergelijkingstabellen met synthetische data, normaal gesproken worden deze tabellen gevuld door javaprogrammatuur

-- Maak een random resultaat van de vergelijking van de kop van een bericht
DROP FUNCTION IF EXISTS create_random_kop_resultaat(bigint, bigint, character varying, character varying, bigint, bigint, character varying);
CREATE FUNCTION create_random_kop_resultaat(bigint, bigint, character varying, character varying, bigint, bigint, character varying) RETURNS void AS $$
DECLARE
	bijhouding_ber_id_gbav bigint = $1;
	bijhouding_ber_id_brp bigint = $2;
	bijhouding_eref bigint = $3;
	afnemer_code bigint = $4;
	levering_ber_id_gbav bigint = $5;
	levering_ber_id_brp bigint = $6;
	berichtnummer character varying(4) = $7;
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
		INSERT INTO mig_leveringsvergelijking_resultaat_kop 
			(bijhouding_ber_id_gbav, bijhouding_ber_id_brp, bijhouding_eref, afnemer_code, levering_ber_id_gbav, levering_ber_id_brp, afwijkingen, berichtnummer) VALUES
			(bijhouding_ber_id_gbav, bijhouding_ber_id_brp, bijhouding_eref, afnemer_code, levering_ber_id_gbav, levering_ber_id_brp, afwijkingen, berichtnummer);
	END IF;
	
	RETURN;
END;
$$ LANGUAGE plpgsql;

-- maak een random resultaat van de vergelijking van de inhoud van een bericht
DROP FUNCTION IF EXISTS create_random_inhoud_resultaat(bigint, bigint, character varying, character varying, bigint, bigint, character varying);
CREATE FUNCTION create_random_inhoud_resultaat(bigint, bigint, character varying, character varying, bigint, bigint, character varying) RETURNS void AS $$
DECLARE
	bijhouding_ber_id_gbav bigint = $1;
	bijhouding_ber_id_brp bigint = $2;
	bijhouding_eref bigint = $3;
	afnemer_code bigint = $4;
	levering_ber_id_gbav bigint = $5;
	levering_ber_id_brp bigint = $6;
	berichtnummer character varying(4) = $7;
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

		INSERT INTO mig_leveringsvergelijking_resultaat_inhoud 
			(bijhouding_ber_id_gbav, bijhouding_ber_id_brp, bijhouding_eref, afnemer_code, levering_ber_id_gbav, levering_ber_id_brp, categorie, stapel, voorkomen, rubriek, berichtnummer) VALUES
			(bijhouding_ber_id_gbav, bijhouding_ber_id_brp, bijhouding_eref, afnemer_code, levering_ber_id_gbav, levering_ber_id_brp, CAST(categorie as integer), ROUND(RANDOM() * 4), ROUND(RANDOM() * 4), rubriek, berichtnummer);

		count := count + 1;
		IF count >= max THEN
			EXIT;
		END IF;
	END LOOP;
	RETURN;
END;
$$ LANGUAGE plpgsql;

-- Vul het resultaat van de vergelijkingen met synthetische data
DROP FUNCTION IF EXISTS create_resultaten_vergelijking();
CREATE FUNCTION create_resultaten_vergelijking() RETURNS void AS $$
DECLARE
	correlatie RECORD;
BEGIN
	RAISE NOTICE 'Bezig met vullen van de resultaat tabellen.';
	
	FOR correlatie IN SELECT gbav.bijhouding_ber_id as bijhouding_ber_id_gbav, 
				 gbav.afnemer_code as afnemer_code,
				 gbav.berichtnummer as berichtnummer, 
				 gbav.levering_ber_id as levering_ber_id_gbav,
				 brp.bijhouding_ber_id as bijhouding_ber_id_brp,
				 brp.levering_ber_id as levering_ber_id_brp,
				 brp.bijhouding_ber_eref as bijhouding_ber_eref 
				 FROM mig_leveringsvergelijking_berichtcorrelatie_gbav AS gbav
				 INNER JOIN mig_leveringsvergelijking_berichtcorrelatie_brp AS brp ON
				 gbav.bijhouding_ber_id = brp.bijhouding_ber_id AND gbav.afnemer_code = brp.afnemer_code AND gbav.berichtnummer = brp.berichtnummer
				 LOOP
		-- bepaal of er een melding gemaakt moet worden over de kop van het bericht
		PERFORM create_random_kop_resultaat(correlatie.bijhouding_ber_id_gbav, correlatie.bijhouding_ber_id_brp, correlatie.bijhouding_ber_eref, correlatie.afnemer_code, correlatie.levering_ber_id_gbav, correlatie.levering_ber_id_brp, correlatie.berichtnummer); 

		-- bepaal of en hoeveel meldingen er gemaakt dienen te worden
		PERFORM create_random_inhoud_resultaat(correlatie.bijhouding_ber_id_gbav, correlatie.bijhouding_ber_id_brp, correlatie.bijhouding_ber_eref, correlatie.afnemer_code, correlatie.levering_ber_id_gbav, correlatie.levering_ber_id_brp, correlatie.berichtnummer); 

	END LOOP;
	
	RAISE NOTICE 'Klaar met vullen van de resultaat tabellen.';
	
	RETURN;
END;
$$ LANGUAGE plpgsql;

-- Opschonen en uitvoeren van de vergelijking
BEGIN;
	DELETE FROM mig_leveringsvergelijking_resultaat_inhoud;
	DELETE FROM mig_leveringsvergelijking_resultaat_kop;
	SELECT create_resultaten_vergelijking(); -- leg de vergelijkingsresultaten met synthetische data
COMMIT;
