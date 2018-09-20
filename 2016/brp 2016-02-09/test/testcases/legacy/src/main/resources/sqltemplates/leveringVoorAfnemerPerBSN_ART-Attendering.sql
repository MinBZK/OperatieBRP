select distinct
   b.id, 
   b.abonnement,
   b.data
from
	ber.ber b
join ber.berpers on b.id = berpers.ber
where
	berpers.pers = [@persoon_id_voor bsn=_objectid$persoon1_ /]
	and b.ontvangendepartij = ${ontvangendepartij_id}
	and b.abonnement = ${abonnement_id}
order by b.id desc limit 1