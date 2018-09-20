select
hpsg.id,
hpsg.pers,
hpsg.dataanvgel,
hpsg.dateindegel,
hpsg.tsreg,
hpsg.tsverval,
hpsg.actieinh,
hpsg.actieverval,
hpsg.actieaanpgel,
hpsg.indafgeleid,
hpsg.indnreeks,
hpsg.predicaat,
hpsg.voornamen,
hpsg.adellijketitel,
hpsg.voorvoegsel, '['||hpsg.scheidingsteken||']' scheidingsteken, hpsg.geslnaamstam from kern.his_perssamengesteldenaam hpsg where hpsg.pers in (select id from kern.pers where bsn = ${DataSource Values#burgerservicenummer_B10})
order by hpsg.dataanvgel ASC;
