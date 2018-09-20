select
	pers.id,
	pers.bsn,
	persnation.pers,
	his_persnation.id,
	his_persnation.persnation,
	his_persnation.dataanvgel,
	his_persnation.dateindegel,
	his_persnation.tsreg,
	his_persnation.tsverval,
	his_persnation.actieinh,
	his_persnation.actieverval,
	his_persnation.actieaanpgel,
	his_persnation.rdnverk,
	his_persnation.rdnverlies,
	nation.naam,
	nation.code
from
	kern.pers 	pers,
	kern.persnation persnation,
	kern.his_persnation his_persnation,
	kern.nation     nation
where
	persnation.pers = pers.id and
	his_persnation.persnation = persnation.id and
	nation.id      = persnation.nation and
	(pers.bsn = ${DataSource Values#|objectid.persoon22|})
order by pers.bsn ASC, nation.code ASC;
