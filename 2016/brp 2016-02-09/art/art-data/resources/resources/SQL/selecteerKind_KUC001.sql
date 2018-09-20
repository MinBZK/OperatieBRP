select p.id, p.bsn, p.geslnaamstam, p.voorvoegsel, '['||p.scheidingsteken||']' scheidingsteken, p.geslnaamstamnaamgebruik, p.voorvoegselnaamgebruik, '['||p.scheidingstekennaamgebruik||']'
from kern.pers p
where
p.bsn = ${burgerservicenummer_B00}
order by p.bsn ASC;
