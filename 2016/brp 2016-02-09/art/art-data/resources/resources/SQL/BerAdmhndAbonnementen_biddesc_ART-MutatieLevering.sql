select distinct
   b.id, 
   b.ontvangendepartij,
   b.abonnement
from 
	ber.ber b
	join ber.berpers on b.id = berpers.ber
where 
	--b.id moet groter zijn dan het b.id van het laatste request met referentienummer
	b.id > (select max(id) 
		from ber.ber 
		where ber.referentienr = '${DataSource Values#referentienr_id}')
		
	and (berpers.pers = persoon_id_voor_bsn(${DataSource Values#|objectid.persoon0|}) or
      berpers.pers = persoon_id_voor_bsn(${DataSource Values#|objectid.persoon1|}) or
      berpers.pers = persoon_id_voor_bsn(${DataSource Values#|objectid.persoon2|}))
	and b.ontvangendepartij = '${DataSource Values#partij_id}'
	and b.abonnement = ${DataSource Values#abonnement_id}
	and b.srtsynchronisatie = ${DataSource Values#srtsynchronisatie_id}
order by b.id desc limit 1

