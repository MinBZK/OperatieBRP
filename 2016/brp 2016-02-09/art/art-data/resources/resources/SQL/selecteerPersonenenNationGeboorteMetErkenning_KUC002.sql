select
	pers.bsn,
	pers.indafgeleid,
	pers.indnreeks,
	pers.predicaat,
	pers.voornamen,
	pers.adellijketitel,
	pers.geslnaamstam,
	pers.voorvoegsel,
	'['||pers.scheidingsteken||']' scheidingsteken,
	pers.geslachtsaand,
	pers.naamgebruik,
	pers.indnaamgebruikafgeleid,
	pers.predicaatnaamgebruik,
	pers.voornamennaamgebruik,
	pers.adellijketitelnaamgebruik,
	pers.voorvoegselnaamgebruik,
	'['||pers.scheidingstekennaamgebruik||']' scheidingstekennaamgebruik,
	pers.geslnaamstamnaamgebruik,
	nation.naam,
	nation.code
from
	kern.pers 	pers,
	kern.persnation persnation,
	kern.nation     nation
where
	persnation.pers = pers.id and
	nation.id      = persnation.nation and
	(pers.bsn = ${DataSource Values#burgerservicenummer_B10} or
	 pers.bsn= ${DataSource Values#|objectid.burgerservicenummer_ipo_B14|} or pers.bsn= ${DataSource Values#|objectid.burgerservicenummer_ipo_B11|})
order by pers.bsn ASC;
