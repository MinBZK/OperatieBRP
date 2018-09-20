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
		
    and (berpers.pers = persoon_id_voor_bsn(${DataSource Values#burgerservicenummer_B10}) or
	  berpers.pers = persoon_id_voor_bsn(${DataSource Values#|objectid.burgerservicenummer_ipo_B11|}) or
	  berpers.pers = persoon_id_voor_bsn(${DataSource Values#|objectid.burgerservicenummer_ipo_B14|}))

	and b.abonnement = ${DataSource Values#abonnement_id}  
order by b.id desc	