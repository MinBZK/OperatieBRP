SELECT
  substring(sr.naam,0,9) relatie,
  relatie.dataanv,
  relatie.gemaanv,
  relatie.wplnaamaanv,
  relatie.blplaatsaanv,
  relatie.blregioaanv,
  relatie.landgebiedaanv,
  relatie.omslocaanv,
  relatie.rdneinde,
  relatie.dateinde,
  relatie.gemeinde,
  relatie.wplnaameinde,
  relatie.blplaatseinde,
  relatie.blregioeinde,
  relatie.landgebiedeinde,
  relatie.omsloceinde,
  relatie.id,
  relatie.srt,
  betr.indouder,
  betr.id,
  betr.relatie,
  betr.rol,
  betr.pers,
  betr.indouderheeftgezag,
  pers.id
FROM
  kern.relatie relatie,
  kern.srtrelatie sr,
  kern.betr,
  kern.pers
WHERE
  betr.relatie = relatie.id AND
  sr.id = relatie.srt AND
  betr.pers = pers.id AND betr.rol = 3 AND
  (pers.bsn = ${DataSource Values#|objectid.persoon4|} or pers.bsn = ${DataSource Values#|objectid.persoon5|})
  order by sr.naam asc, betr.pers ASC;
