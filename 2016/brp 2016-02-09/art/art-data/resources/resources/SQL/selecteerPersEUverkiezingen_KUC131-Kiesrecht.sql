select
p.induitslkiesr,
p.datvoorzeindeuitslkiesr,
p.inddeelneuverkiezingen,
p.datvoorzeindeuitsleuverkiezi,
hpe.tsreg hpe_tsreg,
hpe.tsverval hpe_tsverval,
hpe.actieinh hpe_actieinh,
hpe.actieverval hpe_actieverval,
hpe.inddeelneuverkiezingen hpe_inddeelneuverkiezingen,
hpe.dataanlaanpdeelneuverkiezing hpe_dataanlaanpdeelneuverkiezing,
hpe.datvoorzeindeuitsleuverkiezi hpe_datvoorzeindeuitsleuverkiezi
from kern.pers p, kern.his_persdeelneuverkiezingen hpe
where hpe.pers = p.id  AND
  (p.bsn = ${DataSource Values#objectid.persoon2})
order by p.bsn, hpe.dataanlaanpdeelneuverkiezing, hpe.tsreg;
