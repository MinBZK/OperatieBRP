select 
pind.id, 
pind.pers, 
pind.srt, 
pind.waarde 
from 
kern.persindicatie pind 
where pind.pers in (select id from kern.pers where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon1|}))
order by pind.id ASC;