SELECT
  huwelijk.dataanv,
  huwelijk.gemaanv,
  huwelijk.wplnaamaanv,
  huwelijk.blplaatsaanv,
  huwelijk.blregioaanv,
  huwelijk.landgebiedaanv,
  huwelijk.omslocaanv,
  huwelijk.rdneinde,
  huwelijk.dateinde,
  huwelijk.gemeinde,
  huwelijk.wplnaameinde,
  huwelijk.blplaatseinde,
  huwelijk.blregioeinde,
  huwelijk.landgebiedeinde,
  huwelijk.omsloceinde,
  huwelijk.id,
  huwelijk.srt,
  betr.indouder,
  betr.id,
  betr.relatie,
  betr.rol,
  betr.pers,
  betr.indouderheeftgezag,
  pers.id
FROM
  kern.relatie huwelijk,
  kern.betr,
  kern.pers
WHERE
  betr.relatie = huwelijk.id AND
  betr.pers = pers.id AND betr.rol = 3 AND
  (pers.bsn = ${DataSource Values#burgerservicenummer_ipp41} or pers.bsn = ${DataSource Values#burgerservicenummer_ipp51})
  order by betr.pers ASC;
