select p.id, p.bsn, p.geslnaam, p.voorvoegsel, p.scheidingsteken, p.geslnaamaanschr, p.voorvoegselaanschr, p.scheidingstekenaanschr 
from kern.pers p  
where 
(p.bsn = ${burgerservicenummer_ipr0} or p.bsn= ${DataSource Values#burgerservicenummer_ipp_2})
order by p.bsn ASC;