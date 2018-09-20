select distinct
   b.id,
   b.ontvangendepartij,
   b.abonnement
FROM
	ber.ber b
	join ber.berpers on b.id = berpers.ber
WHERE
	berpers.pers = [@persoon_id_voor bsn=burgerservicenummer_ipr1 /]
	-- ID is hoger dan die van de laatste regel waar srt = 40 (lvg_synRegistreerAfnemerindicatie)
	AND b.id > (select max(id) 
		from ber.ber 
		where referentienr = '${referentienr_id!}'
		  AND srt = 
			(select id 
				from ber.srtber 
				where naam = 'lvg_synRegistreerAfnemerindicatie'))
	-- srt is 23 (lvg_synVerwerkPersoon)
	AND b.srt = (select id 
		from ber.srtber 
		where naam = 'lvg_synVerwerkPersoon')
	AND b.abonnement = ${abonnement_id} 
order by b.id desc limit 1
