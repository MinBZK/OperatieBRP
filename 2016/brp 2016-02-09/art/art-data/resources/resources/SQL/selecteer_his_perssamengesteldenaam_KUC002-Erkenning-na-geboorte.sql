select
hpsg.id,
hpsg.pers,
hpsg.dataanvgel,
hpsg.dateindegel,
to_char(hpsg.tsreg, 'YYYYMMDD') hpsg_tsreg,
to_char(hpsg.tsverval, 'YYYYMMDD') hpsg_tsverval,
hpsg.actieinh,
hpsg.actieverval,
hpsg.actieaanpgel,
hpsg.indafgeleid,
hpsg.indnreeks,
hpsg.predicaat,
hpsg.voornamen,
hpsg.adellijketitel,
hpsg.voorvoegsel, '['||hpsg.scheidingsteken||']' scheidingsteken,
hpsg.geslnaamstam
from
kern.his_perssamengesteldenaam hpsg
where
hpsg.pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.burgerservicenummer_B07|})
order by hpsg.dataanvgel ASC, hpsg.dateindegel desc, hpsg_tsverval asc;
