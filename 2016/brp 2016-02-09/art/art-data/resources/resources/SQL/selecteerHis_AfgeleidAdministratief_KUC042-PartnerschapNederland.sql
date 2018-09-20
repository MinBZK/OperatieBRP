select p.id, h.pers, h.actieinh, h.admhnd, h.tslaatstewijz, h.sorteervolgorde
from 
kern.pers p,
kern.his_persafgeleidadministrati h
where
h.pers=p.id and 
(p.bsn = ${DataSource Values#|objectid.persoon1|} or p.bsn = ${DataSource Values#|objectid.persoon2|})
order by
h.tslaatstewijz desc,
p.id asc;