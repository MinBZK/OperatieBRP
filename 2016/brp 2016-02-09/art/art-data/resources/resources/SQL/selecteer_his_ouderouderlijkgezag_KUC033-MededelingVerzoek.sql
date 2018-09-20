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
houd.indouderheeftgezag
from
kern.his_ouderouderlijkgezag houd
left join kern.betr b1 on (houd.betr = b1.id)
left join kern.pers p1 on (b1.pers = p1.id)
left join kern.relatie r1 on (b1.relatie = r1.id)
where
(p1.bsn = ${DataSource Values#objectid.persoon3} or p1.bsn = ${DataSource Values#objectid.persoon4})
order by
    p1.bsn, houd.betr, houd.dataanvgel DESC, houd.dateindegel DESC
