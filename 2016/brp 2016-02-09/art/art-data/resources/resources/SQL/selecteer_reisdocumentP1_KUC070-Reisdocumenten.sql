select
preis.id,
preis.pers,
preis.srt,
preis.nr,
preis.autvanafgifte,
preis.datingangdoc,
preis.datuitgifte,
preis.dateindedoc,
preis.datinhingvermissing,
preis.aandinhingvermissing
from
kern.persreisdoc preis
where preis.pers in (select id from kern.pers where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon1|}))
order by preis.id, preis.pers ASC;
