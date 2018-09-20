select distinct
   b.id,
   b.abonnement,
   b.data
from
	ber.ber b
	join ber.berpers on b.id = berpers.ber
where
	--b.id moet groter zijn dan het b.id van het laatste request met referentienummer
	b.id > (select max(id) 
		from ber.ber 
		where ber.referentienr = '${DataSource Values#referentienr_id}')
		
    and (berpers.pers = persoon_id_voor_bsn(${burgerservicenummer_ipk0}))
order by b.id asc
FETCH first 2 row only
