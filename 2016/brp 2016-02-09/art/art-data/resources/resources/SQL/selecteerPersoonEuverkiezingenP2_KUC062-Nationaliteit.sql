select p.id, p.bsn, p.inddeelneuverkiezingen 
from kern.pers p  
where 
(p.bsn= ${DataSource Values#|objectid.persoon2|})
order by p.bsn ASC;