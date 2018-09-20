select p.id, p.bsn, p.admhnd, p.tslaatstewijz, p.sorteervolgorde
from 
kern.pers p
where 
(p.bsn = ${DataSource Values#burgerservicenummer_B00} or p.bsn = ${DataSource Values#|objectid.burgerservicenummer_ipo_B01|} or p.bsn = ${DataSource Values#|objectid.burgerservicenummer_ipo_B03|})
order by
p.bsn;