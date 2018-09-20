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
		where ber.referentienr = '${referentienr_id}')
	and b.abonnement = ${abonnement_id}  
    and (berpers.pers = [@persoon_id_voor bsn=burgerservicenummer_ipk0 /]
		or berpers.pers = [@persoon_id_voor bsn=burgerservicenummer_ipo00 /]
		or berpers.pers = [@persoon_id_voor bsn=burgerservicenummer_ipo01 /])
order by b.id desc
FETCH first 2 row only