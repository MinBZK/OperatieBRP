select
p.induitslkiesr,
p.datvoorzeindeuitslkiesr,
p.inddeelneuverkiezingen,
p.datvoorzeindeuitsleuverkiezi,
hpu.tsreg hppu_tsreg,
hpu.tsverval hpu_tsverval,
hpu.actieinh hpu_actieinh,
hpu.actieverval hpu_actieverval,
hpu.induitslkiesr hpu_induitslkiesr,
hpu.datvoorzeindeuitslkiesr hpu_datvoorzeindeuitslkiesr
from kern.pers p, kern.his_persuitslkiesr hpu
where hpu.pers = p.id  AND
  (p.bsn = ${DataSource Values#objectid.persoon1})
order by p.bsn, hpu.datvoorzeindeuitslkiesr, hpu.tsreg;
