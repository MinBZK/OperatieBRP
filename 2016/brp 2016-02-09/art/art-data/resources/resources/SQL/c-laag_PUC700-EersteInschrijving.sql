 with alleActiesBehorendeBijHandeling (actieRegIdnrs, actieRegGeboorte, actieRegNaamgebruik, actieRegNation) as
 (select his_persids.actieinh, his_persgeboorte.actieinh, his_persnaamgebruik.actieinh, his_persnation.actieinh
 from kern.his_persids, kern.his_persgeboorte , kern.his_persnaamgebruik, kern.persnation, kern.his_persnation
 where his_persgeboorte.pers = his_persids.pers
 and his_persids.bsn = ${DataSource Values#db_bsn}
 and his_persnaamgebruik.pers = his_persids.pers
 and persnation.pers = his_persids.pers
 and his_persnation.persnation = persnation.id
 and his_persids.dateindegel is null and his_persids.tsverval is null)
select
    hpa.dataanvgel hpadataanvgel,
    p.id,
    actie.partij actiepartij,
    actie.srt actiesrt,
    to_char(actie.tsreg, 'YYYYMMDD') actietsreg,
    to_char(hpa.tsreg, 'YYYYMMDD') hpatsreg,
    hpb.tsreg hpbtsreg,
    --hpaans.tsreg hpaanstsreg,
    hpg.tsreg hpgtsreg,
    hpga.tsreg hpgatsreg,
    hpnc.tsreg hpnctsreg,
    hpid.tsreg hpidtsreg,
    hpins.tsreg hpinstsreg,
    hpnat.tsreg hpnattsreg,
    hps.tsreg hpstsreg,
    hpn.tsreg hpntsreg,
    hpa.dataanvgel hpadataanvgel,
    hpb.dataanvgel hpbdataanvgel,
    --hpaans.dataanvgel hpaansdataanvgel,
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
    nat.naam nationaliteit,
    p.datgeboorte,
    p.gemgeboorte,
    p.wplnaamgeboorte,
    p.landgebiedgeboorte,
    pl.naam plaats,
    l.naam landgebied,
    p.bijhpartij,
    g.id partijid
from
    kern.persgeslnaamcomp pnc,
    kern.his_persadres hpa,
    kern.his_persids hpid,
    --kern.his_persnaamgebruik hpaans,
    kern.his_persinschr hpins,
    kern.his_persnation hpnat,
    kern.his_perssamengesteldenaam hps,
    kern.his_persvoornaam hpn,
    kern.his_persbijhouding hpb,
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
    kern.landgebied l,
    alleActiesBehorendeBijHandeling
where
    actie.id = alleActiesBehorendeBijHandeling.actieRegGeboorte
    and hpnc.tsverval is null
    and p.wplnaamgeboorte = pl.naam
    and p.gemgeboorte = g.id --// moet eigenlijk and p.bijhpartij = g.id
    and pn.pers = p.id
    and pg.pers = p.id
    and pnat.pers = p.id
    and betr.pers = p.id
    and rel.id = betr.relatie
    and pa.pers = p.id
    and nat.id = pnat.nation
    and p.bsn = ${DataSource Values#db_bsn}
    and p.landgebiedgeboorte = l.id
    and hpnc.persgeslnaamcomp = pnc.id
    and hpa.persadres = pa.id
    and hpa.actieinh = alleActiesBehorendeBijHandeling.actieRegGeboorte
    and hpnc.actieinh = alleActiesBehorendeBijHandeling.actieRegGeboorte
    and hpid.actieinh = alleActiesBehorendeBijHandeling.actieRegIdnrs
    --and hpaans.actieinh = actie.id
    and hpb.actieinh = alleActiesBehorendeBijHandeling.actieRegGeboorte
    and hpg.actieinh = alleActiesBehorendeBijHandeling.actieRegGeboorte
    and hpga.actieinh = alleActiesBehorendeBijHandeling.actieRegGeboorte
    and hpins.actieinh = alleActiesBehorendeBijHandeling.actieRegGeboorte
    and hpnat.actieinh = alleActiesBehorendeBijHandeling.actieRegNation
    and hps.actieinh = actie.id
    and hpn.actieinh = actie.id
order by betr.rol asc;
