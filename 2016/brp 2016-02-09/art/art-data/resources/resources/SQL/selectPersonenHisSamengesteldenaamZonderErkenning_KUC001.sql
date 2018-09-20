select
	pers.bsn burgerservicenummer,
	pers.datgeboorte geboortedatum,
	his_psgn.dataanvgel,
	his_psgn.dateindegel,
	his_psgn.indafgeleid ,
	his_psgn.indnreeks ,
	his_psgn.predicaat ,
	his_psgn.voornamen ,
	his_psgn.adellijketitel ,
	his_psgn.voorvoegsel ,
	'['||his_psgn.scheidingsteken||']' scheidingsteken,
	his_psgn.geslnaamstam
from
	kern.pers	pers,
	kern.his_perssamengesteldenaam his_psgn
where
	pers.id = his_psgn.pers and
	(pers.bsn = ${DataSource Values#burgerservicenummer_B00})
order by pers.bsn ASC;
