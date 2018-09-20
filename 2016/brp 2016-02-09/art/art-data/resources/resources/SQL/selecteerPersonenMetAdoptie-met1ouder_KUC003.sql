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
	pers.datgeboorte,
	pers.datinschr
from
	kern.pers 	pers
where
	(pers.bsn = ${DataSource Values#|objectid.persoon0|} or
	  pers.bsn = ${DataSource Values#|objectid.persoon1|})
order by pers.bsn ASC;
