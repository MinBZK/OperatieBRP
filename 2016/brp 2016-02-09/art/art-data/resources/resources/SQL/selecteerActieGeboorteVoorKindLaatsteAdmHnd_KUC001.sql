SELECT p.id as PERS_ID, p.bsn as PERS_BSN, hpg.actieinh AS HIS_PERSGEBOORTE_ACTIEINH, a.id AS ACTIE_ID, hb.actieinh AS HIS_BETR_ACTIEINH, hr.actieinh as HIS_RELATIE_ACTIEINH
FROM kern.pers p
JOIN kern.his_persgeboorte hpg ON (hpg.pers = p.id)
JOIN kern.betr b ON (b.pers = p.id)
JOIN kern.his_betr hb ON (hb.betr = b.id)
JOIN kern.actie a ON (a.id = hpg.actieinh)
JOIN kern.admhnd ad ON (ad.id = a.admhnd)
JOIN kern.relatie r ON (r.id = b.relatie)
JOIN kern.his_relatie hr ON (hr.relatie = r.id)
WHERE p.bsn = ${DataSource Values#burgerservicenummer_B00}
AND ad.srt = (SELECT id FROM kern.srtadmhnd WHERE naam = 'Geboorte in Nederland')
AND a.srt = (SELECT id FROM kern.srtactie WHERE naam = 'Registratie geboorte')
AND ad.id = p.admhnd
AND hb.actieinh = a.id
AND hpg.actieinh = a.id
AND hr.actieinh = a.id
