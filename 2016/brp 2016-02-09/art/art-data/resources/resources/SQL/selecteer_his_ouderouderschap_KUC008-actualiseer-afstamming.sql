select
b1.relatie,
r1.id,
p1.bsn,
houd.id,
houd.betr,
houd.dataanvgel,
houd.dateindegel,
houd.tsreg,
houd.tsverval,
houd.actieinh,
houd.actieverval,
houd.actieaanpgel,
houd.indouder
from
kern.his_ouderouderschap houd,
kern.betr b1,
kern.pers p1,
kern.relatie r1
where
b1.relatie = r1.id and
b1.pers = p1.id and
houd.betr = b1.id and
(p1.bsn = ${DataSource Values#|objectid.persoon23|})
order by
    p1.bsn, r1.id , p1.id
