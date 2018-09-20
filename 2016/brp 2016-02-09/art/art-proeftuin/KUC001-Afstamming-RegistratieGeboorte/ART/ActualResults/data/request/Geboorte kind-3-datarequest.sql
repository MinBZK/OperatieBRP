select ber.abonnement, a.naam, ber.data
from ber.ber
join autaut.abonnement a on (a.id = ber.abonnement)
where a.id  = 40 and ber.srtsynchronisatie = 2 --2 is Volledigbericht
AND ber.admhnd in ( select admhnd from kern.pers where bsn =  200010992)
AND ber.id > (select max(id)
		from ber.ber
		where ber.referentienr = '00000000-0000-0000-0001-100000000020')
order by ber.tsverzending desc
limit 1;
