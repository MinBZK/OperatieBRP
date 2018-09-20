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
    and (berpers.pers = [@persoon_id_voor bsn=_objectid$persoon0_ /] or
      berpers.pers = [@persoon_id_voor bsn=_objectid$persoon1_ /] or
      berpers.pers = [@persoon_id_voor bsn=_objectid$persoon2_ /])
	and b.abonnement = ${abonnement_id} 
order by b.id desc