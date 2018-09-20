select ber.abonnement, ber.data
from ber.ber
where ber.abonnement  = '${DataSource Values#abonnement_id}'
and ber.srtsynchronisatie = 2 --2 is Volledigbericht ( bij Sync)
and ber.crossreferentienr = '${DataSource Values#referentienr_id}'
order by ber.tsverzending desc
limit 1;