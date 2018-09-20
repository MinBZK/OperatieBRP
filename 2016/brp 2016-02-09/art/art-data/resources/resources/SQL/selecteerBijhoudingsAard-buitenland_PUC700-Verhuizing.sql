select
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
	his_pbg.bijhaard,
	his_pbg.naderebijhaard
from
	kern.his_persbijhouding his_pbg
where
	his_pbg.pers = (select id from kern.pers where bsn = ${DataSource Values#|objectid.burgerservicenummer_ipv4|})
order by his_pbg.dataanvgel desc, his_pbg.dateindegel asc;

