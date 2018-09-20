SELECT
  huwelijk.id,
  huwelijk.relatie,
  huwelijk.tsreg,
  huwelijk.tsverval,
  huwelijk.actieinh,
  huwelijk.actieverval,
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
  huwelijk.omsloceinde
FROM
  kern.his_relatie huwelijk,
  kern.betr,
  kern.pers
WHERE
  betr.relatie = huwelijk.relatie AND
  betr.pers = pers.id AND
 (pers.bsn = ${DataSource Values#|bsn_opr0|})
  order by betr.pers, huwelijk.dataanv ASC, huwelijk.tsreg ASC;
