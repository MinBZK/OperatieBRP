select
p.id pers_id,
p.bsn, p.naamgebruik,
p.indnaamgebruikafgeleid,
p.predicaatnaamgebruik,
p.voornamennaamgebruik,
p.adellijketitelnaamgebruik, Concat (p.voorvoegselnaamgebruik, p.scheidingstekennaamgebruik, p.geslnaamstamnaamgebruik) voorvoegsel_Scheidingsteken_geslnaamstamnaamgebruik
from kern.pers p
where
p.bsn = ${burgerservicenummer_ipk0}
order by p.bsn ASC;
