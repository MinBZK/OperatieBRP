select
ph.id his_persnaamgebruik_id,
p.bsn, ph.naamgebruik,
ph.indnaamgebruikafgeleid,
ph.predicaatnaamgebruik,
ph.voornamennaamgebruik,
ph.adellijketitelnaamgebruik,
ph.voorvoegselnaamgebruik, '['||ph.scheidingstekennaamgebruik||']' scheidingstekennaamgebruik, ph.geslnaamstamnaamgebruik
from kern.his_persnaamgebruik ph, kern.pers p
where p.id = ph.pers and ph.pers in (select id from kern.pers p  where  p.bsn = ${burgerservicenummer_ipk0} )
