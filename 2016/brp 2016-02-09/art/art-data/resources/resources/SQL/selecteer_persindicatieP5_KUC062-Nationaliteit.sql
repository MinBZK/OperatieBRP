select 
pind.id, 
pind.pers, 
pind.srt, 
pind.waarde 
from 
kern.persindicatie pind 
where pind.pers in (select id from kern.pers where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon5|}))
order by pind.srt DESC;