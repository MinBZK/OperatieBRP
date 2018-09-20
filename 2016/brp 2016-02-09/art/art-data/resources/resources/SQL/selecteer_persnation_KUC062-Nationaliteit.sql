select 
pna.id, 
pna.pers, 
pna.nation, 
pna.rdnverk,
pna.rdnverlies 
from 
kern.persnation pna 
where pna.pers in (select id from kern.pers where pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon1|}))
order by pna.pers, pna.nation ASC;