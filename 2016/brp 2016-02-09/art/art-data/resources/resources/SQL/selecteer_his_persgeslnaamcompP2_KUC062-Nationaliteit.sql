select
hpgc.id,
hpgc.persgeslnaamcomp,
hpgc.dataanvgel,
hpgc.dateindegel,
hpgc.tsreg,
hpgc.tsverval,
hpgc.actieinh,
hpgc.actieverval,
hpgc.actieaanpgel,
hpgc.predicaat,
hpgc.adellijketitel,
hpgc.voorvoegsel, '['||hpgc.scheidingsteken||']' scheidingsteken,
hpgc.stam
from
kern.his_persgeslnaamcomp hpgc
where hpgc.persgeslnaamcomp in (select id from kern.persgeslnaamcomp where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon2|}))
order by hpgc.dataanvgel, hpgc.tsreg, hpgc.dateindegel ASC;
