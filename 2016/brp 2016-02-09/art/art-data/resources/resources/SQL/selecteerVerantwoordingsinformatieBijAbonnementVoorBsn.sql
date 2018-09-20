select ber.abonnement, ber.data, a.naam
from ber.ber
join autaut.abonnement a on (a.id = ber.abonnement)
where ber.admhnd = (select admhnd from kern.pers where bsn = ${DataSource Values#bsn}) and a.id  = ${DataSource Values#abonnement_id} and ber.srtsynchronisatie = ${DataSource Values#srtSync} -- 1 = mutatiebericht, 2 is Volledigbericht
order by ber.tsverzending desc
limit 1;