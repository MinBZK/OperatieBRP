select ber.abonnement, ber.data, a.naam
from ber.ber
join autaut.abonnement a on (a.id = ber.abonnement)
where ber.admhnd = (select admhnd from kern.pers where bsn = ${bsn}) and a.id  = ${abonnement_id} and ber.srtsynchronisatie = ${srtSync} -- 1 = mutatiebericht, 2 is Volledigbericht
order by ber.tsverzending desc
limit 1;