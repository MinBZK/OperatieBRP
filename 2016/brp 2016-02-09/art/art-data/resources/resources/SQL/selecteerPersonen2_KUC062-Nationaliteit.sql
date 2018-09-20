select p.id, p.bsn, p.geslnaamstam, p.voorvoegsel, '['||p.scheidingsteken||']' scheidingsteken, p.voornamen, p.geslachtsaand, p.predicaat, p.adellijketitel
from kern.pers p
where
(p.bsn= ${DataSource Values#|objectid.persoon2|})
order by p.bsn ASC;
