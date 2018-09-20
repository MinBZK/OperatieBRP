select
hpna.id,
hpna.persnation,
hpna.dataanvgel,
hpna.dateindegel,
hpna.tsreg,
hpna.tsverval,
hpna.actieinh,
hpna.actieverval,
hpna.actieaanpgel,
hpna.rdnverk,
hpna.rdnverlies
from
kern.his_persnation hpna
where hpna.persnation in (select id from kern.persnation where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon1|}))
order by hpna.persnation, hpna.dataanvgel, hpna.tsreg, hpna.dateindegel ASC;
