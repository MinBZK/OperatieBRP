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
	persnation.rdnverk persnationrdnverk,
	persnation.rdnverlies persnationrdnverlies,
	hpnation.dataanvgel hpnationdataanvgel,
	hpnation.dateindegel hpnationdateindegel,
	hpnation.rdnverk hpnationrdnverk,
	hpnation.rdnverlies hpnationrdnverlies,
	nation.naam nationaliteit
from
	kern.pers 		pers,
	kern.persnation 	persnation,
	kern.nation     	nation,
	kern.his_persnation 	hpnation
where
	persnation.pers = pers.id and
	nation.id      	= persnation.nation and
	persnation.id 	= hpnation.persnation and
	(pers.bsn = ${DataSource Values#|objectid.persoon0|} or
	 pers.bsn= ${DataSource Values#|objectid.persoon1|} or pers.bsn= ${DataSource Values#|objectid.persoon3|})
order by pers.bsn, nation.naam ASC;
