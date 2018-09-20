select
hpeu.id,
hpeu.pers,
hpeu.tsreg,
hpeu.tsverval,
hpeu.actieinh,
hpeu.actieverval,
hpeu.inddeelneuverkiezingen,
hpeu.dataanlaanpdeelneuverkiezing,
hpeu.datvoorzeindeuitsleuverkiezi
from
kern.his_persdeelneuverkiezingen hpeu
where hpeu.pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon1|})
order by hpeu.pers, hpeu.tsreg, hpeu.tsverval ASC;
