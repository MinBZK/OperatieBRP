select
hpreis.id,
hpreis.persreisdoc,
hpreis.tsreg,
hpreis.tsverval,
hpreis.actieinh,
hpreis.actieverval,
hpreis.nr,
hpreis.autvanafgifte,
hpreis.datingangdoc,
hpreis.datuitgifte,
hpreis.dateindedoc,
hpreis.datinhingvermissing,
hpreis.aandinhingvermissing
from
kern.his_persreisdoc hpreis
where hpreis.persreisdoc in (select id from kern.persreisdoc where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon2|}))
order by hpreis.id, hpreis.persreisdoc, hpreis.tsreg, hpreis.actieinh ASC;
