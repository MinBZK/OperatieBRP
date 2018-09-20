select
	pers.id,
	pers.bsn burgerservicenummer,
	pers.bijhaard,
	pers.bijhpartij,
	pers.indonverwdocaanw,
	his_pbg.id,
	his_pbg.pers,
	his_pbg.dataanvgel,
	his_pbg.dateindegel,
	his_pbg.tsreg,
	his_pbg.tsverval,
	his_pbg.actieinh,
	his_pbg.actieverval,
	his_pbg.actieaanpgel,
	his_pbg.bijhpartij,
	his_pbg.indonverwdocaanw
from
	kern.pers	pers,
	kern.his_persbijhouding his_pbg
where
	his_pbg.pers = pers.id and
	(pers.bsn = ${DataSource Values#|objectid.burgerservicenummer_ipv0|})
order by his_pbg.dataanvgel desc;
