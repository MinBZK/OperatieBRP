select distinct
   b.id, 
   b.abonnement,
   b.data
from
	ber.ber b
join ber.berpers on b.id = berpers.ber
where
	berpers.pers = persoon_id_voor_bsn(${DataSource Values#|objectid.persoon1|})
	and b.ontvangendepartij = ${DataSource Values#ontvangendepartij_id}
	and b.abonnement = ${DataSource Values#abonnement_id}
order by b.id desc limit 1