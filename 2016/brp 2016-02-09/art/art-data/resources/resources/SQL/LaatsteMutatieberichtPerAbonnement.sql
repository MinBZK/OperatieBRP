select ber.abonnement, a.naam, ber.data 
from ber.ber 
join autaut.abonnement a on (a.id = ber.abonnement)
where a.id  = ${DataSource Values#abonnement_id} and ber.srtsynchronisatie = 1 --1 is mutatiebericht 
AND ber.admhnd in ( select admhnd from kern.pers where bsn =  ${DataSource Values#|bsn_rOO0|})
AND ber.id > (select max(id) 
		from ber.ber 
		where (ber.referentienr = '${DataSource Values#referentienummer_sOO0}' or ber.referentienr = '${DataSource Values#referentienr_id}'))
order by ber.tsverzending desc
limit 1;