SELECT p.id as P_ID, p.bsn as P_BSN, b.id as BETR_ID, hb.id as HIS_BETR_ID, sb.code as BETR_CODE, r.id as RELATIE_ID,
hr.id as HIS_RELATIE_ID, sr.code as RELATIE_CODE, hb.actieinh as HIS_BETR_ACTIEINH, hb.tsreg as HIS_BETR_TSREG
FROM kern.pers p
JOIN kern.betr b ON (b.pers = p.id)
JOIN kern.his_betr hb ON (hb.betr = b.id)
JOIN kern.srtbetr sb ON (b.rol = sb.id)
JOIN kern.relatie r ON (r.id  = b.relatie)
JOIN kern.his_relatie hr ON (hr.relatie = r.id)
JOIN kern.srtrelatie sr ON (sr.id = r.srt)
WHERE b.rol = (SELECT id FROM kern.srtbetr WHERE code = 'K')
AND p.bsn = ${DataSource Values#burgerservicenummer_B00}
ORDER BY p.bsn ASC;
