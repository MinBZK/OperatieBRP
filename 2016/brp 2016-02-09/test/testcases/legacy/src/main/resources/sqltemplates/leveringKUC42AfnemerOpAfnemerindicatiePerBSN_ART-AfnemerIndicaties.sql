SELECT DISTINCT
   b.id, 
   b.abonnement,
   b.data
FROM 
	ber.ber b
WHERE 
-- ID is hoger dan die van de laatste regel waar srt = 3 (bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap)
b.id > (select max(id) 
		from ber.ber 
		where referentienr = '${referentienr_id}'
		  AND srt = 
			(select id 
				from ber.srtber 
				where naam = 'bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap'))
AND 
-- srt is 23 (lvg_synVerwerkPersoon)
b.srt = (select id 
		from ber.srtber 
		where naam = 'lvg_synVerwerkPersoon')
AND b.ontvangendepartij = ${ontvangendepartij_id}
AND b.abonnement = ${abonnement_id}
AND b.admhnd is not null
ORDER BY b.id desc