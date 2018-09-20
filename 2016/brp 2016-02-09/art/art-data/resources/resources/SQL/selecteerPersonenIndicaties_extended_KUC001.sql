select
p.id p_id,
p.bsn p_bsn,
p.geslnaamstam,
i.id i_id,
i.pers i_pers,
i.srt i_srt,
si.naam,
i.waarde i_waarde,
hi.persindicatie h_persindicatie,
hi.tsreg h_tsreg,
hi.tsverval h_tsverval,
hi.dataanvgel h_dataanvgel,
hi.dateindegel h_dateindegel,
hi.actieinh h_actieinh,
hi.actieaanpgel h_actieaanpgel,
hi.waarde h_waarde
from kern.pers p, kern.persindicatie i, kern.his_persindicatie hi, kern.srtindicatie si
where i.pers = p.id AND hi.persindicatie = i.id AND si.id = i.srt AND
(p.bsn = ${burgerservicenummer_B00});
