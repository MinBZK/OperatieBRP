select
p.id,
p.bsn,
p.geslnaamstam,
i.id,
i.pers,
i.srt,
i.waarde,
hi.persindicatie,
hi.tsreg,
hi.tsverval,
hi.dataanvgel,
hi.dateindegel,
hi.actieinh,
hi.actieaanpgel,
hi.waarde
from kern.pers p, kern.persindicatie i, kern.his_persindicatie hi
where i.pers = p.id AND hi.persindicatie = i.id AND
(p.bsn = ${DataSource Values#|objectid.persoon22|})
order by i.srt asc;
