select
	p.id,
	strbetr.naam rol,
	pa.rdnwijz pardnwijz,
	p.tslaatstewijz laatstewijziging,
	p.srt,
	p.bsn,
	p.anr,
	p.geslachtsaand,
	p.predicaat perspredicaat,
	pn.volgnr volgnrvoorn,
	pn.naam voornaam,
	p.voornamen persvoornamen,
	pg.volgnr volgnrGesl,pg.stam pggeslnaamstam,
	'['||pg.scheidingsteken||']' pgscheidingsteken,
	pg.voorvoegsel pgvoorvoegsel,
	pg.predicaat pgpredicaat,
	pg.adellijketitel pgadellijketitel,
	p.voorvoegsel persvoorvoegsel,
	'['||p.scheidingsteken||']' persscheidingsteken,
	p.adellijketitel persadellijketitel,
	p.geslnaamstam persgeslnaamstam,
	p.indnreeks persindnreeksalsgeslnaamstam,
	p.indafgeleid persindafgeleid,
	pnat.rdnverk,
	nat.naam nationaliteit,
	adres_wpl.naam woonplaats_adres,
	adres_land.naam land_adres,
	p.datgeboorte,
	p.gemgeboorte,
	p.wplnaamgeboorte,
	p.landgebiedgeboorte,
	pl.naam geb_plaats,
	l.naam geb_land,
	p.bijhpartij,
	g.id partijid,
	betr.id betr_id
from
	kern.srtbetr strbetr,
	kern.betr betr,
	kern.relatie rel,
	kern.pers p,
	kern.persadres pa,
	kern.persvoornaam pn,
	kern.persgeslnaamcomp pg,
	kern.persnation pnat,
	kern.nation nat,
	kern.partij g,
	kern.plaats pl,
	kern.landgebied l,
	kern.landgebied adres_land,
	kern.plaats adres_wpl
where
	p.wplnaamgeboorte = pl.naam
	and p.gemgeboorte = g.id --// moet eigenlijkand p.bijhpartij = g.id
	and pn.pers = p.id
	and pg.pers = p.id
	and pnat.pers = p.id
	and betr.pers = p.id
	and rel.id = betr.relatie
	and pa.pers = p.id
	and nat.id = pnat.nation
	and p.bsn = ${DataSource Values#db_bsn}
	and p.landgebiedgeboorte = l.id
	and pa.wplnaam = adres_wpl.naam
	and pa.landgebied = adres_land.id
	and strbetr.id = betr.rol
order by betr.id, volgnrvoorn asc;
