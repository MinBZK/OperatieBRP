select
hpvn.id,
hpvn.persvoornaam,
hpvn.dataanvgel,
hpvn.dateindegel,
hpvn.tsreg,
hpvn.tsverval,
hpvn.actieinh,
hpvn.actieverval,
hpvn.actieaanpgel,
hpvn.naam
from
kern.his_persvoornaam hpvn
where hpvn.persvoornaam in (select id from kern.persvoornaam where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon2|}))
order by hpvn.persvoornaam, hpvn.dataanvgel, hpvn.tsreg, hpvn.dateindegel ASC;
