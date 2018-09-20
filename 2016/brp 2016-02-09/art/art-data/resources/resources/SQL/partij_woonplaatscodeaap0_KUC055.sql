select
  partij.dateinde,
  partij.dataanv,
  partij.id,
  partij.naam,
  partij.srt,
  partij.code
from
  kern.persadres,
  kern.plaats,
  kern.gem,
  kern.partij
where
  gem.id = persadres.gem and
  gem.partij = partij.id and
  plaats.naam = persadres.wplnaam and
 (persadres.wplnaam = '${DataSource Values#woonplaatsnaam_rOO0}');

