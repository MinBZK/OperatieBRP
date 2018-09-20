-- overzicht tonen van de verschullende berichtcombinaties
SELECT * 
	FROM mig_leveringsvergelijking_berichtcombinaties_gbav_brp 
	ORDER BY aantal DESC;

-- overzicht hoe vaak een berichtcombinatie voorkomt
SELECT SUM(aantal) 
	FROM mig_leveringsvergelijking_berichtcombinaties_gbav_brp;

-- overzicht van alle berichtcombinaties die gelijk zijn (en waar de inhoud met elkaar vergeleken dien te worden)
SELECT * 
	FROM mig_leveringsvergelijking_berichtcombinaties_gbav_brp 
	WHERE gbav_berichtnummers = brp_berichtnummers 
	ORDER BY aantal DESC;

-- overzicht maken van de soort verschillen per berichtnummer en hoe vaak die voorkomen
SELECT berichtnummer, rubriek, count(rubriek) AS aantal 
	FROM mig_leveringsvergelijking_resultaat_inhoud 
	GROUP BY berichtnummer, rubriek  
	ORDER BY aantal DESC, berichtnummer asc;

-- overzicht maken van de soort verschillen per berichtnummer in de kop van het bericht en hoe vaak die voorkomen
SELECT berichtnummer, afwijkingen, count(afwijkingen) as aantal 
	FROM mig_leveringsvergelijking_resultaat_kop 
	GROUP BY berichtnummer, afwijkingen 
	ORDER BY berichtnummer, aantal DESC;

-- bepalen hoe vaak het voorkomt dat een afnemer in gbav wel een bericht ontvangt en in brp niet
SELECT gbav.*, brp.* 
	FROM mig_leveringsvergelijking_berichtcorrelatie_gbav AS gbav
	LEFT JOIN mig_leveringsvergelijking_berichtcorrelatie_brp AS brp 
	ON gbav.bijhouding_ber_id = brp.bijhouding_ber_eref 
	AND gbav.afnemer_code = brp.afnemer_code;
				
SELECT COUNT(*) 
	FROM (
		SELECT brp.berichtnummer, gbav.afnemer_code FROM mig_leveringsvergelijking_berichtcorrelatie_gbav AS gbav
		LEFT JOIN mig_leveringsvergelijking_berichtcorrelatie_brp AS brp 
		ON gbav.bijhouding_ber_id = brp.bijhouding_ber_eref 
		AND gbav.afnemer_code = brp.afnemer_code
	) t
	WHERE berichtnummer IS NULL 
	AND afnemer_code IS NOT NULL;

-- bepalen hoe vaak het voorkomt dat een afnemer in brp wel een bericht ontvangt en in gbav niet
SELECT gbav.*, brp.* 
	FROM mig_leveringsvergelijking_berichtcorrelatie_brp AS brp
	LEFT JOIN mig_leveringsvergelijking_berichtcorrelatie_gbav AS gbav 
	ON gbav.bijhouding_ber_id = brp.bijhouding_ber_eref 
	AND gbav.afnemer_code = brp.afnemer_code;

SELECT COUNT(*) 
	FROM (
		SELECT gbav.berichtnummer, brp.afnemer_code FROM mig_leveringsvergelijking_berichtcorrelatie_brp AS brp
		LEFT JOIN mig_leveringsvergelijking_berichtcorrelatie_gbav AS gbav 
		ON gbav.bijhouding_ber_id = brp.bijhouding_ber_eref 
		AND gbav.afnemer_code = brp.afnemer_code
	) t
	WHERE berichtnummer IS NULL 
	AND afnemer_code IS NOT NULL;


-- bepalen hoeveel bijhoudingsberichten er zijn ontvangen in gbav
SELECT COUNT(DISTINCT bijhouding_ber_id) 
	FROM mig_leveringsvergelijking_berichtcorrelatie_gbav;

-- bepalen hoeveel bijhoudingsberichten er zijn ontvangen in brp
SELECT COUNT(DISTINCT bijhouding_ber_id) 
	FROM mig_leveringsvergelijking_berichtcorrelatie_brp;

-- bepalen hoeveel leveringsberichten er zijn verstuurd in gbav
SELECT COUNT(*) 
	FROM mig_leveringsvergelijking_berichtcorrelatie_gbav 
	WHERE afnemer_code IS NOT NULL;

-- bepalen hoeveel leveringsberichten er zijn verstuurd in de brp
SELECT COUNT(*) 
	FROM mig_leveringsvergelijking_berichtcorrelatie_brp 
	WHERE afnemer_code IS NOT NULL;

-- bepalen hoe vaak er naar aanleiding van een bijhoudingsbericht geen leveringsbericht is verstuurd (gbav)
SELECT COUNT(*) 
	FROM mig_leveringsvergelijking_berichtcorrelatie_gbav 
	WHERE afnemer_code IS NULL;

-- bepalen hoe vaak er naar aanleiding van een bijhoudingsbericht geen leveringsbericht is verstuurd (brp)
SELECT COUNT(*) 
	FROM mig_leveringsvergelijking_berichtcorrelatie_brp 
	WHERE afnemer_code IS NULL;

