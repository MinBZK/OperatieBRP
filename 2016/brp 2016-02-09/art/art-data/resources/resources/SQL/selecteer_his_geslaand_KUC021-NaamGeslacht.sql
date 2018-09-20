select
hpgs.id,
hpgs.pers,
hpgs.dataanvgel,
hpgs.dateindegel,
hpgs.tsreg,
hpgs.tsverval,
hpgs.actieinh,
hpgs.actieverval,
hpgs.actieaanpgel,
hpgs.geslachtsaand
from
kern.his_persgeslachtsaand hpgs
where hpgs.pers in (select id from kern.pers where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon1|}))
order by hpgs.pers, hpgs.dataanvgel, hpgs.tsreg, hpgs.dateindegel ASC;
