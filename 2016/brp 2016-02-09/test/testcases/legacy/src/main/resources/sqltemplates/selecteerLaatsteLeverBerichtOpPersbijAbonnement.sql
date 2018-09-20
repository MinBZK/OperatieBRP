SELECT
	b.data,
	b.ontvangendepartij
FROM
	ber.ber b
	join ber.berpers on b.id = berpers.ber
WHERE
	--b.id moet groter zijn dan het b.id van het laatste request met referentienummer
	b.id > (select max(id) 
		from ber.ber 
		where ber.referentienr = '${referentienr_id}')
	AND berpers.pers = [@persoon_id_voor bsn=_objectid$burgerservicenummer_ipv0_ /]
	AND b.abonnement = ${abonnement_id}
ORDER BY b.id DESC limit 1