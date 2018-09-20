SELECT DISTINCT
b.id,
b.admhnd,
b.abonnement,
b.data

FROM 
ber.ber b
WHERE
b.admhnd = ${DataSource Values#admhnd_id}

and b.abonnement = ${DataSource Values#abonnement_id}
				
-- srt 23 betreft een leverbericht
AND b.srt = (select id 
		from ber.srtber 
		where naam = 'lvg_synVerwerkPersoon')
order by b.id desc limit 1 