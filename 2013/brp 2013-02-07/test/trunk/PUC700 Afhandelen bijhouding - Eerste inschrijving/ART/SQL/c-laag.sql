select 
	hpa.dataanvgel hpadataanvgel,
	p.id,
	actie.partij actiepartij,
	actie.srt actiesrt,
	to_char(actie.tijdstipreg, 'YYYYMMDD') actietsreg,
	to_char(hpa.tsreg, 'YYYYMMDD') hpatsreg,
	hpbg.tsreg hpbgtsreg,
	hpaans.tsreg hpaanstsreg,
	hpbv.tsreg hpbvtsreg,
	hpg.tsreg hpgtsreg,
	hpga.tsreg hpgatsreg,
	hpnc.tsreg hpnctsreg,
	hpid.tsreg hpidtsreg,
	hpins.tsreg hpinstsreg,
	hpnat.tsreg hpnattsreg,
	hps.tsreg hpstsreg,
	hpn.tsreg hpntsreg,
	hpa.dataanvgel hpadataanvgel,
	hpbg.dataanvgel hpbgdataanvgel,
	hpaans.dataanvgel hpaansdataanvgel,
	hpbv.dataanvgel hpbvdataanvgel,
	hpg.datgeboorte hpgdataanvgel,
	hpga.dataanvgel hpgadataanvgel,
	hpnc.dataanvgel hpncdataanvgel,
	hpid.dataanvgel hpiddataanvgel,
	hpins.datinschr hpinsdataanvgel,
	hpnat.dataanvgel hpnatdataanvgel,
	hps.dataanvgel hpsdataanvgel,
	hpn.dataanvgel hpndataanvgel,
	actie.partij actiepartij,
	betr.rol,
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
	nat.naam nationaliteit,
	p.datgeboorte,
	p.gemgeboorte,
	p.wplgeboorte,
	p.landgeboorte,
	pl.naam plaats,
	l.naam land,
	p.bijhgem,
	g.id partijid
from 
	kern.persgeslnaamcomp pnc,
	kern.his_persadres hpa,
	kern.his_persids hpid,
	kern.his_persaanschr hpaans,
	kern.his_persinschr hpins,
	kern.his_persnation hpnat,
	kern.his_perssamengesteldenaam hps,
	kern.his_persvoornaam hpn,
	kern.his_persbijhgem hpbg,
	kern.his_persbijhverantwoordelijk hpbv,
	kern.his_persgeboorte hpg,
	kern.his_persgeslachtsaand hpga,
	kern.his_persgeslnaamcomp hpnc,
	kern.actie actie,
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
	kern.land l
where 
	hpnc.tsverval is null
	and p.wplgeboorte = pl.id
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
	and hpnc.persgeslnaamcomp = pnc.id
	and hpa.persadres = pa.id
	and hpa.actieinh = actie.id
	and hpnc.actieinh = actie.id
	and hpbg.actieinh = actie.id
	and hpaans.actieinh = actie.id
	and hpbv.actieinh = actie.id
	and hpg.actieinh = actie.id
	and hpga.actieinh = actie.id
	and hpid.actieinh = actie.id
	and hpins.actieinh = actie.id
	and hpnat.actieinh = (actie.id +1)
	and hps.actieinh = actie.id
	and hpn.actieinh = actie.id
order by betr.rol asc;