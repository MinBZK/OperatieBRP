select
hpind.id,
hpind.persindicatie,
hpind.dataanvgel,
hpind.dateindegel,
hpind.tsreg,
hpind.tsverval,
hpind.actieinh,
hpind.actieverval,
hpind.actieaanpgel,
hpind.waarde
from
kern.his_persindicatie hpind
where hpind.persindicatie in (select id from kern.persindicatie where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon1|}))
order by hpind.persindicatie, hpind.dataanvgel, hpind.tsreg, hpind.dateindegel ASC;
