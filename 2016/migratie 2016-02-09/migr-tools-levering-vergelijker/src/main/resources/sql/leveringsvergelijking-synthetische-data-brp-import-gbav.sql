-- Dit script legt de berichtcorrelaties aan tussen de berichten
-- Dit wordt gedaan voor zowel de berichten van GBAV als BRP
-- Dit script dient ook uitgevoerd te worden als er sprake is van synthetische data
-- Er wordt gebruik gemaakt van de dblink extension zodat een COPY niet meer nodig is (CREATE EXTENSION dblink; -- om aan te zetten)
-- Vergeet niet de connectie string aan te passen (hostaddr=127.0.0.1 port=5432 dbname=mydb user=postgres password=mypasswd)

-- leg de berichtcorrelatie aan van alle berichten van gbav
DELETE FROM mig_leveringsvergelijking_berichtcorrelatie_gbav;
INSERT INTO mig_leveringsvergelijking_berichtcorrelatie_gbav (bijhouding_ber_id, afnemer_code, levering_ber_id, berichtnummer)
SELECT * FROM dblink('dbname=GBAV user=postgres password=zielig', '
SELECT ber1.lo3_bericht_id as bijhouding_ber_id, ber2.originator_or_recipient as afnemer_code, ber2.lo3_bericht_id as levering_ber_id, ber2.kop_berichtsoort_nummer as berichtnummer
	FROM proefsynchronisatiebericht AS proef
	INNER JOIN lo3_bericht AS ber1 ON
		proef.bericht_id = ber1.lo3_bericht_id
	INNER JOIN activiteit AS act1 ON -- Lg01/La01 bericht cyclus activiteit (100)
		ber1.bericht_activiteit_id = act1.activiteit_id AND
		act1.activiteit_type = 100 AND act1.activiteit_subtype IN (1111, 1112) AND act1.toestand >= 8000 and act1.toestand < 9000
	LEFT JOIN activiteit AS act2 ON -- Spontaan activiteit
		act1.activiteit_id = act2.moeder_id AND
		act2.activiteit_type = 107 AND act2.activiteit_subtype = 1220 AND act2.toestand >= 8000 and act2.toestand < 9000
	LEFT JOIN activiteit AS act3 ON -- Verstrekkingsbericht activiteit (101)
		act3.moeder_id = act2.activiteit_id AND
		act3.activiteit_type = 101 AND act3.toestand >= 8000 and act3.toestand < 9000
	LEFT JOIN lo3_bericht AS ber2 ON -- alle uitgaande berichten die bij de gevonden activiteiten horen
		act3.activiteit_id = ber2.bericht_activiteit_id AND
		ber2.kop_berichtsoort_nummer IN (''Ag11'', ''Ag21'', ''Ag31'', ''Ng01'', ''Wa11'', ''Gv01'', ''Gv02'')
') AS t(bijhouding_ber_id bigint, afnemer_code character varying, levering_ber_id bigint, berichtnummer character varying);