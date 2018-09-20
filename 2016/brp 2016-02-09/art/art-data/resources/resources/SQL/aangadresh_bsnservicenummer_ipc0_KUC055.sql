select
  kern.aang.id,
  kern.aang.code,
  kern.aang.naam,
  kern.aang.oms
from
  kern.aang,
  kern.persadres,
  kern.pers
where
  persadres.aangadresh = aang.id and
  persadres.pers = pers.id and
 (pers.bsn = ${DataSource Values#|bsn_rOO0|});
