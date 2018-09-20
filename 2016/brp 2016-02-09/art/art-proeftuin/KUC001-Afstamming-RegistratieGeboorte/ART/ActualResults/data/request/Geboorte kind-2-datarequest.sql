select ber.abonnement, a.naam, ber.data 
from ber.ber 
join autaut.abonnement a on (a.id = ber.abonnement)
where a.id  = 40 and ber.srtsynchronisatie = 1 --1 is mutatiebericht 
AND ber.admhnd in ( select admhnd from kern.pers where bsn =  100000009)
AND ber.id > (select max(id) 
		from ber.ber 
		where ber.referentienr = '00000000-0000-0000-0001-100000000020')
order by ber.tsverzending desc
limit 1;