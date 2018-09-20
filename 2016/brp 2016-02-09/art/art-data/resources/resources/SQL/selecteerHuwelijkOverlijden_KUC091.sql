SELECT
  huwelijk.id huwelijk_id,
  trim(to_char(huwelijk.dataanv,'0000-00-00'))dataanv,
  huwelijk.gemaanv,
  huwelijk.wplnaamaanv,
  huwelijk.blplaatsaanv,
  huwelijk.blregioaanv,
  huwelijk.landgebiedaanv,
  huwelijk.omslocaanv,
  huwelijk.rdneinde,
  trim(to_char(huwelijk.dateinde,'0000-00-00'))dateinde,
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
  trim(to_char(pers.datoverlijden, '0000-00-00'))datoverlijden,
  pers.id
FROM
  kern.relatie huwelijk,
  kern.betr,
  kern.pers
WHERE
  betr.relatie = huwelijk.id AND
  betr.pers = pers.id AND
  (pers.bsn = ${DataSource Values#|bsn_opr0|})
  AND
  huwelijk.srt = 1
  order by betr.pers, huwelijk.dataanv ASC;
