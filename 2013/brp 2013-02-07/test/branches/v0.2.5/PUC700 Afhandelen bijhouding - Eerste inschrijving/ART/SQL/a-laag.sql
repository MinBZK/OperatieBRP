select 
	p.id,
	strbetr.naam rol,
	betr.ouderschapstatushis,
	betr.ouderlijkgezagstatushis,
	pa.rdnwijz pardnwijz,
	p.tijdstiplaatstewijz laatstewijziging,
	p.srt,
	p.bsn,
	p.anr,
	p.idsstatushis,
	p.geslachtsaand,
	p.geslachtsaandstatushis,
	p.predikaat perspredikaat,
	pn.volgnr volgnrvoorn,
	pn.naam voornaam,
	p.voornamen persvoornamen,
	pg.volgnr volgnrGesl,pg.naam pggeslnaam,
	pg.scheidingsteken pgscheidingsteken,
	pg.voorvoegsel pgvoorvoegsel,
	pg.predikaat pgpredikaat,
	pg.adellijketitel pgadellijketitel,
	p.voorvoegsel persvoorvoegsel,
	p.scheidingsteken persscheidingsteken,
	p.adellijketitel persadellijketitel,
	p.geslnaam persgeslnaam,
	p.indnreeksalsgeslnaam persindnreeksalsgeslnaam,
	p.indalgoritmischafgeleid persindalgoritmischafgeleid,
	p.samengesteldenaamstatushis,
	pnat.rdnverk,
	nat.naam nationaliteit,
	adres_wpl.naam woonplaats_adres,
	adres_land.naam land_adres,
	p.datgeboorte,
	p.gemgeboorte,
	p.wplgeboorte,
	p.landgeboorte,
	pl.naam geb_plaats,
	l.naam geb_land,
	p.bijhgem,
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
	kern.land l,
	kern.land adres_land,
	kern.plaats adres_wpl
where  
	p.wplgeboorte = pl.id
	and p.gemgeboorte = g.id --// moet eigenlijkand p.bijhgem = g.id
	and pn.pers = p.id
	and pg.pers = p.id
	and pnat.pers = p.id
	and betr.pers = p.id
	and rel.id = betr.relatie
	and pa.pers = p.id
	and nat.id = pnat.nation
	and p.bsn = ${DataSource Values#db_bsn}
	and p.landgeboorte = l.id
	and pa.wpl = adres_wpl.id
	and pa.land = adres_land.id
	and strbetr.id = betr.rol
order by betr.id, volgnrvoorn asc;