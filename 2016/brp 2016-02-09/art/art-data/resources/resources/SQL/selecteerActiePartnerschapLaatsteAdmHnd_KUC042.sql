SELECT p.id as PERS_ID, p.bsn as PERS_BSN, a.id AS ACTIE_ID, hb.actieinh AS HIS_BETR_ACTIEINH, hr.actieinh as HIS_RELATIE_ACTIEINH
FROM kern.pers p
JOIN kern.betr b ON (b.pers = p.id)
JOIN kern.his_betr hb ON (hb.betr = b.id)
JOIN kern.actie a ON (a.id = hb.actieinh)
JOIN kern.admhnd ad ON (ad.id = a.admhnd)
JOIN kern.relatie r ON (r.id = b.relatie)
JOIN kern.his_relatie hr ON (hr.relatie = r.id)
WHERE (p.bsn = ${DataSource Values#|objectid.persoon1|}
OR p.bsn = ${DataSource Values#|objectid.persoon2|})
AND ad.srt = (SELECT id FROM kern.srtadmhnd WHERE naam = 'Aangaan geregistreerd partnerschap in Nederland')
AND a.srt = (SELECT id FROM kern.srtactie WHERE naam = 'Registratie aanvang huwelijk geregistreerd partnerschap')
AND ad.id = p.admhnd
AND hb.actieinh = a.id
AND hr.actieinh = a.id
ORDER BY p.bsn ASC
