select
  p.id as pid,
  p.bsn,
  p.geslnaamstam,
  i.id as iid,
  i.pers,
  i.srt,
  i.waarde as iwaarde,
  hi.persindicatie,
  hi.tsreg,
  hi.tsverval,
  hi.dataanvgel,
  hi.dateindegel,
  hi.actieinh,
  hi.actieaanpgel,
  hi.waarde as hiwaarde
from kern.pers p, kern.persindicatie i, kern.his_persindicatie hi
where i.pers = p.id AND hi.persindicatie = i.id AND
  (p.bsn = ${objectid$persoon1})
order by p.bsn, hi.dataanvgel, hi.tsreg;
