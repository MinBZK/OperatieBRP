select
pgc.id,
pgc.pers,
pgc.volgnr,
pgc.predicaat,
pgc.adellijketitel,
pgc.voorvoegsel, '['||pgc.scheidingsteken||']' scheidingsteken,
pgc.stam
from
kern.persgeslnaamcomp pgc
where
pers in (select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon1|});
