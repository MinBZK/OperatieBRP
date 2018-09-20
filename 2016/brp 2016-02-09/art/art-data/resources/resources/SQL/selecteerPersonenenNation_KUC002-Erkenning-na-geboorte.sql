select
	pers.id,
	pers.bsn,
	pers.anr,
	pers.indafgeleid,
	pers.indnreeks,
	pers.predicaat,
	pers.voornamen,
	pers.adellijketitel,
	pers.geslnaamstam,
	pers.voorvoegsel,
	'['||pers.scheidingsteken||']' scheidingsteken,
	pers.datgeboorte,
	pers.gemgeboorte,
	pers.wplnaamgeboorte,
	pers.blplaatsgeboorte,
	pers.blregiogeboorte,
	pers.landgebiedgeboorte,
	pers.geslachtsaand,
	pers.datinschr,
	pers.naderebijhaard,
	pers.naamgebruik,
	pers.indnaamgebruikafgeleid,
	pers.predicaatnaamgebruik,
	pers.voornamennaamgebruik,
	pers.adellijketitelnaamgebruik,
	pers.voorvoegselnaamgebruik,
	'['||pers.scheidingstekennaamgebruik||']' scheidingstekennaamgebruik,
	pers.geslnaamstamnaamgebruik,
	pers.landgebiedmigratie,
	persnation.id,
	persnation.pers,
	persnation.nation,
	persnation.rdnverk,
	persnation.rdnverlies,
	nation.naam,
	nation.code
from
	kern.pers 	pers,
	kern.persnation persnation,
	kern.nation     nation
where
	persnation.pers = pers.id and
	nation.id      = persnation.nation and
	(pers.bsn = ${DataSource Values#|objectid.burgerservicenummer_B07|})
order by pers.bsn ASC, nation.code desc;
