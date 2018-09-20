SELECT
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
FROM
	kern.pers 	pers
WHERE
	(pers.bsn = ${DataSource Values#|objectid.persoon0|} OR
	  pers.bsn = ${DataSource Values#|objectid.persoon1|} OR
	  pers.bsn = ${DataSource Values#|objectid.persoon3|})
ORDER BY pers.bsn ASC;
