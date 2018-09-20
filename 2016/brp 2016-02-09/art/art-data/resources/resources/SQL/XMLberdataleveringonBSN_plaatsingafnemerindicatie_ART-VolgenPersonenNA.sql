SELECT DISTINCT
  b.id,
  b.data
FROM
  ber.ber b
WHERE
-- ID is hoger dan die van de laatste regel waar srt = 40 (lvg_afnRegistreerAfnemerindicatie)
  id > (select max(id) 
		from ber.ber 
		where referentienr = '${DataSource Values#referentienr_id}'
		  AND srt = 
			(select id 
				from ber.srtber 
				where naam = 'lvg_afnRegistreerAfnemerindicatie'))
AND 
-- srt is 23 (lvg_synVerwerkPersoon)
  srt = (select id 
		from ber.srtber 
		where naam = 'lvg_synVerwerkPersoon')
AND b.abonnement = ${DataSource Values#abonnement_id} 
ORDER BY b.id DESC
