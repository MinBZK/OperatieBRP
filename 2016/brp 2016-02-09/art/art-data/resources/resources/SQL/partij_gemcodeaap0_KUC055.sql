select
  partij.dateinde,
  partij.dataanv,
  partij.id,
  partij.naam,
  partij.srt,
  partij.code,
  gem.partij,
  gem.dataanvgel,
  gem.dateindegel
from
  kern.partij,
  kern.gem
where
  (partij.id = gem.partij and
   gem.code = ${DataSource Values#gemeenteCode_rOO0});
