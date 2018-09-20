select
 plaats.id,
 plaats.code,
 plaats.naam
from
 kern.plaats,
 kern.persadres,
 kern.pers
where
 pers.id = persadres.pers and
 persadres.wplnaam = plaats.id and
(pers.bsn = ${DataSource Values#|bsn_rOO0|});
