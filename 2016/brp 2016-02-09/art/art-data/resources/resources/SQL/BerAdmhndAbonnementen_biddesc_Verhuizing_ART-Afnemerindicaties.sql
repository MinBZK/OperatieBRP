select distinct
   b.id, 
   b.ontvangendepartij,
   b.abonnement
from 
	ber.srtber srtb,
	ber.ber b
	join ber.berpers on b.id = berpers.ber
	
where 
	--b.id moet groter zijn dan het b.id van het laatste request met referentienummer
	b.id > (select max(id) 
		from ber.ber 
		where referentienr = '${DataSource Values#referentienr_id}')
		
	and (berpers.pers = 1003)
	AND b.abonnement = 5670002
	AND srtb.naam = 'lvg_synVerwerkPersoon'

order by b.id desc
limit 1;