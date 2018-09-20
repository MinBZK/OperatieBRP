select distinct
   b.id,
   b.data
from
	ber.ber b
	join ber.berpers on b.id = berpers.ber

where
  --Selecteer de laatste administratieve handeling op het ingeschoten referentienummer
	b.admhnd = (select admhnd
		from ber.ber
		where ber.referentienr = '${DataSource Values#referentienr_id}'
		order by id desc limit 1)

and (berpers.pers = persoon_id_voor_bsn(${DataSource Values#burgerservicenummer_B00})
	or berpers.pers = persoon_id_voor_bsn(${DataSource Values#|objectid.burgerservicenummer_ipo_B01|})
	or berpers.pers = persoon_id_voor_bsn(${DataSource Values#|objectid.burgerservicenummer_ipo_B03|}))
and b.abonnement = ${DataSource Values#abonnement_id}
--srtsynchronisatie 1=mutatiebericht srtsynchronisatie 2=Volledigbericht
and b.srtsynchronisatie = ${DataSource Values#srtsynchronisatie_id}
order by b.id desc
