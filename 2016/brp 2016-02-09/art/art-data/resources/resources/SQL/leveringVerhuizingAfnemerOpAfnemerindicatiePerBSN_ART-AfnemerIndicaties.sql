SELECT DISTINCT
   b.id, 
   b.abonnement,
   b.data
FROM 
	ber.ber b
WHERE 
-- ID is hoger dan die van de laatste regel waar srt = 5 (bhg_vbaRegistreerVerhuizing)
b.id > (select max(id) 
		from ber.ber 
		where referentienr = '${DataSource Values#referentienr_id}'
		  AND srt = 
			(select id 
				from ber.srtber 
				where naam = 'bhg_vbaRegistreerVerhuizing'))
AND 
-- srt is 23 (lvg_synVerwerkPersoon)
b.srt = (select id 
		from ber.srtber 
		where naam = 'lvg_synVerwerkPersoon')
AND b.ontvangendepartij = ${DataSource Values#ontvangendepartij_id}
AND b.abonnement = ${DataSource Values#abonnement_id}
ORDER BY b.id desc