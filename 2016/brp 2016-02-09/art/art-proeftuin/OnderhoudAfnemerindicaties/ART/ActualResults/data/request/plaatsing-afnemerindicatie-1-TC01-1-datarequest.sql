select ber.abonnement, a.naam, ber.data
from ber.ber
join autaut.abonnement a on (a.id = ber.abonnement)
where a.id  = 40 and ber.srtsynchronisatie = 2 --2 is Volledigbericht
--AND ber.admhnd in ( select admhnd from kern.pers where bsn =  100000186)
AND ber.id > (select max(id)
		from ber.ber
		where ber.referentienr = '0000000A-3000-1000-0000-100000000027')
order by ber.tsverzending desc
limit 1;
