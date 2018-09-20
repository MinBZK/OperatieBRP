select ber.abonnement, ber.data 
from ber.ber 
where ber.abonnement  = '${abonnement_id}' 
and ber.srtsynchronisatie = 2 --2 is volledigbericht ( bij Sync)
and ber.crossreferentienr = '${referentienr_id}'
order by ber.tsverzending desc
limit 1;