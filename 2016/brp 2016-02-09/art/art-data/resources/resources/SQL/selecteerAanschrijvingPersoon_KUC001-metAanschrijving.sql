select
p.id pers_id,
p.bsn,
p.naamgebruik,
p.indnaamgebruikafgeleid,
p.predicaatnaamgebruik,
p.voornamennaamgebruik,
p.adellijketitelnaamgebruik,
p.voorvoegselnaamgebruik, '['||p.scheidingstekennaamgebruik||']' scheidingstekennaamgebruik, p.geslnaamstamnaamgebruik
from kern.pers p
where
p.bsn = ${burgerservicenummer_ipk0}
order by p.bsn ASC;
