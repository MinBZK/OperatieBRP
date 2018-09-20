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
	kern.pers 		pers
	left join kern.persnation persnation on (persnation.pers = pers.id)
	left join kern.nation  nation on (nation.id   = persnation.nation)
	left join kern.his_persnation 	hpnation on (persnation.id 	= hpnation.persnation)
where
	(pers.bsn = ${burgerservicenummer_B10} or
	 pers.bsn= ${_objectid$burgerservicenummer_ipo_B14_} or pers.bsn= ${_objectid$burgerservicenummer_ipo_B11_})
order by pers.bsn, nation.naam ASC;