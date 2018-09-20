select p.id, p.bsn, p.admhnd, p.tslaatstewijz, p.sorteervolgorde
from 
kern.pers p
where 
(p.bsn = ${DataSource Values#|objectid.persoon1|})
order by
p.bsn;